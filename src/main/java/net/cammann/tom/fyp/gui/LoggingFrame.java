package net.cammann.tom.fyp.gui;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import net.cammann.tom.fyp.utils.VisibilityEvent;
import net.cammann.tom.fyp.utils.VisibilityListener;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

/**
 * <p>
 * LoggingFrame class.
 * </p>
 * 
 * @author tc
 * @version $Id: $
 */
public class LoggingFrame {

	/**
	 * Logger.
	 */
	private final Logger logger = Logger.getLogger(LoggingFrame.class);

	/**
	 * Holds limited list of all debugging strings.
	 */
	List<String> loggingStrings = new ArrayList<String>();
	/**
	 * Combobox for choosing what logging information is shown.
	 */
	private final JComboBox dropList;
	/**
	 * Strings used for the comboBox.
	 */
	private final String[] petStrings;
	/**
	 * Singleton instance of loggingFrame.
	 */
	private static final LoggingFrame logFrame = new LoggingFrame();
	/**
	 * JFrame that logging frame uses.
	 */
	private final JFrame coreFrame;
	/**
	 * Area that the logging output spews to.
	 */
	private JTextArea textArea;
	/**
	 * Pane to enable scrolling as logging is appended.
	 */
	private JScrollPane jsp;

	private static List<VisibilityListener> listeners = new ArrayList<VisibilityListener>();

	/**
	 * <p>
	 * Constructor for LoggingFrame.
	 * </p>
	 * 
	 * @param sf
	 *            a {@link net.cammann.tom.fyp.gui.SimulationFrame} object.
	 */
	private LoggingFrame() {
		coreFrame = new JFrame("Logging Frame");
		final JPanel jpanel = new JPanel();
		petStrings = new String[] { "All", "Debug", "Info", "Error" };
		dropList = new JComboBox(petStrings);
		jpanel.setLayout(new BorderLayout());
		this.textArea = new JTextArea();
		jsp = new JScrollPane(textArea);
		coreFrame.setContentPane(jpanel);
		jpanel.add(jsp);
		// this.setContentPane(panel);
		coreFrame.setSize(600, 600);
		coreFrame.setLocationByPlatform(true);
		coreFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		jpanel.add(createToolBar(), BorderLayout.SOUTH);
		coreFrame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(final WindowEvent arg0) {
				setVisible("windowClosing", false);
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
			protected void append(final LoggingEvent e) {
				StringBuilder sb = new StringBuilder();
				sb.append(e.getLevel());
				sb.append(" (");
				sb.append(e.getLocationInformation().getFileName());
				sb.append(":");
				sb.append(e.getLocationInformation().getLineNumber());
				sb.append(") [");
				sb.append(e.getLocationInformation().getMethodName());
				sb.append("] ").append(e.getMessage());

				appendLine(sb.toString());
			}
		});
	}

	public static LoggingFrame getInstance() {
		return logFrame;
	}

	private static void updateVisibilityListeners() {
		for (VisibilityListener i : listeners) {
			i.VisibilityChanged(new VisibilityEvent(LoggingFrame.getInstance()
					.isVisible()));
		}
	}

	public void addVisibilityListener(VisibilityListener listener) {
		listeners.add(listener);
	}

	public boolean isVisible() {
		return coreFrame.isVisible();
	}

	public void setVisible(String where, boolean bool) {
		coreFrame.setVisible(bool);
		logger.debug("Set logger visibility: " + bool + "FROM: " + where);
		updateVisibilityListeners();
	}

	private JToolBar createToolBar() {
		final JToolBar jtb = new JToolBar();

		dropList.setSelectedIndex(0);
		dropList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				reloadLog();
			}
		});
		jtb.add(dropList);
		return jtb;
	}

	private void reloadLog() {
		textArea.setText("");
		for (String i : loggingStrings) {
			if (liveLog(i)) {
				textArea.append(i + "\n");
			}
			textArea.scrollRectToVisible(new Rectangle(0,
					textArea.getHeight() - 2, 1, 1));
		}
	}

	private boolean liveLog(String line) {
		line = line.toUpperCase();
		if (dropList.getSelectedIndex() == 0) {
			return true;
		}
		if (dropList.getSelectedIndex() == 1) {
			if (line.startsWith("TRACE")) {
				return false;
			}
			return true;
		}
		if (dropList.getSelectedIndex() == 2) {
			if (line.startsWith("DEBUG") || line.startsWith("TRACE")) {
				return false;
			}
			return true;
		}
		if (dropList.getSelectedIndex() == 3) {
			if (line.startsWith("ERROR") || line.startsWith("FATAL")) {
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * <p>
	 * appendLine.
	 * </p>
	 * 
	 * @param line
	 *            a {@link java.lang.String} object.
	 */
	public void appendLine(final String line) {

		if (loggingStrings.size() > 1500) {
			loggingStrings.remove(0);
		}

		loggingStrings.add(line);

		if (liveLog(line)) {
			textArea.append(line + "\n");
			textArea.scrollRectToVisible(new Rectangle(0,
					textArea.getHeight() - 2, 1, 1));
		}
	}

	/**
	 * <p>
	 * appendLine.
	 * </p>
	 * 
	 * @param obj
	 *            a {@link java.lang.Object} object.
	 */
	public void appendLine(final Object obj) {
		appendLine(obj.toString());
	}
}
