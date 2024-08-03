-- Thống kê doanh thu các ngày trong tháng
create proc sp_ThongKeDoanhThuNgay
as
	begin
		SELECT NgayMua, SUM(HoaDonChiTiet.SoLuong * DonGia) AS DoanhThu
		FROM HoaDonChiTiet
		INNER JOIN HoaDon ON HoaDonChiTiet.MaHD = HoaDon.MaHD
		INNER JOIN SanPham ON HoaDonChiTiet.MaSP = SanPham.MaSP
		WHERE MONTH(NgayMua) = MONTH(GETDATE()) and HoaDon.TrangThai = 1
		GROUP BY NgayMua
	end
go
-- Thống kê lượng khách hàng theo năm
create proc sp_LuongSanPhamNam @ngay Date
as
	begin		
		Select 
			SUM(SoLuong) as LuongSanPham
		from 
			HoaDon hd 
			inner join HoaDonChiTiet hdct 
			on hd.MaHD = hdct.MaHD
		where 
			YEAR(hd.NgayMua) = YEAR(@ngay)
	end
go
-- Thống kê lượng khách hàng theo thang
create proc sp_LuongSanPhamThang @ngay Date
as
	begin		
		Select 
			SUM(SoLuong) as LuongSanPham
		from 
			HoaDon hd 
			inner join HoaDonChiTiet hdct 
			on hd.MaHD = hdct.MaHD
		where 
			MONTH(hd.NgayMua) = MONTH(@ngay) and YEAR(hd.NgayMua) = YEAR(@ngay)
	end
go
-- Thống kê lượng khách hàng theo năm
create proc sp_LuongSanPhamNgay @ngay Date
as
	begin		
		Select 
			SUM(SoLuong) as LuongSanPham
		from 
			HoaDon hd 
			inner join HoaDonChiTiet hdct 
			on hd.MaHD = hdct.MaHD
		where 
			hd.NgayMua = @ngay
	end
go
-- Thống kê lượng khách hàng theo năm
create proc sp_LuongKhachTheoNam @ngay Date
as
	begin		
		SELECT 
			COUNT(DISTINCT MaKH) AS SoKhachHang
		FROM 
			HoaDon
		WHERE 
			YEAR(NgayMua) = YEAR(@ngay)
	end
go
-- Thống kê lượng khách hàng theo tháng
create proc sp_LuongKhachTheoThang @ngay Date
as
	begin		
		SELECT
			COUNT(DISTINCT HoaDon.MaKH) as SoKhachHang
		FROM
			HoaDon
		WHERE
			MONTH(HoaDon.NgayMua) = MONTH(@ngay) and YEAR(HoaDon.NgayMua) = YEAR(@ngay)
	end
go
-- Thống kê lượng khách hàng theo ngày
create proc sp_LuongKhachTheoNgay @ngay Date
as
	begin		
		SELECT
			COUNT(DISTINCT HoaDon.MaKH) as SoKhachHang
		FROM
			HoaDon
		WHERE
			HoaDon.NgayMua = @ngay
	end
go
-- Thống kê lượng hóa đơn theo năm
create proc sp_LuongHoaDonTheoNam @ngay Date
as
	begin
		SELECT
			COUNT(HoaDon.MaHD) as SoHoaDon
		FROM
			HoaDon
		WHERE
			Year(HoaDon.NgayMua) = YEAR(@ngay)
		GROUP BY
			Year(HoaDon.NgayMua)
	end
go
-- Thống kê lượng hóa đơn theo tháng
create proc sp_LuongHoaDonTheoThang @ngay Date
as
	begin
		SELECT
			COUNT(HoaDon.MaHD) as SoHoaDon
		FROM
			HoaDon
		WHERE
			MONTH(HoaDon.NgayMua) = MONTH(@ngay) and YEAR(HoaDon.NgayMua) = YEAR(@ngay)
		GROUP BY
			MONTH(HoaDon.NgayMua)
	end
go
-- Thống kê lượng hóa đơn theo ngày
create proc sp_LuongHoaDonTheoNgay @ngay Date
as
	begin
		SELECT
			COUNT(HoaDon.MaHD) as SoHoaDon
		FROM
			HoaDon
		WHERE
			HoaDon.NgayMua = @ngay
		GROUP BY
			DAY(HoaDon.NgayMua)
	end
go
-- Thống kê doanh thu theo ngày
create proc sp_DoanhThuTheoNgay @ngay Date
as
	begin
		SELECT
			SUM(HoaDonChiTiet.SoLuong * SanPham.DonGia) AS DoanhThu
		FROM
			HoaDon
			INNER JOIN HoaDonChiTiet ON HoaDon.MaHD = HoaDonChiTiet.MaHD
			INNER JOIN SanPham ON HoaDonChiTiet.MaSP = SanPham.MaSP
		WHERE
			HoaDon.NgayMua = @ngay
		GROUP BY
			Day(HoaDon.NgayMua)
	end
go
-- Thống kê doanh thu theo tháng
create proc sp_DoanhThuTheoThang @ngay date
as
	begin
		SELECT
			SUM(HoaDonChiTiet.SoLuong * SanPham.DonGia) AS DoanhThu
		FROM
			HoaDon
			INNER JOIN HoaDonChiTiet ON HoaDon.MaHD = HoaDonChiTiet.MaHD
			INNER JOIN SanPham ON HoaDonChiTiet.MaSP = SanPham.MaSP
		WHERE
			Month(HoaDon.NgayMua) = MONTH(@ngay) and Year(HoaDon.NgayMua) = Year(@ngay) and HoaDon.TrangThai = 1
		GROUP BY
			Month(HoaDon.NgayMua)
	end
go
-- Thống kê doanh thu theo năm
create proc sp_DoanhThuTheoNam @nam date
as
	begin
		SELECT
			SUM(HoaDonChiTiet.SoLuong * SanPham.DonGia) AS DoanhThu
		FROM
			HoaDon
			INNER JOIN HoaDonChiTiet ON HoaDon.MaHD = HoaDonChiTiet.MaHD
			INNER JOIN SanPham ON HoaDonChiTiet.MaSP = SanPham.MaSP
		WHERE
			YEAR(HoaDon.NgayMua) = YEAR(@nam) and HoaDon.TrangThai = 1
		GROUP BY
			YEAR(HoaDon.NgayMua)
	end



	