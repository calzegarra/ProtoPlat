package Prototipo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileReader;


//Diseño y Renderizacion del escenario
public class TileMap {

	private int x;
	private int y;
	
	private int tilesize;
	private int [][] map;
	private int mapwidth;
	private int mapheight;
	
	public TileMap(String s, int tilesize) {
		this.tilesize = tilesize;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(s));
			mapwidth = Integer.parseInt(br.readLine());
			mapheight = Integer.parseInt(br.readLine());
			map = new int [mapheight][mapwidth];
			
			String delimiters = " ";
			for (int row = 0; row < mapheight; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delimiters);
				for (int col = 0; col < mapwidth; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		}		

	public int getX() {
		return x;
	}

	public void setX(int i) {
		x = i;
	}

	public int getY() {
		return y;
	}

	public void setY(int i) {
		y = i;
	}

	public int getColTile(int x) {
		return x / tilesize;
	}
	
	public int getFilTile(int y) {
		return y / tilesize;
	}
	
	public int getTile(int fila, int colum) {
		return map[fila][colum];
	}
	
	public int getTileSize() {
		return tilesize;
	}
	
	public void update() {	
		
	}
	
	public void draw(Graphics2D g) {
		
		for (int row = 0; row < mapheight; row++) {
			for (int col = 0; col < mapwidth; col++) {
				int rc = map[row][col];
				
				if (rc == 0) {
					g.setColor(Color.BLACK);
				}
				if (rc == 1) {
					g.setColor(Color.WHITE);
				}				
				g.fillRect(x + col * tilesize, y + row * tilesize, tilesize, tilesize);
			}
		}
	}
	
}
