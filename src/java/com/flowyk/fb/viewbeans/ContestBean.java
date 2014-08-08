/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.viewbeans;

import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.auth.FacebookLogin;
import com.flowyk.fb.entity.RegisteredUser;
import com.flowyk.fb.entity.Registration;
import com.flowyk.fb.sigrequest.SignedRequest;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * @author Lukas
 */
@Named
@SessionScoped
public class ContestBean implements Serializable {

    private static final Logger LOG = Logger.getLogger(ContestBean.class.getName());

    @Inject
    FacebookLogin login;

    @PersistenceContext(unitName = "fbContestDB")
    EntityManager em;
    
    @Resource
    private UserTransaction utx;

    private Boolean returning = false;
    private Contest active = null;

    @NotNull
    private RegisteredUser activeUser;
    
    @Pattern(regexp = "[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+" //meno
            + "(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*" //subdomena
            + "@(?:[a-zA-Z0-9]+\\.)+" //domena
            + "[a-zA-Z]{1,4}")  //root
    private String returningEmail;
    
    @AssertTrue
    private Boolean acceptedRules = false;
    /**
     * Creates a new instance of ContestBean
     */
    public ContestBean() {
    }

    @PostConstruct
    public void init() {
        activeUser = new RegisteredUser();
    }
    // Actions -----------------------------------------------------------------------------------
    /**
     *
     * @param list
     * @return returns active contest or null if no active found
     */
    private Contest selectActiveContest(List<Contest> list) {
        Collections.sort(list, Collections.reverseOrder());
        Contest selected = null;
        Date now = new Date();
        for (Contest x : list) {
            if (!x.getDisabled() && x.getContestEnd().after(now)) {
                selected = x;
            }
        }
        return selected;
    }

        public String register() {
        activeUser.setContest(getActiveContest());
        activeUser.setLocale(login.getSignedRequest().getLocale());
        activeUser.setCountry(login.getSignedRequest().getCountry());
        activeUser.setAgeMax(login.getSignedRequest().getAgeMax());
        activeUser.setAgeMin(login.getSignedRequest().getAgeMin());
        try {
            utx.begin();
            Collection<RegisteredUser> userList = (Collection<RegisteredUser>) em.createNamedQuery("RegisteredUser.findByEmailAndContest")
                    .setParameter("email", activeUser.getEmail())
                    .setParameter("contest", activeUser.getContest())
                    .getResultList();
            if (userList.isEmpty()) {
                em.persist(activeUser);
                createNewTicket(activeUser);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zadaný email už je zaregistrovaný", "Zadaný email už je zaregistrovaný"));
                utx.commit();
                setReturning(Boolean.TRUE);
                return null;
            }
            utx.commit();
            return "registered";
        } catch (PersistenceException e) {
            Logger.getLogger(ContestBean.class.getName()).log(Level.SEVERE, null, e);
            return null;
        } 
        catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(ContestBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * expects open transaction and persisted user
     */
    private void createNewTicket(RegisteredUser forUser) {
        Registration ticket = new Registration();
        ticket.setRegisteredUser(forUser);
        ticket.setTimeRegistered(new Date());
        ticket.setIpAddress(login.getSignedRequest().getIpAddress());
        ticket.setUserAgent(login.getSignedRequest().getUserAgent());
        em.persist(ticket);
    }

    public String registerNewTicket() {
        try {
            utx.begin();
            //if not exists returns exception
            RegisteredUser returningUser = (RegisteredUser) em.createNamedQuery("RegisteredUser.findByEmailAndContest")
                    .setParameter("email", returningEmail)
                    .setParameter("contest", getActiveContest())
                    .getSingleResult();
            //TODO: check if time after time interval for returning
            createNewTicket(returningUser);
            utx.commit();
            return "registered";
        } catch (NoResultException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zadaný email ešte nieje v súťaži", "Zadaný email ešte nieje v súťaži"));
            setReturning(Boolean.FALSE);
            return null;
        } catch (PersistenceException ex) {
            Logger.getLogger(ContestBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } 
        catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(ContestBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    // Getters -----------------------------------------------------------------------------------
    /**
     *
     * @return null if not active signed request or page does not have contest
     */
    public Contest getActiveContest() {
        if (active == null && login != null && login.getSignedRequest() != null) {
            List<Contest> list = (List<Contest>) em.createNamedQuery("Contest.findByRegisteredPage")
                    .setParameter("registeredPage", login.getSignedRequest().getPageId()).getResultList();
            active = selectActiveContest(list);
        }
        return active;
    }

    /**
     *
     * @param page with starting slash
     * @return
     */
    public String getPageUrl(String page) {
        Contest contest = getActiveContest();
        if (contest != null) {
            return "/WEB-INF/contest/layouts/" + contest.getContestLayout().getName() + "/" + page;
        } else {
            return null;
        }
    }

    /**
     *
     * @param resource with starting slash
     * @return
     */
    public String getResourceUrl(String resource) {
        Contest contest = getActiveContest();
        if (contest != null) {
            return "./contest/layouts/" + contest.getContestLayout().getName() + "/" + resource;
        } else {
            return null;
        }
    }

    /**
     *
     * @param image with starting slash
     * @return
     */
    public String getImageUrl(String image) {
        if (login.getSignedRequest() != null) {
            return "/images/" + login.getSignedRequest().getPageId() + "/" + image;
        } else {
            return null;
        }
    }

    public String getContestPage() {
        SignedRequest req = login.getSignedRequest();
        if (req != null) {
            String page;
            if (req.isPageLike()) {
                if (returning) {
                    page = "/returning.xhtml";
                } else {
                    page = "/register.xhtml";
                }
            } else {
                page = "/presslike.xhtml";
            }
            return getPageUrl(page);
        } else {
            LOG.severe("I got to contest page without signed request!!!");
            return null;
        }
    }

    public Boolean isReturning() {
        return returning;
    }

//    public String getShareLink() {
//        String link = "https://sutaz.flowyk.com:8181/facebookContest/og.xhtml?id=151";
//        StringBuilder sb = new StringBuilder("https://www.facebook.com/dialog/share?");
//        sb.append("app_id=").append(login.getAppId());
//        sb.append("&display=popup");
//        sb.append("&href=").append(link);
//        sb.append("&redirect_uri=").append(link);
//        return sb.toString();
//    }

//    FB.ui({
//method: 'share_open_graph',
//action_type: 'flowykcontests:attend',
//action_properties: JSON.stringify({
//object: 'https://sutaz.flowyk.com:8181/facebookContest/og.xhtml?id=301',
//}) });
    public String getShareScript() {
        StringBuilder sb = new StringBuilder("FB.ui({");
        sb.append("method: 'share_open_graph',");
        sb.append("action_type: 'flowykcontests:attend',");
        sb.append("action_properties: JSON.stringify({");
        sb.append("contest: 'https://sutaz.flowyk.com:8181/facebookContest/og.xhtml?id=").append(activeUser.getId()).append("'");
        sb.append("}) }); return false;");
        return sb.toString();
    }

    
    public RegisteredUser getUser() {
        return activeUser;
    }

    public Boolean getAcceptedRules() {
        return acceptedRules;
    }

    public String getReturningEmail() {
        return returningEmail;
    }
    
    // Setters -----------------------------------------------------------------------------------
    public void setReturning(Boolean value) {
        returning = value;
    }
    
    public void setAcceptedRules(Boolean value) {
        acceptedRules = value;
    }

    public void setReturningEmail(String email) {
        this.returningEmail = email;
    }
}
