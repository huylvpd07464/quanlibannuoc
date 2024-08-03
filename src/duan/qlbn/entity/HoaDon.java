package duan.qlbn.entity;

import java.util.Date;

/**
 *
 * @author HP
 */
public class HoaDon {

    private int maHD;
    private Date ngayMua;
    private boolean trangThai;
    private String maKH;
    private String maNV;

    public HoaDon() {
    }

    public HoaDon(int maHD, Date ngayMua, boolean trangThai, String maKH, String maNV) {
        this.maHD = maHD;
        this.ngayMua = ngayMua;
        this.trangThai = trangThai;
        this.maKH = maKH;
        this.maNV = maNV;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public Date getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(Date ngayMua) {
        this.ngayMua = ngayMua;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    

    
    
    
    
}
