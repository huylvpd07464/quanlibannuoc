package duan.qlbn.dao;

import duan.qlbn.entity.HoaDonChiTiet;
import duan.qlbn.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class HoaDonChiTietDAO extends QLBNDAO<HoaDonChiTiet, String> {

    final String INSERT_SQL = "INSERT INTO HoaDonChiTiet (SoLuong, MaHD, MaSP) VALUES (?, ?, ?)";
    final String UPDATE_SQL = "UPDATE HoaDonChiTiet SET SoLuong=?, MaHD=?, MaSP=? WHERE MaHDCT=?";
    final String DELETE_SQL = "DELETE FROM HoaDonChiTiet WHERE MaHDCT=?";
    final String SELECT_ALL_SQL = "SELECT * FROM HoaDonChiTiet";
    final String SELECT_BY_ID_SQL = "SELECT * FROM HoaDonChiTiet WHERE MaHDCT=?";
    final String SELECT_BY_MA_HD_SQL = "SELECT * FROM HoaDonChiTiet WHERE MaHD=?";
    final String UPDATE_SoLuong_SQL = "UPDATE HoaDonChiTiet SET SoLuong=? WHERE MaHDCT=?";
    final String SELECT_MaHDCT_BY_MHD_MSP = "select hdct.MaHDCT, hdct.SoLuong, hdct.MaHD, hdct.MaSP from HoaDon hd inner join HoaDonChiTiet hdct on hd.MaHD = hdct.MaHD where hdct.MaHD = ? and hdct.MaSP = ?";
    final String SELECT_ALLHDCTBYMaHD_SQL = "select * from HoaDonChiTiet where MaHD = ?";
    
    public List<HoaDonChiTiet> selectAllHDCTByMaHD(int maHD) {
        return selectBySql(SELECT_ALLHDCTBYMaHD_SQL, maHD);
    }


    public HoaDonChiTiet selectMaHDCT(int maHD, String maSP) {
        List<HoaDonChiTiet> list = selectBySql(SELECT_MaHDCT_BY_MHD_MSP, maHD, maSP);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<HoaDonChiTiet> selectByHoaDon(int maKH) {
        return selectBySql(SELECT_BY_MA_HD_SQL, maKH);
    }

    @Override
    public void insert(HoaDonChiTiet entity) {
        JdbcHelper.update(INSERT_SQL, entity.getSoLuong(), entity.getMaHD(), entity.getMaSP());
    }

    @Override
    public void update(HoaDonChiTiet entity) {
        JdbcHelper.update(UPDATE_SQL, entity.getSoLuong(), entity.getMaHD(), entity.getMaSP(), entity.getMaHDCT());
    }

    public void updateSoLuong(int soLuong, int maHDCT) {
        JdbcHelper.update(UPDATE_SoLuong_SQL, soLuong, maHDCT);
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    public void deleteByInt(int id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<HoaDonChiTiet> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public HoaDonChiTiet selectByID(String id) {
        List<HoaDonChiTiet> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<HoaDonChiTiet> selectBySql(String sql, Object... args) {
        List<HoaDonChiTiet> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                HoaDonChiTiet entity = new HoaDonChiTiet();
                entity.setMaHDCT(rs.getInt("MaHDCT"));
                entity.setSoLuong(rs.getInt("SoLuong"));
                entity.setMaHD(rs.getInt("MaHD"));
                entity.setMaSP(rs.getString("MaSP"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
