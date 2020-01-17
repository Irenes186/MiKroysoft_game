package com.mikroysoft.game;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Map {
    /* Container class for all map elements
     * I.e includes roads and alien bases
     * but does not include GUI or fire engines (???)
     */
    IRenderable[][] grid;
    Texture bg;
    int TILEWIDTH, TILEHEIGHT;
    int MAPWIDTH, MAPHEIGHT;
    public Coordinate fireStation = new Coordinate(0, 0);

    // constructor: takes map dimensions
    public Map(int MAPWIDTH, int MAPHEIGHT, String bgtex) throws Exception {
        this.bg = new Texture(bgtex + ".png");
        //Calculate tile dimensions
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
            }
            row++;
        }

        reader.close();
        //Populate grid with class instances as described in map_information
        String fileName;
        //Variables for configuring road gfx
        boolean hasRoadAbove, hasRoadBelow, hasRoadLeft, hasRoadRight;
        AlienBaseParameters params;
        
        // iterate through inGrid
        for (row = 0; row < MAPHEIGHT; row++) {
            for (int col = 0; col < MAPWIDTH; col++) {
                //Decode the required class instance
                switch (inGrid[row][col]) {
                    //Instance fire stations
                    case "0" :
                        fireStation.setCoordinate(col * TILEWIDTH, (MAPHEIGHT-row) * TILEHEIGHT);
                        grid[row][col] = new FireStation(10, fireStation, 10, 10);
                        break;
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
                    // Clifford's tower
                    case "2":
                        params = new AlienBaseParameters(Integer.parseInt(inGrid[row][col]));
                        grid[row][col] = new AlienBase("Cliffords's Tower", params, new Coordinate(col * TILEWIDTH, (MAPHEIGHT-row) * TILEHEIGHT), TILEWIDTH, TILEHEIGHT, "cliffords-tower");
                        break;
                    // Aldi
                    case "3":
                        params = new AlienBaseParameters(Integer.parseInt(inGrid[row][col]));
                        grid[row][col] = new AlienBase("Aldi", params, new Coordinate(col * TILEWIDTH, (MAPHEIGHT-row) * TILEHEIGHT), TILEWIDTH, TILEHEIGHT, "aldi");
                        break;
                    // Holgate Windmill
                    case "4":
                        params = new AlienBaseParameters(Integer.parseInt(inGrid[row][col]));
                        grid[row][col] = new AlienBase("Holgate Windmill", params, new Coordinate(col * TILEWIDTH, (MAPHEIGHT-row) * TILEHEIGHT), TILEWIDTH, TILEHEIGHT, "Holgate-Windmill");
                        break;
                    //Jorvick Viking Centre
                    case "5":    
                        params = new AlienBaseParameters(Integer.parseInt(inGrid[row][col]));
                        grid[row][col] = new AlienBase("Jorvick Viking Centre", params, new Coordinate(col * TILEWIDTH, (MAPHEIGHT-row) * TILEHEIGHT), TILEWIDTH, TILEHEIGHT, "viking");
                        break;
                    //York train station
                    case "6":
                        params = new AlienBaseParameters(Integer.parseInt(inGrid[row][col]));
                    	grid[row][col] = new AlienBase("York Station", params, new Coordinate(col * TILEWIDTH, (MAPHEIGHT-row) * TILEHEIGHT), TILEWIDTH, TILEHEIGHT, "york_station");
                    	break;
                   //York City FC stadium
                    case "7":
                        params = new AlienBaseParameters(Integer.parseInt(inGrid[row][col]));
                    	grid[row][col] = new AlienBase("York City FC", params, new Coordinate(col * TILEWIDTH, (MAPHEIGHT-row) * TILEHEIGHT), TILEWIDTH, TILEHEIGHT, "york_city_fc");
                    	break;
                }
            }
        }
    }

    /* Returns all currently active alien bases
    * TODO: Inefficient! Runs in O(2n) time :/
    */
    public AlienBase[] getBases() {
        AlienBase[] bases;
        int numBases = 0;
        for (IRenderable[] row: this.grid) {
            for (IRenderable cell: row) {
                if (cell instanceof AlienBase) {
                    numBases++;
                }
            }
        }
        bases = new AlienBase[numBases];
        int currentBase = 0;
        for (IRenderable[] row: this.grid) {
            for (IRenderable cell: row) {
                if (cell instanceof AlienBase) {
                    bases[currentBase] = (AlienBase) cell;
                    currentBase++;
                }
            }
        }
        return bases;
    }
    //Renders map elements onto the screen
    public void render(SpriteBatch batch) {
        batch.draw (this.bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //Iterates through grid and call each object's render method
        for (int col = 0; col < grid.length; col++) {
            for (int row = 0; row < grid[0].length; row++) {
                if (grid[row][col] != null)
                    grid[row][col].render(batch);
            }
        }
    }
    public boolean isInStationRange(Coordinate engineCoordinates) {
        float tempX = engineCoordinates.x - fireStation.x;
        float tempY = engineCoordinates.y - ((MAPHEIGHT * TILEHEIGHT) - fireStation.y);
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
    public int getStationX() { return (int)fireStation.x;}
    public int getStationY() {
    	return (MAPHEIGHT * TILEHEIGHT) - (int)fireStation.y;
    }
}

