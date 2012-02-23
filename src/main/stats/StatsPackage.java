package stats;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jgap.Population;

import utils.BucketList;

public class StatsPackage {
	
	private final List<PopStat> stats;
	
	public StatsPackage() {
		stats = new ArrayList<PopStat>();
	}
	
	public void add(Population pop, String id) {
		stats.add(new PopStat(pop, id));
	}
	
	public void add(Population pop, int id) {
		stats.add(new PopStat(pop, String.valueOf(id)));
	}
	
	public void textStats(int num) {
		PopStat ps = stats.get(num);
		BucketList<Double> bucket = ps.getFitnessBucket();
		for (Double i : bucket) {
			System.out.println(bucket.getCount(i) + " fitness values of: " + i);
		}
	}
	
	public void showFitnessGraph() {
		String title = "Frequency of fitness across generations";
		XYSeriesCollection data = new XYSeriesCollection();
		XYSeries avgFit = new XYSeries("Average Fitness");
		XYSeries minFit = new XYSeries("Minimum Fitness");
		XYSeries maxFit = new XYSeries("Maximum Fitness");
		int count = 0;
		for (PopStat pop : stats) {
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
		
		for (PopStat pop : stats) {
			
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
