package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Gui extends JFrame implements ActionListener{
	
	JButton start;
	public Gui(){
		start=new JButton("start");
		setLayout(new GridLayout());
		start.setBounds(100, 100, 200, 200);
		start.addActionListener(this);
		getContentPane().add(start);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(start)){
		}
		
	}
	
	
	
	
	
	
	
	

}
