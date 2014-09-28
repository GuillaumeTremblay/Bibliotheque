
package ca.qc.collegeahuntsic.bibliotheque.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.DAOException;

// TODO: Auto-generated Javadoc
/**
 * Permet d'effectuer les accès à la table membre.
 * Cette classe gère tous les accès à la table membre.
 *
 *</pre>
 */

public class MembreDAO extends DAO {
    /**
     * TODO Auto-generated field javadoc
     */
    private static final long serialVersionUID = 1L;

    private static final String ADD_REQUEST = "INSERT INTO membre (idMembre, nom, telephone, limitePret, nbpret) "
        + "VALUES (?, ?, ?, ?,0)";

    private static final String READ_REQUEST = "SELECT idMembre, nom, telephone, limitePret, nbpret"
        + "FROM membre "
        + "WHERE idMembre = ?";

    private static final String UPDATE_REQUEST = "UPDATE livre "
        + "SET nom = ?, telephone = ?, limitePret = ?, nbpret = ?"
        + "WHERE idMembre = ?";

    private static final String DELETE_REQUEST = "DELETE FROM membre "
        + "WHERE idMembre = ?";

    private static final String GET_ALL_REQUEST = "SELECT idMembre, nom, telephone, limitePret, nbpret "
        + "FROM membre";

    private static final String FIND_BY_NAME = "SELECT idMembre, nom, telephone, limitePret, nbpret "
        + "FROM membre "
        + "WHERE LOWER(nom) like %?%";

    private static final String FIND_BY_PHONE = "SELECT idMembre, nom, telephone, limitePret, nbpret "
        + "FROM membre "
        + "WHERE telephone = ?";

    /**
     * Crée un DAO à partir d'une connexion à la base de données.
     *
     * @param connexion La connexion à utiliser
     */
    public MembreDAO(Connexion connexion) {
        super(connexion);
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
     * @param idMembre L'ID du membre à lire
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public MembreDTO read(MembreDTO membreDTO) throws DAOException {
        MembreDTO unMembreDTO = null;
        try(
            PreparedStatement readPreparedStatement = getConnection().prepareStatement(MembreDAO.READ_REQUEST)) {
            readPreparedStatement.setInt(1,
                membreDTO.getIdMembre());
            try(
                ResultSet resultSet = readPreparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    unMembreDTO = new MembreDTO();
                    unMembreDTO.setIdMembre(resultSet.getInt(1));
                    unMembreDTO.setNom(resultSet.getString(2));
                    unMembreDTO.setTelephone(resultSet.getLong(3));
                    unMembreDTO.setLimitePret(resultSet.getInt(4));
                    unMembreDTO.setNbPret(resultSet.getInt(5));
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return unMembreDTO;
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
            updatePreparedStatement.setString(1,
                membreDTO.getNom());
            updatePreparedStatement.setLong(2,
                membreDTO.getTelephone());
            updatePreparedStatement.setInt(3,
                membreDTO.getLimitePret());
            updatePreparedStatement.setInt(4,
                membreDTO.getNbPret());
            updatePreparedStatement.setInt(4,
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
            PreparedStatement getAllPreparedStatement = getConnection().prepareStatement(MembreDAO.GET_ALL_REQUEST)) {
            try(
                ResultSet resultSet = getAllPreparedStatement.executeQuery()) {
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

    /**
     * Trouve les membres à partir d'un nom.
     *
     * @param nom Le nom à utiliser
     * @return La liste des membres correspondants ; une liste vide sinon
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public List<MembreDTO> findByNom(String nom) throws DAOException {
        List<MembreDTO> membres = Collections.EMPTY_LIST;
        try(
            PreparedStatement findByNomPreparedStatement = getConnection().prepareStatement(MembreDAO.FIND_BY_NAME)) {
            findByNomPreparedStatement.setString(1,
                nom);
            try(
                ResultSet resultSet = findByNomPreparedStatement.executeQuery()) {
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

    /**
     * Trouve les membres à partir d'un telephone.
     *
     * @param tel Le telephone à utiliser
     * @return La liste des membres correspondants ; une liste vide sinon
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public MembreDTO findByTel(long tel) throws DAOException {
        MembreDTO membreDTO = null;
        try(
            PreparedStatement findByTelPreparedStatement = getConnection().prepareStatement(MembreDAO.FIND_BY_PHONE)) {
            findByTelPreparedStatement.setLong(1,
                tel);
            try(
                ResultSet resultSet = findByTelPreparedStatement.executeQuery()) {
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

    //    /**
    //     * Emprunte un livre.
    //     *
    //     * @param membreDTO Le membre à mettre à jour
    //     * @throws DAOException S'il y a une erreur avec la base de données
    //     */
    //    public void emprunter(MembreDTO membreDTO) throws DAOException {
    //        membreDTO.setNbPret(membreDTO.getNbPret() + 1);
    //        update(membreDTO);
    //    }
    //
    //    /**
    //     * Retourne un livre.
    //     *
    //     * @param membreDTO Le membre à mettre à jour
    //     * @throws DAOException S'il y a une erreur avec la base de données
    //     */
    //    public void retourner(MembreDTO membreDTO) throws DAOException {
    //        membreDTO.setNbPret(membreDTO.getNbPret() - 1);
    //        update(membreDTO);
    //    }
}
