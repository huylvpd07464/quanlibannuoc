create database QLBN
go

use QLBN

--Tạo bảng nhân viên
create table NhanVien(
	MaNV nvarchar(50) not null primary key,
	MatKhau nvarchar(50) not null,
	email nvarchar(50) not null,
	TenNV nvarchar(50) not null,
	VaiTro bit not null,
	TrangThai bit not null
)
go

--Tạo bảng khách hàng
create table KhachHang(
	MaKH nvarchar(50) not null primary key,
	TenKH nvarchar(50) not null,
	NgaySinh date not null,
	DienThoai nvarchar(50) not null,
	Email nvarchar(50) not null,
	NgayDK date not null,
	GhiChu nvarchar(max)
)
go

--Tạo bảng nhà cung cấp
create table NhaCungCap(
	MaNCC nvarchar(50) not null primary key,
	TenNCC nvarchar(50) not null,
	DienThoai nvarchar(50) not null,
	DiaChi nvarchar(255) not null,
	Email nvarchar(50) not null,
	GhiChu nvarchar(max)
)
go

-- Tạo bảng loại sản phẩm
create table LoaiSanPham(
	MaLSP int identity(1,1) not null primary key,
	TenLSP nvarchar(50) not null,
	MoTa nvarchar(max)
)
go

--Tạo bảng sản phẩm
create table SanPham(
	MaSP nvarchar(50) not null primary key,
	TenSP nvarchar(50) not null,
	SoLuong int not null,
	DonGia float not null,
	HinhAnh nvarchar(50) not null,
	MoTa nvarchar(max),
	MaLSP int not null,
	MaNCC nvarchar(50) not null,
	MaNV nvarchar(50) not null,
	constraint fk_SanPhamLSP foreign key(MaLSP) references LoaiSanPham(MaLSP),
	constraint fk_SanPhamNCC foreign key(MaNCC) references NhaCungCap(MaNCC),
	constraint fk_SanPhamNV foreign key(MaNV) references NhanVien(MaNV)
)
go

--Tạo bảng hóa đơn
create table HoaDon(
	MaHD int identity(1,1) not null primary key,
	NgayMua date not null,
	TrangThai bit not null,
	MaKH nvarchar(50) not null,
	MaNV nvarchar(50) not null,
	constraint fk_HoaDonKH foreign key(MaKH) references KhachHang(MaKH),
	constraint fk_HoaDonNV foreign key(MaNV) references NhanVien(MaNV)
)
go

--Tạo bảng hóa đơn chi tiết
create table HoaDonChiTiet(
	MaHDCT int identity(1,1) not null primary key,
	SoLuong int not null,
	MaHD int not null,
	MaSP nvarchar(50) not null,
	constraint fk_HoaDonChiTietHD foreign key(MaHD) references HoaDon(MaHD),
	constraint fk_HoaDonChiTietSP foreign key(MaSP) references SanPham(MaSP)
)
go
-- Tạo danh sách hóa đơn
create proc sp_DanhSachHD @tenKHCanTim nvarchar(50)
as
	begin
		select hd.MaHD, nv.TenNV, kh.TenKH, hd.NgayMua, hd.TrangThai from HoaDon hd
		inner join NhanVien nv on hd.MaNV = nv.MaNV
		inner join KhachHang kh on kh.MaKH = hd.MaKH
		where kh.TenKH like @tenKHCanTim
	end
go
-- Tạo thống kê doang thu tháng hiện tại
create proc sp_DoanhThuNgay @thang int
as
	begin
		SELECT
			DAY(hd.NgayMua),
			SUM(hdct.SoLuong * sp.DonGia) AS DoanhThu
		FROM
			HoaDon hd
			INNER JOIN HoaDonChiTiet hdct ON hd.MaHD = hdct.MaHD
			INNER JOIN SanPham sp ON hdct.MaSP = sp.MaSP
		WHERE
			MONTH(hd.NgayMua) = @thang and hd.TrangThai = 1
		Group by
			DAY(hd.NgayMua)
	end
go

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



	
