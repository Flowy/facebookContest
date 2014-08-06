/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.viewbeans;

import com.flowyk.fb.auth.FacebookLogin;
import com.flowyk.fb.sigrequest.SignedRequest;
import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lukas
 */
@WebServlet(name = "LayoutGenerator", urlPatterns = {"/contest/backgrounds.css"})
public class LayoutGenerator extends HttpServlet {

    @Inject
    FacebookLogin login;

    private String getClassBg(String cssClass) {
        String result = null;
        if (cssClass != null) {
            String projectId = null;
            if (login != null) {
                SignedRequest sigReq = login.getSignedRequest();
                if (sigReq != null) {
                    projectId = sigReq.getPageId();
                }
            }
            result = String.format(
                    ".%1$s { background: url('%3$s/images/%2$s/%1$s-bg.png') no-repeat; } ", 
                    cssClass, projectId, getServletContext().getContextPath());
        }
        return result;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/css;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
//            out.println(getClassBg("registerForm"));
//            out.println(getClassBg("inputText"));
//            out.println(getClassBg("inputSubmit"));
//            out.println(getClassBg("base"));
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
