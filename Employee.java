import java.sql.Connection;
import java.sql.Date;
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
       
        System.out.println("Enter 1:- For Add Job \nEnter 2:- For Manage Job\nEnter 3:- For Remove Jobs \nEnter 4:- For manageApplications\nEnter Any Number For Back");
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
                manageApplications();
                break;
                default:
                System.out.println("Exit......");
                break;
        }
    }
    public void manageApplications() {
        try {
            employee_posts(empId); // Display available job posts
            System.out.println("Enter the Serial Number (sno) of the job to manage applications:\nEnter 0 For Back");

            int sno = shortcut.changeformat(s.nextLine());  // Get job serial number
            if (sno==0) {
                new Employee();
                return;
            }
    
            // Query to get candidate applications and personal details for the job
            String query = "SELECT s.id, s.name, s.mobile_no, s.mail, s.age, s.skills, s.address, a.status, a.Date,a.slot " +
                           "FROM seaker_data s " +
                           "JOIN application a ON s.id = a.id " +
                           "WHERE a.job = ?";
    
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, sno);  // Set the job serial number
    
                // Execute the query and get the result
                try (ResultSet rs = ps.executeQuery()) {
                    // Check if there are any applicants
                    if (!rs.isBeforeFirst()) {
                        System.out.println("No candidates have applied for this job.");
                        manageApplications();
                        return;
                    }
    
                    // Display candidate details in a structured list
                    System.out.println("Candidates who have applied for this job:");
                    ArrayList<Integer> candidateIds = new ArrayList<>();
                    System.out.println(
                        "--------------------------------------------------------------------------------------------------------------------------------------------------------");
                    System.out.printf("| %-13s | %-10s | %-10s | %-20s | %-7s | %-7s | %-6s | %-12s | %-10s|%-10s |%n",
                        "Candidate ID", "Name", "Mobile No.", "Email", "Age", "Skills", "Address", "Current Status", "Date","Slot");
                    System.out.println(
                        "--------------------------------------------------------------------------------------------------------------------------------------------------------");    
                    while (rs.next()) {
                        int candidateId = rs.getInt("id");
                        String name = rs.getString("name");
                        Long mobileNo = rs.getLong("mobile_no");
                        String email = rs.getString("mail");
                        int age = rs.getInt("age");
                        String skills = rs.getString("skills");
                        String address = rs.getString("address");
                        String currentStatus = rs.getString("status");
                        Date interviewDate = rs.getDate("Date");
                        String slot=rs.getString("slot");
    
                        System.err.printf("| %-13s | %-10s | %-10s | %-20s | %-7s | %-7s | %-6s | %-12s | %-10s|%-10s |%n",
                            candidateId, name, mobileNo, email, age, skills, address, currentStatus, interviewDate,slot);
                        System.out.println(
                            "--------------------------------------------------------------------------------------------------------------------------------------------------------");
    
                        // Add candidateId to the list for further selection
                        candidateIds.add(candidateId);
                    }
    
                    // Ask the user to select a candidate for further actions
                    System.out.println("Enter the Candidate ID to update their application status or conduct an interview:\nEnter 0 For Back");
        
                    int selectedCandidateId = Integer.parseInt(s.nextLine());

                    if (selectedCandidateId==0) {
                        new Employee();
                        return;
                    }
                    // Validate if the selected candidate ID is in the list
                    if (!candidateIds.contains(selectedCandidateId)) {
                        System.out.println("Invalid Candidate ID. Exiting...");
                        return;
                    }else{

                        System.out.println("Show resume \n1 -> yes \n2 -> no");
                        if(shortcut.changeformat(s.nextLine())==1){
                            ResumeGenerator1.fetchAndGenerateResume(selectedCandidateId);
                        }
    
                    // Ask for a new status for the selected candidate
                    String newStatus = "";
                    while (true) {
                        System.out.println("Enter new status for this candidate (Reject, Pending, Select):");
                        newStatus = s.next();
                        if (newStatus.equalsIgnoreCase("Reject") || 
                            newStatus.equalsIgnoreCase("Pending") || 
                            newStatus.equalsIgnoreCase("Select")) {
                            break;
                        } else {
                            System.out.println("Invalid status. Please enter again.");
                        }
                    }
    
                    // Update the status for the selected candidate in the 'application' table
                    String updateQuery = "UPDATE application SET status = ? WHERE id = ? AND job = ?";
                    try (PreparedStatement updatePs = con.prepareStatement(updateQuery)) {
                        updatePs.setString(1, newStatus);
                        updatePs.setInt(2, selectedCandidateId);
                        updatePs.setInt(3, sno);
                        updatePs.executeUpdate();
                        System.out.println("Status updated for candidate ID: " + selectedCandidateId);
                    }
    
                    // Now handle interview scheduling
                    if (newStatus.equalsIgnoreCase("Select")) {
                        String dateQuery = "SELECT interview_Date FROM jobs WHERE sno = ?";
                        try (PreparedStatement datePs = con.prepareStatement(dateQuery)) {
                            datePs.setInt(1, sno);
                            try (ResultSet dateRs = datePs.executeQuery()) {
                                if (dateRs.next()) {
                                    java.sql.Date interviewDate = dateRs.getDate("interview_Date");
                                    System.out.println("Initial Interview Date: " + interviewDate);
                    
                                    String availableSlot = "";
                                    boolean slotFound = false;
                                    int slotCount = 0; // To track how many slots have been checked for the current date
                    
                                    while (!slotFound) {
                                        // Check how many candidates are already scheduled in the current date and time slot
                                        String timeSlotQuery = "SELECT COUNT(*) AS interview_count FROM application a " +
                                                               "JOIN jobs j ON a.job = j.sno " +
                                                               "WHERE j.interview_Date = ? AND a.slot = ? AND a.status = 'Select'";
                    
                                        String[] timeSlots = {"10:00 AM to 12:00 PM", "1:00 PM to 3:00 PM", "4:00 PM to 6:00 PM"};
                                        if (slotCount >= timeSlots.length) {
                                            // All slots filled for this date, move to the next date
                                            Calendar cal = Calendar.getInstance();
                                            cal.setTime(interviewDate);
                                            cal.add(Calendar.DATE, 1);
                                            interviewDate = new java.sql.Date(cal.getTimeInMillis());
                                            slotCount = 0; // Reset slot count for the new date
                                            System.out.println("All slots are filled for " + interviewDate + ". Checking the next available date.");
                                        } else {
                                            availableSlot = timeSlots[slotCount];
                                            try (PreparedStatement timeSlotPs = con.prepareStatement(timeSlotQuery)) {
                                                timeSlotPs.setDate(1, interviewDate);
                                                timeSlotPs.setString(2, availableSlot);
                                                try (ResultSet timeSlotRs = timeSlotPs.executeQuery()) {
                                                    int interviewCount = 0;
                                                    if (timeSlotRs.next()) {
                                                        interviewCount = timeSlotRs.getInt("interview_count");
                                                    }
                    
                                                    // If no candidate has taken the current slot, it's available
                                                    if (interviewCount == 0) {
                                                        System.out.println("Available Slot: " + availableSlot);
                                                        slotFound = true;
                                                    } else {
                                                        // If the slot is taken, move to the next slot
                                                        slotCount++;
                                                    }
                                                }
                                            }
                                        }
                                    }
                    
                                    // Insert interview date and slot into the application table
                                    String q = "UPDATE application SET Date = ?, slot = ? WHERE id = ?";
                                    try (PreparedStatement q2 = con.prepareStatement(q)) {
                                        q2.setDate(1, interviewDate);
                                        q2.setString(2, availableSlot);
                                        q2.setInt(3, selectedCandidateId);
                                        q2.executeUpdate();
                                        System.out.println("Interview scheduled on " + interviewDate + " during the slot: " + availableSlot);
                                    }
                                }
                            }
                        }
                    }
                    
                }}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
   
    public void remove() {
        try {
           employee_posts(empId); // Display all jobs
          // Display all jobs
          
            // Get the serial number (sno) and employeeId as input
            System.out.println("Enter the Serial Number (sno) of the job to remove:");
            int sno = shortcut.changeformat(s.nextLine());  // Getting serial number as input
            
            // Prepare the delete query based on the serial number (sno) and employeeId
            String q = "DELETE FROM jobs WHERE sno = ? AND createdBy = ?";
    
            // Prepare the statement
            PreparedStatement ps = con.prepareStatement(q);
    
            // Set the values for the placeholders
            ps.setInt(1, sno);
            ps.setInt(2, empId);
    
            // Execute the delete query
            int rowsAffected = ps.executeUpdate();
    
            // Check if the job was successfully removed
            if (rowsAffected > 0) {
                System.out.println("Job with serial number " + sno + " and employee ID " +empId + " has been removed.");
            } else {
                System.out.println("No job found with serial number " + sno + " and employee ID " + empId);
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Employee();
    }
    
    public void manage() {
        try {
            // Assume this method lists all jobs
            employee_posts(empId);
            System.out.println("Enter 1 For Back Any Number For Continue");
            int no=shortcut.changeformat(s.nextLine());
            if (no==1) {
                new Employee();
                return;
            }
            System.out.println("Enter the serial number to update:");
            
            int sno = shortcut.changeformat(s.nextLine());  // Getting serial number
            
            // Fetching the `createdBy` value for the job
            String fetchCreatedByQuery = "SELECT createdBy FROM jobs WHERE sno = ?";
            PreparedStatement fetchCreatedByPS = con.prepareStatement(fetchCreatedByQuery);
            fetchCreatedByPS.setInt(1, sno);
            
            ResultSet rs = fetchCreatedByPS.executeQuery();
            
            if (rs.next()) {
                int createdBy = rs.getInt("createdBy");
                
                // Assuming `currentEmployeeId` holds the ID of the currently logged-in employee
                int currentEmployeeId =empId; 
                
                // Check if the current employee is the one who added the job
                if (currentEmployeeId != createdBy) {
                    System.out.println("You do not have permission to update this job.");
                    return;
                }
            } else {
                System.out.println("Job not found with the given serial number.");
                return;
            }
    
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
    
        
    public void add() {  // Accept employee ID as a parameter
        try {
            // Input employee ID (assuming it's passed as empId)
            int employeeId = empId;
          
            
            
            // Input job position name
            System.out.println("Enter The Job Position Name:");
            String job = s.nextLine();
    
            // Input salary
            System.out.println("Enter The Salary:");
            String salary = s.nextLine();
    
            // Input timing
            System.out.println("Enter The Time:");
            String time = timing();
    
            // Input available posts
            System.out.println("How Many Posts Are There:");
            int availablePost = shortcut.changeformat(s.nextLine());
    
            // Input job summary
            System.out.println("Enter The Job Summary:");
            String summary = s.nextLine();
    
            // Input job location
            System.out.println("Enter The Location:");
            String location = s.nextLine();
    
            // Input required experience
            System.out.println("Enter The Required Experience:");
            String experience = s.nextLine();
    
            // Input company name
            System.out.println("Enter The Company Name:");
            String company = s.nextLine();
    
            // Input interview date
            System.out.println("Enter The Interview Date (yyyy-mm-dd):");
            String interviewDateString = s.nextLine();
            java.sql.Date interviewDate = java.sql.Date.valueOf(interviewDateString); // Convert String to Date
    
            // Get the current date
            java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
    
            // SQL query to insert data including the interview date and createdBy (employee ID)
            String q = "INSERT INTO jobs (post, salary, timing, availablepost, Date, summary, location, experience, company, interview_Date, createdBy) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
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
            ps.setDate(10, interviewDate);  // Set the interview date
            ps.setInt(11, employeeId);  // Set the employee ID in the createdBy column
    
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
        while (true) {
  
        System.out.print("Enter Login id -> ");
        int tempId = shortcut.changeformat(s.nextLine());

        System.out.print("Enter password -> ");
        String password= s.nextLine();

        try {
            Statement st=jdbc.con.createStatement();
            rs=st.executeQuery("select pass,id_status from js_acc where id = "+tempId+" and role = 'employee'");
            int tempnum2=0;
            String status="";
            while (rs.next()) {
                String temp_pass = rs.getString("pass");
                status=rs.getString("id_status");
                if(temp_pass.equals(password)){
                    tempnum2++;
                }
            }

            if(tempnum2==0){
               System.out.println("Your id and password is not found");
               System.out.println("may be your password is incorrect");
               System.out.println("or your id does'nt exist");
            //   employeeLogin();
            }else if(tempnum2==1){
            
                // System.out.println("------------------------------");
                // System.out.println("Logged in successfully");
                // System.out.println("------------------------------");
                if(status.equals("ACTIVE")){
                    empId=tempId;
                   new Employee();
                   return;
                }else if(status.equals("BLOCK")){
                    System.out.println("You are suspended or blocked");
                }
            }
            else{
               System.out.println("error");
               System.out.println("error in login job seaker account ");

            }

        }catch (Exception exception) {
            System.out.println(exception);
            System.out.println("PROBLEM IN CHECKING DETAILS");
        }

        System.out.println("1. Re try");
        System.out.println("2. Back");
        int tempselection=shortcut.changeformat(s.nextLine());
        if(tempselection==1){

        }else{
            return;
        }
    }
    }

public static void employee_posts(int empId) {  // Pass the empId as an argument

    try {
        // Prepare a query to select jobs created by the employee with the given empId
        String query = "SELECT * FROM jobs WHERE createdBy = " + empId;
        Statement st = jdbc.con.createStatement();
        ResultSet rs = st.executeQuery(query);

        // Check if there are any jobs created by the employee
        if (!rs.isBeforeFirst()) {
            System.out.println("There is no job available at this time.");
            return;
        }

        // Loop through the result set and display job details in a poster format
        while (rs.next()) {
            System.out.println("===============================================================");
            System.out.printf("| %-4s : %-50s |%n", "ID", rs.getString("sno"));
            System.out.printf("| %-4s : %-50s |%n", "Company", rs.getString("company"));
            System.out.printf("| %-4s : %-50s |%n", "Post", rs.getString("post"));
            System.out.printf("| %-4s : %-50s |%n", "Timing", rs.getString("timing"));
            System.out.printf("| %-4s : %-50s |%n", "Location", rs.getString("location"));
            System.out.printf("| %-4s : %-50s |%n", "Date", rs.getDate("Date"));
            System.out.printf("| %-4s : %-50s |%n", "Salary", rs.getString("salary"));
            System.out.printf("| %-4s : %-50s |%n", "Available Posts", rs.getInt("availablepost"));
            System.out.printf("| %-4s : %-50s |%n", "Summary", rs.getString("summary"));
            System.out.printf("| %-4s : %-50s |%n", "Experience", rs.getString("experience"));
            System.out.printf("| %-4s : %-50s |%n", "Interview Date", rs.getDate("interview_Date"));
            System.out.println("===============================================================\n");
        }

    } catch (Exception e) {
        System.out.println("Error while receiving data.");
        e.printStackTrace();
    }
} 
}