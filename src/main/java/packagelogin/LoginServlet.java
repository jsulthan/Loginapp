package packagelogin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (username.isEmpty() || password.isEmpty()) {
			response.sendRedirect("login.jsp?error=empty");
			return;
		}
		
		Connection connection = null;

		try {
			String dbURL = "jdbc:mysql://login.czk2fqrgbvyw.us-east-1.rds.amazonaws.com:3306/login";
			String dbUser = "admin";
			String dbPassword = "jamaal12345";
			connection = DriverManager.getConnection(dbURL, dbUser, dbPassword);
			
			System.out.println(connection.getAutoCommit());
			connection.setAutoCommit(false);
			
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
			statement.setString(1, username);

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				String storedHashedPassword = result.getString("password");

				if (BCrypt.checkpw(password, storedHashedPassword)) {
					String firstName = result.getString("firstName");
					String userData = result.getString("userData");

					HttpSession session = request.getSession();
					session.setAttribute("firstName", firstName);
					session.setAttribute("UserData", userData);

					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
					out.println("<h3>Welcome " + firstName + "!!</h3>");
					out.println("<p>Your favorite food is: " + userData + "</p>");
				} else {
					response.sendRedirect("login.jsp?error=invalid");
				}
			} else {
				response.sendRedirect("login.jsp?error=db");
			}
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}