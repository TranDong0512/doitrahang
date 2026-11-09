DROP DATABASE IF EXISTS supermarket;
CREATE DATABASE supermarket CHARACTER SET utf8mb4;
USE supermarket;
CREATE TABLE tblStaff(id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(50) UNIQUE, password VARCHAR(50), fullName VARCHAR(100), position VARCHAR(30));
CREATE TABLE tblCustomer(id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100), tel VARCHAR(15), address VARCHAR(200), point INT DEFAULT 0);
CREATE TABLE tblItem(id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100), unit VARCHAR(20), price FLOAT, stock INT);
CREATE TABLE tblInvoice(id INT AUTO_INCREMENT PRIMARY KEY, code VARCHAR(20) UNIQUE, createdDate DATE, total FLOAT,
  tblCustomerId INT, tblStaffId INT, FOREIGN KEY(tblCustomerId) REFERENCES tblCustomer(id), FOREIGN KEY(tblStaffId) REFERENCES tblStaff(id));
CREATE TABLE tblInvoiceItem(id INT AUTO_INCREMENT PRIMARY KEY, tblInvoiceId INT, tblItemId INT, quantity INT, price FLOAT, returnedQty INT DEFAULT 0,
  FOREIGN KEY(tblInvoiceId) REFERENCES tblInvoice(id), FOREIGN KEY(tblItemId) REFERENCES tblItem(id));
CREATE TABLE tblReturnSlip(id INT AUTO_INCREMENT PRIMARY KEY, createdDate DATE, tblInvoiceId INT, tblStaffId INT, type VARCHAR(10), reason VARCHAR(200),
  refund FLOAT, extraPay FLOAT, FOREIGN KEY(tblInvoiceId) REFERENCES tblInvoice(id), FOREIGN KEY(tblStaffId) REFERENCES tblStaff(id));
CREATE TABLE tblReturnItem(id INT AUTO_INCREMENT PRIMARY KEY, tblReturnSlipId INT, tblInvoiceItemId INT, returnQty INT, newItemId INT NULL, newQty INT,
  itemCondition VARCHAR(20), FOREIGN KEY(tblReturnSlipId) REFERENCES tblReturnSlip(id), FOREIGN KEY(tblInvoiceItemId) REFERENCES tblInvoiceItem(id),
  FOREIGN KEY(newItemId) REFERENCES tblItem(id));
INSERT INTO tblStaff VALUES(1,'nva','123','Nguyễn Văn A','seller');
INSERT INTO tblCustomer VALUES(1,'Nguyễn Văn B','0912345678','Hà Đông',0);
INSERT INTO tblItem VALUES(1,'Sữa Vinamilk 1L','hộp',32000,100),(2,'Bánh Oreo','gói',25000,40),(3,'Sữa TH true milk 1L','hộp',35000,50),(4,'Sữa TH true milk 180ml','hộp',8000,200);
-- HD0125: còn hạn (lùi 4 ngày); HD0090: quá hạn (lùi 20 ngày)
INSERT INTO tblInvoice VALUES(1,'HD0125',CURDATE()-INTERVAL 4 DAY,178000,1,1),(2,'HD0090',CURDATE()-INTERVAL 20 DAY,64000,1,1);
INSERT INTO tblInvoiceItem VALUES(1,1,1,4,32000,0),(2,1,2,2,25000,0),(3,2,1,2,32000,0);
