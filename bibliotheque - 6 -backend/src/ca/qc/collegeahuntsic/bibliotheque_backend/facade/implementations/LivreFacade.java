// Fichier LivreFacade.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-01

package ca.qc.collegeahuntsic.bibliotheque_backend.facade.implementations;

import ca.qc.collegeahuntsic.bibliotheque_backend.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dto.MissingDTOException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.facade.FacadeException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.facade.InvalidServiceException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.service.ServiceException;
import ca.qc.collegeahuntsic.bibliotheque_backend.facade.interfaces.ILivreFacade;
import ca.qc.collegeahuntsic.bibliotheque_backend.service.interfaces.ILivreService;
import org.hibernate.Session;

/**
 * Facade pour interagir avec le service de livres.
 *
 * @author Gilles Benichou
 */
public class LivreFacade extends Facade implements ILivreFacade {
    private ILivreService livreService;

    /**
     * Crée la façade de la table <code>livre</code>.
     * 
     * @param livreService Le service de la table <code>livre</code>
     * @throws InvalidServiceException Si le service de livres est <code>null</code>
     */
    LivreFacade(ILivreService livreService) throws InvalidServiceException {
        super();
        if(livreService == null) {
            throw new InvalidServiceException("Le service de livres ne peut être null");
        }
        setLivreService(livreService);
    }

    // Region Getters and Setters
    /**
     * Getter de la variable d'instance <code>this.livreService</code>.
     *
     * @return La variable d'instance <code>this.livreService</code>
     */
    private ILivreService getLivreService() {
        return this.livreService;
    }

    /**
     * Setter de la variable d'instance <code>this.livreService</code>.
     *
     * @param livreService La valeur à utiliser pour la variable d'instance <code>this.livreService</code>
     */
    private void setLivreService(ILivreService livreService) {
        this.livreService = livreService;
    }

    // EndRegion Getters and Setters

    /**
     * {@inheritDoc}
     */
    @Override
    public void acquerir(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        FacadeException {
        try {
            getLivreService().acquerir(session,
                livreDTO);
        } catch(ServiceException serviceException) {
            throw new FacadeException(serviceException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void vendre(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        InvalidPrimaryKeyException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ExistingLoanException,
        ExistingReservationException,
        FacadeException {
        try {
            getLivreService().vendre(session,
                livreDTO);
        } catch(ServiceException serviceException) {
            throw new FacadeException(serviceException);
        }
    }

    @Override
    public LivreDTO getLivre(Session session,
        String idLivre) throws InvalidHibernateSessionException,
        InvalidPrimaryKeyException,
        FacadeException {
        try {
            return getLivreService().getLivre(session,
                idLivre);
        } catch(ServiceException e) {
            throw new FacadeException(e);
        }
    }
}
