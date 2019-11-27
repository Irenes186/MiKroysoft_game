package com.mikroysoft.game;

import java.io.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class Map {
	
	IRenderable[][] grid;

	public Map() throws Exception{
		int WIDTH = 20;
		int HEIGHT = 20;
        int TILEWIDTH = Gdx.graphics.getWidth() / WIDTH;
        int TILEHEIGHT = Gdx.graphics.getHeight() / HEIGHT;

		grid = new IRenderable[WIDTH][HEIGHT];
		String[][] inGrid = new String[WIDTH][HEIGHT];
		
		File file = new File("map_information.txt");
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int row = 0;
		String fileInput;
		String[] items;
		
		while ((fileInput = reader.readLine()) != null) {
			items = fileInput.split("",WIDTH);
			for (int col = 0; col < WIDTH; col++) {
				inGrid[row][col] = items[col];
				System.out.print(items[col]);
			}
			row++;
			System.out.println();
		}
		
		reader.close();
		
		String fileName;
		boolean u, d, l, r;
		for (row = 0; row < HEIGHT; row++) {

            for (int col = 0; col < WIDTH; col++) {
                switch (inGrid[row][col]) {
                    case "0" :
                        grid[row][col] = new FireStation(10, new Coordinate(col * TILEWIDTH, (HEIGHT-row) * TILEHEIGHT), TILEWIDTH, TILEHEIGHT);
                        break;
                    case "1" :
                    	fileName = "roadV";
                    	u = false;
    					d = false;
    					l = false;
    					r = false;
    					if (row > 0 && inGrid[row-1][col].equals("1")) {
    						u = true;
    					}
    					if (col < (WIDTH - 1) && inGrid[row][col+1].equals("1")) {
    						r = true;
    					}
    					if (row < (HEIGHT - 1) && inGrid[row+1][col].equals("1")) {
    						System.out.println("DOWN FOUND");
    						d = true;
    					}
    					if (col > 0 && inGrid[row][col-1].equals("1")) {
    						l = true;
    					}

    					if (u) {
    						if (r) {
    							if (l) {
    								if (d) {
    									fileName = "road-cross";
    								} else {
    									fileName = "road-TU";
    								}
    							} else {
    								if (d) {
    									fileName = "road-TR";
    								} else {
    									fileName = "road-cornerTR";
    								}
    							}
    						} else {
    							if (l) {
    								if (d) {
    									fileName = "road-TL";
    								} else {
    									fileName = "road-cornerTL";
    								}
    							} else {
    								fileName = "roadV";
    							}
    						}
    					} else {
    						if (d) {
    							if (l) {
    								if (r) {
    									fileName = "road-TD";
    								} else {
    									if (l) {
    										fileName = "road-cornerBL";
    									} else {
    										fileName = "roadH";
    									}
    								}
    							} else {
    								if (r) {
    									fileName = "road-cornerBR";
    								} else if (l) {
    									fileName = "roadH";
    								} else {
    									fileName = "roadV";
    								}
    							}
    						} else {
    							fileName = "roadH";
    						}
    					}
    					
                        grid[row][col] = new Road(new Coordinate(col * TILEWIDTH, (HEIGHT-row) * TILEHEIGHT), TILEWIDTH, TILEHEIGHT, fileName);
                        break;
                     
                }
            }
        }
		
		reader.close();
	}

        public void render(SpriteBatch batch) {
            for (int col = 0; col < grid.length; col++) {
                for (int row = 0; row < grid[0].length; row++) {
                    if (grid[row][col] != null)
                        grid[row][col].render(batch);
                }
            }
        }
}
