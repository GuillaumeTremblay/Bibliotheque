import java.sql.*;

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
public class Connexion {

private Connection conn;

/**
 * Ouverture d'une connexion en mode autocommit false et sérialisable (si supporté)
 * @param serveur serveur SQL de la BD
 * @bd nom de la base de données
 * @user userid sur le serveur SQL
 * @pass mot de passe sur le serveur SQL
 */
public Connexion(String serveur, String bd, String user, String pass)
  throws SQLException
{
Driver d;
try {
    if (serveur.equals("local"))
        {
        d = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
        DriverManager.registerDriver(d);
        conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/" + bd,
            user, pass);
        }
    if (serveur.equals("distant"))
        {
        d = (Driver) Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
        DriverManager.registerDriver(d);
        conn = DriverManager.getConnection(
            "jdbc:oracle:thin:@collegeahuntsic.info:1521:" + bd,
             user, pass);
        }
    else if (serveur.equals("postgres"))
        {
        d = (Driver) Class.forName("org.postgresql.Driver").newInstance();
        DriverManager.registerDriver(d);
        conn = DriverManager.getConnection(
            "jdbc:postgresql:" + bd,
            user, pass);
        }
    else // access
        {
        d = (Driver) Class.forName("org.postgresql.Driver").newInstance();
        DriverManager.registerDriver(new sun.jdbc.odbc.JdbcOdbcDriver());
        conn = DriverManager.getConnection(
            "jdbc:odbc:" + bd,
            "", "");
        }

    // mettre en mode de commit manuel
    conn.setAutoCommit(false);
    
    // mettre en mode sérialisable si possible
    // (plus haut niveau d'integrité l'accès concurrent aux données)
    DatabaseMetaData dbmd = conn.getMetaData();
    if (dbmd.supportsTransactionIsolationLevel(Connection.TRANSACTION_SERIALIZABLE))
        {
        conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        System.out.println(
          "Ouverture de la connexion en mode sérialisable :\n" +
          "Estampille " + System.currentTimeMillis() + " " + conn);
        }
    else
        System.out.println(
          "Ouverture de la connexion en mode read committed (default) :\n" +
          "Heure " + System.currentTimeMillis() + " " + conn);
    }// try
    
catch (SQLException e) {throw e;}
catch (Exception e)
    {
    e.printStackTrace(System.out);
    throw new SQLException("JDBC Driver non instancié");
    }
}

/**
 *fermeture d'une connexion
 */
public void fermer()
  throws SQLException
{
conn.rollback();
conn.close();
System.out.println("Connexion fermée" + " " + conn);
}

/**
 *commit
 */
public void commit()
  throws SQLException
{
conn.commit();
}

/**
 *rollback
 */
public void rollback()
  throws SQLException
{
conn.rollback();
}

/**
 *retourne la Connection jdbc
 */
public Connection getConnection()
{
return conn;
}

/**
  * Retourne la liste des serveurs supportés par ce gestionnaire de connexions
  */
public static String serveursSupportes()
{
return "local : MySQL installé localement\n" +
       "distant : Oracle installé au Département d'Informatique du Collège Ahuntsic\n" +
       "postgres : Postgres installé localement\n" +
       "access : Microsoft Access installé localement et inscrit dans ODBC";
}
}// Classe Connexion
