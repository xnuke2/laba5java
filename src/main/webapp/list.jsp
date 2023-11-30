<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>

<html>
    <head>
        <title>Users</title>
        <link rel="stylesheet" href="styleMain.css">
    </head>
    <body>
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
                <span id = "logined">
                    1000
                </span>
            </div>
        </div>
        <button onclick="location.href='/laba5java/'" class="glow-on-hover">
            Главная
        </button>
        <button onclick="location.href='/laba5java/add'" class="glow-on-hover">
            Регистрация
        </button>
        <button onclick="location.href='/laba5java/list'" class = "glow-on-hover">Поиск</button>

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
    <input label="awd" type="text" class="ear" id="qwerty">
    <div class="window"> dsfghjkmnbvcdrtyuikmnhgf</div>
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
