package duan.qlbn.ui;

import duan.qlbn.dao.NhanVienDAO;
import duan.qlbn.utils.MsgBox;

public class DoiMatKhauJDialog extends javax.swing.JDialog {

    NhanVienDAO nvDAO = new NhanVienDAO();
    private String maNhanVien;

    public DoiMatKhauJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        txtMatKhauMoi.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtXacNhanMK.setBackground(new java.awt.Color(0, 0, 0, 1));
    }

    boolean validateForm() {
        if (txtMatKhauMoi.getText().trim().isEmpty()) {
            MsgBox.alert(this, "Chưa nhập mật khẩu!");
            return false;
        }
        if (txtMatKhauMoi.getText().toString().length() < 3) {
            MsgBox.alert(this, "Mật khẩu ít nhất 3 ký tự!");
            return false;
        }
        if (txtXacNhanMK.getText().trim().isEmpty()) {
            MsgBox.alert(this, "Chưa nhập xác nhận mật khẩu!");
            return false;
        }
        return true;
    }

    void doiMatKhau() {
        String matKhauMoi = new String(txtMatKhauMoi.getPassword());
        String matKhauMoi2 = new String(txtXacNhanMK.getPassword());
        if (!matKhauMoi.equals(matKhauMoi2)) {
            MsgBox.alert(this, "Xác nhận mật khẩu không đúng");
        } else {
            nvDAO.updateMatKhau(matKhauMoi2, maNhanVien);
            MsgBox.alert(this, "Đổi mật khẩu thành công");
            this.dispose();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnXacNhan = new javax.swing.JButton();
        btnHuyBo = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtMatKhauMoi = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        txtXacNhanMK = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ĐỔI MẬT KHẨU");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 6, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Mật khẩu mới");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 84, -1, -1));

        btnXacNhan.setText("Xác nhận");
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });
        jPanel1.add(btnXacNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 286, -1, -1));

        btnHuyBo.setText("Hủy bỏ");
        btnHuyBo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyBoActionPerformed(evt);
            }
        });
        jPanel1.add(btnHuyBo, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 286, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Xác nhận mật khẩu");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 188, -1, -1));

        txtMatKhauMoi.setFont(txtMatKhauMoi.getFont().deriveFont(txtMatKhauMoi.getFont().getSize()+2f));
        txtMatKhauMoi.setForeground(new java.awt.Color(255, 255, 255));
        txtMatKhauMoi.setBorder(null);
        txtMatKhauMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMatKhauMoiMouseClicked(evt);
            }
        });
        jPanel1.add(txtMatKhauMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 340, 30));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("______________________________________________________________________");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 340, 40));

        txtXacNhanMK.setFont(txtXacNhanMK.getFont().deriveFont(txtXacNhanMK.getFont().getSize()+2f));
        txtXacNhanMK.setForeground(new java.awt.Color(255, 255, 255));
        txtXacNhanMK.setBorder(null);
        txtXacNhanMK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtXacNhanMKMouseClicked(evt);
            }
        });
        jPanel1.add(txtXacNhanMK, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 340, 30));

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("______________________________________________________________________");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 340, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        // TODO add your handling code here:
        if (validateForm()) {
            doiMatKhau();
        }

    }//GEN-LAST:event_btnXacNhanActionPerformed

    private void btnHuyBoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyBoActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnHuyBoActionPerformed

    private void txtMatKhauMoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMatKhauMoiMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMatKhauMoiMouseClicked

    private void txtXacNhanMKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtXacNhanMKMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtXacNhanMKMouseClicked

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
            java.util.logging.Logger.getLogger(DoiMatKhauJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DoiMatKhauJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DoiMatKhauJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DoiMatKhauJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DoiMatKhauJDialog dialog = new DoiMatKhauJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnHuyBo;
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtMatKhauMoi;
    private javax.swing.JPasswordField txtXacNhanMK;
    // End of variables declaration//GEN-END:variables

    void guiMaNV(String maNV) {
        maNhanVien = maNV;
    }
}
