/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.model.canvas;

import static com.flowyk.fb.base.Constants.API_KEY;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Lukas
 */
@Named(value="contestCanvasBean")
@ViewScoped
public class ContestCanvasBean implements Serializable {
    
    private String pageId;

    // Actions -----------------------------------------------------------------------------------

    public void allowContests() {
        //TODO: script to create db row and image folder
    }

    // Getters -----------------------------------------------------------------------------------
    
    public String getAddPageTabLink() {
        return "https://www.facebook.com/dialog/pagetab?app_id="
                + API_KEY
                + "&redirect_uri=https://www.facebook.com";
    }
    
    public String getPageId() {
        return pageId;
    }

    // Setters -----------------------------------------------------------------------------------

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }
}
