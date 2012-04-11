package net.cammann.tom.fyp.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.cammann.tom.fyp.core.AbstactMap;
import net.cammann.tom.fyp.core.Beta;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.Resource;

/**
 * Utility class for exporting /importing maps.
 * 
 * @author TC
 * 
 */
public final class MapUtils {
	
	/**
	 * Hide constructor for utility class.
	 */
	private MapUtils() {
		
	}
	
	/**
	 * Method to save map to a file.
	 * 
	 * @param file
	 *            to save too.
	 * @param map
	 *            to save
	 * @Beta not currently working
	 */
	@Beta
	public static void saveMap(final File file, final EnvironmentMap map) {
		try {
			// TODO fix
			final BufferedWriter br = new BufferedWriter(new FileWriter(file));
			
			br.write("" + map.getWidth());
			br.write("\n");
			br.write("" + map.getHeight());
			br.write("\n");
			//
			// for (Resource r : map.getResourceList()) {
			// br.write(r.getX() + " " + r.getY() + " " + r.getCalories());
			// br.write("\n");
			// }
			
			br.flush();
			br.close();
			// write each resource
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to load map from file.
	 * 
	 * Saves map in basic plain text format.
	 * 
	 * @param file
	 *            save location.
	 * 
	 * @return If map is successfully loaded return EnvironmentMap, otherwise
	 *         return null
	 * 
	 * @Beta - NOT FINISHED OR WORKING
	 */
	@Beta
	public static EnvironmentMap loadMap(final File file) {
		AbstactMap map = null;
		final List<Resource> rList = new ArrayList<Resource>();
		/**
		 * Small class to create map from.
		 * 
		 * @author TC
		 * 
		 */
		class QuickMap extends AbstactMap {
			
			/**
			 * straight to super.
			 * 
			 * @param width
			 *            -
			 * @param height
			 *            -
			 */
			public QuickMap(final int width, final int height) {
				super(width, height);
			}
			
			@Override
			public void initResources() {
				for ( final Resource r : rList ) {
					addResource(r);
				}
				
			}
			
		}
		
		try {
			final BufferedReader br = new BufferedReader(new FileReader(file));
			
			final int width = Integer.valueOf(br.readLine());
			final int height = Integer.valueOf(br.readLine());
			
			map = new QuickMap(width, height);
			String line = "";
			while ((line = br.readLine()) != null) {
				// final String[] split = line.split(" ");
				// TODO fix
				// Resource r = new SimpleResource(map,
				// Integer.valueOf(split[0]),
				// Integer.valueOf(split[1]));
				// r.setCalories(Integer.valueOf(split[2]));
				// rList.add(r);
			}
			
			br.close();
			
		} catch (final FileNotFoundException e) {
			
			e.printStackTrace();
			return null;
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return map;
	}
}
