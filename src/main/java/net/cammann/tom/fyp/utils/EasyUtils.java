package net.cammann.tom.fyp.utils;

import java.text.DecimalFormat;

import org.jgap.Gene;
import org.jgap.IChromosome;

public class EasyUtils {
	
	public static String getChromosoneString(IChromosome chrome) {
		StringBuilder sb = new StringBuilder();
		
		int num = 0;
		sb.append("{ ");
		for (Gene i : chrome.getGenes()) {
			if (num++ != chrome.getGenes().length - 1) {
				sb.append(i.getAllele()).append(", ");
			} else {
				sb.append(i.getAllele()).append(" }");
			}
		}
		return sb.toString();
	}
	
	public static int[] getChromosoneArray(IChromosome chrome) {
		
		int genes[] = new int[chrome.getGenes().length];
		int idx = 0;
		for (Gene g : chrome.getGenes()) {
			genes[idx++] = (Integer) g.getAllele();
		}
		return genes;
	}
	
	public static double roundOneDecimal(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.#");
		return Double.valueOf(twoDForm.format(d));
	}
	
	public static double roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
	}
	
}
