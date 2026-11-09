package view;
import javax.swing.*; import java.awt.*; import model.Staff;
public class SellerHomeFrm extends JFrame {
  public SellerHomeFrm(Staff staff){ super("Siêu thị - Nhân viên bán hàng");
    JPanel p=new JPanel(new BorderLayout(10,10)); p.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    p.add(new JLabel("Xin chào: "+staff.getFullName(),SwingConstants.CENTER),BorderLayout.NORTH);
    JPanel b=new JPanel(); JButton btnSell=new JButton("Bán hàng"), btnReturn=new JButton("Đổi trả hàng"), btnOut=new JButton("Đăng xuất");
    b.add(btnSell); b.add(btnReturn); b.add(btnOut); p.add(b,BorderLayout.CENTER);
    btnSell.addActionListener(e->JOptionPane.showMessageDialog(this,"Chức năng thuộc module khác"));
    btnReturn.addActionListener(e->{ new SearchInvoiceFrm(staff).setVisible(true); dispose(); });
    btnOut.addActionListener(e->System.exit(0));
    setContentPane(p); setSize(420,160); setLocationRelativeTo(null); setDefaultCloseOperation(EXIT_ON_CLOSE); }
  public static void main(String[] a){ SwingUtilities.invokeLater(()->new SellerHomeFrm(new Staff(1,"nva","Nguyễn Văn A","seller")).setVisible(true)); } }
