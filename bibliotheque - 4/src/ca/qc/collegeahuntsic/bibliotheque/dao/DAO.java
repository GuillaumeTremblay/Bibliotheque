// Fichier DAO.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliotheque.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.exception.DAOException;

/**
 * Classe de base pour tous les DAOs.
 *
 * @author Gilles Benichou
 */
public class DAO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Connexion connexion;

    /**
     * Crée un DAO à partir d'une connexion à la base de données.
     */
    public DAO(Connexion connexion) {
        super();
        setConnexion(connexion);
    }

    // Region Getters and Setters
    /**
     * Getter de la variable d'instance <code>this.connexion</code>.
     *
     * @return La variable d'instance <code>this.connexion</code>
     */
    private Connexion getConnexion() {
        return this.connexion;
    }

    /**
     * Setter de la variable d'instance <code>this.connexion</code>.
     *
     * @param connexion La valeur à utiliser pour la variable d'instance <code>this.connexion</code>
     */
    private void setConnexion(Connexion connexion) {
        this.connexion = connexion;
    }

    // EndRegion Getters and Setters

    /**
     * Retourne la {@link java.sql.Connection} JDBC.
     *
     * @return La {@link java.sql.Connection} JDBC
     */
    protected Connection getConnection() {
        return getConnexion().getConnection();
    }

    /**
     * Crée une nouvelle clef primaire.
     * 
     * @param createPrimaryKeyRequest La requête à utiliser pour générer la clef primaire
     * @return La nouvelle clef primaire
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    protected int getPrimaryKey(String createPrimaryKeyRequest) throws DAOException {
        try(
            PreparedStatement createPreparedStatement = getConnection().prepareStatement(createPrimaryKeyRequest)) {
            try(
                ResultSet resultSet = createPreparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    return resultSet.getInt(1);
                }
                throw new DAOException("Impossible de lire la séquence");
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }
}
