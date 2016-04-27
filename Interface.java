/*
	Simple graphical interface to be able to retrive information about an entity from the knowledge. 
	Mainly used during development.
*/
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Interface implements ActionListener{
	
	Querying q = new Querying();
	
	JTextField in_textfield;
	JTextField in_context;
	JTextArea out_textarea;
	ButtonGroup button_group;
	
	
	Interface(){
		
		
		JFrame frame = new JFrame("Ask a question");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		
		JPanel input_panel = new JPanel();
		//input_panel.setLayout(new BorderLayout());
		frame.add(input_panel, BorderLayout.NORTH);
		
		JPanel i = new JPanel();
		i.setLayout(new BorderLayout());
		input_panel.add(i);
		JLabel q_label = new JLabel("Question:  ");
		q_label.setLabelFor(in_textfield);
		i.add(q_label,BorderLayout.WEST);
		in_textfield = new JTextField(30);
		i.add(in_textfield,BorderLayout.CENTER);
		
		JPanel c = new JPanel();
		c.setLayout(new BorderLayout());
		input_panel.add(c);
		JLabel c_label = new JLabel("Context:  ");
		c_label.setLabelFor(in_context);
		c.add(c_label,BorderLayout.WEST);
		in_context = new JTextField(30);
		c.add(in_context,BorderLayout.CENTER);
		
		
		JPanel button_panel = new JPanel();
		button_panel.setLayout(new GridLayout(0,4));
		input_panel.add(button_panel, BorderLayout.SOUTH);
		
		button_group = new ButtonGroup();
		JRadioButton c_but1 = new JRadioButton("Person");
		button_group.add(c_but1);
		button_panel.add(c_but1);
		
		JRadioButton c_but2 = new JRadioButton("City");
		button_group.add(c_but2);
		button_panel.add(c_but2);
		
		JRadioButton c_but4 = new JRadioButton("Location");
		button_group.add(c_but4);
		button_panel.add(c_but4);
		
		JRadioButton c_but3 = new JRadioButton("Thing");
		button_group.add(c_but3);
		button_panel.add(c_but3);
		
		JButton sub_but = new JButton("Submit");
		frame.add(sub_but,BorderLayout.SOUTH);
		sub_but.addActionListener(this);
		out_textarea = new JTextArea("");
		out_textarea.setPreferredSize(new Dimension(250,250));;
		frame.add(out_textarea, BorderLayout.CENTER);
		
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		
	}

	public void actionPerformed(ActionEvent arg0) {
		out_textarea.setText("Fetching data....");
		String indata = in_textfield.getText();
		String type = "";
		for (Enumeration<AbstractButton> buttons = button_group.getElements(); buttons.hasMoreElements();){
			 AbstractButton button = buttons.nextElement();
	          if (button.isSelected()) {
	               type = button.getText();
	           }
		}
		String[] context = {""};
		Carrier carrier = q.querying(in_textfield.getText(), context, type);
	
		context[0]=in_context.getText();
		//ArrayList<String> Results = query.Count(indata,type, context);
		//carrier = p.personTemp(Results, context);
		
		out_textarea.setText(carrier.toString());
		//TestURLImage getimg = new TestURLImage(carrier.getValue("dbo:thumbnail"));
		
	}
}
