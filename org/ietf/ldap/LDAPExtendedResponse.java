/* **************************************************************************
 * $Novell: /ldap/src/jldap/com/novell/ldap/LDAPExtendedResponse.java,v 1.24 2001/03/01 00:29:51 cmorris Exp $
 *
 * Copyright (C) 1999, 2000, 2001 Novell, Inc. All Rights Reserved.
 *
 * THIS WORK IS SUBJECT TO U.S. AND INTERNATIONAL COPYRIGHT LAWS AND
 * TREATIES. USE, MODIFICATION, AND REDISTRIBUTION OF THIS WORK IS SUBJECT
 * TO VERSION 2.0.7 OF THE OPENLDAP PUBLIC LICENSE, A COPY OF WHICH IS
 * AVAILABLE AT HTTP://WWW.OPENLDAP.ORG/LICENSE.HTML OR IN THE FILE "LICENSE"
 * IN THE TOP-LEVEL DIRECTORY OF THE DISTRIBUTION. ANY USE OR EXPLOITATION
 * OF THIS WORK OTHER THAN AS AUTHORIZED IN VERSION 2.0.7 OF THE OPENLDAP
 * PUBLIC LICENSE, OR OTHER PRIOR WRITTEN CONSENT FROM NOVELL, COULD SUBJECT
 * THE PERPETRATOR TO CRIMINAL AND CIVIL LIABILITY.
 ******************************************************************************/

package org.ietf.ldap;

/**
 *
 *  Encapsulates the response returned by an LDAP server on an
 *  asynchronous extended operation request.
 *
 * @see com.novell.ldap.LDAPExtendedResponse
 */
public class LDAPExtendedResponse extends LDAPResponse {

    com.novell.ldap.LDAPExtendedResponse response;

    /**
     * Creates an LDAPExtendedResponse from a
     *      com.novell.ldap.LDAPExtendedResponse
     */
    /* package */
    LDAPExtendedResponse(com.novell.ldap.LDAPExtendedResponse response)
    {
        super( response);
        this.response = response;
    }

    /**
     * Gets the com.novell.ldap.LDAPExtendedResponse object
     */
    /* package */
    com.novell.ldap.LDAPExtendedResponse getWrappedObject()
    {
        return response;
    }

    /**
     * Returns the message identifier of the response.
     *
     * @return OID of the response.
     */
    public String getID()
    {
        return response.getID();
    }

    /**
     * Returns the value part of the response in raw bytes.
     *
     * @return The value of the response.
     */
    public byte[] getValue()
    {
        return response.getValue();
    }
}