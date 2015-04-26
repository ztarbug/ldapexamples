package de.starwit.lirejarp.ldap;

import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

public class LDAPFunctions {

	private LdapContext context;

	private final String base = "ou=users,dc=starwit,dc=de";

	private final String filterBase = "(&(objectclass=person)(uid=";

	/**
	 * name of fields that will be retrieved from LDAP for user data
	 */
	private final String[] fieldsForUserData = { "cn", "sn", "mail" };

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
				if (attr != null) {
					image = (byte[]) attr.get();
				}
			}
		} catch (NamingException e) {
			// TODO
		}

		return image;
	}

	public Map<String, String> getUserDetails(String userName) {

		Map<String, String> userData = new HashMap<String, String>();

		NamingEnumeration<SearchResult> results = searchDirectory(userName);
		if (results != null && results.hasMoreElements()) {
			SearchResult sr = (SearchResult) results.nextElement();
			if (results.hasMoreElements()) {
				// TODO more than one user found - very bad!
				return null;
			}
			Attributes attrs = sr.getAttributes();

			for (String fieldName : fieldsForUserData) {
				userData.put(fieldName, extractAttribute(attrs, fieldName));
			}
		}

		return userData;
	}

	private NamingEnumeration<SearchResult> searchDirectory(String userName) {
		SearchControls sc = new SearchControls();
		sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
		sc.setReturningObjFlag(false);
		String filter = filterBase + userName + "))";

		try {
			NamingEnumeration<SearchResult> results = context.search(base,
					filter, sc);
			return results;
		} catch (NamingException e) {
			return null; // TODO
		}
	}

	private String extractAttribute(Attributes attrs, String attrKey) {
		String attributeContent = "";
		Attribute attribute = attrs.get(attrKey);

		try {
			if (attribute != null) {
				attributeContent = (String) attribute.get();
			}
		} catch (NamingException e) {
			// attribute empty ...
		}

		return attributeContent;
	}
}
