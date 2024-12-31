# 1. Phân tích bài toán

## 1.1. Các chức năng chính

- **Import Bank Statement:** Tải và nạp dữ liệu giao dịch ngân hàng vào hệ thống.
- **Validate/Clean Data:** Kiểm tra và làm sạch dữ liệu trước khi phân tích (có thể gộp chung vào với bước Import, nhưng ở mức khái niệm, ta tách để rõ ràng).
- **Analyze Transactions:** Tiến hành phân tích dữ liệu giao dịch, tạo ra các thống kê (tổng số tiền, số giao dịch, phân nhóm giao dịch,…).
- **Export/Generate Report:** Xuất báo cáo hoặc hiển thị kết quả thống kê cho người dùng.

## 1.2. Các tác nhân liên quan

- **Người dùng (User):** Có thể là khách hàng, nhân viên phân tích, hoặc bất cứ ai tương tác với hệ thống để import dữ liệu, chạy phân tích, xem báo cáo, v.v.
- **(Tùy chọn) Hệ thống Lưu trữ (Storage System):** Được nhắc đến nếu ta muốn thể hiện việc hệ thống xuất dữ liệu phân tích hay lấy dữ liệu từ kho lưu trữ ngoài.

# 2. Biểu đồ ca sử dụng (Use Case Diagram)
![Use Case Diagram](https://www.planttext.com/plantuml/png/RT2z2i8m4C3nFKznTDAX5DjE3bAj8YuEVkr7UvHYUvKanOztyHrSnL7mFNmJ4omKIhSS-P7xaqXfbBek2u69hYGuw-pp-BxkEQJl9sSms2kIGmTGWJq9KIjAK14ug_9KIGqpv2DiDMegYVKVngEM7R38KtFhnGKXmnmrDiXlqPInkDu8TX9PcP2yOjMee5MBYzteBOb9cXTXGtPkN2Y43GGthSxiJcm4ZA4tiWjz0tiokommh2-qwqBZ-bE5Y8XJ-za_)

# 3. Đặc tả ca sử dụng “Thống kê (Analyze) các giao dịch ngân hàng”
## 3.1. Tên ca sử dụng
UC3: Analyze Transactions (Phân tích giao dịch ngân hàng)

## 3.2. Tác nhân tham gia
**Người dùng (User):** Thực hiện thao tác chọn dữ liệu, yêu cầu phân tích, điều chỉnh tham số phân tích (nếu có).

## 3.3. Mô tả tóm tắt
Ca sử dụng này cho phép hệ thống xử lý dữ liệu giao dịch đã được nhập vào và làm sạch, sau đó áp dụng các thuật toán hoặc quy tắc phân tích (như thống kê tổng số tiền giao dịch, phân loại giao dịch, xác định tần suất, số lần rút tiền, nạp tiền, …). Kết quả phân tích sẽ được lưu hoặc hiển thị lại cho người dùng.

## 3.4. Tiền điều kiện (Preconditions)
- Hệ thống đã khởi động và sẵn sàng hoạt động.
- Dữ liệu giao dịch ngân hàng đã được import và validate thành công (UC1, UC2 hoàn thành).
- Người dùng đã đăng nhập (nếu có yêu cầu xác thực).

## 3.5. Luồng sự kiện chính (Main Flow)
1. Người dùng chọn chức năng “Analyze Transactions” trên giao diện.
2. Hệ thống hiển thị các tùy chọn hoặc tham số phân tích (nếu có).
3. Người dùng thiết lập tham số (nếu cần) và nhấn nút “Phân tích”.
4. Hệ thống bắt đầu xử lý dữ liệu giao dịch:
   - Tính toán số lượng giao dịch, tổng tiền, phân loại giao dịch theo loại (rút tiền, nạp tiền, chuyển khoản, v.v.), xác định các khoảng thời gian, …
   - Áp dụng các quy tắc phân tích bổ sung (nếu có).
5. Hệ thống tổng hợp kết quả phân tích vào cấu trúc dữ liệu phù hợp.
6. Hệ thống hiển thị tóm tắt kết quả phân tích (ví dụ: bảng, biểu đồ, …) trên giao diện cho Người dùng.

## 3.6. Luồng sự kiện phụ (Alternative Flow) – nếu có
**AF1:** Trong quá trình phân tích, nếu hệ thống phát hiện dữ liệu có format bất thường (chưa được validate đúng cách):
- Hệ thống hiển thị cảnh báo lỗi hoặc đề xuất chạy lại Validate/Clean Data.
- Ca sử dụng dừng hoặc quay về bước validate để làm sạch dữ liệu một lần nữa.

## 3.7. Hậu điều kiện (Post Conditions)
- Kết quả phân tích được lưu tạm thời trong hệ thống.
- Người dùng có thể xem báo cáo nhanh hoặc chuyển sang bước Export/Generate Report (UC4) để xuất ra file.

## 3.8. Các ngoại lệ (Exceptions)
**E1:** Lỗi hệ thống (mất kết nối cơ sở dữ liệu, hỏng service phân tích, v.v.) – Hệ thống thông báo cho người dùng, ghi log lỗi và dừng quá trình phân tích.
**E2:** Dữ liệu trống (không có giao dịch để phân tích) – Hệ thống hiển thị thông báo “Không có dữ liệu để phân tích” và kết thúc ca sử dụng.

# 4. Mô tả 01 yêu cầu phi chức năng (Non-functional Requirement)

## 4.1. Hiệu năng (Performance)
- **Mô tả:** Hệ thống phải có khả năng phân tích và trả về kết quả thống kê cho một tệp giao dịch 10.000 dòng trong vòng 5 giây.
- **Cách đo lường:** Trong môi trường kiểm thử tiêu chuẩn (với cấu hình phần cứng và dữ liệu giả lập), hệ thống cần được đo thời gian phản hồi khi chạy phân tích trên tệp 10.000 dòng. Thời gian xử lý trung bình không được vượt quá 5 giây.
