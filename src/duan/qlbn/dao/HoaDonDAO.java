/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan.qlbn.dao;

import duan.qlbn.entity.HoaDon;
import duan.qlbn.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class HoaDonDAO extends QLBNDAO<HoaDon, String> {

    final String INSERT_SQL = "INSERT INTO HoaDon (NgayMua, TrangThai,MaKH,MaNV) VALUES (?, ?, ?,?)";
    final String UPDATE_SQL = "UPDATE HoaDon SET NgayMua=?, TrangThai=?, MaKH=?, MaNV=? WHERE MaHD=?";
    final String DELETE_SQL = "DELETE FROM HoaDon WHERE MaHD=?";
    final String SELECT_ALL_SQL = "SELECT * FROM HoaDon";
    final String SELECT_BY_ID_SQL = "SELECT * FROM HoaDon WHERE MaHD=?";
    final String UPDATE_TrangThai_SQL = "UPDATE HoaDon SET TrangThai=? WHERE MaHD=?";

    public List<Object[]> getHoaDon(String tenKH) {
        String sql = "{CALL sp_DanhSachHD(?)}";
        String[] cols = {"MaHD", "TenNV", "TenKH", "NgayMua", "TrangThai"};
        return getListOfArray(sql, cols, "%"+ tenKH+ "%");
    }

    @Override
    public void insert(HoaDon entity) {
        JdbcHelper.update(INSERT_SQL, entity.getNgayMua(), entity.isTrangThai(), entity.getMaKH(), entity.getMaNV());
    }

    @Override
    public void update(HoaDon entity) {
        JdbcHelper.update(UPDATE_SQL, entity.getNgayMua(), entity.isTrangThai(), entity.getMaKH(), entity.getMaNV(), entity.getMaHD());
    }

    public void updateTrangThai(boolean trangThai, int maHD) {
        JdbcHelper.update(UPDATE_TrangThai_SQL, trangThai, maHD);
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    public void deleteByInt(int id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<HoaDon> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public HoaDon selectByID(String id) {
        List<HoaDon> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public HoaDon selectByIDInt(int id) {
        List<HoaDon> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<HoaDon> selectBySql(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                HoaDon entity = new HoaDon();
                entity.setMaHD(rs.getInt("MaHD"));
                entity.setNgayMua(rs.getDate("NgayMua"));
                entity.setTrangThai(rs.getBoolean("TrangThai"));
                entity.setMaKH(rs.getString("MaKH"));
                entity.setMaNV(rs.getString("MaNV"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

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

}
