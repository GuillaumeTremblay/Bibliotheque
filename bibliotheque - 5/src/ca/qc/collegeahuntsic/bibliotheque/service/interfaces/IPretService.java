// Fichier IPretService.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-01

package ca.qc.collegeahuntsic.bibliotheque.service.interfaces;

import java.sql.Timestamp;
import java.util.List;
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
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.InvalidLoanLimitException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.MissingLoanException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ServiceException;

/**
 * Interface de service pour manipuler les prêts dans la base de données.
 * 
 * @author Gilles Benichou
 */
public interface IPretService extends IService {
    /**
     * Ajoute un nouveau prêt dans la base de données.
     * 
     * @param connexion La connexion à utiliser
     * @param pretDTO Le prêt à ajouter
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si le prêt est <code>null</code>
     * @throws InvalidDTOClassException Si la classe du prêt n'est pas celle que prend en charge le DAO
     * @throws InvalidPrimaryKeyRequestException Si la requête de la clef primaire du prêt est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void add(Connexion connexion,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        InvalidPrimaryKeyRequestException,
        ServiceException;

    /**
     * Lit un prêt à partir de la base de données.
     * 
     * @param connexion La connexion à utiliser
     * @param idPret L'ID du prêt à lire
     * @return Le prêt
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidPrimaryKeyException Si la clef primaire du prêt est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    PretDTO get(Connexion connexion,
        String idPret) throws InvalidHibernateSessionException,
        InvalidPrimaryKeyException,
        ServiceException;

    /**
     * Met à jour un prêt dans la base de données.
     * 
     * @param connexion La connexion à utiliser
     * @param pretDTO Le prêt à mettre à jour
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si le prêt est <code>null</code>
     * @throws InvalidDTOClassException Si la classe du prêt n'est pas celle que prend en charge le DAO
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void update(Connexion connexion,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException;

    /**
     * Supprime un prêt de la base de données.
     * 
     * @param connexion La connexion à utiliser
     * @param pretDTO Le prêt à supprimer
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si le prêt est <code>null</code>
     * @throws InvalidDTOClassException Si la classe du prêt n'est pas celle que prend en charge le DAO
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void delete(Connexion connexion,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException;

    /**
     * Trouve tous les prêts de la base de données. La liste est classée par ordre croissant sur <code>sortByPropertyName</code>. Si aucun prêt
     * n'est trouvé, une {@link List} vide est retournée.
     * 
     * @param connexion La connexion à utiliser
     * @param sortByPropertyName The nom de la propriété à utiliser pour classer
     * @return La liste de tous les prêts ; une liste vide sinon
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    List<PretDTO> getAll(Connexion connexion,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        ServiceException;

    /**
     * Trouve les prêts à partir d'un membre. La liste est classée par ordre croissant sur <code>sortByPropertyName</code>. Si aucun prêt n'est
     * trouvé, une {@link List} vide est retournée.
     * 
     * @param connexion La connexion à utiliser
     * @param idMembre L'ID du membre à trouver
     * @param sortByPropertyName The nom de la propriété à utiliser pour classer
     * @return La liste des prêts correspondants ; une liste vide sinon
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidCriterionException Si l'ID du membre est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    List<PretDTO> findByMembre(Connexion connexion,
        String idMembre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ServiceException;

    /**
     * Trouve les prêts à partir d'un livre. La liste est classée par ordre croissant sur <code>sortByPropertyName</code>. Si aucun prêt n'est
     * trouvé, une {@link List} vide est retournée.
     * 
     * @param connexion La connexion à utiliser
     * @param idLivre L'ID du livre à trouver
     * @param sortByPropertyName The nom de la propriété à utiliser pour classer
     * @return La liste des prêts correspondants ; une liste vide sinon
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidCriterionException Si l'ID du livre est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    List<PretDTO> findByLivre(Connexion connexion,
        String idLivre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ServiceException;

    /**
     * Trouve les prêts à partir d'une date de prêt. La liste est classée par ordre croissant sur <code>sortByPropertyName</code>. Si aucun prêt
     * n'est trouvé, une {@link List} vide est retournée.
     * 
     * @param connexion La connexion à utiliser
     * @param datePret La date de prêt à trouver
     * @param sortByPropertyName The nom de la propriété à utiliser pour classer
     * @return La liste des prêts correspondants ; une liste vide sinon
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidCriterionException Si la date de prêt est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    List<PretDTO> findByDatePret(Connexion connexion,
        Timestamp datePret,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ServiceException;

    /**
     * Trouve les prêts à partir d'une date de retour. La liste est classée par ordre croissant sur <code>sortByPropertyName</code>. Si aucun
     * prêt n'est trouvé, une {@link List} vide est retournée.
     * 
     * @param connexion La connexion à utiliser
     * @param dateRetour La date de retour à trouver
     * @param sortByPropertyName The nom de la propriété à utiliser pour classer
     * @return La liste des prêts correspondants ; une liste vide sinon
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidCriterionException Si la date de retour est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    List<PretDTO> findByDateRetour(Connexion connexion,
        Timestamp dateRetour,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ServiceException;

    /**
     * Commence un prêt.
     * 
     * @param connexion La connexion à utiliser
     * @param pretDTO Le prêt à commencer
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si le prêt est <code>null</code>
     * @throws InvalidPrimaryKeyException Si la clef primaire du membre est <code>null</code> ou si la clef primaire du livre est
     *         <code>null</code>
     * @throws MissingDTOException Si le membre n'existe pas ou si le livre n'existe pas
     * @throws InvalidCriterionException Si l'ID du livre est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ExistingLoanException Si le livre a été prêté
     * @throws InvalidLoanLimitException Si le membre a atteint sa limite de prêt
     * @throws ExistingReservationException Si le livre a été réservé
     * @throws InvalidDTOClassException Si la classe du membre n'est pas celle que prend en charge le DAO
     * @throws InvalidPrimaryKeyRequestException Si la requête de la clef primaire du membre est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void commencer(Connexion connexion,
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
        ServiceException;

    /**
     * Renouvelle le prêt d'un livre.
     * 
     * @param connexion La connexion à utiliser
     * @param pretDTO Le prêt à renouveler
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si le prêt est <code>null</code>
     * @throws InvalidPrimaryKeyException Si la clef primaire du prêt est <code>null</code>, si la clef primaire du membre est <code>null</code>
     *         ou si la clef primaire du livre est <code>null</code>
     * @throws MissingDTOException Si le prêt n'existe pas, si le membre n'existe pas ou si le livre n'existe pas
     * @throws InvalidCriterionException Si l'ID du membre est <code>null</code> ou si l'ID du livre est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws MissingLoanException Si le livre n'a pas encore été prêté
     * @throws ExistingLoanException Si le livre a été prêté à quelqu'un d'autre
     * @throws ExistingReservationException Si le livre a été réservé
     * @throws InvalidDTOClassException Si la classe du prêt n'est pas celle que prend en charge le DAO
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void renouveler(Connexion connexion,
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
        ServiceException;

    /**
     * Termine un prêt.
     * 
     * @param connexion La connexion à utiliser
     * @param pretDTO Le prêt à terminer
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si le prêt est <code>null</code>
     * @throws InvalidPrimaryKeyException Si la clef primaire du prêt est <code>null</code>, si la clef primaire du membre est <code>null</code>
     *         ou si la clef primaire du livre est <code>null</code>
     * @throws MissingDTOException Si le prêt n'existe pas, si le membre n'existe pas ou si le livre n'existe pas
     * @throws InvalidCriterionException Si l'ID du membre est <code>null</code> ou si l'ID du livre est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws MissingLoanException Si le livre n'a pas encore été prêté
     * @throws ExistingLoanException Si le livre a été prêté à quelqu'un d'autre
     * @throws InvalidDTOClassException Si la classe du membre n'est pas celle que prend en charge le DAO ou si la classe du prêt n'est pas
     *         celle que prend en charge le DAO
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void terminer(Connexion connexion,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidPrimaryKeyException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        MissingLoanException,
        ExistingLoanException,
        InvalidDTOClassException,
        ServiceException;
}
