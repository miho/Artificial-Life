package net.cammann.tom.fyp.gp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.EvolutionFactory;
import net.cammann.tom.fyp.core.SimulationContext;
import net.cammann.tom.fyp.gui.SimulationFrame;

public class GPVisual extends JFrame {
	
	// private final GeneticProgramFrame gpf;
	// private final EvolutionFactory factory;
	
	public GPVisual(final GeneticProgramFrame gpf,
			final EvolutionFactory factory) {
		// this.gpf = gpf;
		// this.factory = factory;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		JPanel jp = new JPanel();
		// setUndecorated(true);
		JButton jb = new JButton("Launch Best");
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (GeneticProgramFrame.fittest != null) {
					EnvironmentMap map = factory.createMap();
					
					final SimulationContext sc = new SimulationContext(map);
					
					sc.addLife(new ALifeGP(GeneticProgramFrame.fittest, map));
					
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
		
		setSize(200, 80);
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
