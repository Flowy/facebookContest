/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.sigrequest;

import java.io.Serializable;
import javax.json.JsonObject;

/**
 *
 * @author Lukas
 */
public class AppData implements Serializable {
    
    public static AppData parseJson(JsonObject json) {
        if (json == null) {
            return null;
        }
        AppData result = new AppData();
        
        return result;
    }
}
