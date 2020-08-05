/*
 * Created by JFormDesigner on Sun Aug 02 20:28:58 PDT 2020
 */

package com.company;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

/**
 * @author Diec Tin Toan
 */
public class StudentForm extends JPanel {
    DefaultTableModel tableModel;
    Connection con = null;
    PreparedStatement statement = null;


    public StudentForm() {

        initComponents();
        tableModel = (DefaultTableModel) table1.getModel();


    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Student");
        jFrame.setContentPane(new StudentForm().mainPanel);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.pack();
    }

    private void showButtonMouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void scrollPane1MouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    public void showData() {

        Statement statement = null;
        tableModel.setRowCount(0);
        List<StudentInfo> students = new ArrayList<>();


        try {

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "");
            statement = con.createStatement();

            String sql = "select * from student";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                StudentInfo student = new StudentInfo(resultSet.getString("fullname"), resultSet.getString("phonenum"),
                        resultSet.getString("email"), resultSet.getString("address"));

                students.add(student);
            }

        } catch (SQLException exception) {
            Logger.getLogger(StudentForm.class.getName()).log(Level.SEVERE, null, exception);
        } finally {
            if (con != null) {
                try {

                    con.close();
                } catch (SQLException exception) {
                    Logger.getLogger(StudentForm.class.getName()).log(Level.SEVERE, null, exception);
                }
            }
            if (statement != null) {
                try {

                    statement.close();
                } catch (SQLException exception) {
                    Logger.getLogger(StudentForm.class.getName()).log(Level.SEVERE, null, exception);
                }
            }
        }


        for (StudentInfo studentInfo : students) {

            tableModel.addRow(new Object[]{studentInfo.getFullName(), studentInfo.getPhoneNum(), studentInfo.getEmail(), studentInfo.getAddress()});
        }

    }

    private void Showbutton4MouseClicked(MouseEvent e) {
        // TODO add your code here
        showData();
    }

    private void Addbutton1MouseClicked(MouseEvent e) {
        // TODO add your code here
        Connection con = null;
        PreparedStatement statement = null;

        if (fullNametextField1.getText().trim().isEmpty() || phoneNumtextField2.getText().trim().isEmpty() ||
                EmailtextField3.getText().trim().isEmpty() || AddresstextField4.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Please input name to modiy the data");
        } else {


            try {

                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "");


                statement = con.prepareStatement("Select * from student where fullname = ?");

                statement.setString(1, fullNametextField1.getText());


                ResultSet rs = statement.executeQuery();


                if (rs.isBeforeFirst()) {          //res.isBeforeFirst() is true if the cursor

                    JOptionPane.showMessageDialog(null, "The name you are trying to enter already exists ");

                    fullNametextField1.setText("");
                    phoneNumtextField2.setText("");
                    EmailtextField3.setText("");
                    AddresstextField4.setText("");

                    fullNametextField1.requestFocus();

                    return;
                } else {

                    String sql = "insert into student values (?,?,?,?)";
                    statement = con.prepareStatement(sql);
                    statement.setString(1, fullNametextField1.getText());
                    statement.setString(2, phoneNumtextField2.getText());
                    statement.setString(3, EmailtextField3.getText());
                    statement.setString(4, AddresstextField4.getText());

                    statement.executeUpdate();
                    showData();
                }

            } catch (SQLException exception) {
                Logger.getLogger(StudentForm.class.getName()).log(Level.SEVERE, null, exception);
            } finally {
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException exception) {
                        Logger.getLogger(StudentForm.class.getName()).log(Level.SEVERE, null, exception);
                    }
                }
                if (con != null) {
                    try {

                        con.close();
                    } catch (SQLException exception) {
                        Logger.getLogger(StudentForm.class.getName()).log(Level.SEVERE, null, exception);
                    }
                }

            }
        }
    }

    private void Modifybutton3MouseClicked(MouseEvent e) throws SQLException {
        // TODO add your code here
        String fullName, phoneNum, email, address;
        Connection con = null;


        if (fullNametextField1.getText().trim().isEmpty() || phoneNumtextField2.getText().trim().isEmpty() ||
                EmailtextField3.getText().trim().isEmpty() || AddresstextField4.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Please input all the data");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "");

                fullName = fullNametextField1.getText();
                phoneNum = phoneNumtextField2.getText();
                email = EmailtextField3.getText();
                address = AddresstextField4.getText();


                statement = con.prepareStatement("update student set fullname=?,phonenum=?, email=?, address=? where fullname =?");

                statement.setString(1, fullName);
                statement.setString(2, phoneNum);
                statement.setString(3, email);
                statement.setString(4, address);
                statement.setString(5, fullName);

                statement.executeUpdate();

                JOptionPane.showMessageDialog(null, "Record edited");

                fullNametextField1.setText("");
                phoneNumtextField2.setText("");
                EmailtextField3.setText("");
                AddresstextField4.setText("");
                fullNametextField1.requestFocus();

            } catch (SQLException exception) {
                Logger.getLogger(StudentForm.class.getName()).log(Level.SEVERE, null, exception);
            } finally {
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException exception) {
                        Logger.getLogger(StudentForm.class.getName()).log(Level.SEVERE, null, exception);
                    }
                }
                if (con != null) {
                    try {

                        con.close();
                    } catch (SQLException exception) {
                        Logger.getLogger(StudentForm.class.getName()).log(Level.SEVERE, null, exception);
                    }
                }

            }
        }
    }

    private void Deletebutton2MouseClicked(MouseEvent e) throws SQLException {
        // TODO add your code here
        if (fullNametextField1.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Please input all the data");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "");

                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {

                    statement = con.prepareStatement("delete from student where fullname =?");

                    statement.setString(1, fullNametextField1.getText());

                }


                JOptionPane.showMessageDialog(null, "Record deleted");


                statement.executeUpdate();

                fullNametextField1.setText("");
                phoneNumtextField2.setText("");
                EmailtextField3.setText("");
                AddresstextField4.setText("");
                fullNametextField1.requestFocus();

            } catch (SQLException exception) {
                Logger.getLogger(StudentForm.class.getName()).log(Level.SEVERE, null, exception);
            } finally {
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException exception) {
                        Logger.getLogger(StudentForm.class.getName()).log(Level.SEVERE, null, exception);
                    }
                }
                if (con != null) {
                    try {

                        con.close();
                    } catch (SQLException exception) {
                        Logger.getLogger(StudentForm.class.getName()).log(Level.SEVERE, null, exception);
                    }
                }

            }
        }

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Diec Tin Toan
        mainPanel = new JPanel();
        label1 = new JLabel();
        fullNametextField1 = new JTextField();
        phoneNumtextField2 = new JTextField();
        EmailtextField3 = new JTextField();
        AddresstextField4 = new JTextField();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        Addbutton1 = new JButton();
        Deletebutton2 = new JButton();
        Modifybutton3 = new JButton();
        Showbutton4 = new JButton();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();

        //======== mainPanel ========
        {
            mainPanel.setBorder(new TitledBorder(null, "Student Form", TitledBorder.LEADING, TitledBorder.TOP,
                    new Font("Segoe UI", Font.BOLD, 12)));
            mainPanel.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(
                    new javax.swing.border.EmptyBorder(0, 0, 0, 0), "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn"
                    , javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BOTTOM
                    , new java.awt.Font("Dia\u006cog", java.awt.Font.BOLD, 12)
                    , java.awt.Color.red), mainPanel.getBorder()));
            mainPanel.addPropertyChangeListener(
                    new java.beans.PropertyChangeListener() {
                        @Override
                        public void propertyChange(java.beans.PropertyChangeEvent e
                        ) {
                            if ("\u0062ord\u0065r".equals(e.getPropertyName())) throw new RuntimeException()
                                    ;
                        }
                    });
            mainPanel.setLayout(null);

            //---- label1 ----
            label1.setText("Full Name");
            mainPanel.add(label1);
            label1.setBounds(new Rectangle(new Point(25, 40), label1.getPreferredSize()));
            mainPanel.add(fullNametextField1);
            fullNametextField1.setBounds(120, 35, 250, fullNametextField1.getPreferredSize().height);
            mainPanel.add(phoneNumtextField2);
            phoneNumtextField2.setBounds(125, 85, 250, 30);
            mainPanel.add(EmailtextField3);
            EmailtextField3.setBounds(125, 135, 250, 30);
            mainPanel.add(AddresstextField4);
            AddresstextField4.setBounds(125, 185, 250, 30);

            //---- label2 ----
            label2.setText("Phone Number");
            mainPanel.add(label2);
            label2.setBounds(20, 90, 100, 16);

            //---- label3 ----
            label3.setText("Email");
            mainPanel.add(label3);
            label3.setBounds(40, 140, 60, 16);

            //---- label4 ----
            label4.setText("Address");
            mainPanel.add(label4);
            label4.setBounds(35, 195, 65, 16);

            //---- Addbutton1 ----
            Addbutton1.setText("Add");
            Addbutton1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Addbutton1MouseClicked(e);
                }
            });
            mainPanel.add(Addbutton1);
            Addbutton1.setBounds(new Rectangle(new Point(15, 260), Addbutton1.getPreferredSize()));

            //---- Deletebutton2 ----
            Deletebutton2.setText("Delete");
            Deletebutton2.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        Deletebutton2MouseClicked(e);
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                }
            });
            mainPanel.add(Deletebutton2);
            Deletebutton2.setBounds(115, 260, 78, 30);

            //---- Modifybutton3 ----
            Modifybutton3.setText("Modify");
            Modifybutton3.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        Modifybutton3MouseClicked(e);
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                }
            });
            mainPanel.add(Modifybutton3);
            Modifybutton3.setBounds(210, 260, 78, 30);

            //---- Showbutton4 ----
            Showbutton4.setText("Show");
            Showbutton4.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Showbutton4MouseClicked(e);
                }
            });
            mainPanel.add(Showbutton4);
            Showbutton4.setBounds(310, 260, 78, 30);

            //======== scrollPane1 ========
            {

                //---- table1 ----
                table1.setBorder(LineBorder.createBlackLineBorder());
                table1.setModel(new DefaultTableModel(
                        new Object[][]{
                        },
                        new String[]{
                                "Full Name", "Phone Number", "Email", "Address"
                        }
                ));
                scrollPane1.setViewportView(table1);
            }
            mainPanel.add(scrollPane1);
            scrollPane1.setBounds(40, 315, 350, 160);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for (int i = 0; i < mainPanel.getComponentCount(); i++) {
                    Rectangle bounds = mainPanel.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = mainPanel.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                mainPanel.setMinimumSize(preferredSize);
                mainPanel.setPreferredSize(preferredSize);
            }
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Diec Tin Toan
    private JPanel mainPanel;
    private JLabel label1;
    private JTextField fullNametextField1;
    private JTextField phoneNumtextField2;
    private JTextField EmailtextField3;
    private JTextField AddresstextField4;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JButton Addbutton1;
    private JButton Deletebutton2;
    private JButton Modifybutton3;
    private JButton Showbutton4;
    private JScrollPane scrollPane1;
    private JTable table1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
