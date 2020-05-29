package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class LOGIN
{
    public static JFrame jFrameLogin;
    public static JFrame jFrameBranches;
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JButton button1;
    private JLabel ImageLogo;

    public LOGIN()
    {
        button1.setPreferredSize(new Dimension(100,25));

        button1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String name = textField1.getText();
                String password = textField2.getText();
//                if(name.equalsIgnoreCase("kritip") && password.equalsIgnoreCase("qsefthuko;"))
//                {
//                    JOptionPane.showMessageDialog(null,"Successfully Logged In" );
                    jFrameBranches=new JFrame("MODUS");
                    jFrameBranches.setContentPane(new BRANCHES().panel1);
                    jFrameBranches.setVisible(true);
                    jFrameBranches.setSize(400,140 );
                    jFrameBranches.setLocationRelativeTo(null);
                     jFrameBranches.setExtendedState(JFrame.MAXIMIZED_BOTH);
                     jFrameBranches.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                LOGIN.jFrameLogin.setVisible(false);
//                }
//                else
//                JOptionPane.showMessageDialog(null,"Login Failed" );
            }
        });

    }

    public static void main(String[] args)
    {
        jFrameLogin=new JFrame("MODUS");
        jFrameLogin.setContentPane(new LOGIN().panel1);
        jFrameLogin.setVisible(true);
        jFrameLogin.setSize(340,440 );
        jFrameLogin.setLocationRelativeTo(null);
        jFrameLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        ImageLogo=new JLabel(new ImageIcon("res/Gro.png"));
        ImageLogo.setHorizontalAlignment(JLabel.CENTER);
    }
}
