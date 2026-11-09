package dao;
import java.sql.*; import model.*;
public class ReturnSlipDAO extends DAO {
  /** Lưu phiếu đổi trả + cập nhật SL đã trả + cập nhật kho trong 1 transaction */
  public boolean saveSlip(ReturnSlip s){
    try{ con.setAutoCommit(false);
      PreparedStatement ps=con.prepareStatement("INSERT INTO tblReturnSlip(createdDate,tblInvoiceId,tblStaffId,type,reason,refund,extraPay) VALUES(?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
      ps.setDate(1,new java.sql.Date(s.getCreatedDate().getTime())); ps.setInt(2,s.getInvoice().getId()); ps.setInt(3,s.getStaff().getId());
      ps.setString(4,s.getType()); ps.setString(5,s.getReason()); ps.setFloat(6,s.getRefund()); ps.setFloat(7,s.getExtraPay()); ps.executeUpdate();
      ResultSet k=ps.getGeneratedKeys(); if(k.next()) s.setId(k.getInt(1));
      PreparedStatement pIt=con.prepareStatement("INSERT INTO tblReturnItem(tblReturnSlipId,tblInvoiceItemId,returnQty,newItemId,newQty,itemCondition) VALUES(?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
      PreparedStatement pRet=con.prepareStatement("UPDATE tblInvoiceItem SET returnedQty=returnedQty+? WHERE id=? AND quantity-returnedQty>=?");
      PreparedStatement pIn=con.prepareStatement("UPDATE tblItem SET stock=stock+? WHERE id=?");
      PreparedStatement pOut=con.prepareStatement("UPDATE tblItem SET stock=stock-? WHERE id=? AND stock>=?");
      for(ReturnItem r:s.getItems()){
        pIt.setInt(1,s.getId()); pIt.setInt(2,r.getInvoiceItem().getId()); pIt.setInt(3,r.getReturnQty());
        if(r.getNewItem()==null) pIt.setNull(4,Types.INTEGER); else pIt.setInt(4,r.getNewItem().getId());
        pIt.setInt(5,r.getNewQty()); pIt.setString(6,r.getCondition()); pIt.executeUpdate();
        ResultSet k2=pIt.getGeneratedKeys(); if(k2.next()) r.setId(k2.getInt(1));
        pRet.setInt(1,r.getReturnQty()); pRet.setInt(2,r.getInvoiceItem().getId()); pRet.setInt(3,r.getReturnQty());
        if(pRet.executeUpdate()==0) throw new SQLException("Số lượng trả vượt quá số lượng còn được trả");
        if(ReturnItem.INTACT.equals(r.getCondition())){ pIn.setInt(1,r.getReturnQty()); pIn.setInt(2,r.getInvoiceItem().getItem().getId()); pIn.executeUpdate(); }
        if(r.getNewItem()!=null){ pOut.setInt(1,r.getNewQty()); pOut.setInt(2,r.getNewItem().getId()); pOut.setInt(3,r.getNewQty());
          if(pOut.executeUpdate()==0) throw new SQLException("Mặt hàng mới không đủ tồn kho"); }
      }
      con.commit(); return true;
    }catch(SQLException e){ e.printStackTrace(); try{con.rollback();}catch(SQLException ex){} return false; }
    finally{ try{con.setAutoCommit(true);}catch(SQLException ex){} } } }
