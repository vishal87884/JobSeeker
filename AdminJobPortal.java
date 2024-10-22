import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

 

public class AdminJobPortal {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        boolean running = true;

        while (running) {
            System.out.println("Admin Job Portal - Choose an Option:");
            System.out.println("1. Monitor the Platform");
            System.out.println("2. Manage User Accounts");
            System.out.println("3. Generate Analytics Report");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    monitorPlatform();
                    break;
                case 2:
                    manageUserAccounts(sc);
                    break;
                case 3:
                    generateAnalyticsReport();
                    break;
                case 4:
                    running = false;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
        sc.close();
    }


    // Option 1: Monitor the Platform
    private static void monitorPlatform() {
        System.out.println("Monitoring the platform for activity...");
        // Add platform monitoring logic here
    }

    // Option 2: Manage User Accounts
    private static void manageUserAccounts(Scanner sc) {
        boolean managing = true;

        while (managing) {
            System.out.println("\nManage User Accounts:");
            System.out.println("1. Add User");
            System.out.println("2. Delete User");
            System.out.println("3. View All Users");
            System.out.println("4. View All Jobs");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addUser(sc);
                    break;
                case 2:
                    deleteUser(sc);
                    break;
                case 3:
                    viewAllUsers();  // View All Users
                    break;
                case 4:
                    viewAllJobs();   // View All Jobs
                    break;
                case 5:
                    managing = false; // Back to Main Menu
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

     
   
    private static boolean isEmailAlreadyExists(String email) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/job_portaldb1", "root", "R!y@@9575");
             PreparedStatement pstmt = con.prepareStatement("SELECT COUNT(*) FROM employeer_admin WHERE email = ?")) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;  // Returns true if email exists
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    

 // Add this method to validate email
public static boolean isValidEmail(String email) {
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    Pattern pattern = Pattern.compile(emailRegex);
     if(email==null)
     {
        return false;
     }
     return pattern.matcher(email).matches();
}


private static boolean isEmailAlreadyExists1(String email) {
    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/job_portaldb1", "root", "R!y@@9575");
         PreparedStatement pstmt = con.prepareStatement("SELECT COUNT(*) FROM jobseeker_admin WHERE Jobseeker_email = ?")) {
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        return rs.getInt(1) > 0;  // Returns true if email exists
    } catch (Exception e) {
        System.out.println(e);
        return false;
    }
}



    // Add a new user
    private static void addUser(Scanner sc) {

        System.out.print(" 1. Add Employeer :");
        System.out.println(" 2. Add Jobseeker :");
        int press = sc.nextInt();

        if(press == 1)
        {
        System.out.println("Enter Employeer Name: ");
        String name = sc.next();

        System.out.println("Enter Employeer Email: ");
        String email = sc.next();
        isValidEmail(email);
        
        if (!isValidEmail(email)) {
            System.out.println("Invalid email format ! Please enter a valid email");
            return;
        }

        if (isEmailAlreadyExists(email)) {
            System.out.println("Email already exists. Try a different one.");
            return;
        }

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/job_portaldb1", "root", "R!y@@9575");
            String query = "INSERT INTO employeer_admin (employeer_name, email) VALUES (?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, name);
            pstmt.setString(2, email);

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Employeer added successfully to the database!");
            }

       
            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();

       
        }  
        }
        
        else if(press == 2)
        {
            System.out.print("Enter Jobseeker Name: ");
            String name = sc.next();
            System.out.print("Enter Jobseeker Email: ");
            String email = sc.next();
            isValidEmail(email);
            
            if (!isValidEmail(email)) {
                System.out.println("Invalid email format ! Please enter a valid email");
                return;
            }
    
            // Check if Jobseeker email already exists
        if (isEmailAlreadyExists1(email)) {
            System.out.println("Email already exists. Try a different one.");
            return;
        }
            
    
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/job_portaldb1", "root", "R!y@@9575");
                String query = "INSERT INTO Jobseeker_admin (Jobseeker_name, Jobseeker_email) VALUES (?, ?)";
                PreparedStatement pstmt = con.prepareStatement(query);
    
                pstmt.setString(1, name);
                pstmt.setString(2, email);
    
                int rowsInserted = pstmt.executeUpdate();
    
                if (rowsInserted > 0) {
                    System.out.println("User added successfully to the database!");
                }
    
                pstmt.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       
    }

    // View all users
    private static void viewAllUsers() {
      
      Scanner scanner = new Scanner(System.in);
      System.out.println(" 1. View All Employeers : ");
      System.out.println(" 2. View All Jobseekers : ");
      System.out.println(" 3. View All users : ");
      int choice = scanner.nextInt();

      if(choice == 1)
      {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/job_portaldb1", "root", "R!y@@9575");
            String query = "SELECT * FROM employeer_admin";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            List<User> users = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("employeer_id");
                String name = rs.getString("employeer_name");
                String email = rs.getString("email");
                users.add(new User(id, name, email));
            }

            if (users.isEmpty()) {
                System.out.println("No users available.");
            } else {
                for (User user : users) {
                    System.out.println(user);
                }
            }
  
          
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    else if(choice ==2)
    {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/job_portaldb1", "root", "R!y@@9575");
            String query = "SELECT * FROM  jobseeker_admin";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            List<User> users = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("Jobseeker_id");
                String name = rs.getString("Jobseeker_name");
                String email = rs.getString("Jobseeker_email");
                users.add(new User(id, name, email));
            }

            if (users.isEmpty()) {
                System.out.println("No users available.");
            } else {
                for (User user : users) {
                    System.out.println(user);
                }
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    else 
    {

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
    
        try {
            // Establishing the connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/job_portaldb1", "root", "R!y@@9575");
    
            // Executing query to select all users
            String query = "SELECT employeer_admin.employeer_id AS id, employeer_admin.employeer_name AS name, employeer_admin.email AS email " +
                   "FROM employeer_admin " +
                   "UNION " +
                   "SELECT jobseeker_admin.Jobseeker_id AS id, jobseeker_admin.Jobseeker_name AS name, jobseeker_admin.Jobseeker_email AS email " +
                   "FROM jobseeker_admin";

            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
    
            // Storing users in a list
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                users.add(new User(id, name, email));
            }
    
            // Checking if users list is empty
            if (users.isEmpty()) {
                System.out.println("No users available.");
            } else {
                for (User user : users) {
                    System.out.println(user);
                }
            }
    
        } 
        catch (SQLException e) 
        {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();  // Prints detailed stack trace for debugging
        } 
        finally 
        {
            // Closing resources in finally block to ensure they close even if an exception occurs
            try {
                if (rs != null) rs.close();  // Closing ResultSet
                if (stmt != null) stmt.close();  // Closing Statement
                if (con != null) con.close();  // Closing Connection
            } catch (SQLException e) {
                e.printStackTrace();  // If any exception occurs during closing
            }
        }
    }
    }

       

    // Delete a user
    private static void deleteUser(Scanner sc) {
        System.out.print("Enter User ID to delete: ");
        int id = sc.nextInt();

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/job_portaldb1", "root", "R!y@@9575");
            String query = "DELETE FROM user WHERE user_id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setInt(1, id);

            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("User deleted successfully from the database!");
            } else {
                System.out.println("User not found in the database!");
            }

            pstmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // View all jobs
    private static void viewAllJobs() {
        // Logic for viewing all jobs
    }

    // Option 3: Generate Analytics Report
    private static void generateAnalyticsReport() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/job_portaldb1", "root", "R!y@@9575");

            // Count total users
            String userCountQuery = "SELECT COUNT(*) AS user_count FROM user";
            Statement stmt = con.createStatement();
            ResultSet rs1 = stmt.executeQuery(userCountQuery);
            rs1.next();
            int totalUsers = rs1.getInt("user_count");

            // Count total jobs
            String jobCountQuery = "SELECT COUNT(*) AS job_count FROM job";
            ResultSet rs2 = stmt.executeQuery(jobCountQuery);
            rs2.next();
            int totalJobs = rs2.getInt("job_count");

            System.out.println("Total Users: " + totalUsers);
            System.out.println("Total Jobs: " + totalJobs);

            rs1.close();
            rs2.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // User class to represent users in the system
    public static class User {
        private int id;
        private String name;
        private String email;

        public User(int id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        @Override
        public String toString() {
            return "User ID: " + id + ", Name: " + name + ", Email: " + email;
        }
    }

    // Job class to represent jobs in the system
    public static class Job {
        private int id;
        private String title;
        private String description;
        private String company;

        public Job(int id, String title, String description, String company) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.company = company;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getCompany() {
            return company;
        }

        @Override
        public String toString() {
            return "Job ID: " + id + ", Title: " + title + ", Company: " + company;
        }
    }
}
