package model;
public class Item { private int id; private String name, unit; private float price; private int stock;
  public Item(){} public Item(int id,String name,String unit,float price,int stock){this.id=id;this.name=name;this.unit=unit;this.price=price;this.stock=stock;}
  public int getId(){return id;} public String getName(){return name;} public String getUnit(){return unit;} public float getPrice(){return price;} public int getStock(){return stock;}
  public void setStock(int s){stock=s;} @Override public String toString(){return name;} }
