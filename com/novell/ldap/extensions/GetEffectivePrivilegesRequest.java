/* **************************************************************************
 * $Id: GetEffectivePrivilegesRequest.java,v 1.9 2000/09/25 17:36:04 fzhao Exp $
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
 *
 * Returns the effective rights of one object to a
 * specific attribute of another object.
 *
 * <p>To use this class, you must instantiate an object of this class and then 
 * call the extendedOperation method with this object as the required
 * LDAPExtendedOperation parameter.</p>
 *
 * <p>The returned LDAPExtendedResponse object can then be converted to
 * a GetEffectivePrivilegesResponse object. The GetEffectivePrivilegesResponse class 
 * contains methods for retrieving the effective rights.</p>
 *
 * <p>The GetEffectivePrivilegesRequest operation uses the following OID:<br>
 *  &nbsp;&nbsp;&nbsp;2.16.840.1.113719.1.27.100.33</p>
 *
 * <p>The RequestValue has the following ASN.1 format:<br>
 *
 *  requestValue ::=<br>
 *  &nbsp;&nbsp;&nbsp;&nbsp;    dn          LDAPDN<br>
 *  &nbsp;&nbsp;&nbsp;&nbsp;    trusteeDN   LDAPDN<br>
 *  &nbsp;&nbsp;&nbsp;&nbsp;    attrName    LDAPDN </p>    
 */
 public class GetEffectivePrivilegesRequest extends LDAPExtendedOperation {
 
    /**
    * Constructs an extended operation object for checking effective rights.
    *
    * @param dn        The distinguished name of the entry whose attribute is 
    *                  being checked.
    *<br><br>
    * @param trusteeDN The distinguished name of the entry whose trustee rights 
    *                  are being returned
    *<br><br>
    * @param attrName  The LDAP attribute name.
    *
    * @exception LDAPException A general exception which includes an error 
    *                          message and an LDAP error code.
    */  

    public GetEffectivePrivilegesRequest(String dn, String trusteeDN, String attrName) 
                throws LDAPException {
        
        super(NamingContextConstants.GET_EFFECTIVE_PRIVILEGES_REQ, null);
        
        try {
            
            if ( (dn == null) )
                throw new LDAPException("Invalid parameter",
                                    LDAPException.PARAM_ERROR);
         
         ByteArrayOutputStream encodedData = new ByteArrayOutputStream();
         LBEREncoder encoder  = new LBEREncoder();
                               
          ASN1OctetString asn1_dn = new ASN1OctetString(dn);
          ASN1OctetString asn1_trusteeDN = new ASN1OctetString(trusteeDN);
          ASN1OctetString asn1_attrName = new ASN1OctetString(attrName);
            
            asn1_dn.encode(encoder, encodedData);
            asn1_trusteeDN.encode(encoder, encodedData);
            asn1_attrName.encode(encoder, encodedData);
            
            setValue(encodedData.toByteArray());
                   
        }
      catch(IOException ioe) {
         throw new LDAPException("Encoding Error",
                                 LDAPException.ENCODING_ERROR);
      }
        
     }
}
