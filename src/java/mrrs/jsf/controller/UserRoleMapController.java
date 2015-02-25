package mrrs.jsf.controller;

import mrrs.persistent.entity.UserRoleMap;
import mrrs.jsf.controller.util.JsfUtil;
import mrrs.jsf.controller.util.PaginationHelper;
import mrrs.logic.ejb.UserRoleMapFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("userRoleMapController")
@SessionScoped
public class UserRoleMapController implements Serializable {

    private UserRoleMap current;
    private DataModel items = null;
    @EJB
    private mrrs.logic.ejb.UserRoleMapFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public UserRoleMapController() {
    }

    public UserRoleMap getSelected() {
        if (current == null) {
            current = new UserRoleMap();
            current.setUserRoleMapPK(new mrrs.persistent.entity.UserRoleMapPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private UserRoleMapFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (UserRoleMap) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new UserRoleMap();
        current.setUserRoleMapPK(new mrrs.persistent.entity.UserRoleMapPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getUserRoleMapPK().setRoleID(current.getRole().getId());
            current.getUserRoleMapPK().setUserID(current.getUser().getId());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserRoleMapCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (UserRoleMap) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getUserRoleMapPK().setRoleID(current.getRole().getId());
            current.getUserRoleMapPK().setUserID(current.getUser().getId());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserRoleMapUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (UserRoleMap) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserRoleMapDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public UserRoleMap getUserRoleMap(mrrs.persistent.entity.UserRoleMapPK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = UserRoleMap.class)
    public static class UserRoleMapControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UserRoleMapController controller = (UserRoleMapController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "userRoleMapController");
            return controller.getUserRoleMap(getKey(value));
        }

        mrrs.persistent.entity.UserRoleMapPK getKey(String value) {
            mrrs.persistent.entity.UserRoleMapPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new mrrs.persistent.entity.UserRoleMapPK();
            key.setUserID(new Long(values[0]));
            key.setRoleID(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(mrrs.persistent.entity.UserRoleMapPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getUserID());
            sb.append(SEPARATOR);
            sb.append(value.getRoleID());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof UserRoleMap) {
                UserRoleMap o = (UserRoleMap) object;
                return getStringKey(o.getUserRoleMapPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + UserRoleMap.class.getName());
            }
        }

    }

}
