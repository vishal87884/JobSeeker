import java.io.*;
import java.sql.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.util.Scanner;  
import java.awt.Desktop;
import java.io.File;


public class Ana {

    public static void generateAnalyticsReport(int s) {
        String fileName = "AnalyticsReport.txt"; 

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jobportal", "root", "9589");

            // Total Employers Count and Names
            String employerQuery = "SELECT employeer_name FROM employeer_admin";
            Statement stmt = con.createStatement();
            ResultSet rs1 = stmt.executeQuery(employerQuery);

            writer.println("..........Analytics Report..........");
            writer.println("Employers: \n");
            int totalEmployers = 0;
            while (rs1.next()) {
                String employerName = rs1.getString("employeer_name");
                writer.println(" - " + employerName);
                totalEmployers++;
            }
            writer.println("Total Employers: " + totalEmployers + "\n");

            // Total Jobseekers Count and Names
            String jobseekerQuery = "SELECT Jobseeker_name FROM jobseeker_admin";
            ResultSet rs2 = stmt.executeQuery(jobseekerQuery);

            writer.println("Jobseekers: \n");
            int totalJobseekers = 0;
            while (rs2.next()) {
                String jobseekerName = rs2.getString("Jobseeker_name");
                writer.println(" - " + jobseekerName);
                totalJobseekers++;
            }
            writer.println("Total Jobseekers: " + totalJobseekers + "\n");

            // Total Users and Jobs Counts
            writer.println("Total Users: " + (totalEmployers + totalJobseekers));
            String jobCountQuery = "SELECT COUNT(*) AS job_count FROM job";
            ResultSet rs4 = stmt.executeQuery(jobCountQuery);
            rs4.next();
            int totalJobs = rs4.getInt("job_count");
            writer.println("Total Jobs: " + totalJobs);

            // Close resources
            rs1.close();
            rs2.close();
            rs4.close();
            stmt.close();
            con.close();

            System.out.println("Report generated and saved as " + fileName);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        convertToPDF(fileName, "AnalyticsReport.pdf");  
    }

    public static void convertToPDF(String textFile, String pdfFile) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();

            BufferedReader br = new BufferedReader(new FileReader(textFile));
            String line;
            while ((line = br.readLine()) != null) {
                document.add(new Paragraph(line));
            }
            br.close();
            System.out.println("PDF generated successfully as " + pdfFile);

            // User prompt for opening PDF
            Scanner scanner = new Scanner(System.in);
            System.out.print("Do you want to see the report as PDF? (yes/no): ");
            String response = scanner.nextLine();

            if (response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y")) {
                File pdf = new File(pdfFile);
                if (pdf.exists()) {
                    String command = "rundll32 url.dll,FileProtocolHandler " + pdf.getAbsolutePath();
                    try {
                        Runtime.getRuntime().exec(command);
                    } catch (IOException e) {
                        System.out.println("Error opening PDF: " + e.getMessage());
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error generating PDF: " + e.getMessage());
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
    }

    public static void main(String[] args) {
        generateAnalyticsReport(0); 
    }
}
