package app.servlets;

import app.entities.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Console;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setMaxInactiveInterval(1800);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("add.jsp");
        req.setAttribute("time", new java.util.Date().getTime());
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("Error", null);
        String name = req.getParameter("name").trim();
        String password = req.getParameter("pass").trim();
        if(name.equals("")||password.equals("")){
            req.setAttribute("Error", "Одно из полей пусто");
            doGet(req, resp);
            return;
        }
        if(password.length()<8){
            req.setAttribute("Error", "Пароль слишком короткий");
            doGet(req, resp);
            return;
        }
        DatabaseHandler dbhandler = new DatabaseHandler();
        if(dbhandler.CheckUserIndb(name)){
            req.setAttribute("Error", "Пользователь с таким именем уже есть");
            doGet(req, resp);
            return;
        }
        User user = new User(name,password);
        user.setRole("basic");
        dbhandler.singUpUser(user);
        dbhandler.NumOfPeopleUpdate();
        req.getSession().setAttribute("userName", name);
        resp.sendRedirect(req.getContextPath() + "/personalAccount");
    }
}