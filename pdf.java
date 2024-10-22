import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.text.SimpleDateFormat; // Add this line
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class pdf {

    private static Connection con;
    // static List<String> userCapNos = Registeration.userCapNos;
    static Scanner sc = new Scanner(System.in);

    static {
        
    }

    /**
    //  * @param eventName
    //  * @throws Exception
     */
    public static void pdfGenrator(String eventName) throws Exception {
        String q = "select * from participants WHERE eventName= ?";
        PreparedStatement statement = con.prepareStatement(q);

        statement.setString(1, eventName);
        ResultSet rs = statement.executeQuery();

        Document pdfDoc = new Document();

        PdfWriter.getInstance(pdfDoc, new FileOutputStream(eventName + "_participants.pdf"));
        pdfDoc.open();

        // ** Event Name Header Add Karna **
        Paragraph header = new Paragraph(eventName.toUpperCase(),
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK));
        header.setAlignment(Element.ALIGN_CENTER); // Center-align the header
        pdfDoc.add(header);

        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
        Paragraph dateParagraph = new Paragraph("Date: " + dateStr,
                FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK));
        dateParagraph.setAlignment(Element.ALIGN_CENTER); // Center-align the date
        pdfDoc.add(dateParagraph);
        // ** Add a blank line for spacing **
        pdfDoc.add(new Paragraph(" "));

        // Table create karna
        PdfPTable pdfTable = new PdfPTable(rs.getMetaData().getColumnCount());

        // Table header add karna
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            pdfTable.addCell(new PdfPCell(new Phrase(rs.getMetaData().getColumnName(i))));
        }

        // Table ke rows add karna
        while (rs.next()) {
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                pdfTable.addCell(rs.getString(i));
            }
        }

        // PDF document mein table add karna
        pdfDoc.add(pdfTable);
        pdfDoc.close();

        System.out.println("____________________________________________________________");
        System.out.println("PDF file successfully created for the event: " + eventName);
        System.out.println("____________________________________________________________");
        System.out.print("Would you like to continue managing events? (yes/no): ");
        go();
        // userCapNos = Registeration.userCapNos;
        // DeleteUserDefineEntries(userCapNos);
    }

    public static void go() throws Exception {
        // Ask user if they want to exit or continue

        String continueChoice = sc.nextLine();
        if (continueChoice.equalsIgnoreCase("no")) {
            System.out.println("\nExiting the manager interface... Thank you for using the system!");
            System.out.println("____________________________________________________________");
           
            // Exit the loop
        } else if (continueChoice.equalsIgnoreCase("yes")) {
            System.out.println("\nTaking you back to the event management options...");
           
        } else {
            System.out.print("Invalid input! Please enter 'yes' or 'no': ");
            go();
        }
    }
    // public static void DeleteUserDefineEntries(List<String> userCapNos) throws
    // SQLException {

    // for (String cap : userCapNos) {
    // String deleteQuery = "DELETE FROM participants WHERE capNo = ?";
    // PreparedStatement st = con.prepareStatement(deleteQuery);
    // st.setString(1, cap);
    // st.executeUpdate();
    // }
    // System.out.println("participant entries deleted successfully.");
    // System.out.println("-: Thanks For Coming :-");
    // }
}
