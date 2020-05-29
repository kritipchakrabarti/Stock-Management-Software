package com.company;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class master {

    JPanel panel1;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JButton button1;
    private JComboBox comboBoxUnit;
    public String combo_msg_unit;
    private JComboBox comboBoxProduct;
    public String combo_msg_product;
    private JComboBox comboBoxCompany;
    public String combo_msg_company;
    private JComboBox comboBoxDistributor;
    private JButton clearButton;
    public String combo_msg_distributor;


    public master() {

        AutoCompleteDecorator.decorate(comboBoxUnit);
        AutoCompleteDecorator.decorate(comboBoxProduct);
        AutoCompleteDecorator.decorate(comboBoxCompany);
        AutoCompleteDecorator.decorate(comboBoxDistributor);

        Connection connection= null;
        try
        {
            connection = DriverManager.getConnection("jdbc:sqlite:Products.db");
            String queryCreate="CREATE TABLE IF NOT EXISTS goods(Product_Name varchar(40), Company varchar(40), Unit varchar(10), Market_Price NUMBER(6,2),Cost_Price NUMBER(6,2),Selling_Price NUMBER(5,2),Current_Distributor VARCHAR(20),Quantity Number(9,2))";
            Statement statement1=connection.createStatement();
            statement1.execute(queryCreate);
            String queryCount="SELECT COUNT(Product_Name) FROM goods";
            Statement statement3=connection.createStatement();
            ResultSet resultSet=statement3.executeQuery(queryCount);
            resultSet.next();
            int count=resultSet.getInt(1);
            connection.close();

            if(count==0)
            {
                String queryinsert="INSERT INTO goods VALUES( ' ', ' ', ' ', 0, 0, 0, ' ', 0)";
                connection = DriverManager.getConnection("jdbc:sqlite:Products.db");
                Statement statement4=connection.createStatement();
                statement4.execute(queryinsert);
                connection.close();
            }

            connection = DriverManager.getConnection("jdbc:sqlite:Products.db");
            String query="SELECT Product_Name FROM goods";
            Statement statement=connection.createStatement();
            ResultSet resultSet2=statement.executeQuery(query);

            ArrayList<String> names=new ArrayList<String>();
            while(resultSet2.next())
            {
                names.add(resultSet2.getString("Product_Name"));
            }
            comboBoxProduct.setModel(new DefaultComboBoxModel(names.toArray()));

            String queryUnit="SELECT Unit FROM goods";
            Statement statement2=connection.createStatement();
            ResultSet resultSetUnit=statement2.executeQuery(queryUnit);

            ArrayList<String> namesUnit=new ArrayList<String>();
            while(resultSetUnit.next())
            {
                namesUnit.add(resultSetUnit.getString("Unit"));
            }
            comboBoxUnit.setModel(new DefaultComboBoxModel(namesUnit.toArray()));

            String queryCompany="SELECT Company FROM goods";
            Statement statement0=connection.createStatement();
            ResultSet resultSetCompany=statement0.executeQuery(queryCompany);

            ArrayList<String> namesComapnay=new ArrayList<String>();
            while(resultSetCompany.next())
            {
                namesComapnay.add(resultSetCompany.getString("Company"));
            }
            comboBoxCompany.setModel(new DefaultComboBoxModel(namesComapnay.toArray()));

            String queryDist="SELECT Current_Distributor FROM goods";
            Statement statement4=connection.createStatement();
            ResultSet resultSetDist=statement4.executeQuery(queryDist);

            ArrayList<String> namesDist=new ArrayList<String>();
            while(resultSetDist.next())
            {
                namesDist.add(resultSetDist.getString("Current_Distributor"));
            }
            comboBoxDistributor.setModel(new DefaultComboBoxModel(namesDist.toArray()));
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        button1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Connection connection= null;
                try {


                    String p_name=combo_msg_product;
                    String c_name=combo_msg_company;
                    String unit=combo_msg_unit;
                    String mp=textField4.getText();
                    String cp=textField5.getText();
                    String sp=textField6.getText();
                    String c_dist=combo_msg_distributor;

                    connection = DriverManager.getConnection("jdbc:sqlite:Products.db");
                    String queryselect="SELECT COUNT(Product_Name) FROM goods WHERE LOWER(Product_Name) = LOWER('"+p_name+"') AND LOWER(Unit) = LOWER('"+unit+"')";
                    Statement statement7=connection.createStatement();
                    ResultSet resultSet7=statement7.executeQuery(queryselect);
                    resultSet7.next();
                    int count=resultSet7.getInt(1);
                    resultSet7.close();
                    if(count==1)
                        JOptionPane.showMessageDialog(null, "Product Already Registered");
                    else {

                        String queryinsert = "INSERT INTO goods VALUES( '" + p_name + "' , '" + c_name + "' , '" + unit + "' ," + mp + "," + cp + "," + sp + ",'" + c_dist + "'," + 0 + ")";
                        connection = DriverManager.getConnection("jdbc:sqlite:Products.db");
                        Statement statement2 = connection.createStatement();
                        statement2.execute(queryinsert);
                        connection.close();
                        JOptionPane.showMessageDialog(null, "Registered a new Product");
                    }
                }
                catch (SQLException e1)
                {
                    JOptionPane.showMessageDialog(null, "Could not Insert Data. Check it once Again");
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
        comboBoxDistributor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox jComboBox= ((JComboBox)e.getSource());
                combo_msg_distributor=(String) jComboBox.getSelectedItem();
            }
        });
        comboBoxCompany.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox jComboBox= ((JComboBox)e.getSource());
                combo_msg_company=(String) jComboBox.getSelectedItem();
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
                textField5.setText("");
                textField6.setText("");
                comboBoxCompany.getEditor().setItem("");
                comboBoxDistributor.getEditor().setItem("");
                comboBoxProduct.getEditor().setItem("");
                comboBoxUnit.getEditor().setItem("");
            }
        });
    }
}
