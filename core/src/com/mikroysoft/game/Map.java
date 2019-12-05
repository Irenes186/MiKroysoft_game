package com.mikroysoft.game;

import java.io.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class Map {
	
	IRenderable[][] grid;

	public Map(int MAPWIDTH, int MAPHEIGHT) throws Exception{
    int TILEWIDTH = Gdx.graphics.getWidth() / MAPWIDTH;
    int TILEHEIGHT = Gdx.graphics.getHeight() / MAPHEIGHT;

		grid = new IRenderable[MAPWIDTH][MAPHEIGHT];
		String[][] inGrid = new String[MAPWIDTH][MAPHEIGHT];		
		File file = new File("map_information.txt");
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int row = 0;
		String fileInput;
		String[] items;
  
		while ((fileInput = reader.readLine()) != null) {
			items = fileInput.split("",MAPWIDTH);
			for (int col = 0; col < MAPWIDTH; col++) {
				inGrid[row][col] = items[col];
				// debug: print grid
				//System.out.print(items[col]);
			}
			row++;
			// debug: print grid
			//System.out.println();
		}
		
		reader.close();
		
		String fileName;
		boolean hasRoadAbove, hasRoadBelow, hasRoadLeft, hasRoadRight;
		for (row = 0; row < MAPHEIGHT; row++) {
            for (int col = 0; col < MAPWIDTH; col++) {
                switch (inGrid[row][col]) {
                    case "0" :
                        grid[row][col] = new FireStation(10, new Coordinate(col * TILEWIDTH, (MAPHEIGHT-row) * TILEHEIGHT), TILEWIDTH, TILEHEIGHT);
                        break;
                    
                    case "2":
                    	AlienBaseParameters params = new AlienBaseParameters();
                    	grid[row][col] = new AlienBase("Cliffords's Tower", params, new Coordinate(col * TILEWIDTH, (MAPHEIGHT-row) * TILEHEIGHT), TILEWIDTH, TILEHEIGHT, "cliffords-tower"); 
      	
                    case "1" :
                    	fileName = "roadV";
                    	hasRoadAbove = false;
                    	hasRoadBelow = false;
                    	hasRoadLeft = false;
                    	hasRoadRight = false;
    					if (row > 0 && inGrid[row-1][col].equals("1")) {
    						hasRoadAbove = true;
    					}
    					if (col < (MAPWIDTH - 1) && inGrid[row][col+1].equals("1")) {
    						hasRoadRight = true;
    					}
    					if (row < (MAPHEIGHT - 1) && inGrid[row+1][col].equals("1")) {
    						hasRoadBelow = true;
    					}
    					if (col > 0 && inGrid[row][col-1].equals("1")) {
    						hasRoadLeft = true;
    					}

    					if (hasRoadAbove) {
    						if (hasRoadRight) {
    							if (hasRoadLeft) {
    								if (hasRoadBelow) {
    									fileName = "road-cross";
    								} else {
    									fileName = "road-TU";
    								}
    							} else {
    								if (hasRoadBelow) {
    									fileName = "road-TR";
    								} else {
    									fileName = "road-cornerTR";
    								}
    							}
    						} else {
    							if (hasRoadLeft) {
    								if (hasRoadBelow) {
    									fileName = "road-TL";
    								} else {
    									fileName = "road-cornerTL";
    								}
    							} else {
    								fileName = "roadV";
    							}
    						}
    					} else {
    						if (hasRoadBelow) {
    							if (hasRoadLeft) {
    								if (hasRoadRight) {
    									fileName = "road-TD";
    								} else {
    									if (hasRoadLeft) {
    										fileName = "road-cornerBL";
    									} else {
    										fileName = "roadH";
    									}
    								}
    							} else {
    								if (hasRoadRight) {
    									fileName = "road-cornerBR";
    								} else if (hasRoadLeft) {
    									fileName = "roadH";
    								} else {
    									fileName = "roadV";
    								}
    							}
    						} else {
    							fileName = "roadH";
    						}
    					}
                        grid[row][col] = new Road(new Coordinate(col * TILEWIDTH, (MAPHEIGHT-row) * TILEHEIGHT), TILEWIDTH, TILEHEIGHT, fileName);
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
