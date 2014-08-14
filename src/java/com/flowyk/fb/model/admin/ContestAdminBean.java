/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model.admin;

import com.flowyk.fb.entity.Contest;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.Part;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Lukas
 */
@Named(value = "contestAdminBean")
@ViewScoped
public class ContestAdminBean implements Serializable {

    @NotNull
    private Contest actualContest;
    private final Map<String, Part> images;

    public ContestAdminBean() {
        images = new HashMap<>();
    }
    // Actions -----------------------------------------------------------------------------------

    public void uploadFiles() {
        Path folderPath = Paths.get("C:\\var\\webapp");
        if (!Files.exists(folderPath)) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage("Can not upload files to server - directory for files doesn't exists - contact administrator")
            );
            return;
        }
        Path contestPath = folderPath.resolve("/" + actualContest.getRegisteredPage().getPageId());
        if (!Files.exists(contestPath)) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage("This contest does not have directory in file system - contact administrator, contest ID: " + actualContest.getRegisteredPage().getPageId())
            );
            return;
        }
        for (Part image : images.values()) {
            Path imagePath = contestPath.resolve("/" + image.getSubmittedFileName() + ".png");

            if (Files.exists(imagePath)) {
                try {
                    Files.delete(imagePath);
                } catch (IOException ex) {
                    Logger.getLogger(ContestAdminBean.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    AsyncInputStreamWriter fileWriter = new AsyncInputStreamWriter(image.getInputStream(), imagePath);
                    fileWriter.run();
                } catch (IOException ex) {
                    Logger.getLogger(ContestAdminBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
//        if (file != null) {
//            try {
//                fileContent = new Scanner(file.getInputStream())
//                        .useDelimiter("\\A").next();
//            } catch (IOException e) {
//                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
//                        "error uploading file",
//                        null);
//                FacesContext.getCurrentInstance().addMessage(null, msg);
//            }
//        }
    }

    // Getters -----------------------------------------------------------------------------------
    public Map<String, Part> getImages() {
        return images;
    }

    public Contest getActualContest() {
        return actualContest;
    }

    // Setters -----------------------------------------------------------------------------------
    public void setActualContest(Contest actualContest) {
        this.actualContest = actualContest;
    }

}
