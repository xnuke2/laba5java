                                <%@ page import="app.servlets.DatabaseHandler" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.io.OutputStream" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="app.servlets.Const" %>
<%@ page import="java.sql.Blob" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
  OutputStream oImage;
  DatabaseHandler dbHandler= new DatabaseHandler();
  String img_name =(String)request.getSession().getAttribute("userName");
  ResultSet resSet = null;
  PreparedStatement pstmt = null;
  byte barray[] = null;
  try {
    String insert = "SELECT * FROM " + Const.USERS_TABLE + " WHERE "+Const.USERS_NAME + "=?";
    pstmt =dbHandler.getDbConnection().prepareStatement(insert);
    pstmt.setString(1, img_name);
    resSet = pstmt.executeQuery();
    if(resSet.next()){
      Blob blob = resSet.getBlob("avatar");
      barray = blob.getBytes(1, (int)blob.length());
      response.setContentType("image/gif");
      oImage = response.getOutputStream();
      oImage.write(barray);
      oImage.flush();
      oImage.close();
    }
  }
  catch(Exception ex){
    ex.printStackTrace();
  }
%>
</body>
</html>
