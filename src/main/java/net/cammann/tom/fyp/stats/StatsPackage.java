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

import net.cammann.tom.fyp.utils.BucketList;
import net.cammann.tom.fyp.utils.ListChangeEvent;
import net.cammann.tom.fyp.utils.ListChangeListener;
import net.cammann.tom.fyp.utils.WatchableList;

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

public class StatsPackage {
	
	private final WatchableList<GenerationInformation> stats;
	
	public StatsPackage() {
		stats = new WatchableList<GenerationInformation>();
	}
	
	public void add(Population pop, String id, int genNum) {
		stats.add(new GenerationInformation(pop, id, genNum));
	}
	
	public void add(GPPopulation pop, String id, int genNum) {
		stats.add(new GenerationInformation(pop, id, genNum));
	}
	
	public void add(Population pop, int id) {
		stats.add(new GenerationInformation(pop, String.valueOf(id), id));
		
	}
	
	public void add(GPPopulation pop, int id) {
		stats.add(new GenerationInformation(pop, String.valueOf(id), id));
	}
	
	public void textStats(int num) {
		GenerationInformation ps = stats.get(num);
		BucketList<Double> bucket = ps.getFitnessBucket();
		for (Double i : bucket) {
			System.out.println(bucket.getCount(i) + " fitness values of: " + i);
		}
	}
	
	public void showGenerationGeneTable() {
		JFrame jf = new JFrame("Generatin Gene Table");
		
		JPanel jp = new JPanel();
		
		TableModel tm = new TableModel() {
			
			@Override
			public void setValueAt(Object arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void removeTableModelListener(TableModelListener arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isCellEditable(int arg0, int arg1) {
				return false;
			}
			
			@Override
			public Object getValueAt(int arg0, int arg1) {
				return stats.get(arg0).getBestGene()[arg1];
			}
			
			@Override
			public int getRowCount() {
				return stats.size();
			}
			
			@Override
			public String getColumnName(int arg0) {
				// TODO Auto-generated method stub
				return "Gene " + arg0;
			}
			
			@Override
			public int getColumnCount() {
				return stats.get(0).getBestGene().length;
			}
			
			@Override
			public Class<?> getColumnClass(int arg0) {
				return Integer.class;
			}
			
			@Override
			public void addTableModelListener(TableModelListener arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		
		JTable table = new JTable(tm);
		table.setPreferredScrollableViewportSize(new Dimension(600, 300));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		for (int i = 0; i < table.getColumnCount(); i++) {
			TableColumn column = table.getColumnModel().getColumn(i);
			
			column.setPreferredWidth(50);
			
		}
		
		JScrollPane sp = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setSize(600, 400);
		jp.add(sp);
		jf.setContentPane(jp);
		
		jf.setSize(700, 400);
		jf.setLocationByPlatform(true);
		jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		jf.setVisible(true);
		
	}
	
	public void startFitnessGraph() {
		String title = "Fitness across generations";
		XYSeriesCollection data = new XYSeriesCollection();
		final XYSeries avgFit = new XYSeries("Average Fitness");
		final XYSeries minFit = new XYSeries("Minimum Fitness");
		final XYSeries maxFit = new XYSeries("Maximum Fitness");
		
		data.addSeries(minFit);
		data.addSeries(avgFit);
		data.addSeries(maxFit);
		// TODO Normalise data, (25000 - x is not good)
		for (GenerationInformation info : stats) {
			avgFit.add(info.getGenNum(), info.getAvgFitness());
			minFit.add(info.getGenNum(), info.getMinFitness());
			maxFit.add(info.getGenNum(), info.getMaxFitness());
		}
		
		stats.addListChangeListener(new ListChangeListener() {
			
			@Override
			public void dataRemoved(ListChangeEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void dataChanged(ListChangeEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void dataAdded(ListChangeEvent e) {
				GenerationInformation info = (GenerationInformation) e
						.getElement();
				
				avgFit.add(info.getGenNum(), info.getAvgFitness());
				minFit.add(info.getGenNum(), info.getMinFitness());
				maxFit.add(info.getGenNum(), info.getMaxFitness());
			}
		});
		
		final JFreeChart chart = ChartFactory.createXYLineChart(title,
				"Generation", "Fitness Value", data, PlotOrientation.VERTICAL,
				true, true, false);
		
		XYPlot plot = chart.getXYPlot();
		NumberAxis axis = new NumberAxis();
		// axis.setRange(0, 20);
		axis.setTickUnit(new NumberTickUnit(4));
		plot.setDomainAxis(axis);
		plot.setRangeGridlinePaint(Color.red);
		ChartFrame chartFrame = new ChartFrame(title, chart);
		chartFrame.setVisible(true);
		chartFrame.setSize(600, 450);
		
	}
	
	public void showFitnessGraph() {
		String title = "Fitness across generations";
		XYSeriesCollection data = new XYSeriesCollection();
		final XYSeries avgFit = new XYSeries("Average Fitness");
		final XYSeries minFit = new XYSeries("Minimum Fitness");
		final XYSeries maxFit = new XYSeries("Maximum Fitness");
		int count = 0;
		for (GenerationInformation pop : stats) {
			avgFit.add(count, pop.getAvgFitness());
			minFit.add(count, pop.getMinFitness());
			maxFit.add(count, pop.getMaxFitness());
			count++;
		}
		data.addSeries(minFit);
		data.addSeries(avgFit);
		data.addSeries(maxFit);
		
		JFreeChart chart = ChartFactory.createXYLineChart(title, "Generation",
				"Fitness Value", data, PlotOrientation.VERTICAL, true, true,
				false);
		
		XYPlot plot = chart.getXYPlot();
		NumberAxis axis = new NumberAxis();
		// axis.setRange(0, 20);
		axis.setTickUnit(new NumberTickUnit(4));
		plot.setDomainAxis(axis);
		plot.setRangeGridlinePaint(Color.red);
		ChartFrame chartFrame = new ChartFrame(title, chart);
		chartFrame.setVisible(true);
		chartFrame.setSize(600, 450);
		
	}
	
	public void startFreqFitnessGraph() {
		String title = "Frequency of fitness across generations";
		final XYSeriesCollection dataset = new XYSeriesCollection();
		
		for (GenerationInformation pop : stats) {
			
			XYSeries series1 = new XYSeries("Generation " + pop.getId());
			for (Double i : pop.getFitnessBucket()) {
				series1.add(i,
						Double.valueOf(pop.getFitnessBucket().getCount(i)));
			}
			dataset.addSeries(series1);
		}
		stats.addListChangeListener(new ListChangeListener() {
			
			@Override
			public void dataRemoved(ListChangeEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void dataChanged(ListChangeEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void dataAdded(ListChangeEvent e) {
				GenerationInformation pop = (GenerationInformation) e
						.getElement();
				
				XYSeries series1 = new XYSeries("Generation " + pop.getId());
				for (Double i : pop.getFitnessBucket()) {
					series1.add(i,
							Double.valueOf(pop.getFitnessBucket().getCount(i)));
				}
				dataset.addSeries(series1);
			}
		});
		JFreeChart chart = ChartFactory.createXYBarChart(title,
				"Fitness Value", false, "Frequency in Pop", dataset,
				PlotOrientation.VERTICAL, true, true, false);
		
		XYPlot plot = chart.getXYPlot();
		
		plot.setRangeGridlinePaint(Color.red);
		ChartFrame chartFrame = new ChartFrame(title, chart);
		chartFrame.setVisible(true);
		chartFrame.setSize(400, 350);
		
	}
	
	public void showFreqFitnessGraph() {
		String title = "Frequency of fitness across generations";
		XYSeriesCollection dataset = new XYSeriesCollection();
		
		for (GenerationInformation pop : stats) {
			
			XYSeries series1 = new XYSeries("Generation " + pop.getId());
			for (Double i : pop.getFitnessBucket()) {
				series1.add(i,
						Double.valueOf(pop.getFitnessBucket().getCount(i)));
			}
			dataset.addSeries(series1);
		}
		JFreeChart chart = ChartFactory.createXYBarChart(title,
				"Fitness Value", false, "Frequency in Pop", dataset,
				PlotOrientation.VERTICAL, true, true, false);
		
		XYPlot plot = chart.getXYPlot();
		
		plot.setRangeGridlinePaint(Color.red);
		ChartFrame chartFrame = new ChartFrame(title, chart);
		chartFrame.setVisible(true);
		chartFrame.setSize(400, 350);
		
	}
	
}
