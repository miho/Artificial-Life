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
import java.util.Random;

import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.Resource;
import net.cammann.tom.fyp.core.SimpleMap;
import net.cammann.tom.fyp.core.SimpleResource;

public class MapUtils {
	public static void SaveMap(File file, EnvironmentMap map) {
		try {
			BufferedWriter br = new BufferedWriter(new FileWriter(file));
			
			br.write("" + map.getWidth());
			br.write("\n");
			br.write("" + map.getHeight());
			br.write("\n");
			
			for (Resource r : map.getResourceList()) {
				br.write(r.getX() + " " + r.getY() + " " + r.getCalories());
				br.write("\n");
			}
			
			br.flush();
			br.close();
			// write each resource
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static EnvironmentMap LoadMap(File file) {
		SimpleMap map = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			int width = Integer.valueOf(br.readLine());
			int height = Integer.valueOf(br.readLine());
			final List<Resource> rList = new ArrayList<Resource>();
			
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] split = line.split(" ");
				Resource r = new SimpleResource(Integer.valueOf(split[0]),
						Integer.valueOf(split[1]));
				r.setCalories(Integer.valueOf(split[2]));
				rList.add(r);
			}
			
			br.close();
			class QuickMap extends SimpleMap {
				
				public QuickMap(int width, int height) {
					super(width, height);
				}
				
				@Override
				public void resetMap() {
					resourceList.clear();
					initResources();
				}
				
				@Override
				public void initResources() {
					for (Resource r : rList) {
						addResource(r);
					}
					
				}
				
				@Override
				public void initLife(ALife life) {
					life.setX(new Random()
							.nextInt((life.getMap().getWidth() + 1) / 10) * 10);
					life.setY(new Random()
							.nextInt((life.getMap().getHeight() + 1) / 10) * 10);
				}
				
			}
			
			map = new QuickMap(width, height);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return map;
	}
}