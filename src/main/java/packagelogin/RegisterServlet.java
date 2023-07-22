package packagelogin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String userData = request.getParameter("userData");

		if (firstName.isEmpty() || lastName.isEmpty() || userData.isEmpty() || username.isEmpty()
				|| password.isEmpty()) {
			response.sendRedirect("register.jsp?error=empty");
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
			
			String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO users (firstname, lastname, username, password, userdata) VALUES (?, ?, ?, ?, ?)");
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			statement.setString(3, username);
			statement.setString(4, hashedPassword);
			statement.setString(5, userData);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				response.sendRedirect("login.jsp");
			}
			
			connection.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendRedirect("register.jsp?error=db");
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}