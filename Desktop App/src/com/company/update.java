package com.company;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class update {
    private JTextField textField3;
    private JTextField textField5;
    private JTextField textField4;
    private JButton buttonUpdate;
    private JTextField textField8;
    private JTextField textField7;
    private JTextField textField6;
    private JButton buttonCheck;
    private JComboBox comboBox;
    public String combo_msg;

    JPanel panel1;
    private JComboBox comboBoxName;
    private JButton clearButton;
    public String combo_msg_name;


    public update() {
        AutoCompleteDecorator.decorate(comboBox);
        AutoCompleteDecorator.decorate(comboBoxName);
        Connection connection= null;
        try
        {
            connection = DriverManager.getConnection("jdbc:sqlite:Products.db");
            String query="SELECT Product_Name FROM goods";
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(query);

            ArrayList<String> names=new ArrayList<String>();
            while(resultSet.next())
            {
                names.add(resultSet.getString("Product_Name"));
            }
            comboBoxName.setModel(new DefaultComboBoxModel(names.toArray()));


            String queryUnit = "SELECT Unit FROM goods";
            Statement statement2 = connection.createStatement();
            ResultSet resultSetUnit = statement2.executeQuery(queryUnit);
            ArrayList<String> namesUnit = new ArrayList<String>();
            while (resultSetUnit.next())
            {
                namesUnit.add(resultSetUnit.getString("Unit"));
            }
            comboBox.setModel(new DefaultComboBoxModel(namesUnit.toArray()));
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        buttonCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String p_name=combo_msg_name;
                String unit=combo_msg;

                try
                {
                    Connection connection=DriverManager.getConnection("jdbc:sqlite:Products.db");
                    String queryselect1 = "SELECT COUNT(Product_Name) FROM goods WHERE LOWER(Product_Name) = LOWER('" + p_name + "') AND LOWER(Unit) = LOWER('" + unit + "')";
                    Statement statement7 = connection.createStatement();
                    ResultSet resultSet7 = statement7.executeQuery(queryselect1);
                    resultSet7.next();
                    int count = resultSet7.getInt(1);
                    resultSet7 = null;
                    if (count == 0)
                        JOptionPane.showMessageDialog(null, "Product Not Registered");
                    else {
                    String query="SELECT * FROM goods WHERE LOWER(Product_Name) = LOWER('"+p_name+"') AND LOWER(Unit) = LOWER('"+unit+"')";
                    Statement statement=connection.createStatement();
                    ResultSet resultSet=statement.executeQuery(query);
                    textField3.setText(resultSet.getString("Selling_Price"));
                    textField4.setText(resultSet.getString("Cost_Price"));
                    textField5.setText(resultSet.getString("Market_Price"));
                    textField6.setText(resultSet.getString("Company"));
                    textField7.setText(resultSet.getString("Current_Distributor"));
                    textField8.setText(resultSet.getString("Quantity"));
                    connection.close();
                }}
                catch (SQLException e1)
                {

                    JOptionPane.showMessageDialog(null, "Not Found");
                    e1.printStackTrace();
                }
            }
        });

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox jComboBox= ((JComboBox)e.getSource());
                combo_msg=(String) jComboBox.getSelectedItem();
            }
        });

        comboBoxName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox jComboBox= ((JComboBox)e.getSource());
                combo_msg_name=(String) jComboBox.getSelectedItem();
            }
        });
        buttonUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection connection= null;
                try {
                    connection = DriverManager.getConnection("jdbc:sqlite:Products.db");
                    Statement statement2 = connection.createStatement();

                    String p_name = combo_msg_name;
                    String unit = combo_msg;
                    String sp = textField3.getText();
                    String cp = textField4.getText();
                    String mp = textField5.getText();
                    String c_name = textField6.getText();
                    String c_dist = textField7.getText();
                    String qty = textField8.getText();

                    String queryselect1 = "SELECT COUNT(Product_Name) FROM goods WHERE LOWER(Product_Name) = LOWER('" + p_name + "') AND LOWER(Unit) = LOWER('" + unit + "')";
                    Statement statement7 = connection.createStatement();
                    ResultSet resultSet7 = statement7.executeQuery(queryselect1);
                    resultSet7.next();
                    int count = resultSet7.getInt(1);
                    resultSet7 = null;
                    if (count == 0)
                        JOptionPane.showMessageDialog(null, "Product Not Registered");
                    else {

                        String queryinsert = "UPDATE goods SET Quantity = " + (qty) + " , Cost_Price = " + cp + " , Market_Price = " + mp + ", Selling_Price = " + sp + ", Company = '" + c_name + "' ,Current_Distributor = '" + c_dist + "' WHERE LOWER(Product_Name) = LOWER('" + p_name + "') AND LOWER(Unit) = LOWER('" + unit + "')";
                        statement2.execute(queryinsert);
                        connection.close();
                        JOptionPane.showMessageDialog(null, "Product Updated");
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
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField4.setText("");
                textField5.setText("");
                textField6.setText("");
                textField7.setText("");
                textField8.setText("");
                comboBoxName.getEditor().setItem("");
                comboBox.getEditor().setItem("");
            }
        });
    }
}
