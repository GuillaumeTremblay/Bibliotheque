// Fichier PretDAO.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-26

package ca.qc.collegeahuntsic.bibliotheque.dao.implementations;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IPretDAO;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque.util.BibliothequeDate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

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
    PretDAO(Class<PretDTO> pretDTOClass) throws InvalidDTOClassException {
        super(pretDTOClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PretDTO> findPretByDatePret(Session session,
        Timestamp datePret,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidCriterionValueException,
        InvalidSortByPropertyException,
        DAOException {
        Date dateDebut = BibliothequeDate.getStartDate(datePret);
        Date dateFin = BibliothequeDate.getEndDate(datePret);
        Criteria cr = session.createCriteria(getDtoClass());
        cr.add(Restrictions.between(sortByPropertyName,
            dateDebut,
            dateFin));
        List<PretDTO> prets = cr.list();
        return prets;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PretDTO> findPretByDateRetour(Session session,
        Timestamp dateRetour,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidCriterionValueException,
        InvalidSortByPropertyException,
        DAOException {
        Date dateDebut = BibliothequeDate.getStartDate(dateRetour);
        Date dateFin = BibliothequeDate.getEndDate(dateRetour);
        Criteria cr = session.createCriteria(getDtoClass());
        cr.add(Restrictions.between(sortByPropertyName,
            dateDebut,
            dateFin));
        List<PretDTO> prets = cr.list();
        return prets;
    }
}
