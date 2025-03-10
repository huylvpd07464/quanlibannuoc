/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package duan.qlbn.ui;

import duan.qlbn.utils.Auth;
import duan.qlbn.utils.MsgBox;
import duan.qlbn.utils.XImage;
import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 *
 * @author HP
 */
public class QLBNJFrame extends javax.swing.JFrame {

    private JPanel childPanel;

    /**
     * Creates new form QLBNJFrame
     */
    public QLBNJFrame() {
        initComponents();
        init();
    }

    void init() {
        setIconImage(XImage.getAppIcon());
        setLocationRelativeTo(null);
        setTitle("QUẢN LÝ BÁN NƯỚC");
        childPanel = new LogoPanel();
        pnMain.removeAll();
        pnMain.add(childPanel, BorderLayout.EAST);
        pnMain.revalidate();
    }

    boolean kiemTraVaiTro() {
        if (Auth.user.isVaiTro() == false) {
            return false;
        }
        return true;
    }

    void moHoaDon() {
        childPanel = new HoaDonPanel();
        pnMain.removeAll();
        pnMain.add(childPanel, BorderLayout.EAST);
        pnMain.revalidate();
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
        pnMain = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnNhanVien = new javax.swing.JButton();
        btnNhaCungCap = new javax.swing.JButton();
        btnHoaDon = new javax.swing.JButton();
        btnSanPham = new javax.swing.JButton();
        btnKhachHang = new javax.swing.JButton();
        btnThongKe = new javax.swing.JButton();
        btnKetThuc = new javax.swing.JButton();
        btnDangXuat = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 255, 255));

        jPanel1.setBackground(new java.awt.Color(102, 204, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        pnMain.setBackground(new java.awt.Color(255, 255, 255));
        pnMain.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnMain.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 227, 187));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnNhanVien.setBackground(new java.awt.Color(255, 210, 143));
        btnNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/duan/qlbn/icon/staff.png"))); // NOI18N
        btnNhanVien.setText("Nhân Viên");
        btnNhanVien.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNhanVien.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhanVienActionPerformed(evt);
            }
        });

        btnNhaCungCap.setBackground(new java.awt.Color(255, 210, 143));
        btnNhaCungCap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/duan/qlbn/icon/supplier.png"))); // NOI18N
        btnNhaCungCap.setText("Nhà Cung Cấp");
        btnNhaCungCap.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNhaCungCap.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNhaCungCap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhaCungCapActionPerformed(evt);
            }
        });

        btnHoaDon.setBackground(new java.awt.Color(255, 210, 143));
        btnHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/duan/qlbn/icon/bill.png"))); // NOI18N
        btnHoaDon.setText("Hóa Đơn");
        btnHoaDon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHoaDon.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoaDonActionPerformed(evt);
            }
        });

        btnSanPham.setBackground(new java.awt.Color(255, 210, 143));
        btnSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/duan/qlbn/icon/product.png"))); // NOI18N
        btnSanPham.setText("Sản Phẩm");
        btnSanPham.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSanPham.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSanPhamActionPerformed(evt);
            }
        });

        btnKhachHang.setBackground(new java.awt.Color(255, 210, 143));
        btnKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/duan/qlbn/icon/client.png"))); // NOI18N
        btnKhachHang.setText("Khách Hàng");
        btnKhachHang.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnKhachHang.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhachHangActionPerformed(evt);
            }
        });

        btnThongKe.setBackground(new java.awt.Color(255, 210, 143));
        btnThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/duan/qlbn/icon/graph-report.png"))); // NOI18N
        btnThongKe.setText("Thống kê");
        btnThongKe.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnThongKe.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongKeActionPerformed(evt);
            }
        });

        btnKetThuc.setBackground(new java.awt.Color(255, 210, 143));
        btnKetThuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/duan/qlbn/icon/power.png"))); // NOI18N
        btnKetThuc.setText("Kết Thúc");
        btnKetThuc.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnKetThuc.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnKetThuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKetThucActionPerformed(evt);
            }
        });

        btnDangXuat.setBackground(new java.awt.Color(255, 210, 143));
        btnDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/duan/qlbn/icon/icons8-log-out-25.png"))); // NOI18N
        btnDangXuat.setText("Đăng Xuất");
        btnDangXuat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDangXuat.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNhanVien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDangXuat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNhaCungCap, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                    .addComponent(btnHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSanPham, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnKhachHang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThongKe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnKetThuc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnDangXuat)
                .addGap(18, 18, 18)
                .addComponent(btnNhanVien)
                .addGap(18, 18, 18)
                .addComponent(btnNhaCungCap)
                .addGap(18, 18, 18)
                .addComponent(btnHoaDon)
                .addGap(18, 18, 18)
                .addComponent(btnSanPham)
                .addGap(18, 18, 18)
                .addComponent(btnKhachHang)
                .addGap(18, 18, 18)
                .addComponent(btnThongKe)
                .addGap(18, 18, 18)
                .addComponent(btnKetThuc)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnMain, javax.swing.GroupLayout.PREFERRED_SIZE, 892, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(187, 187, 187)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoaDonActionPerformed
        moHoaDon();
    }//GEN-LAST:event_btnHoaDonActionPerformed

    private void btnNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhanVienActionPerformed
        // TODO add your handling code here:
        if (Auth.isLogin()) {
            if (kiemTraVaiTro()) {
                childPanel = new NhanVienPanel();
                pnMain.removeAll();
                pnMain.add(childPanel, BorderLayout.EAST);
                pnMain.revalidate();
            } else {
                MsgBox.alert(this, "Chỉ quản lý mới có quyền");
            }
        } else {
            MsgBox.alert(this, "Vui lòng đăng nhập!");
        }


    }//GEN-LAST:event_btnNhanVienActionPerformed

    private void btnNhaCungCapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhaCungCapActionPerformed
        // TODO add your handling code here:
        if (Auth.isLogin()) {
            if (kiemTraVaiTro()) {
                childPanel = new NhaCungCapPanel();
                pnMain.removeAll();
                pnMain.add(childPanel, BorderLayout.EAST);
                pnMain.revalidate();
            } else {
                MsgBox.alert(this, "Chỉ quản lý mới có quyền");
            }
        } else {
            MsgBox.alert(this, "Vui lòng đăng nhập!");
        }


    }//GEN-LAST:event_btnNhaCungCapActionPerformed

    private void btnSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSanPhamActionPerformed
        // TODO add your handling code here:
        if (Auth.isLogin()) {
            childPanel = new SanPhamPanel();
            pnMain.removeAll();
            pnMain.add(childPanel, BorderLayout.EAST);
            pnMain.revalidate();
        } else {
            MsgBox.alert(this, "Vui lòng đăng nhập!");
        }

    }//GEN-LAST:event_btnSanPhamActionPerformed

    private void btnKetThucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKetThucActionPerformed
        if (MsgBox.confirm(this, "Ban muon ket thuc cong viec?")) {
            System.exit(0);
        }
    }//GEN-LAST:event_btnKetThucActionPerformed

    private void btnKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhachHangActionPerformed
        // TODO add your handling code here:
        if (Auth.isLogin()) {
            childPanel = new KhachHangPanel();
            pnMain.removeAll();
            pnMain.add(childPanel, BorderLayout.EAST);
            pnMain.revalidate();
        } else {
            MsgBox.alert(this, "Vui lòng đăng nhập!");
        }
    }//GEN-LAST:event_btnKhachHangActionPerformed

    private void btnThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongKeActionPerformed
        // TODO add your handling code here:
        if (Auth.isLogin()) {
            if (kiemTraVaiTro()) {
                childPanel = new ThongKeJPanel();
                pnMain.removeAll();
                pnMain.add(childPanel, BorderLayout.EAST);
                pnMain.revalidate();
            } else {
                MsgBox.alert(this, "Chỉ quản lý mới có quyền");
            }
        } else {
            MsgBox.alert(this, "Vui lòng đăng nhập!");
        }

    }//GEN-LAST:event_btnThongKeActionPerformed

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        // TODO add your handling code here:
        Auth.clear();
        this.dispose();
        new DangNhapJDialog(this, true).setVisible(true);
    }//GEN-LAST:event_btnDangXuatActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(QLBNJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLBNJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLBNJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLBNJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLBNJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JButton btnHoaDon;
    private javax.swing.JButton btnKetThuc;
    private javax.swing.JButton btnKhachHang;
    private javax.swing.JButton btnNhaCungCap;
    private javax.swing.JButton btnNhanVien;
    private javax.swing.JButton btnSanPham;
    private javax.swing.JButton btnThongKe;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel pnMain;
    // End of variables declaration//GEN-END:variables
}
