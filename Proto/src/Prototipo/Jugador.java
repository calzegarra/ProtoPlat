package Prototipo;

import java.awt.Color;
import java.awt.Graphics2D;

public class Jugador {

	//Posicion
	private double x;
	private double y;
	private double dx;
	private double dy;
	
	//caracteristicas
	private int width;
	private int height;
	
	//movimientos basicos
	private boolean izquierda;
	private boolean derecha;
	private boolean saltando;
	private boolean cayendo;
	
	//velocidad de movimientos 
	private double moveSpeed;
	private double maxSpeed;
	private double maxFallSpeed;
	private double stopSpeed;
	private double jumpStart;
	private double gravedad;
	
	private TileMap tilemap;
	
	private boolean topIzq;
	private boolean topDer;
	private boolean botIzq;
	private boolean botDer;
	
	public Jugador(TileMap tm) {
		
		tilemap = tm;
		
		//Cargando las caracteristicas del personaje
		width = 20;
		height = 20;
		moveSpeed = 0.6;
		maxSpeed = 4.2;
		maxFallSpeed = 12;
		stopSpeed = 0.30;
		jumpStart = -11.0;
		gravedad = 0.64;
		
	}
	
	public void setx(int i) {
		x = i;
	}
	
	public void sety(int i) {
		y = i;
	}
	
	public void setIzquierda(boolean b) {
		izquierda = b;
	}
	
	public void setDerecha(boolean b) {
		derecha = b;
	}
	
	public void setSaltando(boolean b) {
		if (!cayendo) {
			saltando = true;
		}
	}
	
	private void calcularEsquina(double x, double y) {
		
		int IzqTile = tilemap.getColTile((int)(x - width / 2));
		int DerTile = tilemap.getColTile((int)(x + width / 2) - 1);
		
		int TopTile = tilemap.getFilTile((int)(y - height /2));
		int BotTile = tilemap.getFilTile((int)(y + height /2) - 1);
		
		topIzq = tilemap.getTile(TopTile, IzqTile) == 0;
		topDer = tilemap.getTile(TopTile ,DerTile) == 0;
		
		botIzq = tilemap.getTile(BotTile, IzqTile) == 0;
		botDer = tilemap.getTile(BotTile, DerTile) == 0;		
		
	} 
	
	//Evaluar esto
	public void update() {
       //Determinar la siguiente posicion
	   if (izquierda) {
		   dx -= moveSpeed;
		   if(dx < -maxSpeed) {
			  dx = -maxSpeed;
		   }
	   }
	   else if (derecha) {
	    	  dx += moveSpeed;
	    	  if(dx > maxSpeed) {
	    		dx = maxSpeed;
	    	}
	   }
       else {
    	   if(dx > 0) {
    		  dx -= stopSpeed;
    		  if(dx < 0) {
    			 dx = 0;
    		  }
    	   }
    	   else if(dx < 0) {
    		   dx += stopSpeed;
    		   if(dx > 0){
    			  dx = 0;
    		   }
    	   }
       }
	   if (saltando) {
		   dy = jumpStart;
		   cayendo = true;
		   saltando = false;
	   }
	   
	   if (cayendo) {
		   dy += gravedad;
		   if(dy > maxFallSpeed) {
			  dy = maxFallSpeed; 
		   }
	   }
	   else {
		   dy = 0;
	   }
	   //Verificar las coliciones
	   int actCol = tilemap.getColTile((int) x);
	   int actFil = tilemap.getFilTile((int) y); 
	   
	   double tox = x + dx;
	   double toy = y + dy;
	   
	   double tempx = x;
	   double tempy = y;
	   
	   calcularEsquina(x, toy);
	   if (dy < 0) {
		   if (topIzq || topDer) {
			   dy = 0;
			   tempy = actFil * tilemap.getTileSize() + height/2;
		   }
		   else {
			   tempy += dy;
		   }
	   }
	   if (dy > 0){
		   if(botIzq || botDer) {
			   dy = 0;
			   cayendo = false;
			   tempy = (actFil + 1)* tilemap.getTileSize() - height/2;
		   }
		   else {
			   tempy += dy;
		   }
	   }
	   
	   calcularEsquina(tox, y);
	   if (dx < 0) {
		   if (topIzq || topDer) {
			   dx = 0;
			   tempx = actCol * tilemap.getTileSize() + width/2;
		   }
		   else {
			   tempx += dx;
		   }
	   }
	   if (dx > 0){
		   if(botIzq || botDer) {
			   dx = 0;
			   tempx = (actCol + 1)* tilemap.getTileSize() - width/2;
		   }
		   else {
			   tempx += dx;
		   }
	   }	
	   
	   if(!cayendo) {
		   calcularEsquina(x, y + 1);
		   if(!botIzq && !botDer) {
			   cayendo = true;
		   }
	   }
	   
	   x = tempx;
	   y = tempy;
	   
	   //Cuando el mapa se mueve con el personaje
	   tilemap.setX((int)(GamePanel.WIDTH / 2 - x));
	   tilemap.setY((int)(GamePanel.HEIGHT/ 2 - y));
	}
	
	// Luego de definir las caracteristicas de nuestro personaje, 
	// lo dibujamos en la clase TileMap
	public void draw(Graphics2D g) {
		int tx = tilemap.getX();
		int ty = tilemap.getY();
		
		g.setColor(Color.BLUE);
		g.fillRect(
				(int)(tx + x - width/2), 
				(int)(ty + y - height/2), 
				width, 
				height);
	}
}
