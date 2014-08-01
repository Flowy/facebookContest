/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.oauth;

import com.flowyk.fb.sigrequest.SignedRequest;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
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
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParsingException;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.exceptions.OAuthException;
import org.scribe.model.OAuthConstants;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import static com.flowyk.fb.base.Constants.*;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Lukas
 */
@Named
@SessionScoped
public class FacebookLogin implements Serializable {

    private static final Logger LOG = Logger.getLogger(FacebookLogin.class.getName());

    private static final String API_KEY = "675005359221214";
    private static final String API_SECRET = "842b931caf789225b22182d92a670bf0";
    private static final String CALLBACK_URL = "https://91.148.1.245:8181/FacebookSutaz-newwar/contest.xhtml";

    private final static OAuthService service;
    private final static String authUrl;
    private static final Mac sha256_HMAC;
    private static final SecretKeySpec hmacKey;

    static {
        service = new ServiceBuilder()
                .provider(FacebookApi.class)
                .apiKey(API_KEY)
                .apiSecret(API_SECRET)
                .callback(CALLBACK_URL)
                .build();
        authUrl = service.getAuthorizationUrl(OAuthConstants.EMPTY_TOKEN);

        hmacKey = new SecretKeySpec(API_SECRET.getBytes(), "HmacSHA256");
        Mac dummy;
        try {
            dummy = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(FacebookLogin.class.getName()).log(Level.SEVERE, null, ex);
            dummy = null;
        }
        sha256_HMAC = dummy;
    }

    private String code;
    private Token accessToken = null;
    private FacebookClient fbClient;

    private SignedRequest signedRequest = null;

    // Actions -----------------------------------------------------------------------------------
    public String goAuthenticate() {
        return authUrl;
    }

    public String goAddPageTab() {
        return "https://www.facebook.com/dialog/pagetab?app_id=" + API_KEY + "&redirect_uri=" + CALLBACK_URL;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * decodes signed request from facebook and sets token
     *
     * @param signedRequestInput
     */
    public void parseSignedRequest(String signedRequestInput) {
        String signature = signedRequestInput.substring(0, signedRequestInput.indexOf('.'));
        String payload = signedRequestInput.substring(signedRequestInput.indexOf('.') + 1);
        boolean signatureOk = false;

        Base64.Decoder decoder = Base64.getUrlDecoder();
        try {
            sha256_HMAC.init(hmacKey);
            String decodedSignature = new String(decoder.decode(signature.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            String payloadHash = new String(sha256_HMAC.doFinal(payload.getBytes()), StandardCharsets.UTF_8);
            signatureOk = !decodedSignature.isEmpty() && decodedSignature.equals(payloadHash);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(FacebookLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (signatureOk) {
            String decodedPayload = new String(decoder.decode(payload), StandardCharsets.UTF_8);
            if (FINE_DEBUG) {
                LOG.log(Level.INFO, "Decoded payload: {0}", decodedPayload);
            }
            try (JsonReader jsonReader = Json.createReader(new StringReader(decodedPayload))) {
                JsonObject jObject = jsonReader.readObject();
                signedRequest = SignedRequest.parseJson(jObject);
            } catch (JsonParsingException e) {
                LOG.log(Level.SEVERE, "Decoded payload: " + decodedPayload, e);
            }
        }
    }

    // Getters -----------------------------------------------------------------------------------
    public boolean checkLogin() {
        if (code != null) {
            Verifier v = new Verifier(code);
            try {
                accessToken = service.getAccessToken(OAuthConstants.EMPTY_TOKEN, v);
                return true;
            } catch (OAuthException e) {
                System.out.println("OAUTH EXCEPTION " + e.getMessage());
                return false;
            }
        } else {
            return false;
        }
    }

    public FacebookClient getFacebookClient() {
        if (fbClient != null) {
            return fbClient;
        } else if (accessToken != null) {
            return new DefaultFacebookClient(accessToken.getToken(), accessToken.getSecret());
        } else {
            throw new IllegalStateException("Not logged in");
        }
    }

    public SignedRequest getSignedRequest() {
        return signedRequest;
    }

    public String getShareLink() {
        String pageLink = "https://www.facebook.com/" + getSignedRequest().getPageId();
        return pageLink;
    }

    public String getAppId() {
        return API_KEY;
    }

        // Setters -----------------------------------------------------------------------------------
}
