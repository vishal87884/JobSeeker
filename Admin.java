import java.lang.Thread.State;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Admin {

    static Admin admin = new Admin();
    static Scanner sc = new Scanner(System.in);
    static PreparedStatement pst;

    public void loginAdmin() {

    }

    public void adminWork(int id) {

        System.out.println("----------------------------------");
        System.out.println("1. Monitor the Platform");
        System.out.println("2. Manage User Accounts");
        System.out.println("3. Generate Analytics Report");
        System.out.println("4. Logout");
        System.out.print("Enter your choice: ");
        int choice = shortcut.changeformat(sc.nextLine());

        if (choice == 1) {

        } else if (choice == 2) {
            manageUser();
        } else if (choice == 3) {

        } else if (choice == 4) {

        } else {
            System.out.println("Invalid selection please Re- select ");
            adminWork(id);
        }

    }

    public void monitoring() {

    }

    public void generateAnalyticReport() {

    }

    public void manageUser() {
        System.out.println("\nManage User Accounts:");
        System.out.println("1. Add User");
        System.out.println("3. View All Users");
        // System.out.println("4. View All Jobs");
        System.out.println("4. Delete user/employee");
        System.out.println("5. Back to Main Menu");
        System.out.print("Enter your choice: ");
        int choice = shortcut.changeformat(sc.nextLine());

        if (choice == 1) {
            System.out.println("1. Add new Employee");
            System.out.println("2. Add new Admin");
            System.out.println("3. Back ");
            int tempselection = shortcut.changeformat(sc.nextLine());

            if (tempselection == 1) {
                addEmployee();
            } else if (tempselection == 2) {
                int id = shortcut.generateradomnumber(4);
                System.out.println("Your id is -> " + id);
                int checking = takeDetail(id, "admin");
                if (checking == 999) {
                    return;
                } else if (checking == 0) {
                    System.out.println("Something went wrong");
                } else {
                    System.out.print("Create password -> ");
                    String password = sc.nextLine();

                    try {
                        String sqlquery = "insert into js_acc values(?,?,?)";
                        pst = jdbc.con.prepareStatement(sqlquery);
                        pst.setInt(1, id);
                        pst.setString(2, password);
                        pst.setString(3, "admin");
                        int tempdt = pst.executeUpdate();

                        // int tempdt = takeDetail(id, "employee");
                        if (tempdt == 1) {
                            System.out.println("-------------------------------------");
                            System.out.println("Please remeber your information");
                            System.out.println("Your id is -> " + id);
                            System.out.println("your password is -> " + password);

                        } else {
                            System.out.println("Technical error in reating new admin");
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            } else if (tempselection == 3) {
                manageUser();
            } else if(tempselection==4){
                block_delete();
            }else {
                System.out.println("Invalid selection ");
                manageUser();
            }
            // }else if(choice==2){

        } else if (choice == 3) {
            // viewJobseaker();System.out.println("---------------------------------------------------------------------------------");
            // viewEmployee();
            System.out.println("1. View employee data");
            System.out.println("2. View Job Seeker Data");
            System.out.println("3. View all data");
            System.out.println("");
            int tempselection = shortcut.changeformat(sc.nextLine());

            if (tempselection == 1) {
                viewEmployee();
                block_delete();
            } else if (tempselection == 2) {
                viewJobseaker();
                block_delete();
            } else if (tempselection == 3) {
                viewEmployee();
                viewJobseaker();
                block_delete();
            } else {
                System.out.println("Invalid selection");
            }
        } else if (choice == 4) {

        } else if (choice == 5) {

        } else {
            System.out.println("Invalid selection ");
            manageUser();
        }

    }

    public int takeDetail(int id, String whom) {
        System.out.println("Enter your name -> ");
        String name = sc.nextLine();

        // System.out.println("Enter your personal mail id -> ");
        String gmail = shortcut.validMailTaking();
        if ("Cancel form" == gmail) {
            return 999;
        }

        System.out.println("Enter your Mobile number -> ");
        long phonenumber = shortcut.phonenumbertaking();

        String company_mail = name + ".e" + id + "@jobportal.org";
        company_mail = company_mail.replace(" ", "");

        try {
            pst = jdbc.con.prepareStatement(
                    "INSERT INTO `jobportal`.`details` (`id`, `name`, `mobile_no`, `gmail`, `mail`,`role`) VALUES (?,?,?,?,?,?)");

            pst.setInt(1, id);
            pst.setString(2, name);
            pst.setLong(3, phonenumber);
            pst.setString(4, gmail);
            pst.setString(5, company_mail);
            pst.setString(6, whom);

            return pst.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
    }

    public static void addEmployee() {

        int id = shortcut.generateradomnumber(5);
        System.out.println("Your id is -> " + id);
        int lp = admin.takeDetail(id, "employee");
        if (lp == 999) {
            return;
        } else if (lp == 0) {
            System.out.println("Somethings went wrong ");
        } else
            System.out.print("Create password -> ");
        String password = sc.nextLine();

        try {
            String sqlquery = "insert into js_acc values(?,?,?)";
            pst = jdbc.con.prepareStatement(sqlquery);
            pst.setInt(1, id);
            pst.setString(2, password);
            pst.setString(3, "employee");
            int tempdt = pst.executeUpdate();

            // int tempdt = takeDetail(id, "employee");
            if (tempdt == 1) {
                System.out.println("------------------------------------");
                System.out.println("You are registered successfully");
                System.out.println("-------------------------------------");
                System.out.println("Please remeber your information");
                System.out.println("Your id is -> " + id);
                System.out.println("your password is -> " + password);

            } else {
                System.out.println("Technical error in reating new employee");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void viewJobseaker() {
        try {
            // String sql = "select * from seaker_data";
            // Statement st=jdbc.con.createStatement();
            // ResultSet rs = st.executeQuery(sql);
            // while (rs.next()==true) {

            // System.out.println("_______________________________________________________________");
            // // System.out.printf("|
            // %-60s|%n","-------------------------------------------------------------");
            // // System.out.printf("| %-60s |%n","Serial number -> "+rs.getString("sno"));

            // System.out.printf("| %-60s|%n",
            // "ID - "+ rs.getString("Id"));
            // System.out.printf("| %-60s |%n","Name -> "+rs.getString("name"));
            // // System.out.printf("| %-60s |%n","Salary -> "+rs.getString("salary"));
            // System.out.printf("| %-60s |%n","Mobile no. -> "+rs.getString("mobile_no"));
            // System.out.printf("| %-60s |%n","Mail -> "+rs.getString("mail"));
            // System.out.printf("| %-60s |%n","Age -> "+rs.getString("age"));
            // System.out.printf("| %-60s |%n","Skills -> "+rs.getString("skills"));
            // System.out.printf("| %-60s |%n","Address -> "+rs.getString("address"));
            // System.out.printf("| %-60s |%n","Experiened ->
            // "+rs.getString("experienced"));
            // System.out.printf("| %-60s |%n","Work in Project ->
            // "+rs.getString("project"));
            // System.out.printf("| %-60s |%n","Registration date -> "+rs.getDate("date"));

            // //
            // System.out.println("-------------------------------------------------------------");
            // System.out.printf("|%-60s|%n","______________________________________________________________");
            // System.out.println();
            // }

            System.out.println("-----------------");

            // String query="select * from jobs"
            Statement st = jdbc.con.createStatement();
            ResultSet rs = st.executeQuery("select * from seaker_data s");

            System.out.println(
                    "--------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-8s | %-18s | %-10s | %-25s | %-25s | %-10s | %-6s | %-7s |%n",
                    "ID", "Name", "Mobile No.", "Mail", "Skills", "Registerd", "Exp", "Project");

            System.out.println(
                    "--------------------------------------------------------------------------------------------------------------------------------------------------------");

            while (rs.next()) {

                System.out.printf("| %-8s | %-18s | %-10s | %-25s | %-25s | %-10s | %-6s | %-7s |%n",
                        rs.getInt("id"), rs.getString("name"), rs.getString("mobile_no"), rs.getString("mail"),
                        rs.getString("skills"), rs.getDate("date"), rs.getString("experienced"),
                        rs.getString("Project"));

            }
            System.out.println(
                    "--------------------------------------------------------------------------------------------------------------------------------------------------------");

            System.out.println("");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void viewEmployee() {
        try {
            Statement st = jdbc.con.createStatement();
            ResultSet rs = st.executeQuery("Select * from details");

            System.out.println(
                    "--------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-8s | %-18s | %-10s | %-30s | %-30s |%n",
                    "ID", "Name", "Mobile No.", "Gmail", "Mail");

            System.out.println(
                    "--------------------------------------------------------------------------------------------------------------------------------------------------------");

            while (rs.next()) {

                System.out.printf("| %-8s | %-18s | %-10s | %-30s | %-30s |%n",
                        rs.getInt("id"), rs.getString("name"), rs.getString("mobile_no"), rs.getString("gmail"),
                        rs.getString("mail"));

            }
            System.out.println(
                    "--------------------------------------------------------------------------------------------------------------------------------------------------------");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void block_delete() {
        while (true) {
            System.out.print("Enter Id -> ");
            int id = shortcut.changeformat(sc.nextLine());
            String section = "";
            if (shortcut.checkdataexist(id, "id", "seaker_data")) {
                section = "seaker_data";
            } else if (shortcut.checkdataexist(id, "id", "details")) {
                section = "details";
            } else {
                System.out.println("Invalid id ");
                System.out.println("1. Re try");
                System.out.println("2. Back");
                int tempselection = shortcut.changeformat(sc.nextLine());

                if (tempselection == 1) {

                } else if (tempselection == 2) {
                    return;
                } else {
                    System.out.println("Invalid selection");
                }
            }

            if (section.equals("seaker_data") || section.equals("details")) {
                System.out.println("1. Block");
                System.out.println("2. Delete");
                System.out.println("3. Back");
                int tempselection = shortcut.changeformat(sc.nextLine());

                while (tempselection != 1 && tempselection != 2 && tempselection != 3) {
                    System.out.println("Please enter a valid selection");
                    tempselection = shortcut.changeformat(sc.nextLine());
                }
                if (tempselection == 3) {
                    return;
                }else {
                    if(section.equals("details")){
                        try {        
                                    Statement st=jdbc.con.createStatement();
                                    String sqlQuery = "select sno from jobs where createdBy = " + id;
                                        ResultSet rs = st.executeQuery(sqlQuery);
                                        while (rs.next()) {
                                            try{String query2 = "delete from application where job = " + rs.getInt("sno");
                                            st.executeUpdate(query2);}
                                            catch(Exception e){
                                            }
                                        }
                                        String sqlquery2="delete from jobs where createdBy = "+id;
                                                st.executeUpdate(sqlquery2);
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                    }else if(section.equals("seaker_data")){
                        try{
                            Statement st=jdbc.con.createStatement();
                            String query1 ="DELETE FROM application WHERE (id = " + id + ")";
                            st.executeUpdate(query1);
                        }catch(Exception e){
                            System.out.println(e);
                            System.out.println("error auto work 3238748");
                        }
                    }

                    if(tempselection==1){
                        try{
                            Statement st=jdbc.con.createStatement();
                        String sql = "UPDATE js_acc SET id_status = 'BLOCK' WHERE (id = " + id + ")";

                                    if (st.executeUpdate(sql) == 1) {
                                        System.out.println("Blocked Successfully");
                                    } else {
                                        System.out.println("Unable to block ");
                                    }
                            }catch(Exception e){
                                System.out.println(e);
                                System.out.println(" error 9869569569");
                            }
                    }else if(tempselection==2){
                        try{
                            Statement st=jdbc.con.createStatement();
                        String sql2 = "DELETE FROM " + section + " WHERE (id = " + id + ")";
                                      st.executeUpdate(sql2);
                                        String sql = "DELETE FROM js_acc WHERE (id = " + id + ")";
                                        st.executeUpdate(sql);
                        }catch(Exception e){
                            System.out.println(e);
                            System.out.println("u4875758475475");
                        }
                    }

                







                    //     try {
                    //     Statement st = jdbc.con.createStatement();
                        
                    //     // if (section.equals("seaker_data")) {
                    //     //     String sql3 = "DELETE FROM application WHERE (id = " + id + ")";
                    //     //     st.executeUpdate(sql3);

                    //     // } else if (section.equals("details")) {
                    //         String sqlQuery = "select sno from jobs where createdBy = " + id;
                    //         try {
                    //             ResultSet rs = st.executeQuery(sqlQuery);
                    //             while (rs.next()) {
                    //                 try{String query2 = "delete from application where job = " + rs.getInt("sno");
                    //                 st.executeUpdate(query2);}
                    //                 catch(Exception e){
                    //                 }
                    //             }
                    //         } catch (Exception e) {
                    //             System.out.println(e);
                    //         }
                    //         String sqlquery2="delete from jobs where createdBy = "+id;
                    //         st.executeUpdate(sqlquery2);

                    //         if (tempselection == 1) {
                    //             String sql = "UPDATE js_acc SET id_status = 'BLOCK' WHERE (id = " + id + ")";
                               
        
                    //             if (st.executeUpdate(sql) == 1) {
                    //                 System.out.println("Blocked Successfully");
                    //             } else {
                    //                 System.out.println("Unable to block ");
                    //             }
                    //         }else if(tempselection==2){
                    //             String sql2 = "DELETE FROM " + section + " WHERE (id = " + id + ")";
                    // st.executeUpdate(sql2);
                    //                 String sql = "DELETE FROM js_acc WHERE (id = " + id + ")";
                    //                 st.executeUpdate(sql);

                    //         }
                        
                    // } catch (Exception e) {
                    //     e.getStackTrace();
                    //     System.out.println(e.getMessage());
                    //     System.out.println(e);

                    // }
                    }                     
            }
        }

    }
}
