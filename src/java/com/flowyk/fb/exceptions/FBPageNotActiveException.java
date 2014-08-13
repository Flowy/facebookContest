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
public class FBPageNotActiveException extends RuntimeException {

    /**
     * Creates a new instance of <code>FBPageNotActiveException</code> without
     * detail message.
     */
    public FBPageNotActiveException() {
    }

    /**
     * Constructs an instance of <code>FBPageNotActiveException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public FBPageNotActiveException(String msg) {
        super(msg);
    }
}
