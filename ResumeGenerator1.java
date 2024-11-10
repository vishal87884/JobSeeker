import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.*;
import java.sql.*;
import java.awt.Desktop;
import java.util.*;

public class ResumeGenerator1 {

    public static void generateResume(String name, String email, String phone, String address, String experience, String education, String projects, String skills) {
        Document document = new Document();
        String pdfFilePath = name + "_Resume.pdf";

        try {
            PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
            document.open();

            // Adding Title with formatting
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 30, Font.BOLD);
            Paragraph title = new Paragraph("Resume", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));
            // Creating table for contact information
            PdfPTable contactTable = new PdfPTable(2);
            contactTable.setWidthPercentage(100);
            contactTable.setSpacingBefore(10f);
            contactTable.setSpacingAfter(10f);

            float[] columnWidths = {1f, 2f};
            contactTable.setWidths(columnWidths);

            Font headerFont = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD);
            PdfPCell cell;

            cell = new PdfPCell(new Phrase("Contact Information", headerFont));
            cell.setColspan(2);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            contactTable.addCell(cell);

            contactTable.addCell(new PdfPCell(new Phrase("Name")));
            contactTable.addCell(new PdfPCell(new Phrase(name)));

            contactTable.addCell(new PdfPCell(new Phrase("Email")));
            contactTable.addCell(new PdfPCell(new Phrase(email)));

            contactTable.addCell(new PdfPCell(new Phrase("Phone")));
            contactTable.addCell(new PdfPCell(new Phrase(phone)));

            contactTable.addCell(new PdfPCell(new Phrase("Address")));
            contactTable.addCell(new PdfPCell(new Phrase(address)));

            document.add(contactTable);

            Font sectionFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            document.add(new Paragraph("Experience", sectionFont));
            document.add(new Paragraph(experience));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Education", sectionFont));
            document.add(new Paragraph(education));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Projects", sectionFont));
            document.add(new Paragraph(projects));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Skills", sectionFont));
            document.add(new Paragraph(skills));
            document.add(new Paragraph("\n"));

            System.out.println("Resume PDF created successfully!");

            saveResumeToDatabase(name, email, phone, address, experience, education, projects, skills, pdfFilePath);

            openPDF(pdfFilePath);

        } catch (Exception e) {
            System.out.println("Error generating resume: " + e.getMessage());
        } finally {
            document.close();
        }
    }

    public static void fetchAndGenerateResume(int seekerId) {
        String name = "";
        String email = "";
        String phone = "";
        String address = "";
        String experience = "";
        String education = "Not Specified"; // Education details can be added if there is a table for it
        String projects = "";
        String skills = "";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jobportal", "root", "735403")) {
            // Fetch seeker details
            PreparedStatement seekerStmt = con.prepareStatement("SELECT * FROM seaker_data WHERE id = ?");
            seekerStmt.setInt(1, seekerId);
            ResultSet seekerRs = seekerStmt.executeQuery();
            if (seekerRs.next()) {
                name = seekerRs.getString("name");
                email = seekerRs.getString("mail");
                phone = seekerRs.getString("mobile_no");
                address = seekerRs.getString("address");
                skills = seekerRs.getString("skills");
                experience = seekerRs.getString("experienced");
            }

            // Fetch projects
            PreparedStatement projectStmt = con.prepareStatement("SELECT pName, skills_used, summary FROM project WHERE id = ?");
            projectStmt.setInt(1, seekerId);
            ResultSet projectRs = projectStmt.executeQuery();
            StringBuilder projectBuilder = new StringBuilder();
            while (projectRs.next()) {
                projectBuilder.append("Project Name: ").append(projectRs.getString("pName"))
                        .append("\nSkills Used: ").append(projectRs.getString("skills_used"))
                        .append("\nSummary: ").append(projectRs.getString("summary")).append("\n\n");
            }
            projects = projectBuilder.toString();

            // Fetch experience
            PreparedStatement experienceStmt = con.prepareStatement("SELECT cName, jobTittle, starts, ends FROM experience WHERE id = ?");
            experienceStmt.setInt(1, seekerId);
            ResultSet experienceRs = experienceStmt.executeQuery();
            StringBuilder experienceBuilder = new StringBuilder();
            while (experienceRs.next()) {
                experienceBuilder.append("Company: ").append(experienceRs.getString("cName"))
                        .append("\nJob Title: ").append(experienceRs.getString("jobTittle"))
                        .append("\nDuration: ").append(experienceRs.getDate("starts")).append(" to ").append(experienceRs.getDate("ends")).append("\n\n");
            }
            experience = experienceBuilder.toString();

            // Generate the resume with fetched data
            generateResume(name, email, phone, address, experience, education, projects, skills);

        } catch (Exception e) {
            System.out.println("Error fetching data from database: " + e.getMessage());
        }
    }

    private static void saveResumeToDatabase(String name, String email, String phone, String address, String experience, String education, String projects, String skills, String pdfFilePath) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jobportal", "root", "735403");
             PreparedStatement pstmt = con.prepareStatement("INSERT INTO resume (name, email, phone, address, experience, education, projects, skills, resume_pdf) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setString(4, address);
            pstmt.setString(5, experience);
            pstmt.setString(6, education);
            pstmt.setString(7, projects);
            pstmt.setString(8, skills);

            try (FileInputStream fis = new FileInputStream(pdfFilePath)) {
                pstmt.setBinaryStream(9, fis, (int) new File(pdfFilePath).length());
                pstmt.executeUpdate();
                System.out.println("Resume saved in database successfully!");
            }

        } catch (Exception e) {
            System.out.println("Error saving resume to database: " + e.getMessage());
        }
    }

    private static void openPDF(String pdfFilePath) {
        try {
            File pdfFile = new File(pdfFilePath);
            if (pdfFile.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(pdfFile);
                    System.out.println("PDF opened in browser successfully!");
                } else {
                    System.out.println("Desktop is not supported. Cannot open PDF automatically.");
                }
            } else {
                System.out.println("PDF file does not exist.");
            }
        } catch (Exception e) {
            System.out.println("Error opening PDF: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Seeker ID: ");
        int seekerId = scanner.nextInt();

        fetchAndGenerateResume(seekerId);
    }
}
