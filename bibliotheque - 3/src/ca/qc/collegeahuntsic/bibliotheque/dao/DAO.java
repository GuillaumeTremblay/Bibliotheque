// Fichier DAO.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliotheque.dao;

import java.io.Serializable;
import java.sql.Connection;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;

/**
 * Classe de base pour tous les DAOs.
 *
 * @author Chou Huynh
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

    /**
     * Retourne la {@link java.sql.Connection} JDBC.
     *
     * @return La {@link java.sql.Connection} JDBC
     */
    protected Connection getConnection() {
        return getConnexion().getConnection();
    }
}