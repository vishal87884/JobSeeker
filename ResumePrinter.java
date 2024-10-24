import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class ResumePrinter {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your email to print resume: ");
        String email = sc.nextLine();

        
        String URL = "jdbc:mysql://localhost:3306/jobportal?useSSL=false";
        String username = "root"; 
        String password = "9538"; 

        try (Connection connection = DriverManager.getConnection(URL, username, password)) {
            printResumeToConsole(connection, email); 
        } catch (Exception e) {
            System.out.println("Error during SQL execution: " + e);
        } finally {
            sc.close();
        }
    }

    public static void printResumeToConsole(Connection connection, String email) {
        String query = "SELECT resume_pdf FROM pdf_files WHERE email = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    InputStream pdfInputStream = rs.getBlob("resume_pdf").getBinaryStream();
                    PdfReader reader = new PdfReader(pdfInputStream);
                    System.out.println("Resume Content for email: " + email);

                    for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                        String pageText = PdfTextExtractor.getTextFromPage(reader, i);
                        System.out.println(pageText);
                    }

                    reader.close();
                } else {
                    System.out.println("No resume found for the specified email.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error during resume retrieval: " + e);
        }
    }
}
