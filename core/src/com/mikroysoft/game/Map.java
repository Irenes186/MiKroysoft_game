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
		
		File file = new File("map_information.txt");
		
		BufferedReader reader = new BufferedReader(new FileReader(file)); 
		
		String fileInput;
		String[] items;
                int col = MAPHEIGHT -1 ;
		while ((fileInput = reader.readLine()) != null) {
			items = fileInput.split("",MAPWIDTH);

                        for (int row = 0; row < MAPWIDTH; row++) {
                            switch (items[row]) {
                                case "0" :
                                    grid[row][col] = new FireStation(10, new Coordinate(row * TILEWIDTH, col * TILEHEIGHT), TILEWIDTH, TILEHEIGHT);
                                    break;
                                case "1" :
                                    grid[row][col] = new Road(new Coordinate(row * TILEWIDTH, col * TILEHEIGHT), TILEWIDTH, TILEHEIGHT, "roadV");
                                    break;
                                

                            }
                        }
                    col--;
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
