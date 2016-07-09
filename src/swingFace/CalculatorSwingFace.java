package swingFace;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import brain.*;

public class CalculatorSwingFace extends JFrame {

	private JPanel contentPane;
	private JTextField txtDisplay;
	private Calculator C;
	private String strBuffer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalculatorSwingFace frame = new CalculatorSwingFace();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CalculatorSwingFace() {
		
		C=new Calculator();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 542, 348);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtDisplay = new JTextField();
		txtDisplay.setBounds(32, 25, 488, 50);
		contentPane.add(txtDisplay);
		txtDisplay.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBounds(32, 87, 162, 207);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnSin = new JButton("sin");
		btnSin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=btnSin.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt+"(");
			}
		});
		btnSin.setBounds(6, 6, 53, 29);
		panel.add(btnSin);
		
		JButton btnCos = new JButton("cos");
		btnCos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=btnCos.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt+"(");
			}
		});
		btnCos.setBounds(56, 6, 53, 29);
		panel.add(btnCos);
		
		JButton btnTan = new JButton("tan");
		btnTan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=btnTan.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt+"(");
			}
		});
		btnTan.setBounds(103, 6, 53, 29);
		panel.add(btnTan);
		
		JButton btnCsc = new JButton("csc");
		btnCsc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=btnCsc.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt+"(");
			}
		});
		btnCsc.setBounds(6, 36, 53, 29);
		panel.add(btnCsc);
		
		JButton btnSec = new JButton("sec");
		btnSec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=btnSec.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt+"(");
			}
		});
		btnSec.setBounds(55, 36, 53, 29);
		panel.add(btnSec);
		
		JButton btnCot = new JButton("cot");
		btnCot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=btnCot.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt+"(");
			}
		});
		btnCot.setBounds(103, 36, 53, 29);
		panel.add(btnCot);
		
		JButton btnAsin = new JButton("asin");
		btnAsin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=btnAsin.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt+"(");
			}
		});
		btnAsin.setBounds(6, 86, 53, 29);
		panel.add(btnAsin);
		
		JButton btnAcos = new JButton("acos");
		btnAcos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=btnAcos.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt+"(");
			}
		});
		btnAcos.setBounds(56, 86, 53, 29);
		panel.add(btnAcos);
		
		JButton btnAtan = new JButton("atan");
		btnAtan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=btnAtan.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt+"(");
			}
		});
		btnAtan.setBounds(103, 86, 53, 29);
		panel.add(btnAtan);
		
		JButton btnAcsc = new JButton("acsc");
		btnAcsc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=btnAcsc.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt+"(");
			}
		});
		btnAcsc.setBounds(6, 115, 53, 29);
		panel.add(btnAcsc);
		
		JButton btnAsec = new JButton("asec");
		btnAsec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=btnAsec.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt+"(");
			}
		});
		btnAsec.setBounds(56, 115, 53, 29);
		panel.add(btnAsec);
		
		JButton btnAcot = new JButton("acot");
		btnAcot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=btnAcot.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt+"(");
			}
		});
		btnAcot.setBounds(103, 115, 53, 29);
		panel.add(btnAcot);
		
		JButton btnLn = new JButton("ln");
		btnLn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=btnLn.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt+"(");
			}
		});
		btnLn.setBounds(6, 172, 75, 29);
		panel.add(btnLn);
		
		JButton btnLog = new JButton("log10");
		btnLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=btnLog.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt+"(");
			}
		});
		btnLog.setBounds(81, 172, 75, 29);
		panel.add(btnLog);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(370, 86, 147, 208);
		contentPane.add(panel_1);
		
		JButton btn1 = new JButton("1");
		btn1.setBounds(6, 6, 49, 29);
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=btn1.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt);
			}
		});
		panel_1.setLayout(null);
		panel_1.add(btn1);
		
		JButton btn2 = new JButton("2");
		btn2.setBounds(49, 6, 49, 29);
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=btn2.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt);
			}
		});
		panel_1.add(btn2);
		
		JButton btn3 = new JButton("3");
		btn3.setBounds(92, 6, 49, 29);
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=btn3.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt);
			}
		});
		panel_1.add(btn3);
		
		JButton btn4 = new JButton("4");
		btn4.setBounds(6, 34, 49, 29);
		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=btn4.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt);
			}
		});
		panel_1.add(btn4);
		
		JButton btn5 = new JButton("5");
		btn5.setBounds(49, 34, 49, 29);
		btn5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=btn5.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt);
			}
		});
		panel_1.add(btn5);
		
		JButton btn6 = new JButton("6");
		btn6.setBounds(92, 34, 49, 29);
		btn6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=btn6.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt);
			}
		});
		panel_1.add(btn6);
		
		JButton btn7 = new JButton("7");
		btn7.setBounds(6, 63, 49, 29);
		btn7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=btn7.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt);
			}
		});
		panel_1.add(btn7);
		
		JButton btn8 = new JButton("8");
		btn8.setBounds(49, 63, 49, 29);
		btn8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=btn8.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt);
			}
		});
		panel_1.add(btn8);
		
		JButton btn9 = new JButton("9");
		btn9.setBounds(92, 63, 49, 29);
		btn9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=btn9.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt);
			}
		});
		panel_1.add(btn9);
		
		JButton btn0 = new JButton("0");
		btn0.setBounds(49, 92, 49, 29);
		btn0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=btn0.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt);
			}
		});
		panel_1.add(btn0);
		
		JButton btnOpenPar = new JButton("(");
		btnOpenPar.setBounds(6, 92, 49, 29);
		btnOpenPar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=btnOpenPar.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt);
			}
		});
		panel_1.add(btnOpenPar);
		
		JButton btnClosePar = new JButton(")");
		btnClosePar.setBounds(92, 92, 49, 29);
		btnClosePar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=btnClosePar.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt);
			}
		});
		panel_1.add(btnClosePar);
		
		JButton button_5 = new JButton("!");
		button_5.setBounds(6, 144, 49, 29);
		panel_1.add(button_5);
		
		JButton button_6 = new JButton("^");
		button_6.setBounds(49, 144, 49, 29);
		panel_1.add(button_6);
		
		JButton button_7 = new JButton("*");
		button_7.setBounds(92, 144, 49, 29);
		panel_1.add(button_7);
		
		JButton button_8 = new JButton("/");
		button_8.setBounds(6, 173, 49, 29);
		panel_1.add(button_8);
		
		JButton button_9 = new JButton("+");
		button_9.setBounds(49, 173, 49, 29);
		panel_1.add(button_9);
		
		JButton button_10 = new JButton("-");
		button_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=button_10.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt);
			}
		});
		button_10.setBounds(92, 173, 49, 29);
		panel_1.add(button_10);
		
		JButton btnPoint = new JButton(".");
		btnPoint.setBounds(49, 118, 49, 29);
		panel_1.add(btnPoint);
		btnPoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=btnPoint.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt);
			}
		});
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=button_9.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt);
			}
		});
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=button_8.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt);
			}
		});
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=button_7.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt);
			}
		});
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=button_6.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt);
			}
		});
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String btnTxt=button_5.getText();
				txtDisplay.setText(txtDisplay.getText()+btnTxt);
			}
		});
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(206, 87, 152, 207);
		contentPane.add(panel_3);
		
		JRadioButton rdbtnStepMode = new JRadioButton("Step Mode");
		rdbtnStepMode.setBounds(25, 6, 114, 23);
		rdbtnStepMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(C.stepQ)
					C.stepQ=false;
				else
					C.stepQ=true;
			}
		});
		panel_3.setLayout(null);
		panel_3.add(rdbtnStepMode);
		
		JButton btnSolve = new JButton("Solve");
		btnSolve.setBounds(15, 118, 124, 29);
		btnSolve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				strBuffer=txtDisplay.getText();
				String answer=C.DrillDownSolveAndSub(txtDisplay.getText());
				txtDisplay.setText(answer);
			}
		});
		panel_3.add(btnSolve);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(83, 159, 56, 29);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtDisplay.setText("");
			}
		});
		panel_3.add(btnClear);
		
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(15, 159, 56, 29);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String oldStr=txtDisplay.getText();
				if(oldStr.contains("Error")){
					txtDisplay.setText(strBuffer);
				}
				else{
					int len=oldStr.length();
					txtDisplay.setText(oldStr.substring(0,len-1));
				}
				
			}
		});
		panel_3.add(btnBack);
		
		JRadioButton rdbtnRadianMode = new JRadioButton("Radian Mode");
		rdbtnRadianMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(C.inRadians)
					C.inRadians=false;
				else
					C.inRadians=true;
			}
		});
		rdbtnRadianMode.setBounds(25, 35, 121, 23);
		panel_3.add(rdbtnRadianMode);
	}
}
