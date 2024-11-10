  
    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.Statement;
    import java.util.Scanner;
    
    public class Admin {
        static Admin admin = new Admin();
        static Scanner sc = new Scanner(System.in);
        static PreparedStatement pst;
    
        public static void loginAdmin() {
            System.out.print("Enter Admin ID: ");
            String id = sc.nextLine();
            if (id.matches("0")) {
                Main.main(null);
            }
            System.out.print("Enter Admin Password: ");
            String password = sc.nextLine();
            if (password.matches("0")) {
                Main.main(null);
            }
            if (validateLogin(id, password)) {
                System.out.println("Login successful!");
                admin.adminWork();
            } else {
                System.out.println("Invalid ID or Password. Please try again.");
                System.out.println("0 -> Back");
                loginAdmin();
            }
        }
    
        private static boolean validateLogin(String id, String password) {
            try {
                // Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jobportal", "root", "735403")) {
                String sql = "SELECT * FROM js_acc WHERE id = ? AND pass = ? AND role = ?";
                pst = jdbc.con.prepareStatement(sql);
                pst.setString(1, id);
                pst.setString(2, password);
                pst.setString(3, "admin");
    
                ResultSet rs = pst.executeQuery();
                return rs.next();
            } catch (Exception e) {
                System.out.println("Error while connecting to the database: " + e.getMessage());
                return false;
            }
        }
    
        public static void adminWork() {
            System.out.println("----------------------------------");
            System.out.println("1. Monitor the Platform");
            System.out.println("2. Manage User Accounts");
            System.out.println("3. Generate Analytics Report");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            int choice = shortcut.changeformat(sc.nextLine());
    
            switch (choice) {
                case 1 -> monitoring();
                case 2 -> manageUser();
                case 3 -> AnalyticsReportPDF.main(null);
                case 4 -> {
                    System.out.println("Logged out.");
                    Main.main(null);
                }
                default -> {
                    System.out.println("Invalid selection, please re-select.");
                    adminWork();
                }
            }
        }
    
        public static void monitoring() {
            // Placeholder for platform monitoring logic
            System.out.println("Monitoring the platform...");
            adminWork();
        }
    
        public static void manageUser() {
            System.out.println("\nManage User Accounts:");
            System.out.println("1. Add User");
            System.out.println("2. View All Users");
            System.out.println("3. Delete user/employee");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = shortcut.changeformat(sc.nextLine());
    
            switch (choice) {
                case 1 -> addUser();
                case 2 -> viewUsers();
                case 3 -> block_delete();
                case 4 -> adminWork();
                default -> {
                    System.out.println("Invalid selection.");
                    manageUser();
                }
            }
        }
    
        private static void addUser() {
            System.out.println("1. Add new Employee");
            System.out.println("2. Add new Admin");
            System.out.println("3. Back");
            int selection = shortcut.changeformat(sc.nextLine());
    
            switch (selection) {
                case 1 -> addEmployee();
                case 2 -> addAdmin();
                case 3 -> manageUser();
                default -> {
                    System.out.println("Invalid selection.");
                    addUser();
                }
            }
        }
    
        private static void addAdmin() {
            int id = shortcut.generateradomnumber(4);
            System.out.println("Your ID is -> " + id);
            int result = takeDetail(id, "admin");
            if (result == 999) {
                return;
            } else if (result == 0) {
                System.out.println("Something went wrong.");
            } else {
                System.out.print("Create password -> ");
                String password = sc.nextLine();
                try {
                    String sqlquery = "INSERT INTO js_acc VALUES(?,?,?)";
                    pst = jdbc.con.prepareStatement(sqlquery);
                    pst.setInt(1, id);
                    pst.setString(2, password);
                    pst.setString(3, "admin");
                    int tempdt = pst.executeUpdate();
    
                    if (tempdt == 1) {
                        System.out.println("Admin created successfully. Remember your information:");
                        System.out.println("Your ID is -> " + id);
                        System.out.println("Your password is -> " + password);
                    } else {
                        System.out.println("Technical error in creating new admin.");
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            manageUser();
        }
    
        public static int takeDetail(int id, String whom) {
            System.out.println("Enter your name -> ");
            String name = sc.nextLine();
            String gmail = shortcut.validMailTaking();
            if ("Cancel form".equals(gmail)) {
                return 999;
            }
            System.out.println("Enter your Mobile number -> ");
            long phonenumber = shortcut.phonenumbertaking();
    
            String company_mail = (name + ".e" + id + "@jobportal.org").replace(" ", "");
    
            try {
                pst = jdbc.con.prepareStatement(
                        "INSERT INTO jobportal.details (id, name, mobile_no, gmail, mail, role) VALUES (?,?,?,?,?,?)");
    
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
            System.out.println("Your ID is -> " + id);
            int result = admin.takeDetail(id, "employee");
            if (result == 999) {
                return;
            } else if (result == 0) {
                System.out.println("Something went wrong.");
            } else {
                System.out.print("Create password -> ");
                String password = sc.nextLine();
                try {
                    String sqlquery = "INSERT INTO js_acc VALUES(?,?,?,?)";
                    pst = jdbc.con.prepareStatement(sqlquery);
                    pst.setInt(1, id);
                    pst.setString(2, password);
                    pst.setString(3, "employee");
                    pst.setString(4, "ACTIVE");
                    int tempdt = pst.executeUpdate();
    
                    if (tempdt == 1) {
                        System.out.println("Employee registered successfully. Remember your information:");
                        System.out.println("Your ID is -> " + id);
                        System.out.println("Your password is -> " + password);
                    } else {
                        System.out.println("Technical error in creating new employee.");
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    
        public static void viewUsers() {
            System.out.println("1. View employee data");
            System.out.println("2. View job seeker data");
            System.out.println("3. View all data");
            System.out.println("4. Back");
            int selection = shortcut.changeformat(sc.nextLine());
    
            switch (selection) {
                case 1 -> {
                    viewEmployee();
                    block_delete();
                }
                case 2 -> {
                    viewJobseaker();
                    block_delete();
                }
                case 3 -> {
                    viewEmployee();
                    viewJobseaker();
                    block_delete();
                }
                case 4 -> manageUser();
                default -> {
                    System.out.println("Invalid selection.");
                    viewUsers();
                }
            }
        }
    
        public static void viewJobseaker() {
            try {
                Statement st = jdbc.con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM seaker_data s");
    
                System.out.println(
                        "--------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.printf("| %-8s | %-20s | %-10s | %-30s | %-5s | %-10s | %-6s | %-7s |%n",
                        "ID", "Name", "Mobile No.", "Mail", "Age", "Registered", "Exp", "Project");
    
                System.out.println(
                        "--------------------------------------------------------------------------------------------------------------------------------------------------------");
    
                while (rs.next()) {
                    System.out.printf("| %-8s | %-20s | %-10s | %-30s | %-5s | %-10s | %-6s | %-7s |%n",
                            rs.getInt("id"), rs.getString("name"), rs.getString("mobile_no"), rs.getString("mail"),
                            rs.getString("age"), rs.getDate("date"), rs.getString("experienced"),
                            rs.getString("Project"));
                }
                System.out.println(
                        "--------------------------------------------------------------------------------------------------------------------------------------------------------");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    
        public static void viewEmployee() {
            try {
                Statement st = jdbc.con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM details d WHERE role='employee'");
    
                System.out.println(
                        "--------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.printf("| %-8s | %-18s | %-10s | %-30s | %-35s | %-8s |%n", "ID", "Name", "Mobile No.",
                        "Gmail", "Mail", "Role");
    
                System.out.println(
                        "--------------------------------------------------------------------------------------------------------------------------------------------------------");
    
                while (rs.next()) {
                    System.out.printf("| %-8s | %-18s | %-10s | %-30s | %-35s | %-8s |%n", rs.getInt("id"),
                            rs.getString("name"), rs.getString("mobile_no"), rs.getString("gmail"),
                            rs.getString("mail"), rs.getString("role"));
                }
                System.out.println(
                        "--------------------------------------------------------------------------------------------------------------------------------------------------------");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    
        public static void block_delete() {
            System.out.println("\n1. Delete User");
            System.out.println("2. Block User");
            System.out.println("3. Back");
            System.out.print("Enter your choice: ");
            int choice = shortcut.changeformat(sc.nextLine());
    
            switch (choice) {
                case 1 -> deleteUser();
                case 2 -> blockUser();
                case 3 -> manageUser();
                default -> {
                    System.out.println("Invalid selection.");
                    block_delete();
                }
            }
        }
    
        public static void deleteUser() {
            System.out.print("Enter user ID to delete (or 0 to go back): ");
            int id = shortcut.changeformat(sc.nextLine());
            if (id == 0) {
                block_delete();
            }
            try {
                String sqlquery = "DELETE FROM js_acc WHERE id = ?";
                pst = jdbc.con.prepareStatement(sqlquery);
                pst.setInt(1, id);
                int result = pst.executeUpdate();
                if (result == 1) {
                    System.out.println("User deleted successfully.");
                } else {
                    System.out.println("User not found or unable to delete.");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            block_delete();
        }
    
        public static void blockUser() {
            System.out.print("Enter user ID to block (or 0 to go back): ");
            int id = shortcut.changeformat(sc.nextLine());
            if (id == 0) {
                block_delete();
            }
            try {
                String sqlquery = "UPDATE js_acc SET status='BLOCKED' WHERE id = ?";
                pst = jdbc.con.prepareStatement(sqlquery);
                pst.setInt(1, id);
                int result = pst.executeUpdate();
                if (result == 1) {
                    System.out.println("User blocked successfully.");
                } else {
                    System.out.println("User not found or unable to block.");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            block_delete();
        }
    }