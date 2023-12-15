<%@ page import="app.entities.User" %>
<%@ page import="javax.lang.model.element.Element" %>

<%@ page import="app.servlets.DatabaseHandler" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.sun.jdi.StringReference" %>
<%@ page import="javax.xml.crypto.Data" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Other posts</title>
    <link rel="stylesheet" href="styleMain.css">
    <link rel="stylesheet" href="style.css">
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
<div id="red" class="news">
    Личные посты
    <%
        DatabaseHandler dbhandler = new DatabaseHandler();
        ResultSet data = dbhandler.PostSelect();
        //ResultSet tmp = dbhandler.getUser(new User(request.getSession().getAttribute("userName").toString(),"12345678"));
        out.println(request.getSession().getAttribute("user"));
        while(data.next()) {
            if(data.getString("UserName").equals(request.getSession().getAttribute("user"))){
                String name = data.getString("NameOfPost");
                String post = data.getString("ContentOfPost");
                String user = data.getString("UserName");
                out.println("<div id=\"redactable\"><label>" + "<h1>" + name + "</h1>" + " " + post + " by: " + user + "</label></div>");
            }
        }
//        if(tmp.next() && tmp.getString("role").equals("admin")){
//            while(data.next()) {
//                if(data.getString("UserName").equals(request.getSession().getAttribute("user"))){
//                    String name = data.getString("NameOfPost");
//                    String post = data.getString("ContentOfPost");
//                    String user = data.getString("UserName");
//                    out.println("<form method=\"post\"><div id=\"redactable\">" +
//                            "<label>" + "<h1>" + name + "</h1>" + " " + post + " by: " + user + "</label></div>" +
//                            "<label>Название поста:</label><input name=\"name\"><label>пост:</label><input name=\"post\">" +
//                            "<button name=\"redact\" value=\""+name+"\" type=\"submit\">Редактировать</button></form>");
//                }
//            }
//        }
//        else{
//
//        }
    %>
</div>
<%
    if (request.getAttribute("Error") != null) {
        out.println("<p>" + request.getAttribute("Error") + " </p>");
        request.setAttribute("Error",null);
    }
%>
</body>
</html>
