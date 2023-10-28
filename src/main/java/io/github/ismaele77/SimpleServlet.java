package io.github.ismaele77;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(description = "A simple servlet app" , urlPatterns = {"/SimpleServlet"})
public class SimpleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = "World";
		if(request.getParameter("userName") != null && !request.getParameter("userName").equals(""))
			userName = request.getParameter("userName");
		
		response.setContentType("text/html");
		response.getWriter().append("<h1>HELLO "+userName+"</h1>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = "World";
		if(request.getParameter("userName") != null && !request.getParameter("userName").equals(""))
			userName = request.getParameter("userName");
		
		response.setContentType("text/html");
		response.getWriter().append("<h1>HELLO "+userName+" From Post</h1>");
	}

}
