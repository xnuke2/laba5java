package app.servlets;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
        req.getSession().setMaxInactiveInterval(1800);
        req.setAttribute("time", new java.util.Date().getTime());
        requestDispatcher.forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.setCharacterEncoding("UTF-8");
        if(req.getParameter("submit").equals("submit")){
            req.setAttribute("Error", null);
            String comment = req.getParameter("news").trim();
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
            if(dbhandler.CheckNewsIndb(name)){
                req.setAttribute("Error", "Новость с таким названием уже есть");
                doGet(req, resp);
                return;
            }
            dbhandler.NewsUpload(user, name, comment);
            //req.getSession().setAttribute("recordInsertedSuccessfully","true");
            doGet(req, resp);
            return;
        }
        else{
            DatabaseHandler dbhandlernews = new DatabaseHandler();
            ResultSet datanews = dbhandlernews.NewsSelect();
            ResultSet datafav = dbhandlernews.FavPostSelect();
            String userName = (String)req.getSession().getAttribute("userName");

            try {
                if(userName == null){
                    req.setAttribute("Error", "Войдите в аккаунт");
                    doGet(req, resp);
                    return;
                }
                while(datanews.next()){
                    String name = req.getParameter("submit");
                    String nameofpost = datanews.getString("NameOfPost");
                    if(req.getParameter("submit").equals(nameofpost)){
                        dbhandlernews.deleteNews(nameofpost);
                        doGet(req,resp);
                        return;
                    }
                    else if(req.getParameter("submit").equals(name)){
                        String parts[] = name.split("/");
                        while(datafav.next()){
                            if(datafav.getString("NameOfFavPost").equals(parts[0]) && datafav.getString("FavUserName").equals(parts[3])){
                                req.setAttribute("Error", "Этот пост уже есть");
                                doGet(req, resp);
                                return;
                            }
                        }
                        dbhandlernews.FavPostUpload(parts[0],parts[1],parts[2],parts[3]);
                        doGet(req, resp);
                        return;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
