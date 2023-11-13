package app.servlets;

import app.entities.User;

import javax.lang.model.element.Name;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

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
            String insert = "INSERT INTO "+Const.USER_TABLE+"("+Const.USERS_NAME+","+Const.USERS_PASSWORD+","
                    +Const.USERS_ROLE+")"+"VALUES(?,?,?)";
            PreparedStatement prSt =getDbConnection().prepareStatement(insert);
            prSt.setString(1, user.getName());
            prSt.setString(2, user.getPassword());
            prSt.setString(3, user.getRole());
            prSt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public ResultSet getUser(User user){
        ResultSet resSet = null;
        String select = "SELECT * FROM "+ Const.USER_TABLE + " WHERE "+Const.USERS_NAME + "=? AND "
                +Const.USERS_PASSWORD+"=?";
        try {
            PreparedStatement prSt =getDbConnection().prepareStatement(select);
            prSt.setString(1, user.getName());
            prSt.setString(2, user.getPassword());
            resSet = prSt.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return resSet;
    }
    public boolean CheckUserIndb(String name){
        ResultSet resSet = null;
        String select = "SELECT * FROM "+ Const.USER_TABLE + " WHERE "+Const.USERS_NAME + "=?";
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
}
