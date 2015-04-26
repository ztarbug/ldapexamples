package de.starwit.tomcat;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.spi.ObjectFactory;

/**
 *
 * @author markus
 *
 * Simple factory for creating an LDAPContext. Used as a Tomcat resource.
 *
 */
public class LdapContextFactory implements ObjectFactory {

	public Object getObjectInstance(final Object obj, final Name name,
			final Context nameCtx, final Hashtable<?, ?> environment)
			throws Exception {

		Hashtable<Object, Object> env = new Hashtable<Object, Object>();
		Reference reference = (Reference) obj;
		Enumeration<RefAddr> references = reference.getAll();

		while (references.hasMoreElements()) {

			RefAddr address = references.nextElement();
			String type = address.getType();
			String content = (String) address.getContent();

			switch (type) {

			case Context.INITIAL_CONTEXT_FACTORY:
				env.put(Context.INITIAL_CONTEXT_FACTORY, content);
				break;

			case Context.PROVIDER_URL:
				env.put(Context.PROVIDER_URL, content);
				break;

			case Context.SECURITY_AUTHENTICATION:
				env.put(Context.SECURITY_AUTHENTICATION, content);
				break;

			case Context.SECURITY_PRINCIPAL:
				env.put(Context.SECURITY_PRINCIPAL, content);
				break;

			case Context.SECURITY_CREDENTIALS:
				env.put(Context.SECURITY_CREDENTIALS, content);
				break;

			default:
				break;
			}
		}

		LdapContext context = new InitialLdapContext(env, null);
		return context;
	}
}
