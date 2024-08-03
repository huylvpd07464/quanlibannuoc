/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan.qlbn.utils;

import duan.qlbn.entity.NhanVien;

/**
 *
 * @author HP
 */
public class Auth {

    //    Đối tượng này chứa thông tin người dùng sau khi đăng nhập
    public static NhanVien user = null;

//    Xóa thông tin người sử dụng sau khi đăng xuất
    public static void clear() {
        Auth.user = null;
    }

//    Kiểm tra xem đăng nhập hay chưa
//    @return về đăng nhập hay chưa
    public static boolean isLogin() {
        return Auth.user != null;
    }

//    Kiểm tra vai trò quản lý hay nhân viên
    public static boolean isManager() {
        return Auth.isLogin() && user.isVaiTro();
    }
}
