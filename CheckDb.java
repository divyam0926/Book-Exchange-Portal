import java.sql.*;
public class CheckDb {
  public static void main(String[] args) {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/book?useSSL=false&serverTimezone=UTC", "root", "root");
      System.out.println("DB_OK");
      c.close();
    } catch (Exception e) {
      e.printStackTrace(System.out);
      System.exit(1);
    }
  }
}
