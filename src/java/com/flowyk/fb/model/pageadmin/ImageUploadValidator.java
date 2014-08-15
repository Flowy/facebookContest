/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model.pageadmin;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

/**
 *
 * @author Lukas
 */
@FacesValidator
public class ImageUploadValidator implements Validator {

    private final static long maxFileSize = 5L * 1024L * 1024L;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        List<FacesMessage> msgs = new ArrayList<>();
        Part tempFile = (Part) value;
        if (tempFile.getSubmittedFileName().isEmpty()) {
            String msg = "No file selected";
            msgs.add(new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
        } else {
            String expectedFileName = tempFile.getName().replace("uploadForm:", "") + ".png";
            if (!expectedFileName.equals(tempFile.getSubmittedFileName())) {
                String msg = "Expected name of image: " + expectedFileName + " got name: " + tempFile.getSubmittedFileName();
                msgs.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            }
            if (tempFile.getSize() > maxFileSize) {
                String msg = "Maximum size of file is: " + maxFileSize;
                msgs.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            }
        }
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }
}
