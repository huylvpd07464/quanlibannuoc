/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan.qlbn.entity;

/**
 *
 * @author HP
 */
public class LoaiSanPham {
    private int MaLSP;
    private String tenLSP;
    private String moTa;

    public LoaiSanPham() {
    }

    public LoaiSanPham(int MaLSP, String tenLSP, String moTa) {
        this.MaLSP = MaLSP;
        this.tenLSP = tenLSP;
        this.moTa = moTa;
    }

    public int getMaLSP() {
        return MaLSP;
    }

    public void setMaLSP(int MaLSP) {
        this.MaLSP = MaLSP;
    }

    public String getTenLSP() {
        return tenLSP;
    }

    public void setTenLSP(String tenLSP) {
        this.tenLSP = tenLSP;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
      
}
