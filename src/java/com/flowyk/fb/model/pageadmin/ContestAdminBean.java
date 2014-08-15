/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model.pageadmin;

import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.model.session.ContestBean;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Lukas
 */
@Named(value = "contestAdminBean")
@ViewScoped
public class ContestAdminBean implements Serializable {

    @Inject
    ContestBean contestBean;

    @NotNull
    private Contest actualContest;

    public ContestAdminBean() {
    }
    // Actions -----------------------------------------------------------------------------------

    public void uploadFiles(FileUploadEvent event) {
//        System.out.println("uploading: " + event.getFile().getFileName());
        UploadedFile uploadedFile = event.getFile();
        this.actualContest = contestBean.getActiveContest();
        if (actualContest == null) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage("This page is not accessible the way you opened it, try it another way or contact administrator")
            );
            throw new IllegalStateException("Actual contest not set for contestAdminBean");
        }
        Path folderPath = Paths.get("C:\\var\\webapp\\images");
        if (!Files.exists(folderPath)) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage("Can not upload files to server - directory for files doesn't exists - contact administrator")
            );
            return;
        }
        Path contestPath = folderPath.resolve(actualContest.getRegisteredPage().getPageId());
        if (!Files.exists(contestPath)) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage("This contest does not have directory in file system - contact administrator, contest ID: " + actualContest.getRegisteredPage().getPageId())
            );
            return;
        }
        Path imagePath = contestPath.resolve(uploadedFile.getFileName());
        try {
            AsyncInputStreamWriter fileWriter = new AsyncInputStreamWriter(uploadedFile.getInputstream(), imagePath);
            fileWriter.run();
        } catch (IOException ex) {
            Logger.getLogger(ContestAdminBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Getters -----------------------------------------------------------------------------------
    public Contest getActualContest() {
        return actualContest;
    }

    // Setters -----------------------------------------------------------------------------------
    public void setActualContest(Contest actualContest) {
        this.actualContest = actualContest;
    }

}
