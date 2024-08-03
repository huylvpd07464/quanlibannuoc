/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package duan.qlbn.ui;

import duan.qlbn.dao.LoaiSanPhamDAO;
import duan.qlbn.dao.NhaCungCapDAO;
import duan.qlbn.dao.SanPhamDAO;
import duan.qlbn.entity.LoaiSanPham;
import duan.qlbn.entity.MyCombobox;
import duan.qlbn.entity.NhaCungCap;
import duan.qlbn.entity.SanPham;
import duan.qlbn.utils.Auth;
import duan.qlbn.utils.MsgBox;
import duan.qlbn.utils.XImage;
import java.io.File;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class SanPhamPanel extends javax.swing.JPanel {

    JFileChooser fileChooser = new JFileChooser();
    LoaiSanPhamDAO lspDAO = new LoaiSanPhamDAO();
    NhaCungCapDAO nccDAO = new NhaCungCapDAO();
    SanPhamDAO spDAO = new SanPhamDAO();
    int rowLSP = -1;
    int rowSP = -1;
    boolean test = false;

    /**
     * Creates new form SPPanel
     */
    public SanPhamPanel() {
        initComponents();
        init();
    }

    void init() {
        fillToTableLSP();
        fillComboBoxLSP();
        fillComboBoxNCC();
        fillToTableSP();
        updateStatusSP();
        updateStatusLSP();
    }

    //Sản phẩm
    void updateStatusSP() {
        boolean edit = this.rowSP >= 0;
        boolean first = this.rowSP == 0;
        boolean last = this.rowSP == tblSanPham.getRowCount() - 1;
        txtMaSP.setEditable(!edit);
        //Khi insert thì không update, delete
        btnThemSP.setEnabled(!edit);
        btnSuaSP.setEnabled(edit);
        btnXoaSP.setEnabled(edit);

        btnDauSP.setEnabled(edit && !first);
        btnSauSP.setEnabled(edit && !first);
        btnTruocSP.setEnabled(edit && !last);
        btnCuoiSP.setEnabled(edit && !last);
    }

    void deleteSP() {
        if (MsgBox.confirm(this, "Bạn thực sự muốn xóa sản phẩm này!")) {
            String maSP = txtMaSP.getText();
            try {
                spDAO.delete(maSP);
                test = true;
                this.fillToTableSP();
                this.clearFormSP();
                MsgBox.alert(this, "Xóa thành công!");
            } catch (Exception e) {
                MsgBox.alert(this, "Xóa thất bại!");
            }
        }
    }

    void updateSP() {
        SanPham model = getFormSP();
        try {
            spDAO.update(model);
            this.fillToTableSP();
            MsgBox.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Cập nhật thất bại!");
        }
    }

    void editSP() {
        try {
            String maSP = (String) tblSanPham.getValueAt(this.rowSP, 0);
            SanPham model = spDAO.selectByID(maSP);
            if (model != null) {
                this.setFormSP(model);
                updateStatusSP();
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void setFormSP(SanPham model) {
        txtMaSP.setText(model.getMaSP());
        txtTenSP.setText(model.getTenSP());
        txtSoLuong.setText(String.valueOf(model.getSoLuong()));
        txtDonGia.setText(String.valueOf(Math.round(model.getDonGia())));
        int demLSP = 0;
        try {
            List<LoaiSanPham> list = lspDAO.selectAll();
            for (LoaiSanPham lsp : list) {
                if (lsp.getMaLSP() == model.getMaLSP()) {
                    break;
                }
                demLSP++;
            }
        } catch (Exception e) {
        }
        cboLoaiSanPham.setSelectedIndex(demLSP);

        int demNCC = 0;
        try {
            List<NhaCungCap> list = nccDAO.selectAll();
            for (NhaCungCap ncc : list) {
                if (ncc.getMaNCC().equals(model.getMaNCC())) {
                    break;
                }
                demNCC++;
            }
        } catch (Exception e) {
        }
        cboNhaCungCap.setSelectedIndex(demNCC);

        txtMoTaSP.setText(model.getMoTa());
        if (model.getHinhAnh() != null) {
            lblHinh.setIcon(XImage.read(model.getHinhAnh()));
            lblHinh.setToolTipText(model.getHinhAnh());
        }
    }

    void clearFormSP() {
        updateStatusSP();
        txtMaSP.setText("");
        txtTenSP.setText("");
        txtSoLuong.setText("");
        txtDonGia.setText("");
        cboLoaiSanPham.setSelectedIndex(0);
        cboNhaCungCap.setSelectedIndex(0);
        txtMoTaSP.setText("");
        lblHinh.setIcon(null);
        rowSP = -1;
        updateStatusSP();
    }

    void chonAnh() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            XImage.save(file);
            ImageIcon icon = XImage.read(file.getName());
            lblHinh.setIcon(icon);
            lblHinh.setToolTipText(file.getName());
        }
    }

    SanPham getFormSP() {
        SanPham sp = new SanPham();
        sp.setMaSP(txtMaSP.getText());
        sp.setTenSP(txtTenSP.getText());
        sp.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
        sp.setDonGia(Double.parseDouble(txtDonGia.getText()));
        sp.setHinhAnh(lblHinh.getToolTipText());
        sp.setMoTa(txtMoTaSP.getText());
        MyCombobox maLSP = (MyCombobox) cboLoaiSanPham.getSelectedItem();
        sp.setMaLSP(maLSP.MaInt());
        MyCombobox maNCC = (MyCombobox) cboNhaCungCap.getSelectedItem();
        sp.setMaNCC(maNCC.MaString());
        sp.setMaNV(Auth.user.getMaNV());
        return sp;
    }

    void insertSP() {
        SanPham sp = getFormSP();
        try {
            spDAO.insert(sp);
            fillToTableSP();
            fillComboBoxLSP();
            clearFormSP();
            MsgBox.alert(this, "Thêm mới thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Thêm mới thất bại!");
        }

    }

    void fillToTableSP() {
        DefaultTableModel tblModeSP = (DefaultTableModel) tblSanPham.getModel();
        tblModeSP.setRowCount(0);

        try {
            String keyword = txtTimKiemSP.getText();
            List<SanPham> list = spDAO.selectByKeyword(keyword);
            for (SanPham sp : list) {
                Object[] row = {
                    sp.getMaSP(),
                    sp.getTenSP(),
                    sp.getSoLuong(),
                    sp.getDonGia(),
                    sp.getHinhAnh(),
                    sp.getMoTa(),
                    sp.getMaLSP(),
                    sp.getMaNCC(),
                    sp.getMaNV()
                };
                tblModeSP.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu sản phẩm!");
        }
    }

    void fillComboBoxNCC() {
        DefaultComboBoxModel cboModelNCC = (DefaultComboBoxModel) cboNhaCungCap.getModel();
        cboModelNCC.removeAllElements();
        try {
            List<NhaCungCap> list = nccDAO.selectAll();
            for (NhaCungCap ncc : list) {
                MyCombobox mycbb = new MyCombobox(ncc.getMaNCC(), ncc.getTenNCC());
                cboModelNCC.addElement(mycbb);
            }
        } catch (Exception e) {
        }
    }

    void fillComboBoxLSP() {
        DefaultComboBoxModel cboModelLSP = (DefaultComboBoxModel) cboLoaiSanPham.getModel();
        cboModelLSP.removeAllElements();
        try {
            List<LoaiSanPham> list = lspDAO.selectAll();
            for (LoaiSanPham lsp : list) {
                MyCombobox mycbb = new MyCombobox(lsp.getMaLSP(), lsp.getTenLSP());
                cboModelLSP.addElement(mycbb);
            }
        } catch (Exception e) {
        }
    }

    boolean validateFormSP() {
        if (txtMaSP.getText().trim().isEmpty()) {
            MsgBox.alert(this, "Chưa nhập mã!");
            return false;
        }
        if (txtTenSP.getText().trim().isEmpty()) {
            MsgBox.alert(this, "Chưa nhập tên sản phẩm!");
            return false;
        }
        if (txtSoLuong.getText().trim().isEmpty()) {
            MsgBox.alert(this, "Chưa nhập số lượng!");
            return false;
        }
        if (txtSoLuong.getText().matches("[0-9]+") == false) {
            MsgBox.alert(this, "Chỉ cho nhập số!");
            txtSoLuong.requestFocus();
            return false;
        }
        if (txtDonGia.getText().trim().isEmpty()) {
            MsgBox.alert(this, "Chưa nhập đơn giá!");
            return false;
        }
        if (txtDonGia.getText().matches("[0-9]+") == false) {
            MsgBox.alert(this, "Chỉ cho nhập số!");
            txtDonGia.requestFocus();
            return false;
        }
        return true;
    }

    //---------------------------------------------------------
    //Loại sản phẩm
    void updateStatusLSP() {
        boolean edit = this.rowLSP >= 0;
        boolean first = this.rowLSP == 0;
        boolean last = this.rowLSP == tblLoaiSanPham.getRowCount() - 1;
        //Khi insert thì không update, delete
        btnThemLoaiSP.setEnabled(!edit);
        btnSuaLSP.setEnabled(edit);
        btnXoaLSP.setEnabled(edit);
    }

    void clearFormLSP() {
        txtTenLSP.setText("");
        txtMoTaLSP.setText("");
        this.updateStatusLSP();
        rowLSP = -1;
        updateStatusLSP();
    }

    void updateLSP() {
        int maLSP = (int) tblLoaiSanPham.getValueAt(rowLSP, 0);
        LoaiSanPham lspModel = new LoaiSanPham();
        lspModel.setMaLSP(maLSP);
        lspModel.setTenLSP(txtTenLSP.getText());
        lspModel.setMoTa(txtMoTaLSP.getText());
        try {
            lspDAO.update(lspModel);
            this.fillToTableLSP();
            fillComboBoxLSP();
            this.clearFormLSP();
            MsgBox.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Cập nhật thất bại!");
        }
    }

    void deleteLSP() {
        if (MsgBox.confirm(this, "Bạn thực sự muốn xóa nhà cung cấp này!")) {
            int maLSP = (int) tblLoaiSanPham.getValueAt(rowLSP, 0);
            try {
                lspDAO.delete(String.valueOf(maLSP));
                this.fillToTableLSP();
                fillComboBoxLSP();
                this.clearFormLSP();
                MsgBox.alert(this, "Xóa thành công!");
            } catch (Exception e) {
                MsgBox.alert(this, "Xóa thất bại!");
            }
        }
    }

    void editLSP() {
        try {
            int maLSP = (int) tblLoaiSanPham.getValueAt(this.rowLSP, 0);
            LoaiSanPham model = lspDAO.selectByID(String.valueOf(maLSP));
            if (model != null) {
                setFormLSP(model);
                updateStatusLSP();
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void setFormLSP(LoaiSanPham model) {
        txtTenLSP.setText(model.getTenLSP());
        txtMoTaLSP.setText(model.getMoTa());
    }

    void fillToTableLSP() {
        DefaultTableModel tblModel = (DefaultTableModel) tblLoaiSanPham.getModel();
        tblModel.setRowCount(0);
        try {
            String keyword = txtTimKiemLSP.getText();
            List<LoaiSanPham> list = lspDAO.selectByKeyword(keyword);
            for (LoaiSanPham lsp : list) {
                Object[] row = {
                    lsp.getMaLSP(),
                    lsp.getTenLSP(),
                    lsp.getMoTa(),};
                tblModel.addRow(row);
            }
        } catch (Exception e) {
        }
    }

    void insertLSP() {
        LoaiSanPham lsp = getFormLSP();
        try {
            lspDAO.insert(lsp);
            fillToTableLSP();
            clearFormLSP();
            MsgBox.alert(this, "Thêm mới thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Thêm mới thất bại!");
        }
    }

    LoaiSanPham getFormLSP() {
        LoaiSanPham lsp = new LoaiSanPham();
        lsp.setTenLSP(txtTenLSP.getText());
        lsp.setMoTa(txtMoTaLSP.getText());
        return lsp;
    }

    void first() {
        rowSP = 0;
        editSP();
    }

    void prev() {
        if (rowSP > 0) {
            rowSP--;
            editSP();
        }
    }

    void next() {
        if (rowSP < tblSanPham.getRowCount() - 1) {
            rowSP++;
            editSP();
        }
    }

    void last() {
        rowSP = tblSanPham.getRowCount() - 1;
        editSP();
    }

    boolean validateFormLSP() {
        if (txtTenLSP.getText().trim().isEmpty()) {
            MsgBox.alert(this, "Chưa nhập tên sản phẩm!");
            return false;
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabs = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtTimKiemSP = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        btnTimKiemSP = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        btnThemSP = new javax.swing.JButton();
        btnSuaSP = new javax.swing.JButton();
        btnXoaSP = new javax.swing.JButton();
        btnMoiSP = new javax.swing.JButton();
        btnDauSP = new javax.swing.JButton();
        btnSauSP = new javax.swing.JButton();
        btnTruocSP = new javax.swing.JButton();
        btnCuoiSP = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        lblHinh = new javax.swing.JLabel();
        txtTenSP = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        txtDonGia = new javax.swing.JTextField();
        cboLoaiSanPham = new javax.swing.JComboBox<>();
        cboNhaCungCap = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtMoTaSP = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtTenLSP = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtMoTaLSP = new javax.swing.JTextArea();
        jLabel16 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        btnThemLoaiSP = new javax.swing.JButton();
        btnSuaLSP = new javax.swing.JButton();
        btnXoaLSP = new javax.swing.JButton();
        btnMoiLSP = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        txtTimKiemLSP = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblLoaiSanPham = new javax.swing.JTable();
        btnTimKiemLSP = new javax.swing.JButton();

        setBackground(new java.awt.Color(0, 204, 255));

        tabs.setBackground(new java.awt.Color(0, 153, 255));
        tabs.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel2.setBackground(new java.awt.Color(0, 204, 255));

        jPanel1.setBackground(new java.awt.Color(0, 204, 255));

        jPanel4.setBackground(new java.awt.Color(0, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ SP", "TÊN SP", "SỐ LƯỢNG", "ĐƠN GIÁ", "Hình Ảnh", "MÔ TẢ", "MÃ LSP", "MÃ NCC", "MÃ NV"
            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblSanPhamMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);

        btnTimKiemSP.setBackground(new java.awt.Color(0, 153, 255));
        btnTimKiemSP.setText("Tìm kiếm");
        btnTimKiemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnTimKiemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimKiemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiemSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiemSP))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(0, 204, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnThemSP.setBackground(new java.awt.Color(0, 153, 255));
        btnThemSP.setText("Thêm");
        btnThemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSPActionPerformed(evt);
            }
        });

        btnSuaSP.setBackground(new java.awt.Color(0, 153, 255));
        btnSuaSP.setText("Sửa");
        btnSuaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaSPActionPerformed(evt);
            }
        });

        btnXoaSP.setBackground(new java.awt.Color(0, 153, 255));
        btnXoaSP.setText("Xóa");
        btnXoaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSPActionPerformed(evt);
            }
        });

        btnMoiSP.setBackground(new java.awt.Color(0, 153, 255));
        btnMoiSP.setText("Mới");
        btnMoiSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiSPActionPerformed(evt);
            }
        });

        btnDauSP.setBackground(new java.awt.Color(0, 153, 255));
        btnDauSP.setText("|<");
        btnDauSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDauSPActionPerformed(evt);
            }
        });

        btnSauSP.setBackground(new java.awt.Color(0, 153, 255));
        btnSauSP.setText("<<");
        btnSauSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSauSPActionPerformed(evt);
            }
        });

        btnTruocSP.setBackground(new java.awt.Color(0, 153, 255));
        btnTruocSP.setText(">>");
        btnTruocSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTruocSPActionPerformed(evt);
            }
        });

        btnCuoiSP.setBackground(new java.awt.Color(0, 153, 255));
        btnCuoiSP.setText(">|");
        btnCuoiSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCuoiSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThemSP, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                    .addComponent(btnCuoiSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSuaSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoaSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMoiSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDauSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTruocSP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSauSP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnThemSP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSuaSP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXoaSP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMoiSP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDauSP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTruocSP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSauSP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCuoiSP)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.setBackground(new java.awt.Color(0, 204, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setText("Mã sản phẩm");

        txtMaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaSPActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel24.setText("Hình");

        lblHinh.setBackground(new java.awt.Color(255, 255, 255));
        lblHinh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 255)));
        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblHinhMousePressed(evt);
            }
        });

        txtTenSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenSPActionPerformed(evt);
            }
        });

        txtSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoLuongActionPerformed(evt);
            }
        });

        txtDonGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDonGiaActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setText("Tên sản phẩm");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setText("Số lượng");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setText("Đơn giá");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setText("Loại sản phẩm");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setText("Nhà cung cấp");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setText("Mô tả");

        txtMoTaSP.setColumns(20);
        txtMoTaSP.setRows(5);
        jScrollPane5.setViewportView(txtMoTaSP);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel25)
                        .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                        .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel26)
                    .addComponent(jLabel22)
                    .addComponent(jLabel29))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboNhaCungCap, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboLoaiSanPham, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDonGia)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenSP)
                    .addComponent(txtSoLuong))
                .addGap(41, 41, 41)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboLoaiSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboNhaCungCap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addContainerGap())))))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabs.addTab("SẢN PHẨM", jPanel2);

        jPanel3.setBackground(new java.awt.Color(0, 204, 255));

        jPanel7.setBackground(new java.awt.Color(0, 204, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Tên loại sản phẩm");

        txtTenLSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenLSPActionPerformed(evt);
            }
        });

        txtMoTaLSP.setColumns(20);
        txtMoTaLSP.setRows(5);
        jScrollPane3.setViewportView(txtMoTaLSP);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setText("Mô tả");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                    .addComponent(txtTenLSP))
                .addGap(64, 64, 64))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtTenLSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(0, 204, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnThemLoaiSP.setBackground(new java.awt.Color(0, 153, 255));
        btnThemLoaiSP.setText("Thêm");
        btnThemLoaiSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemLoaiSPActionPerformed(evt);
            }
        });

        btnSuaLSP.setBackground(new java.awt.Color(0, 153, 255));
        btnSuaLSP.setText("Sửa");
        btnSuaLSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaLSPActionPerformed(evt);
            }
        });

        btnXoaLSP.setBackground(new java.awt.Color(0, 153, 255));
        btnXoaLSP.setText("Xóa");
        btnXoaLSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaLSPActionPerformed(evt);
            }
        });

        btnMoiLSP.setBackground(new java.awt.Color(0, 153, 255));
        btnMoiLSP.setText("Mới");
        btnMoiLSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiLSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThemLoaiSP, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                    .addComponent(btnSuaLSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoaLSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMoiLSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnThemLoaiSP)
                .addGap(18, 18, 18)
                .addComponent(btnSuaLSP)
                .addGap(18, 18, 18)
                .addComponent(btnXoaLSP)
                .addGap(18, 18, 18)
                .addComponent(btnMoiLSP)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(0, 204, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh loại sản phẩm"));

        tblLoaiSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ LOẠI", "TÊN LOẠI", "MÔ TẢ"
            }
        ));
        tblLoaiSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblLoaiSanPhamMousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(tblLoaiSanPham);

        btnTimKiemLSP.setBackground(new java.awt.Color(0, 153, 255));
        btnTimKiemLSP.setText("Tìm kiếm");
        btnTimKiemLSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemLSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(btnTimKiemLSP, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimKiemLSP, javax.swing.GroupLayout.PREFERRED_SIZE, 706, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiemLSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiemLSP))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabs.addTab("LOẠI SẢN PHẨM", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 872, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabs))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSPActionPerformed
        // TODO add your handling code here:
        if (validateFormSP()) {
            insertSP();
        }

    }//GEN-LAST:event_btnThemSPActionPerformed

    private void btnSuaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaSPActionPerformed
        // TODO add your handling code here:
        if (validateFormSP()) {
            updateSP();
        }

    }//GEN-LAST:event_btnSuaSPActionPerformed

    private void btnXoaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSPActionPerformed
        // TODO add your handling code here:
        deleteSP();
    }//GEN-LAST:event_btnXoaSPActionPerformed

    private void btnDauSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDauSPActionPerformed
        // TODO add your handling code here:
        first();
    }//GEN-LAST:event_btnDauSPActionPerformed

    private void btnSauSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSauSPActionPerformed
        // TODO add your handling code here:
        prev();
    }//GEN-LAST:event_btnSauSPActionPerformed

    private void txtTenLSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenLSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenLSPActionPerformed

    private void btnThemLoaiSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemLoaiSPActionPerformed
        // TODO add your handling code here:
        if (validateFormLSP()) {
            insertLSP();
        }
    }//GEN-LAST:event_btnThemLoaiSPActionPerformed

    private void btnSuaLSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaLSPActionPerformed
        // TODO add your handling code here:
        if (validateFormLSP()) {
            updateLSP();
        }

    }//GEN-LAST:event_btnSuaLSPActionPerformed

    private void btnXoaLSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaLSPActionPerformed
        // TODO add your handling code here:
        deleteLSP();
    }//GEN-LAST:event_btnXoaLSPActionPerformed

    private void btnMoiLSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiLSPActionPerformed
        // TODO add your handling code here:
        clearFormLSP();
    }//GEN-LAST:event_btnMoiLSPActionPerformed

    private void txtMaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaSPActionPerformed

    private void txtTenSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenSPActionPerformed

    private void txtSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoLuongActionPerformed

    private void txtDonGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDonGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDonGiaActionPerformed

    private void tblLoaiSanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLoaiSanPhamMousePressed
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.rowLSP = tblLoaiSanPham.rowAtPoint(evt.getPoint());
            editLSP();
        }
    }//GEN-LAST:event_tblLoaiSanPhamMousePressed

    private void lblHinhMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMousePressed
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            chonAnh();
        }
    }//GEN-LAST:event_lblHinhMousePressed

    private void btnMoiSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiSPActionPerformed
        // TODO add your handling code here:
        clearFormSP();
    }//GEN-LAST:event_btnMoiSPActionPerformed

    private void tblSanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblSanPhamMousePressed

    private void btnTruocSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTruocSPActionPerformed
        // TODO add your handling code here:
        next();
    }//GEN-LAST:event_btnTruocSPActionPerformed

    private void btnCuoiSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCuoiSPActionPerformed
        // TODO add your handling code here:
        last();
    }//GEN-LAST:event_btnCuoiSPActionPerformed

    private void btnTimKiemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemSPActionPerformed
        // TODO add your handling code here:
        fillToTableSP();
    }//GEN-LAST:event_btnTimKiemSPActionPerformed

    private void btnTimKiemLSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemLSPActionPerformed
        // TODO add your handling code here:
        fillToTableLSP();
    }//GEN-LAST:event_btnTimKiemLSPActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // TODO add your handling code here:
        try {
            if (evt.getClickCount() == 1) {
                this.rowSP = tblSanPham.rowAtPoint(evt.getPoint());
                editSP();
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi chọn!");
        }
    }//GEN-LAST:event_tblSanPhamMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCuoiSP;
    private javax.swing.JButton btnDauSP;
    private javax.swing.JButton btnMoiLSP;
    private javax.swing.JButton btnMoiSP;
    private javax.swing.JButton btnSauSP;
    private javax.swing.JButton btnSuaLSP;
    private javax.swing.JButton btnSuaSP;
    private javax.swing.JButton btnThemLoaiSP;
    private javax.swing.JButton btnThemSP;
    private javax.swing.JButton btnTimKiemLSP;
    private javax.swing.JButton btnTimKiemSP;
    private javax.swing.JButton btnTruocSP;
    private javax.swing.JButton btnXoaLSP;
    private javax.swing.JButton btnXoaSP;
    private javax.swing.JComboBox<String> cboLoaiSanPham;
    private javax.swing.JComboBox<String> cboNhaCungCap;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblLoaiSanPham;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextArea txtMoTaLSP;
    private javax.swing.JTextArea txtMoTaSP;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenLSP;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTimKiemLSP;
    private javax.swing.JTextField txtTimKiemSP;
    // End of variables declaration//GEN-END:variables
}
