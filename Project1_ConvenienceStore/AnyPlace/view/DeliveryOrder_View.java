package AnyPlace.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import AnyPlace.view.View;
import AnyPlace.controller.DeliveryCont;
import AnyPlace.mouseEventListener.DeliveryView_MouseEventListener;

public class DeliveryOrder_View extends JPanel {
//	ImageIcon icon;
//
//	public Dimension getPreferredSize() {
//		Dimension largeBtnSz = new Dimension(super.getPreferredSize().width + 30, super.getPreferredSize().height + 30);
//		return largeBtnSz;
//	}
	JTable table;
	JScrollPane scroll; // 테이블 위에 열 라벨을 넣어주자~ scroll
	
	String col_name[] = { "중분류", "상품코드", "상품명", "구매수량" };
	String data[][];// {{ "1.도시락", "1", "알찬도시락", "1", "06-11 00:00" }};
	DeliveryCont deliveryCont = new DeliveryCont();
	private JButton button;
	private JButton button_1;

	// 주문 수량, 원가
	private int cnt = 0;
	private int total_cost = 0;

	int row;
	DeliveryView_MouseEventListener dvm = new DeliveryView_MouseEventListener(table);
	public Dimension getPreferredSize() {
		Dimension largeBtnSz = new Dimension(super.getPreferredSize().width + 30, super.getPreferredSize().height + 30);
		return largeBtnSz;
	}

	public DeliveryOrder_View() {
//		setTitle("Any Place");
//		getContentPane().setLayout(null);
//		try {
//			final Image backgroundImage = javax.imageio.ImageIO.read(new File("./img/애니플_보드.jpg"));
//			setContentPane(new JPanel(new BorderLayout()) {
//				@Override
//				public void paintComponent(Graphics g) {
//					g.drawImage(backgroundImage, 0, 0, null);
//					setOpaque(false);
//					super.paintComponent(g);
//				}
//			});
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}

		setBackground(Color.WHITE);

		// 테이블
		data = deliveryCont.allProductsCount();
		DefaultTableModel model = new DefaultTableModel(data, col_name) {
			@Override
			public boolean isCellEditable(int row, int column) {
				if (column >= 0) {
					return false;
				} else {
					return true;
				}
			}
		};
		table = new JTable(model);

		table.getTableHeader().setFont(new Font("나눔고딕", Font.BOLD, 20)); // 컬럼 폰트 
		table.getTableHeader().setBackground(new Color(22,56,81)); 	// 컬럼 배경색
		table.getTableHeader().setForeground(new Color(255,255,255)); // 컬럼 폰트색
		table.getTableHeader().setPreferredSize(new Dimension(0, 60)); // 컬럼 너비, 높이
		table.getTableHeader().setReorderingAllowed(false); // 컬럼 이동 안되게
		
		// 셀들의 글자 가운데 정렬 
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = table.getColumnModel();
		for(int i=0; i<table.getColumnCount(); i++) {
			tcm.getColumn(i).setCellRenderer(dtcr);
		}
		table.setRowHeight(40);  // 행 높이
		table.setFont(new Font("나눔고딕", Font.BOLD, 15)); // 행 폰트
		table.setBackground(new Color(198,198,198)); // 행 배경색
 		table.setForeground(new Color(22,56,81)); // 행 폰트색
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); // 단일선택
		table.setRowSelectionAllowed(true); // 셀 선택되는 색이 ..
		table.addMouseListener(new DeliveryView_MouseEventListener(table)); // table전용 MouseListener
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scroll = new JScrollPane(table);
		scroll.setBounds(0, 20, 851, 392);
		add(scroll);

		JTextArea textArea = new JTextArea(" 합계수량 "); 
		textArea.setForeground(new Color(254,182,63));
		textArea.setFont(new Font("나눔고딕", Font.BOLD, 30));
		textArea.setEditable(false);
		textArea.setBackground(new Color(22,56,81));
		textArea.setBounds(0, 416, 200 ,91);
		add(textArea);
		
		JTextArea textArea_1 = new JTextArea(""+cnt);
		textArea_1.setForeground(new Color(255,255,255));
		textArea_1.setFont(new Font("나눔고딕", Font.BOLD, 30));
		textArea_1.setEditable(false);
		textArea_1.setBackground(new Color(22, 56, 81));
		textArea_1.setBounds(200, 416, 274, 91);
		add(textArea_1);
		
		JTextArea textArea_2 = new JTextArea("합계 금액");
		textArea_2.setForeground(new Color(254, 182, 63));
		textArea_2.setFont(new Font("나눔고딕", Font.BOLD, 30));
		textArea_2.setEditable(false);
		textArea_2.setBackground(new Color(22, 56, 81));
		textArea_2.setBounds(474, 416, 206, 91);
		add(textArea_2);
		
		JTextArea textArea_3 = new JTextArea(Integer.toString(total_cost));
		textArea_3.setForeground(Color.WHITE);
		textArea_3.setFont(new Font("나눔고딕", Font.BOLD, 30));
		textArea_3.setEditable(false);
		textArea_3.setBackground(new Color(22, 56, 81));
		textArea_3.setBounds(679, 416, 172, 91);
		add(textArea_3);
		
		button_1 = new JButton(new ImageIcon("./img/menu_C/주문.png"));
		button_1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				dvm.addProducts(table);

				JDialog dialog = new JDialog(); // 팝업창
				JButton btn = new JButton(new ImageIcon ("./img/menu_C/확인.png")); // 확인버튼
				JTextField tf = new JTextField("합계수량 "+ cnt + "  원가 금액 합계 " + total_cost + " 주문 완료"); // 팝업 text
				
				//확인버튼 
				btn.setBorderPainted(false);
				btn.setContentAreaFilled(false);
				btn.setFocusPainted(false);
				btn.setLocation(24, 34);
				
				//text
				tf.setEditable(false);
				tf.setBackground(Color.white);
				tf.setFont(new Font("나눔고딕", Font.BOLD, 15));
				tf.setEditable(false);
				tf.setForeground(new Color(22, 56, 81));
				tf.setBorder(null);
				// 팝업
				dialog.setLayout(new FlowLayout());
				dialog.getContentPane().setBackground(Color.white);
				dialog.add(tf);
				dialog.add(btn);
				dialog.setBounds(500,400,400,100);
				
				dialog.setVisible(true);
				btn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dialog.setVisible(false);
						View.paymentManagerment_refresh();
					}
				});
			}
			
		});
		button_1.setBackground(new Color(72, 61, 139));
		button_1.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		button_1.setContentAreaFilled(false);
		button_1.setFocusPainted(false);
		button_1.setBounds(661, 513, 200, 68);
		button_1.setPressedIcon(new ImageIcon("./img/menu_C/주문B.png"));
		button_1.setBorderPainted(false);
		button_1.setContentAreaFilled(false);
		add(button_1);
		
		JButton btnNewButton_2 = new JButton(new ImageIcon("./img/menu_C/플러스.png"));
		btnNewButton_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int temp;
				
				for(int i=0; i<dvm.nSelectedRow.length; i++) {
					temp = 0;
					temp = Integer.parseInt(String.valueOf(table.getValueAt(dvm.nSelectedRow[i], 3))); //temp = 현재 선택된 셀들의 [i] 번째 셀의 수량
					table.setValueAt(temp+1,dvm.nSelectedRow[i], 3); // 선택된 셀의 수량을 현재 수량 + 1 아래 버튼들도 동일한 방법
					cnt++; // 물품 총 수량 ++
					total_cost = dvm.getTotalCost(table); // 물품 총 원가 
//					textArea.setText("합계수량  " + cnt + "\t\t원가 금액 합계  " + total_cost);  
					textArea_1.setText(""+cnt);
					textArea_3.setText(""+total_cost);
				}
			}
		});
		btnNewButton_2.setBounds(0, 513, 70, 70);
		btnNewButton_2.setPressedIcon(new ImageIcon("./img/menu_C/플러스B.png"));
		btnNewButton_2.setBorderPainted(false);
		btnNewButton_2.setContentAreaFilled(false);
		add(btnNewButton_2);
		
		JButton button_2 = new JButton(new ImageIcon("./img/menu_C/플러스10.png"));
		button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int temp;
				for(int i=0; i<dvm.nSelectedRow.length; i++) {
					temp = 0;
					temp = Integer.parseInt(String.valueOf(table.getValueAt(dvm.nSelectedRow[i], 3)));
					table.setValueAt(temp+10,dvm.nSelectedRow[i], 3);
					cnt = cnt+10;
					total_cost = dvm.getTotalCost(table); // 물품 총 원가 
//					textArea.setText("합계수량  " + cnt + "\t\t원가 금액 합계  " + total_cost);  
					textArea_1.setText(""+cnt);
					textArea_3.setText(""+total_cost);
				}
			}
		});
		button_2.setBounds(115, 513, 70, 70);
		button_2.setPressedIcon(new ImageIcon("./img/menu_C/플러스10B.png"));
		button_2.setBorderPainted(false);
		button_2.setContentAreaFilled(false);
		add(button_2);
		
		JButton button_3 = new JButton(new ImageIcon("./img/menu_C/플러스100.png"));
		button_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int temp;
				for(int i=0; i<dvm.nSelectedRow.length; i++) {
					temp = 0;
					temp = Integer.parseInt(String.valueOf(table.getValueAt(dvm.nSelectedRow[i], 3)));
					table.setValueAt(temp+100,dvm.nSelectedRow[i], 3);
					cnt = cnt+100;
					total_cost = dvm.getTotalCost(table); // 물품 총 원가 
//					textArea.setText("합계수량  " + cnt + "\t\t원가 금액 합계  " + total_cost);  
					textArea_1.setText(""+cnt);
					textArea_3.setText(""+total_cost);
				}
			}
		});
		button_3.setBounds(230, 513, 70, 70);
		button_3.setPressedIcon(new ImageIcon("./img/menu_C/플러스100B.png"));
		button_3.setBorderPainted(false);
		button_3.setContentAreaFilled(false);
		add(button_3);
		
		JButton button_4 = new JButton(new ImageIcon("./img/menu_C/마이너스.png"));
		button_4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int temp;
				for(int i=0; i<dvm.nSelectedRow.length; i++) {
					temp = 0;
					temp = Integer.parseInt(String.valueOf(table.getValueAt(dvm.nSelectedRow[i], 3)));
					if(Integer.parseInt(String.valueOf(table.getValueAt(dvm.nSelectedRow[i], 3))) != 0) {
						table.setValueAt(temp-1,dvm.nSelectedRow[i], 3);
						cnt--;
						total_cost = dvm.getTotalCost(table); // 물품 총 원가 
//						textArea.setText("합계수량  " + cnt + "\t\t원가 금액 합계  " + total_cost);
						textArea_1.setText(""+cnt);
						textArea_3.setText(""+total_cost);
					}
				}
			}
		});
		button_4.setBounds(345, 513, 70, 70);
		button_4.setPressedIcon(new ImageIcon("./img/menu_C/마이너스B.png"));
		button_4.setBorderPainted(false);
		button_4.setContentAreaFilled(false);
		add(button_4);
		
		JButton button_5 = new JButton(new ImageIcon("./img/menu_C/마이너스10.png"));
		button_5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int temp;
				for(int i=0; i<dvm.nSelectedRow.length; i++) {
					temp = 0;
					// temp = 현재 선택된 셀들의 [i] 번째 셀의 수량 정보
					temp = Integer.parseInt(String.valueOf(table.getValueAt(dvm.nSelectedRow[i], 3)));
					// 현재 셀의 수량이 0보다 작거나, -10했을때 0 보다 작을 경우 수량을 0 으로 초기화
					if(Integer.parseInt(String.valueOf(table.getValueAt(dvm.nSelectedRow[i], 3))) <= 0 || 
							Integer.parseInt(String.valueOf(table.getValueAt(dvm.nSelectedRow[i], 3))) -10 <= 0) {
						table.setValueAt(0,dvm.nSelectedRow[i], 3);
						cnt = cnt-(temp);
						total_cost = dvm.getTotalCost(table); // 물품 총 원가 
//						textArea.setText("합계수량  " + cnt + "\t\t원가 금액 합계  " + total_cost);
						textArea_1.setText(""+cnt);
						textArea_3.setText(""+total_cost);
					// 현재 셀이 10보다 클경우 -10 정상 동작
					}else if(Integer.parseInt(String.valueOf(table.getValueAt(dvm.nSelectedRow[i], 3))) > 10) {
						table.setValueAt(temp-10,dvm.nSelectedRow[i], 3);
						cnt = cnt-10;
						total_cost = dvm.getTotalCost(table); // 물품 총 원가 
//						textArea.setText("합계수량  " + cnt + "\t\t원가 금액 합계  " + total_cost);
						textArea_1.setText(""+cnt);
						textArea_3.setText(""+total_cost);
					}
				}
			}
		});
		button_5.setBounds(460, 513, 70, 70);
		button_5.setPressedIcon(new ImageIcon("./img/menu_C/마이너스10B.png"));
		button_5.setBorderPainted(false);
		button_5.setContentAreaFilled(false);
		add(button_5);
		
		JButton button_6 = new JButton(new ImageIcon("./img/menu_C/마이너스100.png"));
		button_6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int temp;
				for(int i=0; i<dvm.nSelectedRow.length; i++) {
					temp = 0;
					temp = Integer.parseInt(String.valueOf(table.getValueAt(dvm.nSelectedRow[i], 3)));
					if(Integer.parseInt(String.valueOf(table.getValueAt(dvm.nSelectedRow[i], 3))) <= 0 || Integer.parseInt(String.valueOf(table.getValueAt(dvm.nSelectedRow[i], 3))) -100 <= 0) {
						table.setValueAt(0,dvm.nSelectedRow[i], 3);
						cnt = cnt-(temp);
						total_cost = dvm.getTotalCost(table); // 물품 총 원가 
//						textArea.setText("합계수량  " + cnt + "\t\t원가 금액 합계  " + total_cost);
						textArea_1.setText(""+cnt);
						textArea_3.setText(""+total_cost);
					}else if(Integer.parseInt(String.valueOf(table.getValueAt(dvm.nSelectedRow[i], 3))) > 10) {
						table.setValueAt(temp-100,dvm.nSelectedRow[i], 3);
						cnt = cnt-100;
						total_cost = dvm.getTotalCost(table); // 물품 총 원가 
//						textArea.setText("합계수량  " + cnt + "\t\t원가 금액 합계  " + total_cost);
						textArea_1.setText(""+cnt);
						textArea_3.setText(""+total_cost);
					}
				}
			}
		});
		button_6.setPressedIcon(new ImageIcon("./img/menu_C/마이너스100B.png"));
		button_6.setBorderPainted(false);
		button_6.setContentAreaFilled(false);
		button_6.setBounds(575, 513, 70, 70);
		add(button_6);

		setLocation(70, 50);
		setSize(1467, 902);
		setLayout(null);
		setVisible(true);


	}
}
