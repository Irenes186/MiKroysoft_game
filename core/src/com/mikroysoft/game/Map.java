package com.mikroysoft.game;

import java.io.*;

public class Map {
	
	String[][] grid;
	public Map() throws Exception{
		int WIDTH = 16;
		int HEIGHT = 16;
		grid = new String[WIDTH][HEIGHT];
		
		File file = new File("map_information.txt");
		
		BufferedReader reader = new BufferedReader(new FileReader(file)); 
		
		String fileInput;
		String[] items;
		int index = 0;
		while ((fileInput = reader.readLine()) != null) {
			items = fileInput.split(",",WIDTH);
			grid[index] = items;
		}
		
		reader.close();
		
		
	}
}
