// Fichier LivreDAO.java
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
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.ILivreDAO;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.DTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyRequestException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOException;

/**
 * DAO pour effectuer des CRUDs avec la table <code>livre</code>.
 * 
 * @author Gilles Benichou
 */
public class LivreDAO extends DAO implements ILivreDAO {
    private static final String CREATE_PRIMARY_KEY = "SELECT SEQ_ID_LIVRE.NEXTVAL "
        + "FROM DUAL";

    private static final String CREATE_REQUEST = "INSERT INTO livre (idLivre, titre, auteur, dateAcquisition) "
        + "VALUES (?, ?, ?, ?)";

    private static final String READ_REQUEST = "SELECT idLivre, titre, auteur, dateAcquisition "
        + "FROM livre "
        + "WHERE idLivre = ?";

    private static final String UPDATE_REQUEST = "UPDATE livre "
        + "SET titre = ?, auteur = ?, dateAcquisition = ? "
        + "WHERE idLivre = ?";

    private static final String DELETE_REQUEST = "DELETE FROM livre "
        + "WHERE idLivre = ?";

    private static final String GET_ALL_REQUEST = "SELECT idLivre, titre, auteur, dateAcquisition "
        + "FROM livre";

    private static final String FIND_BY_TITRE = "SELECT idLivre, titre, auteur, TdateAcquisition "
        + "FROM livre "
        + "WHERE LOWER(titre) like %?%";

    /**
     * Crée le DAO de la table <code>livre</code>.
     * 
     * @param livreDTOClass The classe de livre DTO to use
     * @throws InvalidDTOClassException Si la classe de DTO est <code>null</code>
     */
    public LivreDAO(Class<LivreDTO> livreDTOClass) throws InvalidDTOClassException { // TODO: Change to package when switching to Spring
        super(livreDTOClass);
    }

    /**
     * Crée une nouvelle clef primaire pour la table <code>livre</code>.
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
            LivreDAO.CREATE_PRIMARY_KEY);
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
        LivreDTO livreDTO = (LivreDTO) dto;
        try(
            PreparedStatement createPreparedStatement = connexion.getConnection().prepareStatement(LivreDAO.CREATE_REQUEST)) {
            livreDTO.setIdLivre(LivreDAO.getPrimaryKey(connexion));
            createPreparedStatement.setString(1,
                livreDTO.getIdLivre());
            createPreparedStatement.setString(2,
                livreDTO.getTitre());
            createPreparedStatement.setString(3,
                livreDTO.getAuteur());
            createPreparedStatement.setTimestamp(4,
                livreDTO.getDateAcquisition());
            createPreparedStatement.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LivreDTO get(Connexion connexion,
        Serializable primaryKey) throws InvalidHibernateSessionException,
        InvalidPrimaryKeyException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(primaryKey == null) {
            throw new InvalidPrimaryKeyException("La clef primaire ne peut être null");
        }
        String idLivre = (String) primaryKey;
        LivreDTO livreDTO = null;
        try(
            PreparedStatement readPreparedStatement = connexion.getConnection().prepareStatement(LivreDAO.READ_REQUEST)) {
            readPreparedStatement.setString(1,
                idLivre);
            try(
                ResultSet resultSet = readPreparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    livreDTO = new LivreDTO();
                    livreDTO.setIdLivre(resultSet.getString(1));
                    livreDTO.setTitre(resultSet.getString(2));
                    livreDTO.setAuteur(resultSet.getString(3));
                    livreDTO.setDateAcquisition(resultSet.getTimestamp(4));
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return livreDTO;
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
        LivreDTO livreDTO = (LivreDTO) dto;
        try(
            PreparedStatement updatePreparedStatement = connexion.getConnection().prepareStatement(LivreDAO.UPDATE_REQUEST)) {
            updatePreparedStatement.setString(1,
                livreDTO.getTitre());
            updatePreparedStatement.setString(2,
                livreDTO.getAuteur());
            updatePreparedStatement.setTimestamp(3,
                livreDTO.getDateAcquisition());
            updatePreparedStatement.setString(4,
                livreDTO.getIdLivre());
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
        LivreDTO livreDTO = (LivreDTO) dto;
        try(
            PreparedStatement deletePreparedStatement = connexion.getConnection().prepareStatement(LivreDAO.DELETE_REQUEST)) {
            deletePreparedStatement.setString(1,
                livreDTO.getIdLivre());
            deletePreparedStatement.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LivreDTO> getAll(Connexion connexion,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(sortByPropertyName == null) {
            throw new InvalidSortByPropertyException("La propriété utilisée pour classer ne peut être null");
        }
        List<LivreDTO> livres = Collections.EMPTY_LIST;
        try(
            PreparedStatement getAllPreparedStatement = connexion.getConnection().prepareStatement(LivreDAO.GET_ALL_REQUEST)) {
            try(
                ResultSet resultSet = getAllPreparedStatement.executeQuery()) {
                LivreDTO livreDTO = null;
                if(resultSet.next()) {
                    livres = new ArrayList<>();
                    do {
                        livreDTO = new LivreDTO();
                        livreDTO.setIdLivre(resultSet.getString(1));
                        livreDTO.setTitre(resultSet.getString(2));
                        livreDTO.setAuteur(resultSet.getString(3));
                        livreDTO.setDateAcquisition(resultSet.getTimestamp(4));
                        livres.add(livreDTO);
                    } while(resultSet.next());
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return livres;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LivreDTO> findByTitre(Connexion connexion,
        String titre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(titre == null) {
            throw new InvalidCriterionException("Le titre ne peut être null");
        }
        if(sortByPropertyName == null) {
            throw new InvalidSortByPropertyException("La propriété utilisée pour classer ne peut être null");
        }
        List<LivreDTO> livres = Collections.EMPTY_LIST;
        try(
            PreparedStatement findByTitrePreparedStatement = connexion.getConnection().prepareStatement(LivreDAO.FIND_BY_TITRE)) {
            findByTitrePreparedStatement.setString(1,
                titre);
            try(
                ResultSet resultSet = findByTitrePreparedStatement.executeQuery()) {
                LivreDTO livreDTO = null;
                if(resultSet.next()) {
                    livres = new ArrayList<>();
                    do {
                        livreDTO = new LivreDTO();
                        livreDTO.setIdLivre(resultSet.getString(1));
                        livreDTO.setTitre(resultSet.getString(2));
                        livreDTO.setAuteur(resultSet.getString(3));
                        livreDTO.setDateAcquisition(resultSet.getTimestamp(4));
                        livres.add(livreDTO);
                    } while(resultSet.next());
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return livres;
    }
}
