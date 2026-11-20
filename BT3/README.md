# Overview

Cải thiện chức năng đăng ký và đăng nhập từ BT2:

- Tính năng đăng ký và quên mật khẩu được xác thực với OTP, sử dụng mail server `smtp.gmail.com` để gửi với giao thức TLS.
- Mật khẩu được lưu và hash bằng SHA256.

Thêm tính năng cho người dùng Upload và xem Video của mình. Video được hiện ở trên cả trang chủ và trang chứa Video.

Cấu hình Sitemesh decorator 3 với một bootstrap template. Thêm chức năng validation cho các loại FORM trong trang web.

Thêm tính năng cập nhật thông tin Profile, upload ảnh đại diện cá nhân bằng Cloudinary.