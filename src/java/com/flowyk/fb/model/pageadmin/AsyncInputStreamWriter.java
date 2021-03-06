/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model.pageadmin;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Lukas
 */
public class AsyncInputStreamWriter implements Runnable {

    private final InputStream file;
    private final Path outPath;

    public AsyncInputStreamWriter(InputStream inputStream, Path path) {
        this.file = inputStream;
        this.outPath = path;
    }

    @Override
    public void run() {
        try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(outPath, CREATE, TRUNCATE_EXISTING))) {
            byte[] data = new byte[1024];
            while (file.read(data) != -1) {
                out.write(data);
            }
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage("", "File " + outPath.getFileName() + ": upload successful.")
            );
        } catch (IOException ex) {
            Logger.getLogger(AsyncInputStreamWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
