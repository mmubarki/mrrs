package mrrs.jsf.controller;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.Principal;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mrrs.jsf.controller.util.JsfUtil;
import mrrs.jsf.controller.util.PaginationHelper;
import mrrs.logic.ejb.UserFacade;
import mrrs.persistent.entity.Patient;
import mrrs.persistent.entity.Physician;
import mrrs.persistent.entity.Role;
import mrrs.persistent.entity.User;
import org.jboss.weld.util.collections.ArraySet;

@Named("userController")
@SessionScoped
public class UserController implements Serializable {

    private User current;
    private DataModel items = null;
    @EJB
    private mrrs.logic.ejb.UserFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String viewPage;
    //Role [] user_type_list;
    public UserController() {
    }

    public String getViewPage() {
        return viewPage;
    }

    public void setViewPage(String viewPage) {
        this.viewPage = viewPage;
    }

    public User getSelected() {
        if (current == null) {
            current = new User();
            selectedItemIndex = -1;
        }
        return current;
    }

    private UserFacade getFacade() {
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
        current = (User) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new User();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (User) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (User) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserDeleted"));
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

    public User getUser(java.lang.Integer id) {
        return ejbFacade.find(id);
    }
    
    /*public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(getSelected().getPassword().getBytes("UTF-8"));
            System.out.println("pwd:"+getSelected().getPassword()+ "  hash:"+hash);
            MessageDigest messageDigest = 
                    java.security.MessageDigest.getInstance("SHA-256");
            final byte bin[] = messageDigest.digest(getSelected().getPassword().getBytes());
            System.out.println("Base64:"+Base64.getEncoder().encode(bin));
            System.out.println("hashing:"+Hashing.sha256().hashString(getSelected().getPassword(), Charsets.UTF_8).toString());
            Principal principal = request.getUserPrincipal();
            System.out.println("@@√ principal:"+principal);
            principal = request.getUserPrincipal();
            System.out.println("Authenticated user: " + principal);

            request.login(getSelected().getId()+"", getSelected().getPassword());
            principal = request.getUserPrincipal();
            System.out.println("Authenticated user: " + principal.getName());

            current = ejbFacade.findLoginMatch(getSelected().getReference_id(), getSelected().getPassword(),getSelected().getUser_Type());
        } catch (Exception e) {
            System.out.println(e.toString());
            //e.printStackTrace();
        }

        if (current == null) {
            context.addMessage(null, new FacesMessage("Unknown login"));
            return null;
        } else {
            return "login";
        }
    }
    */
    /*@Inject
    private List<Role> roleLis;
       
    @PostConstruct
    public void postConstruct(){
            System.out.println("@@inside post contruct");
        
            init();
    }
       
    public void init(){
            setRoleList(new RoleController().getAllRole());
            System.out.println("@@init:"+roleList);
        
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }*/
    
    public String isLoginMatch() {
        System.out.println("@@inside controller isLoginMatch:"+getSelected().getId()+" "+getSelected().getPassword()+" "+getSelected().getUser_Type());
        System.out.println("@@inside controller isLoginMatchcurrent:"+current.getId()+" "+current.getPassword()+" "+current.getUser_Type());
        current= ejbFacade.findLoginMatch(current);
        if (current != null) {
            // get Http Session and store username
            HttpSession session = JsfUtil.getSession();
            session.setAttribute("user", current);
            if(getSelected().getUser_Type() == JsfUtil.PATIENT_ROLE )
            {
               Patient patient = ejbFacade.findPatient(current);
               System.out.println("@@√ patient:"+patient);
               System.out.println("@@√ patient.getUser():"+patient.getUser());
               
               session.setAttribute("patient", patient);
               PatientController patientController = (PatientController)
                    JsfUtil.getManagedBean("patientController");
               patientController.setSelected(patient);
                setViewPage("/mrrs/faces/mrrs/patient/View.xhtml");          
            }
            else if(current.getUser_Type() == JsfUtil.PHYSICIAN_ROLE )
            {
               Physician physician = ejbFacade.findPhysician(current);
               System.out.println("@@√ physician:"+physician);
               System.out.println("@@√ physician.getUser():"+physician.getUser());
               
               session.setAttribute("physician", physician);
               PhysicianController physicianController = (PhysicianController)
                    JsfUtil.getManagedBean("physicianController");
               physicianController.setSelected(physician);
                setViewPage("/mrrs/faces/mrrs/physician/View.xhtml");
            }
            else
            {
                //admin role
                current.setName("Administrator");
                setViewPage("/mrrs/faces/welcome.xhtml");
            }
            
            /*
            boolean isUserInRule = FacesContext.getCurrentInstance().getExternalContext().isUserInRole("Administrator");
            System.out.println("@@√ isUserInRule:"+isUserInRule);
            Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
            System.out.println("@@√ principal:"+principal);
            System.out.println("@@√ remote user:"+FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());*/
             //security.        
            return "login";
        } 
        else {
 
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Invalid Login!",
                    "Please Try Again!"));
 
            // invalidate session, and redirect to other pages
 
            //message = "Invalid Login. Please Try Again!";
            return "login";
        }
    }
    
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }
    /*
    public String logout() {
      HttpSession session = JsfUtil.getSession();
      session.invalidate();
      return "login";
   }*/
    
    @FacesConverter(forClass = User.class)
    public static class UserControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UserController controller = (UserController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "userController");
            return controller.getUser(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof User) {
                User o = (User) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + User.class.getName());
            }
        } 

    }

}
