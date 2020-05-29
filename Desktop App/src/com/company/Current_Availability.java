package com.company;


import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Current_Availability {

    JPanel panel1;
    private JTable table1;
    private JButton button1;

    Current_Availability()
    {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                try
                {
                    Connection connection=DriverManager.getConnection("jdbc:sqlite:Products.db");
                    String query="SELECT * FROM goods ORDER BY Quantity ASC";
                    Statement statement=connection.createStatement();
                    ResultSet resultSet=statement.executeQuery(query);
                    resultSet.next();
                    table1.setModel(DbUtils.resultSetToTableModel(resultSet));

                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }


            }
        });
    }
}
