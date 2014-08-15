package com.flowyk.fb.model.admin;

import com.flowyk.fb.entity.ContestLayout;
import com.flowyk.fb.model.admin.util.JsfUtil;
import com.flowyk.fb.model.admin.util.JsfUtil.PersistAction;
import com.flowyk.fb.entity.facade.ContestLayoutFacade;

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

@Named("contestLayoutController")
@SessionScoped
public class ContestLayoutController implements Serializable {

    @EJB
    private com.flowyk.fb.entity.facade.ContestLayoutFacade ejbFacade;
    private List<ContestLayout> items = null;
    private ContestLayout selected;

    public ContestLayoutController() {
    }

    public ContestLayout getSelected() {
        return selected;
    }

    public void setSelected(ContestLayout selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ContestLayoutFacade getFacade() {
        return ejbFacade;
    }

    public ContestLayout prepareCreate() {
        selected = new ContestLayout();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ContestLayoutCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ContestLayoutUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ContestLayoutDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ContestLayout> getItems() {
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

    public ContestLayout getContestLayout(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<ContestLayout> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ContestLayout> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ContestLayout.class)
    public static class ContestLayoutControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ContestLayoutController controller = (ContestLayoutController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "contestLayoutController");
            return controller.getContestLayout(getKey(value));
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
            if (object instanceof ContestLayout) {
                ContestLayout o = (ContestLayout) object;
                return getStringKey(o.getName());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ContestLayout.class.getName()});
                return null;
            }
        }

    }

}
