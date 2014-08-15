package com.flowyk.fb.model.admin;

import com.flowyk.fb.entity.RegisteredPage;
import com.flowyk.fb.model.admin.util.JsfUtil;
import com.flowyk.fb.model.admin.util.JsfUtil.PersistAction;
import com.flowyk.fb.entity.facade.RegisteredPageFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("registeredPageController")
@SessionScoped
public class RegisteredPageController implements Serializable {

    @EJB
    private com.flowyk.fb.entity.facade.RegisteredPageFacade ejbFacade;
    private List<RegisteredPage> items = null;
    private RegisteredPage selected;

    public RegisteredPageController() {
    }

    public RegisteredPage getSelected() {
        return selected;
    }

    public void setSelected(RegisteredPage selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private RegisteredPageFacade getFacade() {
        return ejbFacade;
    }

    public RegisteredPage prepareCreate() {
        selected = new RegisteredPage();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RegisteredPageCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RegisteredPageUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RegisteredPageDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RegisteredPage> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public RegisteredPage getRegisteredPage(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<RegisteredPage> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RegisteredPage> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RegisteredPage.class)
    public static class RegisteredPageControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RegisteredPageController controller = (RegisteredPageController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "registeredPageController");
            return controller.getRegisteredPage(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof RegisteredPage) {
                RegisteredPage o = (RegisteredPage) object;
                return getStringKey(o.getPageId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RegisteredPage.class.getName()});
                return null;
            }
        }

    }

}
