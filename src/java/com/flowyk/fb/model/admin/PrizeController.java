package com.flowyk.fb.model.admin;

import com.flowyk.fb.entity.Prize;
import com.flowyk.fb.model.admin.util.JsfUtil;
import com.flowyk.fb.model.admin.util.JsfUtil.PersistAction;
import com.flowyk.fb.entity.facade.PrizeFacade;

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

@Named("prizeController")
@SessionScoped
public class PrizeController implements Serializable {

    @EJB
    private com.flowyk.fb.entity.facade.PrizeFacade ejbFacade;
    private List<Prize> items = null;
    private Prize selected;

    public PrizeController() {
    }

    public Prize getSelected() {
        return selected;
    }

    public void setSelected(Prize selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getPrizePK().setContestId(selected.getContest().getId());
    }

    protected void initializeEmbeddableKey() {
        selected.setPrizePK(new com.flowyk.fb.entity.PrizePK());
    }

    private PrizeFacade getFacade() {
        return ejbFacade;
    }

    public Prize prepareCreate() {
        selected = new Prize();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PrizeCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PrizeUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PrizeDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Prize> getItems() {
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

    public Prize getPrize(com.flowyk.fb.entity.PrizePK id) {
        return getFacade().find(id);
    }

    public List<Prize> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Prize> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Prize.class)
    public static class PrizeControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PrizeController controller = (PrizeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "prizeController");
            return controller.getPrize(getKey(value));
        }

        com.flowyk.fb.entity.PrizePK getKey(String value) {
            com.flowyk.fb.entity.PrizePK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.flowyk.fb.entity.PrizePK();
            key.setPosition(Integer.parseInt(values[0]));
            key.setContestId(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(com.flowyk.fb.entity.PrizePK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getPosition());
            sb.append(SEPARATOR);
            sb.append(value.getContestId());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Prize) {
                Prize o = (Prize) object;
                return getStringKey(o.getPrizePK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Prize.class.getName()});
                return null;
            }
        }

    }

}
