import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// import com.mysql.cj.protocol.Resultset;
// import com.mysql.*;
// import com.mysql.cj.protocol.Resultset;

public class shortcut {

    
    static Scanner scanner = job_seaker.sc;

    public static int changeformat(String st){
        if (st.matches("0")) {
            System.out.println("Exiting...");
            job_seaker.acc();

        }
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
        if (phone_no_string.matches("0")) {
            System.out.println("Exiting....");
job_seaker.acc();
        }
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
            }
            if(n1.matches("0")){
                System.out.println("Exiting");
               job_seaker. acc();
            
            }
            else {
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

    
    public static void updating(String columnName, String newValue, int seekerId , String taleName) {
        // Validate column name to prevent SQL injection
        // if (!isValidColumnName(columnName)) {
        //     throw new IllegalArgumentException("Invalid column name: " + columnName);
        // }

        String sql = "UPDATE "+taleName+" SET " + columnName + " = ? WHERE id = ?";
        
        try (
             PreparedStatement pstmt = jdbc.con.prepareStatement(sql)) {
             
            pstmt.setString(1, newValue);
            pstmt.setInt(2, seekerId);
            pstmt.executeUpdate();
            // System.out.println("Updated " + affectedRows + " record(s).");
                System.out.println("Updated successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static int ageCheck() {
        int age = 0;
        boolean valid = false;
    
        while (!valid) {
            System.out.println("Please enter your age (18-60): ");
            String input = scanner.nextLine();
    
            try {
                age = Integer.parseInt(input);
    
                // Check if age is within the valid range
                if (age >= 18 && age <= 60) {
                    valid = true; // Exit the loop if the age is valid
                } else {
                    System.out.println("Invalid age. Age must be between 18 and 60.");
                    System.out.println("1 -> Try again");
                    System.out.println("2 -> Back to main menu");
                    System.out.print("Choose an option: ");
                    
                    String option = scanner.nextLine();
                    
                    if (option.equals("2")) {
                        System.out.println("Returning to main menu...");
                        return -1; // Use -1 to indicate going back to main menu
                    }
                }
    
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                System.out.println("1 -> Try again");
                System.out.println("2 -> Back to main menu");
                System.out.print("Choose an option: ");
                
                String option = scanner.nextLine();
                
                if (option.equals("2")) {
                    System.out.println("Returning to main menu...");
                    return -1; // Use -1 to indicate going back to main menu
                }
            }
        }
    
        return age; // Return the valid age
    }
    public static String passwordCheck() {
        boolean valid = false;
        String password = "";
    
        while (!valid) {
            System.out.println("Enter password (must contain alphabet, number, special character, and be 6-12 characters long): ");
            password = scanner.nextLine();
    
            // Check length
            if (password.length() < 6 || password.length() > 12) {
                System.out.println("Password length must be between 6 and 12 characters.");
                continue;
            }
    
            // Check for required character types
            boolean hasLetter = false;
            boolean hasDigit = false;
            boolean hasSpecialChar = false;
    
            for (char ch : password.toCharArray()) {
                if (Character.isLetter(ch)) {
                    hasLetter = true;
                } else if (Character.isDigit(ch)) {
                    hasDigit = true;
                } else if (!Character.isLetterOrDigit(ch)) {
                    hasSpecialChar = true;
                }
            }
    
            // Validate all conditions
            if (hasLetter && hasDigit && hasSpecialChar) {
                valid = true; // Password is valid
            } else {
                System.out.println("Password must contain at least one letter, one number, and one special character.");
            }
        }
    
        return password; // Return the valid password
    }
    
    public static String validMailTaking(){
        String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
        String condition1=".org";
        String condition2=".com";

       while (true) {
        System.out.println("Enter Valid Mail Id :- ");
        String email = scanner.nextLine();
        if (email.matches("0")) {
            System.out.println("Exiting...");
            job_seaker.acc();
        }
        boolean isCorrect = EMAIL_PATTERN.matcher(email).matches();

        if((isCorrect==true) && (email.endsWith(condition2) || email.endsWith(condition1)) ){
                return email;
        }else{
            System.out.println("You entered email is invalid");
            System.out.println("1. Re - try");
            System.out.println("2. Cancel form");
            int tempselection=shortcut.changeformat(scanner.nextLine());

            if(tempselection==1){

            }else{
                return "Cancel form";
            }
        }
       }

    }
  
  
}
