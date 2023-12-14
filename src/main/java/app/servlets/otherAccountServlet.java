package app.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;

public class otherAccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.getSession().setMaxInactiveInterval(1800);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("otherAccount.jsp");
        req.setAttribute("time", new java.util.Date().getTime());
        requestDispatcher.forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String old_name = req.getParameter("redact");
        String new_name = req.getParameter("name");
        String post = req.getParameter("post");
        if(new_name == "" || post == ""){
            req.setAttribute("Error", "Название или содержание поста пусто");
            doGet(req, resp);
            return;
        }
        DatabaseHandler dbhandler = new DatabaseHandler();
        dbhandler.PostUpdate(old_name, new_name, post);
        doGet(req, resp);
    }
}
