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
                        // System.out.println("UPLODE YOUR'S RESUME.");
                        // seekerInfo(id);
                        ResumeUtils.uploadResume(id);
                        seekerInfo(id);
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

        // call post method so all of post are shown
        
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
                // String tempString = "kk";
                // int tempp = Integer.parseInt(tempString);
                throw new Exception("Invalid selection exception");
            } else {
               if(temp==1){
                //next step
                //sout summary of selected job
                post2(select);
                System.out.println("------------------------------");
                System.out.println("1 -> Apply for this job");
                System.out.println("Back Menu - press any number");
                int sel=shortcut.changeformat(sc.nextLine());
                if(sel==1){
                    // apply
                    // System.out.println("applied");

                    try{
                        pst=jdbc.con.prepareStatement("insert into application (id, job,status) values (?,?,?)");
                        pst.setInt(1, id);
                        pst.setInt(2, select);
                        pst.setString(3, "pending");
                        int result = pst.executeUpdate();
                        if(result==1){
                            System.out.println("Applied succefully");
                            seekerInfo(id);
                        }else{
                            System.out.println("Unable to apply");
                        }

                    }catch(Exception e ){
                        System.out.println(e);
                    }
                }else{
                    seekerInfo(id);
                }



               }else if(select==-1){
                // back button
                // System.out.println("back");
                 // just fr testing
                 seekerInfo(id);
               }
            }

        } catch (Exception e) {
            System.out.println("Enter valid selection");
            seaker(id);
        }
    }

    public static int takingdetails(int id) {
        

        System.out.println("-----------ENTER  YOUR PERSONAL DETAILS -----------");
        System.out.print("Enter your name -> ");
        String name = shortcut.nameFormating();
        System.out.print("Enter your mobile number -> ");
        // long number=sc.nextLong(10);       
         long phonenumber = shortcut.phonenumbertaking();
        System.out.print("Enter your mail id -> ");
        String mail = sc.nextLine();
        System.out.print("Enter your age -> ");
        int age = shortcut.changeformat(sc.nextLine());
        System.out.print("Enter your Address -> ");
        String address = sc.nextLine();
        System.out.print("Enter your Skills -> ");
        String skills = sc.nextLine();

       

        System.out.println("Do you have any experience");
        System.out.print("1 -> Yes \nany other number -> No");
        System.out.print(" -> ");
        int experience_selection = shortcut.changeformat(sc.nextLine());
        if (experience_selection == 1) {
            // experience taken
            // with ref of id
            experience(id);
        }

        System.out.println("had you done any project yet! ");
        System.out.print("1 -> Yes \nany other number -> No");
        System.out.print(" -> ");
        int project_select = shortcut.changeformat(sc.nextLine());
        if (project_select == 1) {
            // project taking method
            // with ref of id
            project(id);

        }

        String sql2 = "insert into seaker_data(id, name, mobile_no, mail, age, skills, address, experienced, project, date) values( ?,?, ?,?,?,?,?,?,?, current_date())";
        try {
            pst = jdbc.con.prepareStatement(sql2);

            pst.setInt(1, id);
            pst.setString(2, name);
            pst.setLong(3, phonenumber);
            pst.setString(4, mail);
            pst.setInt(5, age);
            pst.setString(6, skills);
            pst.setString(7, address);
            // pst.setString(6, );
            if (experience_selection == 1) {
                pst.setString(8, "yes");
            } else {
                pst.setString(8, "no");
            }

            if (project_select == 1) {
                pst.setString(9, "yes");
            } else {
                pst.setString(9, "no");
            }
           
           return pst.executeUpdate();
           
        }
        
        catch (Exception e) {
            System.out.println(e);
            System.out.println(e.getMessage());
            e.getStackTrace();
            System.exit(0);
            return 0;
        }
        // job_seaker.seekerInfo(id);
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
         if (num>0) {
            System.out.println("-----------------------------------");
            System.out.println("Your id -> "+ id);
           System.out.println("Create password -> ");
           String password=sc.nextLine();
           
           try{
               String sqlquery="insert into js_acc values(?,?,?)";
               pst=jdbc.con.prepareStatement(sqlquery);
               pst.setInt(1, id);
               pst.setString(2, password);
               pst.setString(3, "seaker");
               pst.executeUpdate();
               
               System.out.println("-------------------------------------");
               System.out.println("Please remeber your information");
               System.out.println("Your id is -> "+id);
               System.out.println("your password is -> "+password);
               
            }catch(Exception e){
                System.out.println(e);
            }
            System.out.println("--------------------------------------") ;         
             System.out.println("YOU ARE PROFILE CREATED SUSCESSFULLY");
             System.out.println("--------------------------------------");
            
            job_seaker.seekerInfo(id);
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
                rs=st.executeQuery("select pass from js_acc where id = "+id+" and role='seaker' ");
                int tempnum2=0;
                while (rs.next()) {
                    String temp_pass = rs.getString("pass");
                    if(temp_pass.equals(password)){
                        tempnum2++;
                    }
                    // int temp_id = rs.getInt("id");
                    // System.out.println(id) ;
                    // System.out.println(password);
                    // if(temp_id==id){
                    //     tempnum2++;
                    // }
                }

                if(tempnum2==0){
                   System.out.println("Your id and password is not found");
                   System.out.println("may be your password is incorrect");
                   System.out.println("or your id does'nt exist");
                   acc();
                }else if(tempnum2==1){
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
            shortcut.updating("email", email, id, "seaker_data");

        }else if(updateSelection==5){
            System.out.println("Enter your age ");
            int age = shortcut.changeformat(sc.nextLine());
            shortcut.updating("age", String.valueOf(age), id, "seaker_data");


            // shortcut.updating("age", String.valueOf(shortcut.changeformat(sc.nextLine())), id, "seaker_data");


        }else if(updateSelection==6){
           
        }
        else if(updateSelection==7){
            seekerInfo(id);
        }
        else{
            System.out.println("Invalid selection");
            updateYourSelf(id);
        }
    }


}
