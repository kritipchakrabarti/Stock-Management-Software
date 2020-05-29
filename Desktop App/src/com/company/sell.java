package com.company;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class sell
{
    public static JFrame jFramePrint;
    JPanel panel1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton SUBMITButton;
    private JComboBox comboBoxProduct;
    public String combo_msg_product;
    private JComboBox comboBoxUnit;
    private JButton printButton;
    private JComboBox comboBox1;
    public String combo_msg_1;
    private JComboBox comboBox2;
    private JLabel labelName1;
    private JLabel labelName2;
    private JLabel ImageIcon;
    private JButton clearButton;
    public String combo_msg_2;
    public String combo_msg_unit;

    public sell()
    {
        AutoCompleteDecorator.decorate(comboBoxUnit);
        AutoCompleteDecorator.decorate(comboBoxProduct);
        AutoCompleteDecorator.decorate(comboBox1);
        AutoCompleteDecorator.decorate(comboBox2);


        Connection connection= null;
        Connection connection2=null;
        try
        {
            connection2 = DriverManager.getConnection("jdbc:sqlite:Bills.db");
            String queryCreate="CREATE TABLE IF NOT EXISTS temp(Sellid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Product_Name varchar(40), Unit varchar(10),Selling_Price NUMBER(5,2),Quantity Number(9,2), fname varchar(40), lname varchar(40), Bill_no varchar(20))";
            Statement statement1=connection2.createStatement();
            statement1.execute(queryCreate);
            connection2.close();
            statement1.close();

            connection2 = DriverManager.getConnection("jdbc:sqlite:Bills.db");
            String queryCount="SELECT COUNT(Product_Name) FROM temp";
            Statement statement9=connection2.createStatement();
            ResultSet resultSet9=statement9.executeQuery(queryCount);
            resultSet9.next();
            int count=resultSet9.getInt(1);
            connection2.close();
            resultSet9.close();
            statement9.close();

            if(count==0)
            {
                String queryinsert="INSERT INTO temp (Product_Name,Unit,Selling_Price,Quantity,fname,lname) VALUES( ' ', ' ', 0, 0, ' ', ' ')";
                connection2 = DriverManager.getConnection("jdbc:sqlite:Bills.db");
                Statement statement4=connection2.createStatement();
                statement4.execute(queryinsert);
                connection2.close();
                statement4.close();
            }


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
            statement.close();

            String queryUnit = "SELECT Unit FROM goods";
            Statement statement2 = connection.createStatement();
            ResultSet resultSetUnit = statement2.executeQuery(queryUnit);
            ArrayList<String> namesUnit = new ArrayList<String>();
            while (resultSetUnit.next())
            {
                namesUnit.add(resultSetUnit.getString("Unit"));
            }
            comboBoxUnit.setModel(new DefaultComboBoxModel(namesUnit.toArray()));
            statement2.close();
            connection.close();



            connection2 = DriverManager.getConnection("jdbc:sqlite:Bills.db");
            String queryname1 = "SELECT fname FROM temp";
            Statement statement5 = connection2.createStatement();
            ResultSet resultSet5 = statement5.executeQuery(queryname1);
            ArrayList<String> names5 = new ArrayList<String>();
            while (resultSet5.next())
            {
                names5.add(resultSet5.getString("fname"));
            }
            comboBox1.setModel(new DefaultComboBoxModel(names5.toArray()));

            String queryname2 = "SELECT lname FROM temp";
            Statement statement6 = connection2.createStatement();
            ResultSet resultSet6 = statement6.executeQuery(queryname2);
            ArrayList<String> names6 = new ArrayList<String>();
            while (resultSet6.next())
            {
                names6.add(resultSet6.getString("lname"));
            }
            comboBox2.setModel(new DefaultComboBoxModel(names6.toArray()));
            connection2.close();
        }

        catch (SQLException e) {
            e.printStackTrace();
        }


        SUBMITButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Connection connection= null;
                try {

                    String fname = combo_msg_1;
                    String lname = combo_msg_2;
                    String p_name = combo_msg_product;
                    String unit = combo_msg_unit;
                    double qty = Double.valueOf(textField2.getText());
                    String sp = textField3.getText();

                    connection = DriverManager.getConnection("jdbc:sqlite:Products.db");
                    String queryselect1 = "SELECT COUNT(Product_Name) FROM goods WHERE LOWER(Product_Name) = LOWER('" + p_name + "') AND LOWER(Unit) = LOWER('" + unit + "')";
                    Statement statement7 = connection.createStatement();
                    ResultSet resultSet7 = statement7.executeQuery(queryselect1);
                    resultSet7.next();
                    int count = resultSet7.getInt(1);
                    resultSet7.close();
                    statement7.close();
                    connection.close();

                    if (count == 0)
                        JOptionPane.showMessageDialog(null, "Product Not Registered");
                    else {
                        connection = DriverManager.getConnection("jdbc:sqlite:Products.db");
                        String queryselect = "SELECT Quantity FROM goods WHERE LOWER(Product_Name) = LOWER('" + p_name + "') AND LOWER(Unit) = LOWER('" + unit + "')";
                        Statement statement3 = connection.createStatement();
                        ResultSet resultSet0 = statement3.executeQuery(queryselect);
                        double qtyi = resultSet0.getDouble("Quantity");
                        resultSet0.close();
                        statement3.close();
                        connection.close();

                        double netqty = qtyi - qty;
                        if (netqty < 0) {
                            throw new SQLException();
                        } else {
                            connection = DriverManager.getConnection("jdbc:sqlite:Products.db");
                            Statement statement2 = connection.createStatement();
                            String queryUpdate = "UPDATE goods SET Quantity = '" + netqty + "' , Selling_Price = '" + sp + "' WHERE LOWER(Product_Name) = LOWER('" + p_name + "') AND LOWER(Unit) = LOWER('" + unit + "')";
                            statement2.execute(queryUpdate);
                            statement2.close();
                            connection.close();

                            String queryInsert = "INSERT INTO temp (Product_Name,Unit,Selling_Price,Quantity,fname,lname) VALUES( '" + p_name + "' , '" + unit + "' ," + sp + "," + qty + ", '" + fname + "', '" + lname + "')";
                            connection = DriverManager.getConnection("jdbc:sqlite:Bills.db");
                            Statement statement4 = connection.createStatement();
                            statement4.execute(queryInsert);
                            connection.close();
                            BRANCHES.insert++;
                            labelName1.setEnabled(false);
                            comboBox1.setEnabled(false);
                            labelName2.setEnabled(false);
                            comboBox2.setEnabled(false);
                            JOptionPane.showMessageDialog(null, "Added Successfully");

                        }
                    }
                }
                catch (SQLException e1)
                {
                    JOptionPane.showMessageDialog(null, "You are selling more quantity than available or Your data is Invalid");
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
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFramePrint=new JFrame("MODUS");
                jFramePrint.setContentPane(new Print().jPanel1);
                jFramePrint.setVisible(true);
                jFramePrint.setSize(600,600 );
                jFramePrint.setLocationRelativeTo(null);
                BRANCHES.jFrameSell.setVisible(false);
                Print.k=1;
                jFramePrint.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                labelName1.setEnabled(true);comboBox1.setEnabled(true);
                labelName2.setEnabled(true);comboBox2.setEnabled(true);
            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JComboBox jComboBox= ((JComboBox)e.getSource());
                combo_msg_1=(String) jComboBox.getSelectedItem();
            }
        });

        comboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox jComboBox= ((JComboBox)e.getSource());
                combo_msg_2=(String) jComboBox.getSelectedItem();
            }
        });


                clearButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        textField2.setText("");
                        textField3.setText("");
                        comboBoxProduct.getEditor().setItem("");
                        comboBoxUnit.getEditor().setItem("");
                    }
                });
             }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        ImageIcon=new JLabel(new ImageIcon("res/sell.png"));
        ImageIcon.setHorizontalAlignment(JLabel.CENTER);
    }
}
