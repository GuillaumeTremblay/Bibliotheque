// Fichier dao.java
// Auteur : Vincent
// Date de création : Sep 17, 2014

package ca.qc.collegeahuntsic.bibliotheque.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import ca.qc.collegeahuntsic.bibliotheque.DB.ConnexionDb;

/**
 * TODO Auto-generated class javadoc
 *
 * @author 
 */
public class DAO {

    /** The stmt existe. */
    protected PreparedStatement stmtExiste;

    /** The stmt insert. */
    protected PreparedStatement stmtInsert;

    /** The stmt update. */
    protected PreparedStatement stmtUpdate;

    /** The stmt delete. */
    protected PreparedStatement stmtDelete;

    /** The cx. */
    protected ConnexionDb cx;

    /** The stmt update incr nb pret. */
    protected PreparedStatement stmtUpdateIncrNbPret;

    /** The stmt update dec nb pret. */
    protected PreparedStatement stmtUpdateDecNbPret;

    /** The stmt existe livre. */
    protected PreparedStatement stmtExisteLivre;

    /** The stmt existe membre. */
    protected PreparedStatement stmtExisteMembre;

    /**
     * Creation d'une instance. Des énoncés SQL pour chaque requête sont précompilés.
     *
     * @param cx the cx
     * @throws SQLException the SQL exception
     */

    @SuppressWarnings("unused")
    private void setConnexion(ConnexionDb connexion) {
        this.cx = connexion;
    }

    public ConnexionDb getConnexion() {

        return this.cx;
    }

    /**
     * Verifie si une reservation existe.
     *
     * @param idReservation the id reservation
     * @return true, if successful
     * @throws SQLException the SQL exception
     */
    //rien a ajouter
}
