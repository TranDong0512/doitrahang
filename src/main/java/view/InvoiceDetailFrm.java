package view;
import javax.swing.*; import javax.swing.table.DefaultTableModel; import java.awt.*; import java.text.SimpleDateFormat; import java.util.Calendar;
import dao.InvoiceDAO; import model.*;
public class InvoiceDetailFrm extends JFrame {
  private final Staff staff; private final Invoice inv; private final JTable tbl; private final DefaultTableModel model;
  private final JTextField txtReason=new JTextField(20); private final JRadioButton rdExchange=new JRadioButton("Đổi hàng",true), rdReturn=new JRadioButton("Trả hàng");
  private final JComboBox<String> cbCondition=new JComboBox<>(new String[]{ReturnItem.INTACT,ReturnItem.DEFECT});
  public InvoiceDetailFrm(Staff staff,Invoice inv){ super("Chi tiết hóa đơn "+inv.getCode()); this.staff=staff; this.inv=inv;
    SimpleDateFormat f=new SimpleDateFormat("dd/MM/yyyy"); Calendar c=Calendar.getInstance(); c.setTime(inv.getCreatedDate()); c.add(Calendar.DATE,InvoiceDAO.RETURN_DAYS);
    JLabel head=new JLabel("<html>Hóa đơn: "+inv.getCode()+" &nbsp; Ngày: "+f.format(inv.getCreatedDate())+" &nbsp; KH: "+inv.getCustomer().getName()+
      "<br>Trạng thái: Còn hạn đổi trả (đến "+f.format(c.getTime())+")</html>");
    model=new DefaultTableModel(new Object[]{"STT","Mặt hàng","SL","Đã trả","Đơn giá","SL trả","Chọn"},0){
      public Class<?> getColumnClass(int i){ return i==6?Boolean.class:i==5?Integer.class:Object.class; }
      public boolean isCellEditable(int r,int col){ return col==5||col==6; } };
    int i=1; for(InvoiceItem ii:inv.getItems()) model.addRow(new Object[]{i++,ii.getItem().getName(),ii.getQuantity(),ii.getReturnedQty(),ii.getPrice(),0,false});
    tbl=new JTable(model);
    ButtonGroup g=new ButtonGroup(); g.add(rdExchange); g.add(rdReturn);
    JPanel bottom=new JPanel(new GridLayout(2,1)); JPanel r1=new JPanel(); r1.add(new JLabel("Lý do:")); r1.add(txtReason); r1.add(new JLabel("Tình trạng:")); r1.add(cbCondition);
    JPanel r2=new JPanel(); r2.add(rdExchange); r2.add(rdReturn); JButton btnNext=new JButton("Tiếp tục"), btnCancel=new JButton("Hủy"); r2.add(btnNext); r2.add(btnCancel);
    bottom.add(r1); bottom.add(r2);
    btnNext.addActionListener(e->next()); btnCancel.addActionListener(e->{ new SellerHomeFrm(staff).setVisible(true); dispose(); });
    JPanel p=new JPanel(new BorderLayout(5,5)); p.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    p.add(head,BorderLayout.NORTH); p.add(new JScrollPane(tbl),BorderLayout.CENTER); p.add(bottom,BorderLayout.SOUTH);
    setContentPane(p); setSize(700,380); setLocationRelativeTo(null); setDefaultCloseOperation(EXIT_ON_CLOSE); }
  private void next(){ if(tbl.isEditing()) tbl.getCellEditor().stopCellEditing();
    if(txtReason.getText().trim().isEmpty()){ JOptionPane.showMessageDialog(this,"Vui lòng nhập lý do"); return; }
    ReturnSlip slip=new ReturnSlip(inv,staff,rdExchange.isSelected()?ReturnSlip.EXCHANGE:ReturnSlip.RETURN,txtReason.getText().trim());
    for(int r=0;r<model.getRowCount();r++){ if(!Boolean.TRUE.equals(model.getValueAt(r,6))) continue;
      InvoiceItem ii=inv.getItems().get(r); Object v=model.getValueAt(r,5); int q=v==null?0:(Integer)v;
      if(!InvoiceDAO.isValidQuantity(ii,q)){ JOptionPane.showMessageDialog(this,"Số lượng trả của \""+ii.getItem().getName()+"\" không hợp lệ (tối đa "+ii.getRemainQty()+")"); return; }
      slip.getItems().add(new ReturnItem(ii,q,(String)cbCondition.getSelectedItem())); }
    if(slip.getItems().isEmpty()){ JOptionPane.showMessageDialog(this,"Vui lòng chọn ít nhất 1 mặt hàng"); return; }
    if(rdExchange.isSelected()){ new SearchItemFrm(slip,0).setVisible(true); }
    else { slip.calcMoney(); new ConfirmReturnFrm(slip).setVisible(true); }
    dispose(); } }
