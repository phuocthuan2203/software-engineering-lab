## Vấn đề 1: High Coupling (Phụ thuộc mạnh) vào TransactionParser trong lớp StatementAnalyzer
**Mô tả vấn đề:** Lớp StatementAnalyzer có phụ thuộc trực tiếp vào danh sách các TransactionParser. Điều này làm giảm tính linh hoạt của hệ thống, vì khi cần thêm/loại bỏ hoặc thay đổi cách thức phân tích tệp (ví dụ, thêm định dạng mới như JSON, XML), lớp này sẽ cần sửa đổi trực tiếp. Điều này vi phạm nguyên tắc Open/Closed Principle (OCP) trong lập trình hướng đối tượng.

**Giải pháp:** Thay vì sử dụng danh sách TransactionParser, StatementAnalyzer nên sử dụng interface, ví dụ ITransactionParser. Các lớp con như CSVParser, PDFParser, hay các parser khác sẽ implement giao diện này. Điều này cho phép thêm parser mới mà không cần thay đổi lớp StatementAnalyzer.

## Vấn đề 2: Lớp Report không tuân thủ Single Responsibility Principle
**Mô tả vấn đề:** Lớp Report vừa có nhiệm vụ lưu trữ dữ liệu kết quả phân tích (totalIncome, totalExpense, ...), vừa chịu trách nhiệm hiển thị dữ liệu (generate(), display()). Điều này vi phạm nguyên tắc Single Responsibility Principle (SRP), vì một lớp chỉ nên có một lý do để thay đổi. Nếu giao diện hoặc cách hiển thị thay đổi, lớp này sẽ phải được chỉnh sửa.

**Giải pháp:** Tách chức năng hiển thị ra một lớp khác, ví dụ ReportView. Lớp Report chỉ nên chịu trách nhiệm lưu trữ và xử lý dữ liệu thống kê. Việc hiển thị hoặc xuất báo cáo nên được chuyển sang lớp ReportView.

## Vấn đề 3: Quản lý danh sách giao dịch trong BankStatement
**Mô tả vấn đề**: Lớp BankStatement hiện lưu danh sách các Transaction trực tiếp, nhưng không có cơ chế quản lý hoặc truy xuất hiệu quả (ví dụ: tìm kiếm, lọc giao dịch theo ngày, loại, hoặc giá trị).

**Giải pháp**: Thêm các phương thức quản lý danh sách, chẳng hạn:
**filterTransactionsByDate(startDate, endDate)**,
**filterTransactionsByType(type)**,
**getTotalAmountByCategory(category)**
