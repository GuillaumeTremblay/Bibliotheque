// Fichier PretDAO.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-26

package ca.qc.collegeahuntsic.bibliotheque.dao.implementations;

import java.sql.Timestamp;
import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IPretDAO;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import org.hibernate.Session;

/**
 * DAO pour effectuer des CRUDs avec la table <code>pret</code>.
 * 
 * @author Gilles Benichou
 */
public class PretDAO extends DAO implements IPretDAO {

    /**
     * Crée le DAO de la table <code>pret</code>.
     * 
     * @param pretDTOClass The classe de prêt DTO to use
     * @throws InvalidDTOClassException Si la classe de DTO est <code>null</code>
     */
    public PretDAO(Class<PretDTO> pretDTOClass) throws InvalidDTOClassException { // TODO: Change to package when switching to Spring
        super(pretDTOClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PretDTO> findByMembre(Session session,
        String idMembre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidCriterionValueException,
        InvalidSortByPropertyException,
        DAOException {

        return prets;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PretDTO> findByLivre(Session session,
        String idLivre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidCriterionValueException,
        InvalidSortByPropertyException,
        DAOException {
        return prets;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PretDTO> findByDatePret(Session session,
        Timestamp datePret,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidCriterionValueException,
        InvalidSortByPropertyException,
        DAOException {
        return prets;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PretDTO> findByDateRetour(Session session,
        Timestamp dateRetour,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidCriterionValueException,
        InvalidSortByPropertyException,
        DAOException {
        return prets;
    }
}
