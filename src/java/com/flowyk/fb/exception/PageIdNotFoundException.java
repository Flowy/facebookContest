/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.flowyk.fb.exception;

/**
 *
 * @author Lukas
 */
public class PageIdNotFoundException extends RuntimeException {

    /**
     * Creates a new instance of <code>PageIdNotFoundException</code> without
     * detail message.
     */
    public PageIdNotFoundException() {
        super();
    }

    /**
     * Constructs an instance of <code>PageIdNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PageIdNotFoundException(String msg) {
        super(msg);
    }
}
