import static org.junit.Assert.*; import org.junit.Test; import java.util.*; import dao.InvoiceDAO; import model.*;
public class ReturnLogicTest {
  private static Date daysAgo(int d){ return new Date(System.currentTimeMillis()-d*24L*60*60*1000); }
  private final Item vina=new Item(1,"Sữa Vinamilk 1L","hộp",32000,100), oreo=new Item(2,"Bánh Oreo","gói",25000,40), th=new Item(3,"Sữa TH true milk 1L","hộp",35000,50);
  private Invoice inv(int ago){ return new Invoice(1,"HD0125",daysAgo(ago),178000,null,null); }
  @Test public void tc1_exchangeExtraPay(){ InvoiceItem ii=new InvoiceItem(1,vina,4,32000,0); ReturnSlip s=new ReturnSlip(inv(4),null,ReturnSlip.EXCHANGE,"Hỏng bao bì");
    ReturnItem r=new ReturnItem(ii,2,ReturnItem.DEFECT); r.setNewItem(th); r.setNewQty(2); s.getItems().add(r); s.calcMoney();
    assertEquals(6000f,s.getExtraPay(),0.01); assertEquals(0f,s.getRefund(),0.01); }
  @Test public void tc2_returnRefund(){ InvoiceItem ii=new InvoiceItem(2,oreo,2,25000,0); ReturnSlip s=new ReturnSlip(inv(4),null,ReturnSlip.RETURN,"Không còn nhu cầu");
    s.getItems().add(new ReturnItem(ii,1,ReturnItem.INTACT)); s.calcMoney(); assertEquals(25000f,s.getRefund(),0.01); }
  @Test public void tc3_expired(){ assertFalse(InvoiceDAO.isInReturnPeriod(inv(20),new Date())); assertTrue(InvoiceDAO.isInReturnPeriod(inv(7),new Date())); }
  @Test public void tc4_quantity(){ InvoiceItem ii=new InvoiceItem(1,vina,4,32000,1);
    assertFalse(InvoiceDAO.isValidQuantity(ii,5)); assertFalse(InvoiceDAO.isValidQuantity(ii,4)); assertTrue(InvoiceDAO.isValidQuantity(ii,3)); assertFalse(InvoiceDAO.isValidQuantity(ii,0)); }
  @Test public void cheaperExchangeRefund(){ InvoiceItem ii=new InvoiceItem(1,vina,4,32000,0); Item small=new Item(4,"TH 180ml","hộp",8000,200);
    ReturnSlip s=new ReturnSlip(inv(1),null,ReturnSlip.EXCHANGE,"x"); ReturnItem r=new ReturnItem(ii,1,ReturnItem.INTACT); r.setNewItem(small); r.setNewQty(2); s.getItems().add(r); s.calcMoney();
    assertEquals(16000f,s.getRefund(),0.01); } }
