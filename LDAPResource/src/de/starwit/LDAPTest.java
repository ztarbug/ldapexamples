package de.starwit;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.print.AttributeException;

public class LDAPTest {

	public static void main(String[] args) throws NamingException, IOException {
		
		Hashtable<Object, Object> env = new Hashtable<Object, Object>();
		
		env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL,"ldap://127.0.0.1:10389");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
		env.put(Context.SECURITY_CREDENTIALS, "secret");
		env.put("java.naming.ldap.attributes.binary", "jpegPhoto");
		
		DirContext ctx = new InitialDirContext(env);
		String base = "ou=users,dc=starwit,dc=de";
		
        SearchControls sc = new SearchControls();
        sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
        sc.setReturningObjFlag(true);

        String filter = "(&(objectclass=person)(uid=fe_user*))";
        NamingEnumeration<SearchResult> results = ctx.search(base, filter, sc);
        
        while (results.hasMoreElements()) {
			SearchResult sr = (SearchResult) results.nextElement();
			Attributes attrs = sr.getAttributes();
			
			NamingEnumeration<? extends Attribute> attributeNames = attrs.getAll();
			while (attributeNames.hasMoreElements()) {
				Attribute attribute = (Attribute) attributeNames.nextElement();
				System.out.println(attribute.getID() + " : " + attribute.get(0));
				System.out.println(attribute.get().getClass().getCanonicalName());
			}
			
			Attribute attr = attrs.get("jpegPhoto");
			byte[] content = (byte[])attr.get();
			System.out.println("Size " + content.length);

			BufferedImage image = ImageIO.read( new ByteArrayInputStream( content) );
			ImageIO.write(image, "PNG", new File("test.png"));
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
		}
        ctx.close();

	}
}
