// Fichier IMembreFacade.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-01

package ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces;

import java.util.List;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.FacadeException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingReservationException;
import org.hibernate.Session;

/**
 * Interface de façade pour manipuler les membres dans la base de données.
 *
 * @author Gilles Benichou
 */
public interface IMembreFacade extends IFacade {

    /**.
     *  Retourne un membre a l'aide de son ID
     *
     *
     * @param session La session à utiliser
     * @param idMembre L'id du membre à utiliser
     * @return Un MembreDTO
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidPrimaryKeyException Si la clef primaire du membre est <code>null</code>
     * @throws FacadeException S'il y a une erreur avec la base de données
     */
    MembreDTO getMembre(Session session,
        String idMembre) throws InvalidHibernateSessionException,
        InvalidPrimaryKeyException,
        FacadeException;

    /**
     * Met à jour un membre dans la base de données.
     *
     * @param session La session Hibernate à utiliser
     * @param membreDTO Le membre à mettre à jour
     * @throws InvalidHibernateSessionException Si la session Hibernate est <code>null</code>
     * @throws InvalidDTOException Si le membre est <code>null</code>
     * @throws FacadeException S'il y a une erreur avec la base de données
     */
    void updateMembre(Session session,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        FacadeException;

    /**
     * Trouve tous les membres de la base de données. La liste est classée par ordre croissant sur <code>sortByPropertyName</code>. Si aucun
     * membre n'est trouvé, une {@link List} vide est retournée.
     *
     * @param session La session Hibernate à utiliser
     * @param sortByPropertyName The nom de la propriété à utiliser pour classer
     * @return La liste de tous les membres ; une liste vide sinon
     * @throws InvalidHibernateSessionException Si la session Hibernate est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws FacadeException S'il y a une erreur avec la base de données
     */
    List<MembreDTO> getAllMembres(Session session,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        FacadeException;

    /**
     * Inscrit un membre.
     *
     * @param session La session à utiliser
     * @param membreDTO Le membre à inscrire
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidDTOException Si le membre est <code>null</code>
     * @throws FacadeException S'il y a une erreur avec la base de données
     */
    void inscrireMembre(Session session,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        FacadeException;

    /**
     * Désincrit un membre.
     *
     * @param session La session à utiliser
     * @param membreDTO Le membre à désinscrire
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidDTOException Si le livre est <code>null</code>
     * @throws ExistingLoanException Si le membre a encore des prêts
     * @throws ExistingReservationException Si le membre a des réservations
     * @throws FacadeException S'il y a une erreur avec la base de données
     */
    void desinscrireMembre(Session session,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        ExistingLoanException,
        ExistingReservationException,
        FacadeException;

}
