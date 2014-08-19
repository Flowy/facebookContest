/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model.pageadmin;

import com.flowyk.fb.base.Constants;
import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.entity.Prize;
import com.flowyk.fb.entity.facade.ContestFacade;
import com.flowyk.fb.entity.facade.ContestLayoutFacade;
import com.flowyk.fb.entity.facade.RegisteredPageFacade;
import com.flowyk.fb.model.signedrequest.SignedRequest;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Lukas
 */
@Named(value = "contestEditBean")
@SessionScoped
public class ContestEditBean implements Serializable {

    @EJB
    ContestFacade contestFacade;

    @Inject
    SignedRequest signedRequest;

    @EJB
    RegisteredPageFacade registeredPageFacade;

    @EJB
    ContestLayoutFacade contestLayoutFacade;

    private Contest selectedContest = null;

    // Actions -----------------------------------------------------------------------------------
    public void createNewContest() {
        selectedContest = new Contest();
        selectedContest.setPrizeList(new ArrayList<>());
        String pageId = signedRequest.getPage().getPageId();
        selectedContest.setRegisteredPage(registeredPageFacade.find(pageId));
        selectedContest.setContestLayout(contestLayoutFacade.find("default"));
    }

    public void save() {
        if (selectedContest.getId() != null && contestFacade.find(selectedContest.getId()) != null) {
            contestFacade.edit(selectedContest);
        } else {
            contestFacade.create(selectedContest);
        }
    }

    public void createNewPrize() {
        Prize prize = new Prize();
        prize.setContest(selectedContest);
        selectedContest.getPrizeList().add(prize);
    }

    public void rollWinnerFor(Prize prize) {
//TODO: roll for winner
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
        return selectedContest.getPrizeList();
    }

    // Setters -----------------------------------------------------------------------------------
    public void setSelectedContest(Contest selectedContest) {
        this.selectedContest = selectedContest;
    }

}
