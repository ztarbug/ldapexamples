package de.starwit.lirejarp.userdata;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.naming.ldap.LdapContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.starwit.lirejarp.ldap.LDAPFunctions;

/**
 * Servlet implementation class GetUserData
 */
@WebServlet("/GetUserData")
public class GetUserData extends HttpServlet {
	
	private static final long serialVersionUID = -5543191533200075769L;
	
	@Resource(name = "ldap/LdapResource")
	private LdapContext context;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUserData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		String userName = req.getRemoteUser();
		
		LDAPFunctions ldapFunctions = new LDAPFunctions(context);
		Map<String, String> userData = ldapFunctions.getUserDetails(userName);
		
		Set<String> keys = userData.keySet();
		out.write("{");
		for (String key : keys) {
			out.write(key + " : " + userData.get(key) + ",");
		}
		out.write("}");
		
		out.flush();
		out.close();
	}

}
