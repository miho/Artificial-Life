package net.cammann.tom.fyp.stats;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import net.cammann.tom.fyp.utils.BucketList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jgap.Population;

public class StatsPackage {
	
	private final List<GenerationInformation> stats;
	
	public StatsPackage() {
		stats = new ArrayList<GenerationInformation>();
	}
	
	public void add(Population pop, String id) {
		stats.add(new GenerationInformation(pop, id));
	}
	
	public void add(Population pop, int id) {
		stats.add(new GenerationInformation(pop, String.valueOf(id)));
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
		table.setPreferredScrollableViewportSize(new Dimension(1500, 400));
		JScrollPane sp = new JScrollPane(table);
		sp.setSize(1000, 400);
		jp.add(sp);
		jf.setContentPane(jp);
		
		jf.setSize(600, 400);
		jf.setLocationByPlatform(true);
		jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		jf.setVisible(true);
		
	}
	
	public void showFitnessGraph() {
		String title = "Fitness across generations";
		XYSeriesCollection data = new XYSeriesCollection();
		XYSeries avgFit = new XYSeries("Average Fitness");
		XYSeries minFit = new XYSeries("Minimum Fitness");
		XYSeries maxFit = new XYSeries("Maximum Fitness");
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
		
		plot.setRangeGridlinePaint(Color.red);
		ChartFrame chartFrame = new ChartFrame(title, chart);
		chartFrame.setVisible(true);
		chartFrame.setSize(600, 450);
		
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
