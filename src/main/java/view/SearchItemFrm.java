package view;
import javax.swing.*; import javax.swing.table.DefaultTableModel; import java.awt.*; import java.util.*; import java.util.List;
import dao.ItemDAO; import model.*;
/** Chọn mặt hàng mới cho từng dòng trả (index) */
public class SearchItemFrm extends JFrame {
  private final ReturnSlip slip; private final int index; private final JTextField txtKey=new JTextField(15);
  private final DefaultTableModel model=new DefaultTableModel(new Object[]{"STT","Tên","Giá","Tồn kho"},0){ public boolean isCellEditable(int r,int c){return false;} };
  private final JTable tbl=new JTable(model); private final JSpinner spQty; private List<Item> result=new ArrayList<>();
  public SearchItemFrm(ReturnSlip slip,int index){ super("Tìm mặt hàng mới"); this.slip=slip; this.index=index;
    ReturnItem ri=slip.getItems().get(index); spQty=new JSpinner(new SpinnerNumberModel(ri.getReturnQty(),1,9999,1));
    JPanel top=new JPanel(); JButton btnSearch=new JButton("Tìm"); top.add(new JLabel("Đổi \""+ri.getInvoiceItem().getItem().getName()+"\" x"+ri.getReturnQty()+" sang:"));
    top.add(txtKey); top.add(btnSearch);
    JPanel bot=new JPanel(); JButton btnChoose=new JButton("Chọn"), btnBack=new JButton("Hủy"); bot.add(new JLabel("SL:")); bot.add(spQty); bot.add(btnChoose); bot.add(btnBack);
    btnSearch.addActionListener(e->search()); txtKey.addActionListener(e->search()); btnChoose.addActionListener(e->choose());
    btnBack.addActionListener(e->{ new SellerHomeFrm(slip.getStaff()).setVisible(true); dispose(); });
    JPanel p=new JPanel(new BorderLayout()); p.add(top,BorderLayout.NORTH); p.add(new JScrollPane(tbl),BorderLayout.CENTER); p.add(bot,BorderLayout.SOUTH);
    setContentPane(p); setSize(600,350); setLocationRelativeTo(null); setDefaultCloseOperation(EXIT_ON_CLOSE); }
  private void search(){ result=new ItemDAO().searchItem(txtKey.getText()); model.setRowCount(0); int i=1;
    for(Item it:result) model.addRow(new Object[]{i++,it.getName(),it.getPrice(),it.getStock()});
    if(result.isEmpty()) JOptionPane.showMessageDialog(this,"Không có mặt hàng phù hợp"); }
  private void choose(){ int r=tbl.getSelectedRow(); if(r<0){ JOptionPane.showMessageDialog(this,"Vui lòng chọn mặt hàng"); return; }
    Item it=result.get(r); int q=(Integer)spQty.getValue();
    if(q>it.getStock()){ JOptionPane.showMessageDialog(this,"Mặt hàng không đủ tồn kho (còn "+it.getStock()+")"); return; }
    ReturnItem ri=slip.getItems().get(index); ri.setNewItem(it); ri.setNewQty(q);
    if(index+1<slip.getItems().size()) new SearchItemFrm(slip,index+1).setVisible(true);
    else { slip.calcMoney(); new ConfirmReturnFrm(slip).setVisible(true); }
    dispose(); } }
