package dao;
import java.sql.*; import java.util.*; import model.*;
public class InvoiceDAO extends DAO {
  public static final int RETURN_DAYS=7;
  /** Lấy hóa đơn kèm chi tiết theo mã; null nếu không có */
  public Invoice getInvoiceByCode(String code){
    String sql="SELECT i.*, c.name cname, c.tel, c.address, s.username, s.fullName, s.position FROM tblInvoice i "+
      "JOIN tblCustomer c ON c.id=i.tblCustomerId JOIN tblStaff s ON s.id=i.tblStaffId WHERE i.code=?";
    try(PreparedStatement ps=con.prepareStatement(sql)){ ps.setString(1,code.trim()); ResultSet rs=ps.executeQuery(); if(!rs.next()) return null;
      Customer c=new Customer(rs.getInt("tblCustomerId"),rs.getString("cname"),rs.getString("tel"),rs.getString("address"));
      Staff s=new Staff(rs.getInt("tblStaffId"),rs.getString("username"),rs.getString("fullName"),rs.getString("position"));
      Invoice inv=new Invoice(rs.getInt("id"),rs.getString("code"),rs.getDate("createdDate"),rs.getFloat("total"),c,s);
      PreparedStatement p2=con.prepareStatement("SELECT ii.*, it.name, it.unit, it.price iprice, it.stock FROM tblInvoiceItem ii JOIN tblItem it ON it.id=ii.tblItemId WHERE ii.tblInvoiceId=?");
      p2.setInt(1,inv.getId()); ResultSet r2=p2.executeQuery(); List<InvoiceItem> l=new ArrayList<>();
      while(r2.next()){ Item it=new Item(r2.getInt("tblItemId"),r2.getString("name"),r2.getString("unit"),r2.getFloat("iprice"),r2.getInt("stock"));
        l.add(new InvoiceItem(r2.getInt("id"),it,r2.getInt("quantity"),r2.getFloat("price"),r2.getInt("returnedQty"))); }
      inv.setItems(l); return inv;
    }catch(SQLException e){ e.printStackTrace(); return null; } }
  /** Hóa đơn còn trong thời hạn đổi trả tại thời điểm now */
  public static boolean isInReturnPeriod(Invoice inv, java.util.Date now){
    long days=(now.getTime()-inv.getCreatedDate().getTime())/(24L*60*60*1000); return days>=0 && days<=RETURN_DAYS; }
  /** Số lượng trả hợp lệ: 0 < qty <= SL đã mua - SL đã trả */
  public static boolean isValidQuantity(InvoiceItem ii,int qty){ return qty>0 && qty<=ii.getRemainQty(); } }
