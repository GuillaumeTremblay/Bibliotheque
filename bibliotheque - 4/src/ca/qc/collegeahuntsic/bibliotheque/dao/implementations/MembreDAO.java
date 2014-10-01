// Fichier MembreDAO.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliotheque.dao.implementations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.DAOException;

/**
 * DAO pour effectuer des CRUDs avec la table <code>membre</code>.
 * 
 * @author Gilles Benichou
 */
public class MembreDAO extends DAO {
    private static final long serialVersionUID = 1L;

    private static final String CREATE_PRIMARY_KEY = "SELECT SEQ_ID_MEMBRE.NEXTVAL "
        + "FROM DUAL";

    private static final String ADD_REQUEST = "INSERT INTO membre (idMembre, nom, telephone, limitePret, nbPret) "
        + "VALUES (? ,? ,? ,? , 0)";

    private static final String READ_REQUEST = "SELECT idMembre, nom, telephone, limitePret, nbPret "
        + "FROM membre "
        + "WHERE idMembre = ?";

    private static final String UPDATE_REQUEST = "UPDATE membre "
        + "SET idMembre = ?, nom = ?,  telephone = ?, limitePret = ?, nbPret = ? "
        + "WHERE idMembre = ?";

    private static final String DELETE_REQUEST = "DELETE FROM membre "
        + "WHERE idMembre = ?";

    private static final String GET_ALL_REQUEST = "SELECT idMembre, nom, telephone, limitePret, nbPret "
        + "FROM membre";

    /**
     * Crée un DAO à partir d'une connexion à la base de données.
     * 
     * @param connexion La connexion à utiliser
     */
    public MembreDAO(Connexion connexion) {
        super(connexion);
    }

    /**
     * Crée une nouvelle clef primaire.
     * 
     * @return La nouvelle clef primaire
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    private int getPrimaryKey() throws DAOException {
        return getPrimaryKey(MembreDAO.CREATE_PRIMARY_KEY);
    }

    /**
     * Ajoute un nouveau membre.
     * 
     * @param membreDTO Le membre à ajouter
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public void add(MembreDTO membreDTO) throws DAOException {
        try(
            PreparedStatement addPreparedStatement = getConnection().prepareStatement(MembreDAO.ADD_REQUEST)) {
            membreDTO.setIdMembre(getPrimaryKey());
            addPreparedStatement.setInt(1,
                membreDTO.getIdMembre());
            addPreparedStatement.setString(2,
                membreDTO.getNom());
            addPreparedStatement.setLong(3,
                membreDTO.getTelephone());
            addPreparedStatement.setInt(4,
                membreDTO.getLimitePret());
            addPreparedStatement.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

    /**
     * Lit un membre.
     * 
     * @param idMembre Le membre à lire
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public MembreDTO read(int idMembre) throws DAOException {
        MembreDTO membreDTO = null;
        try(
            PreparedStatement readPreparedStatement = getConnection().prepareStatement(MembreDAO.READ_REQUEST)) {
            readPreparedStatement.setInt(1,
                idMembre);
            try(
                ResultSet resultSet = readPreparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    membreDTO = new MembreDTO();
                    membreDTO.setIdMembre(resultSet.getInt(1));
                    membreDTO.setNom(resultSet.getString(2));
                    membreDTO.setTelephone(resultSet.getLong(3));
                    membreDTO.setLimitePret(resultSet.getInt(4));
                    membreDTO.setNbPret(resultSet.getInt(5));
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return membreDTO;
    }

    /**
     * Met à jour un membre.
     * 
     * @param membreDTO Le membre à mettre à jour
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public void update(MembreDTO membreDTO) throws DAOException {
        try(
            PreparedStatement updatePreparedStatement = getConnection().prepareStatement(MembreDAO.UPDATE_REQUEST)) {
            updatePreparedStatement.setInt(1,
                membreDTO.getIdMembre());
            updatePreparedStatement.setString(2,
                membreDTO.getNom());
            updatePreparedStatement.setLong(3,
                membreDTO.getTelephone());
            updatePreparedStatement.setInt(4,
                membreDTO.getLimitePret());
            updatePreparedStatement.setInt(5,
                membreDTO.getNbPret());
            updatePreparedStatement.setInt(6,
                membreDTO.getIdMembre());
            updatePreparedStatement.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

    /**
     * Supprime un membre.
     * 
     * @param membreDTO Le membre à supprimer
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public void delete(MembreDTO membreDTO) throws DAOException {
        try(
            PreparedStatement deletePreparedStatement = getConnection().prepareStatement(MembreDAO.DELETE_REQUEST)) {
            deletePreparedStatement.setInt(1,
                membreDTO.getIdMembre());
            deletePreparedStatement.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

    /**
     * Trouve tous les membres.
     * 
     * @return La liste des membres ; une liste vide sinon
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public List<MembreDTO> getAll() throws DAOException {
        List<MembreDTO> membres = Collections.EMPTY_LIST;
        try(
            PreparedStatement getAll = getConnection().prepareStatement(MembreDAO.GET_ALL_REQUEST)) {
            try(
                ResultSet resultSet = getAll.executeQuery()) {
                MembreDTO membreDTO = null;
                if(resultSet.next()) {
                    membres = new ArrayList<>();
                    do {
                        membreDTO = new MembreDTO();
                        membreDTO.setIdMembre(resultSet.getInt(1));
                        membreDTO.setNom(resultSet.getString(2));
                        membreDTO.setTelephone(resultSet.getLong(3));
                        membreDTO.setLimitePret(resultSet.getInt(4));
                        membreDTO.setNbPret(resultSet.getInt(5));
                        membres.add(membreDTO);
                    } while(resultSet.next());
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return membres;
    }
}
