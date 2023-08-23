package org.sp.mvcapp.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.sp.mvcapp.model.blood.BloodManager;

public class BloodForm extends JFrame{
	JComboBox box;
	JButton bt;
	
	public BloodForm() {
		box = new JComboBox();
		bt = new JButton("결과보기");
		
		//혈액형 채우기 
		box.addItem("A");
		box.addItem("B");
		box.addItem("AB");
		box.addItem("O");
		box.setPreferredSize(new Dimension(280, 40));
		setLayout(new FlowLayout());
		add(box);
		add(bt);
		
		setSize(300,150);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		bt.addActionListener((e)->{
			getResult();
		});
		
	}
	public void getResult() {
		BloodManager manager=new BloodManager();
		String msg=manager.getAdvice((String)box.getSelectedItem());
		JOptionPane.showMessageDialog(this, msg);
	}
	
	public static void main(String[] args) {
		new BloodForm();

	}

}


