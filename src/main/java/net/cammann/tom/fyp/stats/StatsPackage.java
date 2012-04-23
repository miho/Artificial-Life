package net.cammann.tom.fyp.stats;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import net.cammann.tom.fyp.core.EvolutionCycleEvent;
import net.cammann.tom.fyp.utils.BucketList;
import net.cammann.tom.fyp.utils.ListChangeEvent;
import net.cammann.tom.fyp.utils.ListChangeListener;
import net.cammann.tom.fyp.utils.WatchableList;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jgap.Population;
import org.jgap.gp.impl.GPPopulation;

/**
 * <p>
 * StatsPackage class.
 * </p>
 * 
 * @author tc
 * @version $Id: $
 */
public final class StatsPackage {
	
	/**
	 * Logger.
	 */
	private transient Logger logger = Logger.getLogger(StatsPackage.class);
	
	/**
	 * List that can be watched for changes.
	 */
	private final WatchableList<GenerationInformation> stats;
	/**
	 * Default graph size.
	 */
	private static final Dimension DEFAULT_GRAPH_SIZE = new Dimension(600, 400);
	
	/**
	 * <p>
	 * Constructor for StatsPackage.
	 * </p>
	 */
	public StatsPackage() {
		stats = new WatchableList<GenerationInformation>();
	}
	
	/**
	 * <p>
	 * add.
	 * </p>
	 * 
	 * Directly add from evolution cycle event. This encapsulated both GP and GA
	 * information. Abstract away any differences.
	 * 
	 * @param e
	 *            a {@link net.cammann.tom.fyp.coreEvolutionCycleEvent} object.
	 */
	public void add(final EvolutionCycleEvent e) {
		if (e.isGeneticAlgorithm()) {
			add(e.getPopulation(), e.getGenerationNum());
		} else {
			add(e.getGPPopulation(), e.getGenerationNum());
		}
		
	}
	
	/**
	 * <p>
	 * add.
	 * </p>
	 * 
	 * @param pop
	 *            a {@link org.jgap.Population} object.
	 * @param id
	 *            a {@link java.lang.String} object.
	 * @param genNum
	 *            a int.
	 */
	public void add(final Population pop, final String id, final int genNum) {
		stats.add(new GenerationInformation(pop, id, genNum));
	}
	
	/**
	 * <p>
	 * add.
	 * </p>
	 * 
	 * @param pop
	 *            a {@link org.jgap.gp.impl.GPPopulation} object.
	 * @param id
	 *            a {@link java.lang.String} object.
	 * @param genNum
	 *            a int.
	 */
	public void add(final GPPopulation pop, final String id, final int genNum) {
		stats.add(new GenerationInformation(pop, id, genNum));
	}
	
	/**
	 * <p>
	 * add.
	 * </p>
	 * 
	 * @param pop
	 *            a {@link org.jgap.Population} object.
	 * @param id
	 *            a int.
	 */
	public void add(final Population pop, final int id) {
		stats.add(new GenerationInformation(pop, String.valueOf(id), id));
		
	}
	
	/**
	 * <p>
	 * add.
	 * </p>
	 * 
	 * @param pop
	 *            a {@link org.jgap.gp.impl.GPPopulation} object.
	 * @param id
	 *            a int.
	 */
	public void add(final GPPopulation pop, final int id) {
		stats.add(new GenerationInformation(pop, String.valueOf(id), id));
	}
	
	/**
	 * <p>
	 * textStats.
	 * </p>
	 * 
	 * @param num
	 *            a int.
	 */
	public void textStats(final int num) {
		final GenerationInformation ps = stats.get(num);
		final BucketList<Double> bucket = ps.getFitnessBucket();
		for ( final Double i : bucket ) {
			logger.info(bucket.getCount(i) + " fitness values of: " + i);
		}
	}
	
	/**
	 * <p>
	 * showGenerationGeneTable.
	 * </p>
	 */
	public void showGenerationGeneTable() {
		final JFrame jf = new JFrame("Generatin Gene Table");
		
		final JPanel jp = new JPanel();
		
		final TableModel tm = new TableModel() {
			
			@Override
			public void setValueAt(final Object arg0, final int arg1,
					final int arg2) {
				
			}
			
			@Override
			public void removeTableModelListener(final TableModelListener arg0) {
				
			}
			
			@Override
			public boolean isCellEditable(final int arg0, final int arg1) {
				return false;
			}
			
			@Override
			public Object getValueAt(final int x, final int y) {
				if (y == 0) {
					return stats.get(x).getGenNum();
				}
				return stats.get(x).getBestGene()[y - 1];
			}
			
			@Override
			public int getRowCount() {
				return stats.size();
			}
			
			@Override
			public String getColumnName(final int i) {
				if (i == 0) {
					return "Generation";
				}
				return "Gene " + i;
				
			}
			
			@Override
			public int getColumnCount() {
				return stats.get(0).getBestGene().length;
			}
			
			@Override
			public Class<?> getColumnClass(final int arg0) {
				return Integer.class;
			}
			
			@Override
			public void addTableModelListener(final TableModelListener arg0) {
				
			}
		};
		
		final JTable table = new JTable(tm);
		table.setPreferredScrollableViewportSize(new Dimension(650, 300));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		for ( int i = 0 ; i < table.getColumnCount() ; i++ ) {
			final TableColumn column = table.getColumnModel().getColumn(i);
			
			column.setPreferredWidth(70);
			
		}
		
		final JScrollPane sp = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setSize(600, 400);
		jp.add(sp);
		jf.setContentPane(jp);
		
		jf.setSize(700, 400);
		jf.setLocationByPlatform(true);
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf.setVisible(true);
		// TODO create and show gui.
		
	}
	
	/**
	 * <p>
	 * startFitnessGraph.
	 * </p>
	 */
	public void startFitnessGraph() {
		final String title = "Fitness across generations";
		final XYSeriesCollection data = new XYSeriesCollection();
		final XYSeries avgFit = new XYSeries("Average Fitness");
		final XYSeries minFit = new XYSeries("Minimum Fitness");
		final XYSeries maxFit = new XYSeries("Maximum Fitness");
		
		data.addSeries(minFit);
		data.addSeries(avgFit);
		data.addSeries(maxFit);
		// TODO Normalise data, (25000 - x is not good)
		for ( final GenerationInformation info : stats ) {
			avgFit.add(info.getGenNum(), info.getAvgFitness());
			minFit.add(info.getGenNum(), info.getMinFitness());
			maxFit.add(info.getGenNum(), info.getMaxFitness());
		}
		
		stats.addListChangeListener(new ListChangeListener() {
			
			@Override
			public void dataRemoved(final ListChangeEvent e) {
			}
			
			@Override
			public void dataChanged(final ListChangeEvent e) {
			}
			
			@Override
			public void dataAdded(final ListChangeEvent e) {
				final GenerationInformation info = (GenerationInformation) e
						.getElement();
				
				avgFit.add(info.getGenNum(), info.getAvgFitness());
				minFit.add(info.getGenNum(), info.getMinFitness());
				maxFit.add(info.getGenNum(), info.getMaxFitness());
			}
		});
		
		final JFreeChart chart = ChartFactory.createXYLineChart(title,
				"Generation", "Fitness Value", data, PlotOrientation.VERTICAL,
				true, true, false);
		
		final XYPlot plot = chart.getXYPlot();
		final NumberAxis axis = new NumberAxis();
		// axis.setRange(0, 20);
		axis.setTickUnit(new NumberTickUnit(4));
		plot.setDomainAxis(axis);
		plot.setRangeGridlinePaint(Color.red);
		final ChartFrame chartFrame = new ChartFrame(title, chart);
		chartFrame.setVisible(true);
		chartFrame.setSize(DEFAULT_GRAPH_SIZE);
		
	}
	
	/**
	 * <p>
	 * showFitnessGraph.
	 * </p>
	 */
	public void showFitnessGraph() {
		final String title = "Fitness across generations";
		final XYSeriesCollection data = new XYSeriesCollection();
		final XYSeries avgFit = new XYSeries("Average Fitness");
		final XYSeries minFit = new XYSeries("Minimum Fitness");
		final XYSeries maxFit = new XYSeries("Maximum Fitness");
		int count = 0;
		for ( final GenerationInformation pop : stats ) {
			avgFit.add(count, pop.getAvgFitness());
			minFit.add(count, pop.getMinFitness());
			maxFit.add(count, pop.getMaxFitness());
			count++;
		}
		data.addSeries(minFit);
		data.addSeries(avgFit);
		data.addSeries(maxFit);
		
		final JFreeChart chart = ChartFactory.createXYLineChart(title,
				"Generation", "Fitness Value", data, PlotOrientation.VERTICAL,
				true, true, false);
		
		final XYPlot plot = chart.getXYPlot();
		final NumberAxis axis = new NumberAxis();
		// axis.setRange(0, 20);
		axis.setTickUnit(new NumberTickUnit(4));
		plot.setDomainAxis(axis);
		plot.setRangeGridlinePaint(Color.red);
		final ChartFrame chartFrame = new ChartFrame(title, chart);
		chartFrame.setVisible(true);
		chartFrame.setSize(DEFAULT_GRAPH_SIZE);
		
	}
	
	/**
	 * <p>
	 * startFreqFitnessGraph.
	 * </p>
	 */
	public void startFreqFitnessGraph() {
		final String title = "Frequency of fitness across generations";
		final XYSeriesCollection dataset = new XYSeriesCollection();
		
		for ( final GenerationInformation pop : stats ) {
			
			final XYSeries series1 = new XYSeries("Generation " + pop.getId());
			for ( final Double i : pop.getFitnessBucket() ) {
				series1.add(i,
						Double.valueOf(pop.getFitnessBucket().getCount(i)));
			}
			dataset.addSeries(series1);
		}
		stats.addListChangeListener(new ListChangeListener() {
			
			@Override
			public void dataRemoved(final ListChangeEvent e) {
				
			}
			
			@Override
			public void dataChanged(final ListChangeEvent e) {
				
			}
			
			@Override
			public void dataAdded(final ListChangeEvent e) {
				final GenerationInformation pop = (GenerationInformation) e
						.getElement();
				
				final XYSeries series1 = new XYSeries("Generation "
						+ pop.getId());
				for ( final Double i : pop.getFitnessBucket() ) {
					series1.add(i,
							Double.valueOf(pop.getFitnessBucket().getCount(i)));
				}
				dataset.addSeries(series1);
			}
		});
		final JFreeChart chart = ChartFactory.createXYBarChart(title,
				"Fitness Value", false, "Frequency in Pop", dataset,
				PlotOrientation.VERTICAL, true, true, false);
		
		final XYPlot plot = chart.getXYPlot();
		
		plot.setRangeGridlinePaint(Color.red);
		final ChartFrame chartFrame = new ChartFrame(title, chart);
		chartFrame.setVisible(true);
		chartFrame.setSize(DEFAULT_GRAPH_SIZE);
		
	}
	
	/**
	 * <p>
	 * showFreqFitnessGraph.
	 * </p>
	 */
	public void showFreqFitnessGraph() {
		final String title = "Frequency of fitness across generations";
		final XYSeriesCollection dataset = new XYSeriesCollection();
		
		for ( final GenerationInformation pop : stats ) {
			
			final XYSeries series1 = new XYSeries("Generation " + pop.getId());
			for ( final Double i : pop.getFitnessBucket() ) {
				series1.add(i,
						Double.valueOf(pop.getFitnessBucket().getCount(i)));
			}
			dataset.addSeries(series1);
		}
		final JFreeChart chart = ChartFactory.createXYBarChart(title,
				"Fitness Value", false, "Frequency in Pop", dataset,
				PlotOrientation.VERTICAL, true, true, false);
		
		final XYPlot plot = chart.getXYPlot();
		
		plot.setRangeGridlinePaint(Color.red);
		final ChartFrame chartFrame = new ChartFrame(title, chart);
		chartFrame.setVisible(true);
		chartFrame.setSize(DEFAULT_GRAPH_SIZE);
		
	}
	
}
