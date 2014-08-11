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
public class User {

    private String locale;
    private String country;
    private int ageMin;
    private int ageMax;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(int ageMin) {
        this.ageMin = ageMin;
    }

    public int getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(int ageMax) {
        this.ageMax = ageMax;
    }

    public void parseJsonObject(JsonObject jObject) {
        if (jObject == null) {
            return;
        }
        this.locale = jObject.getString("locale", null);
        this.country = jObject.getString("country", null);
        JsonObject age = jObject.getJsonObject("age");
        if (age != null) {
            this.ageMin = age.getInt("min", 0);
            this.ageMax = age.getInt("max", 0);
        }
    }
}
