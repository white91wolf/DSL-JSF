/*
 * Copyright 2014 Florian Vogelpohl <floriantobias@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dataAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Implementation of some default operations with an EntityManager instance.
 *
 * @see EntityManager
 * @author Florian Vogelpohl <floriantobias@gmail.com>
 */
public abstract class AbstractDataAccess<T> implements DataAccess<T> {
    /**
     * Gets an EntityManager instance. The Jpa ensures that every thread gets
     * its own EntityManager instance
     *
     * @return EntityManager instance
     */
    protected abstract EntityManager getEntityManager();

    public void clear() {
        getEntityManager().clear();
    }

    /**
     * Stores an object.
     *
     * @param o Entity instance to store
     * @return True if successfully stored, otherwise false
     */
    @Override
    public void persist(T o) {
        getEntityManager()
                .persist(o);

    }

    /**
     * Merges/updates an object
     *
     * @param t Entity instance to update/merge
     * @return The updated Entity-instance
     */
    @Override
    public T update(T t) {
        return getEntityManager()
                .merge(t);
    }

    /**
     * Removes the given entity instance
     *
     * @param o Entity instance to remove
     */
    @Override
    public void remove(final T o) {
        T mergedEntity = getEntityManager()
                .merge(o);

        getEntityManager()
                .remove(mergedEntity);
    }

    /**
     * Helper method to execute a named query.
     *
     * @param queryName Name of the named query
     * @param returnTypeClass Result class datatype
     * @return List of entity instances results
     */
    protected List<T> getBy(final String queryName, final Class returnTypeClass) {
        return getBy(queryName, null, returnTypeClass);
    }

    /**
     * Helper Method to execute a named query.
     *
     * @param queryName Name of the named query
     * @param returnTypeClass Result class datatype
     * @param range Range instance
     * @return List of entity instances results
     */
    protected List<T> getBy(final String queryName, final Class returnTypeClass, final Range range) {
        return getBy(queryName, null, returnTypeClass, range);
    }

    /**
     * Helper Method to execute a named query.
     *
     * @param queryName Name of the named query
     * @param parameters Parameter Map with &lt;ParameterNameInNamedQuery,
     * Parameter>
     * @param returnTypeClass Result class datatype
     * @return List of entity instances results
     */
    protected List<T> getBy(final String queryName, final Map<String, Object> parameters, final Class returnTypeClass) {
        return getBy(queryName, parameters, returnTypeClass, null);
    }

    /**
     * Helper Method to execute a named query.
     *
     * @param queryName Name of the named query
     * @param parameters Parameter Map with &lt;ParameterNameInNamedQuery,
     * Parameter>
     * @param returnTypeClass Result class datatype
     * @param range Range instance
     * @return List of entity instances results
     */
    protected List<T> getBy(final String queryName,
            final Map<String, Object> parameters,
            final Class returnTypeClass,
            final Range range) {

        List<T> result = null;

        TypedQuery<T> query = getEntityManager()
                .createNamedQuery(queryName, returnTypeClass);

        assignParameters(query, parameters);

        if (range != null) {
            if (range.getOffset() != null) {
                query.setFirstResult(range.getOffset());
            }

            if (range.getCount() != null) {
                query.setMaxResults(range.getCount());
            }
        }

        try {
            result = query.getResultList();
        } catch (NoResultException ex) {
            result = new ArrayList<>();
        }

        return result;
    }

    /**
     * Helper Method to do a named query operation, like update or delete.
     *
     * @param queryName Name of the named query
     * @param parameters Parameter Map with &lt;ParameterNameInNamedQuery,
     * Parameter>
     * @return Number of effected rows
     */
    protected Integer doNamedQuery(final String queryName, final Map<String, Object> parameters) {
        Query query = getEntityManager()
                .createNamedQuery(queryName);

        assignParameters(query, parameters);

        Integer affectedRows = query.executeUpdate();
        
        return affectedRows;
    }

    /**
     * Helper method to assign the parameter map to a query.
     *
     * @param queryName Name of the named query
     * @param parameters Parameter Map with &lt;ParameterNameInNamedQuery,
     * Parameter>
     */
    private void assignParameters(Query query, final Map<String, Object> parameters) {
        if (parameters != null) {
            for (Map.Entry<String, Object> e : parameters.entrySet()) {
                query.setParameter(e.getKey(), e.getValue());
            }
        }
    }
}
