package model;
public class ReturnItem { private int id; private InvoiceItem invoiceItem; private int returnQty; private Item newItem; private int newQty; private String condition;
  public static final String INTACT="Nguyên vẹn", DEFECT="Lỗi";
  public ReturnItem(){} public ReturnItem(InvoiceItem ii,int returnQty,String condition){invoiceItem=ii;this.returnQty=returnQty;this.condition=condition;}
  public int getId(){return id;} public void setId(int v){id=v;} public InvoiceItem getInvoiceItem(){return invoiceItem;} public int getReturnQty(){return returnQty;}
  public Item getNewItem(){return newItem;} public void setNewItem(Item i){newItem=i;} public int getNewQty(){return newQty;} public void setNewQty(int q){newQty=q;}
  public String getCondition(){return condition;}
  public float getReturnAmount(){return returnQty*invoiceItem.getPrice();}
  public float getNewAmount(){return newItem==null?0:newQty*newItem.getPrice();} }
