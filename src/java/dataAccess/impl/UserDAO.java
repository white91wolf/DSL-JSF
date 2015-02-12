package dataAccess.impl;

import dataAccess.AbstractDataAccess;
import dataAccess.Range;
import entities.User;
import java.util.List;
import java.util.Date;
import javax.swing.table.AbstractTableModel;

import entities.User;
import java.io.Serializable;
import java.util.ArrayList;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserDAO extends AbstractDataAccess<User> implements Serializable {

    private List<User> cache = new ArrayList<>();
    private User newEntity = new User();

    public User getNewEntity() {
        return newEntity;
    }

    public void setNewEntity(User newEntity) {
        this.newEntity = newEntity;
    }
    
    public void persistNewEntity(){
        persist(newEntity);
        newEntity = new User();
    }
    
    @PersistenceContext(unitName = "JSF_DSLPU")
    protected EntityManager em;

    @Override
    public List<User> get(Range range) {
        return this.getAll();
    }

    public List<User> getAll() {
        if(cache.isEmpty()) {
            cache = getBy("User.getAll", User.class);
        }
        return cache;
    }

    @Override
    public void remove(User entity) {
        super.remove(entity);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
