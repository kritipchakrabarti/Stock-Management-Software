package com.company;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class purchase {
    JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton SUBMITButton;
    private JComboBox comboBoxProduct;
    public String combo_msg_product;
    private JComboBox comboBoxUnit;
    private JButton clearButton;
    public String combo_msg_unit;


    public purchase()
    {
        AutoCompleteDecorator.decorate(comboBoxUnit);
        AutoCompleteDecorator.decorate(comboBoxProduct);

        Connection connection= null;
        try
        {
            connection = DriverManager.getConnection("jdbc:sqlite:Products.db");
            String query = "SELECT Product_Name FROM goods";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ArrayList<String> names = new ArrayList<String>();
            while (resultSet.next())
            {
                names.add(resultSet.getString("Product_Name"));
            }
            comboBoxProduct.setModel(new DefaultComboBoxModel(names.toArray()));


            String queryUnit = "SELECT Unit FROM goods";
            Statement statement2 = connection.createStatement();
            ResultSet resultSetUnit = statement2.executeQuery(queryUnit);
            ArrayList<String> namesUnit = new ArrayList<String>();
            while (resultSetUnit.next())
            {
                namesUnit.add(resultSetUnit.getString("Unit"));
            }
            comboBoxUnit.setModel(new DefaultComboBoxModel(namesUnit.toArray()));
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

            SUBMITButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Connection connection= null;
                try {
                    connection = DriverManager.getConnection("jdbc:sqlite:Products.db");

                    String p_name = combo_msg_product;
                    String unit = combo_msg_unit;
                    double qty = Double.valueOf(textField2.getText());
                    String cp = textField3.getText();
                    String mp = textField4.getText();

                    String queryselect1 = "SELECT COUNT(Product_Name) FROM goods WHERE LOWER(Product_Name) = LOWER('" + p_name + "') AND LOWER(Unit) = LOWER('" + unit + "')";
                    Statement statement7 = connection.createStatement();
                    ResultSet resultSet7 = statement7.executeQuery(queryselect1);
                    resultSet7.next();
                    int count = resultSet7.getInt(1);
                    resultSet7 = null;
                    if (count == 0)
                        JOptionPane.showMessageDialog(null, "Product Not Registered");
                    else {

                        String queryselect = "SELECT Quantity FROM goods WHERE LOWER(Product_Name) = LOWER('" + p_name + "') AND LOWER(Unit) = LOWER('" + unit + "')";
                        Statement statement3 = connection.createStatement();
                        ResultSet resultSet = statement3.executeQuery(queryselect);

                        double qtyi = 0;
                        if (resultSet.next())
                            qtyi = resultSet.getDouble("Quantity");

                        String queryinsert = "UPDATE goods SET Quantity = '" + (qty + qtyi) + "', Cost_Price = '" + cp + "', Market_Price = '" + mp + "' WHERE LOWER(Product_Name) = LOWER('" + p_name + "') AND LOWER(Unit) = LOWER('" + unit + "')";
                        Statement statement4 = connection.createStatement();
                        statement4.execute(queryinsert);
                        connection.close();
                        JOptionPane.showMessageDialog(null, "You Successfully Purchased the new Item");
                    }
                }
                catch (Exception e1)
                {
                    JOptionPane.showMessageDialog(null, "Invalid Data");
                    e1.printStackTrace();
                }

            }
        });
        comboBoxUnit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox jComboBox= ((JComboBox)e.getSource());
                combo_msg_unit=(String) jComboBox.getSelectedItem();
            }
        });
        comboBoxProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox jComboBox= ((JComboBox)e.getSource());
                combo_msg_product=(String) jComboBox.getSelectedItem();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField4.setText("");
                textField3.setText("");
                textField2.setText("");
                comboBoxProduct.getEditor().setItem("");
                comboBoxUnit.getEditor().setItem("");
            }
        });
    }
}
