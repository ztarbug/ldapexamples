package de.starwit.lirejarp;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.naming.ldap.LdapContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Returns an image (binary) of currently logged in user.
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

		//String userName = req.getParameter("userName");
		String userName = req.getRemoteUser(); //TODO what if null
		LDAPFunctions ldapFunctions = new LDAPFunctions(context);

		OutputStream out = resp.getOutputStream();
		resp.setContentType("image/png");

		byte[] image = ldapFunctions.getUserImage(userName);
		
		if (image.length == 0) {
			image = loadDefaultImage();
		}

		sendImageToOuputStream(out, image);
		out.flush();
		out.close();
	}

	/**
	 * Splits given image byte array to 1k blocks and send to given output stream.
	 * 
	 * @param out Stream where image bytes to send to
	 * @param image image bytes 
	 * @throws IOException
	 */
	private void sendImageToOuputStream(OutputStream out, byte[] image)
			throws IOException {
		ByteArrayInputStream imageStream = new ByteArrayInputStream(image);
		byte[] buf = new byte[1024];
		int count = 0;
		while ((count = imageStream.read(buf)) >= 0) {
			out.write(buf, 0, count);
		}
	}

	/**
	 * Returns a default image packed with this app.
	 * @return
	 */
	private byte[] loadDefaultImage() {
		System.out.println(getServletContext().getRealPath("/WEB-INF/default-user.png"));
		// open image
		File imageFile = new File(getServletContext().getRealPath("/WEB-INF/default-user.png"));

		try {
			FileReader fileReader = new FileReader(imageFile);
			byte[] fileData = new byte[(int) imageFile.length()];
		    DataInputStream dis = new DataInputStream(new FileInputStream(imageFile));
		    dis.readFully(fileData);
		    dis.close();
			fileReader.close();
			return fileData;
		} catch (IOException e) {
			System.out.println("could not load default image");
			return new byte[0];
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
