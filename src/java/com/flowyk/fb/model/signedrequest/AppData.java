/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model.signedrequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Lukas
 */
public class AppData {

    private final static Pattern PATTERN = Pattern.compile("ref(?<reference>[0-9]+)");
    
    private Integer reference = null;

    public void setReference(Integer ref) {
        this.reference = ref;
    }
    
    public Integer getReference() {
        return reference;
    }

    public static AppData parseString(String header) {
        AppData instance = new AppData();
        String[] params = header.split(";");
        for (String param : params) {
            Matcher m = PATTERN.matcher(param);
            if (m.matches()) {
                String refIdString = m.group("reference");
                //cannot get non number - group matches [0-9]
                instance.reference = Integer.parseInt(refIdString);
            }
        }
        return instance;
    }
    
    public String getAsHeader() {
        return "app_data=ref" + reference;
    }
}
