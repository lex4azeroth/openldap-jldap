
package org.ietf.asn1.ldap;

import java.io.*;
import org.ietf.asn1.*;

/**
 *       SearchResultDone ::= [APPLICATION 5] LDAPResult
 */
public class SearchResultDone extends LDAPResult {

   //*************************************************************************
   // Constructors for SearchResultDone
   //*************************************************************************

   /**
    * The only time a client will create a SearchResultDone is when it is
    * decoding it from an InputStream
    */
   public SearchResultDone(ASN1Decoder dec, InputStream in, int len)
      throws IOException
   {
      super(dec, in, len);
   }

   //*************************************************************************
   // Accessors
   //*************************************************************************

   /**
    * Override getIdentifier to return an application-wide id.
    */
   public ASN1Identifier getIdentifier()
   {
      return new ASN1Identifier(ASN1Identifier.APPLICATION, true,
                                ProtocolOp.SEARCH_RESULT_DONE);
   }

}

