
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
import ca.qc.collegeahuntsic.bibliotheque.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.DAOException;

// TODO: Auto-generated Javadoc
/**
 * Permet d'effectuer les accès à la table reservation.
 *<pre>
 *
 * Cette classe gère tous les accès à la table reservation.
 *
 *</pre>
 */
public class ReservationDAO extends DAO {
    /**
     * TODO Auto-generated field javadoc
     */
    private static final long serialVersionUID = 1L;

    private static final String ADD_REQUEST = "INSERT INTO reservation (idReservation, idMembre, idLivre, dateReservation) "
        + "VALUES (SEQ_ID_RESERVATION.NEXTVAL, ?, ?, SYSTIMESTAMP)";

    private static final String READ_REQUEST = "SELECT idReservation, idMembre, idLivre, dateReservation"
        + "FROM reservation "
        + "WHERE idReservation = ?";

    private static final String UPDATE_REQUEST = "UPDATE reservation "
        + "SET idMembre = ?, idLivre = ?, dateReservation = SYSTIMESTAMP "
        + "WHERE idReservation = ?";

    private static final String DELETE_REQUEST = "DELETE FROM reservation "
        + "WHERE idReservation = ?";

    private static final String GET_ALL_REQUEST = "SELECT idReservation, idMembre, idLivre, dateReservation "
        + "FROM reservation";

    private static final String FIND_BY_MEMBRE = "SELECT idReservation, idMembre, idLivre, dateReservation "
        + "FROM reservation "
        + "WHERE idMembre = ?";

    private static final String FIND_BY_LIVRE = "SELECT idReservation, idMembre, idLivre, dateReservation "
        + "FROM reservation "
        + "WHERE idLivre = ?";

    /**
     * Crée un DAO à partir d'une connexion à la base de données.
     *
     * @param connexion La connexion à utiliser
     */
    public ReservationDAO(Connexion connexion) {
        super(connexion);
    }

    /**
     * Ajoute un nouveau reservation.
     *
     * @param reservationDTO La reservation à ajouter
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public void add(ReservationDTO reservationDTO) throws DAOException {
        try(
            PreparedStatement addPreparedStatement = getConnection().prepareStatement(ReservationDAO.ADD_REQUEST)) {
            addPreparedStatement.setInt(1,
                reservationDTO.getMembreDTO().getIdMembre());
            addPreparedStatement.setInt(2,
                reservationDTO.getLivreDTO().getIdLivre());
            addPreparedStatement.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

    /**
     * Lit une reservation.
     *
     * @param idReservation L'id d'une reservation à lire
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public ReservationDTO read(ReservationDTO reservationDTO) throws DAOException {
        ReservationDTO unReservationDTO = null;
        try(
            PreparedStatement readPreparedStatement = getConnection().prepareStatement(ReservationDAO.READ_REQUEST)) {
            readPreparedStatement.setInt(1,
                reservationDTO.getIdReservation());
            try(
                ResultSet resultSet = readPreparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    unReservationDTO = new ReservationDTO();
                    unReservationDTO.setIdReservation(resultSet.getInt(1));
                    MembreDTO membreDTO = new MembreDTO();
                    membreDTO.setIdMembre(resultSet.getInt(2));
                    unReservationDTO.setMembreDTO(membreDTO);
                    LivreDTO livreDTO = new LivreDTO();
                    livreDTO.setIdLivre(resultSet.getInt(3));
                    unReservationDTO.setLivreDTO(livreDTO);
                    unReservationDTO.setDateReservation(resultSet.getTimestamp(4));
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return unReservationDTO;
    }

    /**
     * Met à jour une reservation.
     *
     * @param reservationDTO La reservation à mettre à jour
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public void update(ReservationDTO reservationDTO) throws DAOException {
        try(
            PreparedStatement updatePreparedStatement = getConnection().prepareStatement(ReservationDAO.UPDATE_REQUEST)) {
            updatePreparedStatement.setInt(1,
                reservationDTO.getIdReservation());
            updatePreparedStatement.setInt(2,
                reservationDTO.getMembreDTO().getIdMembre());
            updatePreparedStatement.setInt(3,
                reservationDTO.getLivreDTO().getIdLivre());
            updatePreparedStatement.setTimestamp(4,
                reservationDTO.getDateReservation());
            updatePreparedStatement.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

    /**
     * Supprime une reservation.
     *
     * @param reservationDTO La reservation à supprimer
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public void delete(ReservationDTO reservationDTO) throws DAOException {
        try(
            PreparedStatement deletePreparedStatement = getConnection().prepareStatement(ReservationDAO.DELETE_REQUEST)) {
            deletePreparedStatement.setInt(1,
                reservationDTO.getIdReservation());
            deletePreparedStatement.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

    /**
     * Trouve tous les reservations.
     *
     * @return La liste des reservations ; une liste vide sinon
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public List<ReservationDTO> getAll() throws DAOException {
        List<ReservationDTO> reservations = Collections.EMPTY_LIST;
        try(
            PreparedStatement getAllPreparedStatement = getConnection().prepareStatement(ReservationDAO.GET_ALL_REQUEST)) {
            try(
                ResultSet resultSet = getAllPreparedStatement.executeQuery()) {
                ReservationDTO reservationDTO = null;
                if(resultSet.next()) {
                    reservations = new ArrayList<>();
                    do {
                        reservationDTO = new ReservationDTO();
                        reservationDTO.setIdReservation(resultSet.getInt(1));
                        MembreDTO membreDTO = new MembreDTO();
                        membreDTO.setIdMembre(resultSet.getInt(2));
                        reservationDTO.setMembreDTO(membreDTO);
                        LivreDTO livreDTO = new LivreDTO();
                        livreDTO.setIdLivre(resultSet.getInt(3));
                        reservationDTO.setLivreDTO(livreDTO);
                        reservationDTO.setDateReservation(resultSet.getTimestamp(4));
                        reservations.add(reservationDTO);
                    } while(resultSet.next());
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return reservations;
    }

    /**
     * Trouve les reservations à partir d'un membre.
     *
     * @param membreDTO Le membre à utiliser
     * @return La liste des membres correspondants ; une liste vide sinon
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public List<ReservationDTO> findByMembre(MembreDTO membreDTO) throws DAOException {
        List<ReservationDTO> reservations = Collections.EMPTY_LIST;
        try(
            PreparedStatement findByIDPreparedStatement = getConnection().prepareStatement(ReservationDAO.FIND_BY_MEMBRE)) {
            findByIDPreparedStatement.setInt(1,
                membreDTO.getIdMembre());
            try(
                ResultSet resultSet = findByIDPreparedStatement.executeQuery()) {
                ReservationDTO reservationDTO = null;
                if(resultSet.next()) {
                    reservations = new ArrayList<>();
                    do {
                        reservationDTO = new ReservationDTO();
                        reservationDTO.setIdReservation(resultSet.getInt(1));
                        MembreDTO unMembreDTO = new MembreDTO();
                        unMembreDTO.setIdMembre(resultSet.getInt(2));
                        reservationDTO.setMembreDTO(unMembreDTO);
                        LivreDTO livreDTO = new LivreDTO();
                        livreDTO.setIdLivre(resultSet.getInt(3));
                        reservationDTO.setLivreDTO(livreDTO);
                        reservationDTO.setDateReservation(resultSet.getTimestamp(4));
                        reservations.add(reservationDTO);
                    } while(resultSet.next());
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return reservations;
    }

    /**
     * Trouve les reservations à partir d'un livre.
     *
     * @param LivreDTO Le livre à utiliser
     * @return La liste des membres correspondants ; une liste vide sinon
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public List<ReservationDTO> findByLivre(LivreDTO livreDTO) throws DAOException {
        List<ReservationDTO> reservations = Collections.EMPTY_LIST;
        try(
            PreparedStatement findByIDPreparedStatement = getConnection().prepareStatement(ReservationDAO.FIND_BY_LIVRE)) {
            findByIDPreparedStatement.setInt(1, 
                livreDTO.getIdLivre());
            try(
                ResultSet resultSet = findByIDPreparedStatement.executeQuery()) {
                ReservationDTO reservationDTO = null;
                if(resultSet.next()) {
                    reservations = new ArrayList<>();
                    do {
                        reservationDTO = new ReservationDTO();
                        reservationDTO.setIdReservation(resultSet.getInt(1));
                        MembreDTO membreDTO = new MembreDTO();
                        membreDTO.setIdMembre(resultSet.getInt(2));
                        reservationDTO.setMembreDTO(membreDTO);
                        LivreDTO unLivreDTO = new LivreDTO();
                        unLivreDTO.setIdLivre(resultSet.getInt(3));
                        reservationDTO.setLivreDTO(unLivreDTO);
                        reservationDTO.setDateReservation(resultSet.getTimestamp(4));
                        reservations.add(reservationDTO);
                    } while(resultSet.next());
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return reservations;
    }
}
