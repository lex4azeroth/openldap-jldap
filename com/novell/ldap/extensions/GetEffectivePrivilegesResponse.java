/* **************************************************************************
 * $Id: GetEffectivePrivilegesResponse.java,v 1.8 2000/09/11 21:05:57 vtag Exp $
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
package com.novell.ldap.extensions; 

import com.novell.ldap.*;
import com.novell.ldap.asn1.*;
import java.io.*;
 
/**
 *  Retrieves the effective rights from an GetEffectivePrivilegesResponse object.
 *
 *  <p>An object in this class is generated from an ExtendedResponse object
 *  using the ExtendedResponseFactory class.</p>
 *
 *  <p>The GetEffectivePrivilegesResponse operation uses the following OID:<br> 
 *  &nbsp;&nbsp;&nbsp;2.16.840.1.113719.1.27.100.34</p>
 *
 */
public class GetEffectivePrivilegesResponse implements ParsedExtendedResponse {
   
   // Identity returned by the server
   private int privileges;
   
   /**
    * Constructs an object from the responseValue which contains the effective
    * privileges.
    *
    *  <p>The constructor parses the responseValue which has the following 
    *  ASN.1 format:<br>
    *   responseValue ::=<br>
    *   &nbsp;&nbsp;&nbsp;&nbsp;    privileges     INTEGER</p>
    *
    * @exception IOException The responseValue could not be decoded.
    */   
   public GetEffectivePrivilegesResponse (LDAPExtendedResponse r) 
         throws IOException {
        
        // parse the contents of the reply
        byte [] returnedValue = r.getValue();
        if (returnedValue == null)
            throw new IOException("No returned value");
        
        // Create a decoder object
        LBERDecoder decoder = new LBERDecoder();
        if (decoder == null)
            throw new IOException("Decoding error");

        ASN1Integer asn1_privileges = (ASN1Integer)decoder.decode(returnedValue);        
        if (asn1_privileges == null)
            throw new IOException("Decoding error");
            
            
        privileges = asn1_privileges.getInt();
   }
   
   /** 
    * Returns the effective privileges.
    * 
    * @return A flag which is a combination of zero or more privilege flags as
    * returned by the server.
    */
   public int getPrivileges() {
        return privileges;
   }
    
}
