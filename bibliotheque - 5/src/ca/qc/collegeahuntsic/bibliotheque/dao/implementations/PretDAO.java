// Fichier PretDAO.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-26

package ca.qc.collegeahuntsic.bibliotheque.dao.implementations;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IPretDAO;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.DTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyRequestException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOException;

/**
 * DAO pour effectuer des CRUDs avec la table <code>pret</code>.
 * 
 * @author Gilles Benichou
 */
public class PretDAO extends DAO implements IPretDAO {
    private static final String CREATE_PRIMARY_KEY = "SELECT SEQ_ID_PRET.NEXTVAL "
        + "FROM DUAL";

    private static final String CREATE_REQUEST = "INSERT INTO pret (idPret, idMembre, idLivre, datePret, dateRetour) "
        + "VALUES (?, ?, ?, ?, ?)";

    private static final String READ_REQUEST = "SELECT idPret, idMembre, idLivre, datePret, dateRetour "
        + "FROM pret "
        + "WHERE idPret = ?";

    private static final String UPDATE_REQUEST = "UPDATE pret "
        + "SET idMembre = ?, idLivre = ?, datePret = ?, dateRetour = ? "
        + "WHERE idPret = ?";

    private static final String DELETE_REQUEST = "DELETE FROM pret "
        + "WHERE idPret = ?";

    private static final String GET_ALL_REQUEST = "SELECT idPret, idMembre, idLivre, datePret, dateRetour "
        + "FROM pret";

    private static final String FIND_BY_MEMBRE = "SELECT idPret, idMembre, idLivre, datePret, dateRetour "
        + "FROM pret "
        + "WHERE idMembre = ? "
        + "AND dateRetour IS NULL "
        + "ORDER BY datePret ASC";

    private static final String FIND_BY_LIVRE = "SELECT idPret, idMembre, idLivre, datePret, dateRetour "
        + "FROM pret "
        + "WHERE idLivre = ? "
        + "AND dateRetour IS NULL "
        + "ORDER BY datePret ASC";

    private static final String FIND_BY_DATE_PRET = "SELECT idPret, idMembre, idLivre, datePret, dateRetour "
        + "FROM pret "
        + "WHERE datePret = ?";

    private static final String FIND_BY_DATE_RETOUR = "SELECT idPret, idMembre, idLivre, datePret, dateRetour "
        + "FROM pret "
        + "WHERE dateRetour = ?";

    /**
     * Crée le DAO de la table <code>pret</code>.
     * 
     * @param pretDTOClass The classe de prêt DTO to use
     * @throws InvalidDTOClassException Si la classe de DTO est <code>null</code>
     */
    public PretDAO(Class<PretDTO> pretDTOClass) throws InvalidDTOClassException { // TODO: Change to package when switching to Spring
        super(pretDTOClass);
    }

    /**
     * Crée une nouvelle clef primaire pour la table <code>pret</code>.
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
            PretDAO.CREATE_PRIMARY_KEY);
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
        PretDTO pretDTO = (PretDTO) dto;
        try(
            PreparedStatement createPreparedStatement = connexion.getConnection().prepareStatement(PretDAO.CREATE_REQUEST)) {
            pretDTO.setIdPret(PretDAO.getPrimaryKey(connexion));
            createPreparedStatement.setString(1,
                pretDTO.getIdPret());
            createPreparedStatement.setString(2,
                pretDTO.getMembreDTO().getIdMembre());
            createPreparedStatement.setString(3,
                pretDTO.getLivreDTO().getIdLivre());
            createPreparedStatement.setTimestamp(4,
                pretDTO.getDatePret());
            createPreparedStatement.setTimestamp(5,
                pretDTO.getDateRetour());
            createPreparedStatement.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PretDTO get(Connexion connexion,
        Serializable primaryKey) throws InvalidHibernateSessionException,
        InvalidPrimaryKeyException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(primaryKey == null) {
            throw new InvalidPrimaryKeyException("La clef primaire ne peut être null");
        }
        String idPret = (String) primaryKey;
        PretDTO pretDTO = null;
        try(
            PreparedStatement readPreparedStatement = connexion.getConnection().prepareStatement(PretDAO.READ_REQUEST)) {
            readPreparedStatement.setString(1,
                idPret);
            try(
                ResultSet resultSet = readPreparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    pretDTO = new PretDTO();
                    pretDTO.setIdPret(resultSet.getString(1));
                    MembreDTO membreDTO = new MembreDTO();
                    membreDTO.setIdMembre(resultSet.getString(2));
                    pretDTO.setMembreDTO(membreDTO);
                    LivreDTO livreDTO = new LivreDTO();
                    livreDTO.setIdLivre(resultSet.getString(3));
                    pretDTO.setLivreDTO(livreDTO);
                    pretDTO.setDatePret(resultSet.getTimestamp(4));
                    pretDTO.setDateRetour(resultSet.getTimestamp(5));
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return pretDTO;
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
        PretDTO pretDTO = (PretDTO) dto;
        try(
            PreparedStatement updatePreparedStatement = connexion.getConnection().prepareStatement(PretDAO.UPDATE_REQUEST)) {
            updatePreparedStatement.setString(1,
                pretDTO.getMembreDTO().getIdMembre());
            updatePreparedStatement.setString(2,
                pretDTO.getLivreDTO().getIdLivre());
            updatePreparedStatement.setTimestamp(3,
                pretDTO.getDatePret());
            updatePreparedStatement.setTimestamp(4,
                pretDTO.getDateRetour());
            updatePreparedStatement.setString(5,
                pretDTO.getIdPret());
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
        PretDTO pretDTO = (PretDTO) dto;
        try(
            PreparedStatement deletePreparedStatement = connexion.getConnection().prepareStatement(PretDAO.DELETE_REQUEST)) {
            deletePreparedStatement.setString(1,
                pretDTO.getIdPret());
            deletePreparedStatement.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PretDTO> getAll(Connexion connexion,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(sortByPropertyName == null) {
            throw new InvalidSortByPropertyException("La propriété utilisée pour classer ne peut être null");
        }
        List<PretDTO> prets = Collections.EMPTY_LIST;
        try(
            PreparedStatement getAllPreparedStatement = connexion.getConnection().prepareStatement(PretDAO.GET_ALL_REQUEST)) {
            try(
                ResultSet resultSet = getAllPreparedStatement.executeQuery()) {
                PretDTO pretDTO = null;
                if(resultSet.next()) {
                    prets = new ArrayList<>();
                    do {
                        pretDTO = new PretDTO();
                        pretDTO.setIdPret(resultSet.getString(1));
                        MembreDTO membreDTO = new MembreDTO();
                        membreDTO.setIdMembre(resultSet.getString(2));
                        pretDTO.setMembreDTO(membreDTO);
                        LivreDTO livreDTO = new LivreDTO();
                        livreDTO.setIdLivre(resultSet.getString(3));
                        pretDTO.setLivreDTO(livreDTO);
                        pretDTO.setDatePret(resultSet.getTimestamp(4));
                        pretDTO.setDateRetour(resultSet.getTimestamp(5));
                        prets.add(pretDTO);
                    } while(resultSet.next());
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return prets;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PretDTO> findByMembre(Connexion connexion,
        String idMembre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(idMembre == null) {
            throw new InvalidCriterionException("L'ID du membre ne peut être null");
        }
        if(sortByPropertyName == null) {
            throw new InvalidSortByPropertyException("La propriété utilisée pour classer ne peut être null");
        }
        List<PretDTO> prets = Collections.EMPTY_LIST;
        try(
            PreparedStatement findByMembrePreparedStatement = connexion.getConnection().prepareStatement(PretDAO.FIND_BY_MEMBRE)) {
            findByMembrePreparedStatement.setString(1,
                idMembre);
            try(
                ResultSet resultSet = findByMembrePreparedStatement.executeQuery()) {
                PretDTO pretDTO = null;
                if(resultSet.next()) {
                    prets = new ArrayList<>();
                    do {
                        pretDTO = new PretDTO();
                        pretDTO.setIdPret(resultSet.getString(1));
                        MembreDTO membreDTO = new MembreDTO();
                        membreDTO.setIdMembre(resultSet.getString(2));
                        pretDTO.setMembreDTO(membreDTO);
                        LivreDTO livreDTO = new LivreDTO();
                        livreDTO.setIdLivre(resultSet.getString(3));
                        pretDTO.setLivreDTO(livreDTO);
                        pretDTO.setDatePret(resultSet.getTimestamp(4));
                        pretDTO.setDateRetour(resultSet.getTimestamp(5));
                        prets.add(pretDTO);
                    } while(resultSet.next());
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return prets;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PretDTO> findByLivre(Connexion connexion,
        String idLivre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(idLivre == null) {
            throw new InvalidCriterionException("L'ID du livre ne peut être null");
        }
        if(sortByPropertyName == null) {
            throw new InvalidSortByPropertyException("La propriété utilisée pour classer ne peut être null");
        }
        List<PretDTO> prets = Collections.EMPTY_LIST;
        try(
            PreparedStatement findByLivrePreparedStatement = connexion.getConnection().prepareStatement(PretDAO.FIND_BY_LIVRE)) {
            findByLivrePreparedStatement.setString(1,
                idLivre);
            try(
                ResultSet resultSet = findByLivrePreparedStatement.executeQuery()) {
                PretDTO pretDTO = null;
                if(resultSet.next()) {
                    prets = new ArrayList<>();
                    do {
                        pretDTO = new PretDTO();
                        pretDTO.setIdPret(resultSet.getString(1));
                        MembreDTO membreDTO = new MembreDTO();
                        membreDTO.setIdMembre(resultSet.getString(2));
                        pretDTO.setMembreDTO(membreDTO);
                        LivreDTO livreDTO = new LivreDTO();
                        livreDTO.setIdLivre(resultSet.getString(3));
                        pretDTO.setLivreDTO(livreDTO);
                        pretDTO.setDatePret(resultSet.getTimestamp(4));
                        pretDTO.setDateRetour(resultSet.getTimestamp(5));
                        prets.add(pretDTO);
                    } while(resultSet.next());
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return prets;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PretDTO> findByDatePret(Connexion connexion,
        Timestamp datePret,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(datePret == null) {
            throw new InvalidCriterionException("La date de prêt ne peut être null");
        }
        if(sortByPropertyName == null) {
            throw new InvalidSortByPropertyException("La propriété utilisée pour classer ne peut être null");
        }
        List<PretDTO> prets = Collections.EMPTY_LIST;
        try(
            PreparedStatement findByDatePretPreparedStatement = connexion.getConnection().prepareStatement(PretDAO.FIND_BY_DATE_PRET)) {
            findByDatePretPreparedStatement.setTimestamp(1,
                datePret);
            try(
                ResultSet resultSet = findByDatePretPreparedStatement.executeQuery()) {
                PretDTO pretDTO = null;
                if(resultSet.next()) {
                    prets = new ArrayList<>();
                    do {
                        pretDTO = new PretDTO();
                        pretDTO.setIdPret(resultSet.getString(1));
                        MembreDTO membreDTO = new MembreDTO();
                        membreDTO.setIdMembre(resultSet.getString(2));
                        pretDTO.setMembreDTO(membreDTO);
                        LivreDTO livreDTO = new LivreDTO();
                        livreDTO.setIdLivre(resultSet.getString(3));
                        pretDTO.setLivreDTO(livreDTO);
                        pretDTO.setDatePret(resultSet.getTimestamp(4));
                        pretDTO.setDateRetour(resultSet.getTimestamp(5));
                        prets.add(pretDTO);
                    } while(resultSet.next());
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return prets;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PretDTO> findByDateRetour(Connexion connexion,
        Timestamp dateRetour,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(dateRetour == null) {
            throw new InvalidCriterionException("La date de retour ne peut être null");
        }
        if(sortByPropertyName == null) {
            throw new InvalidSortByPropertyException("La propriété utilisée pour classer ne peut être null");
        }
        List<PretDTO> prets = Collections.EMPTY_LIST;
        try(
            PreparedStatement findByDateRetourPreparedStatement = connexion.getConnection().prepareStatement(PretDAO.FIND_BY_DATE_RETOUR)) {
            findByDateRetourPreparedStatement.setTimestamp(1,
                dateRetour);
            try(
                ResultSet resultSet = findByDateRetourPreparedStatement.executeQuery()) {
                PretDTO pretDTO = null;
                if(resultSet.next()) {
                    prets = new ArrayList<>();
                    do {
                        pretDTO = new PretDTO();
                        pretDTO.setIdPret(resultSet.getString(1));
                        MembreDTO membreDTO = new MembreDTO();
                        membreDTO.setIdMembre(resultSet.getString(2));
                        pretDTO.setMembreDTO(membreDTO);
                        LivreDTO livreDTO = new LivreDTO();
                        livreDTO.setIdLivre(resultSet.getString(3));
                        pretDTO.setLivreDTO(livreDTO);
                        pretDTO.setDatePret(resultSet.getTimestamp(4));
                        pretDTO.setDateRetour(resultSet.getTimestamp(5));
                        prets.add(pretDTO);
                    } while(resultSet.next());
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return prets;
    }
}
