// Fichier Action.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-06

package ca.qc.collegeahuntsic.bibliotheque.struts.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import ca.qc.collegeahuntsic.bibliotheque.exception.BibliothequeException;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Bibliothèque base action. <br />
 * All action classes should inherit from it.
 * 
 * @author Gilles Benichou
 */
public class BibliothequeAction extends org.apache.struts.action.Action {
    /**
     * The session factory.
     */
    private SessionFactory sessionFactory;

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
     * 
     * @param sessionFactory The session factory to use
     */
    public BibliothequeAction(SessionFactory sessionFactory) {
        super();
        setSessionFactory(sessionFactory);
    }

    // Region Getters and Setters
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

    /**
     * Adds a message to parameter <code>actionMessages</code>.
     * 
     * @param request The request to use
     * @param actionMessages The action messages to populate
     * @param messageId The message ID to use
     * @param nameIds The name IDs to use
     * @throws Exception If an error occurs
     */
    protected void addMessage(HttpServletRequest request,
        ActionMessages actionMessages,
        String messageId,
        String[] nameIds) throws Exception {
        final Locale locale = getLocale(request);
        final MessageResources messageResources = getResources(request);
        final List<String> nameIdsList = Arrays.asList(nameIds);
        final List<String> valuesList = new ArrayList<>(nameIds.length);
        String nameId;
        String value;
        final Iterator<String> iterator = nameIdsList.iterator();
        while(iterator.hasNext()) {
            nameId = iterator.next();
            value = messageResources.getMessage(locale,
                nameId);
            if(value == null) {
                value = nameId;
            }
            valuesList.add(value);
        }
        final Object[] values = valuesList.toArray();
        actionMessages.add(ActionMessages.GLOBAL_MESSAGE,
            new ActionMessage(messageId,
                values));
    }

    /**
     * Saves a message to parameter <code>actionMessages</code>.
     * 
     * @param request The request to use
     * @param actionMessages The action messages to save
     * @param messageId The message ID to use
     * @param nameIds The name IDs to use
     * @throws Exception If an error occurs
     */
    protected void saveMessage(HttpServletRequest request,
        ActionMessages actionMessages,
        String messageId,
        String[] nameIds) throws Exception {
        addMessage(request,
            actionMessages,
            messageId,
            nameIds);
        saveMessages(request,
            actionMessages);
    }
}
