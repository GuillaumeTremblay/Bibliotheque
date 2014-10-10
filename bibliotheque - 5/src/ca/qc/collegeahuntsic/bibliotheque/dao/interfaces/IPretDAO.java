// Fichier IPretDAO.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-31

package ca.qc.collegeahuntsic.bibliotheque.dao.interfaces;

import java.sql.Timestamp;
import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;

/**
 * Interface DAO pour manipuler les prêts dans la base de données.
 * 
 * @author Gilles Benichou
 */
public interface IPretDAO extends IDAO {
    /**
     * Trouve les prêts non retournés d'un membre. La liste est classée par ordre croissant sur <code>sortByPropertyName</code>. Si aucun prêt
     * n'est trouvé, une {@link List} vide est retournée.
     * 
     * @param connexion La connexion à utiliser
     * @param idMembre L'ID du membre à trouver
     * @param sortByPropertyName The nom de la propriété à utiliser pour classer
     * @return La liste des prêts correspondants ; une liste vide sinon
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidCriterionException Si l'ID du membre est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    List<PretDTO> findByMembre(Connexion connexion,
        String idMembre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException;

    /**
     * Trouve les livres en cours d'emprunt. La liste est classée par ordre croissant sur <code>sortByPropertyName</code>. Si aucun prêt n'est
     * trouvé, une {@link List} vide est retournée.
     * 
     * @param connexion La connexion à utiliser
     * @param idLivre L'ID du livre à trouver
     * @param sortByPropertyName The nom de la propriété à utiliser pour classer
     * @return La liste des prêts correspondants ; une liste vide sinon
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidCriterionException Si l'ID du livre est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    List<PretDTO> findByLivre(Connexion connexion,
        String idLivre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException;

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
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    List<PretDTO> findByDatePret(Connexion connexion,
        Timestamp datePret,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException;

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
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    List<PretDTO> findByDateRetour(Connexion connexion,
        Timestamp dateRetour,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException;
}
