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
        if(req.getParameter("submit").equals("Change")){
            req.setAttribute("Error", null);
            String role = req.getParameter("role");
            String name = req.getParameter("user");
            if(!(role.equals("moderator") || role.equals("basic"))){
                req.setAttribute("Error", "Роль должна быть moderator или basic");
                doGet(req, resp);
                return;
            }
            DatabaseHandler dbhandler = new DatabaseHandler();
            ResultSet tmp = dbhandler.getUser(new User(name, "12345678"));
            try {
                if(tmp.next()){
                    String currole = tmp.getString("role");
                    if(currole.equals("admin")){
                        req.setAttribute("Error", "У админа нельзя изменить роль");
                        doGet(req, resp);
                        return;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            dbhandler.RoleUpdate(role, name);
            doGet(req, resp);
        } else if (req.getParameter("submit").equals("submit")) {
            String user = req.getParameter("user");
            req.getSession().setAttribute("user", user);
            resp.sendRedirect("http://localhost:8080/laba5java/otherAccount");
            return;
        }
    }
}