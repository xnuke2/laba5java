package app.servlets;

import app.entities.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.transform.Result;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class personalAccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("personalAccount.jsp");
        req.getSession().setMaxInactiveInterval(1800);
        req.setAttribute("time", new java.util.Date().getTime());
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("submit").equals("exit")){
            req.getSession().setAttribute("userName", null);
            resp.sendRedirect(req.getContextPath());
            return;
        }
        if(req.getParameter("submit").equals("submit")){
            req.setAttribute("Error", null);
            String comment = req.getParameter("posts").trim();
            String name = req.getParameter("name").trim();
            String user = (String) req.getSession().getAttribute("userName");
            if(comment == ""){
                req.setAttribute("Error", "Новость пуста");
                doGet(req, resp);
                return;
            }
            if(name == ""){
                req.setAttribute("Error","Название пусто");
                doGet(req, resp);
                return;
            }
            DatabaseHandler dbhandler = new DatabaseHandler();
            //ResultSet data = dbhandler.PostSelect();
            //String s = data.getString("NameOfPost");
            dbhandler.PostUpload(user, name, comment);
            doGet(req, resp);
        }
        else {
            if (req.getSession().getAttribute("userName")==null){
                req.setAttribute("Error","Вы не вошли в аккаунт");
                return;
            }
            InputStream inputStream = null;
            String message = null;
            String name = req.getParameter("name").trim();
            String oldPassword = req.getParameter("oldPassword").trim();
            String newPassword = req.getParameter("newPassword").trim();
            if(name.equals("")||oldPassword.equals("")){
                req.setAttribute("Error", "Одно из полей пусто");
                doGet(req, resp);
                return;
            }
            if(newPassword.length()<8){
                req.setAttribute("Error", "Пароль слишком короткий");
                doGet(req, resp);
                return;
            }
            DatabaseHandler dbHandler= new DatabaseHandler();
            User user =new User(name,oldPassword);
            ResultSet result = dbHandler.getUser(user);
            int counter =0;
            try {
                while (result.next()){
                    counter++;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if(counter<1){
                req.getSession().setAttribute("Error", "Данные некорректны");
                doGet(req, resp);
                return;
            }
            Part filePart
                    = req.getPart("photo");

            if (filePart != null) {
                inputStream = filePart.getInputStream();
            }

            dbHandler.singUpUserPhoto((String) req.getSession().getAttribute("userName"),name,newPassword, inputStream);
            req.setAttribute("message","Фото успешно установлено");
            doGet(req, resp);
        }
    }
}
