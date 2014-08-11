/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.model.canvas;

import com.flowyk.fb.base.Constants;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Lukas
 */
@Named(value="contestCanvasBean")
@RequestScoped
public class ContestCanvasBean implements Serializable {

    // Actions -----------------------------------------------------------------------------------

    // Getters -----------------------------------------------------------------------------------
    
    public String getAddPageTabLink() {
        return "https://www.facebook.com/dialog/pagetab?app_id="
                + Constants.API_KEY
                + "&redirect_uri=https://www.facebook.com";
    }
    // Setters -----------------------------------------------------------------------------------

}
