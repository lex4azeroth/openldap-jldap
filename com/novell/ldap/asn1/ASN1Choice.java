
package org.ietf.asn1;

import java.io.*;

/**
 * The ASN1Choice object represents the choice of any ASN1Object. All
 * ASN1Object methods are delegated to the object this ASN1Choice contains.
 *
 * Can a CHOICE contain anything BUT a TAGGED Type?
 */
public class ASN1Choice extends ASN1Object {

	private ASN1Object content;

	//*************************************************************************
	// Constructors for ASN1Choice
	//*************************************************************************

	/**
	 * Constructs an ASN1Choice object using an ASN1Object value.
	 */
	public ASN1Choice(ASN1Object content)
	{
		this.content = content;
	}

	/**
	 * No arg Constructor. This is used by Filter, who subsequently sets the
	 * content after parsing the RFC 2254 Search Filter String.
	 */
	protected ASN1Choice()
	{
	}

	/**
	 * Constructs an ASN1Choice object by decoding data from an input stream.
	 */
	public ASN1Choice(ASN1Decoder dec, InputStream in, int len)
		throws IOException
	{
		// how do we decode a choice?
	}

	//*************************************************************************
	// ASN1Object implementation
	//*************************************************************************

	/**
	 * Encodes the contents of this ASN1Choice directly to an output stream.
	 */
	public void encode(ASN1Encoder enc, OutputStream out)
		throws IOException
	{
		content.encode(enc, out);
	}

	//*************************************************************************
	// ASN1Choice specific methods
	//*************************************************************************

	/**
	 * Returns the CHOICE value stored in this ASN1Choice.
	 */
	public ASN1Object getContent()
	{
		return content;
	}

	/**
	 * Sets the CHOICE value stored in this ASN1Choice.
	 */
	protected void setContent(ASN1Object content)
	{
		this.content = content;
	}

	/**
	 * Override to return the ASN1Identifier of the choice object.
	 */
	public ASN1Identifier getIdentifier()
	{
		return content.getIdentifier();
	}

	/**
	 * Override to set the ASN1Identifier of the choice object.
	 */
	public void setIdentifier(ASN1Identifier id)
	{
		content.setIdentifier(id);
	}

	/**
	 * Return a String representation of this ASN1Object.
	 */
	public String toString()
	{
		return content.toString();
	}

}

