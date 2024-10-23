import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;


public class Employee {
   static Scanner s=new Scanner(System.in);
    Connection con=jdbc.con;
    PreparedStatement ps;
    static ResultSet rs;
    static int empId;
    Employee(){
        System.out.println("Enter 1:- For Add Job \nEnter 2:- For Manage Job\nEnter 3:- For Remove Jobs \nEnter 4:-  For manageApplications");
        String n=s.nextLine();
        switch ((shortcut.changeformat(n))) {
                case 1:
                add(empId);
                break;
                case 2:
                manage();
                break;
                case 3:
                remove();
                break;
                case 4:
                manageApplications();
                break;
                default:
                
                break;
        }
    }
  public void manageApplications() {
    try {
        employee_posts(empId);
        // job_seaker.posts();  // Display available job posts

        System.out.println("Enter the Serial Number (sno) of the job to manage applications:");
        int sno = shortcut.changeformat(s.nextLine());  // Get job serial number

        
        // Query to get candidate applications and personal details for the job
        String query = "SELECT s.id, s.name, s.mobile_no, s.mail, s.age, s.skills, s.address, a.status " +
                       "FROM seaker_data s " +
                       "JOIN application a ON s.id = a.id " +
                       "WHERE a.job = ? ";

        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, sno);  // Set the job serial number

        // Execute the query and get the result
        ResultSet rs = ps.executeQuery();

        // Check if there are any applicants
        if (!rs.isBeforeFirst()) {
            // System.out.println("----------------------------------------");
            System.out.println("No candidates have applied for this job.");
            manageApplications();
            return;
        }

        // Display candidate details in a structured list
        System.out.println("Candidates who have applied for this job:");
        ArrayList<Integer> candidateIds = new ArrayList<>();
        while (rs.next()) {
            int candidateId = rs.getInt("id");
            String name = rs.getString("name");
            Long mobileNo = rs.getLong("mobile_no");
            String email = rs.getString("mail");
            int age = rs.getInt("age");
            String skills = rs.getString("skills");
            String address = rs.getString("address");
            String currentStatus = rs.getString("status");

            // Display candidate's personal info in a structured format
            System.out.println("Candidate ID: " + candidateId);
            System.out.println("Name: " + name);
            System.out.println("Mobile No: " + mobileNo);
            System.out.println("Email: " + email);
            System.out.println("Age: " + age);
            System.out.println("Skills: " + skills);
            System.out.println("Address: " + address);
            System.out.println("Current Status: " + currentStatus);
            System.out.println("-------------------------------");

            // Add candidateId to the list for further selection
            candidateIds.add(candidateId);
        }

        // Ask the user to select a candidate for further actions
        System.out.println("Enter the Candidate ID to update their application status or conduct an interview:");
        int selectedCandidateId = Integer.parseInt(s.nextLine());

        // Validate if the selected candidate ID is in the list
        if (!candidateIds.contains(selectedCandidateId)) {
            System.out.println("Invalid Candidate ID. Exiting...");
            return;
        }

        // Ask for a new status for the selected candidate
        System.out.println("Enter new status for this candidate (Reject, Pending, Select):");
        String newStatus = s.nextLine();

        // Validate input
        if (!newStatus.equalsIgnoreCase("Reject") &&
            !newStatus.equalsIgnoreCase("Pending") &&
            !newStatus.equalsIgnoreCase("Select")) {
            System.out.println("Invalid status. Operation aborted.");
            return;
        }

        // Update the status for the selected candidate in the 'application' table
        String updateQuery = "UPDATE application SET status = ? WHERE id = ? AND job = ?";
        PreparedStatement updatePs = con.prepareStatement(updateQuery);
        updatePs.setString(1, newStatus);
        updatePs.setInt(2, selectedCandidateId);
        updatePs.setInt(3, sno);

        // Execute the update
        updatePs.executeUpdate();
        System.out.println("Status updated for candidate ID: " + selectedCandidateId);

        // Now handle interview scheduling
        if (newStatus.equalsIgnoreCase("Select")) {
            // Get interview date from the 'jobs' table
            String dateQuery = "SELECT interview_Date FROM jobs WHERE sno = ?";
            PreparedStatement datePs = con.prepareStatement(dateQuery);
            datePs.setInt(1, sno);
            ResultSet dateRs = datePs.executeQuery();

            if (dateRs.next()) {
                java.sql.Date interviewDate = dateRs.getDate("interview_Date");
                System.out.println("Interview Date: " + interviewDate);

                // Check for existing interviews on this date
                String timeSlotQuery = "SELECT COUNT(*) AS interview_count FROM application a " +
                                       "JOIN jobs j ON a.job = j.sno " +
                                       "WHERE j.interview_Date = ? AND a.status = 'Select'";
                PreparedStatement timeSlotPs = con.prepareStatement(timeSlotQuery);
                timeSlotPs.setDate(1, interviewDate);
                ResultSet timeSlotRs = timeSlotPs.executeQuery();

                int interviewCount = 0;
                if (timeSlotRs.next()) {
                    interviewCount = timeSlotRs.getInt("interview_count");
                }

                // Offer time slots based on the current count of interviews
                if (interviewCount < 3) {
                    System.out.println("Available Slot: 10:00 AM to 12:00 PM");
                } else if (interviewCount < 6) {
                    System.out.println("Available Slot: 1:00 PM to 3:00 PM");
                } else if (interviewCount < 9) {
                    System.out.println("Available Slot: 4:00 PM to 6:00 PM");
                } else {
                    // All slots filled, increase the date
                    //interviewDate = new Date(interviewDate.getTime() + (1000 * 60 * 60 * 24)); // Next day
                    System.out.println("All slots are filled for this date. Next available date: " + interviewDate);
                }
            }
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
    // public void newEmployee() {

    //        }
           public void remove() {
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
        
         public void manage() {
            try {
                job_seaker.posts();  // Assume this method lists all jobs
                System.out.println("Enter the serial number to update:");
                int sno = shortcut.changeformat(s.nextLine());  // Getting serial number
        
                // Display options for what the user wants to update
                System.out.println("What do you want to update?");
                System.out.println("1. Salary");
                System.out.println("2. Timing");
                System.out.println("3. Available Posts");
                System.out.println("4. Experience");
                System.out.println("5. Location");
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
        
                    default:
                        System.out.println("Invalid choice.");
                        return;
                }
        
                // Execute the query
                ps.executeUpdate();
                System.out.println("Job details updated successfully.");
        
            } catch (Exception e) {
                e.printStackTrace();
            }
            new Employee();
        }
        
        public  void add(int employeeId) {  // Accept employee ID as a parameter
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
        
                // Get the current date
                java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
        
                // SQL query to insert data including the company and createdBy (employee ID)
                String q = "INSERT INTO jobs (post, salary, timing, availablepost, Date, summary, location, experience, company, createdBy) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
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
                ps.setInt(10, employeeId);  // Set the employee ID in the createdBy column
        
                // Execute the query
                ps.executeUpdate();
        
                System.out.println("Data inserted successfully!");
        
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            new Employee();  // Call to Employee class, if necessary
        }
        
        // Timing method remains unchanged
        public String timing() {
            String defaultTime = "";
            try {
                System.out.println("Select The Time");
                System.out.println("Enter 1 :- Full Time");
                System.out.println("Enter 2 :- Part Time");
        
                int num = shortcut.changeformat(s.nextLine());
                switch (num) {
                    case 1:
                        defaultTime = "Full Time";
                        break;
                    case 2:
                        defaultTime = "Part Time";
                        break;
                    default:
                        defaultTime = "Part Time";
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid Input...\nTry Again");
                timing();
            }
            return defaultTime;
        }
        

    public static void employeeLogin(){
        System.out.print("Enter Login id -> ");
        int tempId = shortcut.changeformat(s.nextLine());

        System.out.print("Enter password -> ");
        String password= s.nextLine();

        try {
            Statement st=jdbc.con.createStatement();
            rs=st.executeQuery("select pass from js_acc where id = "+tempId+" and role = 'employee'");
            int tempnum2=0;
            while (rs.next()) {
                String temp_pass = rs.getString("pass");
                if(temp_pass.equals(password)){
                    tempnum2++;
                }
            }

            if(tempnum2==0){
               System.out.println("Your id and password is not found");
               System.out.println("may be your password is incorrect");
               System.out.println("or your id does'nt exist");
              employeeLogin();
            }else if(tempnum2==1){
                System.out.println("------------------------------");
                System.out.println("Logged in successfully");
                System.out.println("------------------------------");
                empId=tempId;
               // run 
               new Employee();
            }
            else{
               System.out.println("error");
               System.out.println("error in login job seaker account ");

            }

        }catch (Exception exception) {
            System.out.println(exception);
            System.out.println("PROBLEM IN CHECKING DETAILS");
        }
    }

    public static void employee_posts(int jId) {

        try {
            // String query="select * from jobs"
            Statement st = jdbc.con.createStatement();
            rs = st.executeQuery("select * from jobs where sno = "+jId);
            // System.out.println(rs.next());
            // String [] postsarray = new String[5];
            if(!rs.isBeforeFirst()){
                System.out.println("There is no job available at this time");
                return;
            }
        
            while (rs.next()==true) {
               
                System.out.println("_______________________________________________________________");
                // System.out.printf("| %-60s|%n","-------------------------------------------------------------");
                // System.out.printf("| %-60s |%n","Serial number -> "+rs.getString("sno"));
                
                System.out.printf("| %-4s |%-6s%-46s|%n",
                                "ID - "+ rs.getString("sno"),"",rs.getString("company") );
                System.out.printf("| %-60s |%n","  "+rs.getString("post"));
                // System.out.printf("| %-60s |%n","Salary -> "+rs.getString("salary"));
                System.out.printf("| %-60s |%n","  "+rs.getString("timing"));
                System.out.printf("| %-60s |%n","  "+rs.getString("location"));
                System.out.printf("| %-60s |%n","  "+rs.getDate("Date"));
                // System.out.println("-------------------------------------------------------------");
                System.out.printf("|%-60s|%n","______________________________________________________________");
                System.out.println();
            }
        
            // System.out.println(
            //         "--------------------------------------------------------------------------------------------------------------------------------------------------------");

        } catch (Exception e) {
            System.out.println("error while receving");
            System.out.println(e);
            e.getStackTrace();
        }
    }

    
}









