package de.starwit.lirejarp;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

public class LDAPFunctions {

	private LdapContext context;

	private String base = "ou=users,dc=starwit,dc=de";

	private String filterBase = "(&(objectclass=person)(uid=";

	public LDAPFunctions(LdapContext context) {
		super();
		this.context = context;
	}

	public byte[] getUserImage(String userName) {

		byte[] image = new byte[0];

		SearchControls sc = new SearchControls();
		sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
		sc.setReturningObjFlag(true);

		// example (&(objectclass=person)(uid=fe_user))
		String filter = filterBase + userName + "))"; 

		try {
			NamingEnumeration<SearchResult> results = context.search(base,
					filter, sc);
			if (results.hasMoreElements()) {
				SearchResult sr = (SearchResult) results.nextElement();
				Attributes attrs = sr.getAttributes();
				Attribute attr = attrs.get("jpegPhoto");
				image = (byte[]) attr.get();
			}
		} catch (NamingException e) {

		}

		return image;
	}
}
