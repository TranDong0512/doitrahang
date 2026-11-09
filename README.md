# Module Đổi trả hàng – BTL Nhập môn CNPM (PTIT)
Java 17 + Swing + MySQL 8, mô hình MVC (model / view / dao).

1. Chạy `sql/supermarket.sql` trong MySQL (tạo CSDL + dữ liệu test: HD0125 còn hạn, HD0090 quá hạn).
2. Sửa USER/PASS trong `src/main/java/dao/DAO.java`.
3. Mở bằng IntelliJ/NetBeans (project Maven) → chạy `view.SellerHomeFrm`.
4. Unit test: `mvn test` (ReturnLogicTest: TC1–TC4 + đổi sang hàng rẻ hơn).
