/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model.signedrequest;

import javax.json.JsonObject;

/**
 *
 * @author Lukas
 */
public class Page {

    private String id;
    private boolean liked;
    private boolean admin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void parseJsonObject(JsonObject jObject) {
        if (jObject == null) {
            return;
        }
        this.id = jObject.getString("id", null);
        this.liked = jObject.getBoolean("liked", false);
        this.admin = jObject.getBoolean("admin", false);
    }
}
