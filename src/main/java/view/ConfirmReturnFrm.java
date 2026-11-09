package view;
import javax.swing.*; import java.awt.*; import java.text.DecimalFormat; import dao.ReturnSlipDAO; import model.*;
public class ConfirmReturnFrm extends JFrame {
  public ConfirmReturnFrm(ReturnSlip slip){ super("Phiếu đổi trả"); DecimalFormat m=new DecimalFormat("#,###đ");
    StringBuilder sb=new StringBuilder("<html><h3>PHIẾU ĐỔI TRẢ</h3>NV: "+slip.getStaff().getFullName()+" &nbsp; HĐ: "+slip.getInvoice().getCode()+
      "<br>Hình thức: "+slip.getType()+" &nbsp; Lý do: "+slip.getReason()+"<br><br>");
    for(ReturnItem r:slip.getItems()){ sb.append("Hàng trả: ").append(r.getInvoiceItem().getItem().getName()).append(" x").append(r.getReturnQty())
        .append(" (").append(r.getCondition()).append(") = ").append(m.format(r.getReturnAmount())).append("<br>");
      if(r.getNewItem()!=null) sb.append("Hàng mới: ").append(r.getNewItem().getName()).append(" x").append(r.getNewQty()).append(" = ").append(m.format(r.getNewAmount())).append("<br>"); }
    sb.append("<br><b>").append(slip.getRefund()>0?"Hoàn khách: "+m.format(slip.getRefund()):"Khách bù: "+m.format(slip.getExtraPay())).append("</b></html>");
    JPanel p=new JPanel(new BorderLayout()); p.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); p.add(new JLabel(sb.toString()),BorderLayout.CENTER);
    JPanel b=new JPanel(); JButton ok=new JButton("Xác nhận"), cancel=new JButton("Hủy"); b.add(ok); b.add(cancel); p.add(b,BorderLayout.SOUTH);
    ok.addActionListener(e->{ if(new ReturnSlipDAO().saveSlip(slip)){ JOptionPane.showMessageDialog(this,"Đổi trả thành công"); new SellerHomeFrm(slip.getStaff()).setVisible(true); dispose(); }
      else JOptionPane.showMessageDialog(this,"Lưu phiếu thất bại (kiểm tra số lượng / tồn kho)"); });
    cancel.addActionListener(e->{ new SellerHomeFrm(slip.getStaff()).setVisible(true); dispose(); });
    setContentPane(p); setSize(480,340); setLocationRelativeTo(null); setDefaultCloseOperation(EXIT_ON_CLOSE); } }
