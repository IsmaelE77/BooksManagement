package servlet;

import dao.BookDAO;
import dao.LoginDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.AppUser;
import model.LoginViewModel;

import javax.security.sasl.AuthenticationException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.Serial;

@WebServlet(description = "Login" , urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    private LoginDao loginDao;
    public void init() {
        DataSource dataSource = (DataSource) getServletContext().getAttribute("datasource");
        loginDao = new LoginDao(dataSource);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.sendRedirect(request.getContextPath()+"/Login.jsp");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String identifier = request.getParameter("identifier");
        String password = request.getParameter("password");

        LoginViewModel loginBean = new LoginViewModel();

        if(!loginBean.setIdentifier(identifier)) {
            response.sendRedirect(request.getContextPath()+"/Login.jsp");
            return;
        }

        loginBean.setPassword(password);
        try {
            AppUser user = loginDao.authenticateUser(loginBean);
            if (user != null) {
                HttpSession session = request.getSession(); // Creating a session
                session.setAttribute("user", user); // Setting session attribute
                response.sendRedirect(request.getContextPath() + "/"); // Redirect to a dashboard page after successful login
            } else {
                response.sendRedirect(request.getContextPath()+"/Login.jsp");
                return;
            }
        } catch (AuthenticationException e) {
            log("Authentication failed: " + e.getMessage());
            response.sendRedirect("/error.jsp"); // Redirect to an error page
        }
    } //End of doPost()

}
