/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan.qlbn.entity;

/**
 *
 * @author HP
 */
public class NhaCungCap {

    private String maNCC;
    private String tenNCC;
    private String dienThoai;
    private String diaChi;
    private String email;
    private String ghiChu;

    public NhaCungCap() {
    }

    public NhaCungCap(String maNCC, String tenNCC, String dienThoai, String diaChi, String email, String ghiChu) {
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.dienThoai = dienThoai;
        this.diaChi = diaChi;
        this.email = email;
        this.ghiChu = ghiChu;
    }

    public String getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
    
}
