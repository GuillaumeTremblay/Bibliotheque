// Fichier IMembreService.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-01

package ca.qc.collegeahuntsic.bibliotheque.service.interfaces;

import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyRequestException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.MissingDTOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ServiceException;

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
    void add(Connexion connexion,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        InvalidPrimaryKeyRequestException,
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
    MembreDTO get(Connexion connexion,
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
    void update(Connexion connexion,
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
    void delete(Connexion connexion,
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
    List<MembreDTO> getAll(Connexion connexion,
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
     */
    List<MembreDTO> findByNom(Connexion connexion,
        String nom,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ServiceException;

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
    void inscrire(Connexion connexion,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        InvalidPrimaryKeyRequestException,
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
    void desinscrire(Connexion connexion,
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
