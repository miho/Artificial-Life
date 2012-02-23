package net.cammann.tom.fyp.gui;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

public class LoggingFrame extends JFrame {
	
	JTextArea textArea;
	JScrollPane jsp;
	
	private final SimulationFrame simFrame;
	
	public LoggingFrame(SimulationFrame sf) {
		simFrame = sf;
		JPanel jpanel = new JPanel();
		jpanel.setLayout(new BorderLayout());
		this.textArea = new JTextArea();
		jsp = new JScrollPane(textArea);
		setContentPane(jpanel);
		jpanel.add(jsp);
		// this.setContentPane(panel);
		this.setSize(400, 600);
		this.setLocationByPlatform(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		jpanel.add(createToolBar(), BorderLayout.SOUTH);
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				setVisible(false);
				
			}
		});
		
	}
	
	private JToolBar createToolBar() {
		String[] petStrings = { "All", "Bug", "Brain", "SimContext" };
		JToolBar jtb = new JToolBar();
		JComboBox dropList = new JComboBox(petStrings);
		dropList.setSelectedIndex(0);
		dropList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				simFrame.hideLogFrame();
			}
		});
		jtb.add(dropList);
		return jtb;
	}
	
	public void appendLine(String line) {
		textArea.append(line + "\n");
		textArea.scrollRectToVisible(new Rectangle(0, textArea.getHeight() - 2,
				1, 1));
		
	}
}
