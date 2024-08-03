/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package duan.qlbn.ui;

import duan.qlbn.utils.MsgBox;
import java.text.MessageFormat;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class InHoaDonJDialog extends javax.swing.JDialog {

    DefaultTableModel tblInHD;
    String inTongTien;
    String inTienThanhToan;
    String inTienThua;
    int inSoHang;

    public InHoaDonJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
    }

    public void bill_print() {
        try {
            bill.setText("                         \tCửa Hàng Nước \n");
            bill.setText(bill.getText() + "---------------------------------------------------------------------------------------\n");
            bill.setText(bill.getText() + " STT \tMã SP \tTên SP \tSố Lượng \tĐơn Giá \n");
            bill.setText(bill.getText() + "---------------------------------------------------------------------------------------\n");

            for (int i = 0; i < inSoHang; i++) {
                String stt = tblInHD.getValueAt(i, 0).toString();
                String maSP = tblInHD.getValueAt(i, 1).toString();
                String tenSP = tblInHD.getValueAt(i, 2).toString();
                String soLuong = tblInHD.getValueAt(i, 3).toString();
                String donGia = tblInHD.getValueAt(i, 4).toString();

                bill.setText(bill.getText() + stt + "\t" + maSP + "\t" + tenSP + "\t" + soLuong + "\t" + donGia + " \n");

            }
            bill.setText(bill.getText() + "---------------------------------------------------------------------------------------\n");
            bill.setText(bill.getText() + "Tổng tiền hàng :\t" + inTongTien + "\n");
            bill.setText(bill.getText() + "Tiền thanh toán :\t" + inTienThanhToan + "\n");
            bill.setText(bill.getText() + "Tiền thừa :\t\t" + inTienThua + "\n");
            bill.setText(bill.getText() + "================================================\n");

            boolean xuatHD = MsgBox.confirm(this, "Bạn có muốn xuất hóa đơn");
            MessageFormat footer = new MessageFormat("Trang{0,number,integer}");
            try {
                if (xuatHD) {
                    bill.print();
                    this.dispose();
                }else{
                    this.dispose();
                }
            } catch (Exception e) {
                MsgBox.alert(this, "Xuất thất bại");
            }
        } catch (Exception e) {
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        bill = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jScrollPane1.setViewportView(bill);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        bill_print();
    }//GEN-LAST:event_formWindowOpened

    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(InHoaDonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InHoaDonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InHoaDonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InHoaDonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InHoaDonJDialog dialog = new InHoaDonJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane bill;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    void bangHoaDon(DefaultTableModel tblHDCT, int soHang, String tongTienHang, String tienThanhToan, String tienThua) {
        tblInHD = tblHDCT;
        inSoHang = soHang;
        inTongTien = tongTienHang;
        inTienThanhToan = tienThanhToan;
        inTienThua = tienThua;
    }
}
