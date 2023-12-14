package app.servlets;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        if (req.getSession().getAttribute("recordInsertedSuccessfully") == null )
        {
            dbhandler.NewsUpload(user, name, comment);
            req.getSession().setAttribute("recordInsertedSuccessfully","true");
        } else {
            //case of form re-submission
        }
        doGet(req, resp);
    }
}
