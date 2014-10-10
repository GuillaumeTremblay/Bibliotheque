// Fichier MembreDAO.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliotheque.dao.implementations;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IMembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.DTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyRequestException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOException;

/**
 * DAO pour effectuer des CRUDs avec la table <code>membre</code>.
 * 
 * @author Gilles Benichou
 */
public class MembreDAO extends DAO implements IMembreDAO {
    private static final String CREATE_PRIMARY_KEY = "SELECT SEQ_ID_MEMBRE.NEXTVAL "
        + "FROM DUAL";

    private static final String CREATE_REQUEST = "INSERT INTO membre (idMembre, nom, telephone, limitePret, nbPret) "
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

    private static final String FIND_BY_NOM = "SELECT idMembre, nom, telephone, limitePret, nbPret "
        + "FROM membre "
        + "WHERE LOWER(nom) like %?%";

    /**
     * Crée le DAO de la table <code>membre</code>.
     * 
     * @param membreDTOClass The classe de membre DTO to use
     * @throws InvalidDTOClassException Si la classe de DTO est <code>null</code>
     */
    public MembreDAO(Class<MembreDTO> membreDTOClass) throws InvalidDTOClassException { // TODO: Change to package when switching to Spring
        super(membreDTOClass);
    }

    /**
     * Crée une nouvelle clef primaire pour la table <code>membre</code>.
     * 
     * @param connexion La connexion à utiliser
     * @return La nouvelle clef primaire
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidPrimaryKeyRequestException Si la requête de la clef primaire du livre est <code>null</code>
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    private static String getPrimaryKey(Connexion connexion) throws InvalidHibernateSessionException,
        InvalidPrimaryKeyRequestException,
        DAOException {
        return DAO.getPrimaryKey(connexion,
            MembreDAO.CREATE_PRIMARY_KEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Connexion connexion,
        DTO dto) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        InvalidPrimaryKeyRequestException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(dto == null) {
            throw new InvalidDTOException("Le DTO ne peut être null");
        }
        if(!dto.getClass().equals(getDtoClass())) {
            throw new InvalidDTOClassException("Le DTO doit être un "
                + getDtoClass().getName());
        }
        MembreDTO membreDTO = (MembreDTO) dto;
        try(
            PreparedStatement createPreparedStatement = connexion.getConnection().prepareStatement(MembreDAO.CREATE_REQUEST)) {
            membreDTO.setIdMembre(MembreDAO.getPrimaryKey(connexion));
            createPreparedStatement.setString(1,
                membreDTO.getIdMembre());
            createPreparedStatement.setString(2,
                membreDTO.getNom());
            createPreparedStatement.setString(3,
                membreDTO.getTelephone());
            createPreparedStatement.setString(4,
                membreDTO.getLimitePret());
            createPreparedStatement.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MembreDTO get(Connexion connexion,
        Serializable primaryKey) throws InvalidHibernateSessionException,
        InvalidPrimaryKeyException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(primaryKey == null) {
            throw new InvalidPrimaryKeyException("La clef primaire ne peut être null");
        }
        String idMembre = (String) primaryKey;
        MembreDTO membreDTO = null;
        try(
            PreparedStatement readPreparedStatement = connexion.getConnection().prepareStatement(MembreDAO.READ_REQUEST)) {
            readPreparedStatement.setString(1,
                idMembre);
            try(
                ResultSet resultSet = readPreparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    membreDTO = new MembreDTO();
                    membreDTO.setIdMembre(resultSet.getString(1));
                    membreDTO.setNom(resultSet.getString(2));
                    membreDTO.setTelephone(resultSet.getString(3));
                    membreDTO.setLimitePret(resultSet.getString(4));
                    membreDTO.setNbPret(resultSet.getString(5));
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return membreDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Connexion connexion,
        DTO dto) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(dto == null) {
            throw new InvalidDTOException("Le DTO ne peut être null");
        }
        if(!dto.getClass().equals(getDtoClass())) {
            throw new InvalidDTOClassException("Le DTO doit être un "
                + getDtoClass().getName());
        }
        MembreDTO membreDTO = (MembreDTO) dto;
        try(
            PreparedStatement updatePreparedStatement = connexion.getConnection().prepareStatement(MembreDAO.UPDATE_REQUEST)) {
            updatePreparedStatement.setString(1,
                membreDTO.getIdMembre());
            updatePreparedStatement.setString(2,
                membreDTO.getNom());
            updatePreparedStatement.setString(3,
                membreDTO.getTelephone());
            updatePreparedStatement.setString(4,
                membreDTO.getLimitePret());
            updatePreparedStatement.setString(5,
                membreDTO.getNbPret());
            updatePreparedStatement.setString(6,
                membreDTO.getIdMembre());
            updatePreparedStatement.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Connexion connexion,
        DTO dto) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(dto == null) {
            throw new InvalidDTOException("Le DTO ne peut être null");
        }
        if(!dto.getClass().equals(getDtoClass())) {
            throw new InvalidDTOClassException("Le DTO doit être un "
                + getDtoClass().getName());
        }
        MembreDTO membreDTO = (MembreDTO) dto;
        try(
            PreparedStatement deletePreparedStatement = connexion.getConnection().prepareStatement(MembreDAO.DELETE_REQUEST)) {
            deletePreparedStatement.setString(1,
                membreDTO.getIdMembre());
            deletePreparedStatement.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MembreDTO> getAll(Connexion connexion,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(sortByPropertyName == null) {
            throw new InvalidSortByPropertyException("La propriété utilisée pour classer ne peut être null");
        }
        List<MembreDTO> membres = Collections.EMPTY_LIST;
        try(
            PreparedStatement getAll = connexion.getConnection().prepareStatement(MembreDAO.GET_ALL_REQUEST)) {
            try(
                ResultSet resultSet = getAll.executeQuery()) {
                MembreDTO membreDTO = null;
                if(resultSet.next()) {
                    membres = new ArrayList<>();
                    do {
                        membreDTO = new MembreDTO();
                        membreDTO.setIdMembre(resultSet.getString(1));
                        membreDTO.setNom(resultSet.getString(2));
                        membreDTO.setTelephone(resultSet.getString(3));
                        membreDTO.setLimitePret(resultSet.getString(4));
                        membreDTO.setNbPret(resultSet.getString(5));
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
     * {@inheritDoc}
     */
    @Override
    public List<MembreDTO> findByNom(Connexion connexion,
        String nom,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(nom == null) {
            throw new InvalidCriterionException("Le nom ne peut être null");
        }
        if(sortByPropertyName == null) {
            throw new InvalidSortByPropertyException("La propriété utilisée pour classer ne peut être null");
        }
        List<MembreDTO> membres = Collections.EMPTY_LIST;
        try(
            PreparedStatement findByNomPreparedStatement = connexion.getConnection().prepareStatement(MembreDAO.FIND_BY_NOM)) {
            findByNomPreparedStatement.setString(1,
                nom);
            try(
                ResultSet resultSet = findByNomPreparedStatement.executeQuery()) {
                MembreDTO membreDTO = null;
                if(resultSet.next()) {
                    membres = new ArrayList<>();
                    do {
                        membreDTO = new MembreDTO();
                        membreDTO.setIdMembre(resultSet.getString(1));
                        membreDTO.setNom(resultSet.getString(2));
                        membreDTO.setTelephone(resultSet.getString(3));
                        membreDTO.setLimitePret(resultSet.getString(4));
                        membreDTO.setNbPret(resultSet.getString(5));
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
