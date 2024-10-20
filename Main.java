import java.util.Scanner;
import java.sql.Statement;

public class Main {

    static int id=0;
    static String password="";

    public static void main(String[] args) {

        startHook();
        // System.out.println(jdbc.con);
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("-------------------------------------");
            System.out.println("1 - Admin");
            System.out.println("2 - Employ");
            System.out.println("3 - Job Seeker");
            System.out.println("4 - Exit...");
            // System.out.println("4 - Track application");
            // System.out.println("5 - Exit");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt(); 
               

                switch (choice) {
                    case 1:
                        System.out.println("Admin selected.");
                        // Call Admin class/methods
                        job_seaker.posts();
                        break;
                    case 2:
                        System.out.println("Employ selected.");
                       new Employee();
                        break;
                    case 3:
                        System.out.println("Job Seeker selected.");
                        // Call Job Seeker class/methods
                        // job_seaker.seaker();
                       job_seaker.acc();
                        // job_seaker.takingdetails();
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        scanner.close(); // Close the scanner before exiting
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                        break;
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Clear the invalid input
            }
        }
    }
}
