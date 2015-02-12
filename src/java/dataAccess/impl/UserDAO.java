package dataAccess.impl;

import dataAccess.AbstractDataAccess;
import dataAccess.Range;
import entities.User;
import java.util.List;
import java.util.Date;
import javax.swing.table.AbstractTableModel;

import entities.User;
import java.io.Serializable;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserDAO extends AbstractDataAccess<User> implements Serializable {

    @PersistenceContext(unitName = "JSF_DSLPU")
    protected EntityManager em;

    @Override
    public List<User> get(Range range) {
        return this.getAll();
    }

    public List<User> getAll() {
        return getBy("User.getAll", User.class);
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
