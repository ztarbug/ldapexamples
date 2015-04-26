package de.starwit.lirejarp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.Resource;
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
 * Servlet implementation class GetUserImage
 */
@WebServlet("/GetUserImage")
public class GetUserImage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name = "ldap/LdapResource")
	private LdapContext context;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetUserImage() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String userName = req.getParameter("userName");

		LDAPFunctions functions = new LDAPFunctions(context);
		byte[] image = functions.getUserImage(userName);
		if (image.length > 0) {
			ByteArrayInputStream imageStream = new ByteArrayInputStream(image);
			resp.setContentType("image/png");
			OutputStream out = resp.getOutputStream();
			byte[] buf = new byte[1024];
			int count = 0;
			while ((count = imageStream.read(buf)) >= 0) {
				out.write(buf, 0, count);
			}
			out.flush();
			out.close();
		} else {
			System.out.println("not found");
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
