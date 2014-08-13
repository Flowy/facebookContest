/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.exceptions;

/**
 *
 * @author Lukas
 */
public class SignedRequestNotSignedException extends RuntimeException {

    /**
     * Creates a new instance of <code>SignedRequestNotSignedException</code>
     * without detail message.
     */
    public SignedRequestNotSignedException() {
    }

    /**
     * Constructs an instance of <code>SignedRequestNotSignedException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public SignedRequestNotSignedException(String msg) {
        super(msg);
    }
}
