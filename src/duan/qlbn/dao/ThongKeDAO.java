/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan.qlbn.dao;

import duan.qlbn.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author HP
 */
public class ThongKeDAO {

    private List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object[]> getDoanhThuNgayTrongThang() {
        String sql = "{CALL sp_ThongKeDoanhThuNgay}";
        String[] cols = {"NgayMua", "DoanhThu"};
        return getListOfArray(sql, cols);
    }
    
    public List<Object[]> getLuongSanPhamNam(String nam) {
        String sql = "{CALL sp_LuongSanPhamNam(?)}";
        String[] cols = {"LuongSanPham"};
        return getListOfArray(sql, cols, nam);
    }
    public List<Object[]> getLuongSanPhamThang(String thang) {
        String sql = "{CALL sp_LuongSanPhamThang(?)}";
        String[] cols = {"LuongSanPham"};
        return getListOfArray(sql, cols, thang);
    }
    public List<Object[]> getLuongSanPhamNgay(String ngay) {
        String sql = "{CALL sp_LuongSanPhamNgay(?)}";
        String[] cols = {"LuongSanPham"};
        return getListOfArray(sql, cols, ngay);
    }
    public List<Object[]> getLuongKhachNam(String nam) {
        String sql = "{CALL sp_LuongKhachTheoNam(?)}";
        String[] cols = {"SoKhachHang"};
        return getListOfArray(sql, cols, nam);
    }
    public List<Object[]> getLuongKhachThang(String thang) {
        String sql = "{CALL sp_LuongKhachTheoThang(?)}";
        String[] cols = {"SoKhachHang"};
        return getListOfArray(sql, cols, thang);
    }
    public List<Object[]> getLuongKhachNgay(String ngay) {
        String sql = "{CALL sp_LuongKhachTheoNgay(?)}";
        String[] cols = {"SoKhachHang"};
        return getListOfArray(sql, cols, ngay);
    }

    public List<Object[]> getLuongHoaDonNam(String nam) {
        String sql = "{CALL sp_LuongHoaDonTheoNam(?)}";
        String[] cols = {"SoHoaDon"};
        return getListOfArray(sql, cols, nam);
    }

    public List<Object[]> getLuongHoaDonThang(String thang) {
        String sql = "{CALL sp_LuongHoaDonTheoThang(?)}";
        String[] cols = {"SoHoaDon"};
        return getListOfArray(sql, cols, thang);
    }

    public List<Object[]> getLuongHoaDonNgay(String ngay) {
        String sql = "{CALL sp_LuongHoaDonTheoNgay(?)}";
        String[] cols = {"SoHoaDon"};
        return getListOfArray(sql, cols, ngay);
    }

    public List<Object[]> getDoanhThuNgay(String ngay) {
        String sql = "{CALL sp_DoanhThuTheoNgay(?)}";
        String[] cols = {"DoanhThu"};
        return getListOfArray(sql, cols, ngay);
    }

    public List<Object[]> getDoanhThuThang(String thang) {
        String sql = "{CALL sp_DoanhThuTheoThang(?)}";
        String[] cols = {"DoanhThu"};
        return getListOfArray(sql, cols, thang);
    }

    public List<Object[]> getDoanhThuNam(String nam) {
        String sql = "{CALL sp_DoanhThuTheoNam(?)}";
        String[] cols = {"DoanhThu"};
        return getListOfArray(sql, cols, nam);
    }
}
