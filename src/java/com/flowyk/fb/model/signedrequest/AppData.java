/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.model.signedrequest;

import java.io.Serializable;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author Lukas
 */
public class AppData implements Serializable {
    
    private int reference;
    
    public void parseJson(JsonObject json) {
        if (json == null) {
            return;
        }
        AppData result = new AppData();
        result.reference = json.getInt("reference", 0);
    }

    public JsonObject getAsJson() {
        JsonObject json = Json.createObjectBuilder()
                .add("reference", getReference())
                .build();
        return json;
    }
    
    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }
    
}
