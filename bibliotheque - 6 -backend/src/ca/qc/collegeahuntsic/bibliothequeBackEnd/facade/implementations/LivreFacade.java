// Fichier LivreFacade.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-01

package ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.implementations;

import java.util.List;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.FacadeException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.InvalidServiceException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ServiceException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces.ILivreFacade;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.service.interfaces.ILivreService;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateLivre(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        FacadeException {
        try {
            getLivreService().updateLivre(session,
                livreDTO);
        } catch(ServiceException serviceException) {
            throw new FacadeException(serviceException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LivreDTO> getAllLivres(Session session,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        FacadeException {
        try {
            return getLivreService().getAllLivres(session,
                sortByPropertyName);
        } catch(ServiceException serviceException) {
            throw new FacadeException(serviceException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void acquerirLivre(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        FacadeException {
        try {
            getLivreService().acquerirLivre(session,
                livreDTO);
        } catch(ServiceException serviceException) {
            throw new FacadeException(serviceException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void vendreLivre(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        ExistingReservationException,
        FacadeException,
        ExistingLoanException {
        try {
            getLivreService().vendreLivre(session,
                livreDTO);
        } catch(ServiceException serviceException) {
            throw new FacadeException(serviceException);
        }
    }
}
