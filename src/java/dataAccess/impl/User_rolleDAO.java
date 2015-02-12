package dataAccess.impl;

import dataAccess.AbstractDataAccess;
import dataAccess.Range;
import entities.User_rolle;
import java.util.List;
import java.util.Date;
import javax.swing.table.AbstractTableModel;

import entities.User_rolle;
import java.io.Serializable;
import javax.ejb.Startup;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class User_rolleDAO extends AbstractDataAccess<User_rolle> implements Serializable {

    @PersistenceContext(unitName = "JSF_DSLPU")
    protected EntityManager em;

    @Override
    public List<User_rolle> get(Range range) {
        return this.getAll();
    }

    public List<User_rolle> getAll() {
        return getBy("User_rolle.getAll", User_rolle.class);
    }

    @Override
    public void remove(User_rolle entity) {

        remove(entity);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
