package model;
public class Staff { private int id; private String username, fullName, position;
  public Staff(){} public Staff(int id,String username,String fullName,String position){this.id=id;this.username=username;this.fullName=fullName;this.position=position;}
  public int getId(){return id;} public void setId(int v){id=v;} public String getUsername(){return username;} public String getFullName(){return fullName;} public String getPosition(){return position;} }
