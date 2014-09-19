package ca.qc.collegeahuntsic.bibliotheque.db;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

// TODO: Auto-generated Javadoc
/**
 * Gestionnaire d'une connexion avec une BD relationnelle via JDBC.
 *
 * Ce programme ouvrir une connexion avec une BD via JDBC.
 * La méthode serveursSupportes() indique les serveurs supportés.
 *
 * Pré-condition
 *   le driver JDBC approprié doit être accessible.
 *
 * Post-condition
 *   la connexion est ouverte en mode autocommit false et sérialisable, 
 *   (s'il est supporté par le serveur).
 * </pre>
 */
public class ConnexionDb {

    /** The conn. */
    private Connection conn;

    /**
     * Ouverture d'une connexion en mode autocommit false et sérialisable (si supporté).
     *
     * @param serveur serveur SQL de la BD
     * @param bd the bd
     * @param user the user
     * @param pass the pass
     * @throws SQLException the SQL exception
     * @bd nom de la base de données
     * @user userid sur le serveur SQL
     * @pass mot de passe sur le serveur SQL
     */
    public ConnexionDb(String serveur,
        String bd,
        String user,
        String pass) throws SQLException {
        Driver d;
        try {
            if(serveur.equals("local")) {
                d = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
                DriverManager.registerDriver(d);
                this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"
                    + bd,
                    user,
                    pass);
            } else if(serveur.equals("distant")) {
                d = (Driver) Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
                DriverManager.registerDriver(d);
                this.conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:"
                    + bd,
                    user,
                    pass);
            } else if(serveur.equals("postgres")) {
                d = (Driver) Class.forName("org.postgresql.Driver").newInstance();
                DriverManager.registerDriver(d);
                this.conn = DriverManager.getConnection("jdbc:postgresql:"
                    + bd,
                    user,
                    pass);
            } else // access
            {
                d = (Driver) Class.forName("org.postgresql.Driver").newInstance();
                DriverManager.registerDriver(new sun.jdbc.odbc.JdbcOdbcDriver());
                this.conn = DriverManager.getConnection("jdbc:odbc:"
                    + bd,
                    "",
                    "");
            }

            // mettre en mode de commit manuel
            this.conn.setAutoCommit(false);

            // mettre en mode sérialisable si possible
            // (plus haut niveau d'integrité l'accès concurrent aux données)
            DatabaseMetaData dbmd = this.conn.getMetaData();
            if(dbmd.supportsTransactionIsolationLevel(Connection.TRANSACTION_SERIALIZABLE)) {
                this.conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                System.out.println("Ouverture de la connexion en mode sérialisable :\n"
                    + "Estampille "
                    + System.currentTimeMillis()
                    + " "
                    + this.conn);
            } else {
                System.out.println("Ouverture de la connexion en mode read committed (default) :\n"
                    + "Heure "
                    + System.currentTimeMillis()
                    + " "
                    + this.conn);
            }
        }// try

        catch(SQLException e) {
            throw e;
        } catch(Exception e) {
            e.printStackTrace(System.out);
            throw new SQLException("JDBC Driver non instancié");
        }
    }

    /**
     * fermeture d'une connexion.
     *
     * @throws SQLException the SQL exception
     */
    public void fermer() throws SQLException {
        this.conn.rollback();
        this.conn.close();
        System.out.println("Connexion fermée"
            + " "
            + this.conn);
    }

    /**
     * commit.
     *
     * @throws SQLException the SQL exception
     */
    public void commit() throws SQLException {
        this.conn.commit();
    }

    /**
     * rollback.
     *
     * @throws SQLException the SQL exception
     */
    public void rollback() throws SQLException {
        this.conn.rollback();
    }

    /**
     * retourne la Connection jdbc.
     *
     * @return the connection
     */
    public Connection getConnection() {
        return this.conn;
    }

    /**
     * Retourne la liste des serveurs supportés par ce gestionnaire de connexions.
     *
     * @return the string
     */
    public static String serveursSupportes() {
        return "local : MySQL installé localement\n"
            + "distant : Oracle installé au Département d'Informatique du Collège Ahuntsic\n"
            + "postgres : Postgres installé localement\n"
            + "access : Microsoft Access installé localement et inscrit dans ODBC";
    }
}// Classe Connexion
