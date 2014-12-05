// Fichier TestAll.java
// Auteur : Gilles Benichou
// Date de création : 2014-09-23

package test.collegeahuntsic.bibliothequeBackEnd;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.collegeahuntsic.bibliothequeBackEnd.facade.TestLivreFacade;

/**
 * Test case for all facades in all packages.
 *
 * @author Gilles Benichou
 */
public class TestAll extends junit.framework.TestCase {
    /**
     * The test case title.
     */
    private static final String TEST_CASE_TITLE = "All test cases"; //$NON-NLS-1$

    /**
     * Default constructor.
     * 
     * @param name The name of the test case
     */
    public TestAll(String name) {
        super(name);
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
     * Configures the tests to be executed in this test case. This suite is now visible for a {@link junit.awtui.TestRunner}.<br /><br />
     * The suite contains all test cases from:<br />
     * <ul>
     * <li>{@link test.collegeahuntsic.bibliothequeBackEnd.facade.TestCase#suite() }
     * </ul>
     * 
     * @return Test The tests to be executed in this test case
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite(TestAll.TEST_CASE_TITLE);
        suite.addTestSuite(TestLivreFacade.class);
        return suite;
    }
}
