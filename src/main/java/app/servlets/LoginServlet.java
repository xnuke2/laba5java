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

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setMaxInactiveInterval(1800);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("login.jsp");
        req.setAttribute("time", new java.util.Date().getTime());
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("userName", null);
        req.setAttribute("Error", null);
        String name = req.getParameter("name").trim();
        String password = req.getParameter("pass").trim();
        if(name.equals("")||password.equals("")){
            req.setAttribute("Error", "Одно из полей пусто");
            doGet(req, resp);
            return;
        }
        DatabaseHandler dbHandler= new DatabaseHandler();
        User user =new User(name,password);
        ResultSet result = dbHandler.getUser(user);
        dbHandler.NumOfPeopleUpdate();
        int counter = 0;
        try {
            if (result.next()){
                if(result.getString(Const.USERS_PASSWORD).equals(password)){
                    req.getSession().setAttribute("userName", name);
                    resp.sendRedirect(req.getContextPath() + "/personalAccount");
                    return;
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        req.setAttribute("Error", "Неккоректный логин или пароль");
        doGet(req, resp);
    }
}
