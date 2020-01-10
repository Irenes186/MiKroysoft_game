package com.mikroysoft.game;

import java.io.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Map {
	/* Container class for all map elements
	 * I.e includes roads and alien bases
	 * but does not include GUI or fire engines (???)
	 */
	
	// actual array containing class instances
	IRenderable[][] grid;
	Texture bg;
	public Coordinate c = new Coordinate(0, 0); 
	private int MAPWIDTH, MAPHEIGHT;
	private int TILEWIDTH, TILEHEIGHT;
	
	// constructor: takes map dimensions
	public Map(int MAPWIDTH, int MAPHEIGHT, String bgtex) throws Exception {
		this.bg = new Texture(bgtex + ".png");
		// calculate tile dimensions
		TILEWIDTH = Gdx.graphics.getWidth() / MAPWIDTH;
		TILEHEIGHT = Gdx.graphics.getHeight() / MAPHEIGHT;
		
		this.MAPWIDTH = MAPWIDTH;
		this.MAPHEIGHT = MAPHEIGHT;
		
		grid = new IRenderable[MAPWIDTH][MAPHEIGHT];
		// Grid containing text description of map
		String[][] inGrid = new String[MAPWIDTH][MAPHEIGHT];
		
		// import map info
		File file = new File("map_information.txt");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int row = 0;
		String fileInput;
		String[] items;
		
		// read map info into inGrid where each character is a cell
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
		
		// populate grid with class instances as described in map_information
		String fileName;
		// temp variables for configuring road gfx
		boolean hasRoadAbove, hasRoadBelow, hasRoadLeft, hasRoadRight;
		// config var for AlienBases
		AlienBaseParameters params;
		// iterate through inGrid
		for (row = 0; row < MAPHEIGHT; row++) {
            for (int col = 0; col < MAPWIDTH; col++) {
            	// Decode the required class instance
                switch (inGrid[row][col]) {
                	// instance fire stations
                    case "0" :
                    	c.setCoordinate(col * TILEWIDTH, (MAPHEIGHT-row) * TILEHEIGHT);
                        grid[row][col] = new FireStation(10, c, 10, 10);
                        break;
                        
                    // instance roads
                    case "1":
                    	// default to vertical road
                    	fileName = "roadV";
                    	// reset config vars for current cell
                    	hasRoadAbove = false;
                    	hasRoadBelow = false;
                    	hasRoadLeft = false;
                    	hasRoadRight = false;
                    	
                    	// decide which of the surrounding cells are roads
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
    					
    					// set road texture according to the combination of surrounding roads
    					// [!] May later wish to allow any IRenderable to influence road directions (e.g firestations)
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
    					
    					// instance road with the required gfx
                        grid[row][col] = new Road(new Coordinate(col * TILEWIDTH, (MAPHEIGHT-row) * TILEHEIGHT), TILEWIDTH, TILEHEIGHT, fileName);
                        break;
                    
                    // instance alien bases
                    // clifford's tower
                    case "2":
                    	params = new AlienBaseParameters();
                    	grid[row][col] = new AlienBase("Cliffords's Tower", params, new Coordinate(col * TILEWIDTH, (MAPHEIGHT-row) * TILEHEIGHT), TILEWIDTH, TILEHEIGHT, "cliffords-tower"); 
                    	break;
                    
                	// Aldi
                    case "3":
                    	params = new AlienBaseParameters();
                    	grid[row][col] = new AlienBase("Aldi", params, new Coordinate(col * TILEWIDTH, (MAPHEIGHT-row) * TILEHEIGHT), TILEWIDTH, TILEHEIGHT, "aldi"); 
                    	break;
                    	
                	// Holgate Windmill
                    case "4":
                    	params = new AlienBaseParameters();
                    	grid[row][col] = new AlienBase("Holgate Windmill", params, new Coordinate(col * TILEWIDTH, (MAPHEIGHT-row) * TILEHEIGHT), TILEWIDTH, TILEHEIGHT, "Holgate-Windmill"); 
                    	break;
                }
            }
        }
		
		reader.close();
	}

	// render map elements onto the screen
    public void render(SpriteBatch batch) {
    	// batch.draw (this.bg, 0, 0, MAPWIDTH, MAPHEIGHT, 0, 0, this.bg.getWidth(), this.bg.getHeight(), false, false);
    	batch.draw (this.bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    	// iterate through grid, and call each object's render method
        for (int col = 0; col < grid.length; col++) {
            for (int row = 0; row < grid[0].length; row++) {
                if (grid[row][col] != null)
                    grid[row][col].render(batch);
            }
        }
    }
    
    public boolean isInStationRange(Coordinate engineCoordinates) {
		int tempX = engineCoordinates.x - c.x;
		int tempY = engineCoordinates.y - c.y;
		System.out.println(tempY);
		if(tempX < 0) {
			tempX = tempX * -1;
		}
		if (tempY < 0) {
			tempY = tempY * -1;
		}
		if((tempX < this.TILEWIDTH) && (tempY < this.TILEHEIGHT)) {
			return true;
		} else { 
			return false;
		}
		
	}
}
