// Fichier ILivreService.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-01

package ca.qc.collegeahuntsic.bibliotheque.service.interfaces;

import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
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
 * Interface de base pour les services.<br />
 * Toutes les interfaces de service devrait en hériter.
 * 
 * @author Gilles Benichou
 */
public interface ILivreService extends IService {
    /**
     * Ajoute un nouveau livre dans la base de données.
     * 
     * @param connexion La connexion à utiliser
     * @param livreDTO Le livre à ajouter
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si le livre est <code>null</code>
     * @throws InvalidDTOClassException Si la classe du livre n'est pas celle que prend en charge le DAO
     * @throws InvalidPrimaryKeyRequestException Si la requête de la clef primaire du livre est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void add(Connexion connexion,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        InvalidPrimaryKeyRequestException,
        ServiceException;

    /**
     * Lit un livre à partir de la base de données.
     * 
     * @param connexion La connexion à utiliser
     * @param idLivre L'ID du livre à lire
     * @return Le livre
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidPrimaryKeyException Si la clef primaire du livre est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    LivreDTO get(Connexion connexion,
        String idLivre) throws InvalidHibernateSessionException,
        InvalidPrimaryKeyException,
        ServiceException;

    /**
     * Met à jour un livre dans la base de données.
     * 
     * @param connexion La connexion à utiliser
     * @param livreDTO Le livre à mettre à jour
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si le livre est <code>null</code>
     * @throws InvalidDTOClassException Si la classe du livre n'est pas celle que prend en charge le DAO
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void update(Connexion connexion,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException;

    /**
     * Supprime un livre de la base de données.
     * 
     * @param connexion La connexion à utiliser
     * @param livreDTO Le livre à supprimer
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si le livre est <code>null</code>
     * @throws InvalidDTOClassException Si la classe du livre n'est pas celle que prend en charge le DAO
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void delete(Connexion connexion,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException;

    /**
     * Trouve tous les livres de la base de données. La liste est classée par ordre croissant sur <code>sortByPropertyName</code>. Si aucun
     * livre n'est trouvé, une {@link List} vide est retournée.
     * 
     * @param connexion La connexion à utiliser
     * @param sortByPropertyName The nom de la propriété à utiliser pour classer
     * @return La liste de tous les livres ; une liste vide sinon
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    List<LivreDTO> getAll(Connexion connexion,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        ServiceException;

    /**
     * Trouve les livres à partir d'un titre. La liste est classée par ordre croissant sur <code>sortByPropertyName</code>. Si aucun livre
     * n'est trouvé, une {@link List} vide est retournée.
     * 
     * @param connexion La connexion à utiliser
     * @param titre Le titre à trouver
     * @param sortByPropertyName The nom de la propriété à utiliser pour classer
     * @return La liste des livres correspondants ; une liste vide sinon
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidCriterionException Si le titre est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    List<LivreDTO> findByTitre(Connexion connexion,
        String titre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ServiceException;

    /**
     * Acquiert un livre.
     * 
     * @param connexion La connexion à utiliser
     * @param livreDTO Le livre à acquérir
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si le livre est <code>null</code>
     * @throws InvalidDTOClassException Si la classe du livre n'est pas celle que prend en charge le DAO
     * @throws InvalidPrimaryKeyRequestException Si la requête de la clef primaire du livre est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void acquerir(Connexion connexion,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        InvalidPrimaryKeyRequestException,
        ServiceException;

    /**
     * Vend un livre.
     * 
     * @param connexion La connexion à utiliser
     * @param livreDTO Le livre à vendre
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si le livre est <code>null</code>
     * @throws InvalidDTOClassException Si la classe du livre n'est pas celle que prend en charge le DAO
     * @throws InvalidPrimaryKeyException Si la clef primaire du livre est <code>null</code>
     * @throws MissingDTOException Si le livre n'existe pas
     * @throws InvalidCriterionException Si l'ID du membre est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ExistingLoanException Si le livre a été prêté
     * @throws ExistingReservationException Si le livre a été réservé
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void vendre(Connexion connexion,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        InvalidPrimaryKeyException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ExistingLoanException,
        ExistingReservationException,
        ServiceException;
}
