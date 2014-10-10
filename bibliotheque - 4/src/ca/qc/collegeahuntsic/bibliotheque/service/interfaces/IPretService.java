// Fichier Facade.java
// Auteur : Samuel Filion-Normandeau
// Date de création : 2014-10-03

package ca.qc.collegeahuntsic.bibliotheque.service.interfaces;

//[region] Importations
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
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ServiceException;

//[endRegion] Fin package, importations

public interface IPretService extends IService {

    //[region] Opérations CRUD

    /**
     *
     * Retourne un prêt
     *
     */
    PretDTO get(Connexion connexion,
        String idPret) throws ServiceException,
        InvalidHibernateSessionException,
        InvalidPrimaryKeyException;

    /**
     *
     * Ajoute un prêt
     *
     */
    void add(Connexion connexion,
        PretDTO pretDTO) throws ServiceException,
        InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        InvalidPrimaryKeyRequestException;

    /**
     *
     * Modifie un prêt
     *
     */
    void update(Connexion connexion,
        PretDTO pretDTO) throws ServiceException,
        InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException;

    /**
     *
     * Suprimme un prêt
     *
     */
    void delete(Connexion connexion,
        PretDTO pretDTO) throws ServiceException,
        InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException;

    //[endRegion] Fin opérations CRUD

    //[region] Opérations de recherche

    List<PretDTO> getAll(Connexion connexion,
        String sortByPropertyName) throws ServiceException,
        InvalidHibernateSessionException,
        InvalidSortByPropertyException;

    List<PretDTO> findByMembre(Connexion connexion,
        String idMembre,
        String sortByPropertyName) throws ServiceException,
        InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException;

    List<PretDTO> findByLivre(Connexion connexion,
        String idLivre,
        String sortByPropertyName) throws ServiceException,
        InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException;

    List<PretDTO> findByDatePret(Connexion connexion,
        Timestamp datePret) throws ServiceException,
        InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException;

    List<PretDTO> findByDateRetour(Connexion connexion,
        Timestamp dateRetour,
        String sortByPropertyName) throws ServiceException,
        InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException;

    //[endRegion] Fin opérations de recherche

    //[region] Commandes
    // REVOIR :(
    /**
     *
     * Suprimme le prêt ??
     *
     */
    void commencer(Connexion connexion,
        PretDTO pretDTO) throws ServiceException,
        InvalidHibernateSessionException,
        InvalidPrimaryKeyException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        InvalidDTOException,
        InvalidDTOClassException,
        InvalidPrimaryKeyRequestException;

    void retourner(Connexion connexion,
        PretDTO pretDTO) throws ServiceException,
        InvalidHibernateSessionException,
        InvalidPrimaryKeyException,
        InvalidDTOException,
        InvalidDTOClassException,
        InvalidCriterionException,
        InvalidSortByPropertyException;

    void renouveler(Connexion connexion,
        PretDTO pretDTO) throws ServiceException,
        InvalidHibernateSessionException,
        InvalidPrimaryKeyException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        InvalidDTOException,
        InvalidDTOClassException;
    //[endRegion] Fin commandes

}
