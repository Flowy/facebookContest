/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model.admin;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.Part;

/**
 *
 * @author Lukas
 */
@Named(value = "contestAdminBean")
@ViewScoped
public class ContestAdminBean implements Serializable {

    private Part file;
    private String fileContent;

    // Actions -----------------------------------------------------------------------------------
     public void validateFile(FacesContext ctx, UIComponent comp, Object value) {
        List<FacesMessage> msgs = new ArrayList<>();
        Part tempFile = (Part)value;
        msgs.add(new FacesMessage("file submitted name: " + tempFile.getSubmittedFileName()));
        msgs.add(new FacesMessage("content type: " + tempFile.getContentType()));
        if (tempFile.getSize() > 1024) {
            msgs.add(new FacesMessage("file too big"));
        }
        if (!"image/png".equals(tempFile.getContentType())) {
            msgs.add(new FacesMessage("not a text file"));
        }
//        if (!"".equals(tempFile.getSubmittedFileName())) {
//            msgs.add(new FacesMessage(""));
//        }
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }
     
    public void upload() {
        try {
            fileContent = new Scanner(file.getInputStream())
                    .useDelimiter("\\A").next();
        } catch (IOException e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                "error uploading file",
                                                null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    // Getters -----------------------------------------------------------------------------------
    public Part getFile() {
        return file;
    }
    
    public String getFileContent() {
        return fileContent;
    }

    // Setters -----------------------------------------------------------------------------------
    
    public void setFile(Part file) {
        this.file = file;
    }
    
    public void setFileContent(String content) {
        this.fileContent = content;
    }
}
