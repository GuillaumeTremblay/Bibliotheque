// Fichier IReservationFacade.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-01

package ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces;

import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.FacadeException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.InvalidLoanLimitException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.MissingLoanException;
import org.hibernate.Session;

/**
 * Interface de façade pour manipuler les réservations dans la base de données.
 *
 * @author Gilles Benichou
 */
public interface IReservationFacade extends IFacade {

    /**.
     *
     * Retourne une réservation à l'aide de son ID
     *
     * @param session La session à utiliser
     * @param idReservation L'ID de la réservation à retourner
     * @return Une ReservationDTO
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidPrimaryKeyException Si la clef primaire de la réservation est <code>null</code>
     * @throws FacadeException si la réservation n'existe pas ou s'il y a
     *             une erreur avec la base de données
     */
    ReservationDTO getReservation(Session session,
        String idReservation) throws InvalidHibernateSessionException,
        InvalidPrimaryKeyException,
        FacadeException;

    /**
     * Place une réservation.
     * @param session La session à utiliser
     * @param reservationDTO La réservation à placer
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidDTOException Si la réservation est <code>null</code>
     * @throws MissingLoanException Si le livre n'a pas encore été prêté
     * @throws ExistingLoanException Si le livre est déjà prêté au membre
     * @throws ExistingReservationException Si le membre a déjà réservé ce livre charge le DAO
     * @throws FacadeException S'il y a une erreur avec la base de données
     */
    void placerReservation(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        MissingLoanException,
        ExistingLoanException,
        ExistingReservationException,
        FacadeException;

    /**
     * Utilise une réservation.
     *
     * @param session La session à utiliser
     * @param reservationDTO La réservation à utiliser
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidDTOException Si la réservation est <code>null</code>
     * @throws ExistingReservationException Si la réservation n'est pas la première de la liste
     * @throws ExistingLoanException Si le livre est déjà prêté au membre
     * @throws InvalidLoanLimitException Si le membre a atteint sa limite de prêt
     * @throws FacadeException S'il y a une erreur avec la base de données
     */
    void utiliserReservation(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        ExistingReservationException,
        ExistingLoanException,
        InvalidLoanLimitException,
        FacadeException;

    /**
     * Annule une réservation.
     *
     * @param session La session à utiliser
     * @param reservationDTO Le reservation à annuler
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidDTOException Si la réservation est <code>null</code>
     * @throws FacadeException Si la session est <code>null</code>, si la réservation est <code>null</code>, si une erreur avec la base de données
     */
    void annulerReservation(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        FacadeException;
}
