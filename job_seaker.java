
import java.sql.Statement;
import java.time.YearMonth;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


// import java.sql.Date;


public class job_seaker {

        static ResultSet rs;
        static Statement st;
        static Scanner sc = new Scanner(System.in);
        static PreparedStatement pst;
       

        // Seeker personal details3
    public static void seekerInfo(int id){
            // Scanner scanner = new Scanner(System.in);
           

                System.out.println("1 - UPLODE YOUR'S RESUME");
                System.out.println("2 - APPLY FOR JOB'S");
                boolean trackdata=shortcut.checkdataexist(id, "id", "application");
                boolean isDataExists = shortcut.checkdataexist(id, "id", "seaker_data");
                if(trackdata==true){
                    System.out.println("3 - Track Application");
                }
                if(isDataExists==true){
                    System.out.println("4 - View self data");
                }
                System.out.println("5 - Logout");
                // System.out.println("4 - TRACK APPLICATION");
                // System.out.println("5 - Exit");
                System.out.print("Enter your choice: ");
                int choice = shortcut.changeformat(sc.nextLine());

                
                    if(choice==1){
                        System.out.println("Do you want to uplode resume ");
                        System.out.println("1 -> yes \n2 -> no");
                        if(shortcut.changeformat(sc.nextLine())==1){
                            try{
                                String query = "UPDATE seaker_data SET resume = 'yes' WHERE id = ?";
                                PreparedStatement pst = jdbc.con.prepareStatement(query);
                                pst.setInt(1, id);
                                int rowsUpdated = pst.executeUpdate();
                                if (rowsUpdated > 0) {
                                    System.out.println("Resume updated successfully.");
                                } else {
                                    System.out.println("Job seeker not found.");
                                }
                            }catch(Exception e){
                                System.out.println("error in 49 in job seaker");
                            }
                        }else{
                            System.out.println("Sure ! no problem");
                            seekerInfo(id);
                        }
                    }
                    else if(choice==2){
                        // System.out.println("APPLY FOR JOBS.");
                        seaker(id);
                    }
                    else if(choice==5){
                        System.out.println("Logout");
                    }
                    else if(choice==3 && trackdata==true){
                        //track application
                        trackApplication(id);
                        seekerInfo(id);
                    }else if(choice==4 && isDataExists==true){
                        // System.out.println("This method is in working state ");
                        // make update method and call from here 
                        try{
                            String sql = "select * from seaker_data where id = "+id;
                            st=jdbc.con.createStatement();
                            rs=st.executeQuery(sql);

                            while(rs.next()){
                                System.out.println("-----------------------------------------------");
                                System.out.println("Your id   -> "+rs.getString("id"));
                                System.out.println("Name      -> "+rs.getString("name"));
                                System.out.println("Mobile no -> "+rs.getString("mobile_no"));
                                System.out.println("Mail      -> "+rs.getString("mail"));
                                System.out.println("Age       -> "+rs.getString("age"));
                                System.out.println("Skills    -> "+rs.getString("skills"));
                                System.out.println("Address   -> "+rs.getString("address"));
                                System.out.println("------------------------------------------------");

                                System.out.println("1. Update");
                                System.out.println("2. Back");
                                int tempp=shortcut.changeformat(sc.nextLine());
                                if(tempp==1){
                                    updateYourSelf(id);
                                }else{
                                    seekerInfo(id);
                                }
                            }
                            
                        }catch(Exception e){
                            System.out.println(e);
                        }
                    }
                    else{
                        System.out.println("Invalid choice. Please enter a valid choice");
                        seekerInfo(id);
                    }
        }

    // shows posts names details
    public static void posts() {

        try {
       // String query="select * from jobs"     
            st = jdbc.con.createStatement();
            rs = st.executeQuery("select * from jobs");
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

        // try {
        //     // String query="select * from jobs"
            // st = jdbc.con.createStatement();
        //     rs = st.executeQuery("select * from jobs");

        //     System.out.println(
        //             "--------------------------------------------------------------------------------------------------------------------------------------------------------");
        //     System.out.printf("| %-15s | %-15s | %-15s | %-15s | %-15s |%n",
        //             "ID", "POST", "SALARY", "TIMING", "SEATS");

        //     System.out.println(
        //             "--------------------------------------------------------------------------------------------------------------------------------------------------------");

        //     while (rs.next()) {
        //         int id = rs.getInt("sno");
        //         String post = rs.getString("post");
        //         String salary = rs.getString("salary");
        //         String timing = rs.getString("timing");
        //         int seats = rs.getInt("availablepost");

        //         System.out.printf("| %-15s | %-15s | %-15s | %-15s | %-15s |%n",
        //                 id, post, salary, timing, seats);

        //     }
        //     System.out.println(
        //             "--------------------------------------------------------------------------------------------------------------------------------------------------------");

        // } catch (Exception e) {
        //     System.out.println("error while receving");
        //     System.out.println(e);
        //     e.getStackTrace();
        // }
    }

    // seaker works
    public static void seaker(int id) {
        System.out.println("AVAILABLE POSTS ARE SHOWN BELOW ");
        System.out.println();
    
        // Call post method so all posts are shown
        posts();
    
        System.out.println("ENTER SERIAL NUMBER FOR SELECT POST ");
        System.out.println("PRESS -1 FOR MAIN MENU");
    
        String temp_selected = sc.nextLine();
        int select = 0;
    
        try {
            select = Integer.parseInt(temp_selected);
            st = jdbc.con.createStatement();
            rs = st.executeQuery("select sno from jobs");
    
            int temp = 0;
            while (rs.next()) {
                if (rs.getInt("sno") == select) {
                    temp = 1;
                }
            }
            if (temp == 0 && select != -1) {
                throw new Exception("Invalid selection exception");
            } else {
                if (temp == 1) {
                    // Next step
                    // Display summary of selected job
                    post2(select);
                    System.out.println("------------------------------");
                    System.out.println("1 -> Apply for this job");
                    System.out.println("Back Menu - press any number");
                    int sel = shortcut.changeformat(sc.nextLine());
    
                    if (sel == 1) {
                        // Check if the user has already applied for this job
                        try {
                            pst = jdbc.con.prepareStatement("select count(*) from application where id = ? and job = ?");
                            pst.setInt(1, id);
                            pst.setInt(2, select);
                            ResultSet rsCheck = pst.executeQuery();
                            rsCheck.next();
                            int alreadyApplied = rsCheck.getInt(1);
    
                            if (alreadyApplied > 0) {
                                System.out.println("You have already applied for this job.");
                                seekerInfo(id);
                            } else {
                                // Proceed to apply for the job
                                pst = jdbc.con.prepareStatement("insert into application (id, job, status) values (?, ?, ?)");
                                pst.setInt(1, id);
                                pst.setInt(2, select);
                                pst.setString(3, "pending");
                                int result = pst.executeUpdate();
                                if (result == 1) {
                                    System.out.println("Applied successfully");
                                    seekerInfo(id);
                                } else {
                                    System.out.println("Unable to apply");
                                }
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    } else {
                        seekerInfo(id);
                    }
                } else if (select == -1) {
                    // Back button
                    seekerInfo(id);
                }
            }
        } catch (Exception e) {
            System.out.println("Enter valid selection");
            seaker(id);
        }
    }
     
    
    public static int takingdetails(int id) {
        System.out.println("-----------ENTER YOUR PERSONAL DETAILS -----------");

        System.out.println("0->Back");
        System.out.print("Enter your name -> ");
        String name = shortcut.nameFormating();
        
    System.out.println("0->Back");
        System.out.print("Enter your mobile number -> ");
        long phonenumber = shortcut.phonenumbertaking();

        System.out.println("0->Back");
        // System.out.print("Enter your mail id -> ");
        String mail = shortcut.validMailTaking();
        System.out.println("0->Back");

        System.out.print("Enter your age -> ");
        int age = shortcut.ageCheck();
        
        
    System.out.println("1->Back");
        System.out.print("Enter your Address -> ");
        String address = sc.nextLine();
        if (address.matches("1")) {
            System.out.println("Exiting...");
            job_seaker.acc();
        }
        System.out.println("1->Back");

        System.out.println("Use (,) to differentiate languages, no spaces.");
        System.out.print("Enter your Skills -> ");
        String skills = sc.nextLine().replace(" ", "");
        if (skills.matches("1")) {
            System.out.println("Exiting...");
            job_seaker.acc();
        }
        System.out.println("0-> Back");
        System.out.println("Do you have any experience?");
        System.out.print("1 -> Yes \nAny other number -> No\n-> ");
        
        int experienceSelection = shortcut.changeformat(sc.nextLine());
    
        if (experienceSelection == 1) {
            experience(id);  // Call to collect experience details
        }
        System.out.println("0->Back");

        System.out.println("Have you completed any projects?");
        System.out.print("1 -> Yes \nAny other number -> No\n-> ");
        int projectSelect = shortcut.changeformat(sc.nextLine());
    
        if (projectSelect == 1) {
            project(id);  // Call to collect project details
        }
    
        String sql = "INSERT INTO seaker_data(id, name, mobile_no, mail, age, skills, address, experienced, project, date) " +
                     "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, current_date())";
    
        try {
            pst = jdbc.con.prepareStatement(sql);
    
            pst.setInt(1, id);
            pst.setString(2, name);
            pst.setLong(3, phonenumber);
            pst.setString(4, mail);
            pst.setInt(5, age);
            pst.setString(6, skills);
            pst.setString(7, address);
            pst.setString(8, experienceSelection == 1 ? "yes" : "no");
            pst.setString(9, projectSelect == 1 ? "yes" : "no");
    
            return pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error inserting data: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
    

    public static void experience(int id){
        
        System.out.print("Enter company name you worked for -> ");
        String cName=sc.nextLine();

        System.out.print("Enter your job tittle / post -> ");
        String job_name=sc.nextLine();

        System.out.println("When you start your job (month / year)");
        // temp_start_date=shortcut.selective_date();
        // String start_date= temp_start_date.atDay(1).toString(); // Converts to 'YYYY-MM-DD'
        YearMonth temp_start_date;
        do {
             temp_start_date=shortcut.selective_date();
             
            } while (temp_start_date==null);

           String start_date = temp_start_date.atDay(1).toString();

        System.out.println("When you leave your company (month / year)");
        YearMonth temp_end_date;
   // Converts to 'YYYY-MM-DD'

     String end_date;
        do {
            System.out.println("Your date must be comes after starting date");
            temp_end_date=shortcut.selective_date();
            
          
        } while (temp_end_date==null || temp_end_date.isBefore(temp_start_date));
         end_date= temp_end_date.atDay(1).toString();
        
        try{
            String q1="insert into experience (id, cName, jobTittle, starts, ends) values (?,?,?,?,?)";
            pst=jdbc.con.prepareStatement(q1);

            pst.setInt(1, id);
            pst.setString(2, cName);
            pst.setString(3, job_name);
            pst.setString(4, start_date);
            pst.setString(5, end_date);

            pst.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
            e.getStackTrace();
        }
        
    }

    public static void project(int id){
        System.out.print("Enter name of your project -> ");
        String pname=sc.nextLine();

        System.out.print("Enter skills used in project -> ");
        String pskills=sc.nextLine();

        System.out.println("Enter short summary about your project ");
        System.out.println(" -> ");
        String psummary=sc.nextLine();

        try{
            String sql = "insert into project(id, pName, skills_used, summary) values(?,?,?,?)";
            pst=jdbc.con.prepareStatement(sql);
            
            pst.setInt(1, id);
            pst.setString(2, pname);
            pst.setString(3, pskills);
            pst.setString(4, psummary);

            pst.executeUpdate();
            // if(0==pst.executeUpdate()){
            //     System.out.println("there was an error in adding project specific data");
            // }

        }catch(Exception e){
            System.out.println("there was an error in adding project specific data");
            System.out.println(e);
            e.getStackTrace();
        }

    }

    public static void acc() {
       System.out.println("-----------------------");
       System.out.println("1 -> login");
       System.out.println("2 -> signup");
       System.out.println("3 -> Back");

       String selection = sc.nextLine();
       int select=shortcut.changeformat(selection);

        if(select==2){
            int id = shortcut.generateradomnumber(6);
            
            System.out.println("-------------------------------------");
            System.out.println(" CREATE PROFILE ");
        int num= job_seaker.takingdetails(id);
        System.out.println("checking nummmmm"+num);
         if (num>0) {
            System.out.println("-----------------------------------");
            System.out.println("Your id -> "+ id);
           System.out.println("Create password -> ");
           String password=shortcut.passwordCheck();
           
           try{
               String sqlquery="insert into js_acc values(?,?,?,?)";
               pst=jdbc.con.prepareStatement(sqlquery);
               pst.setInt(1, id);
               pst.setString(2, password);
               pst.setString(3, "seaker");
               pst.setString(4, "ACTIVE");
               pst.executeUpdate();
               
               System.out.println("-------------------------------------");
               System.out.println("Please remeber your information");
               System.out.println("Your id is -> "+id);
               System.out.println("your password is -> "+password);
               
               System.out.println("--------------------------------------") ;         
                System.out.println("YOU ARE PROFILE CREATED SUSCESSFULLY");
                System.out.println("--------------------------------------");
               
               job_seaker.seekerInfo(id);
            }catch(Exception e){
                System.out.println(e);
                System.out.println("Unable to create profile of yours ");
            }
         }
         else{
            System.out.println("Somthing Went Wrong !....");

         }

        }
        else if(select==1){
            // implement login method 
            
            System.out.print("Enter your id -> ");
            int id=shortcut.changeformat(sc.nextLine());

            System.out.print("Enter your password -> ");
            String password=sc.nextLine();

            try {
                st=jdbc.con.createStatement();
                rs=st.executeQuery("select pass,id_status from js_acc where id = "+id+" and role='seaker' ");
                int tempnum2=0;
                String status="";
                while (rs.next()) {
                    status=rs.getString("id_status");
                    String temp_pass = rs.getString("pass");

                    if(temp_pass.equals(password)){
                        if(status.equals("ACTIVE")){
                            tempnum2++;
                        }
                        if(status.equals("BLOCK")){
                            tempnum2= -5;
                        }
                    }
                    
                }

                if(tempnum2==0){
                   System.out.println("Your id and password is not found");
                   System.out.println("may be your password is incorrect");
                   System.out.println("or your id does'nt exist");
                   acc();
                }else if(tempnum2== -5){
                    System.out.println("------------------------------");
                    System.out.println("You are susspended or block");
                    System.out.println("-------------------------------");
                }
                else if(tempnum2==1){
                    System.out.println("------------------------------");
                    System.out.println("Logged in successfully");
                    System.out.println("------------------------------");

                    // change method their not seaker info , direct track application
                    seekerInfo(id);
                }
                else{
                   System.out.println("error");
                   System.out.println("error in login job seaker account ");

                }

            }catch (Exception exception) {
                System.out.println(exception);
                System.out.println("PROBLEM IN CHECKING DETAILS");
            }

        }else if(select==3){
            Main.mainwork();
        }
        else{
            System.out.println("Invalid selection");
            acc();
        }
        
       
    }

    public static void post2(int sno){
        try {
            // String query="select * from jobs"
            st = jdbc.con.createStatement();
            rs = st.executeQuery("select * from jobs where sno ="+sno);

            while (rs.next()){
                System.out.println("Company    -> "+ rs.getString("company"));
                System.out.println("Job tittle -> "+ rs.getString("post"));
                System.out.println("Timing     -> "+rs.getString("timing"));
                System.out.println("location   -> "+rs.getString("location"));
                System.out.println("Available post -> "+rs.getString("availablepost"));
                System.out.println("Experience -> "+rs.getString("experience"));
                System.out.println("Experience -> "+rs.getDate("Date"));
                System.out.println(rs.getString("summary"));
            }

        }catch(Exception e){
            System.out.println(e);        }
    }

    public static void trackApplication(int id){
        try{
            String query = "select j.company,j.post,a.status from application a join jobs j on j.sno = a.job where a.id = "+id  ;
            st=jdbc.con.createStatement();
            rs=st.executeQuery(query);

            while(rs.next()){
                System.out.println("--------------------------------------------------");
                System.out.printf("| %-10s | %-20s | %-10s |%n",
                   rs.getString("j.company"), rs.getString("j.post"), rs.getString("a.status") );
                   System.out.println("--------------------------------------------------");
            }

        }catch(Exception e){
            System.out.println("Error in recieving application details");
            System.out.println(e);
        }
    }

    public static void updateYourSelf(int id){
        // int tempNum =0;
        while(true){
        System.out.println("What you want to update :-");
        System.out.println("1 -> Name");
        System.out.println("2 -> Mobile number");
        System.out.println("3 -> Address");
        System.out.println("4 -> Email Address");
        System.out.println("5 -> Age");
        System.out.println("6 -> Skill");
        System.out.println("7 -> Back ");
        int updateSelection = shortcut.changeformat(sc.nextLine());
        System.out.println("--------------------------------------");

        if(updateSelection==1){
            System.out.print("Enter your Correct Name :- ");
            String name = sc.nextLine();
            shortcut.updating("name", name, id, "seaker_data");
            
        }else if(updateSelection==2){
            System.out.println("Enter your correct/new mobile number :- ");

            Long newMobileNumber = shortcut.phonenumbertaking();
            shortcut.updating("mobile_no", newMobileNumber.toString(), id, "seaker_data");

            // shortcut.updating("mobile_no", shortcut.phonenumbertaking().toString(), id, null);

        }else if(updateSelection==3){
            System.out.println("Enter your correct/new Address :- ");
            shortcut.updating("address", sc.nextLine(), id, "seaker_data");
            

        }else if(updateSelection==4){
            System.out.println("Enter your Email Address :- ");
            String email = sc.nextLine();
            email=email.replace(" ", "");
            shortcut.updating("mail", email, id, "seaker_data");

        }
        else if(updateSelection==5){
            System.out.println("Enter your age ");
            int age = shortcut.changeformat(sc.nextLine());
            shortcut.updating("age", String.valueOf(age), id, "seaker_data");


            // shortcut.updating("age", String.valueOf(shortcut.changeformat(sc.nextLine())), id, "seaker_data");


        }else if(updateSelection==6){
           System.out.println("You have to re enter all your skills");
           System.out.println("Enter your skills :- ");
        //    String st=sc.nextLine();
           shortcut.updating("skills", sc.nextLine(), id, "seaker_data");
        }
        else if(updateSelection==7){
            seekerInfo(id);

            // tempNum++;
            // continue;
            return;
        }
        else{
            System.out.println("Invalid selection");
            // updateYourSelf(id);
        }}
        
    }


}
