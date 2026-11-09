package dao;
import java.sql.*; import java.util.*; import model.Item;
public class ItemDAO extends DAO {
  public List<Item> searchItem(String key){ List<Item> l=new ArrayList<>();
    try(PreparedStatement ps=con.prepareStatement("SELECT * FROM tblItem WHERE name LIKE ? ORDER BY name")){ ps.setString(1,"%"+key.trim()+"%"); ResultSet rs=ps.executeQuery();
      while(rs.next()) l.add(new Item(rs.getInt("id"),rs.getString("name"),rs.getString("unit"),rs.getFloat("price"),rs.getInt("stock")));
    }catch(SQLException e){ e.printStackTrace(); } return l; } }
