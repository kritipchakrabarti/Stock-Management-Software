/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company;

import java.awt.*;
import java.awt.print.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author mic
 */
public class Print extends javax.swing.JFrame {

    /**
     * Creates new form bill_form
     */

    Connection connection=null;
    Connection connection2=null;
    public static int k=1;
    public Print() {
        initComponents();
    }

    public PageFormat getPageFormat(PrinterJob pj)
    {

        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();

        double middleHeight =8.0;
        double headerHeight = 2.0;
        double footerHeight = 2.0;
        double width = convert_CM_To_PPI(15);      //printer know only point per inch.default value is 72ppi
        double height = convert_CM_To_PPI(headerHeight+middleHeight+footerHeight);
        paper.setSize(width, height);
        paper.setImageableArea(
                0,
                10,
                width,
                height - convert_CM_To_PPI(1)
        );   //define boarder size    after that print area width is about 180 points

        pf.setOrientation(PageFormat.PORTRAIT);           //select orientation portrait or landscape but for this time portrait
        pf.setPaper(paper);

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:Bills.db");
            String query = "SELECT Sellid FROM temp ORDER BY Sellid DESC ";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int i=0;
            String id="";
            ArrayList<String> sid=new ArrayList<>();
            while(resultSet.next()&&i<=BRANCHES.insert)
            {
                if(i==0)
                id = resultSet.getString("Sellid");
                sid.add(resultSet.getString("Sellid"));
                i++;
            }
            connection.close();
            resultSet.close();
            statement.close();

            connection = DriverManager.getConnection("jdbc:sqlite:Bills.db");
            Statement statement2 = connection.createStatement();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String date_bill=formatter.format(date).substring(0,10);
            String billno=date_bill.substring(6,10)+date_bill.substring(3,5 )+date_bill.substring(0,2 )+id;
            i=0;
            while(i<BRANCHES.insert)
            {
                statement2 = connection.createStatement();
                String queryUpdate = "UPDATE temp SET Bill_no = '"+billno +"' WHERE Sellid = '" + sid. get(i)+"'";
                statement2.execute(queryUpdate);
                statement2.close();
                i++;
            }

            connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return pf;
    }

    protected static double convert_CM_To_PPI(double cm) {
        return toPPI(cm * 0.393600787);
    }

    protected static double toPPI(double inch) {
        return inch * 72d;
    }






    public class BillPrintable implements Printable
    {




        public int print(Graphics graphics, PageFormat pageFormat,int pageIndex)
                throws PrinterException
        {



            int result = NO_SUCH_PAGE;
            if (pageIndex == 0) {

                Graphics2D g2d = (Graphics2D) graphics;

                double width = pageFormat.getImageableWidth();

                g2d.translate((int) pageFormat.getImageableX(),(int) pageFormat.getImageableY());

                ////////// code by alqama//////////////

                FontMetrics metrics=g2d.getFontMetrics(new Font("Arial",Font.BOLD,7));
                //    int idLength=metrics.stringWidth("000000");
                //int idLength=metrics.stringWidth("00");
                int idLength=metrics.stringWidth("000");
                int amtLength=metrics.stringWidth("000000");
                int qtyLength=metrics.stringWidth("00000");
                int priceLength=metrics.stringWidth("000000");
                int prodLength=(int)width - idLength - amtLength - qtyLength - priceLength-17;

                //    int idPosition=0;
                //    int productPosition=idPosition + idLength + 2;
                //    int pricePosition=productPosition + prodLength +10;
                //    int qtyPosition=pricePosition + priceLength + 2;
                //    int amtPosition=qtyPosition + qtyLength + 2;

                int productPosition = 0;
                int discountPosition= prodLength+5;
                int pricePosition = discountPosition +idLength+10;
                int qtyPosition=pricePosition + priceLength + 4;
                int amtPosition=qtyPosition + qtyLength;



                try{
                    /*Draw Header*/
                    int y=20;
                    int yShift = 10;
                    int headerRectHeight=15;
                    int headerRectHeighta=40;
                    ArrayList<String> p = new ArrayList<String>();
                    ArrayList<String> c = new ArrayList<String>();
                    ArrayList<String> qt = new ArrayList<String>();
                    String fname = "", lname = "";
                    int i = 1;
                    double ta = 0;

                    if(k==2||k==1)
                    {
                        ///////////////// Product names Get ///////////
                        connection = DriverManager.getConnection("jdbc:sqlite:Bills.db");
                        String query = "SELECT Product_Name, Unit, Selling_Price, Quantity, fname, lname FROM temp ORDER BY Sellid DESC ";
                        Statement statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery(query);
                        while (resultSet.next() && i <= BRANCHES.insert) {
                            fname = resultSet.getString("fname");
                            lname = resultSet.getString("lname");
                            p.add(resultSet.getString("Product_Name") + " " + resultSet.getString("Unit"));
                            c.add(Double.toString(resultSet.getDouble("Selling_Price")));
                            qt.add(Double.toString(resultSet.getDouble("Quantity")));
                            i++;
                        }
                        ///////////////// Total Cost Found ///////////

                        for (i = 0; i < p.size(); i++)
                            ta += Double.parseDouble(c.get(i)) * Double.parseDouble(qt.get(i));
                        for (i = 0; i < p.size(); i++) {
                            while (p.get(i).length() < 25)
                                p.set(i, p.get(i) + " ");
                        }

                        while (fname.length() < 37)
                            fname += " ";
                        while (lname.length() < 30)
                            lname += " ";

                        for (i = 0; i < c.size(); i++) {
                            while (c.get(i).length() < 10)
                                c.set(i, c.get(i) + " ");
                        }
                        for (i = 0; i < qt.size(); i++) {
                            while (qt.get(i).length() < 10)
                                qt.set(i, qt.get(i) + " ");
                        }

                        connection.close();
                        k++;
                    }

                        /////////////////Address and Phone Number Get//////////
                        String pn1a = pn1.getText();
                        String pn2a = pn2.getText();
                        String pn3a = pn3.getText();
                        String pn4a = pn4.getText();


                        /////////////////Customer name Get//////////

                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date date = new Date();

                        g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
                        g2d.drawString("---------------------------------------------------", 12, y);
                        y += yShift;
                        g2d.drawString("                      MODUS Store                  ", 12, y);
                        y += yShift;
                        g2d.drawString("---------------------------------------------------", 12, y);
                        y += headerRectHeight;
                        g2d.drawString(" " + fname + "Date - Time   ", 10, y);
                        y += yShift;
                        g2d.drawString(" " + lname + "" + formatter.format(date), 10, y);
                        y += yShift;
                        g2d.drawString(" " + pn1a + "( Near " + pn2a + ")                                  ", 10, y);
                        y += yShift;
                        g2d.drawString(" " + pn3a + ", " + pn4a + "                                        ", 10, y);
                        y += yShift;
                        g2d.drawString("---------------------------------------------------", 10, y);
                        y += yShift;
                        g2d.drawString(" Product Name             Qty       Unit      Total", 10, y);
                        y += yShift;
                        g2d.drawString("---------------------------------------------------", 10, y);
                        y += headerRectHeight;
                        for (i = 0; i < p.size(); i++) {
                            g2d.drawString(" " + p.get(i) + "" + qt.get(i) + "" + c.get(i) + "" + Double.parseDouble(c.get(i)) * Double.parseDouble(qt.get(i)) + "", 10, y);
                            y += yShift;
                        }
                        g2d.drawString("---------------------------------------------------", 10, y);
                        y += yShift;
                        g2d.drawString(" Total amount:                                " + ta + "", 10, y);
                        y += yShift;
                        g2d.drawString("---------------------------------------------------", 10, y);
                        y += yShift;
                        g2d.drawString("                 Free Home Delivery                ", 10, y);
                        y += yShift;
                        g2d.drawString("                     8013708152                    ", 10, y);
                        y += yShift;
                        g2d.drawString("***************************************************", 10, y);
                        y += yShift;
                        g2d.drawString("                THANKS FOR ORDERING                ", 10, y);
                        y += yShift;
                        g2d.drawString("***************************************************", 10, y);
                        y += yShift;
//            g2d.setFont(new Font("Monospaced",Font.BOLD,10));
//            g2d.drawString("Customer Shopping Invoice", 30,y);y+=yShift;

                }
                catch(Exception r){
                    r.printStackTrace();
                }

                result = PAGE_EXISTS;
            }
            return result;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        pn4 = new javax.swing.JTextField();
        pn2 = new javax.swing.JTextField();
        pn3 = new javax.swing.JTextField();
        pn1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Bill Receipt Printing");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(153, 0, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Bill Rceipt Printing System");
        jLabel1.setToolTipText("");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(199, 11, 278, 59));

        jLabel2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Address Line 2: ");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 99, 26));

        jLabel4.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Near: ");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 99, 26));

        jLabel5.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Address Line 1: ");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 99, 26));

        jLabel6.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Phone Number: ");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 99, 26));
        jPanel1.add(pn4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 250, 283, 30));
        jPanel1.add(pn2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 283, 30));
        jPanel1.add(pn3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 220, 283, 30));
        jPanel1.add(pn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, 283, 30));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jButton1.setText("Print Receipt");
        jButton1.setBorder(null);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 310, 170, 50));

        jLabel8.setText("Kritip Chakrabarti|Software Creation|Contact : kritipchakrabarti123@gmail.com");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel8)
                                .addContainerGap(453, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 660, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_jButton1ActionPerformed

        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(new BillPrintable(),getPageFormat(pj));
        try {
            pj.print();
        }
        catch (PrinterException ex) {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Print.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Print.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Print.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Print.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Print().setVisible(true);
            }
        });
        System.out.println("main");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField pn1;
    private javax.swing.JTextField pn2;
    private javax.swing.JTextField pn3;
    private javax.swing.JTextField pn4;

    // End of variables declaration//GEN-END:variables
}
