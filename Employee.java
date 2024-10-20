import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Employee {
    Scanner s=new Scanner(System.in);
    Connection con=jdbc.con;
    PreparedStatement ps;
    ResultSet rs;

    Employee(){
        System.out.println("Enter 1:- For Add Job \nEnter 2:- For Manage Job\nEnter 3:- For Remove Jobs \nEnter 4:- For New Employee\nEnter 5:- For manageApplications");
        String n=s.nextLine();
        switch ((shortcut.changeformat(n))) {
                case 1:
                add();
                break;
                case 2:
                manage();
                break;
                case 3:
                remove();
                break;
                case 4:
                newEmployee();
                break;
                case 5:
                manageApplications();
                break;
                default:
                
                break;
        }
    }
    private void manageApplications() {
        try {
            job_seaker.posts();  // Display available job posts
            System.out.println("Enter the Serial Number (sno) of the job to manage applications:");
            int sno = shortcut.changeformat(s.nextLine());  // Get job serial number
    
            // Query to get candidate applications and personal details for the job
            String query = "SELECT s.id, s.name, s.mobile_no, s.mail, s.age, s.skills, s.address, a.status " +
                           "FROM seaker_data s " +
                           "JOIN application a ON s.id = a.id " +
                           "WHERE a.job = ?";
    
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, sno);  // Set the job serial number
    
            // Execute the query and get the result
            ResultSet rs = ps.executeQuery();
    
            // Check if there are any applicants
            if (!rs.isBeforeFirst()) {
                System.out.println("No candidates have applied for this job.");
                return;
            }
    
            // Display candidate details and allow the user to update status
            while (rs.next()) {
                int candidateId = rs.getInt("id");
                String name = rs.getString("name");
                long mobileNo = rs.getLong("mobile_no");  // Retrieves as long
                String email = rs.getString("mail");
                int age = rs.getInt("age");
                String skills = rs.getString("skills");
                String address = rs.getString("address");
                String currentStatus = rs.getString("status");
    
                // Display candidate's personal info
                System.out.println("Candidate ID: " + candidateId);
                System.out.println("Name: " + name);
                System.out.println("Mobile No: " + mobileNo);
                System.out.println("Email: " + email);
                System.out.println("Age: " + age);
                System.out.println("Skills: " + skills);
                System.out.println("Address: " + address);
                System.out.println("Current Status: " + currentStatus);
                System.out.println("-------------------------------");
    
                // Ask for a new status
                System.out.println("Enter new status for this candidate (Reject, Pending, Select):");
                String newStatus = s.nextLine();
    
                // Validate input
                if (!newStatus.equalsIgnoreCase("Reject") &&
                    !newStatus.equalsIgnoreCase("Pending") &&
                    !newStatus.equalsIgnoreCase("Select")) {
                    System.out.println("Invalid status. Skipping candidate update.");
                    continue;
                }
    
                // Update the status for this candidate in the 'application' table
                String updateQuery = "UPDATE application SET status = ? WHERE id = ? AND job = ?";
                PreparedStatement updatePs = con.prepareStatement(updateQuery);
                updatePs.setString(1, newStatus);
                updatePs.setInt(2, candidateId);
                updatePs.setInt(3, sno);
    
                // Execute the update
                updatePs.executeUpdate();
                System.out.println("Status updated for candidate: " + name);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // private String timing() {
    //   System.out.print("Enter Time to :- ");
    //   String start=s.nextLine();
    //   if ((shortcut.changeformat(start))>0&&(shortcut.changeformat(start))<=12) {
        
    //   }
    //   else{
    //     timing();
    //   }
    //     System.out.println(" Am Ya Pm :- ");
    //     String day=s.nextLine();
    //     if ((day.equalsIgnoreCase("am"))||day.equalsIgnoreCase("pm")) {
            
    //     }else{
    //         timing();
    //     }
    //     System.out.println("End Time ");
    //     String end=s.nextLine();
    //   if ((shortcut.changeformat(end)<0&&shortcut.changeformat(end)>=12)) {
        
    //   } else {
    //     timing();
    //   }
    //   System.out.println(" Am Ya Pm :- ");
    //     String dayEnd=s.nextLine();
    //     if ((dayEnd.equalsIgnoreCase("am"))||dayEnd.equalsIgnoreCase("pm")) {
            
    //     }else{
    //         timing();
    //     }
    //    if (day.equalsIgnoreCase("am")&&dayEnd.equalsIgnoreCase("pm")||day.equalsIgnoreCase("pm")&&dayEnd.equalsIgnoreCase("am")) {
        
    //    }else{
    //     timing();
    //    }
    //    return start+" "+day+" "+end+" "+dayEnd;
    // }
    private void newEmployee() {
         
           }
           private void remove() {
            try {
                job_seaker.posts();  // Display all jobs
                System.out.println("Enter the Serial Number (sno) of the job to remove:");
                int sno = shortcut.changeformat(s.nextLine());  // Getting serial number as input
        
                // Prepare the delete query based on the serial number (sno)
                String q = "DELETE FROM jobs WHERE sno = ?";
        
                // Prepare the statement
                PreparedStatement ps = con.prepareStatement(q);
        
                // Set the value for the placeholder
                ps.setInt(1, sno);
        
                // Execute the delete query
                int rowsAffected = ps.executeUpdate();
        
                // Check if the job was successfully removed
                if (rowsAffected > 0) {
                    System.out.println("Job with serial number " + sno + " has been removed.");
                } else {
                    System.out.println("No job found with serial number " + sno);
                }
        
            } catch (Exception e) {
                e.printStackTrace();
            }
            new Employee();
        }
        
        private void manage() {
            try {
                job_seaker.posts();  // Assume this method lists all jobs
                System.out.println("Enter the serial number to update:");
                int sno = shortcut.changeformat(s.nextLine());  // Getting serial number
        
                // Check if the job exists
                String checkQuery = "SELECT COUNT(*) FROM jobs WHERE sno = ?";
                PreparedStatement checkPs = con.prepareStatement(checkQuery);
                checkPs.setInt(1, sno);
                ResultSet rs = checkPs.executeQuery();
                rs.next();
                int count = rs.getInt(1);
        
                // If no job with the provided serial number, show "Not Found" message
                if (count == 0) {
                    System.out.println("Job not found for the given serial number.");
                    return;
                }
        
                // Display options for what the user wants to update
                System.out.println("What do you want to update?");
                System.out.println("1. Salary");
                System.out.println("2. Timing");
                System.out.println("3. Available Posts");
                System.out.println("4. Experience");
                System.out.println("5. Location");
                System.out.println("6. Interview Date");
                int choice = Integer.parseInt(s.nextLine());
        
                String q = "";  // SQL query placeholder
                PreparedStatement ps;
        
                // Handle user's choice
                switch (choice) {
                    case 1: // Update Salary
                        System.out.println("Enter new Salary:");
                        String salary = s.nextLine();
                        q = "UPDATE jobs SET salary = ? WHERE sno = ?";  // Update based on sno
                        ps = con.prepareStatement(q);
                        ps.setString(1, salary);
                        ps.setInt(2, sno);
                        break;
        
                    case 2: // Update Timing
                        System.out.println("Enter new Timing:");
                        String time = s.nextLine();
                        q = "UPDATE jobs SET timing = ? WHERE sno = ?";  // Update based on sno
                        ps = con.prepareStatement(q);
                        ps.setString(1, time);
                        ps.setInt(2, sno);
                        break;
        
                    case 3: // Update Available Posts
                        System.out.println("Enter new Available Posts:");
                        int availablePost = Integer.parseInt(s.nextLine());
                        q = "UPDATE jobs SET availablepost = ? WHERE sno = ?";  // Update based on sno
                        ps = con.prepareStatement(q);
                        ps.setInt(1, availablePost);
                        ps.setInt(2, sno);
                        break;
        
                    case 4: // Update Experience
                        System.out.println("Enter new Experience:");
                        String experience = s.nextLine();
                        q = "UPDATE jobs SET experience = ? WHERE sno = ?";  // Update based on sno
                        ps = con.prepareStatement(q);
                        ps.setString(1, experience);
                        ps.setInt(2, sno);
                        break;
        
                    case 5: // Update Location
                        System.out.println("Enter new Location:");
                        String location = s.nextLine();
                        q = "UPDATE jobs SET location = ? WHERE sno = ?";  // Update based on sno
                        ps = con.prepareStatement(q);
                        ps.setString(1, location);
                        ps.setInt(2, sno);
                        break;
        
                    case 6: // Update Interview Date
                        System.out.println("Enter new Interview Date (yyyy-mm-dd):");
                        String interviewDate = s.nextLine();
                        java.sql.Date interviewSQLDate = java.sql.Date.valueOf(interviewDate);
                        q = "UPDATE jobs SET interview_date = ? WHERE sno = ?";  // Update based on sno
                        ps = con.prepareStatement(q);
                        ps.setDate(1, interviewSQLDate);
                        ps.setInt(2, sno);
                        break;
        
                    default:
                        System.out.println("Invalid choice.");
                        return;
                }
        
                // Execute the update query
                ps.executeUpdate();
                System.out.println("Job details updated successfully.");
        
            } catch (Exception e) {
                e.printStackTrace();
            }
            new Employee();
        }
        
        
        private void add() {
            try {
                // Input job position name
                System.out.println("Enter The Job Position Name");
                String job = s.nextLine();
        
                // Input salary
                System.out.println("Enter The Salary");
                String salary = s.nextLine();
        
                // Input timing
                System.out.println("Enter The Time");
                String time = timing();
        
                // Input available posts
                System.out.println("How Many Posts Are There");
                int availablePost = shortcut.changeformat(s.nextLine());
        
                // Input job summary
                System.out.println("Enter The Job Summary");
                String summary = s.nextLine();
        
                // Input job location
                System.out.println("Enter The Location");
                String location = s.nextLine();
        
                // Input required experience
                System.out.println("Enter The Required Experience");
                String experience = s.nextLine();
        
                // Input company name
                System.out.println("Enter The Company Name");
                String company = s.nextLine();
        
                // Input interview date
                System.out.println("Enter The Interview Date (yyyy-mm-dd)");
                String interviewDate = s.nextLine();
                java.sql.Date interviewSQLDate = java.sql.Date.valueOf(interviewDate);
        
                // Get the current date
                java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
        
                // SQL query to insert data including the company and interview date
                String q = "INSERT INTO jobs (post, salary, timing, availablepost, Date, summary, location, experience, company, interview_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
                // Prepare the statement
                ps = con.prepareStatement(q);
        
                // Set the values for the placeholders
                ps.setString(1, job);
                ps.setString(2, salary);
                ps.setString(3, time);
                ps.setInt(4, availablePost);
                ps.setDate(5, currentDate);  // Set the current date
                ps.setString(6, summary);
                ps.setString(7, location);
                ps.setString(8, experience);
                ps.setString(9, company);
                ps.setDate(10, interviewSQLDate);  // Set the interview date
        
                // Execute the query
                ps.executeUpdate();
        
                System.out.println("Data inserted successfully!");
        
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            new Employee();
        }
        
        
        
    private String timing() {
      String defaultTime="";
      try {
        System.out.println("Select The Time");
        System.out.println("Enter 1 :- Full Time");
        System.out.println("Enter 2 :- Part Time");
     
        int num=shortcut.changeformat(s.nextLine());
        switch (num) {
            case 1:
                
                defaultTime="Full Time";
                break;
                case 2:               
                defaultTime="Part Time";
                break;
                
            default:
            defaultTime="Part Time";
                break;
        }
      } catch (Exception e) {
        System.out.println("Invalid Input...\nTry Again");
       timing();
      } 
      return defaultTime; 
    }
}
