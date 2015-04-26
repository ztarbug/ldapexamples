package de.starwit.lirejarp;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PrintJNDIServlet
 */
@WebServlet("/PrintJNDIServlet")
public class PrintJNDIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name = "ldap/LdapResource")
	private LdapContext context;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PrintJNDIServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		PrintWriter out = resp.getWriter();
		out.print("Printing User's details<br />");

		String base = "ou=users,dc=starwit,dc=de";

		SearchControls sc = new SearchControls();
		sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
		sc.setReturningObjFlag(true);
		String filter = "(&(objectclass=person)(uid=fe_user*))";

		try {
			NamingEnumeration<SearchResult> results = context.search(base,filter, sc);
			
			while (results.hasMoreElements()) {
				SearchResult sr = (SearchResult) results.nextElement();
				Attributes attrs = sr.getAttributes();

				NamingEnumeration<? extends Attribute> attributeNames = attrs.getAll();
				out.print("<ul>");
				while (attributeNames.hasMoreElements()) {
					Attribute attribute = (Attribute) attributeNames.nextElement();
					if("jpegPhoto".equals(attribute.getID())) {
						byte[] content = (byte[]) attribute.get();
						BufferedImage image = ImageIO.read(new ByteArrayInputStream(content));
						out.print("<li>");
						out.print(image.toString());
						out.print("</li>");
						
					} else {
						out.print(convertAttributeToHTML(attribute));
					}
				}
				out.print("</ul>");
/*
				Attribute attr = attrs.get("jpegPhoto");
				byte[] content = (byte[]) attr.get();

				BufferedImage image = ImageIO.read(new ByteArrayInputStream(
						content));
				ImageIO.write(image, "PNG", new File("test.png"));*/
			}
			//context.close();
		} catch (NamingException e) {
			System.out.println("Exception");
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
	
	private static String convertAttributeToHTML(Attribute attr) throws NamingException {
		StringBuffer sb = new StringBuffer();
		sb.append("<li>");
		sb.append(attr.getID() + ": " + attr.get());
		sb.append("</li>");
		return sb.toString();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
