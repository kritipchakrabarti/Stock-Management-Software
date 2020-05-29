package com.company;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class Search {
    JPanel panel1;
    private JButton buttonCheck;
    private JComboBox comboBoxName;
    public static String combo_msg_name;
    private JTable table1;
    private JComboBox comboBoxLname;
    public static String combo_msg_lname;


    public Search() {

        Connection connection2 = null;
        try {
            connection2 = DriverManager.getConnection("jdbc:sqlite:Bills.db");

            String queryname1 = "SELECT fname FROM temp";
            Statement statement5 = connection2.createStatement();
            ResultSet resultSet5 = statement5.executeQuery(queryname1);
            ArrayList<String> names5 = new ArrayList<String>();
            while (resultSet5.next()) {
                names5.add(resultSet5.getString("fname"));
            }
            comboBoxName.setModel(new DefaultComboBoxModel(names5.toArray()));

            String queryname2 = "SELECT lname FROM temp";
            Statement statement6 = connection2.createStatement();
            ResultSet resultSet6 = statement6.executeQuery(queryname2);
            ArrayList<String> names6 = new ArrayList<String>();
            while (resultSet6.next()) {
                names6.add(resultSet6.getString("lname"));
            }
            comboBoxLname.setModel(new DefaultComboBoxModel(names6.toArray()));
            connection2.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        buttonCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cus_fname=combo_msg_name;
                String cus_lname=combo_msg_lname;


                try
                {
                    Connection connection = DriverManager.getConnection("jdbc:sqlite:Bills.db");
                    String queryselect1 = "SELECT COUNT(Sellid) FROM temp WHERE LOWER(fname) = LOWER('" + cus_fname + "') AND LOWER(lname) = LOWER('" + cus_lname + "')";
                    Statement statement7 = connection.createStatement();
                    ResultSet resultSet7 = statement7.executeQuery(queryselect1);
                    resultSet7.next();
                    int count = resultSet7.getInt(1);
                    resultSet7.close();
                    connection.close();
                    statement7.close();
                    if (count == 0)
                        JOptionPane.showMessageDialog(null, "Not Registered");
                    else {

                        connection=DriverManager.getConnection("jdbc:sqlite:Bills.db");
                        String query="SELECT * FROM temp WHERE LOWER(fname) = LOWER('" + cus_fname + "') AND LOWER(lname) = LOWER('" + cus_lname + "') ORDER BY Sellid DESC";
                        Statement statement=connection.createStatement();
                        ResultSet resultSet=statement.executeQuery(query);
                        table1.setModel(DbUtils.resultSetToTableModel(resultSet));
                        connection.close();
                        resultSet.close();
                        statement.close();

                    }
                }
                catch (SQLException e1)
                {
                    //No Such Column
                    JOptionPane.showMessageDialog(null, "INVALID DATA");
                    e1.printStackTrace();
                }


            }
        });
        comboBoxName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox jComboBox= ((JComboBox)e.getSource());
                combo_msg_name=(String) jComboBox.getSelectedItem();
            }
        });
        comboBoxLname.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox jComboBox= ((JComboBox)e.getSource());
                combo_msg_lname=(String) jComboBox.getSelectedItem();
            }
        });
    }
}
