package model;
public class Customer { private int id; private String name, tel, address;
  public Customer(){} public Customer(int id,String name,String tel,String address){this.id=id;this.name=name;this.tel=tel;this.address=address;}
  public int getId(){return id;} public String getName(){return name;} public String getTel(){return tel;} public String getAddress(){return address;} }
