package app.servlets;

import app.entities.User;
import app.model.Model;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("add.jsp");
        req.setAttribute("time", new java.util.Date().getTime());
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("userName", null);
        req.setAttribute("Error", null);
        String name = req.getParameter("name");
        String password = req.getParameter("pass");
        Model model = Model.getInstance();
        if(name.equals("")||password.equals("")){
            req.setAttribute("Error", "Одно из полей или оба пусты");
            doGet(req, resp);
            return;
        }
        if(password.length()<8){
            req.setAttribute("Error", "Пароль дожен состоят не мение чем из 8 символов");
            doGet(req, resp);
            return;
        }
        for(int i = 0;i<model.GetArrayOfNames().size();i++){
            if(name.equals(model.GetArrayOfNames().toArray()[i])){
                req.setAttribute("Error", "Имя уже занято");
                doGet(req, resp);
                return;
            }
        }
        User user = new User(name, password);

        model.add(user);
        req.setAttribute("userName", name);
        doGet(req, resp);
    }
}