/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan.qlbn.dao;

import duan.qlbn.entity.LoaiSanPham;
import duan.qlbn.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class LoaiSanPhamDAO extends QLBNDAO<LoaiSanPham, String> {

    final String INSERT_SQL = "INSERT INTO LoaiSanPham (TenLSP,MoTa) VALUES (?,?)";
    final String UPDATE_SQL = "UPDATE LoaiSanPham SET TenLSP=?, MoTa=? WHERE MaLSP=?";
    final String DELETE_SQL = "DELETE FROM LoaiSanPham WHERE MaSP=?";
    final String SELECT_ALL_SQL = "SELECT * FROM LoaiSanPham";
    final String SELECT_BY_ID_SQL = "SELECT * FROM LoaiSanPham WHERE MaLSP=?";

    public List<LoaiSanPham> selectByKeyword(String keyword) {
        String sql = "SELECT * FROM LoaiSanPham WHERE TenLSP LIKE ? or MaLSP = ?";
        return selectBySql(sql, "%" + keyword + "%", keyword);
    }

    @Override
    public void insert(LoaiSanPham entity) {
        JdbcHelper.update(INSERT_SQL, entity.getTenLSP(), entity.getMoTa());
    }

    @Override
    public void update(LoaiSanPham entity) {
        JdbcHelper.update(UPDATE_SQL, entity.getTenLSP(), entity.getMoTa(), entity.getMaLSP());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<LoaiSanPham> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public LoaiSanPham selectByID(String id) {
        List<LoaiSanPham> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<LoaiSanPham> selectBySql(String sql, Object... args) {
        List<LoaiSanPham> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                LoaiSanPham entity = new LoaiSanPham();
                entity.setMaLSP(rs.getInt("MaLSP"));
                entity.setTenLSP(rs.getString("TenLSP"));
                entity.setMoTa(rs.getString("MoTa"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
