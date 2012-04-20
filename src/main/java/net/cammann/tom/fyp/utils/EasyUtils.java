package net.cammann.tom.fyp.utils;

import java.text.DecimalFormat;

import org.jgap.Gene;
import org.jgap.IChromosome;

/**
 * <p>EasyUtils class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public class EasyUtils {
	
	/**
	 * <p>getChromosoneString.</p>
	 *
	 * @param chrome a {@link org.jgap.IChromosome} object.
	 * @return a {@link java.lang.String} object.
	 */
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
	
	/**
	 * <p>getChromosoneArray.</p>
	 *
	 * @param chrome a {@link org.jgap.IChromosome} object.
	 * @return an array of int.
	 */
	public static int[] getChromosoneArray(IChromosome chrome) {
		
		int genes[] = new int[chrome.getGenes().length];
		int idx = 0;
		for (Gene g : chrome.getGenes()) {
			genes[idx++] = (Integer) g.getAllele();
		}
		return genes;
	}
	
	/**
	 * <p>roundOneDecimal.</p>
	 *
	 * @param d a double.
	 * @return a double.
	 */
	public static double roundOneDecimal(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.#");
		return Double.valueOf(twoDForm.format(d));
	}
	
	/**
	 * <p>roundTwoDecimals.</p>
	 *
	 * @param d a double.
	 * @return a double.
	 */
	public static double roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
	}
	
}
