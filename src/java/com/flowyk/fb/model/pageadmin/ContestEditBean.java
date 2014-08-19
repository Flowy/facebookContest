/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model.pageadmin;

import com.flowyk.fb.base.Constants;
import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.entity.Prize;
import com.flowyk.fb.entity.PrizePK;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.validation.constraints.NotNull;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Lukas
 */
@Named(value = "contestEditBean")
@ViewScoped
public class ContestEditBean implements Serializable {

    public enum State {

        ILLEGAL, CREATING, EDITING
    }

    private State state = State.ILLEGAL;

    @NotNull
    private Contest selectedContest = new Contest();

    // Actions -----------------------------------------------------------------------------------
    public String createNewContest() {
        selectedContest = new Contest();
        state = State.CREATING;
        return "contest-edit";
    }

    public void save() {

    }

    public void createNewPrize() {
//        Prize prize = new Prize();

//        selectedContest.getPrizeList().add(prize);
    }
    
    public void rollWinnerFor(Prize prize) {
        
    }

    public void uploadFiles(FileUploadEvent event) {
        UploadedFile uploadedFile = event.getFile();
        if (selectedContest == null) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage("This page is not accessible the way you opened it, try it another way or contact administrator")
            );
            throw new IllegalStateException("Actual contest not set for contestAdminBean");
        }

        if (!Files.exists(Constants.LOCAL_IMAGES_PATH)) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage("Can not upload files to server - directory for files doesn't exists - contact administrator")
            );
            return;
        }
        Path contestPath = Constants.LOCAL_IMAGES_PATH.resolve(selectedContest.getRegisteredPage().getPageId());
        if (!Files.exists(contestPath)) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage("This contest does not have directory in file system - contact administrator, contest ID: " + selectedContest.getRegisteredPage().getPageId())
            );
            return;
        }
        Path imagePath = contestPath.resolve(uploadedFile.getFileName());
        try {
            AsyncInputStreamWriter fileWriter = new AsyncInputStreamWriter(uploadedFile.getInputstream(), imagePath);
            fileWriter.run();
        } catch (IOException ex) {
            Logger.getLogger(ContestEditBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Getters -----------------------------------------------------------------------------------
    public Contest getSelectedContest() {
        return selectedContest;
    }

    public List<Prize> getPrizes() {
        List<Prize> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            PrizePK pk = new PrizePK();
            pk.setPosition(i);
            Prize prize = new Prize();
            prize.setPrizePK(pk);
            prize.setName("Prize name");
            list.add(prize);
        }
        return list;
    }

    // Setters -----------------------------------------------------------------------------------
}
