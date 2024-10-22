import java.sql.Connection;
import java.sql.DriverManager;
// import java.sql.Statement;

public class jdbc {
    static Connection con;
    public static void connection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
             con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jobportal", "root",
                    "735403");
            System.out.println("---CONNECTED SERVER SECESSFULLY---");
        //    System.out.println(con);
        } catch (Exception e) {
            System.out.println("---UNABLE TO CONNECT SQL SERVER---");

            System.out.println(e);
            System.out.println(e.getStackTrace());
            System.exit(0);
           
        }
    }



}
