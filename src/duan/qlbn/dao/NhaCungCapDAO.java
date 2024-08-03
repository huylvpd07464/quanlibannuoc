/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan.qlbn.dao;

import duan.qlbn.entity.NhaCungCap;
import duan.qlbn.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class NhaCungCapDAO extends QLBNDAO<NhaCungCap, String> {

    final String INSERT_SQL = "INSERT INTO NhaCungCap (MaNCC, TenNCC, DienThoai, DiaChi,Email,GhiChu) VALUES (?, ?, ?, ?,?,?)";
    final String UPDATE_SQL = "UPDATE NhaCungCap SET TenNCC=?, DienThoai=?, DiaChi=?, Email=?, GhiChu=? WHERE MaNCC=?";
    final String DELETE_SQL = "DELETE FROM NhaCungCap WHERE MaNCC=?";
    final String SELECT_ALL_SQL = "SELECT * FROM NhaCungCap";
    final String SELECT_BY_ID_SQL = "SELECT * FROM NhaCungCap WHERE MaNCC=?";

    public List<NhaCungCap> selectByKeyword(String keyword) {
        String sql = "SELECT * FROM NhaCungCap WHERE TenNCC LIKE ? or MaNCC = ?";
        return selectBySql(sql, "%" + keyword + "%", keyword);
    }

    @Override
    public void insert(NhaCungCap entity) {
        JdbcHelper.update(INSERT_SQL, entity.getMaNCC(), entity.getTenNCC(), entity.getDienThoai(), entity.getDiaChi(), entity.getEmail(), entity.getGhiChu());
    }

    @Override
    public void update(NhaCungCap entity) {
        JdbcHelper.update(UPDATE_SQL, entity.getTenNCC(), entity.getDienThoai(), entity.getDiaChi(), entity.getEmail(), entity.getGhiChu(), entity.getMaNCC());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<NhaCungCap> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public NhaCungCap selectByID(String id) {
        List<NhaCungCap> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NhaCungCap> selectBySql(String sql, Object... args) {
        List<NhaCungCap> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                NhaCungCap entity = new NhaCungCap();
                entity.setMaNCC(rs.getString("MaNCC"));
                entity.setTenNCC(rs.getString("TenNCC"));
                entity.setDienThoai(rs.getString("DienThoai"));
                entity.setDiaChi(rs.getString("DiaChi"));
                entity.setEmail(rs.getString("Email"));
                entity.setGhiChu(rs.getString("GhiChu"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
