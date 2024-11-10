import java.util.Scanner;
// import java.sql.ResultSet;
import java.sql.Statement;

public class Main {

    static int id=0;
    static String password="";

    public static void mainwork(){
        Scanner scanner = new Scanner(System.in);
       
        while (true) {
            System.out.println("---------------------------------- ---");
            System.out.println("1 - Admin");
            System.out.println("2 - Employ");
            System.out.println("3 - Job Seeker");
            System.out.println("4 - Exit...");
            System.out.println("------------------------");
            // System.out.println("4 - Track application");
            // System.out.println("5 - Exit");
            System.out.print("Enter your choice: ");

                int choice = shortcut.changeformat(scanner.nextLine()) ;
                System.out.println("------------------------");


                    if(choice==1){
                        System.out.println("Admin selected.");

                        // Call Admin class/methods
                      Admin.loginAdmin();
                        // Admin admin=new Admin();
                     }
                    else if(choice==2){

                        System.out.println("Employ selected.");
                        // Employee em = new Employee();
                        // em.mainWorkEmployee();
                        // Employee.employeeLogin();

                        System.out.println("1 -> Sign Up");
                        System.out.println("2 -> Sign In");
                        System.out.println("3 -> Back");
                        int selection = shortcut.changeformat(scanner.nextLine());
                        if(selection==1){
                            Admin.addEmployee();
                        }else if(selection==2){
                            Employee.employeeLogin();
                        }else if(selection==3){
                            mainwork();
                        }else{
                            System.out.println("--------------------------");
                            System.out.println("Invalid selection");
                        }
                    }

                    else if(choice==3){

                        System.out.println("Job Seeker selected.");
                        // Call Job Seeker class/methods
                        // job_seaker.seaker();
                        job_seaker.acc();
                        // job_seaker.takingdetails();
                    }
                    else if(choice==4){

                        System.out.println("Exiting...");
                        scanner.close(); // Close the scanner before exiting
                        System.exit(0);
                    }
                   else{

                       System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                       break;
                    }
                
        }
    }
    public static void main(String[] args) {
          
        startHook();
        mainwork();
        
    }

    public static void startHook(){
        jdbc.connection();

        try{
            Statement st = jdbc.con.createStatement();

            String forPost = "DELETE from jobs where Date < (CURDATE() - INTERVAL 7 DAY)";
            st.executeUpdate(forPost);

           
            // String q  = "select id from seaker_data WHERE date < (CURDATE() - INTERVAL 7 DAY)";
            // ResultSet rs= st.executeQuery(q);

            // while(rs.next()){
            //     int tempid = rs.getInt(id);
            //     String q1;
                
            //     q1= "DELETE from experience where id = "+tempid;
            //     st.executeUpdate(q1);
                
            //     q1= "DELETE from project where id = "+tempid;
            //     st.executeUpdate(q1);
                
            //     q1= "DELETE from application where id = "+tempid;
            //     st.executeUpdate(q1);
                
            //     q1= "DELETE from js_acc where id = "+tempid;
            //     st.executeUpdate(q1);
                
            //     q1= "DELETE from seaker_data where id = "+tempid;
            //     st.executeUpdate(q1);
            // }

        }catch(Exception e){
           System.out.println("Start up hook does'nt start");
           System.out.println(e);
        }
    }
}
