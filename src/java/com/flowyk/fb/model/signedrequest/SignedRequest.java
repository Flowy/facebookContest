/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model.signedrequest;

import com.flowyk.fb.exceptions.MalformedSignedRequestException;
import com.flowyk.fb.model.Login;
import java.io.Serializable;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParsingException;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Lukas
 */
@Named
@SessionScoped
public class SignedRequest implements Serializable {

    @Inject
    private Login login;

    private static final Logger LOG = Logger.getLogger(SignedRequest.class.getName());

    private static final String API_SECRET = "842b931caf789225b22182d92a670bf0";
    private static final Mac sha256_HMAC;
    private static final SecretKeySpec hmacKey;

    static {
        hmacKey = new SecretKeySpec(API_SECRET.getBytes(), "HmacSHA256");
        Mac dummy;
        try {
            dummy = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException ex) {
            LOG.log(Level.SEVERE, null, ex);
            dummy = null;
        }
        sha256_HMAC = dummy;
    }

    private final Page page = new Page();
    private final User user = new User();
    private AppData appData = new AppData();

    private boolean signed = false;

    public boolean isSigned() {
        return signed;
    }

    public Page getPage() {
        return page;
    }

    public User getUser() {
        return user;
    }

    public AppData getAppData() {
        return appData;
    }

    /**
     * decodes signed request from facebook and sets token
     *
     * @param signedRequestString
     * @throws com.flowyk.fb.exceptions.MalformedSignedRequestException if
     * signed request not signed or text malformed
     * @throws JsonParsingException
     * @throws NullPointerException if parameter is null
     */
    public void parseSignedRequest(@NotNull String signedRequestString) throws MalformedSignedRequestException {
        String signature = signedRequestString.substring(0, signedRequestString.indexOf('.'));
        String payload = signedRequestString.substring(signedRequestString.indexOf('.') + 1);

        Base64.Decoder decoder = Base64.getUrlDecoder();
        try {
            sha256_HMAC.init(hmacKey);
            String decodedSignature = new String(decoder.decode(signature.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            String payloadHash = new String(sha256_HMAC.doFinal(payload.getBytes()), StandardCharsets.UTF_8);
            if (decodedSignature.isEmpty() || !decodedSignature.equals(payloadHash)) {
                throw new MalformedSignedRequestException("Not signed");
            } else {
                signed = true;
            }
        } catch (InvalidKeyException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }

        String decodedPayload = new String(decoder.decode(payload), StandardCharsets.UTF_8);
        try (JsonReader jsonReader = Json.createReader(new StringReader(decodedPayload))) {
            JsonObject jObject = jsonReader.readObject();
            parseSignedRequestJson(jObject);
        } catch (JsonParsingException | MalformedSignedRequestException e) {
            throw new MalformedSignedRequestException("Decoded payload: " + decodedPayload, e);
        }
    }

    private void parseSignedRequestJson(@NotNull JsonObject json) throws MalformedSignedRequestException {
        System.out.println("Got signed request: " + json);
        JsonObject pageObject = json.getJsonObject("page");
        if (pageObject != null) {
            page.liked = pageObject.getBoolean("liked", false);
            page.admin = pageObject.getBoolean("admin", false);
            page.pageId = pageObject.getString("id", null);
            login.setPage(page.pageId);
            if (page.pageId == null) {
                throw new MalformedSignedRequestException("Unknown page id");
            }
        }

        JsonObject userObject = json.getJsonObject("user");
        if (userObject != null) {
            user.locale = userObject.getString("locale", null);
            user.country = userObject.getString("country", null);
            JsonObject age = userObject.getJsonObject("age");
            if (age != null) {
                user.ageMin = age.getInt("min", 0);
                user.ageMax = age.getInt("max", 0);
            }
        }
        
        String appDataString = json.getString("app_data", null);
        if (appDataString != null) {
            appData = AppData.parseString(appDataString);
        }
    }

    public static class Page implements Serializable {

        private boolean liked;
        private boolean admin;
        private String pageId;

        public boolean isLiked() {
            return liked;
        }

        public boolean isAdmin() {
            return admin;
        }

        public String getPageId() {
            return pageId;
        }

    }

    public static class User implements Serializable {

        private String locale;
        private String country;
        private int ageMin;
        private int ageMax;

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
    }
}
