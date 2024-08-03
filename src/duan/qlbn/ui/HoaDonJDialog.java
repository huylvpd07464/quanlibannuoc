/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package duan.qlbn.ui;

import duan.qlbn.dao.HoaDonChiTietDAO;
import duan.qlbn.dao.HoaDonDAO;
import duan.qlbn.dao.KhachHangDAO;
import duan.qlbn.dao.SanPhamDAO;
import duan.qlbn.entity.HoaDon;
import duan.qlbn.entity.HoaDonChiTiet;
import duan.qlbn.entity.KhachHang;
import duan.qlbn.entity.MyCombobox;
import duan.qlbn.entity.SanPham;
import duan.qlbn.utils.Auth;
import duan.qlbn.utils.MsgBox;
import duan.qlbn.utils.XDate;
import java.awt.Frame;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class HoaDonJDialog extends javax.swing.JDialog {

    KhachHangDAO khDAO = new KhachHangDAO();
    SanPhamDAO spDAO = new SanPhamDAO();
    HoaDonDAO hdDAO = new HoaDonDAO();
    HoaDonChiTietDAO hdctDAO = new HoaDonChiTietDAO();
    int rowSP = -1;
    int rowCTHD = -1;

    /**
     * Creates new form DSHoaDonJDialog
     */
    public HoaDonJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        fillTableSanPham();
        fillComboboxKhachHang();
        txtNgayMua.setText(XDate.toString(XDate.now(), "yyyy-MM-dd"));
        txtMaHD.setEditable(false);
        txtTongTienHang.setEditable(false);
        txtTienThua.setEditable(false);
    }

    void capNhatHoaDonChiTiet() {
        try {
            //Lỗi đây
            int soLuongNhapHDCT = Integer.parseInt(MsgBox.prompt(this, "Nhập số lượng sản phẩm"));
            String maSP = (String) tblChiTietHoaDon.getValueAt(rowCTHD, 1);
            int maHDCT = hdctDAO.selectMaHDCT(Integer.parseInt(txtMaHD.getText()), maSP).getMaHDCT();
            HoaDonChiTiet hdct = hdctDAO.selectByID(String.valueOf(maHDCT));
            int soLuongDaCoHDCT = hdct.getSoLuong();
            
            SanPham sp = spDAO.selectByID(String.valueOf(hdct.getMaSP()));

            if (soLuongNhapHDCT == 0) {
                spDAO.updateSoLuongSP(sp.getSoLuong() + hdct.getSoLuong(), hdct.getMaSP());
                hdctDAO.delete(String.valueOf(maHDCT));

                fillTableHoaDonChiTiet();
                fillTableSanPham();
            } else if (soLuongNhapHDCT > soLuongDaCoHDCT) {
                int soLuongThem = soLuongNhapHDCT - soLuongDaCoHDCT;
                if (sp.getSoLuong() < soLuongThem) {
                    MsgBox.alert(this, "Không đủ sản phẩm!");
                } else {
                    spDAO.updateSoLuongSP(sp.getSoLuong() - soLuongThem, sp.getMaSP());
                    hdctDAO.updateSoLuong(soLuongNhapHDCT, maHDCT);
                    fillTableHoaDonChiTiet();
                    fillTableSanPham();
                }
            } else {
                int soLuongGiam = soLuongDaCoHDCT - soLuongNhapHDCT;
                spDAO.updateSoLuongSP(sp.getSoLuong() + soLuongGiam, sp.getMaSP());
                hdctDAO.updateSoLuong(soLuongNhapHDCT, maHDCT);

                fillTableHoaDonChiTiet();
                fillTableSanPham();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void huyHoaDon() {
        try {
            List<HoaDonChiTiet> list = hdctDAO.selectByHoaDon(Integer.parseInt(txtMaHD.getText()));
            for (HoaDonChiTiet hdct : list) {
                int soLuongDaNhap = hdct.getSoLuong();
                int soLuongConLai = spDAO.selectByID(hdct.getMaSP()).getSoLuong();
                int soLuongSP = soLuongDaNhap + soLuongConLai;
                spDAO.updateSoLuongSP(soLuongSP, hdct.getMaSP());
                hdctDAO.deleteByInt(hdct.getMaHDCT());
            }
            hdDAO.deleteByInt(Integer.parseInt(txtMaHD.getText()));
            clearForm();
            fillTableSanPham();
            MsgBox.alert(this, "Hủy hóa đơn thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Hủy hóa đơn thất bại!");
        }
    }

    void thanhToan() {
        try {
            hdDAO.updateTrangThai(true, Integer.parseInt(txtMaHD.getText()));
            MsgBox.alert(this, "Thanh toán thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Thanh toán thất bại!");
        }
    }

    boolean isValidDate(String inputDate, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);
        try {
            Date date = sdf.parse(inputDate);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    boolean validateForm() {
        if (txtMaHD.getText().trim().isEmpty()) {
            MsgBox.alert(this, "Chưa có hóa đơn!");
            return false;
        }
        if (txtNgayMua.getText().trim().isEmpty()) {
            MsgBox.alert(this, "Chưa có ngày mua!");
            return false;
        }
        
        if (isValidDate(txtNgayMua.getText(), "yyyy-MM-dd") != true) {
            MsgBox.alert(this, "Ngày mua không đúng định dạng(yyyy-MM-dd)!");
            txtNgayMua.requestFocus();
            return false;
        }
        if (txtTongTienHang.getText().trim().isEmpty()) {
            MsgBox.alert(this, "Chưa có sản phẩm!");
            return false;
        }
        if (txtTienThanhToan.getText().trim().isEmpty()) {
            MsgBox.alert(this, "Chưa nhập tiền thanh toán!");
            return false;
        }
        if (txtTienThanhToan.getText().matches("[0-9]+") == false) {
            MsgBox.alert(this, "Chỉ cho nhập số!");
            txtTienThanhToan.requestFocus();
            return false;
        }
        if (Double.parseDouble(txtTienThanhToan.getText()) < Double.parseDouble(txtTongTienHang.getText())) {
            MsgBox.alert(this, "Không đủ tiền thanh toán!");
            return false;
        }
        return true;
    }

    void themSanPhamVaoHoaDon() {
        try {
            HoaDonChiTiet hdct = new HoaDonChiTiet();
            int soLuongSPNhap = Integer.parseInt(MsgBox.prompt(this, "Nhập số lượng"));

            SanPham sp = spDAO.selectByID((String) tblSanPham.getValueAt(rowSP, 0));
            boolean kiemTraSP = false;
            List<HoaDonChiTiet> listHDCT = hdctDAO.selectAllHDCTByMaHD(Integer.parseInt(txtMaHD.getText()));
            for (HoaDonChiTiet hoaDonChiTiet : listHDCT) {
                if (hoaDonChiTiet.getMaSP().equals(sp.getMaSP())) {
                    kiemTraSP = true;
                }
            }

            if (kiemTraSP) {
                MsgBox.alert(this, "Sản phẩm đã có trong hóa đơn!");
                kiemTraSP = false;
            } else {
                if (soLuongSPNhap > sp.getSoLuong()) {
                    MsgBox.alert(this, "Không đủ sản phẩm!");
                } else {
                    hdct.setSoLuong(soLuongSPNhap);
                    hdct.setMaHD(Integer.parseInt(txtMaHD.getText()));
                    hdct.setMaSP((String) tblSanPham.getValueAt(rowSP, 0));
                    hdctDAO.insert(hdct);

                    int soLuongSPConLai = sp.getSoLuong() - soLuongSPNhap;
                    spDAO.updateSoLuongSP(soLuongSPConLai, (String) tblSanPham.getValueAt(rowSP, 0));

                    MsgBox.alert(this, "Thêm sản phẩm thành công");
                    fillTableHoaDonChiTiet();
                    fillTableSanPham();
                }

            }
        } catch (Exception e) {
        }
    }

    void fillTableHoaDonChiTiet() {
        DefaultTableModel model = (DefaultTableModel) tblChiTietHoaDon.getModel();
        model.setRowCount(0);

        try {
            List<HoaDonChiTiet> list = hdctDAO.selectByHoaDon(Integer.parseInt(txtMaHD.getText()));
            double tongTien = 0;
            for (int i = 0; i < list.size(); i++) {
                HoaDonChiTiet hdct = list.get(i);
                String tenSP = spDAO.selectByID(hdct.getMaSP()).getTenSP();
                double donGia = spDAO.selectByID(hdct.getMaSP()).getDonGia();
                Object[] row = {
                    i + 1, hdct.getMaSP(), tenSP, hdct.getSoLuong(), donGia
                };
                model.addRow(row);

                tongTien += (hdct.getSoLuong() * donGia);
                txtTongTienHang.setText(String.valueOf(tongTien));
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Hiển thị hóa đơn chi tiết thất bại");
        }
    }

    void taoHoaDon() {
        HoaDon hd = getForm();
        try {
            hdDAO.insert(hd);
            MsgBox.alert(this, "Tạo hóa đơn thành công!");
            btnThanhToan.setEnabled(true);
            btnHuyDon.setEnabled(true);
        } catch (Exception e) {
            MsgBox.alert(this, "Tạo hóa đơn thất bại!");
        }
    }

    HoaDon getForm() {
        HoaDon hd = new HoaDon();
        hd.setNgayMua(XDate.toDate(txtNgayMua.getText(), "yyyy-MM-dd"));
        hd.setTrangThai(false);
        MyCombobox KHDuocChon = (MyCombobox) cboKhachHang.getSelectedItem();
        hd.setMaKH(KHDuocChon.MaString());
        hd.setMaNV(Auth.user.getMaNV());
        return hd;
    }

    void clearForm() {
        txtMaHD.setText("");
        cboKhachHang.setSelectedIndex(0);
        DefaultTableModel model = (DefaultTableModel) tblChiTietHoaDon.getModel();
        model.setRowCount(0);
        txtTongTienHang.setText("");
        txtTienThanhToan.setText("");
        txtTienThua.setText("");
        rowSP = -1;
    }

    void setForm(HoaDon model) {
        txtMaHD.setText(String.valueOf(model.getMaHD()));
        txtNgayMua.setText(XDate.toString(model.getNgayMua(), "yyyy-MM-dd"));
        int dem = 0;
        // Hiển thị khách hàng của hóa đơn ở combobox khách hàng
        try {
            List<KhachHang> list = khDAO.selectAll();
            for (KhachHang kh : list) {
                if (kh.getMaKH().equals(model.getMaKH())) {
                    break;
                }
                dem++;
            }
        } catch (Exception e) {
        }
        cboKhachHang.setSelectedIndex(dem);
    }

    void selectComboBox() {
        MyCombobox KHDuocChon = (MyCombobox) cboKhachHang.getSelectedItem();
        txtDienThoai.setText(khDAO.selectByID(KHDuocChon.MaString()).getDienThoai());
    }

    void fillComboboxKhachHang() {
        DefaultComboBoxModel cboModelKH = (DefaultComboBoxModel) cboKhachHang.getModel();
        cboModelKH.removeAllElements();
        try {
            List<KhachHang> list = khDAO.selectAll();
            for (KhachHang kh : list) {
                MyCombobox mycbb = new MyCombobox(kh.getMaKH(), kh.getTenKH());
                cboModelKH.addElement(mycbb);
            }
        } catch (Exception e) {
        }
    }

    void fillTableSanPham() {
        DefaultTableModel tblModelSP = (DefaultTableModel) tblSanPham.getModel();
        tblModelSP.setRowCount(0);

        try {
            List<SanPham> list = spDAO.selectAll();
            for (SanPham sp : list) {
                Object[] row = {
                    sp.getMaSP(),
                    sp.getTenSP(),
                    sp.getSoLuong(),
                    sp.getDonGia(),
                    sp.getHinhAnh(),
                    sp.getMoTa(),};
                tblModelSP.addRow(row);
            }
        } catch (Exception e) {
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

        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblChiTietHoaDon = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnTaoMoi = new javax.swing.JButton();
        btnThanhToan = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnHuyDon = new javax.swing.JButton();
        btnTaoHD = new javax.swing.JButton();
        cboKhachHang = new javax.swing.JComboBox<>();
        txtTongTienHang = new javax.swing.JTextField();
        txtDienThoai = new javax.swing.JTextField();
        txtTienThanhToan = new javax.swing.JTextField();
        txtTienThua = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtNgayMua = new javax.swing.JTextField();
        txtMaHD = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(0, 204, 255));

        jPanel6.setBackground(new java.awt.Color(0, 204, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Chi tiết hóa đơn"));

        tblChiTietHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã SP", "Tên SP", "Số lượng", "Đơn giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblChiTietHoaDon.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tblChiTietHoaDonAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        tblChiTietHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChiTietHoaDonMouseClicked(evt);
            }
        });
        tblChiTietHoaDon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblChiTietHoaDonKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tblChiTietHoaDon);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel9.setBackground(new java.awt.Color(0, 204, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Sản phẩm"));

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã SP", "Tên SP", "Số lượng", "Đơn giá", "Hình Ảnh", "Mô Tả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblSanPhamMousePressed(evt);
            }
        });
        jScrollPane7.setViewportView(tblSanPham);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(0, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Tạo hóa đơn"));

        btnTaoMoi.setBackground(new java.awt.Color(0, 153, 255));
        btnTaoMoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTaoMoi.setText("Tạo mới");
        btnTaoMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoMoiActionPerformed(evt);
            }
        });

        btnThanhToan.setBackground(new java.awt.Color(0, 153, 255));
        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        jLabel1.setText("Tên khách hàng");

        jLabel2.setText("Điện thoại");

        jLabel3.setText("Tổng tiền hàng");

        jLabel4.setText("Tiền thanh toán");

        jLabel5.setText("Tiền thừa");

        btnHuyDon.setBackground(new java.awt.Color(0, 153, 255));
        btnHuyDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHuyDon.setText("Hủy đơn");
        btnHuyDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyDonActionPerformed(evt);
            }
        });

        btnTaoHD.setBackground(new java.awt.Color(0, 153, 255));
        btnTaoHD.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTaoHD.setText("Tạo hóa đơn");
        btnTaoHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHDActionPerformed(evt);
            }
        });

        cboKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboKhachHangActionPerformed(evt);
            }
        });

        txtTongTienHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTongTienHangActionPerformed(evt);
            }
        });

        txtDienThoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDienThoaiActionPerformed(evt);
            }
        });

        txtTienThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienThanhToanActionPerformed(evt);
            }
        });
        txtTienThanhToan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienThanhToanKeyReleased(evt);
            }
        });

        txtTienThua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienThuaActionPerformed(evt);
            }
        });

        jLabel7.setText("Ngày mua");

        txtNgayMua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayMuaActionPerformed(evt);
            }
        });

        txtMaHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaHDActionPerformed(evt);
            }
        });

        jLabel6.setText("Mã HĐ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThanhToan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTaoHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtDienThoai)
                                    .addComponent(txtMaHD)
                                    .addComponent(cboKhachHang, 0, 144, Short.MAX_VALUE)
                                    .addComponent(txtNgayMua)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(19, 19, 19)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTienThanhToan, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtTongTienHang)
                                    .addComponent(txtTienThua))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnHuyDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTaoMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cboKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNgayMua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTongTienHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTienThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
                .addComponent(btnTaoHD)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnThanhToan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnHuyDon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTaoMoi)
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTaoMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoMoiActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnTaoMoiActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        // TODO add your handling code here:
        if (validateForm()) {
            thanhToan();
            //In hóa đơn
            DefaultTableModel tblHDCT = (DefaultTableModel) tblChiTietHoaDon.getModel();
            String tongTienHang = txtTongTienHang.getText();
            String tienThanhToan = txtTienThanhToan.getText();
            String tienThua = txtTienThua.getText();
            int soHang = tblHDCT.getRowCount();
            InHoaDonJDialog ihdjd = new InHoaDonJDialog((Frame) SwingUtilities.getWindowAncestor(this), true);
            ihdjd.bangHoaDon(tblHDCT, soHang, tongTienHang, tienThanhToan, tienThua);
            ihdjd.setVisible(true);
            clearForm();
        }
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void btnHuyDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyDonActionPerformed
        // TODO add your handling code here:
        if (txtMaHD.getText().equals("")) {
            MsgBox.alert(this, "Chưa chọn đơn hàng!");
        } else {
            huyHoaDon();
        }
        this.dispose();
    }//GEN-LAST:event_btnHuyDonActionPerformed

    private void btnTaoHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHDActionPerformed
        // TODO add your handling code here:
        if (txtMaHD.getText().equals("")) {
            taoHoaDon();
            int index = hdDAO.selectAll().size() - 1;
            List list = hdDAO.selectAll();
            HoaDon hd = (HoaDon) list.get(index);
            txtMaHD.setText(String.valueOf(hd.getMaHD()));
        } else {
            MsgBox.alert(this, "Hóa đơn đã tồn tại!");
        }
    }//GEN-LAST:event_btnTaoHDActionPerformed

    private void cboKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboKhachHangActionPerformed
        // TODO add your handling code here:
        selectComboBox();
    }//GEN-LAST:event_cboKhachHangActionPerformed

    private void txtTongTienHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongTienHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTongTienHangActionPerformed

    private void txtDienThoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDienThoaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDienThoaiActionPerformed

    private void txtTienThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienThanhToanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienThanhToanActionPerformed

    private void txtTienThuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienThuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienThuaActionPerformed

    private void txtNgayMuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayMuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayMuaActionPerformed

    private void txtMaHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaHDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaHDActionPerformed

    private void tblSanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblSanPhamMousePressed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // TODO add your handling code here:
        if (txtMaHD.getText().equals("")) {
            MsgBox.alert(this, "Chưa có hóa đơn!");
        } else {
            if (evt.getClickCount() == 1) {
                this.rowSP = tblSanPham.rowAtPoint(evt.getPoint());
                themSanPhamVaoHoaDon();
            }
        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void tblChiTietHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiTietHoaDonMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            rowCTHD = tblChiTietHoaDon.rowAtPoint(evt.getPoint());
            capNhatHoaDonChiTiet();
        }
    }//GEN-LAST:event_tblChiTietHoaDonMouseClicked

    private void tblChiTietHoaDonAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tblChiTietHoaDonAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_tblChiTietHoaDonAncestorAdded

    private void txtTienThanhToanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienThanhToanKeyReleased
        // TODO add your handling code here:
        try {
            txtTienThua.setText(String.valueOf(Double.parseDouble(txtTienThanhToan.getText()) - Double.parseDouble(txtTongTienHang.getText())));
        } catch (Exception e) {
        }
    }//GEN-LAST:event_txtTienThanhToanKeyReleased

    private void tblChiTietHoaDonKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblChiTietHoaDonKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_tblChiTietHoaDonKeyReleased

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
            java.util.logging.Logger.getLogger(HoaDonJDialog.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HoaDonJDialog.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HoaDonJDialog.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HoaDonJDialog.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                HoaDonJDialog dialog = new HoaDonJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnHuyDon;
    private javax.swing.JButton btnTaoHD;
    private javax.swing.JButton btnTaoMoi;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JComboBox<String> cboKhachHang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTable tblChiTietHoaDon;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtDienThoai;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtNgayMua;
    private javax.swing.JTextField txtTienThanhToan;
    private javax.swing.JTextField txtTienThua;
    private javax.swing.JTextField txtTongTienHang;
    // End of variables declaration//GEN-END:variables

    void setEditData(HoaDon hd) {
        setForm(hd);
        selectComboBox();
        fillTableHoaDonChiTiet();
    }

}
