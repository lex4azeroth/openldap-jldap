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

package com.novell.ldap.client;

import com.novell.ldap.client.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Enumeration;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.io.IOException;
import com.novell.ldap.LDAPObjectClassSchema;
import com.novell.ldap.LDAPAttributeSchema;

public class SchemaParser{

	String rawString;
	String names[] = null;
	String id;
	String description;
        String syntax;
        String superior;
		String nameForm;
		String objectClass;
        String superiors[];
        String required[];
        String optional[];
		String auxiliary[];
		String precluded[];
        String applies[];
        boolean single = false;
        boolean obsolete = false;
        String equality;
        String ordering;
        String substring;
        boolean collective = false;
        boolean userMod = true;
        int usage = LDAPAttributeSchema.USER_APPLICATIONS;
        int type = -1;
        int result;
        ArrayList qualifiers = new ArrayList();

	public SchemaParser( String rawString ) throws IOException {
		this.rawString = rawString;
  		StreamTokenizer st2 = new StreamTokenizer(new StringReader(rawString));
                st2.ordinaryChar('.');
                st2.ordinaryChars('0', '9');
                st2.ordinaryChar('{');
                st2.ordinaryChar('}');
                st2.ordinaryChar('_');
                st2.ordinaryChar(';');
                st2.wordChars('.', '9');
                st2.wordChars('{', '}');
                st2.wordChars('_', '_');
                st2.wordChars(';', ';');
            //First parse out the OID
            try{
              String currName;
              if( StreamTokenizer.TT_EOF !=  st2.nextToken()){
                  if(st2.ttype == '('){
                  if(StreamTokenizer.TT_WORD ==  st2.nextToken()){
                    id = st2.sval;
                  }
                  while(StreamTokenizer.TT_EOF !=  st2.nextToken()){
                    if(st2.ttype == StreamTokenizer.TT_WORD ){
                      if(st2.sval.equalsIgnoreCase("NAME")){
                        if(st2.nextToken() == '\'' ){
                            names = new String[1];
                            names[0] = st2.sval;
                        }
                        else if(st2.ttype == '(' ){
                          ArrayList nameList = new ArrayList();
                          while( st2.nextToken() == '\''){
                            if ( st2.sval != null )
                                nameList.add( st2.sval );
                          }
                          if(nameList.size() > 0){
                            names = new String[nameList.size()];
                            nameList.toArray(names);
                          }
                        }
                        continue;
                      }
                      if(st2.sval.equalsIgnoreCase("DESC")){
                        if( st2.nextToken() == '\'' ){
                          description = st2.sval;
                        }
                        continue;
                      }
                      if(st2.sval.equalsIgnoreCase("SYNTAX")){
                        result = st2.nextToken();
                        if(( result == StreamTokenizer.TT_WORD ) ||
                            (result == '\'')) //Test for non-standard schema
                        {
                          syntax = st2.sval;
                        }
                        continue;
                      }
                      if(st2.sval.equalsIgnoreCase("EQUALITY")){
                        if( st2.nextToken() == StreamTokenizer.TT_WORD ){
                          equality = st2.sval;
                        }
                        continue;
                      }
                      if(st2.sval.equalsIgnoreCase("ORDERING")){
                        if( st2.nextToken() == StreamTokenizer.TT_WORD ){
                          ordering = st2.sval;
                        }
                        continue;
                      }
                      if(st2.sval.equalsIgnoreCase("SUBSTR")){
                        if( st2.nextToken() == StreamTokenizer.TT_WORD ){
                          substring = st2.sval;
                        }
                        continue;
                      }
					  if(st2.sval.equalsIgnoreCase("FORM")){
					    if( st2.nextToken() == StreamTokenizer.TT_WORD ){
                          nameForm = st2.sval;
                        }
                        continue;
                      }
					  if(st2.sval.equalsIgnoreCase("OC")){
					    if( st2.nextToken() == StreamTokenizer.TT_WORD ){
                          objectClass = st2.sval;
                        }
                        continue;
                      }
                      if(st2.sval.equalsIgnoreCase("SUP")){
                        ArrayList values = new ArrayList();
                        st2.nextToken();
                        if(st2.ttype == '(' ){
                          st2.nextToken();
                          while(st2.ttype != ')' ){
                            if(st2.ttype != '$'){
                            	values.add(st2.sval);
                             }
                             st2.nextToken();
                          }
                        }
                      	else{
                      		values.add(st2.sval);
                        	superior = st2.sval;
                        }
                        if(values.size() > 0){
                            superiors = new String[values.size()];
                            values.toArray(superiors);
                          }
                        continue;
                      }
                      if(st2.sval.equalsIgnoreCase("SINGLE-VALUE")){
                        single = true;
                        continue;
                      }
                      if(st2.sval.equalsIgnoreCase("OBSOLETE")){
                        obsolete = true;
                        continue;
                      }
                      if(st2.sval.equalsIgnoreCase("COLLECTIVE")){
                        collective = true;
                        continue;
                      }
                      if(st2.sval.equalsIgnoreCase("NO-USER-MODIFICATION")){
                        userMod = false;
                        continue;
                      }
                      if(st2.sval.equalsIgnoreCase("MUST")){
                        ArrayList values = new ArrayList();
                        st2.nextToken();
                        if(st2.ttype == '(' ){
                          st2.nextToken();
                          while(st2.ttype != ')' ){
                            if(st2.ttype != '$'){
                            	values.add(st2.sval);
                             }
                             st2.nextToken();
                          }
                        }
                      	else
                      		values.add(st2.sval);
                        if(values.size() > 0){
                            required = new String[values.size()];
                            values.toArray(required);
                          }
                        continue;
                      }
                      if(st2.sval.equalsIgnoreCase("MAY")){
                        ArrayList values = new ArrayList();
                        st2.nextToken();
                        if(st2.ttype == '(' ){
                          st2.nextToken();
                          for(int i = 0; st2.ttype != ')'; i++ ){
                            if(st2.ttype != '$'){
                            	values.add(st2.sval);
                             }
                             st2.nextToken();
                          }
                        }
                      	else
                      		values.add(st2.sval);
                        if(values.size() > 0){
                            optional = new String[values.size()];
                            values.toArray(optional);
                          }
                        continue;
                      }
					  if(st2.sval.equalsIgnoreCase("NOT")){
                        ArrayList values = new ArrayList();
                        st2.nextToken();
                        if(st2.ttype == '(' ){
                          st2.nextToken();
                          for(int i = 0; st2.ttype != ')'; i++ ){
                            if(st2.ttype != '$'){
                            	values.add(st2.sval);
                             }
                             st2.nextToken();
                          }
                        }
                      	else
                      		values.add(st2.sval);
                        if(values.size() > 0){
                            precluded = new String[values.size()];
                            values.toArray(precluded);
                          }
                        continue;
                      }
					  if(st2.sval.equalsIgnoreCase("AUX")){
                        ArrayList values = new ArrayList();
                        st2.nextToken();
                        if(st2.ttype == '(' ){
                          st2.nextToken();
                          for(int i = 0; st2.ttype != ')'; i++ ){
                            if(st2.ttype != '$'){
                            	values.add(st2.sval);
                             }
                             st2.nextToken();
                          }
                        }
                      	else
                      		values.add(st2.sval);
                        if(values.size() > 0){
                            auxiliary = new String[values.size()];
                            values.toArray(auxiliary);
                          }
                        continue;
                      }
                      if(st2.sval.equalsIgnoreCase("ABSTRACT")){
                        type = LDAPObjectClassSchema.ABSTRACT;
                        continue;
                      }
                      if(st2.sval.equalsIgnoreCase("STRUCTURAL")){
                        type = LDAPObjectClassSchema.STRUCTURAL;
                        continue;
                      }
                      if(st2.sval.equalsIgnoreCase("AUXILIARY")){
                        type = LDAPObjectClassSchema.AUXILIARY;
                        continue;
                      }
                      if(st2.sval.equalsIgnoreCase("USAGE")){
                        if( st2.nextToken() == StreamTokenizer.TT_WORD ){
                          currName = st2.sval;
                          if( currName.equalsIgnoreCase("directoryOperation")){
                              usage = LDAPAttributeSchema.DIRECTORY_OPERATION;
                          }
                          else if( currName.equalsIgnoreCase(
                                    "distributedOperation")){
                              usage = LDAPAttributeSchema.DISTRIBUTED_OPERATION;
                          }
                          else if( currName.equalsIgnoreCase("dSAOperation")){
                              usage = LDAPAttributeSchema.DSA_OPERATION;
                          }
                          else if( currName.equalsIgnoreCase(
                                    "userApplications")){
                              usage = LDAPAttributeSchema.USER_APPLICATIONS;
                          }
                        }
                        continue;
                      }
                      if(st2.sval.equalsIgnoreCase("APPLIES")){
                        ArrayList values = new ArrayList();
                        st2.nextToken();
                        if(st2.ttype == '(' ){
                          st2.nextToken();
                          while(st2.ttype != ')' ){
                            if(st2.ttype != '$'){
                            	values.add(st2.sval);
                             }
                             st2.nextToken();
                          }
                        }
                      	else
                      		values.add(st2.sval);
                        if(values.size() > 0){
                            applies = new String[values.size()];
                            values.toArray(applies);
                          }
                        continue;
                      }
                      currName = st2.sval;
                      AttributeQualifier q = parseQualifier( st2, currName );
		      if( q != null)
                     	qualifiers.add(q);
                      continue;
                    }
                  }
		        }
              }
            }
            catch(IOException e){
              throw e;
            }
	}

	public void setRawString( String rawString ) {
		this.rawString = rawString;
	}

	public String getRawString(){
		return rawString;
	}

	public String[] getNames(){
		return names;
	}

    public Enumeration getQualifiers(){
        return new ArrayEnumeration(qualifiers.toArray());
    }

    public String getID() {
		return id;
	}
    public String getDescription() {
		return description;
	}
    public String getSyntax() {
		return syntax;
	}
    public String getSuperior() {
		return superior;
	}
    public boolean getSingle() {
		return single;
	}
    public boolean getObsolete() {
		return obsolete;
	}
    public String getEquality() {
        return equality;
    }
    public String getOrdering() {
        return ordering;
    }
    public String getSubstring() {
        return substring;
    }
    public boolean getCollective(){
        return collective;
    }
    public boolean getUserMod() {
        return userMod;
    }
    public int getUsage() {
        return usage;
    }
 	public int getType() {
		return type;
	}
 	public String[] getSuperiors() {
		return superiors;
	}
 	public String[] getRequired() {
		return required;
	}
 	public String[] getOptional() {
		return optional;
    }
	public String[] getAuxiliary() {
		return auxiliary;
    }
	public String[] getPrecluded() {
		return precluded;
    }
	public String[] getApplies() {
		return applies;
	}
	public String getNameForm() {
        return nameForm;
    }
	public String getObjectClass() {
        return nameForm;
    }

    private AttributeQualifier parseQualifier( StreamTokenizer st, String name )
            throws IOException
    {
        ArrayList values = new ArrayList(5);
        try{
            if(st.nextToken() == '\'' ){
                values.add(st.sval);
           	}
           	else if(st.ttype == '(' ){
           		while(st.nextToken() == '\'' ){
           		    values.add(st.sval);
                }
           	}
        }
        catch(IOException e){
            throw e;
        }
        String[] valArray = new String[ values.size() ];
        valArray = (String[])values.toArray( valArray);
        return new AttributeQualifier( name, valArray );
    }
}
