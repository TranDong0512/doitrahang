package view;
import javax.swing.*; import java.awt.*; import java.util.Date; import dao.InvoiceDAO; import model.*;
public class SearchInvoiceFrm extends JFrame {
  private final JTextField txtCode=new JTextField(15); private final Staff staff;
  public SearchInvoiceFrm(Staff staff){ super("Đổi trả hàng - Tìm hóa đơn"); this.staff=staff;
    JPanel p=new JPanel(new FlowLayout()); JButton btnSearch=new JButton("Tìm"), btnBack=new JButton("Quay lại");
    p.add(new JLabel("Mã hóa đơn:")); p.add(txtCode); p.add(btnSearch); p.add(btnBack);
    btnSearch.addActionListener(e->search()); txtCode.addActionListener(e->search());
    btnBack.addActionListener(e->{ new SellerHomeFrm(staff).setVisible(true); dispose(); });
    setContentPane(p); setSize(480,100); setLocationRelativeTo(null); setDefaultCloseOperation(EXIT_ON_CLOSE); }
  private void search(){ String code=txtCode.getText().trim();
    if(code.isEmpty()){ JOptionPane.showMessageDialog(this,"Vui lòng nhập mã hóa đơn"); return; }
    Invoice inv=new InvoiceDAO().getInvoiceByCode(code);
    if(inv==null){ JOptionPane.showMessageDialog(this,"Không tìm thấy hóa đơn"); return; }
    if(!InvoiceDAO.isInReturnPeriod(inv,new Date())){ JOptionPane.showMessageDialog(this,"Hóa đơn đã quá thời hạn đổi trả"); return; }
    new InvoiceDetailFrm(staff,inv).setVisible(true); dispose(); } }
