// Fichier DAO.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliotheque.dao.implementations;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IDAO;
import ca.qc.collegeahuntsic.bibliotheque.dto.DTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliotheque.util.BibliothequeDate;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * Classe de base pour tous les DAOs.<br />
 * Tous les DAOs devrait en hériter.
 *
 * @author Gilles Benichou
 */
public class DAO implements IDAO {
    private Class<?> dtoClass;

    /**
     * Crée un DAO.
     * 
     * @param dtoClass La classe de DTO à utiliser
     * @throws InvalidDTOClassException Si la classe de DTO est <code>null</code>
     */
    protected DAO(Class<?> dtoClass) throws InvalidDTOClassException {
        super();
        if(dtoClass == null) {
            throw new InvalidDTOClassException("La classe de DTO ne peut être null");
        }
        setDtoClass(dtoClass);
    }

    // Region Getters and Setters
    /**
     * Getter de la variable d'instance <code>this.dtoClass</code>.
     *
     * @return La variable d'instance <code>this.dtoClass</code>
     */
    protected Class<?> getDtoClass() {
        return this.dtoClass;
    }

    /**
     * Setter de la variable d'instance <code>this.dtoClass</code>.
     *
     * @param dtoClass La valeur à utiliser pour la variable d'instance <code>this.dtoClass</code>
     */
    private void setDtoClass(Class<?> dtoClass) {
        this.dtoClass = dtoClass;
    }

    // EndRegion Getters and Setters

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Session session,
        DTO dto) throws InvalidHibernateSessionException,
        InvalidDTOException,
        DAOException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session Hibernate ne peut être null");
        }
        if(dto == null) {
            throw new InvalidDTOException("Le DTO ne peut être null");
        }
        try {
            session.save(dto);
        } catch(HibernateException hibernateException) {
            throw new DAOException(hibernateException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DTO get(Session session,
        Serializable primaryKey) throws InvalidHibernateSessionException,
        InvalidPrimaryKeyException,
        DAOException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session Hibernate ne peut être null");
        }
        if(primaryKey == null) {
            throw new InvalidPrimaryKeyException("La clef primaire ne peut être null");
        }
        try {
            DTO dto = (DTO) session.get(getDtoClass(),
                primaryKey);
            return dto;
        } catch(HibernateException hibernateException) {
            throw new DAOException(hibernateException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Session session,
        DTO dto) throws InvalidHibernateSessionException,
        InvalidDTOException,
        DAOException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session Hibernate ne peut être null");
        }
        if(dto == null) {
            throw new InvalidDTOException("Le DTO ne peut être null");
        }
        try {
            session.update(dto);
        } catch(HibernateException hibernateException) {
            throw new DAOException(hibernateException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Session session,
        DTO dto) throws InvalidHibernateSessionException,
        InvalidDTOException,
        DAOException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session Hibernate ne peut être null");
        }
        if(dto == null) {
            throw new InvalidDTOException("Le DTO ne peut être null");
        }
        try {
            session.saveOrUpdate(dto);
        } catch(HibernateException hibernateException) {
            throw new DAOException(hibernateException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Session session,
        DTO dto) throws InvalidHibernateSessionException,
        InvalidDTOException,
        DAOException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session Hibernate ne peut être null");
        }
        if(dto == null) {
            throw new InvalidDTOException("Le DTO ne peut être null");
        }
        try {
            session.delete(dto);
        } catch(HibernateException hibernateException) {
            throw new DAOException(hibernateException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<?> getAll(Session session,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        DAOException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session Hibernate ne peut être null");
        }
        if(sortByPropertyName == null) {
            throw new InvalidSortByPropertyException("La propriété utilisée pour classer ne peut être null");
        }
        try {
            List<?> results = Collections.EMPTY_LIST;
            Criteria criteria = session.createCriteria(getDtoClass());
            criteria.addOrder(Order.asc(sortByPropertyName));
            results = criteria.list();
            return results;
        } catch(HibernateException hibernateException) {
            throw new DAOException(hibernateException);
        }
    }

    /**
     * Trouve les DTOs à partir d'une propriété <code>propertyName</code> étant égale à une valeur <code>value</code>. La liste est classée par
     * ordre croissant sur <code>sortByPropertyName</code>. Si aucun DTO n'est trouvé, une {@link List} vide est retournée.
     * 
     * @param session La session Hibernate à utiliser
     * @param propertyName Le nom de la propriété à utiliser
     * @param value La valeur à trouver
     * @param sortByPropertyName The nom de la propriété à utiliser pour classer
     * @return La liste des DTOs correspondants ; une liste vide sinon
     * @throws InvalidHibernateSessionException Si la session Hibernate est <code>null</code>
     * @throws InvalidCriterionException Si la propriété à utiliser est <code>null</code>
     * @throws InvalidCriterionValueException Si la valeur à trouver est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    protected List<?> find(Session session,
        String propertyName,
        Object value,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidCriterionValueException,
        InvalidSortByPropertyException,
        DAOException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session Hibernate ne peut être null");
        }
        if(propertyName == null) {
            throw new InvalidCriterionException("La propriété à utiliser ne peut être null");
        }
        if(value == null) {
            throw new InvalidCriterionValueException("La valeur à trouver ne peut être null");
        }
        if(sortByPropertyName == null) {
            throw new InvalidSortByPropertyException("La propriété utilisée pour classer ne peut être null");
        }
        try {
            List<?> results = Collections.EMPTY_LIST;
            if(value instanceof Date) {
                results = findByDate(session,
                    propertyName,
                    (Date) value,
                    sortByPropertyName);
            } else {
                Criteria criteria = session.createCriteria(getDtoClass());
                criteria.add(Restrictions.eq(propertyName,
                    value));
                criteria.addOrder(Order.asc(sortByPropertyName));
                results = criteria.list();
            }
            return results;
        } catch(HibernateException hibernateException) {
            throw new DAOException(hibernateException);
        }
    }

    /**
     * Trouve les DTOs à partir d'une propriété <code>propertyName</code> étant comprise entre la veille et le lendemain de la date
     * <code>date</code>. La liste est classée par ordre croissant sur <code>sortByPropertyName</code>. Si aucun DTO n'est trouvé, une
     * {@link List} vide est retournée.
     * 
     * @param session La session Hibernate à utiliser
     * @param propertyName Le nom de la propriété à utiliser
     * @param date La date à trouver
     * @param sortByPropertyName The nom de la propriété à utiliser pour classer
     * @return La liste des DTOs correspondants ; une liste vide sinon
     * @throws InvalidHibernateSessionException Si la session Hibernate est <code>null</code>
     * @throws InvalidCriterionException Si la propriété à utiliser est <code>null</code>
     * @throws InvalidCriterionValueException Si la date à trouver est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    private List<?> findByDate(Session session,
        String propertyName,
        Date date,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidCriterionValueException,
        InvalidSortByPropertyException,
        DAOException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session Hibernate ne peut être null");
        }
        if(propertyName == null) {
            throw new InvalidCriterionException("La propriété à utiliser ne peut être null");
        }
        if(date == null) {
            throw new InvalidCriterionValueException("La date à trouver ne peut être null");
        }
        if(sortByPropertyName == null) {
            throw new InvalidSortByPropertyException("La propriété utilisée pour classer ne peut être null");
        }
        try {
            List<?> results = Collections.EMPTY_LIST;
            Criteria criteria = session.createCriteria(getDtoClass());
            criteria.add(Restrictions.between(propertyName,
                BibliothequeDate.getStartDate(date),
                BibliothequeDate.getEndDate(date)));
            criteria.addOrder(Order.asc(sortByPropertyName));
            results = criteria.list();
            return results;
        } catch(HibernateException hibernateException) {
            throw new DAOException(hibernateException);
        }
    }
}
