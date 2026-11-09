package dao;
import java.sql.*;
public class DAO { protected static Connection con;
  public static String URL="jdbc:mysql://localhost:3306/supermarket?useUnicode=true&characterEncoding=UTF-8", USER="root", PASS="123456";
  public DAO(){ if(con==null){ try{ con=DriverManager.getConnection(URL,USER,PASS);}catch(SQLException e){ throw new RuntimeException("Không kết nối được CSDL: "+e.getMessage(),e);} } } }
