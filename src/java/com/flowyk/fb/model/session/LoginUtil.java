/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model.session;

import com.flowyk.fb.exceptions.MalformedSignedRequestException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParsingException;

/**
 *
 * @author Lukas
 */
public class LoginUtil {

    private static final Logger LOG = Logger.getLogger(LoginUtil.class.getName());

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

    /**
     * decodes signed request from facebook and sets token
     *
     * @param signedRequestInput
     * @return
     * @throws com.flowyk.fb.exceptions.MalformedSignedRequestException
     */
    public static JsonObject parseSignedRequest(String signedRequestInput) throws MalformedSignedRequestException, JsonParsingException {
        String signature = signedRequestInput.substring(0, signedRequestInput.indexOf('.'));
        String payload = signedRequestInput.substring(signedRequestInput.indexOf('.') + 1);

        Base64.Decoder decoder = Base64.getUrlDecoder();
        try {
            sha256_HMAC.init(hmacKey);
            String decodedSignature = new String(decoder.decode(signature.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            String payloadHash = new String(sha256_HMAC.doFinal(payload.getBytes()), StandardCharsets.UTF_8);
            if (decodedSignature.isEmpty() || !decodedSignature.equals(payloadHash)) {
                throw new MalformedSignedRequestException("Not signed");
            }
        } catch (InvalidKeyException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }

        String decodedPayload = new String(decoder.decode(payload), StandardCharsets.UTF_8);
        try (JsonReader jsonReader = Json.createReader(new StringReader(decodedPayload))) {
            JsonObject jObject = jsonReader.readObject();
            return jObject;
        } catch (JsonParsingException e) {
            LOG.log(Level.SEVERE, "Decoded payload: " + decodedPayload, e);
            throw e;
        }
    }
}
