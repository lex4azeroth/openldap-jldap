/* **************************************************************************
 * $Novell: /ldap/src/jldap/com/novell/ldap/client/LocalException.java,v 1.1 2001/01/04 20:14:48 vtag Exp $
 *
 * Copyright (C) 1999, 2000 Novell, Inc. All Rights Reserved.
 * 
 * THIS WORK IS SUBJECT TO U.S. AND INTERNATIONAL COPYRIGHT LAWS AND
 * TREATIES. USE, MODIFICATION, AND REDISTRIBUTION OF THIS WORK IS SUBJECT
 * TO VERSION 2.0.1 OF THE OPENLDAP PUBLIC LICENSE, A COPY OF WHICH IS
 * AVAILABLE AT HTTP://WWW.OPENLDAP.ORG/LICENSE.HTML OR IN THE FILE "LICENSE"
 * IN THE TOP-LEVEL DIRECTORY OF THE DISTRIBUTION. ANY USE OR EXPLOITATION
 * OF THIS WORK OTHER THAN AS AUTHORIZED IN VERSION 2.0.1 OF THE OPENLDAP
 * PUBLIC LICENSE, OR OTHER PRIOR WRITTEN CONSENT FROM NOVELL, COULD SUBJECT
 * THE PERPETRATOR TO CRIMINAL AND CIVIL LIABILITY. 
 ***************************************************************************/
 
package com.novell.ldap.client;

import com.novell.ldap.*;
import com.novell.ldap.client.Debug;

public class LocalException extends LDAPException
{
    private Message request;
    
    /**
     * Constructs a LocalException with its associated message.
     *
     *  @param message        The text providign additional error information.
     *<br><br>
     *  @param resultCode     The error result code.
     *<br><br>
     *  @param request        The Message class associated with this exception.
     */
    public LocalException(  String message,
                            int resultCode,
                            Message request)
    {
        super( message, resultCode);
        this.request = request;            
        if( Debug.LDAP_DEBUG) {
            Debug.trace( Debug.messages, "LocalException created with msg \"" +
                message + "\" and  code " + resultCode);
        }
        return;
    }
    
    /**
     * Returns the message ID of this message request.
     *
     * @return the message ID.  Returns -1 if no message
     * is associated with this exception.
     */
    public int getMessageID()
    {
        if( request == null) {
            return -1;
        }
        return request.getMessageID();
    }
    
    /**
     * Returns the message type expected as a reply to
     * the message associated with this message's request type.
     *
     * @return the message type of the expected reply.  Returns -1
     * if no reply expected.
     */
    public int getReplyType()
    {
        if( request == null) {
            return -1;
        }
        int reqType = request.getMessageType();
        int responseType = -1;
        switch( reqType) {
            case LDAPMessage.BIND_REQUEST:
                responseType = LDAPMessage.BIND_RESPONSE; 
                break;
            case LDAPMessage.UNBIND_REQUEST:
                responseType = -1; 
                break;
            case LDAPMessage.SEARCH_REQUEST:
                responseType = LDAPMessage.SEARCH_RESULT; 
                break;
            case LDAPMessage.MODIFY_REQUEST:
                responseType = LDAPMessage.MODIFY_RESPONSE; 
                break;
            case LDAPMessage.ADD_REQUEST:
                responseType = LDAPMessage.ADD_RESPONSE; 
                break;
            case LDAPMessage.DEL_REQUEST:
                responseType = LDAPMessage.DEL_RESPONSE; 
                break;
            case LDAPMessage.MODIFY_RDN_REQUEST:
                responseType = LDAPMessage.MODIFY_RDN_RESPONSE; 
                break;
            case LDAPMessage.COMPARE_REQUEST:
                responseType = LDAPMessage.COMPARE_RESPONSE; 
                break;
            case LDAPMessage.ABANDON_REQUEST:
                responseType = -1;
                break;
            case LDAPMessage.EXTENDED_REQUEST:
                responseType = LDAPMessage.EXTENDED_RESPONSE;
                break;
        }
        return responseType;
    }
}