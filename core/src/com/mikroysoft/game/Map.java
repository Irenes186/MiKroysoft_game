package com.mikroysoft.game;

import java.io.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Map {
	
	IRenderable[][] grid;

	public Map() throws Exception{
		int WIDTH = 20;
		int HEIGHT = 20;
                int TILEWIDTH = 50;
                int TILEHEIGHT = 50;

		grid = new IRenderable[WIDTH][HEIGHT];
		
		File file = new File("map_information.txt");
		
		BufferedReader reader = new BufferedReader(new FileReader(file)); 
		
		String fileInput;
		String[] items;
                int col = 0;
		while ((fileInput = reader.readLine()) != null) {
			items = fileInput.split("",WIDTH);

                        for (int row = 0; row < items.length; row++) {
                            switch (items[row]) {
                                case "1" :
                                    grid[col][row] = new Road(new Coordinate(col * TILEWIDTH, row * TILEHEIGHT), TILEWIDTH, TILEHEIGHT);
                                    break;
                                
                                case "0" :
                                    grid[col][row] = new FireStation(10);
                                    break;

                            }
                        }
                    col++;
                }
		
		reader.close();

	}

        public void render(SpriteBatch batch) {
            for (int col = 0; col < grid.length; col++) {
                for (int row = 0; row < grid[0].length; row++) {
                    grid[col][row].render(batch);
                }
            }
        }
}
