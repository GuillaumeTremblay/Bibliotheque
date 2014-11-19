// Fichier BibliothequeServlet.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-06

package ca.qc.collegeahuntsic.bibliotheque.servlet.action;

import javax.servlet.http.HttpServlet;
import ca.qc.collegeahuntsic.bibliotheque.exception.BibliothequeException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces.ILivreFacade;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces.IMembreFacade;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces.IPretFacade;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces.IReservationFacade;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Bibliothèque base action servlet. <br />
 * All action servlet classes should inherit from it.
 * 
 * @author Gilles Benichou
 */
public class BibliothequeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String SESSION_FACTORY_NAME = "sessionFactory";

    private static final String MEMBRE_FACADE_NAME = "membreFacade";

    private static final String LIVRE_FACADE_NAME = "livreFacade";

    private static final String PRET_FACADE_NAME = "pretFacade";

    private static final String RESERVATION_FACADE_NAME = "reservationFacade";

    /**
     * The application context.
     */
    private ApplicationContext applicationContext;

    /**
     * The session factory.
     */
    private SessionFactory sessionFactory;

    /**
     * The membre facade.
     */
    private IMembreFacade membreFacade;

    /**
     * The livre facade.
     */
    private ILivreFacade livreFacade;

    /**
     * The prêt facade.
     */
    private IPretFacade pretFacade;

    /**
     * The réservation facade.
     */
    private IReservationFacade reservationFacade;

    /**
     * The session.
     */
    private Session session;

    /**
     * The transaction.
     */
    private Transaction transaction;

    /**
     * Default constructor.
     */
    public BibliothequeServlet() {
        super();
    }

    // Region Getters and Setters
    /**
     * Getter de la variable d'instance <code>this.applicationContext</code>.
     *
     * @return La variable d'instance <code>this.applicationContext</code>
     */
    private ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    /**
     * Setter de la variable d'instance <code>this.applicationContext</code>.
     *
     * @param applicationContext La valeur à utiliser pour la variable d'instance <code>this.applicationContext</code>
     */
    private void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Getter de la variable d'instance <code>this.membreFacade</code>.
     *
     * @return La variable d'instance <code>this.membreFacade</code>
     */
    protected IMembreFacade getMembreFacade() {
        return this.membreFacade;
    }

    /**
     * Setter de la variable d'instance <code>this.membreFacade</code>.
     *
     * @param membreFacade La valeur à utiliser pour la variable d'instance <code>this.membreFacade</code>
     */
    private void setMembreFacade(IMembreFacade membreFacade) {
        this.membreFacade = membreFacade;
    }

    /**
     * Getter de la variable d'instance <code>this.livreFacade</code>.
     *
     * @return La variable d'instance <code>this.livreFacade</code>
     */
    protected ILivreFacade getLivreFacade() {
        return this.livreFacade;
    }

    /**
     * Setter de la variable d'instance <code>this.livreFacade</code>.
     *
     * @param livreFacade La valeur à utiliser pour la variable d'instance <code>this.livreFacade</code>
     */
    private void setLivreFacade(ILivreFacade livreFacade) {
        this.livreFacade = livreFacade;
    }

    /**
     * Getter de la variable d'instance <code>this.pretFacade</code>.
     *
     * @return La variable d'instance <code>this.pretFacade</code>
     */
    protected IPretFacade getPretFacade() {
        return this.pretFacade;
    }

    /**
     * Setter de la variable d'instance <code>this.pretFacade</code>.
     *
     * @param pretFacade La valeur à utiliser pour la variable d'instance <code>this.pretFacade</code>
     */
    private void setPretFacade(IPretFacade pretFacade) {
        this.pretFacade = pretFacade;
    }

    /**
     * Getter de la variable d'instance <code>this.reservationFacade</code>.
     *
     * @return La variable d'instance <code>this.reservationFacade</code>
     */
    protected IReservationFacade getReservationFacade() {
        return this.reservationFacade;
    }

    /**
     * Setter de la variable d'instance <code>this.reservationFacade</code>.
     *
     * @param reservationFacade La valeur à utiliser pour la variable d'instance <code>this.reservationFacade</code>
     */
    private void setReservationFacade(IReservationFacade reservationFacade) {
        this.reservationFacade = reservationFacade;
    }

    /**
     * Getter for {@link #sessionFactory}.
     * 
     * @return Returns the {@link #sessionFactory}
     */
    private SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    /**
     * Setter for {@link #sessionFactory}.
     * 
     * @param sessionFactory The sessionFactory to use
     */
    private void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Getter for {@link #session}.
     * 
     * @return Returns the {@link #session}
     */
    protected Session getSession() {
        return this.session;
    }

    /**
     * Setter for {@link #session}.
     * 
     * @param session The session to use
     */
    private void setSession(Session session) {
        this.session = session;
    }

    /**
     * Getter for {@link #transaction}.
     * 
     * @return Returns the {@link #transaction}
     */
    private Transaction getTransaction() {
        return this.transaction;
    }

    /**
     * Setter for {@link #transaction}.
     * 
     * @param transaction The transaction to use
     */
    private void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    // EndRegion Getters and Setters

    /**
     * Initializes the Bibliotheque servlet by setting the application context, the session factory and the facades.
     */
    @Override
    public void init() {
        final ApplicationContext unApplicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        setApplicationContext(unApplicationContext);
        final SessionFactory uneSessionFactory = (SessionFactory) getApplicationContext().getBean(BibliothequeServlet.SESSION_FACTORY_NAME);
        setSessionFactory(uneSessionFactory);
        final IMembreFacade uneMembreFacade = (IMembreFacade) getApplicationContext().getBean(BibliothequeServlet.MEMBRE_FACADE_NAME);
        setMembreFacade(uneMembreFacade);
        final ILivreFacade uneLivreFacade = (ILivreFacade) getApplicationContext().getBean(BibliothequeServlet.LIVRE_FACADE_NAME);
        setLivreFacade(uneLivreFacade);
        final IPretFacade unePretFacade = (IPretFacade) getApplicationContext().getBean(BibliothequeServlet.PRET_FACADE_NAME);
        setPretFacade(unePretFacade);
        final IReservationFacade uneReservationFacade = (IReservationFacade) getApplicationContext().getBean(BibliothequeServlet.RESERVATION_FACADE_NAME);
        setReservationFacade(uneReservationFacade);
    }

    /**
     * Opens the session.
     * 
     * @return The session
     * @throws BibliothequeException If an error occurs
     */
    private Session openSession() throws BibliothequeException {
        try {
            setSession(getSessionFactory().openSession());
        } catch(HibernateException hibernateException) {
            throw new BibliothequeException(hibernateException);
        }
        return getSession();
    }

    /**
     * Closes the session.
     * 
     * @throws BibliothequeException If an error occurs
     */
    private void closeSession() throws BibliothequeException {
        try {
            getSession().close();
        } catch(HibernateException hibernateException) {
            throw new BibliothequeException(hibernateException);
        }
    }

    /**
     * Begins a transaction.
     * 
     * @throws BibliothequeException If an error occurs
     */
    protected void beginTransaction() throws BibliothequeException {
        try {
            setTransaction(openSession().beginTransaction());
        } catch(HibernateException hibernateException) {
            throw new BibliothequeException(hibernateException);
        }
    }

    /**
     * Commits the transaction.
     * 
     * @throws BibliothequeException If an error occurs
     */
    protected void commitTransaction() throws BibliothequeException {
        try {
            getTransaction().commit();
            closeSession();
        } catch(HibernateException hibernateException) {
            throw new BibliothequeException(hibernateException);
        }
    }

    /**
     * Rollbacks the transaction.
     * 
     * @throws BibliothequeException If an error occurs
     */
    protected void rollbackTransaction() throws BibliothequeException {
        try {
            getTransaction().rollback();
            closeSession();
        } catch(HibernateException hibernateException) {
            throw new BibliothequeException(hibernateException);
        }
    }
}
