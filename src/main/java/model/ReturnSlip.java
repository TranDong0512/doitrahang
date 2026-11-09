package model;
import java.util.*;
public class ReturnSlip { private int id; private Date createdDate=new Date(); private Invoice invoice; private Staff staff; private String type; private String reason;
  private float refund, extraPay; private List<ReturnItem> items=new ArrayList<>();
  public static final String EXCHANGE="Đổi", RETURN="Trả";
  public ReturnSlip(){} public ReturnSlip(Invoice inv,Staff st,String type,String reason){invoice=inv;staff=st;this.type=type;this.reason=reason;}
  /** Tính tiền hoàn (nếu hàng trả đắt hơn) hoặc tiền khách bù (nếu hàng mới đắt hơn) */
  public void calcMoney(){ float diff=0; for(ReturnItem r:items) diff+=r.getNewAmount()-r.getReturnAmount();
    if(diff>=0){extraPay=diff;refund=0;} else {refund=-diff;extraPay=0;} }
  public int getId(){return id;} public void setId(int v){id=v;} public Date getCreatedDate(){return createdDate;} public Invoice getInvoice(){return invoice;}
  public Staff getStaff(){return staff;} public String getType(){return type;} public String getReason(){return reason;}
  public float getRefund(){return refund;} public float getExtraPay(){return extraPay;} public List<ReturnItem> getItems(){return items;} }
