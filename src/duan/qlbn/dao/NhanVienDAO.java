/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan.qlbn.dao;

import duan.qlbn.entity.NhanVien;
import duan.qlbn.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class NhanVienDAO extends QLBNDAO<NhanVien, String> {

    final String INSERT_SQL = "INSERT INTO NhanVien (MaNV, MatKhau,email, TenNV, VaiTro, TrangThai) VALUES (?, ?,?, ?, ?,?)";
    final String UPDATE_SQL = "UPDATE NhanVien SET MatKhau=?,email=?, TenNV=?, VaiTro=?, TrangThai=? WHERE MaNV=?";
    final String DELETE_SQL = "DELETE FROM NhanVien WHERE MaNV=?";
    final String SELECT_ALL_SQL = "SELECT * FROM NhanVien";
    final String SELECT_BY_ID_SQL = "SELECT * FROM NhanVien WHERE MaNV=?";
    final String UPDATE_MatKhau_SQL = "UPDATE NhanVien SET MatKhau=? WHERE MaNV=?";

    public List<NhanVien> selectByKeyword(String keyword) {
        String sql = "SELECT * FROM NhanVien WHERE TenNV LIKE ? or MaNV = ?";
        return selectBySql(sql, "%" + keyword + "%", keyword);
    }

    @Override
    public void insert(NhanVien entity) {
        JdbcHelper.update(INSERT_SQL, entity.getMaNV(), entity.getMatKhau(), entity.getEmail(), entity.getHoTen(), entity.isVaiTro(), entity.isTrangThai());
    }

    @Override
    public void update(NhanVien entity) {
        JdbcHelper.update(UPDATE_SQL, entity.getMatKhau(), entity.getEmail(), entity.getHoTen(), entity.isVaiTro(), entity.isTrangThai(), entity.getMaNV());
    }
    public void updateMatKhau(String matKhau, String maNV) {
        JdbcHelper.update(UPDATE_MatKhau_SQL, matKhau, maNV);
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<NhanVien> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public NhanVien selectByID(String id) {
        List<NhanVien> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setMaNV(rs.getString("MaNV"));
                entity.setMatKhau(rs.getString("MatKhau"));
                entity.setEmail(rs.getString("email"));
                entity.setHoTen(rs.getString("TenNV"));
                entity.setVaiTro(rs.getBoolean("VaiTro"));
                entity.setTrangThai(rs.getBoolean("TrangThai"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
