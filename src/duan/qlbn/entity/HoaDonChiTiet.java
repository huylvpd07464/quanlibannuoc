package duan.qlbn.entity;

/**
 *
 * @author HP
 */
public class HoaDonChiTiet {
    private int maHDCT;
    private int soLuong;
    private int maHD;
    private String maSP;

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(int maHDCT, int soLuong, int maHD, String maSP) {
        this.maHDCT = maHDCT;
        this.soLuong = soLuong;
        this.maHD = maHD;
        this.maSP = maSP;
    }

    public int getMaHDCT() {
        return maHDCT;
    }

    public void setMaHDCT(int maHDCT) {
        this.maHDCT = maHDCT;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }
    
}
