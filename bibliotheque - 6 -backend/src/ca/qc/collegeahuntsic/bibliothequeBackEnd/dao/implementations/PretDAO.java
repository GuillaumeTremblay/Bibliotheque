// Fichier PretDAO.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-26

package ca.qc.collegeahuntsic.bibliothequeBackEnd.dao.implementations;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dao.interfaces.IPretDAO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.util.BibliothequeDate;
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
        final Date dateDebut = BibliothequeDate.getStartDate(datePret);
        Date dateFin = BibliothequeDate.getEndDate(datePret);
        final Criteria cr = session.createCriteria(getDtoClass());
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
        final Date dateDebut = BibliothequeDate.getStartDate(dateRetour);
        Date dateFin = BibliothequeDate.getEndDate(dateRetour);
        final Criteria cr = session.createCriteria(getDtoClass());
        cr.add(Restrictions.between(sortByPropertyName,
            dateDebut,
            dateFin));
        List<PretDTO> prets = cr.list();
        return prets;
    }
}
