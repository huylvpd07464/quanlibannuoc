/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan.qlbn.dao;

import duan.qlbn.entity.SanPham;
import duan.qlbn.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class SanPhamDAO extends QLBNDAO<SanPham, String> {

    final String INSERT_SQL = "INSERT INTO SanPham (MaSP, TenSP, SoLuong, DonGia,HinhAnh,MoTa,MaLSP,MaNCC,MaNV) VALUES (?,?,?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE SanPham SET TenSP=?, SoLuong=?, DonGia=?, HinhAnh=?, MoTa=?, MaLSP=? ,MaNCC=?, MaNV=? WHERE MaSP=?";
    final String DELETE_SQL = "DELETE FROM SanPham WHERE MaSP=?";
    final String SELECT_ALL_SQL = "SELECT * FROM SanPham";
    final String SELECT_BY_ID_SQL = "SELECT * FROM SanPham WHERE MaSP=?";
    final String UPDATESoLuongSp_SQL = "UPDATE SanPham SET SoLuong=? WHERE MaSP=?";

    public List<SanPham> selectByKeyword(String keyword) {
        String sql = "SELECT * FROM SanPham WHERE TenSP LIKE ? or MaSP = ?";
        return selectBySql(sql, "%" + keyword + "%", keyword);
    }

    @Override
    public void insert(SanPham entity) {
        JdbcHelper.update(INSERT_SQL, entity.getMaSP(), entity.getTenSP(), entity.getSoLuong(), entity.getDonGia(), entity.getHinhAnh(), entity.getMoTa(), entity.getMaLSP(), entity.getMaNCC(), entity.getMaNV());
    }

    @Override
    public void update(SanPham entity) {
        JdbcHelper.update(UPDATE_SQL, entity.getTenSP(), entity.getSoLuong(), entity.getDonGia(), entity.getHinhAnh(), entity.getMoTa(), entity.getMaLSP(), entity.getMaNCC(), entity.getMaNV(), entity.getMaSP());
    }

    public void updateSoLuongSP(int soLuongSP, String maSP) {
        JdbcHelper.update(UPDATESoLuongSp_SQL, soLuongSP, maSP);
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<SanPham> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public SanPham selectByID(String id) {
        List<SanPham> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<SanPham> selectBySql(String sql, Object... args) {
        List<SanPham> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                SanPham entity = new SanPham();
                entity.setMaSP(rs.getString("MaSP"));
                entity.setTenSP(rs.getString("TenSP"));
                entity.setSoLuong(rs.getInt("SoLuong"));
                entity.setDonGia(rs.getDouble("DonGia"));
                entity.setHinhAnh(rs.getString("HinhAnh"));
                entity.setMoTa(rs.getString("MoTa"));
                entity.setMaLSP(rs.getInt("MaLSP"));
                entity.setMaNCC(rs.getString("MaNCC"));
                entity.setMaNV(rs.getString("MaNV"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
