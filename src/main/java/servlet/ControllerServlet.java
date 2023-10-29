package servlet;

import dao.BookDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.AppUser;
import model.Book;
import model.Role;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;
import java.util.List;


@WebServlet(description = "book store controller" , urlPatterns = {"/"})
public class ControllerServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    private BookDAO bookDAO;

    public void init() {
        DataSource dataSource = (DataSource) getServletContext().getAttribute("datasource");
        bookDAO = new BookDAO(dataSource);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        AppUser user = null;
        HttpSession session = request.getSession(false);

        if(session != null)
            user = (AppUser) session.getAttribute("user");

        try {
            switch (action) {
                case "/new":
                    if (hasAdminRole(user)) {
                        showNewForm(request, response);
                    } else {
                        accessDeniedPage(request,response);
                    }
                    break;
                case "/insert":
                    if (hasAdminRole(user)) {
                        insertBook(request, response);
                    } else {
                        accessDeniedPage(request,response);
                    }
                    break;
                case "/delete":
                    if (hasAdminRole(user)) {
                        deleteBook(request, response);
                    } else {
                        accessDeniedPage(request,response);
                    }
                    break;
                case "/edit":
                    if (hasAdminRole(user)) {
                        showEditForm(request, response);
                    } else {
                        accessDeniedPage(request,response);
                    }
                    break;
                case "/update":
                    if (hasAdminRole(user)) {
                        updateBook(request, response);
                    } else {
                        accessDeniedPage(request,response);
                    }
                    break;
                default:
                    listBook(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private boolean hasAdminRole(AppUser user) {
        if(user == null)
            return false;
        String role = String.valueOf(user.getRole());
        return "Admin".equals(role);
    }

    private void accessDeniedPage(HttpServletRequest request ,HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/AccessDenied.jsp");
    }

    private void listBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Book> listBook = bookDAO.listAllBooks();
        request.setAttribute("listBook", listBook);
        RequestDispatcher dispatcher = request.getRequestDispatcher("BookList.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("BookForm.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Book existingBook = bookDAO.getBook(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("BookForm.jsp");
        request.setAttribute("book", existingBook);
        dispatcher.forward(request, response);

    }

    private void insertBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        float price = Float.parseFloat(request.getParameter("price"));

        Book newBook = new Book(title, author, price);
        bookDAO.insertBook(newBook);
        response.sendRedirect("list");
    }

    private void updateBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        float price = Float.parseFloat(request.getParameter("price"));

        Book book = new Book(id, title, author, price);
        bookDAO.updateBook(book);
        response.sendRedirect("list");
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        Book book = new Book(id);
        bookDAO.deleteBook(book);
        response.sendRedirect("list");

    }
}