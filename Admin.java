import java.sql.PreparedStatement;
import java.util.Scanner;

public class Admin {

    static Admin admin=new Admin();
    static Scanner sc=new Scanner(System.in);
    static PreparedStatement pst;
    
public void loginAdmin(){

}

public void adminWork(int id){

    System.out.println("----------------------------------");
            System.out.println("1. Monitor the Platform");
            System.out.println("2. Manage User Accounts");
            System.out.println("3. Generate Analytics Report");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
    int choice = shortcut.changeformat(sc.nextLine());

    if(choice ==1 ){

    }else if(choice==2){
        manageUser();
    }else if(choice==3){

    }else if(choice==4){

    }else{
        System.out.println("Invalid selection please Re- select ");
        adminWork(id);
    }

}

public void monitoring(){

}

public void generateAnalyticReport(){

}

public void manageUser(){
    System.out.println("\nManage User Accounts:");
    System.out.println("1. Add User");
    System.out.println("2. Delete User");
    System.out.println("3. View All Users");
    System.out.println("4. View All Jobs");
    System.out.println("5. Back to Main Menu");
    System.out.print("Enter your choice: ");
    int choice=shortcut.changeformat(sc.nextLine());

    if(choice==1){
        System.out.println("1. Add new Employee");
        System.out.println("2. Add new Admin");
        System.out.println("3. Back ");
        int tempselection = shortcut.changeformat(sc.nextLine());

        if(tempselection==1){
           addEmployee();
        }else if(tempselection==2){
            int id= shortcut.generateradomnumber(4);
            System.out.println("Your id is -> "+id);
            takeDetail(id,"admin");
            System.out.print("Create password -> ");
            String password = sc.nextLine();

            try{
                String sqlquery="insert into js_acc values(?,?,?)";
               pst=jdbc.con.prepareStatement(sqlquery);
               pst.setInt(1, id);
               pst.setString(2, password);
               pst.setString(3, "admin");
               int tempdt = pst.executeUpdate();
               
               //    int tempdt = takeDetail(id, "employee");
               if(tempdt==1){
                   System.out.println("-------------------------------------");
                   System.out.println("Please remeber your information");
                   System.out.println("Your id is -> "+id);
                   System.out.println("your password is -> "+password);

               }else{
                System.out.println("Technical error in reating new admin");
               }
               
            }catch(Exception e){
                System.out.println(e);
            }
            
        }else if(tempselection==3){manageUser();}
        else{
            System.out.println("Invalid selection ");
            manageUser();
        }
    }else if(choice==2){

    }else if(choice==3){

    }else if(choice==4){

    }else if(choice==5){

    }else{
        System.out.println("Invalid selection ");
        manageUser();
    }

}

public int takeDetail(int id,String whom){
    System.out.println("Enter your name -> ");
    String name = sc.nextLine();

    System.out.println("Enter your personal mail id -> ");
    String gmail=sc.nextLine();

    System.out.println("Enter your Mobile number -> ");
    long phonenumber=shortcut.phonenumbertaking();

    
    String company_mail=name+".e"+id+"@jobportal.org";
    company_mail=company_mail.replace(" ", "");

    try{
        pst=jdbc.con.prepareStatement("INSERT INTO `jobportal`.`details` (`id`, `name`, `mobile_no`, `gmail`, `mail`,`role`) VALUES (?,?,?,?,?,?)");

        pst.setInt(1, id);
        pst.setString(2, name);
        pst.setLong(3,phonenumber);
        pst.setString(4, gmail);
        pst.setString(5, company_mail);
        pst.setString(6, whom);

        return pst.executeUpdate();
        

    }catch(Exception e){
        System.out.println(e);
        return 0;
    }
}

public static void addEmployee(){

    int id= shortcut.generateradomnumber(5);
    System.out.println("Your id is -> "+id);
    admin.takeDetail(id,"employee");
    System.out.print("Create password -> ");
    String password = sc.nextLine();

    try{
        String sqlquery="insert into js_acc values(?,?,?)";
       pst=jdbc.con.prepareStatement(sqlquery);
       pst.setInt(1, id);
       pst.setString(2, password);
       pst.setString(3, "employee");
       int tempdt = pst.executeUpdate();
       
       //    int tempdt = takeDetail(id, "employee");
       if(tempdt==1){
            System.out.println("------------------------------------");
            System.out.println("You are registered successfully");
           System.out.println("-------------------------------------");
           System.out.println("Please remeber your information");
           System.out.println("Your id is -> "+id);
           System.out.println("your password is -> "+password);

       }else{
        System.out.println("Technical error in reating new employee");
       }
       
    }catch(Exception e){
        System.out.println(e);
    }
    
}

}
