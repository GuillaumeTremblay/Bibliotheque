// Fichier DAO.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliotheque.dao.implementations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyRequestException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;

/**
 * Classe de base pour tous les DAOs.<br />
 * Tous les DAOs devrait en hériter.
 *
 * @author Gilles Benichou
 */
public class DAO {
    private Class<?> dtoClass;

    /**
     * Crée un DAO.
     * 
     * @param dtoClass La classe de DTO à utiliser
     * @throws InvalidDTOClassException Si la classe de DTO est <code>null</code>
     */
    protected DAO(Class<?> dtoClass) throws InvalidDTOClassException {
        super();
        if(dtoClass == null) {
            throw new InvalidDTOClassException("La classe de DTO ne peut être null");
        }
        setDtoClass(dtoClass);
    }

    // Region Getters and Setters
    /**
     * Getter de la variable d'instance <code>this.dtoClass</code>.
     *
     * @return La variable d'instance <code>this.dtoClass</code>
     */
    protected Class<?> getDtoClass() {
        return this.dtoClass;
    }

    /**
     * Setter de la variable d'instance <code>this.dtoClass</code>.
     *
     * @param dtoClass La valeur à utiliser pour la variable d'instance <code>this.dtoClass</code>
     */
    private void setDtoClass(Class<?> dtoClass) {
        this.dtoClass = dtoClass;
    }

    // EndRegion Getters and Setters

    /**
     * Crée une nouvelle clef primaire.
     * 
     * @param connexion La connexion à utiliser
     * @param createPrimaryKeyRequest La requête à utiliser pour générer la clef primaire
     * @return La nouvelle clef primaire
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidPrimaryKeyRequestException Si la requête de la clef primaire du DTO est <code>null</code>
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    protected static String getPrimaryKey(Connexion connexion,
        String createPrimaryKeyRequest) throws InvalidHibernateSessionException,
        InvalidPrimaryKeyRequestException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(createPrimaryKeyRequest == null) {
            throw new InvalidPrimaryKeyRequestException("La requête de création de clef primaire ne peut être null");
        }
        try(
            PreparedStatement createPreparedStatement = connexion.getConnection().prepareStatement(createPrimaryKeyRequest)) {
            try(
                ResultSet resultSet = createPreparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    return resultSet.getString(1);
                }
                throw new DAOException("Impossible de lire la séquence");
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }
}
