// Fichier PretFacade.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-01

package ca.qc.collegeahuntsic.bibliotheque.facade.implementations;

import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyRequestException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.MissingDTOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.facade.FacadeException;
import ca.qc.collegeahuntsic.bibliotheque.exception.facade.InvalidServiceException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.InvalidLoanLimitException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.MissingLoanException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ServiceException;
import ca.qc.collegeahuntsic.bibliotheque.facade.interfaces.IPretFacade;
import ca.qc.collegeahuntsic.bibliotheque.service.interfaces.IPretService;

/**
 * Facade pour interagir avec le service de prêts.
 *
 * @author Gilles Benichou
 */
public class PretFacade extends Facade implements IPretFacade {
    private IPretService pretService;

    /**
     * Crée la façade de la table <code>pret</code>.
     * 
     * @param pretService Le service de la table <code>pret</code>
     * @throws InvalidServiceException Si le service de prêts est <code>null</code>
     */
    public PretFacade(IPretService pretService) throws InvalidServiceException { // TODO: Change to package when switching to Spring
        super();
        if(pretService == null) {
            throw new InvalidServiceException("Le service de prêts ne peut être null");
        }
        setPretService(pretService);
    }

    // Region Getters and Setters
    /**
     * Getter de la variable d'instance <code>this.pretService</code>.
     *
     * @return La variable d'instance <code>this.pretService</code>
     */
    private IPretService getPretService() {
        return this.pretService;
    }

    /**
     * Setter de la variable d'instance <code>this.pretService</code>.
     *
     * @param pretService La valeur à utiliser pour la variable d'instance <code>this.pretService</code>
     */
    private void setPretService(IPretService pretService) {
        this.pretService = pretService;
    }

    // EndRegion Getters and Setters

    /**
     * {@inheritDoc}
     */
    @Override
    public void commencer(Connexion connexion,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidPrimaryKeyException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ExistingLoanException,
        InvalidLoanLimitException,
        ExistingReservationException,
        InvalidDTOClassException,
        InvalidPrimaryKeyRequestException,
        FacadeException {
        try {
            getPretService().commencer(connexion,
                pretDTO);
        } catch(ServiceException serviceException) {
            throw new FacadeException(serviceException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renouveler(Connexion connexion,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidPrimaryKeyException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        MissingLoanException,
        ExistingLoanException,
        ExistingReservationException,
        InvalidDTOClassException,
        FacadeException {
        try {
            getPretService().renouveler(connexion,
                pretDTO);
        } catch(ServiceException serviceException) {
            throw new FacadeException(serviceException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void terminer(Connexion connexion,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidPrimaryKeyException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        MissingLoanException,
        ExistingLoanException,
        InvalidDTOClassException,
        FacadeException {
        try {
            getPretService().terminer(connexion,
                pretDTO);
        } catch(ServiceException serviceException) {
            throw new FacadeException(serviceException);
        }
    }
}
