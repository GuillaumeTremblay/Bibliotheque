// Fichier IPretFacade.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-01

package ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces;

import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.PretDTO;
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
 * Interface de façade pour manipuler les prêts dans la base de données.
 *
 * @author Gilles Benichou
 */
public interface IPretFacade extends IFacade {

    /**.
     *
     * Retourne un prêt à l'aide de son ID
     *
     * @param session La session à utiliser
     * @param idPret L'ID du prêt à retourner
     * @return Un prêt DTO
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidPrimaryKeyException Si la clef primaire du prêt est <code>null</code>
     * @throws FacadeException S'il y a une erreur avec la base de données
     */
    PretDTO getPret(Session session,
        String idPret) throws InvalidHibernateSessionException,
        InvalidPrimaryKeyException,
        FacadeException;

    /**
     * Commence un prêt.
     *
     * @param session La session à utiliser
     * @param pretDTO Le prêt à commencer
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidDTOException Si le prêt est <code>null</code>
     *         <code>null</code>
     * @throws ExistingLoanException Si le livre a été prêté
     * @throws InvalidLoanLimitException Si le membre a atteint sa limite de prêt
     * @throws ExistingReservationException Si le livre a été réservé
     * @throws FacadeException S'il y a une erreur avec la base de données
     */
    void commencerPret(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        ExistingLoanException,
        InvalidLoanLimitException,
        ExistingReservationException,
        FacadeException;

    /**
     * Renouvelle le prêt d'un livre.
     *
     * @param session La session à utiliser
     * @param pretDTO Le prêt à renouveler
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidDTOException Si le prêt est <code>null</code>
     *         ou si la clef primaire du livre est <code>null</code>
     * @throws MissingLoanException Si le livre n'a pas encore été prêté
     * @throws ExistingReservationException Si le livre a été réservé
     * @throws FacadeException S'il y a une erreur avec la base de données
     */
    void renouvelerPret(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        MissingLoanException,
        ExistingReservationException,
        FacadeException;

    /**
     * Termine un prêt.
     *
     * @param session La session à utiliser
     * @param pretDTO Le prêt à terminer
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidDTOException Si le prêt est <code>null</code>
     *         ou si la clef primaire du livre est <code>null</code>
     * @throws MissingLoanException Si le livre n'a pas encore été prêté
     *         celle que prend en charge le DAO
     * @throws FacadeException S'il y a une erreur avec la base de données
     */
    void terminerPret(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        MissingLoanException,
        FacadeException;

}
