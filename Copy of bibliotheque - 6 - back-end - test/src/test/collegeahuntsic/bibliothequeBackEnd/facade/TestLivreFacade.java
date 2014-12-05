// Fichier TestLivreFacade.java
// Auteur : 201205090
// Date de création : 2014-12-05

package test.collegeahuntsic.bibliothequeBackEnd.facade;

import java.sql.Timestamp;
import java.util.List;
import junit.framework.Test;
import junit.framework.TestSuite;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.FacadeException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingReservationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import test.collegeahuntsic.bibliothequeBackEnd.exception.TestCaseFailedException;

/**
 * TODO Auto-generated class javadoc
 *
 * @author Jonathan Aumont
 */
public class TestLivreFacade extends TestCase {

    private static final Log LOGGER = LogFactory.getLog(TestLivreFacade.class);

    private static final String TEST_CASE_TITLE = "Livre facade test case"; //$NON-NLS-1$

    private static final String TITRE = "Titre "; //$NON-NLS-1$

    private static final String AUTEUR = "Auteur "; //$NON-NLS-1$

    private static int sequence = 1;

    /**
     * Default constructor.
     *
     * @param name The name of the test case.
     * @throws TestCaseFailedException 
     */
    public TestLivreFacade(String name) throws TestCaseFailedException {
        super(name);
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * 
     * Declenche tout les test de la class TestLivreFacade
     *
     * @return Test The tests to be executed in this test case
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite(TestLivreFacade.TEST_CASE_TITLE);
        suite.addTestSuite(TestLivreFacade.class);
        return suite;
    }

    /**.
     * 
     * Test la fonction testAcquerirLivre()
     *
     * @throws TestCaseFailedException S'il y a une erreur
     */
    public void testAcquerirLivre() throws TestCaseFailedException {
        try {
            beginTransaction();
            final LivreDTO livreDTO = new LivreDTO();
            livreDTO.setTitre(TestLivreFacade.TITRE
                + TestLivreFacade.sequence);
            livreDTO.setAuteur(TestLivreFacade.AUTEUR
                + TestLivreFacade.sequence);
            livreDTO.setDateAcquisition(new Timestamp(System.currentTimeMillis()));
            TestLivreFacade.sequence = TestLivreFacade.sequence + 1;
            getLivreFacade().acquerirLivre(getSession(),
                livreDTO);
            commitTransaction();
        } catch(
            InvalidHibernateSessionException
            | InvalidDTOException
            | FacadeException exception) {
            try {
                rollbackTransaction();
            } catch(TestCaseFailedException testCaseFailedException) {
                TestLivreFacade.LOGGER.error(testCaseFailedException);
            }
            TestLivreFacade.LOGGER.error(exception);
        }
    }

    /**.
     * 
     * Test la fonction testGetLivre()
     *
     * @throws TestCaseFailedException S'il y a une erreur
     */
    public void testGetLivre() throws TestCaseFailedException {
        try {
            testAcquerirLivre();
            beginTransaction();
            final List<LivreDTO> livres = getLivreFacade().getAllLivres(getSession(),
                LivreDTO.TITRE_COLUMN_NAME);
            assertFalse(livres.isEmpty());
            LivreDTO livreDTO = livres.get(livres.size() - 1);
            assertNotNull(livreDTO);
            assertNotNull(livreDTO.getAuteur());
            assertNotNull(livreDTO.getIdLivre());
            assertNotNull(livreDTO.getTitre());
            assertNotNull(livreDTO.getDateAcquisition());
            final String idLivre = livreDTO.getIdLivre();
            final String titre = livreDTO.getTitre();
            final String auteur = livreDTO.getAuteur();
            final Timestamp dateAcquisition = livreDTO.getDateAcquisition();
            commitTransaction();

            beginTransaction();
            livreDTO = getLivreFacade().getLivre(getSession(),
                idLivre);
            assertNotNull(livreDTO);
            assertNotNull(livreDTO.getAuteur());
            assertNotNull(livreDTO.getIdLivre());
            assertNotNull(livreDTO.getTitre());
            assertNotNull(livreDTO.getDateAcquisition());
            assertEquals(idLivre,
                livreDTO.getIdLivre());
            assertEquals(titre,
                livreDTO.getTitre());
            assertEquals(auteur,
                livreDTO.getAuteur());
            assertEquals(dateAcquisition,
                livreDTO.getDateAcquisition());
            commitTransaction();

            beginTransaction();
            livreDTO = getLivreFacade().getLivre(getSession(),
                "-1");
            assertNull(livreDTO);
            commitTransaction();
        } catch(
            InvalidHibernateSessionException
            | InvalidPrimaryKeyException
            | FacadeException
            | InvalidSortByPropertyException exception) {
            try {
                rollbackTransaction();
            } catch(TestCaseFailedException testCaseFailedException) {
                TestLivreFacade.LOGGER.error(testCaseFailedException);
            }
            TestLivreFacade.LOGGER.error(exception);
        }
    }

    /**.
     * 
     * Test la fonction GetAllLivre()
     *
     * @throws TestCaseFailedException S'il y a une erreur
     */

    public void testGetAllLivres() throws TestCaseFailedException {
        try {
            testAcquerirLivre();
            beginTransaction();
            final List<LivreDTO> livres = getLivreFacade().getAllLivres(getSession(),
                LivreDTO.TITRE_COLUMN_NAME);
            assertFalse(livres.isEmpty());
            for(LivreDTO livreDTO : livres) {
                assertNotNull(livreDTO);
                assertNotNull(livreDTO.getIdLivre());
                assertNotNull(livreDTO.getTitre());
                assertNotNull(livreDTO.getAuteur());
                assertNotNull(livreDTO.getDateAcquisition());
            }
            commitTransaction();
        } catch(
            InvalidHibernateSessionException
            | InvalidSortByPropertyException
            | FacadeException e) {
            try {
                rollbackTransaction();
            } catch(TestCaseFailedException e1) {
                TestLivreFacade.LOGGER.error(e1);
            }
            TestLivreFacade.LOGGER.error(e);
        }
    }

    /**.
     * 
     * Test la fonction testUpdateLivre()
     *
     * @throws TestCaseFailedException S'il y a une erreur
     */

    public void testUpdateLivre() throws TestCaseFailedException {
        try {
            testAcquerirLivre();
            beginTransaction();
            List<LivreDTO> livres = getLivreFacade().getAllLivres(getSession(),
                LivreDTO.TITRE_COLUMN_NAME);
            assertFalse(livres.isEmpty());
            LivreDTO livreDTO = livres.get(livres.size() - 1);
            assertNotNull(livreDTO);
            assertNotNull(livreDTO.getAuteur());
            assertNotNull(livreDTO.getIdLivre());
            assertNotNull(livreDTO.getTitre());
            assertNotNull(livreDTO.getDateAcquisition());
            final String idLivre = livreDTO.getIdLivre();
            final String titre = livreDTO.getTitre();
            final String auteur = livreDTO.getAuteur();
            final Timestamp dateAcquisition = livreDTO.getDateAcquisition();
            commitTransaction();

            beginTransaction();
            final String testAuteur = TestLivreFacade.AUTEUR
                + TestLivreFacade.sequence;
            livreDTO.setAuteur(testAuteur);
            final String testTitre = TestLivreFacade.TITRE
                + TestLivreFacade.sequence;
            livreDTO.setTitre(testTitre);
            final Timestamp testDateAcquisition = new Timestamp(System.currentTimeMillis());
            livreDTO.setDateAcquisition(testDateAcquisition);
            TestLivreFacade.sequence = TestLivreFacade.sequence + 1;
            getLivreFacade().updateLivre(getSession(),
                livreDTO);
            commitTransaction();

            beginTransaction();
            livreDTO = getLivreFacade().getLivre(getSession(),
                idLivre);
            assertNotNull(livreDTO);
            assertNotNull(livreDTO.getAuteur());
            assertNotNull(livreDTO.getIdLivre());
            assertNotNull(livreDTO.getTitre());
            assertNotNull(livreDTO.getDateAcquisition());
            commitTransaction();

            assertEquals(idLivre,
                livreDTO.getIdLivre());
            assertNotSame(titre,
                livreDTO.getTitre());
            assertNotSame(auteur,
                livreDTO.getAuteur());
            assertNotSame(dateAcquisition,
                livreDTO.getDateAcquisition());
            assertEquals(testTitre,
                livreDTO.getTitre());
            assertEquals(testAuteur,
                livreDTO.getAuteur());
            assertEquals(testDateAcquisition,
                livreDTO.getDateAcquisition());
        } catch(
            InvalidHibernateSessionException
            | InvalidSortByPropertyException
            | FacadeException
            | InvalidDTOException
            | InvalidPrimaryKeyException e) {
            try {
                rollbackTransaction();
            } catch(TestCaseFailedException e1) {
                TestLivreFacade.LOGGER.error(e1);
            }
            TestLivreFacade.LOGGER.error(e);
        }
    }

    /**
     * 
     *Test la fonction testVendreLivre()
     *
     * @throws TestCaseFailedException s'il y a une erreur
     */
    public void testVendreLivre() throws TestCaseFailedException {
        try {
            testAcquerirLivre();
            beginTransaction();
            List<LivreDTO> livres = getLivreFacade().getAllLivres(getSession(),
                LivreDTO.TITRE_COLUMN_NAME);
            assertFalse(livres.isEmpty());
            LivreDTO livreDTO = livres.get(livres.size() - 1);
            assertNotNull(livreDTO);
            assertNotNull(livreDTO.getAuteur());
            assertNotNull(livreDTO.getIdLivre());
            assertNotNull(livreDTO.getTitre());
            assertNotNull(livreDTO.getDateAcquisition());
            final String idLivre = livreDTO.getIdLivre();
            final String titre = livreDTO.getTitre();
            final String auteur = livreDTO.getAuteur();
            final Timestamp dateAcquisition = livreDTO.getDateAcquisition();
            commitTransaction();

            beginTransaction();
            livres.remove(livreDTO);
            livreDTO.getPrets().clear();
            getLivreFacade().vendreLivre(getSession(),
                livreDTO);
            commitTransaction();

            beginTransaction();
            livres = getLivreFacade().getAllLivres(getSession(),
                LivreDTO.TITRE_COLUMN_NAME);
            assertFalse(livres.isEmpty());
            for(LivreDTO unlivreDTO : livres) {
                assertNotNull(unlivreDTO);
                assertNotNull(unlivreDTO.getIdLivre());
                assertNotSame(idLivre,
                    unlivreDTO.getIdLivre());
                assertNotNull(unlivreDTO.getTitre());
                assertNotSame(titre,
                    unlivreDTO.getTitre());
                assertNotNull(unlivreDTO.getAuteur());
                assertNotSame(auteur,
                    unlivreDTO.getAuteur());
                assertNotNull(unlivreDTO.getDateAcquisition());
                assertNotSame(dateAcquisition,
                    unlivreDTO.getDateAcquisition());
            }
            commitTransaction();
        } catch(
            InvalidHibernateSessionException
            | InvalidSortByPropertyException
            | FacadeException
            | InvalidDTOException
            | ExistingLoanException
            | ExistingReservationException e) {
            try {
                rollbackTransaction();
            } catch(TestCaseFailedException e1) {
                TestLivreFacade.LOGGER.error(e1);
            }
            TestLivreFacade.LOGGER.error(e);
        }
    }
}
