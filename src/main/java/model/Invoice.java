package model;
import java.util.*;
public class Invoice { private int id; private String code; private Date createdDate; private float total; private Customer customer; private Staff staff;
  private List<InvoiceItem> items=new ArrayList<>();
  public Invoice(){} public Invoice(int id,String code,Date createdDate,float total,Customer c,Staff s){this.id=id;this.code=code;this.createdDate=createdDate;this.total=total;customer=c;staff=s;}
  public int getId(){return id;} public String getCode(){return code;} public Date getCreatedDate(){return createdDate;} public float getTotal(){return total;}
  public Customer getCustomer(){return customer;} public Staff getStaff(){return staff;} public List<InvoiceItem> getItems(){return items;} public void setItems(List<InvoiceItem> l){items=l;} }
