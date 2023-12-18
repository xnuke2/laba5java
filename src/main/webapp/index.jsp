<%@ page import="app.entities.User" %>
<%@ page import="javax.lang.model.element.Element" %>

<%@ page import="app.servlets.DatabaseHandler" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.sun.jdi.StringReference" %>
<%@ page import="javax.xml.crypto.Data" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Main</title>
        <link rel="stylesheet" href="styleMain.css">
        <link rel="stylesheet" href="styles.css">
    </head>
    <body>
    <!-- шапка сайта -->
    <div class="profile-head" >
        <script type="text/javascript">
            var currentDate = new Date(<%=request.getAttribute("time")%>);
            function run() {
                currentDate.setSeconds(currentDate.getSeconds()+1);
                var time = "";
                var hour = currentDate.getHours();
                var minute = currentDate.getMinutes();
                var second = currentDate.getSeconds();
                if(hour < 10){
                    time += "0" + hour;
                }else{
                    time += hour;
                }
                time += ":";
                if(minute < 10){
                    time += "0" + minute;
                }else{
                    time += minute;
                }
                time += ":";
                if(second < 10){
                    time += "0" + second;
                }else{
                    time += second;
                }
                document.getElementById("Time").innerHTML = time;
                setTimeout(run,1000);
            }
        </script>
        <div class="backColor glow-on-hover">
            <div class="in">
                <span>
                    Текущее время:
                </span>
                <span id ="Time">
                    22:05:11
                </span>
            </div>
        </div>
        <script type="text/javascript">
            run();
        </script>
        <div class="backColor glow-on-hover">
            <div class="in">
                <span>
                    Счётчик вошедших:
                </span>

                <%
                    DatabaseHandler dbhandlercount = new DatabaseHandler();
                    ResultSet datacount = dbhandlercount.NumOfPeopleSelect();
                    while (datacount.next()){
                        String num = datacount.getString("num");
                        out.println("<span>"+ num +"</span>");
                    }
                %>
            </div>
        </div>
        <button onclick="location.href='/laba5java/'" class="glow-on-hover">
            Главная
        </button>

        <button onclick="location.href='/laba5java/list'" class = "glow-on-hover">
            Поиск
        </button>

        <%
            if(request.getSession().getAttribute("userName") != null){
                out.println("<button onclick=\"location.href='/laba5java/personalAccount'\" class=\"glow-on-hover\">\n" +
                        request.getSession().getAttribute("userName") + "</button>");
//                out.println("<div class=backColor class=glow-on-hover> <div class="+"in"+
//                        "> <span>"+request.getSession().getAttribute("userName")+" </span> </div> </div>");
            }else {
                out.println("<button onclick=\"location.href='/laba5java/login'\" class=\"glow-on-hover\">\n" +
                        "Вход\n" + "</button>");
                out.println("    <button onclick=\"location.href='/laba5java/add'\" class=\"glow-on-hover\">\n" +
                        "        Регистрация\n" +
                        "    </button>");
            }
        %>
    </div>
    <div class="news" id="newss">
        <h1>
            Новостная лента
        </h1>
        <%
            if (request.getAttribute("Error") != null) {
                out.println("<p>" + request.getAttribute("Error") + " </p>");
                request.setAttribute("Error",null);
            }
        %>
        <%
            DatabaseHandler dbhandlernews = new DatabaseHandler();
            ResultSet datanews = dbhandlernews.NewsSelect();
            while (datanews.next()) {
                String author = datanews.getString("UserName").trim();
                String comment = datanews.getString("ContentOfPost");
                String name = datanews.getString("NameOfPost");
                String userName = (String)request.getSession().getAttribute("userName");
                String tmp1= "<form method=\"post\"><div class=\"post\"><label>" + "author: " + author + " " + "<h1>"
                        + name + "</h1>" + " " + comment + "</label>";
                if(request.getSession().getAttribute("userName")!=null){
                    tmp1=tmp1+"<button name=\"submit\" type=\"submit\" value=\""+name +
                            "/"+comment+"/"+author+"/"+userName+"\"> Добавить в избранное </button>";
                    DatabaseHandler dbh = new DatabaseHandler();
                    ResultSet tmp = dbh.getUser(new User(request.getSession().getAttribute("userName").toString(),"12345678"));
                    if(tmp.next() && (tmp.getString("role").equals("admin") || tmp.getString("role").equals("moderator"))){
                        tmp1=tmp1+ "<button type=\"submit\" name=\"submit\" value=\""+name+"\">Удалить</button></form>";

                        tmp1=tmp1+"        <div>\n" +
                                "            <form method=\"post\">\n" +
                                "                <p>Новое название</p><input type=\"text\" name=\"newName\" value=\""+name+"\">\n" +
                                "                <p>Новый текст поста</p><input type=\"text\" name=\"newText\" value=\""+ comment +"\">\n <br>" +
                                "                <button type=\"submit\" name=\"submit\" value=\""+name+"/"+comment+"/"+author+"/"+userName+"\">изменить</button>\n" +
                                "            </form>\n" +
                                "        </div>";
                    }else {
                        tmp1=tmp1+"</form>";
                    }
                }else {
                    tmp1=tmp1+"</form>";
                }
                tmp1=tmp1+"</div>";
                out.println(tmp1);
            }
        %>

    </div>
    <%
        if(request.getSession().getAttribute("userName") != null){
            DatabaseHandler handler = new DatabaseHandler();
            ResultSet tmp = handler.getUser(new User(request.getSession().getAttribute("userName").toString(),"12345678"));
            if(tmp.next() && (tmp.getString("role").equals("admin") || tmp.getString("role").equals("moderator"))){
                out.println("<form method=\"post\"><div class=\"ear\">" +
                        "<label>Название поста:</label><input type=\"text\" name=\"name\">" +
                        "<label>Пост:</label><input type=\"text\" id=\"qwerty\" name=\"news\">" +
                        "<button id=\"sub\" name=\"submit\" type=\"submit\" value=\"submit\">submit</button>" +
                        "</div></form>");
            }
        }
    %>
    </body>
</html>