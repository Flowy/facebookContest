/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model;

import com.flowyk.fb.entity.RegisteredUser;
import com.flowyk.fb.entity.facade.RegisteredUserFacade;
import com.flowyk.fb.entity.facade.RegistrationFacade;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.validation.constraints.Pattern;

/**
 *
 * @author Lukas
 */
@Named(value = "returningBean")
@ViewScoped
public class ReturningBean implements Serializable {
    
    @EJB
    private RegisteredUserFacade registeredUserFacade;

    @EJB
    private RegistrationFacade registrationFacade;

    @Inject
    Login contestUser;
    
    @Inject
    private ContestBean contestBean;

    @Pattern(regexp = "[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+" //meno
            + "(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*" //subdomena
            + "@(?:[a-zA-Z0-9]+\\.)+" //domena
            + "[a-zA-Z]{1,4}")  //root
    private String returningEmail;

    public String registerNewTicket() {

//        List<RegisteredUser> userList = registeredUserFacade.findByContestAndEmail(contestBean.getActiveContest(), returningEmail);
//
//        if (userList.isEmpty()) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zadaný email ešte nieje v súťaži", "Zadaný email ešte nieje v súťaži"));
//            return null;
//        } else {
//            RegisteredUser user = userList.iterator().next();
////            contestUser.setContestUser(user);
//            
//            
//            Calendar lastTicket = registrationFacade.getLastTicketTime(user);
//            long lastTicketMillis = lastTicket.getTimeInMillis();
//
//            long delayMillis = user.getContest().getTimeBetweenTickets().getTime();
//
//            Calendar nearestTicket = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
//            nearestTicket.setTimeInMillis(lastTicketMillis + delayMillis);
//
//            if (Calendar.getInstance().after(nearestTicket)) {
////                contestBean.createNewTicket(user, Constants.RETURNING_TICKETS, null);
//                return "thanks";
//            } else {
//                //needed delay for new ticket not passed
//                String msg = "Zatiaľ neuplinul potrebný čas pre znovuregistráciu, najbližšie sa môžte registrovať: {1}".replace("{1}", nearestTicket.toString());
//                FacesContext.getCurrentInstance().addMessage(
//                        null,
//                        new FacesMessage(
//                                FacesMessage.SEVERITY_ERROR,
//                                msg, msg
//                        )
//                );
//                return null;
//            }
//        }
        return null;
    }

    public String getReturningEmail() {
        return returningEmail;
    }

    public void setReturningEmail(String returningEmail) {
        this.returningEmail = returningEmail;
    }

}
