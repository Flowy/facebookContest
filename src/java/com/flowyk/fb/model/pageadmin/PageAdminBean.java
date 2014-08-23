/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model.pageadmin;

import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.entity.RegisteredPage;
import com.flowyk.fb.entity.RegisteredUser;
import com.flowyk.fb.entity.facade.ContestFacade;
import com.flowyk.fb.entity.facade.RegisteredPageFacade;
import com.flowyk.fb.entity.facade.RegisteredUserFacade;
import com.flowyk.fb.jaxb.ContestUsers;
import com.flowyk.fb.model.Login;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * must have login initialized
 * @author Lukas
 */
@Named
@RequestScoped
public class PageAdminBean implements Serializable {

    @Inject
    private Login login;

    @EJB
    RegisteredPageFacade registeredPageFacade;
    
    @EJB
    RegisteredUserFacade registeredUserFacade;
    
    @EJB
    ContestFacade contestFacade;
    
    @Inject
    ContestEditBean contestEditBean;

    // Actions -----------------------------------------------------------------------------------
    
    public String activate() {
        RegisteredPage page = login.getPage();
        page.setActive(true);
        registeredPageFacade.edit(page);
        return "admin";
    }
    
    public String createNewContest() {
        contestEditBean.createNewContest();
        return "contest-edit";
    }

    // Getters -----------------------------------------------------------------------------------
    public List<Contest> getContests() {
        List<Contest> list = contestFacade.findByPage(login.getPage());
        return list;
    }
    
    public String getXmlOutputString() throws JAXBException {
        String output = new String(getXmlOutputStream().toByteArray(), StandardCharsets.UTF_8);
        return output;
    }
    
    private ByteArrayOutputStream getXmlOutputStream() throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(RegisteredUser.class, ContestUsers.class, Contest.class);
        
        List<RegisteredUser> pageUsers = registeredUserFacade.findByRegisteredPage(login.getPage());
        
        ContestUsers contestUsers = new ContestUsers(pageUsers);
        
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        m.marshal(contestUsers, outputStream);
        return outputStream;
    }

    public StreamedContent getXMLExport() throws JAXBException {
        ByteArrayOutputStream outputStream = getXmlOutputStream();
        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        String name = "contestPageExport.xml";
        String mime = "application/xml";
        StreamedContent stream = new DefaultStreamedContent(inputStream, mime, name);
        return stream;
    }
    
    // Setters -----------------------------------------------------------------------------------
}
