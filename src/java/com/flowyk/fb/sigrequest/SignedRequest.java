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
public class SignedRequest implements Serializable {

    private String code;
    private String algorithm;
    private long issuedAt;
    private String userId;
    private String oauthToken;
    private long expires;

    private String ipAddress;
    private String userAgent;
    private String locale;
    private String country;
    private int ageMin;
    private int ageMax;
    private String pageId;
    private boolean pageLike;
    private boolean pageAdmin;

    private AppData appData;
    
    public String getCode() {
        return code;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public long getIssuedAt() {
        return issuedAt;
    }

    public String getUserId() {
        return userId;
    }

    public String getOauthToken() {
        return oauthToken;
    }

    public long getExpires() {
        return expires;
    }

    public AppData getAppData() {
        return appData;
    }

    public String getPageId() {
        return pageId;
    }

    public boolean isPageLike() {
        return pageLike;
    }

    public boolean isPageAdmin() {
        return pageAdmin;
    }

    public String getLocale() {
        return locale;
    }

    public String getCountry() {
        return country;
    }

    public int getAgeMin() {
        return ageMin;
    }

    public int getAgeMax() {
        return ageMax;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    
    public static SignedRequest parseJson(JsonObject json) {
        if (json == null) {
            return null;
        }
        SignedRequest result = new SignedRequest();
        result.code = json.getString("code", null);
        result.algorithm = json.getString("algorithm", null);
        result.userId = json.getString("user_id", null);
        result.oauthToken = json.getString("oauth_token", null);

        JsonObject page = json.getJsonObject("page");
        if (page != null) {
            result.pageId = page.getString("id", null);
            result.pageLike = page.getBoolean("liked", false);
            result.pageAdmin = page.getBoolean("admin", false);
        }
        JsonObject user = json.getJsonObject("user");
        if (user != null) {
            result.locale = user.getString("locale", null);
            result.country = user.getString("country", null);
            JsonObject age = user.getJsonObject("age");
            if (age != null) {
                result.ageMin = age.getInt("min", 0);
                result.ageMax = age.getInt("max", 0);
            }
        }
        result.appData = AppData.parseJson(json.getJsonObject("app_data"));
        System.out.println("New Signed Request: " + json.toString());

//        JsonNumber issuedTemp = json.getJsonNumber("issued_at");
//        if (issuedTemp != null) {
//            result.issuedAt = issuedTemp.longValue();
//        }
//        JsonNumber expiresTemp = json.getJsonNumber("expires");
//        if (expiresTemp != null) {
//            result.expires = expiresTemp.longValue();
//        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("code: ")
                .append(code)
                .append("\nalgorithm: ")
                .append(algorithm)
                .append("\nissued at: ")
                .append(issuedAt)
                .append("\nuser id: ")
                .append(userId)
                .append("\nlocale: ")
                .append(locale)
                .append("\ncountry: ")
                .append(country)
                .append("\nageMin: ")
                .append(ageMin)
                .append("\nage max: ")
                .append(ageMax)
                .append("\nOAuthToken: ")
                .append(oauthToken)
                .append("\nexpires: ")
                .append(expires)
                .append("\napp data: {")
                .append(appData)
                .append("}\npageId: ")
                .append(pageId)
                .append("\npage liked: ")
                .append(pageLike)
                .append("\npage admin: ")
                .append(pageAdmin);
        return sb.toString();
    }
}
