// Fichier Emailer.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-11

package ca.qc.collegeahuntsic.bibliotheque.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import ca.qc.collegeahuntsic.bibliotheque.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Cette classe permet d'envoyer des courriels à l'administrateur.< br/>
 * Les courriels sont envoyés sous forme de Thread.
 * 
 * @author Gilles Benichou
 */
public class Emailer implements Runnable {
    private static final Log LOGGER = LogFactory.getLog(Emailer.class);

    private static Properties emailerProperties;

    private String fromAddress;

    private String fromName;

    private Map<String, String> to;

    private Map<String, String> cc;

    private Map<String, String> bcc;

    private String subject;

    private String content;

    private String contentType;

    static {
        Emailer.loadProperties();
    }

    /**
     * Default constructor.
     */
    public Emailer() {
        super();
    }

    // Region Getters and Setters
    /**
     * Getter de la variable d'instance <code>this.fromAddress</code>.
     *
     * @return La variable d'instance <code>this.fromAddress</code>
     */
    private String getFromAddress() {
        return this.fromAddress;
    }

    /**
     * Setter de la variable d'instance <code>this.fromAddress</code>.
     *
     * @param fromAddress La valeur à utiliser pour la variable d'instance <code>this.fromAddress</code>
     */
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    /**
     * Getter de la variable d'instance <code>this.fromName</code>.
     *
     * @return La variable d'instance <code>this.fromName</code>
     */
    private String getFromName() {
        return this.fromName;
    }

    /**
     * Setter de la variable d'instance <code>this.fromName</code>.
     *
     * @param fromName La valeur à utiliser pour la variable d'instance <code>this.fromName</code>
     */
    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    /**
     * Getter de la variable d'instance <code>this.to</code>.
     *
     * @return La variable d'instance <code>this.to</code>
     */
    private Map<String, String> getTo() {
        return this.to;
    }

    /**
     * Setter de la variable d'instance <code>this.to</code>.
     *
     * @param to La valeur à utiliser pour la variable d'instance <code>this.to</code>
     */
    private void setTo(Map<String, String> to) {
        this.to = to;
    }

    /**
     * Getter de la variable d'instance <code>this.cc</code>.
     *
     * @return La variable d'instance <code>this.cc</code>
     */
    private Map<String, String> getCc() {
        return this.cc;
    }

    /**
     * Setter de la variable d'instance <code>this.cc</code>.
     *
     * @param cc La valeur à utiliser pour la variable d'instance <code>this.cc</code>
     */
    private void setCc(Map<String, String> cc) {
        this.cc = cc;
    }

    /**
     * Getter de la variable d'instance <code>this.bcc</code>.
     *
     * @return La variable d'instance <code>this.bcc</code>
     */
    private Map<String, String> getBcc() {
        return this.bcc;
    }

    /**
     * Setter de la variable d'instance <code>this.bcc</code>.
     *
     * @param bcc La valeur à utiliser pour la variable d'instance <code>this.bcc</code>
     */
    private void setBcc(Map<String, String> bcc) {
        this.bcc = bcc;
    }

    /**
     * Getter de la variable d'instance <code>this.subject</code>.
     *
     * @return La variable d'instance <code>this.subject</code>
     */
    private String getSubject() {
        return this.subject;
    }

    /**
     * Setter de la variable d'instance <code>this.subject</code>.
     *
     * @param subject La valeur à utiliser pour la variable d'instance <code>this.subject</code>
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Getter de la variable d'instance <code>this.content</code>.
     *
     * @return La variable d'instance <code>this.content</code>
     */
    private String getContent() {
        return this.content;
    }

    /**
     * Setter de la variable d'instance <code>this.content</code>.
     *
     * @param unContent La valeur à utiliser pour la variable d'instance <code>this.content</code>
     * @param unContentType La valeur à utiliser pour la variable d'instance <code>this.contentType</code>
     */
    public void setContent(String unContent,
        String unContentType) {
        this.content = unContent;
        setContentType(unContentType);
    }

    /**
     * Setter de la variable d'instance <code>this.content</code>.<br />
     * Le content type est mis par défaut à "text/plain".
     *
     * @param content La valeur à utiliser pour la variable d'instance <code>this.content</code>
     */
    public void setContent(String content) {
        setContent(content,
            "text/plain");
    }

    /**
     * Getter de la variable d'instance <code>this.contentType</code>.
     *
     * @return La variable d'instance <code>this.contentType</code>
     */
    private String getContentType() {
        return this.contentType;
    }

    /**
     * Setter de la variable d'instance <code>this.contentType</code>.
     *
     * @param contentType La valeur à utiliser pour la variable d'instance <code>this.contentType</code>
     */
    private void setContentType(String contentType) {
        this.contentType = contentType;
    }

    // EndRegion Getters and Setters

    /**
     * Loads the email resource bundle properties.
     */
    private static void loadProperties() {
        Emailer.emailerProperties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if(classLoader == null) {
            classLoader = Emailer.class.getClassLoader();
        }
        try(
            InputStream inputStream = classLoader.getResourceAsStream(Constants.MAIL_RESOURCE_BUNDLE_NAME)) {
            Emailer.emailerProperties.load(inputStream);
        } catch(IOException ioException) {
            Emailer.LOGGER.error(ioException);
        }
    }

    /**
     * Ajoute des destinataires au champ "To:".
     * 
     * @param email L'addresse courriel du destinataire
     * @param name Le nom du destinataire
     */
    public void addTo(String email,
        String name) {
        if(getTo() == null) {
            setTo(new Hashtable<String, String>());
        }
        getTo().put(email,
            name);
    }

    /**
     * Ajoute des destinataires au champ "Cc:".
     * 
     * @param email L'addresse courriel du destinataire
     * @param name Le nom du destinataire
     */
    public void addCc(String email,
        String name) {
        if(getCc() == null) {
            setCc(new Hashtable<String, String>());
        }
        getCc().put(email,
            name);
    }

    /**
     * Ajoute des destinataires au champ "Bcc:".
     * 
     * @param email L'addresse courriel du destinataire
     * @param name Le nom du destinataire
     */
    public void addBcc(String email,
        String name) {
        if(getBcc() == null) {
            setBcc(new Hashtable<String, String>());
        }
        getBcc().put(email,
            name);
    }

    /**
     * Sends the email to the recipients. To be used by thread.
     */
    @Override
    public void run() {
        send();
    }

    /**
     * Sends the email to the recipients.
     */
    private void send() {
        try {
            final String user = Emailer.emailerProperties.getProperty(Constants.MAIL_USER_KEY);
            final String password = Emailer.emailerProperties.getProperty(Constants.MAIL_PASSWORD_KEY);
            final Session session = Session.getInstance(Emailer.emailerProperties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user,
                            password);
                    }
                });
            final MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(getFromAddress(),
                getFromName()));
            message.setSubject(getSubject());
            message.setContent(getContent(),
                getContentType());
            if(getTo() != null) {
                InternetAddress address = null;
                for(Map.Entry<String, String> entry : getTo().entrySet()) {
                    address = new InternetAddress(entry.getKey(),
                        entry.getValue());
                    message.addRecipient(RecipientType.TO,
                        address);
                }
            }
            if(getCc() != null) {
                InternetAddress address = null;
                for(Map.Entry<String, String> entry : getCc().entrySet()) {
                    address = new InternetAddress(entry.getKey(),
                        entry.getValue());
                    message.addRecipient(RecipientType.CC,
                        address);
                }
            }
            if(getBcc() != null) {
                InternetAddress address = null;
                for(Map.Entry<String, String> entry : getBcc().entrySet()) {
                    address = new InternetAddress(entry.getKey(),
                        entry.getValue());
                    message.addRecipient(RecipientType.BCC,
                        address);
                }
            }
            Transport.send(message);

        } catch(MessagingException messagingException) {
            Emailer.LOGGER.error(messagingException);
        } catch(UnsupportedEncodingException unsupportedEncodingException) {
            Emailer.LOGGER.error(unsupportedEncodingException);
        }
    }
}
