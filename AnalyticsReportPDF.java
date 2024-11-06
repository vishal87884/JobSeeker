import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.awt.Desktop;

public class AnalyticsReportPDF {
    private static final String FILE_NAME = "JobAndSeekerAnalyticsReport.pdf";

    public static void main(String[] args) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE_NAME));
            document.open();

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jobportal", "root", "9589");

            // Add main heading
            Paragraph mainHeading = new Paragraph("Job and Seeker Analytics Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLUE));
            mainHeading.setAlignment(Element.ALIGN_CENTER);
            document.add(mainHeading);
            document.add(new Paragraph(" "));

            // Add summary section
            addSummarySection(document, connection);

            // Add detailed job information
            addJobDetails(document, connection);

            // Add detailed job seeker information
            addJobSeekerDetails(document, connection);

            document.close();
            connection.close();

            System.out.println("PDF created successfully!");

            // Open the PDF automatically
            File pdfFile = new File(FILE_NAME);
            if (pdfFile.exists() && Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(pdfFile);
            } else {
                System.out.println("The file was created but cannot be opened automatically on this system.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addSummarySection(Document document, Connection connection) throws Exception {
        Paragraph summaryHeading = new Paragraph("Summary", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK));
        document.add(summaryHeading);
        document.add(new Paragraph(" "));

        Statement stmt = connection.createStatement();
        ResultSet jobCountResult = stmt.executeQuery("SELECT COUNT(*) AS employeeCount FROM jobs");
        int employeeCount = jobCountResult.next() ? jobCountResult.getInt("employeeCount") : 0;

        ResultSet seekerCountResult = stmt.executeQuery("SELECT COUNT(*) AS seekerCount FROM seaker_data");
        int seekerCount = seekerCountResult.next() ? seekerCountResult.getInt("seekerCount") : 0;

        document.add(new Paragraph("Total Employees in Company: " + employeeCount));
        document.add(new Paragraph("Total Job Seekers in Application: " + seekerCount));
        document.add(new Paragraph(" "));

        jobCountResult.close();
        seekerCountResult.close();
        stmt.close();
    }

    private static void addJobDetails(Document document, Connection connection) throws Exception {
        Paragraph detailsHeading = new Paragraph("Employee (Job Posting) Details", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK));
        document.add(detailsHeading);
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.addCell("S.No");
        table.addCell("Company");
        table.addCell("Post");
        table.addCell("Location");
        table.addCell("Available Posts");

        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT sno, company, post, location, availablepost FROM jobs");

        while (resultSet.next()) {
            table.addCell(String.valueOf(resultSet.getInt("sno")));
            table.addCell(resultSet.getString("company"));
            table.addCell(resultSet.getString("post"));
            table.addCell(resultSet.getString("location"));
            table.addCell(String.valueOf(resultSet.getInt("availablepost")));
        }

        document.add(table);
        document.add(new Paragraph("\n"));
        resultSet.close();
        stmt.close();
    }

    private static void addJobSeekerDetails(Document document, Connection connection) throws Exception {
        Paragraph seekerHeading = new Paragraph("Job Seeker Details", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK));
        document.add(seekerHeading);
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.addCell("ID");
        table.addCell("Name");
        table.addCell("Mobile No.");
        table.addCell("Email");
        table.addCell("Skills");

        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT id, name, mobile_no, mail, skills FROM seaker_data");

        while (resultSet.next()) {
            table.addCell(String.valueOf(resultSet.getInt("id")));
            table.addCell(resultSet.getString("name"));
            table.addCell(String.valueOf(resultSet.getLong("mobile_no")));
            table.addCell(resultSet.getString("mail"));
            table.addCell(resultSet.getString("skills"));
        }

        document.add(table);
        resultSet.close();
        stmt.close();
    }
}
