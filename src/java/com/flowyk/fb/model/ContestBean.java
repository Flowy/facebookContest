/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowyk.fb.model;

import com.flowyk.fb.entity.Contest;
import com.flowyk.fb.entity.RegisteredPage;
import com.flowyk.fb.entity.RegisteredUser;
import com.flowyk.fb.entity.Registration;
import com.flowyk.fb.exception.FBPageNotActiveException;
import com.flowyk.fb.exception.NoActiveContestException;
import com.flowyk.fb.exception.PageIdNotFoundException;
import com.flowyk.fb.model.signedrequest.SignedRequest;
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
    private SignedRequest signedRequest;

    @PersistenceContext(unitName = "fbContestDB")
    private EntityManager em;

    @Resource
    private UserTransaction utx;

    private Boolean returning = false;

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
    public String goReturning() {
        this.returning = Boolean.TRUE;
        return "returning";
    }
    
    public String register() {
        activeUser.setContest(getActiveContest());
        activeUser.setLocale(signedRequest.getUser().getLocale());
        activeUser.setCountry(signedRequest.getUser().getCountry());
        activeUser.setAgeMax(signedRequest.getUser().getAgeMax());
        activeUser.setAgeMin(signedRequest.getUser().getAgeMin());
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
            this.returning = Boolean.TRUE;
            return "thanks";
        } catch (PersistenceException e) {
            Logger.getLogger(ContestBean.class.getName()).log(Level.SEVERE, null, e);
            return null;
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
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
        ticket.setIpAddress(signedRequest.getIpAddress());
        ticket.setUserAgent(signedRequest.getUserAgent());
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
            return "thanks";
        } catch (NoResultException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zadaný email ešte nieje v súťaži", "Zadaný email ešte nieje v súťaži"));
            setReturning(Boolean.FALSE);
            return null;
        } catch (PersistenceException ex) {
            Logger.getLogger(ContestBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(ContestBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    // Getters -----------------------------------------------------------------------------------
    private Contest selectActiveContest(List<Contest> list) {
        Collections.sort(list, Collections.reverseOrder());
        Contest selected = null;
        Date now = new Date();
        for (Contest x : list) {
            if (!x.getDisabled() && x.getContestEnd().after(now)) {
                selected = x;
            }
        }
        if (selected != null) {
            return selected;
        } else {
            throw new NoActiveContestException();
        }
    }

    /**
     *
     * @return active contest for actual page from signed request
     * @throws PageIdNotFoundException if page not found in signed request
     */
    public Contest getActiveContest() {
        if (signedRequest.getPage().getId() != null) {
            RegisteredPage page = em.find(RegisteredPage.class, signedRequest.getPage().getId());
            if (page != null) {
                List<Contest> list = em.createNamedQuery("Contest.findByRegisteredPage").setParameter("registeredPage", page).getResultList();
//                List<Contest> list = new ArrayList(page.getContestCollection());
                return selectActiveContest(list);
            } else {
                throw new FBPageNotActiveException("Page id: " + signedRequest.getPage().getId());
            }
        } else {
            throw new PageIdNotFoundException();
        }
    }

    /**
     *
     * @param page
     * @return
     */
    public String getPageUrl(String page) {
        Contest contest = getActiveContest();
        return "/WEB-INF/contest/layouts/" + contest.getContestLayout().getName() + "/" + page;
    }

    /**
     *
     * @param resource
     * @return
     */
    public String getResourceUrl(String resource) {
        Contest contest = getActiveContest();
        return "./contest/layouts/" + contest.getContestLayout().getName() + "/" + resource;
    }

    /**
     *
     * @param image with starting slash
     * @return
     */
    public String getImageUrl(String image) {
        if (signedRequest.getPage().getId() != null) {
            return "/images/" + signedRequest.getPage().getId() + "/" + image;
        } else {
            throw new PageIdNotFoundException();
        }
    }

    public String getContestSubpage() {
        String page;
        if (signedRequest.getPage().isLiked()) {
            if (returning) {
                page = "/returning.xhtml";
            } else {
                page = "/register.xhtml";
            }
        } else {
            page = "/presslike.xhtml";
        }
        return getPageUrl(page);
    }

    public boolean isPageAllowed() {
        String pageId = signedRequest.getPage().getId();
        if (pageId != null) {
            RegisteredPage page = em.find(RegisteredPage.class, pageId);
            if (page != null && page.getActive()) {
                return true;
            }
        }
        return false;
    }

    public Boolean isReturning() {
        return returning;
    }
    
    public String getShareScript() {
        StringBuilder sb = new StringBuilder("FB.ui({");
        sb.append("method: 'share_open_graph',");
        sb.append("action_type: 'flowykcontests:attend',");
        sb.append("action_properties: {");
        sb.append("contest: 'https://sutaz.flowyk.com:8181/facebookContest/og.xhtml?id=").append(activeUser.getId()).append("'");
        sb.append("} }); return false;");
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
