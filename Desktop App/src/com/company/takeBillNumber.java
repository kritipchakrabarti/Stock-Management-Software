package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class takeBillNumber {
    JPanel panel1;
    public static JFrame jFrameBILL;
    private JButton buttonCheck;
    private JComboBox comboBox;
    public static String combo_msg;
    private JLabel billNumber;
    public static String billnumber;

    public takeBillNumber() {

        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:Bills.db");
            String query = "SELECT Bill_no FROM temp";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            ArrayList<String> names = new ArrayList<String>();
            while (resultSet.next()) {
                names.add(resultSet.getString("Bill_no"));
            }
            comboBox.setModel(new DefaultComboBoxModel(names.toArray()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        buttonCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                billnumber=combo_msg;
                jFrameBILL=new JFrame("MODUS");
                jFrameBILL.setContentPane(new BILL().jPanel1);
                jFrameBILL.setVisible(true);
                jFrameBILL.setSize(600,600 );
                jFrameBILL.setLocationRelativeTo(null);
                jFrameBILL.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            }
        });
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox jComboBox= ((JComboBox)e.getSource());
                combo_msg=(String) jComboBox.getSelectedItem();
            }
        });
    }
}
