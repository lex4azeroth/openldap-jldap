/* **************************************************************************
 * $OpenLDAP$
 *
 * Copyright (C) 1999, 2000, 2001 Novell, Inc. All Rights Reserved.
 *
 * THIS WORK IS SUBJECT TO U.S. AND INTERNATIONAL COPYRIGHT LAWS AND
 * TREATIES. USE, MODIFICATION, AND REDISTRIBUTION OF THIS WORK IS SUBJECT
 * TO VERSION 2.0.1 OF THE OPENLDAP PUBLIC LICENSE, A COPY OF WHICH IS
 * AVAILABLE AT HTTP://WWW.OPENLDAP.ORG/LICENSE.HTML OR IN THE FILE "LICENSE"
 * IN THE TOP-LEVEL DIRECTORY OF THE DISTRIBUTION. ANY USE OR EXPLOITATION
 * OF THIS WORK OTHER THAN AS AUTHORIZED IN VERSION 2.0.1 OF THE OPENLDAP
 * PUBLIC LICENSE, OR OTHER PRIOR WRITTEN CONSENT FROM NOVELL, COULD SUBJECT
 * THE PERPETRATOR TO CRIMINAL AND CIVIL LIABILITY.
 ******************************************************************************/
package com.novell.ldap.rfc2251;

import java.io.IOException;
import java.io.InputStream;
import com.novell.ldap.asn1.*;
import com.novell.ldap.client.Debug;

/*
 *       SearchResultEntry ::= [APPLICATION 4] SEQUENCE {
 *               objectName      LDAPDN,
 *               attributes      PartialAttributeList }
 */
public class RfcSearchResultEntry extends ASN1Sequence {

   //*************************************************************************
   // Constructors for SearchResultEntry
   //*************************************************************************

   /**
    * The only time a client will create a SearchResultEntry is when it is
    * decoding it from an InputStream
    */
   public RfcSearchResultEntry(ASN1Decoder dec, InputStream in, int len)
      throws IOException
   {
      super(dec, in, len);

      // Decode objectName
//      set(0, new RfcLDAPDN(((ASN1OctetString)get(0)).getContent()));

      // Create PartitalAttributeList. This does not need to be decoded, only
      // typecast.
//      set(1, new PartitalAttributeList());


   }

   //*************************************************************************
   // Accessors
   //*************************************************************************

   /**
    *
    */
   public ASN1OctetString getObjectName()
   {
        if( Debug.LDAP_DEBUG) {
            Debug.trace( Debug.messages, "RfcSearchResultEntry - Object name: " +
                ((ASN1OctetString)get(0)).toString());
        }
      return (ASN1OctetString)get(0);
   }

   /**
    *
    */
   public ASN1Sequence getAttributes()
   {
      return (ASN1Sequence)get(1);
   }

   /**
    * Override getIdentifier to return an application-wide id.
    */
   public ASN1Identifier getIdentifier()
   {
      return new ASN1Identifier(ASN1Identifier.APPLICATION, true,
                                RfcProtocolOp.SEARCH_RESULT_ENTRY);
   }

}

