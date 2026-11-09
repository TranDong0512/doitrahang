package model;
public class InvoiceItem { private int id; private Item item; private int quantity; private float price; private int returnedQty;
  public InvoiceItem(){} public InvoiceItem(int id,Item item,int quantity,float price,int returnedQty){this.id=id;this.item=item;this.quantity=quantity;this.price=price;this.returnedQty=returnedQty;}
  public int getId(){return id;} public Item getItem(){return item;} public int getQuantity(){return quantity;} public float getPrice(){return price;}
  public int getReturnedQty(){return returnedQty;} public void setReturnedQty(int v){returnedQty=v;}
  /** Số lượng còn được phép trả */
  public int getRemainQty(){return quantity-returnedQty;} }
