package net.cammann.tom.fyp.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.cammann.tom.fyp.core.AbstractEnvironmentMap;
import net.cammann.tom.fyp.core.Beta;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.MapObject;
import net.cammann.tom.fyp.core.Obstacle;
import net.cammann.tom.fyp.core.Resource;
import net.cammann.tom.fyp.core.SimpleResource;

import org.apache.log4j.Logger;

/**
 * Utility class for exporting /importing maps.
 * 
 * @author TC
 * @version $Id: $
 */
public final class MapUtils {

	/**
	 * Logger.
	 */
	private static final Logger logger = Logger.getLogger(MapUtils.class);

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
			br.write("Resources\n");
			// Write each resource
			Resource r = null;
			for (Iterator<MapObject> it = map.getResourceIterator(); it
					.hasNext();) {
				r = (Resource) it.next();
				br.write(r.getX() + " " + r.getY() + " "
						+ (int) r.getCalories());
				br.write("\n");
			}
			br.write("Obstacles\n");
			Obstacle o = null;
			// write each obstacle
			for (Iterator<MapObject> it = map.getObstacleIterator(); it
					.hasNext();) {
				o = (Obstacle) it.next();
				br.write(o.getX() + " " + o.getY() + " " + (int) o.getRadius());
				br.write("\n");
			}

			br.flush();
			br.close();

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
	 * @return If map is successfully loaded return EnvironmentMap, otherwise
	 *         return null
	 * @Beta - NOT FINISHED OR WORKING
	 */
	@Beta
	public static EnvironmentMap loadMap(final File file) {
		AbstractEnvironmentMap map = null;
		final List<Resource> rList = new ArrayList<Resource>();
		final List<Obstacle> oList = new ArrayList<Obstacle>();
		/**
		 * Small class to create map from.
		 * 
		 * @author TC
		 * 
		 */
		class QuickMap extends AbstractEnvironmentMap {

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
				for (final Resource r : rList) {
					addResource(r);
				}

			}

			@Override
			protected void initObstacles() {
				for (final Obstacle o : oList) {
					addObstacle(o);
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
				if (line.equals("Obstacles")) {
					break;
				}
				if (line.equals("Resources")) {
					continue;
				}
				logger.trace(line);
				final String[] split = line.split(" ");
				Resource r = new SimpleResource(Integer.valueOf(split[0]),
						Integer.valueOf(split[1]));
				r.setCalories(Integer.valueOf(split[2]));
				rList.add(r);
			}

			if (line.equals("Obstacles")) {
				while ((line = br.readLine()) != null) {

					final String[] split = line.split(" ");
					Obstacle o = new Obstacle(Integer.valueOf(split[0]),
							Integer.valueOf(split[1]),
							Integer.valueOf(split[2]));
					oList.add(o);
				}
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
