import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.Scanner;

public class ResumeUtils {
   public static   String URL = "jdbc:mysql://localhost:3306/jobportal?useSSL=false";
     public static  String username = "root"; 
     public  static String password = "735403"; 

   
    public static void uploadResume() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Resume to Upload");
        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Files", "pdf"));

        int userSelection = fileChooser.showOpenDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String insertSql = "INSERT INTO pdf_files (email, resume_pdf) VALUES (?, ?)";

            try (Connection connection = DriverManager.getConnection(URL,username,password);
                 PreparedStatement pstmt = connection.prepareStatement(insertSql)) {

                InputStream inputStream = new FileInputStream(selectedFile);
                Scanner sc = new Scanner(System.in);
                System.out.print("Enter your email: ");
                String email = sc.nextLine();

                pstmt.setString(1, email);
                pstmt.setBlob(2, inputStream);                  
                pstmt.executeUpdate();
                System.out.println("Resume uploaded successfully to the database.");

                // Save to PC folder
                File saveFile = new File("C:\\Users\\Hp\\OneDrive\\Desktop\\project resume\\" + selectedFile.getName());
                Files.copy(new FileInputStream(selectedFile), saveFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Resume saved to PC at: " + saveFile.getAbsolutePath());

            } catch (Exception e) {
                System.out.println("Error during resume upload: " + e.getMessage());
            }
        } else {
            System.out.println("No file selected. Upload canceled.");
        }
    }
}