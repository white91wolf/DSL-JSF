package Controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dataAccess.impl.RoleDAO;
import dataAccess.impl.UserDAO;
import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.jboss.weld.context.ejb.Ejb;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Stefan
 */
@Named("databaseController")
@ApplicationScoped
public class DatabaseController implements Serializable{
    @Ejb
    private RoleDAO roleDao;
    
    @Inject
    private UserDAO userDao;
    
    @Ejb
    private RoleDAO user_rolleDao;

    public RoleDAO getRoleDao() {
        return roleDao;
    }

    public void setRoleDao(RoleDAO roleDao) {
        this.roleDao = roleDao;
    }

    public UserDAO getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }

    public RoleDAO getUser_rolleDao() {
        return user_rolleDao;
    }

    public void setUser_rolleDao(RoleDAO user_rolleDao) {
        this.user_rolleDao = user_rolleDao;
    }
    
    public DatabaseController() {
        
    }
    
    public void onRowEdit(RowEditEvent event){
        FacesMessage msg = new FacesMessage("Eintrag geändert", (event.getObject()).toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Bearbeitung Abgebrochen");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
}
