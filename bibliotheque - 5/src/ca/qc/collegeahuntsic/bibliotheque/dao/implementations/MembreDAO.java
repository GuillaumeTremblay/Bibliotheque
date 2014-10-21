// Fichier MembreDAO.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliotheque.dao.implementations;

import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IMembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import org.hibernate.Session;

/**
 * DAO pour effectuer des CRUDs avec la table <code>membre</code>.
 *
 * @author Gilles Benichou
 */
public class MembreDAO extends DAO implements IMembreDAO {

    /**
     * Crée le DAO de la table <code>membre</code>.
     *
     * @param membreDTOClass The classe de membre DTO to use
     * @throws InvalidDTOClassException Si la classe de DTO est <code>null</code>
     */
    MembreDAO(Class<MembreDTO> membreDTOClass) throws InvalidDTOClassException {
        super(membreDTOClass);
    }

    /**
     * {@inheritDoc}
     * @throws InvalidCriterionValueException
     */
    @Override
    public List<MembreDTO> findByNom(Session session,
        String nom,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException,
        InvalidCriterionValueException {
        return (List<MembreDTO>) super.find(session,
            MembreDTO.NOM_COLUMN_NAME,
            nom,
            sortByPropertyName);
    }
}
