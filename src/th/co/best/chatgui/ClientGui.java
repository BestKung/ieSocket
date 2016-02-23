/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.best.chatgui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import th.co.best.chatgui.ManageFile;
import static th.co.best.chatgui.ServerGui.message;
import static th.co.best.chatgui.ServerGui.resived;
import static th.co.best.chatgui.ServerGui.send;

/**
 *
 * @author BestKung
 */
public class ClientGui extends javax.swing.JFrame {

    static Socket socket = null;
    static DataOutputStream send = null;
    static BufferedReader resived = null;
    static String messsage = "";
    static DataInputStream inputFile = null;
    static String tmpMessage = "";
    static StyledDocument doc = null;
    static SimpleAttributeSet style = null;

    /**
     * Creates new form ClientGui
     */
    public ClientGui() {
        initComponents();
        setTitle("Chat Client!!");
        doc = txtShow.getStyledDocument();
        style = new SimpleAttributeSet();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        txtSend = new javax.swing.JTextField();
        btnSend = new javax.swing.JButton();
        btnImage = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtShow = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtSend.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSendKeyPressed(evt);
            }
        });

        btnSend.setText("Send");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });
        btnSend.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSendKeyPressed(evt);
            }
        });

        btnImage.setText("Image");
        btnImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImageActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(txtShow);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtSend, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnImage)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSend, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(668, 507));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        PrintWriter printWriter = null;
        send = null;
        try {
            send = new DataOutputStream(socket.getOutputStream());
            printWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex) {
            Logger.getLogger(ServerGui.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sendMessage = txtSend.getText().trim();
        try {
            StyleConstants.setBackground(style, Color.decode("#fff176"));
            StyleConstants.setBold(style, true);
            doc.insertString(doc.getLength(), "\n" + sendMessage, style);
            printWriter.println(sendMessage);
        } catch (BadLocationException ex) {
            Logger.getLogger(ServerGui.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtSend.setText("");
    }//GEN-LAST:event_btnSendActionPerformed

    private void btnImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImageActionPerformed

        PrintWriter printWriter;
        int value = jFileChooser1.showOpenDialog(null);
        File getFile = jFileChooser1.getSelectedFile();
        String pathFile = getFile.getAbsolutePath();
        DataOutputStream dataOutputStream;
        if (jFileChooser1.APPROVE_OPTION == value) {
            try {
                printWriter = new PrintWriter(socket.getOutputStream(), true);
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                File file = new File(pathFile);
                printWriter.println("file&&" + file.getName() + "&&" + file.length());
                new socketchatie.ClientGui().sendFile(file, dataOutputStream);
            } catch (IOException ex) {
                Logger.getLogger(socketchatie.ClientGui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnImageActionPerformed

    private void btnSendKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSendKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnSendKeyPressed

    private void txtSendKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSendKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            PrintWriter printWriter = null;
            send = null;
            try {
                printWriter = new PrintWriter(socket.getOutputStream(), true);
                send = new DataOutputStream(socket.getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(ServerGui.class.getName()).log(Level.SEVERE, null, ex);
            }
            String sendMessage = txtSend.getText().trim();
            try {
                StyleConstants.setBackground(style, Color.decode("#fff176"));
                StyleConstants.setBold(style, true);
                doc.insertString(doc.getLength(), "\n" + sendMessage, style);
                printWriter.println(sendMessage);
            } catch (BadLocationException ex) {
                Logger.getLogger(ServerGui.class.getName()).log(Level.SEVERE, null, ex);
            }
            txtSend.setText("");
        }
    }//GEN-LAST:event_txtSendKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException, BadLocationException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientGui().setVisible(true);
            }
        });
        socket = new Socket("192.168.1.130", 1111);
        resived = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        inputFile = new DataInputStream(socket.getInputStream());
        while (true) {
            message = resived.readLine();
            if (message.contains("file&&")) {
                String spt_file[] = message.split("@");
                String fileName = spt_file[1];
                File file = new File("F:\\" + fileName);
                System.out.println(new ManageFile().reseivedFile(file, inputFile, Long.parseLong(spt_file[2])));
                if ((fileName.substring(fileName.length() - 3, fileName.length()).equalsIgnoreCase("jpg")) || (fileName.substring(fileName.length() - 3, fileName.length()).equalsIgnoreCase("png"))) {
                    FromDialogShowImage dialogShowImage = new FromDialogShowImage(fileName);
                    dialogShowImage.setVisible(true);
                    StyleConstants.setBackground(style, Color.decode("#80deea"));
                    StyleConstants.setBold(style, true);
                    doc.insertString(doc.getLength(), "\n Server : ได้รับรูป ", style);
                } else {
                    StyleConstants.setBackground(style, Color.decode("#80deea"));
                    StyleConstants.setBold(style, true);
                    doc.insertString(doc.getLength(), "\n Server : ได้รับไฟล์ ", style);
                }
            } else {
                message = "Server : " + message;
                StyleConstants.setBackground(style, Color.decode("#80deea"));
                StyleConstants.setBold(style, true);
                doc.insertString(doc.getLength(), "\n Server : " + message, style);
            }

        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImage;
    private javax.swing.JButton btnSend;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtSend;
    private static javax.swing.JTextPane txtShow;
    // End of variables declaration//GEN-END:variables
}
