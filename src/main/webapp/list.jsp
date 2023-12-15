<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="app.servlets.DatabaseHandler" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="app.entities.User" %>

<html>
    <head>
        <title>Users</title>
        <link rel="stylesheet" href="styleMain.css">
        <link rel="stylesheet" href="style.css">
    </head>
    <body>
    <!--Шапка сайта-->
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
    <form method="post">
        <input type="text" name="findBy">
        <input type="submit" name="find" value="find">
    </form>
    <%
        if (request.getAttribute("Error") != null) {
            out.println("<p>" + request.getAttribute("Error") + " </p>");
            request.setAttribute("Error",null);
        }

            DatabaseHandler dbhandler = new DatabaseHandler();
            ResultSet data;
            //ResultSet tmp = dbhandler.getUser(new User(request.getSession().getAttribute("userName").toString(),"12345678"));

            if(request.getSession().getAttribute("findBy")==null){
                data = dbhandler.SearchList();
            }else {
                data = dbhandler.SearchList((String)request.getSession().getAttribute("findBy"));
                if(request.getSession().getAttribute("findBy")!="")
                    out.println("<h3>результаты поиска по "+(String)request.getSession().getAttribute("findBy") +" </h3>");
            }
            while(data.next()){
                String user = data.getString("Name");
                out.println("<form method=\"post\">" + "<div class=\"ear\">" +
                        "<button name=\"submit\" value=\"submit\" type=\"submit\">" +
                        "<input name=\"user\" value=\""+user+"\" onkeyup=\"this.value = this.value.replace(/[^\\]/g,'');\">" +
                        "</button>" +
                        "</div>" + "</form>");
            }
        request.getSession().setAttribute("findBy",null);
            //if(tmp.next() && (tmp.getString("role").equals("admin"))){

//                while(data.next()){
//                    String user = data.getString("Name");
//                    out.println("<form method=\"post\">" +
//                            "<div class=\"ear\">" +
//                            "<button name=\"submit\" type=\"submit\" value=\"submit\" onclick=\"location.href='/laba5java/otherAccount'\">" +
//                            "<input name=\"user\" value=\""+user+"\" onkeyup=\"this.value = this.value.replace(/[^\\d]/g,'');\">" +
//                            "</button> " +
//                            "<input list=\"listing\" type=\"text\" id=\"qwerty\" name=\"role\">" +
//                            "<datalist id=\"listing\">\n" + "<option value=\"moderator\"></option>\n" + "<option value=\"basic\"></option>" +
//                            "</datalist>" +
//                            "<button name=\"submit\" value=\"Change\" type=\"submit\" onclick=\"request.getSession().setAttribute(\"name\","+ user +")\">Изменить роль</button>" +
//                            "</div>" +
//                            "</form>");
//                }



    %>
    <!--<input name="user" value="awdawd" onkeyup="this.value = this.value.replace(/[^\\]/g,'');">-->
    <%--<div class="ear">
    <input list="listing" type="text" id="qwerty">
        <datalist id="listing">
            <option value="tre"></option>
            <option value="кек"></option>
            <option value="["></option>
        </datalist>
    </div>--%>
    <%--<div id="testir">
        <div class="news">dfghjk</div>
    </div>--%>

    <%--<label for="ice-cream-choice">Choose a flavor:</label>
    <input list="ice-cream-flavors" id="ice-cream-choice" name="ice-cream-choice" />

    <datalist id="ice-cream-flavors">
        <option value="Chocolate"></option>
        <option value="Coconut"></option>
        <option value="Mint"></option>
        <option value="Strawberry"></option>
        <option value="Vanilla"></option>
    </datalist>--%>
    <!--<script type="text/javascript">
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
    	document.getElementById("dt").innerHTML = time;
    	setTimeout(run,1000);
    }
    </script>
        <div id = "dt">Время отображается автоматически.
            <script type="text/javascript">
              run();
            </script>
        </div>

        <div>
            <div>
                <div>
                    <h2>Users</h2>
                </div>
                <%
                    List<String> names = (List<String>) request.getAttribute("userNames");

                    if (names != null && !names.isEmpty()) {
                        out.println("<ui>");
                        for (String s : names) {
                            out.println("<li>" + s + "</li>");
                        }
                        out.println("</ui>");
                    } else out.println("<p>There are no users yet!</p>");
                %>
            </div>
        </div>

        <div>
            <button onclick="location.href='/laba5java'">Back to main</button>
        </div>-->
    </body>
</html>
