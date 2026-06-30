
package za.ac.tut.ui; 
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.awt.BorderLayout; 
import java.awt.Color; 
import java.awt.FlowLayout; 
import java.awt.Font; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.io.BufferedReader; 
//import java.io.BufferedWriter; 
import java.io.File; 
import java.io.FileNotFoundException; 
import java.io.FileReader; 
//import java.io.FileWriter; 
import java.io.IOException; 
import java.util.logging.Level; 
import java.util.logging.Logger; 
import javax.swing.JFileChooser; 
import javax.swing.JFrame; 
import javax.swing.JLabel; 
import javax.swing.JMenu; 
import javax.swing.JMenuBar; 
import javax.swing.JMenuItem; 
import javax.swing.JPanel; 
import javax.swing.JScrollPane; 
import javax.swing.JTextArea; 
import javax.swing.border.BevelBorder; 
import javax.swing.border.LineBorder; 
import javax.swing.border.TitledBorder; 
import za.ac.tut.encryption.MessageEncryptor; 
import za.ac.tut.message.Message; 


public class SecureMessagesFrame extends JFrame { 
private JMenuBar menuBar; 
private JMenu fileMenu; 
private JMenuItem openFileMenuItem; 
    private JMenuItem encryptFileMenuItem; 
    private JMenuItem saveEncryptedFileMenuItem; 
    private JMenuItem clearFileMenuItem; 
    private JMenuItem exitFileMenuItem; 
 
     
    private JPanel headingPnl; 
    private JPanel plainTextPnl; 
    private JPanel encryptedTextPnl; 
    private JPanel mainPnl; 
 
    
    private JLabel headingLbl; 
 
     
    private JTextArea plainMsgTxtArea; 
    private JTextArea encryptedMsgTxtArea; 
 
    
    private JScrollPane scrollablePlainMsgTxtArea; 
    private JScrollPane scrollableEncryptedMsgTxtArea; 
 
    public SecureMessagesFrame() { 
         
        setTitle("Secure Messages"); 
        setSize(50, 100); 
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
 
         
        menuBar = new JMenuBar(); 
 
         
        fileMenu = new JMenu("File"); 
 
         
        openFileMenuItem = new JMenuItem("Open file..."); 
        openFileMenuItem.addActionListener(new OpenFileMenuItemListener()); 
 
        encryptFileMenuItem = new JMenuItem("Encrypt message..."); 
        encryptFileMenuItem.addActionListener(new EncryptFileMenuItemListener()); 
 
        saveEncryptedFileMenuItem = new JMenuItem("Save encrypted message..."); 
        saveEncryptedFileMenuItem.addActionListener(new 
SaveEncryptedFileMenuItemListener()); 
 
        clearFileMenuItem = new JMenuItem("Clear"); 
        clearFileMenuItem.addActionListener(new ClearFileMenuItemListener()); 
 
        exitFileMenuItem = new JMenuItem("Exit"); 
        exitFileMenuItem.addActionListener(new ExitFileMenuItemListener()); 
 
         
        fileMenu.add(openFileMenuItem); 
        fileMenu.add(encryptFileMenuItem); 
        fileMenu.add(saveEncryptedFileMenuItem); 
        fileMenu.addSeparator(); 
        fileMenu.add(clearFileMenuItem); 
        fileMenu.add(exitFileMenuItem); 
 
         
        menuBar.add(fileMenu); 
 
         
        headingPnl = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
 
        plainTextPnl = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
        plainTextPnl.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1), 
"Plain message")); 
 
        encryptedTextPnl = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        encryptedTextPnl.setBorder(new TitledBorder(new 
LineBorder(Color.BLACK, 1), "Encrypted message")); 
 
        mainPnl = new JPanel(new BorderLayout()); 
 
         
        headingLbl = new JLabel("Message Encryptor"); 
        headingLbl.setForeground(Color.BLUE); 
        headingLbl.setFont(new Font("SERIF", Font.BOLD + Font.ITALIC, 30)); 
        headingLbl.setBorder(new BevelBorder(BevelBorder.RAISED)); 
 
         
        plainMsgTxtArea = new JTextArea(10, 30); 
        plainMsgTxtArea.setEditable(false); 
 
        encryptedMsgTxtArea = new JTextArea(10, 30); 
        encryptedMsgTxtArea.setEditable(false); 
 
         
        scrollablePlainMsgTxtArea = 
         new JScrollPane(plainMsgTxtArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); 
        scrollableEncryptedMsgTxtArea = new JScrollPane(encryptedMsgTxtArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); 
 
         
        headingPnl.add(headingLbl); 
        plainTextPnl.add(scrollablePlainMsgTxtArea); 
        encryptedTextPnl.add(scrollableEncryptedMsgTxtArea); 
 
        mainPnl.add(headingPnl, BorderLayout.NORTH); 
        mainPnl.add(plainTextPnl, BorderLayout.WEST); 
        mainPnl.add(encryptedTextPnl, BorderLayout.EAST); 
 
         
        setJMenuBar(menuBar); 
 
         
        add(mainPnl); 
 
         
        pack(); 
 
          
        setVisible(true); 
    } 
 
    private class OpenFileMenuItemListener implements ActionListener { 
 
        @Override 
        public void actionPerformed(ActionEvent ae) { 
             
 
            JFileChooser path; 
            File openFile; 
            BufferedReader read; 
            int value; 
            String data, plainMsg = ""; 
 
            path = new JFileChooser(); 
 
            value = path.showOpenDialog(SecureMessagesFrame.this); 
 
            if (value == JFileChooser.APPROVE_OPTION) { 
 
                try { 
                    openFile = path.getSelectedFile(); 
                    read = new BufferedReader(new FileReader(openFile)); 
 
                    while ((data = read.readLine()) != null) { 
 
                        plainMsg = plainMsg + data + "\n"; 
                    } 
 
                    read.close(); 
 
                    plainMsgTxtArea.setText(plainMsg); 
 
                } catch (FileNotFoundException ex) { 
                    
Logger.getLogger(SecureMessagesFrame.class.getName()).log(Level.SEVERE, 
null, ex); 
                } catch (IOException ex) { 
                    
Logger.getLogger(SecureMessagesFrame.class.getName()).log(Level.SEVERE, 
null, ex); 
                } 
            } 
 
        } 
 
    } 
 
    private class EncryptFileMenuItemListener implements ActionListener { 
 
        @Override 
        public void actionPerformed(ActionEvent ae) { 
           
 
            String plainTxtMsg = plainMsgTxtArea.getText(); 
 
            Message myMsg = new Message(plainTxtMsg); 
 
            MessageEncryptor myEncryptor = new MessageEncryptor(); 
 
            Message encryptMsg = myEncryptor.encrypt(myMsg); 
 
            String displayEncryptedMsg = String.valueOf(encryptMsg); 
 
            encryptedMsgTxtArea.setText(displayEncryptedMsg); 
        } 
 
    } 
 
   
 
     private class SaveEncryptedFileMenuItemListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent ae) {
        String encryptedTxtMsg = encryptedMsgTxtArea.getText();
        saveToDatabase(encryptedTxtMsg);
    }

    private void saveToDatabase(String encryptedMsg) {
        String url = "jdbc:derby://localhost:1527/Message;create=true"; // Adjust path if needed
        String sql = "INSERT INTO Messages(encrypted_text, timestamp) VALUES(?, CURRENT_TIMESTAMP)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, encryptedMsg);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            
            Logger.getLogger(SecureMessagesFrame.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}


 
 
    private class ClearFileMenuItemListener implements ActionListener { 
 
        @Override 
        public void actionPerformed(ActionEvent ae) { 
 
            encryptedMsgTxtArea.setText(""); 
            plainMsgTxtArea.setText(""); 
        } 
 
    } 
 
    private class ExitFileMenuItemListener implements ActionListener { 
 
        @Override 
        public void actionPerformed(ActionEvent ae) { 
             
            System.exit(0); 
        } 
 
    } 
} 
 