<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>

<html>
    <head>
        <title>Users</title>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    </head>
    <body>
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
        </div>
    </body>
</html>
