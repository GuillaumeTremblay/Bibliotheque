// Fichier IMembreService.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-01

package ca.qc.collegeahuntsic.bibliotheque_backend.service.interfaces;

import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque_backend.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.dto.MissingDTOException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliotheque_backend.exception.service.ServiceException;
import org.hibernate.Session;

/**
 * Interface de service pour manipuler les membres dans la base de données.
 * 
 * @author Gilles Benichou
 */
public interface IMembreService extends IService {
    /**
     * Ajoute un nouveau membre dans la base de données.
     * 
     * @param connexion La connexion à utiliser
     * @param membreDTO Le membre à ajouter
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si le membre est <code>null</code>
     * @throws InvalidDTOClassException Si la classe du membre n'est pas celle que prend en charge le DAO
     * @throws InvalidPrimaryKeyRequestException Si la requête de la clef primaire du membre est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void addMembre(Session session,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException;

    /**
     * Lit un membre à partir de la base de données.
     * 
     * @param connexion La connexion à utiliser
     * @param idMembre L'ID du membre à lire
     * @return Le membre
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidPrimaryKeyException Si la clef primaire du membre est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    MembreDTO getMembre(Session session,
        String idMembre) throws InvalidHibernateSessionException,
        InvalidPrimaryKeyException,
        ServiceException;

    /**
     * Met à jour un membre dans la base de données.
     * 
     * @param connexion La connexion à utiliser
     * @param membreDTO Le membre à mettre à jour
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si le membre est <code>null</code>
     * @throws InvalidDTOClassException Si la classe du membre n'est pas celle que prend en charge le DAO
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void updateMembre(Session session,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException;

    /**
     * Supprime un membre de la base de données.
     * 
     * @param connexion La connexion à utiliser
     * @param membreDTO Le membre à supprimer
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si le membre est <code>null</code>
     * @throws InvalidDTOClassException Si la classe du membre n'est pas celle que prend en charge le DAO
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void deleteMembre(Session session,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException;

    /**
     * Trouve tous les membres de la base de données. La liste est classée par ordre croissant sur <code>sortByPropertyName</code>. Si aucun
     * membre n'est trouvé, une {@link List} vide est retournée.
     * 
     * @param connexion La connexion à utiliser
     * @param sortByPropertyName The nom de la propriété à utiliser pour classer
     * @return La liste de tous les membres ; une liste vide sinon
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    List<MembreDTO> getAllMembres(Session session,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        ServiceException;

    /**
     * Trouve les membres à partir d'un nom. La liste est classée par ordre croissant sur <code>sortByPropertyName</code>. Si aucun membre n'est
     * trouvé, une {@link List} vide est retournée.
     * 
     * @param connexion La connexion à utiliser
     * @param nom Le nom à trouver
     * @param sortByPropertyName The nom de la propriété à utiliser pour classer
     * @return La liste des membres correspondants ; une liste vide sinon
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidCriterionException Si le nom est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     * @throws InvalidCriterionValueException 
     */
    List<MembreDTO> findMembreByNom(Session session,
        String nom,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ServiceException,
        InvalidCriterionValueException;

    /**
     * Inscrit un membre.
     * 
     * @param connexion La connexion à utiliser
     * @param membreDTO Le membre à inscrire
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si le membre est <code>null</code>
     * @throws InvalidDTOClassException Si la classe du membre n'est pas celle que prend en charge le DAO
     * @throws InvalidPrimaryKeyRequestException Si la requête de la clef primaire du membre est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void inscrire(Session session,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException;

    /**
     * Désincrit un membre.
     * 
     * @param connexion La connexion à utiliser
     * @param membreDTO Le membre à désinscrire
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si le livre est <code>null</code>
     * @throws InvalidDTOClassException Si la classe du membre n'est pas celle que prend en charge le DAO
     * @throws InvalidPrimaryKeyException Si la clef primaire du membre est <code>null</code>
     * @throws MissingDTOException Si le membre n'existe pas
     * @throws ExistingLoanException Si le membre a encore des prêts
     * @throws InvalidCriterionException Si l'ID du membre est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ExistingReservationException Si le membre a des réservations
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void desinscrire(Session session,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        InvalidPrimaryKeyException,
        MissingDTOException,
        ExistingLoanException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ExistingReservationException,
        ServiceException;
}
