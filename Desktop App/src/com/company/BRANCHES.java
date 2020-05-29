package com.company;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BRANCHES {
    public static JFrame jFrameMaster;
    public static JFrame jFrameBill;
    public static JFrame jFramePurchase;
    public static JFrame jFrameSell;
    public static JFrame jFrameCurrent;
    public static JFrame jFrameUpdate;
    public static JFrame jFrameSearch;
    JPanel panel1;
    private JButton MASTERButton;
    private JButton STOCKENQUIRYButton;
    private JButton PURCHASEButton;
    private JButton SELLButton;
    private JButton UPDATEButton;
    private JLabel ImageMaster;
    private JLabel ImageStock;
    private JLabel ImagePurchase;
    private JLabel ImageSell;
    private JLabel ImageUpdate;
    private JButton SEARCHButton;
    private JLabel ImageIconSearch;
    private JButton BILLButton;
    private JLabel ImageBill;
    public static int insert=0;


    public BRANCHES() {
        ImageMaster.setHorizontalAlignment(JLabel.CENTER);
        MASTERButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                jFrameMaster=new JFrame("MODUS");
                jFrameMaster.setContentPane(new master().panel1);
                jFrameMaster.setVisible(true);
                jFrameMaster.setSize(500,700 );
                jFrameMaster.setLocationRelativeTo(null);

            }
        });
        STOCKENQUIRYButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrameCurrent=new JFrame("MODUS");
                jFrameCurrent.setContentPane(new Current_Availability().panel1);
                jFrameCurrent.setVisible(true);
                jFrameCurrent.setSize(400,140 );
                jFrameCurrent.setLocationRelativeTo(null);
                jFrameCurrent.setExtendedState(JFrame.MAXIMIZED_BOTH);

            }
        });
        PURCHASEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFramePurchase=new JFrame("MODUS");
                jFramePurchase.setContentPane(new purchase().panel1);
                jFramePurchase.setVisible(true);
                jFramePurchase.setSize(400,400 );
                jFramePurchase.setLocationRelativeTo(null);

            }
        });
        SELLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrameSell=new JFrame("MODUS");
                jFrameSell.setContentPane(new sell().panel1);
                jFrameSell.setVisible(true);
                jFrameSell.setSize(600,600 );
                jFrameSell.setLocationRelativeTo(null);
                insert=0;

            }
        });

        UPDATEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrameUpdate=new JFrame("MODUS");
                jFrameUpdate.setContentPane(new update().panel1);
                jFrameUpdate.setVisible(true);
                jFrameUpdate.setSize(400,400 );
                jFrameUpdate.setLocationRelativeTo(null);
                jFrameUpdate.setExtendedState(JFrame.MAXIMIZED_BOTH);

            }
        });
        SEARCHButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrameSearch=new JFrame("MODUS");
                jFrameSearch.setContentPane(new Search().panel1);
                jFrameSearch.setVisible(true);
                jFrameSearch.setLocationRelativeTo(null);
                jFrameSearch.setExtendedState(JFrame.MAXIMIZED_BOTH);

            }
        });
        BILLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrameBill=new JFrame("MODUS");
                jFrameBill.setContentPane(new takeBillNumber().panel1);
                jFrameBill.setVisible(true);
                jFrameBill.setLocationRelativeTo(null);
                jFrameBill.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        ImageMaster = new JLabel(new ImageIcon("res/Master.png"));
        ImageStock = new JLabel(new ImageIcon("res/Stock.png"));
        ImageSell = new JLabel(new ImageIcon("res/sell.png"));
        ImagePurchase = new JLabel(new ImageIcon("res/purchase.png"));
        ImageUpdate = new JLabel(new ImageIcon("res/update.png"));
        ImageIconSearch = new JLabel(new ImageIcon("res/Search.png"));
        ImageBill=new JLabel(new ImageIcon("res/bills.png"));
    }
}
