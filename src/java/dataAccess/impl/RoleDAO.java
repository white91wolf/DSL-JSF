package dataAccess.impl;

import dataAccess.AbstractDataAccess;
import dataAccess.Range;
import entities.Role;
import java.util.List;
import java.util.Date;
import javax.swing.table.AbstractTableModel;

import entities.Role;
import java.io.Serializable;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class RoleDAO extends AbstractDataAccess<Role> implements Serializable {

    @PersistenceContext(unitName = "JSF_DSLPU")
    protected EntityManager em;

    @Override
    public List<Role> get(Range range) {
        return this.getAll();
    }

    public List<Role> getAll() {
        return getBy("Role.getAll", Role.class);
    }

    @Override
    public void remove(Role entity) {

        remove(entity);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
