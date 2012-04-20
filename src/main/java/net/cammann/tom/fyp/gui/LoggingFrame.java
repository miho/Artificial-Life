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

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

/**
 * <p>LoggingFrame class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public class LoggingFrame extends JFrame {
	
	JTextArea textArea;
	JScrollPane jsp;
	
	private final SimulationFrame simFrame;
	
	/**
	 * <p>Constructor for LoggingFrame.</p>
	 *
	 * @param sf a {@link net.cammann.tom.fyp.gui.SimulationFrame} object.
	 */
	public LoggingFrame(final SimulationFrame sf) {
		simFrame = sf;
		final JPanel jpanel = new JPanel();
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
			public void windowClosing(final WindowEvent arg0) {
				setVisible(false);
				
			}
		});
		
		Logger.getRootLogger().addAppender(new AppenderSkeleton() {
			
			@Override
			public boolean requiresLayout() {
				return false;
			}
			
			@Override
			public void close() {

			}
			
			@Override
			protected void append(final LoggingEvent arg0) {
				appendLine(arg0.getMessage());
			}
		});
	}
	
	private JToolBar createToolBar() {
		final String[] petStrings = { "All", "Bug", "Brain", "SimContext" };
		final JToolBar jtb = new JToolBar();
		final JComboBox dropList = new JComboBox(petStrings);
		dropList.setSelectedIndex(0);
		dropList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				simFrame.hideLogFrame();
			}
		});
		jtb.add(dropList);
		return jtb;
	}
	
	/**
	 * <p>appendLine.</p>
	 *
	 * @param line a {@link java.lang.String} object.
	 */
	public void appendLine(final String line) {
		textArea.append(line + "\n");
		textArea.scrollRectToVisible(new Rectangle(0, textArea.getHeight() - 2,
				1, 1));
		
	}
	
	/**
	 * <p>appendLine.</p>
	 *
	 * @param obj a {@link java.lang.Object} object.
	 */
	public void appendLine(final Object obj) {
		appendLine(obj.toString());
	}
}
