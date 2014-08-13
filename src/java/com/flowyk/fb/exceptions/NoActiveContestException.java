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
public class NoActiveContestException extends RuntimeException {

    /**
     * Creates a new instance of <code>NoActiveContestException</code> without
     * detail message.
     */
    public NoActiveContestException() {
        super();
    }

    /**
     * Constructs an instance of <code>NoActiveContestException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NoActiveContestException(String msg) {
        super(msg);
    }
}
