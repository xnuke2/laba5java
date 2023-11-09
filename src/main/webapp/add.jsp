<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Add new user</title>
        <link rel="stylesheet" href="styleMain.css">
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
                    <span id = "logined">
                        1000
                    </span>
                </div>
            </div>
            <button href="" class="glow-on-hover">
                Личный кабинет
            </button>
            <button onclick="location.href='/laba5java/'" class="glow-on-hover">
                Главная
            </button>
            <button onclick="location.href='/laba5java/add'" class="glow-on-hover">
                Регистрация(войти)
            </button>

        </div>


        <div>
            <%
                if (request.getAttribute("Error") != null) {
                    out.println("<p>" + request.getAttribute("Error") + " </p>");
                    request.setAttribute("Error",null);
                }
                if (request.getAttribute("userName") != null) {
                    out.println("<p> Пользователь с именем '" + request.getAttribute("userName") + "' успешно создан! </p>");
                    request.setAttribute("Error",null);
                }
            %>
            <div class="window">
                <div>
                    <h2>Add user</h2>
                </div>

                <form method="post">
                    <label>Name:
                        <input type="text" name="name"><br />
                    </label>
                    <label>Password:
                        <input type="password" name="pass"><br />
                    </label>
                    <button type="submit">Submit</button>
                </form>
            </div>
        </div>
    </body>
</html>