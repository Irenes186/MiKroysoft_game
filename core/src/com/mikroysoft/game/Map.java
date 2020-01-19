package com.mikroysoft.game;

import java.io.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/** 
 * Container class for all map elements
 * I.e includes roads and alien bases
 * but does not include GUI or fire engines
 */
public class Map {
    
    Texture bg;
    public Coordinate fireStation;
    IRenderable[][] grid;
    String[][] inGrid;

    /**
     * Class Constructor.
     * Puts all the values from the map-information file into
     * the grid of IRenderables.
     * 
     * @param bgtex The string of the name for the background file,
     * without the .png suffix
     */
    public Map(String bgtex) throws Exception {
        this.bg = new Texture(bgtex + ".png");
        grid = new IRenderable[Util.MAPWIDTH][Util.MAPHEIGHT];
        fireStation = new Coordinate(0, 0);
        
        // Grid containing text description of map
        inGrid = null;

        // read map info into inGrid where each character is a cell
        try {
            inGrid = readMapInfoFile();
        }
        catch(Exception e){
            System.out.println("Failed to read map_information.txt");
        }
        
        //Populate grid with class instances as described in map_information
        String fileName;
        //Variables for configuring road gfx
        boolean hasRoadAbove, hasRoadBelow, hasRoadLeft, hasRoadRight;
        AlienBaseParameters params;
        int row = 0;
        
        // iterate through inGrid
        for (row = 0; row < Util.MAPHEIGHT; row++) {
            for (int col = 0; col < Util.MAPWIDTH; col++) {
                //Decode the required class instance
                switch (inGrid[row][col]) {
                    //Instance fire stations
                    case "0" :
                        fireStation.setCoordinate(col * Util.TILEWIDTH, (Util.MAPHEIGHT-row) * Util.TILEHEIGHT);
                        grid[row][col] = new FireStation(10, fireStation);
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
                        if (col < (Util.MAPWIDTH - 1) && inGrid[row][col+1].equals("1")) {
                            hasRoadRight = true;
                        }
                        if (row < (Util.MAPHEIGHT - 1) && inGrid[row+1][col].equals("1")) {
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
                        grid[row][col] = new Road(new Coordinate(col * Util.TILEWIDTH, (Util.MAPHEIGHT-row) * Util.TILEHEIGHT), fileName);
                        break;
                    // Clifford's tower
                    case "2":
                        params = new AlienBaseParameters(Integer.parseInt(inGrid[row][col]));
                        grid[row][col] = new AlienBase("Cliffords's Tower", params, new Coordinate(col * Util.TILEWIDTH, (Util.MAPHEIGHT-row) * Util.TILEHEIGHT), "cliffords-tower");
                        break;
                    // Aldi
                    case "3":
                        params = new AlienBaseParameters(Integer.parseInt(inGrid[row][col]));
                        grid[row][col] = new AlienBase("Aldi", params, new Coordinate(col * Util.TILEWIDTH, (Util.MAPHEIGHT-row) * Util.TILEHEIGHT), "aldi");
                        break;
                    // Holgate Windmill
                    case "4":
                        params = new AlienBaseParameters(Integer.parseInt(inGrid[row][col]));
                        grid[row][col] = new AlienBase("Holgate Windmill", params, new Coordinate(col * Util.TILEWIDTH, (Util.MAPHEIGHT-row) * Util.TILEHEIGHT), "Holgate-Windmill");
                        break;
                    //Jorvik Viking Centre
                    case "5":
                        params = new AlienBaseParameters(Integer.parseInt(inGrid[row][col]));
                        grid[row][col] = new AlienBase("Jorvik Viking Centre", params, new Coordinate(col * Util.TILEWIDTH, (Util.MAPHEIGHT-row) * Util.TILEHEIGHT), "viking");
                        break;
                    //York train station
                    case "6":
                        params = new AlienBaseParameters(Integer.parseInt(inGrid[row][col]));
                    	grid[row][col] = new AlienBase("York Station", params, new Coordinate(col * Util.TILEWIDTH, (Util.MAPHEIGHT-row) * Util.TILEHEIGHT), "york_station");
                    	break;
                   //York City FC stadium
                    case "7":
                        params = new AlienBaseParameters(Integer.parseInt(inGrid[row][col]));
                    	grid[row][col] = new AlienBase("York City FC", params, new Coordinate(col * Util.TILEWIDTH, (Util.MAPHEIGHT-row) * Util.TILEHEIGHT), "york_city_fc");
                    	break;
                }
            }
        }
    }

    /**
     * Reads the file map-information.txt and returns each character
     * from the file in a 2D array.
     * 
     * @return A 2D string array of characters.
     */
    private String[][] readMapInfoFile() throws IOException {
        // import map info
        File file = new File("map_information.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String[][] grid = new String[Util.MAPWIDTH][Util.MAPHEIGHT];
        
        int row = 0;
        String fileInput;
        String[] items;

        // read map info into inGrid where each character is a cell
        while ((fileInput = reader.readLine()) != null) {
            items = fileInput.split("",Util.MAPWIDTH);
            for (int col = 0; col < Util.MAPWIDTH; col++) {
                grid[row][col] = items[col];
            }
            row++;
        }

        reader.close();
        return grid;
    }

    /**
     * Returns all alien base object, from the grid, in an array.
     * 
     * @return An array of alien bases in the grid.
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

    /**
     * Renders map elements onto the screen.
     * 
     * @param batch The spritebatch object that the map elements
     * should be rendered on.
     */
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

    /**
     * Checks and returns whether or not the fire station is within a game tile
     * of the coordinates provided, typically fireengine coordinates
     * 
     * @param engineCoordinates The Coordinate that the range is checked against.
     * @return true if the coordinate is within a tile of the firestation 
     * object, otherwise false.
     */
    public boolean isInStationRange(Coordinate engineCoordinates) {
        float tempX = engineCoordinates.x - fireStation.x;
        float tempY = engineCoordinates.y - ((Util.MAPHEIGHT * Util.TILEHEIGHT) - fireStation.y);
        if(tempX < 0) {
            tempX = tempX * -1;
        }
        if (tempY < 0) {
            tempY = tempY * -1;
        }
        if((tempX < Util.TILEWIDTH) && (tempY < Util.TILEHEIGHT)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the x coordinate of the firestation object in the grid array.
     * 
     * @return the station x coordinate as an int.
     */
    public int getStationX() { return (int)fireStation.x;}

    /**
     * Returns the y coordinate of the firestation object in the grid array.
     * 
     * @return the station y coordinate as an int.
     */
    public int getStationY() {
    	return (Util.MAPHEIGHT * Util.TILEHEIGHT) - (int)fireStation.y;
    }
}

