/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.model.session;

import com.flowyk.fb.entity.RegisteredUser;
import com.flowyk.fb.entity.facade.custom.CustomRegisteredUserFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author Lukas
 */
@SessionScoped
public class AppData implements Serializable {
    
    @EJB
    CustomRegisteredUserFacade registeredUserFacade;
    
    private RegisteredUser reference = null;
    
    public void parseJson(JsonObject json) {
        if (json == null) {
            return;
        }
        setReference(json.getInt("reference", 0));
    }

    public JsonObject getAsJson() {
        JsonObject json = Json.createObjectBuilder()
                .add("reference", (getReference() != null ? getReference().getId() : 0))
                .build();
        return json;
    }
    
    public RegisteredUser getReference() {
        return reference;
    }

    public void setReference(RegisteredUser reference) {
        this.reference = reference;
    }
    
    public void setReference(int referenceId) {
        if (referenceId != 0) {
            this.reference = registeredUserFacade.find(referenceId);
        }
    }
    
}
