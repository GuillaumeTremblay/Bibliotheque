// Fichier IReservationFacade.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-01

package ca.qc.collegeahuntsic.bibliotheque_backend.facade.interfaces;

import org.hibernate.Session;

import ca.qc.collegeahuntsic.bibliotheque_backend.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dto.MissingDTOException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.facade.FacadeException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.service.InvalidLoanLimitException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.service.MissingLoanException;

/**
 * Interface de façade pour manipuler les réservations dans la base de données.
 * 
 * @author Gilles Benichou
 */
public interface IReservationFacade extends IFacade {
    /**
     * Place une réservation.
     * 
     * @param session La session à utiliser
     * @param reservationDTO La réservation à placer
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidDTOException Si la réservation est <code>null</code>
     * @throws InvalidPrimaryKeyException Si la clef primaire du membre est <code>null</code> ou si la clef primaire du livre est
     *         <code>null</code>
     * @throws MissingDTOException Si le membre n'existe pas ou si le livre n'existe pas
     * @throws InvalidCriterionException Si l'ID du livre est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws MissingLoanException Si le livre n'a pas encore été prêté
     * @throws ExistingLoanException Si le livre est déjà prêté au membre
     * @throws ExistingReservationException Si le membre a déjà réservé ce livre
     * @throws InvalidDTOClassException Si la classe de la réservation n'est pas celle que prend en charge le DAO
     * @throws InvalidPrimaryKeyRequestException Si la requête de la clef primaire de la réservation est <code>null</code>
     * @throws FacadeException S'il y a une erreur avec la base de données
     */
    void placer(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidPrimaryKeyException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        MissingLoanException,
        ExistingLoanException,
        ExistingReservationException,
        InvalidDTOClassException,
        FacadeException;

    /**
     * Utilise une réservation.
     * 
     * @param session La session à utiliser
     * @param reservationDTO La réservation à utiliser
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidDTOException Si la réservation est <code>null</code>
     * @throws InvalidPrimaryKeyException Si la clef primaire de la réservation est <code>null</code>, si la clef primaire du membre est
     *         <code>null</code> ou si la clef primaire du livre est <code>null</code>
     * @throws MissingDTOException Si la réservation n'existe pas, si le membre n'existe pas ou si le livre n'existe pas
     * @throws InvalidCriterionException Si l'ID du livre est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ExistingReservationException Si la réservation n'est pas la première de la liste
     * @throws ExistingLoanException Si le livre est déjà prêté au membre
     * @throws InvalidLoanLimitException Si le membre a atteint sa limite de prêt
     * @throws InvalidDTOClassException Si la classe du membre n'est pas celle que prend en charge le DAO ou si la classe du  n'est pas
     *         celle que prend en charge le DAO
     * @throws InvalidPrimaryKeyRequestException Si la requête de la clef primaire du prêt est <code>null</code>
     * @throws FacadeException S'il y a une erreur avec la base de données
     */
    void utiliser(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidPrimaryKeyException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ExistingReservationException,
        ExistingLoanException,
        InvalidLoanLimitException,
        InvalidDTOClassException,
        FacadeException;

    /**
     * Annule une réservation.
     * 
     * @param session La session à utiliser
     * @param reservationDTO Le reservation à annuler
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidDTOException Si la réservation est <code>null</code>
     * @throws InvalidPrimaryKeyException Si la clef primaire de la réservation est <code>null</code>
     * @throws MissingDTOException Si la réservation n'existe pas, si le membre n'existe pas ou si le livre n'existe pas
     * @throws InvalidDTOClassException Si la classe de la réservation n'est pas celle que prend en charge le DAO
     * @throws FacadeException Si la session est <code>null</code>, si la réservation est <code>null</code>, si la réservation n'existe pas
     *         ou s'il y a une erreur avec la base de données
     */
    void annuler(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidPrimaryKeyException,
        MissingDTOException,
        InvalidDTOClassException,
        FacadeException;
}
