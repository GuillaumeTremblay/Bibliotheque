// Fichier TestCase.java
// Auteur : Gilles Benichou
// Date de création : 2014-09-23

package test.collegeahuntsic.bibliothequeBackEnd.facade;

import ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces.ILivreFacade;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces.IMembreFacade;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces.IPretFacade;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces.IReservationFacade;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import test.collegeahuntsic.bibliothequeBackEnd.exception.TestCaseFailedException;

/**
 * Base test case. <br />
 * All test cases should inherit from it.
 * 
 * @author Gilles Benichou
 */
public class TestCase extends junit.framework.TestCase {
    private static final String APPLICATION_CONTEXT_JDBC_FILENAME = "testApplicationContext-jdbc.xml"; //$NON-NLS-1$

    private static final String APPLICATION_CONTEXT_DTO_FILENAME = "applicationContext-dto.xml"; //$NON-NLS-1$

    private static final String APPLICATION_CONTEXT_DAO_FILENAME = "applicationContext-dao.xml"; //$NON-NLS-1$

    private static final String APPLICATION_CONTEXT_SERVICE_FILENAME = "applicationContext-service.xml"; //$NON-NLS-1$

    private static final String APPLICATION_CONTEXT_FACADE_FILENAME = "applicationContext-facade.xml"; //$NON-NLS-1$

    private static final String APPLICATION_CONTEXT_FILENAME = "testApplicationContext.xml"; //$NON-NLS-1$

    private static final String SESSION_FACTORY_NAME = "sessionFactory"; //$NON-NLS-1$

    private static final String LIVRE_FACADE_NAME = "livreFacade";

    private static final String MEMBRE_FACADE_NAME = "membreFacade";

    private static final String PRET_FACADE_NAME = "pretFacade";

    private static final String RESERVATION_FACADE_NAME = "reservationFacade";

    private static final String[] APPLICATION_CONTEXT_FILENAMES = new String[] {TestCase.APPLICATION_CONTEXT_JDBC_FILENAME,
        TestCase.APPLICATION_CONTEXT_DTO_FILENAME,
        TestCase.APPLICATION_CONTEXT_DAO_FILENAME,
        TestCase.APPLICATION_CONTEXT_SERVICE_FILENAME,
        TestCase.APPLICATION_CONTEXT_FACADE_FILENAME,
        TestCase.APPLICATION_CONTEXT_FILENAME};

    private static final ApplicationContext APPLICATION_CONTEXT = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_FILENAMES);

    private SessionFactory sessionFactory;

    private Session session;

    private Transaction transaction;

    private ILivreFacade livreFacade;

    private IMembreFacade membreFacade;

    private IPretFacade pretFacade;

    private IReservationFacade reservationFacade;

    /**
     * Default constructor.
     * 
     * @param name The name of the test case
     * @throws TestCaseFailedException If an error occurs
     */
    public TestCase(String name) throws TestCaseFailedException {
        super(name);
        try {
            setSessionFactory((SessionFactory) TestCase.APPLICATION_CONTEXT.getBean(TestCase.SESSION_FACTORY_NAME));
            setMembreFacade((IMembreFacade) TestCase.APPLICATION_CONTEXT.getBean(TestCase.MEMBRE_FACADE_NAME));
            setLivreFacade((ILivreFacade) TestCase.APPLICATION_CONTEXT.getBean(TestCase.LIVRE_FACADE_NAME));
            setPretFacade((IPretFacade) TestCase.APPLICATION_CONTEXT.getBean(TestCase.PRET_FACADE_NAME));
            setReservationFacade((IReservationFacade) TestCase.APPLICATION_CONTEXT.getBean(TestCase.RESERVATION_FACADE_NAME));
        } catch(BeansException beansException) {
            throw new TestCaseFailedException(beansException);
        }
    }

    // Region Getters and Setters
    /**
     * Getter de la variable d'instance <code>this.sessionFactory</code>.
     *
     * @return La variable d'instance <code>this.sessionFactory</code>
     */
    private SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    /**
     * Setter de la variable d'instance <code>this.sessionFactory</code>.
     *
     * @param sessionFactory La valeur à utiliser pour la variable d'instance <code>this.sessionFactory</code>
     */
    private void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Getter de la variable d'instance <code>this.session</code>.
     *
     * @return La variable d'instance <code>this.session</code>
     */
    protected Session getSession() {
        return this.session;
    }

    /**
     * Setter de la variable d'instance <code>this.session</code>.
     *
     * @param session La valeur à utiliser pour la variable d'instance <code>this.session</code>
     */
    private void setSession(Session session) {
        this.session = session;
    }

    /**
     * Getter de la variable d'instance <code>this.transaction</code>.
     *
     * @return La variable d'instance <code>this.transaction</code>
     */
    private Transaction getTransaction() {
        return this.transaction;
    }

    /**
     * Setter de la variable d'instance <code>this.transaction</code>.
     *
     * @param transaction La valeur à utiliser pour la variable d'instance <code>this.transaction</code>
     */
    private void setTransaction(Transaction transaction) {
        this.transaction = transaction;
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

    // EndRegion Getters and Setters

    /**
     * Ouvre une session Hibernate.
     * 
     * @return La session Hibernate
     * @throws TestCaseFailedException S'il y a une erreur
     */
    private Session openSession() throws TestCaseFailedException {
        try {
            setSession(getSessionFactory().openSession());
        } catch(HibernateException hibernateException) {
            throw new TestCaseFailedException(hibernateException);
        }
        return getSession();
    }

    /**
     * Ferme une session Hibernate.
     * 
     * @throws TestCaseFailedException S'il y a une erreur
     */
    private void closeSession() throws TestCaseFailedException {
        try {
            getSession().close();
        } catch(HibernateException hibernateException) {
            throw new TestCaseFailedException(hibernateException);
        }
    }

    /**
     * Démarre une transaction.
     * 
     * @throws TestCaseFailedException S'il y a une erreur
     */
    protected void beginTransaction() throws TestCaseFailedException {
        try {
            setTransaction(openSession().beginTransaction());
        } catch(HibernateException hibernateException) {
            throw new TestCaseFailedException(hibernateException);
        }
    }

    /**
     * Commit une transaction.
     * 
     * @throws TestCaseFailedException S'il y a une erreur
     */
    protected void commitTransaction() throws TestCaseFailedException {
        try {
            getTransaction().commit();
            closeSession();
        } catch(HibernateException hibernateException) {
            throw new TestCaseFailedException(hibernateException);
        }
    }

    /**
     * Rollback une transaction.
     * 
     * @throws TestCaseFailedException S'il y a une erreur
     */
    protected void rollbackTransaction() throws TestCaseFailedException {
        try {
            getTransaction().rollback();
            closeSession();
        } catch(HibernateException hibernateException) {
            throw new TestCaseFailedException(hibernateException);
        }
    }

    /**
     * Sets the test case up.
     * 
     * @throws Exception If an error occurs
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Tears the test case down.
     * 
     * @throws Exception If an error occurs
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Tests if the Spring configuration file has been loaded properly.
     */
    public void testSpring() {
        assertNotNull(TestCase.APPLICATION_CONTEXT);
        assertNotNull(getSessionFactory());
        assertNotNull(getLivreFacade());
        assertNotNull(getMembreFacade());
        assertNotNull(getPretFacade());
        assertNotNull(getReservationFacade());
    }
}
