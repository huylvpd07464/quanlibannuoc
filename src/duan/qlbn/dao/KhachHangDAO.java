/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan.qlbn.dao;

import duan.qlbn.entity.KhachHang;
import duan.qlbn.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class KhachHangDAO extends QLBNDAO<KhachHang, String> {

    final String INSERT_SQL = "INSERT INTO KhachHang (MaKH, TenKH, NgaySinh, DienThoai, Email,NgayDK,GhiChu) VALUES (?, ?, ?, ?,?,?,?)";
    final String UPDATE_SQL = "UPDATE KhachHang SET TenKH=?, NgaySinh=?, DienThoai=?, Email=?, NgayDK=?, GhiChu=? WHERE MaKH=?";
    final String DELETE_SQL = "DELETE FROM KhachHang WHERE MaKH=?";
    final String SELECT_ALL_SQL = "SELECT * FROM KhachHang";
    final String SELECT_BY_ID_SQL = "SELECT * FROM KhachHang WHERE MaKH=?";


    public List<KhachHang> selectByKeyword(String keyword) {
        String sql = "SELECT * FROM KhachHang WHERE TenKH LIKE ? or MaKH = ?";
        return selectBySql(sql, "%" + keyword + "%", keyword);
    }

    @Override
    public void insert(KhachHang entity) {
        JdbcHelper.update(INSERT_SQL, entity.getMaKH(), entity.getTenKH(), entity.getNgaySinh(), entity.getDienThoai(), entity.getEmail(), entity.getNgayDangKy(), entity.getGhiChu());
    }

    @Override
    public void update(KhachHang entity) {
        JdbcHelper.update(UPDATE_SQL, entity.getTenKH(), entity.getNgaySinh(), entity.getDienThoai(), entity.getEmail(), entity.getNgayDangKy(), entity.getGhiChu(), entity.getMaKH());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<KhachHang> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public KhachHang selectByID(String id) {
        List<KhachHang> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<KhachHang> selectBySql(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                KhachHang entity = new KhachHang();
                entity.setMaKH(rs.getString("MaKH"));
                entity.setTenKH(rs.getString("TenKH"));
                entity.setNgaySinh(rs.getDate("NgaySinh"));
                entity.setDienThoai(rs.getString("DienThoai"));
                entity.setEmail(rs.getString("Email"));
                entity.setNgayDangKy(rs.getDate("NgayDK"));
                entity.setGhiChu(rs.getString("GhiChu"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
