// Fichier DTO.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliothequeBackEnd.dto;

import java.beans.BeanInfo;
import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.Constants;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Classe de base pour tous les DTOs.
 *
 * @author Gilles Benichou
 */
public class DTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Crée un DTO.
     */
    protected DTO() {
        super();
    }

    /**
     * Indicates whether some other object is "equal to" this one. <br />
     * The equals method implements an equivalence relation on non-<code>null</code> object references: <br />
     * <br />
     * <ul>
     * <li>It is reflexive: for any non-<code>null</code> reference value x, x.equals(x) should return <code>true</code>.
     * <li>It is symmetric: for any non-<code>null</code> reference values x and y, x.equals(y) should return <code>true</code> if and only if
     * y.equals(x) returns <code>true</code>.
     * <li>It is transitive: for any non-<code>null</code> reference values x, y, and z, if x.equals(y) returns <code>true</code> and
     * y.equals(z) returns <code>true</code>, then x.equals(z) should return <code>true</code>.
     * <li>It is consistent: for any non-<code>null</code> reference values x and y, multiple invocations of x.equals(y) consistently return
     * <code>true</code> or consistently return <code>false</code>, provided no information used in equals comparisons on the objects is
     * modified.
     * <li>For any non-<code>null</code> reference value x, x.equals(<code>null</code>) should return <code>false</code>.
     * </ul>
     * <br />
     * <br />
     * The equals method for class Object implements the most discriminating possible equivalence relation on objects; that is, for any
     * non-<code>null</code> reference values x and y, this method returns <code>true</code> if and only if x and y refer to the same object
     * (x == y has the value <code>true</code>). <br />
     * <br />
     * Note that it is generally necessary to override the hashCode method whenever this method is overridden, so as to maintain the general
     * contract for the hashCode method, which states that equal objects must have equal hash codes.
     * 
     * @param obj The reference object with which to compare
     * @return <code>true</code> if this object is the same as the obj argument; <code>false</code> otherwise
     */
    @Override
    public boolean equals(Object obj) {
        boolean equals = this == obj;
        if(!equals) {
            equals = obj != null
                && obj instanceof DTO;
        }
        return equals;
    }

    /**
     * Returns a hash code value for the object. This method is supported for the benefit of hashtables such as those provided by
     * {@link java.util.Hashtable}. <br />
     * <br />
     * The general contract of hashCode is:
     * <ul>
     * <li>Whenever it is invoked on the same object more than once during an execution of a Java application, the hashCode method must
     * consistently return the same integer, provided no information used in equals comparisons on the object is modified. This integer need not
     * remain consistent from one execution of an application to another execution of the same application.
     * <li>If two objects are equal according to the equals(Object) method, then calling the hashCode method on each of the two objects must
     * produce the same integer result.
     * <li>It is not required that if two objects are unequal according to the equals(Object) method, then calling the hashCode method on each
     * of the two objects must produce distinct integer results. However, the programmer should be aware that producing distinct integer results
     * for unequal objects may improve the performance of hashtables.
     * </ul>
     * <br />
     * <br />
     * As much as is reasonably practical, the hashCode method defined by class Object does return distinct integers for distinct objects (this
     * is typically implemented by converting the internal address of the object into an integer, but this implementation technique is not
     * required by the Java<small><sup>TM</sup></small> programming language).
     * 
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(15,
            5);
        return hashCodeBuilder.toHashCode();
    }

    /**
     * Returns a string representation of the object. In general, the <code>toString</code> method returns a string that "textually represents"
     * this object. The result should be a concise but informative representation that is easy for a person to read. It is recommended that all
     * subclasses override this method.
     * <p>
     * The {@link java.lang.Object#toString()} method for class {@link java.lang.Object} returns a string consisting of the name of the class of
     * which the object is an instance, the at-sign character '<code>@</code>', and the unsigned hexadecimal representation of the hash code of
     * the object. In other words, this method returns a string equal to the value of:
     * <blockquote>
     * 
     * <pre>
     * getClass().getName()
     *         + '@'
     *         + Integer.toHexString(hashCode())
     * </pre>
     * 
     * </blockquote>
     * </p>
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        String string = Constants.NEW_LINE.toString()
            + Constants.OPENING_BRACE.toString();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            List<PropertyDescriptor> properties = Arrays.asList(propertyDescriptors);
            String propertyName = null;
            Method getter = null;
            for(PropertyDescriptor property : properties) {
                if(!property.getPropertyType().isInstance(Collections.EMPTY_SET)) {
                    string += Constants.NEW_LINE.toString()
                        + Constants.SPACE.toString()
                        + Constants.SPACE.toString();
                    try {
                        if(property instanceof IndexedPropertyDescriptor) {
                            getter = ((IndexedPropertyDescriptor) property).getIndexedReadMethod();
                        } else {
                            getter = property.getReadMethod();
                        }
                        propertyName = property.getName();
                        string += propertyName
                            + Constants.SPACE.toString()
                            + Constants.EQUALS.toString()
                            + Constants.SPACE.toString()
                            + getter.invoke(this,
                                (Object) null)
                            + Constants.COMMA.toString();
                    } catch(NullPointerException nullPointerException) {
                        // Nothing to do.
                    } catch(IllegalAccessException illegalAccessException) {
                        // Nothing to do.
                    } catch(IllegalArgumentException illegalArgumentException) {
                        // Nothing to do.
                    } catch(InvocationTargetException invocationTargetException) {
                        // Nothing to do.
                    }
                }
            }
        } catch(NullPointerException nullPointerException) {
            // Nothing to do.
        } catch(IntrospectionException introspectionException) {
            // Nothing to do.
        }
        string += Constants.NEW_LINE.toString()
            + Constants.CLOSING_BRACE.toString();
        return string;
    }
}
