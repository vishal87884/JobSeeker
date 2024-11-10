import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.*;
import java.sql.*;
import java.util.regex.Pattern;
import java.awt.Desktop;
import java.util.*;

public class ResumeGenerator {

    public static void generateResume(String name, String email, String phone, String address, String experience, String education, String projects, String skills, String imagePath) {
        Document document = new Document();
        String pdfFilePath = name + "_Resume.pdf";

        try {
            System.out.println("1");
            PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
            document.open();
            System.out.println("1");

            // Adding Title with formatting
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 30, Font.BOLD);
            Paragraph title = new Paragraph("Resume", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));
            System.out.println("2");
                            
            // Creating table for contact information
            PdfPTable contactTable = new PdfPTable(2);
            contactTable.setWidthPercentage(100);
            contactTable.setSpacingBefore(10f);
            contactTable.setSpacingAfter(10f);
            System.out.println("3");

            // Set custom column widths for better control
            float[] columnWidths = {1f, 2f}; // 1st column for labels and 2nd for content
            contactTable.setWidths(columnWidths);
            System.out.println("4");

            Font headerFont = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD);
            PdfPCell cell;
            System.out.println("5");

            // Heading for contact info
            cell = new PdfPCell(new Phrase("Contact Information", headerFont));
            cell.setColspan(2);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            contactTable.addCell(cell);
            System.out.println("6");

            // Table Content: Name, Email, Phone, Address
            PdfPCell cell1 = new PdfPCell(new Phrase("Name"));
            cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            contactTable.addCell(cell1);
            System.out.println("7");

            PdfPCell cell2 = new PdfPCell(new Phrase(name));
            cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
            contactTable.addCell(cell2);
            System.out.println("8");

            PdfPCell cell3 = new PdfPCell(new Phrase("Email"));
            cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
            contactTable.addCell(cell3);
            System.out.println("9");

            PdfPCell cell4 = new PdfPCell(new Phrase(email));
            cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
            contactTable.addCell(cell4);
            System.out.println("10");

            PdfPCell cell5 = new PdfPCell(new Phrase("Phone"));
            cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
            contactTable.addCell(cell5);
            System.out.println("11");

            PdfPCell cell6 = new PdfPCell(new Phrase(phone));
            cell6.setHorizontalAlignment(Element.ALIGN_LEFT);
            contactTable.addCell(cell6);
            System.out.println("12");

            PdfPCell cell7 = new PdfPCell(new Phrase("Address"));
            cell7.setHorizontalAlignment(Element.ALIGN_LEFT);
            contactTable.addCell(cell7);
            System.out.println("13");

            PdfPCell cell8 = new PdfPCell(new Phrase(address));
            cell8.setHorizontalAlignment(Element.ALIGN_LEFT);
            contactTable.addCell(cell8);
            System.out.println("14");

            // Creating a table with 2 columns: one for contact information and one for the image
            PdfPTable mainTable = new PdfPTable(2); // 2 columns for table and image
            mainTable.setWidthPercentage(100);
            System.out.println("15");

            // Left column for the contact info table
            PdfPCell contactCell = new PdfPCell(contactTable);
            contactCell.setBorder(Rectangle.NO_BORDER); // Remove border
            mainTable.addCell(contactCell);
            System.out.println("16");

            // Right column for the image (if exists)
            if (imagePath != null && !imagePath.isEmpty()) {
                try {
                    Image logo = Image.getInstance(imagePath);
                    logo.scaleToFit(100, 100);
                    logo.setAlignment(Element.ALIGN_RIGHT);
                    System.out.println("17");

                    PdfPCell imageCell = new PdfPCell(logo);
                    imageCell.setBorder(Rectangle.NO_BORDER);
                    imageCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    mainTable.addCell(imageCell);
                } catch (Exception e) {
                    System.out.println("Error loading image: " + e.getMessage());
                }
            }

            document.add(mainTable);
            System.out.println("18");

            // Adding other sections (Experience, Education, Projects, Skills)
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

    private static void retrieveAndGenerateResumeFromDB(int id) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jobportal", "root", "735403");
             PreparedStatement stmt = con.prepareStatement("SELECT name, mail AS email, mobile_no AS phone, address, experienced AS experience, skills, project FROM seaker_data WHERE id = ?")) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String phone = String.valueOf(rs.getLong("phone"));
                    String address = rs.getString("address");
                    String experience = rs.getString("experience");
                    String education = ""; // Add logic to retrieve education if needed
                    String projects = rs.getString("project");
                    String skills = rs.getString("skills");
                    String imagePath = ""; // Provide path if image handling is necessary

                    generateResume(name, email, phone, address, experience, education, projects, skills, imagePath);
                } else {
                    System.out.println("No record found for ID: " + id);
                }
            }

        } catch (Exception e) {
            System.out.println("Error retrieving data from database: " + e.getMessage());
        }
    }

    private static String[] fetchDataFromDatabase(int userId) {
        String[] resumeData = new String[8]; // [name, email, phone, address, experience, education, projects, skills]
    
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jobportal", "root", "735403")) {
    
            // Query for seeker data
            PreparedStatement psSeeker = con.prepareStatement(
                "SELECT name, mail, mobile_no, address, experienced, skills FROM seaker_data WHERE id = ?");
            psSeeker.setInt(1, userId);
            ResultSet rsSeeker = psSeeker.executeQuery();
    
            if (rsSeeker.next()) {
                resumeData[0] = rsSeeker.getString("name");
                resumeData[1] = rsSeeker.getString("mail");
                resumeData[2] = String.valueOf(rsSeeker.getLong("mobile_no"));
                resumeData[3] = rsSeeker.getString("address");
                resumeData[4] = rsSeeker.getString("experienced"); // Experience summary
                resumeData[7] = rsSeeker.getString("skills"); // Skills
            }
    
            // Query for projects
            PreparedStatement psProjects = con.prepareStatement(
                "SELECT pName, skills_used, summary FROM project WHERE id = ?");
            psProjects.setInt(1, userId);
            ResultSet rsProjects = psProjects.executeQuery();
            StringBuilder projects = new StringBuilder();
    
            while (rsProjects.next()) {
                projects.append("Project Name: ").append(rsProjects.getString("pName")).append("\n");
                projects.append("Skills Used: ").append(rsProjects.getString("skills_used")).append("\n");
                projects.append("Summary: ").append(rsProjects.getString("summary")).append("\n\n");
            }
            resumeData[6] = projects.toString();
    
            // Query for experience
            PreparedStatement psExperience = con.prepareStatement(
                "SELECT cName, jobTittle, starts, ends FROM experience WHERE id = ?");
            psExperience.setInt(1, userId);
            ResultSet rsExperience = psExperience.executeQuery();
            StringBuilder experience = new StringBuilder();
    
            while (rsExperience.next()) {
                experience.append("Company: ").append(rsExperience.getString("cName")).append("\n");
                experience.append("Job Title: ").append(rsExperience.getString("jobTittle")).append("\n");
                experience.append("Start Date: ").append(rsExperience.getDate("starts")).append("\n");
                experience.append("End Date: ").append(rsExperience.getDate("ends")).append("\n\n");
            }
            resumeData[4] = experience.toString();
    
            // Set default education if needed
            resumeData[5] = "Education information could be added here if available.";
    
        } catch (SQLException e) {
            System.out.println("Error fetching data from database: " + e.getMessage());
        }
        return resumeData;
    }
    


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
    
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        
        // Retrieve data from database
        String[] resumeData = fetchDataFromDatabase(userId);
        if (resumeData != null) {
            generateResume(
                resumeData[0], // name
                resumeData[1], // email
                resumeData[2], // phone
                resumeData[3], // address
                resumeData[4], // experience
                resumeData[5], // education
                resumeData[6], // projects
                resumeData[7], // skills
                null           // Optional image path
            );
        } else {
            System.out.println("User data not found.");
        }
    }
    
}
