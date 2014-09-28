
package ca.qc.collegeahuntsic.bibliotheque.service;

import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.LivreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.MembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.PretDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.ReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.ServiceException;

// TODO: Auto-generated Javadoc
/**
 * Gestion des transactions de reliées aux prêts de livres
 * aux membres dans une bibliothèque.
 *
 * Ce programme permet de gérer les transactions prêter,
 * renouveler et retourner.
 *
 * Pré-condition
 *   la base de données de la bibliothèque doit exister
 *
 * Post-condition
 *   le programme effectue les maj associées à chaque
 *   transaction
 * </pre>
 */

public class PretService extends Service {
    /**
     * TODO Auto-generated field javadoc
     */
    private static final long serialVersionUID = 1L;

    private LivreDAO livreDAO;

    private MembreDAO membreDAO;

    private PretDAO pretDAO;

    private ReservationDAO reservationDAO;

    /**
     * Crée un service à partir des DAOs de livre, member et réservation
     *
     * @param livreDAO Le DAO de la table <code>livre</code>
     * @param membreDAO Le DAO de la table <code>membre</code>
     * @param pretDAO Le DAO de la table <code>pret</code>
     */
    public PretService(LivreDAO livreDAO,
        MembreDAO membreDAO,
        PretDAO pretDAO,
        ReservationDAO reservationDAO) {
        super();
        setLivreDAO(livreDAO);
        setMembreDAO(membreDAO);
        setPretDAO(pretDAO);
        setReservationDAO(reservationDAO);
    }

    /**
     * Getter de la variable d'instance <code>this.livreDAO</code>.
     *
     * @return La variable d'instance <code>this.livreDAO</code>
     */
    public LivreDAO getLivreDAO() {
        return this.livreDAO;
    }

    /**
     * Setter de la variable d'instance <code>this.livreDAO</code>.
     *
     * @param livreDAO La valeur à utiliser pour la variable d'instance <code>this.livreDAO</code>
     */
    public void setLivreDAO(LivreDAO livreDAO) {
        this.livreDAO = livreDAO;
    }

    /**
     * Getter de la variable d'instance <code>this.membreDAO</code>.
     *
     * @return La variable d'instance <code>this.membreDAO</code>
     */
    public MembreDAO getMembreDAO() {
        return this.membreDAO;
    }

    /**
     * Setter de la variable d'instance <code>this.membreDAO</code>.
     *
     * @param membreDAO La valeur à utiliser pour la variable d'instance <code>this.membreDAO</code>
     */
    public void setMembreDAO(MembreDAO membreDAO) {
        this.membreDAO = membreDAO;
    }

    /**
     * Getter de la variable d'instance <code>this.pretDAO</code>.
     *
     * @return La variable d'instance <code>this.pretDAO</code>
     */
    public PretDAO getPretDAO() {
        return this.pretDAO;
    }

    /**
     * Getter de la variable d'instance <code>this.reservationDAO</code>.
     *
     * @return La variable d'instance <code>this.reservationDAO</code>
     */
    public ReservationDAO getReservationDAO() {
        return this.reservationDAO;
    }

    /**
     * Setter de la variable d'instance <code>this.reservationDAO</code>.
     *
     * @param reservationDAO La valeur à utiliser pour la variable d'instance <code>this.reservationDAO</code>
     */
    public void setReservationDAO(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    /**
     * Setter de la variable d'instance <code>this.pretDAO</code>.
     *
     * @param pretDAO La valeur à utiliser pour la variable d'instance <code>this.pretDAO</code>
     */
    public void setPretDAO(PretDAO pretDAO) {
        this.pretDAO = pretDAO;
    }

    public void add(PretDTO pretDTO) throws ServiceException {
        try {
            getPretDAO().add(pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    public PretDTO read(PretDTO pretDTO) throws ServiceException {
        try {
            return getPretDAO().read(pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    public void update(PretDTO pretDTO) throws ServiceException {
        try {
            getPretDAO().update(pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    public List<PretDTO> getAll() throws ServiceException {
        try {
            return getPretDAO().getAll();
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    public PretDTO findByMembre(MembreDTO membreDTO) throws ServiceException {
        try {
            return getPretDAO().findByMembre(membreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

}