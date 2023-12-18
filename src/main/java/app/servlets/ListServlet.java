package app.servlets;

import app.entities.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("time", new java.util.Date().getTime());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("list.jsp");
        requestDispatcher.forward(req, resp);

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        if (req.getParameter("submit").equals("find")){
            req.getSession().setAttribute("findBy",req.getParameter("findBy"));
            doGet(req,resp);
            return;
        }
        if (req.getParameter("submit").equals("submit")) {
            String user = req.getParameter("user");
            req.getSession().setAttribute("user", user);
            resp.sendRedirect("http://localhost:8080/laba5java/otherAccount");
            return;
        }
    }
}