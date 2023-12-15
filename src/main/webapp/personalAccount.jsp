<%@ page import="app.servlets.DatabaseHandler" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.io.OutputStream" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="app.servlets.Const" %>
<%@ page import="java.sql.Blob" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Personal account</title>
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
            <%--<span id = "logined">
                    1000
                </span>--%>
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
    <button onclick="location.href='/laba5java/add'" class="glow-on-hover">
        Регистрация
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
        }
    %>
</div>
<div class="window">
    <form method="post">
        <input type="submit" name="exit" value="exit">
    </form>

    <form method="post" enctype="multipart/form-data">
        <div>
            <img src="getImage.jsp?" height="50px" width="50px"/>
            <!--<img src="personalAccountServlet?" width="50px" height="50px" alt="pfp">-->
        <label>Name:</label>
        <%
            if(request.getSession().getAttribute("userName") != null){
                out.println("<input type=\"text\" name=\"name\" size=\"50\" value=\"" +
                        request.getSession().getAttribute("userName") + "\"/>");
            }else {
                out.println("<input type=\"text\" name=\"Name\" size=\"50\" value=\"" +
                        "error" + "\"/>");
            }
        %>
        </div>
        <div>
            <label>Старый пароль:</label>
            <input type="text" name="oldPassword" size="50" />
        </div>
        <div>
            <label>Новый пароль:</label>
            <input type="text" name="newPassword" size="50" />
        </div>
        <div>
            <%
                if (request.getAttribute("Error") != null) {
                    out.println("<p>" + request.getAttribute("Error") + " </p>");
                    request.setAttribute("Error",null);
                }
                if (request.getAttribute("message") != null) {
                    out.println("<p> Данные изменены </p>");
                    request.setAttribute("Error",null);
                }
            %>
            <label>Profile Photo: </label>
            <input type="file" name="photo" size="50" />
        </div>
        <input type="submit" name="submit" value="Отправить">
    </form>
</div>
<div class="news">
    <div id="left">
    Личные посты
    <%
        DatabaseHandler dbhandler = new DatabaseHandler();
        ResultSet data = dbhandler.PostSelect();
        out.println(request.getSession().getAttribute("userName"));
        while(data.next()) {
            if(data.getString("UserName").equals(request.getSession().getAttribute("userName"))){
                String name = data.getString("NameOfPost");
                String post = data.getString("ContentOfPost");
                String user = data.getString("UserName");
                out.println("<div><label>" + "<h1>" + name + "</h1>" + " " + post + " by: " + user + "</label></div>");
            }
        }
    %>
    </div>
</div>
<form method="post">
    <div class="ear">
        <label>Название поста:</label>
        <input type="text" name="name">
        <label>Пост:</label>
        <input type="text" id="qwerty" name="posts">
    </div>
    <input type="submit" name="submit" value="submit">
</form>
</body>
</html>
