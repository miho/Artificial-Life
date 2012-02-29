package net.cammann.tom.fyp.gp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.cammann.tom.fyp.basicLife.BasicMap;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.SimulationContext;
import net.cammann.tom.fyp.gui.SimulationFrame;

public class GPVisual extends JFrame {
	
	private final GeneticProgramFrame gpf;
	
	public GPVisual(final GeneticProgramFrame gpf) {
		this.gpf = gpf;
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		JPanel jp = new JPanel();
		
		JButton jb = new JButton("Launch Best");
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (gpf.fittest != null) {
					EnvironmentMap map = new BasicMap();
					
					final SimulationContext sc = new SimulationContext(map);
					
					sc.addLife(new ALifeGP(gpf.fittest, map));
					
					sc.initSimulation();
					sc.setVerbosity(0);
					sc.setTimerListener();
					
					final SimulationFrame sf = new SimulationFrame(sc);
					
					javax.swing.SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							SimulationContext.createAndShowGUI(sf, sc);
						}
					});
				}
			}
		});
		jp.add(jb);
		
		setContentPane(jp);
		
		setSize(200, 100);
		setLocationByPlatform(true);
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				
				createAndShowGui();
			}
			
		});
	}
	
	private void createAndShowGui() {
		setVisible(true);
		
	}
}
