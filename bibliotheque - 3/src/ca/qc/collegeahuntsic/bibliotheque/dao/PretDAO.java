
package ca.qc.collegeahuntsic.bibliotheque.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.DAOException;

public class PretDAO extends DAO {

    public PretDAO(Connexion connexion) {
        super(connexion);
    }

    // constantes contenant les chaînes
    private static final String ADD_REQUEST = "INSERT INTO pret (idPret, idMembre, idLivre, datePret, dateRetour "
        + "VALUES (?, ?, ?, ?, ?)";

    private static final String READ_REQUEST = "SELECT idPret, idMembre, idLivre, datePret, dateRetour "
        + "FROM pret "
        + "WHERE idPret = ?";

    private static final String UPDATE_REQUEST = "UPDATE pret "
        + "SET idPret = ?, idMembre = ?, idLivre = ?, datePret = ?, dateRetour = ? "
        + "WHERE idPret = ?";

    private static final String DELETE_REQUEST = "DELETE FROM pret "
        + "WHERE idPret = ?";

    private static final String GET_ALL_REQUEST = "SELECT idPret, idMembre, idLivre, datePret, dateRetour "
        + "FROM pret";

    private static final String FIND_BY_MEMBRE = "SELECT idPret, idMembre, idLivre, datePret, dateRetour "
        + "FROM pret "
        + "WHERE idMembre = ?";

    private static final String FIND_BY_LIVRE = "SELECT idPret, idMembre, idLivre, datePret, dateRetour "
        + "FROM pret "
        + "WHERE idLivre = ?";

    /**
     * Crée un nouveau prêt avec l'objet passé en paramètre
     *
     * @param pretDTO
     * @throws DAOException
     */
    public void add(PretDTO pretDTO) throws DAOException {
        try(
            PreparedStatement addPreparedStatement = getConnection().prepareStatement(PretDAO.ADD_REQUEST)) {
            // configuration de la requête paramètrée
            addPreparedStatement.setInt(1,
                pretDTO.getIdPret());
            addPreparedStatement.setInt(2,
                pretDTO.getMembreDTO().getIdMembre());
            addPreparedStatement.setInt(3,
                pretDTO.getLivreDTO().getIdLivre());
            addPreparedStatement.setTimestamp(4,
                pretDTO.getDatePret());
            addPreparedStatement.setTimestamp(5,
                pretDTO.getDateRetour());

            addPreparedStatement.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

    /**
     * Retourne un objet de prêt qui possède l'ID passé en paramètre
     *
     * @param idPret
     * @return PretDTO pretDTO
     * @throws DAOException
     */
    public PretDTO read(PretDTO pretDTO) throws DAOException {
        PretDTO unPretDTO = null;
        try(
            PreparedStatement readPreparedStatement = getConnection().prepareStatement(PretDAO.READ_REQUEST)) {
            readPreparedStatement.setInt(1,
                pretDTO.getIdPret());
            try(
                ResultSet resultat = readPreparedStatement.executeQuery()) {
                if(resultat.next()) {
                    // configuration de l'objet pretDTO avec le résultat de la requête
                    unPretDTO = new PretDTO();
                    unPretDTO.setIdPret(resultat.getInt(1));
                    MembreDTO membreDTO = new MembreDTO();
                    membreDTO.setIdMembre(resultat.getInt(2));
                    unPretDTO.setMembreDTO(membreDTO);
                    LivreDTO livreDTO = new LivreDTO();
                    livreDTO.setIdLivre(resultat.getInt(3));
                    unPretDTO.setLivreDTO(livreDTO);
                    unPretDTO.setDatePret(resultat.getTimestamp(4));
                    unPretDTO.setDateRetour(resultat.getTimestamp(5));
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }

        return unPretDTO;
    }

    /**
     * Modifie un prêt avec les valeurs de l'objet passé en paramètre
     *
     * @param pretDTO
     * @throws DAOException
     */
    public void update(PretDTO pretDTO) throws DAOException {

        try(
            PreparedStatement updatePreparedStatement = getConnection().prepareStatement(PretDAO.UPDATE_REQUEST)) {

            // configuration de la requête paramètrée
            updatePreparedStatement.setInt(1,
                pretDTO.getIdPret());
            updatePreparedStatement.setInt(2,
                pretDTO.getMembreDTO().getIdMembre());
            updatePreparedStatement.setInt(3,
                pretDTO.getLivreDTO().getIdLivre());
            updatePreparedStatement.setTimestamp(4,
                pretDTO.getDatePret());
            updatePreparedStatement.setTimestamp(5,
                pretDTO.getDateRetour());

            updatePreparedStatement.executeUpdate();

        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }

    }

    /**
     * Efface un prêt dont l'ID est spécifié par l'objet passé en paramètre
     *
     * @param pretDTO
     * @throws DAOException
     */
    public void delete(PretDTO pretDTO) throws DAOException {

        try(
            PreparedStatement deletePreparedStatement = getConnection().prepareStatement(PretDAO.DELETE_REQUEST)) {

            deletePreparedStatement.setInt(1,
                pretDTO.getIdPret());
            deletePreparedStatement.executeUpdate();

        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }

    }

    /**
     * Renvoie une liste de tous les prêts sous forme d'objet PretDTO
     *
     * @return
     */
    public List<PretDTO> getAll() throws DAOException {
        List<PretDTO> prets = Collections.EMPTY_LIST;
        try(
            PreparedStatement getAllPreparedStatement = getConnection().prepareStatement(PretDAO.GET_ALL_REQUEST)) {
            try(
                ResultSet resultSet = getAllPreparedStatement.executeQuery()) {
                PretDTO pretDTO = null;
                if(resultSet != null) {
                    prets = new ArrayList<>();
                    while(resultSet.next()) {
                        // configuration de l'objet pretDTO avec le résultat de la requête
                        pretDTO = new PretDTO();
                        pretDTO.setIdPret(resultSet.getInt(1));
                        MembreDTO membreDTO = new MembreDTO();
                        membreDTO.setIdMembre(resultSet.getInt(2));
                        pretDTO.setMembreDTO(membreDTO);
                        LivreDTO livreDTO = new LivreDTO();
                        livreDTO.setIdLivre(resultSet.getInt(3));
                        pretDTO.setLivreDTO(livreDTO);
                        pretDTO.setDatePret(resultSet.getTimestamp(4));
                        pretDTO.setDateRetour(resultSet.getTimestamp(5));
                        prets.add(pretDTO);
                    }
                }
            }

        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return prets;
    }

    public PretDTO findByLivre(LivreDTO livreDTO) throws DAOException {
        PretDTO pretDTO = null;
        try(
            PreparedStatement findByMembrePreparedStatement = getConnection().prepareStatement(PretDAO.FIND_BY_LIVRE)) {
            findByMembrePreparedStatement.setInt(1,
                livreDTO.getIdLivre());
            try(
                ResultSet resultSet = findByMembrePreparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    pretDTO = new PretDTO();
                    pretDTO.setIdPret(resultSet.getInt(1));
                    MembreDTO unMembreDTO = new MembreDTO();
                    unMembreDTO.setIdMembre(resultSet.getInt(2));
                    pretDTO.setMembreDTO(unMembreDTO);
                    LivreDTO unLivreDTO = new LivreDTO();
                    unLivreDTO.setIdLivre(resultSet.getInt(3));
                    pretDTO.setLivreDTO(unLivreDTO);
                    pretDTO.setDatePret(resultSet.getTimestamp(4));
                    pretDTO.setDateRetour(resultSet.getTimestamp(5));
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return pretDTO;
    }

    public PretDTO findByMembre(MembreDTO membreDTO) throws DAOException {
        PretDTO pretDTO = null;
        try(
            PreparedStatement findByMembrePreparedStatement = getConnection().prepareStatement(PretDAO.FIND_BY_MEMBRE)) {
            findByMembrePreparedStatement.setInt(1,
                membreDTO.getIdMembre());
            try(
                ResultSet resultSet = findByMembrePreparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    pretDTO = new PretDTO();
                    pretDTO.setIdPret(resultSet.getInt(1));
                    MembreDTO unMembreDTO = new MembreDTO();
                    unMembreDTO.setIdMembre(resultSet.getInt(2));
                    pretDTO.setMembreDTO(unMembreDTO);
                    LivreDTO livreDTO = new LivreDTO();
                    livreDTO.setIdLivre(resultSet.getInt(3));
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

}
