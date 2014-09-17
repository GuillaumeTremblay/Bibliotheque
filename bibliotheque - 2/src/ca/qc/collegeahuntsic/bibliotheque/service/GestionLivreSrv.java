package ca.qc.collegeahuntsic.bibliotheque.service;
import java.sql.SQLException;
import ca.qc.collegeahuntsic.bibliotheque.DB.ConnexionDb;
import ca.qc.collegeahuntsic.bibliotheque.dao.LivreDao;
import ca.qc.collegeahuntsic.bibliotheque.dao.ReservationDao;
import ca.qc.collegeahuntsic.bibliotheque.dto.TupleLivreDto;
import ca.qc.collegeahuntsic.bibliotheque.exception.BiblioException;

// TODO: Auto-generated Javadoc
/**
 * Gestion des transactions de reliées à la création et
 * suppresion de livres dans une bibliothèque.
 *
 * Ce programme permet de gérer les transaction reliées à la 
 * création et suppresion de livres.
 *
 * Pré-condition
 *   la base de données de la bibliothèque doit exister
 *
 * Post-condition
 *   le programme effectue les maj associées à chaque
 *   transaction
 * </pre>
 */
public class GestionLivreSrv {

    /** The livre. */
    private LivreDao livre;

    /** The reservation. */
    private ReservationDao reservation;

    /** The cx. */
    private ConnexionDb cx;

    /**
     * Creation d'une instance.
     *
     * @param livre the livre
     * @param reservation the reservation
     */
    public GestionLivreSrv(LivreDao livre,
        ReservationDao reservation) {
        this.cx = livre.getConnexion();
        this.livre = livre;
        this.reservation = reservation;
    }

    /**
     * Ajout d'un nouveau livre dans la base de données.
     * S'il existe deja, une exception est levée.
     *
     * @param idLivre the id livre
     * @param titre the titre
     * @param auteur the auteur
     * @param dateAcquisition the date acquisition
     * @throws SQLException the SQL exception
     * @throws BiblioException the biblio exception
     * @throws Exception the exception
     */
    public void acquerir(int idLivre,
        String titre,
        String auteur,
        String dateAcquisition) throws SQLException,
        BiblioException,
        Exception {
        try {
            /* Vérifie si le livre existe déja */
            if(this.livre.existe(idLivre)) {
                throw new BiblioException("Livre existe deja: "
                    + idLivre);
            }

            /* Ajout du livre dans la table des livres */
            this.livre.acquerir(idLivre,
                titre,
                auteur,
                dateAcquisition);
            this.cx.commit();
        } catch(Exception e) {
            //        System.out.println(e);
            this.cx.rollback();
            throw e;
        }
    }

    /**
     * Vente d'un livre.
     *
     * @param idLivre the id livre
     * @throws SQLException the SQL exception
     * @throws BiblioException the biblio exception
     * @throws Exception the exception
     */
    public void vendre(int idLivre) throws SQLException,
        BiblioException,
        Exception {
        try {
            TupleLivreDto tupleLivre = this.livre.getLivre(idLivre);
            if(tupleLivre == null) {
                throw new BiblioException("Livre inexistant: "
                    + idLivre);
            }
            if(tupleLivre.idMembre != 0) {
                throw new BiblioException("Livre "
                    + idLivre
                    + " prete a "
                    + tupleLivre.idMembre);
            }
            if(this.reservation.getReservationLivre(idLivre) != null) {
                throw new BiblioException("Livre "
                    + idLivre
                    + " réservé ");
            }

            /* Suppression du livre. */
            int nb = this.livre.vendre(idLivre);
            if(nb == 0) {
                throw new BiblioException("Livre "
                    + idLivre
                    + " inexistant");
            }
            this.cx.commit();
        } catch(Exception e) {
            this.cx.rollback();
            throw e;
        }
    }
}
