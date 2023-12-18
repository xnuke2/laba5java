package app.servlets;

import app.entities.User;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;

public class DatabaseHandler extends config {
    Connection dbConnection;
    public Connection getDbConnection() throws ClassNotFoundException, SQLException{
        String connectionString = "jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbName;
        Class.forName("com.mysql.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString,dbUser,dbPass);
        return dbConnection;
    }

    public void singUpUser(User user){
        try {
            String insert = "INSERT INTO "+Const.USERS_TABLE +"("+Const.USERS_NAME+","+Const.USERS_PASSWORD+","
                    +Const.USERS_ROLE+")"+"VALUES(?,?,?)";
            PreparedStatement prSt =getDbConnection().prepareStatement(insert);
            prSt.setString(1, user.getName());
            prSt.setString(2, user.getPassword());
            prSt.setString(3, "basic");
            prSt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public void singUpUserPhoto(String name,String newName,String password,InputStream file) {
        try {
            PreparedStatement ps=null;
            if (file.equals(null)){
                String sql = "UPDATE "+Const.USERS_TABLE +" SET "+ Const.USERS_NAME+"= ? , "
                        + Const.USERS_PASSWORD+ "= ? WHERE "+Const.USERS_NAME +" = ?";
                ps = getDbConnection().prepareStatement(sql);
                ps.setObject(1, newName);
                ps.setObject(2, password);
                ps.setString(3, name);
            }else {
                String sql = "UPDATE " + Const.USERS_TABLE + " SET " + Const.USERS_USERPHOTO + "= ? , "
                        + Const.USERS_NAME + "= ? , " + Const.USERS_PASSWORD + "= ? WHERE " + Const.USERS_NAME + " = ?";
                ps = getDbConnection().prepareStatement(sql);
                ps.setBlob(1, file);
                ps.setObject(2, newName);
                ps.setObject(3, password);
                ps.setString(4, name);
            }
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

    }
    public void UpdateUser(String name, String newname, String newpassword, String newrole, InputStream file){
        try {
            PreparedStatement prSt = null;
            String sql = "UPDATE " + Const.USERS_TABLE + " SET " + Const.USERS_NAME + " = ? , " + Const.USERS_PASSWORD + " = ? , " + Const.USERS_ROLE + " = ? , " + Const.USERS_USERPHOTO + " = ?  WHERE " + Const.USERS_NAME + " = ?";
            prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1, newname);
            prSt.setString(2, newpassword);
            prSt.setString(3, newrole);
            prSt.setBlob(4, file);
            prSt.setString(5, name);
            prSt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public void SignUp(String name, String password, String role, InputStream file){
        try {
            String insert = "INSERT INTO "+Const.USERS_TABLE +"("+Const.USERS_NAME+","+Const.USERS_PASSWORD+","
                    +Const.USERS_ROLE+","+Const.USERS_USERPHOTO+")"+"VALUES(?,?,?,?)";
            PreparedStatement prSt =getDbConnection().prepareStatement(insert);
            prSt.setString(1, name);
            prSt.setString(2, password);
            prSt.setString(3, role);
            prSt.setBlob(4,file);
            prSt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public void deleteUser(String user){
        try {
            String delete = "DELETE FROM "+ Const.USERS_TABLE + " WHERE " + Const.USERS_NAME + "=?";
            PreparedStatement prSt =getDbConnection().prepareStatement(delete);
            prSt.setString(1, user);
            prSt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public ResultSet getUser(User user){
        ResultSet resSet = null;
        String select = "SELECT * FROM "+ Const.USERS_TABLE + " WHERE "+Const.USERS_NAME + "=?";
        try {
            PreparedStatement prSt =getDbConnection().prepareStatement(select);
            prSt.setString(1, user.getName());

            resSet = prSt.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return resSet;
    }
    public void a(String name){
        ResultSet resSet = null;
        PreparedStatement pstmt = null;

        try {
            String insert = "SELECT * FROM " + Const.USERS_TABLE + " WHERE "+Const.USERS_NAME + "=? AND "
                    +Const.USERS_PASSWORD+"=?";
            PreparedStatement prSt =getDbConnection().prepareStatement(insert);
//            prSt.setString(1, user.getName());
//            prSt.setString(2, user.getPassword());
            resSet = prSt.executeQuery();
            //return resSet.getBytes("avatar");

        }
        catch(Exception ex){
            //ex.printStackTrace();
        }
    }
    public boolean CheckUserIndb(String name){
        ResultSet resSet = null;
        String select = "SELECT * FROM "+ Const.USERS_TABLE + " WHERE "+Const.USERS_NAME + "=?";
        try {
            PreparedStatement prSt =getDbConnection().prepareStatement(select);
            prSt.setString(1, name);
            resSet = prSt.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        int counter =0;
        try {
            while (resSet.next()){
                counter++;
                break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(counter>=1){
            return true;
        }
        return false;
    }
    public boolean CheckNewsIndb(String name){
        ResultSet resSet = null;
        String select = "SELECT * FROM "+ Const.NEWS_TABLE + " WHERE "+Const.NEWS_NAMEOFPOST + "=?";
        try {
            PreparedStatement prSt =getDbConnection().prepareStatement(select);
            prSt.setString(1, name);
            resSet = prSt.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        try {
            while (resSet.next()){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public void NewsUpload(String user, String name, String comment){
        try {
            String insert = "INSERT INTO "+Const.NEWS_TABLE +"("+Const.NEWS_USERNAME+","+Const.NEWS_CONTENTOFPOST+","
                    +Const.NEWS_NAMEOFPOST+","+Const.NEWS_PIC+")"+"VALUES(?,?,?,?)";
            PreparedStatement prSt =getDbConnection().prepareStatement(insert);
            prSt.setString(1, user);
            prSt.setString(2, comment);
            prSt.setString(3, name);
            prSt.setObject(4,null);
            prSt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public ResultSet NewsSelect(){
        ResultSet result = null;
        String select = "SELECT * FROM "+ Const.NEWS_TABLE;
        try {
            PreparedStatement prSt =getDbConnection().prepareStatement(select);
            result = prSt.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return result;
    }
    public void deleteNews(String name){
        try {
            String delete = "DELETE FROM "+ Const.NEWS_TABLE + " WHERE " + Const.NEWS_NAMEOFPOST + "=?";
            PreparedStatement prSt =getDbConnection().prepareStatement(delete);
            prSt.setString(1, name);
            prSt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public void PostUpload(String user, String name, String comment){
        try {
            String insert = "INSERT INTO "+Const.POSTS_TABLE +"("+Const.POSTS_NAMEOFPOST+","+Const.POSTS_CONTENTOFPOST+"," + Const.POSTS_USERNAME+")"+"VALUES(?,?,?)";
            PreparedStatement prSt =getDbConnection().prepareStatement(insert);
            prSt.setString(1, name);
            prSt.setString(2, comment);
            prSt.setString(3, user);
            prSt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public ResultSet PostSelect(){
        ResultSet result = null;
        String select = "SELECT * FROM "+ Const.POSTS_TABLE;
        try {
            PreparedStatement prSt =getDbConnection().prepareStatement(select);
            result = prSt.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return result;
    }
    public void PostUpdate(String old_name, String new_name, String post){
        try {
            PreparedStatement prSt = null;
            String sql = "UPDATE " + Const.POSTS_TABLE + " SET " + Const.POSTS_NAMEOFPOST + " = ? , " + Const.POSTS_CONTENTOFPOST + " = ?  WHERE " + Const.POSTS_NAMEOFPOST + " = ?";
            prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1, new_name);
            prSt.setString(2, post);
            prSt.setString(3, old_name);
            prSt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public ResultSet SearchList(){
        ResultSet result = null;
        String select = "SELECT * FROM "+ Const.USERS_TABLE;
        try {
            PreparedStatement prSt =getDbConnection().prepareStatement(select);
            result = prSt.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return result;
    }
    public static String quote(String s) {
        return new StringBuilder()
                .append('\'')
                .append(s)
                .append('\'')
                .toString();
    }
    public ResultSet SearchList(String name){
        ResultSet result = null;
        String select = "SELECT * FROM "+ Const.USERS_TABLE+ " WHERE " + Const.USERS_NAME +" LIKE "+quote(name+"%");
        try {
            PreparedStatement prSt =getDbConnection().prepareStatement(select);
            result = prSt.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return result;
    }
    public void NumOfPeopleUpdate(){
        try {
            DatabaseHandler dbhandler = new DatabaseHandler();
            ResultSet data = dbhandler.NumOfPeopleSelect();
            data.next();
            int num = data.getInt("num");
            PreparedStatement prSt = null;
            String sql = "UPDATE " + Const.NUM_OF_PEOPLE_TABLE + " SET " + Const.NUM_OF_PEOPLE_NUM + " = ?";
            prSt = getDbConnection().prepareStatement(sql);
            prSt.setInt(1, num + 1);
            prSt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public ResultSet NumOfPeopleSelect(){
        ResultSet result = null;
        String select = "SELECT * FROM "+ Const.NUM_OF_PEOPLE_TABLE;
        try {
            PreparedStatement prSt =getDbConnection().prepareStatement(select);
            result = prSt.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return result;
    }
    public void FavPostUpload(String name, String comment, String author, String userName){
        try {
            String insert = "INSERT INTO "+Const.FAVOURITE_TABLE +"("+Const.FAVOURITE_NAME+","+Const.FAVOURITE_CONTENT+"," + Const.FAVOURITE_USERNAME+"," + Const.FAVOURITE_AUTHOR+")"+"VALUES(?,?,?,?)";
            PreparedStatement prSt =getDbConnection().prepareStatement(insert);
            prSt.setString(1, name);
            prSt.setString(2,comment);
            prSt.setString(3,userName);
            prSt.setString(4,author);
            prSt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public ResultSet FavPostSelect(){
        ResultSet result = null;
        String select = "SELECT * FROM "+ Const.FAVOURITE_TABLE;
        try {
            PreparedStatement prSt =getDbConnection().prepareStatement(select);
            result = prSt.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return result;
    }
}
