import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.Statement;

// import com.mysql.cj.protocol.Resultset;
// import com.mysql.*;
// import com.mysql.cj.protocol.Resultset;

public class shortcut {

    
    static Scanner scanner = job_seaker.sc;

    public static int changeformat(String st){
        try{return Integer.parseInt(st);}
        catch(Exception e){
            System.out.print("Do not enter invalid");
            System.out.print("Re-enter -> "); 
            changeformat(scanner.nextLine());
            return 0;
        }
    }


    static long phonenoo=0;
    public static long phonenumbertaking() {
        // sc.next();
        String phone_no_string = scanner.nextLine().toLowerCase();
        phone_no_string = phone_no_string.replace(" ", "");
        
        for (int i = 0; i < phone_no_string.length(); i++) {
            if (phone_no_string.charAt(i) >= 'a' && phone_no_string.charAt(i) <= 'z') {
                System.out.println("--- INVLID FORMAT ---");
                System.out.print("PLEASE ENTER CORRECT MOBILE NUMBER -> ");
                phonenumbertaking();
                break;
            } else if (phone_no_string.length() != 10) {
                System.out.println("---YOU ENETERED INNCORRECT NUMBER---");
                System.out.print("PLEASE ENTER CORRECT 10 DIGIT MOBILE NUMBER -> ");
                phonenumbertaking();
                break;
            } else if (phone_no_string.length() == 10) {
                if ((phone_no_string.charAt(0) >= '0' && phone_no_string.charAt(0) <= '5')) {
                    System.out.print("PLEASE ENTER A VALID INDIAN NUMBER -> ");
                    phonenumbertaking();
                    break;
                }
                // long realphonenumber = Long.parseLong(phone_no_string);
                phonenoo = Long.parseLong(phone_no_string);
                return phonenoo;
            }
        }
        return phonenoo;

    }

    public static int generateradomnumber(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
            return Integer.parseInt(new String(digits));
        }

    public static YearMonth selective_date(){

        String input;

        System.out.print("Enter the date in 'MM-yyyy' format: ");
        input = scanner.nextLine();

         try {
            
            // Define the expected format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");

            // Parse the input
            YearMonth yearMonth = YearMonth.parse(input, formatter);

            // Output the result
            System.out.println("You entered: " + yearMonth);

            if (yearMonth.isAfter(YearMonth.from(LocalDate.now()))) {
                System.out.println("The date is in the future.");
                System.out.println("You can not enter upcoming dates");
               
                return null;
                // System.out.println();
            }
            return yearMonth;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter in 'MM-yyyy' format.");
            System.out.println("PLEASE RE - ENTER ");
            
            return null;
        }
    }
 
    public static String nameFormating(){
      
//    Scanner scanner = new Scanner(System.in);
        String n1;

        while (true) {
            // System.out.print("Enter your name: ");
            n1 = scanner.nextLine();

            // Check if the input is valid (contains only letters and spaces)
            if (n1.matches("[a-z  A-Z\\s]+")) {
                // System.out.println("Name entered successfully: " );
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid name (letters and spaces only).");
            }

        }
       
        return n1;

    }


    // public static void sqlquery(String column , String table , String [] parameters ){
    //     try{

    //     }
    // }


    // public static boolean checkdataexist(int num, String datacolumnname, String tablename) {
         
    //     try {
    //         rs=query.selectQuery(datacolumnname, tablename);
    //         while (rs.next()) {
    //             int tempnum = rs.getInt(datacolumnname);
    //             if (num == tempnum) {
    //                 return true;
    //             }
    //         }
    //         return false;
    //     } catch (Exception exception) {
    //         System.out.println("PROBLEM IN CHECKING DETAILS");
    //         return false;
    //     }
    // }

    public static boolean checkdataexist(int num, String datacolumnname, String tablename) {
         
        try {
            // ResultSet rs=query.selectQuery(datacolumnname, tablename);
            Statement statement=jdbc.con.createStatement();
          String full_query="select "+datacolumnname+" from "+tablename;
           ResultSet rs=statement.executeQuery(full_query);
            while (rs.next()) {
                int tempnum = rs.getInt(datacolumnname);
                if (num == tempnum) {
                    return true;
                }
            }
            return false;
        } catch (Exception exception) {
            System.out.println("PROBLEM IN CHECKING DETAILS");
            return false;
        }
    }
  
}
