package Prototipo;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener {
	
	//Se declaran las variables de la consola de Juego
	public static final int WIDTH = 400;
	public static final int HEIGHT = 400;
	
	private Thread hilo;
	private boolean running;
	private BufferedImage imagen;
	private Graphics2D g;
	private int FPS = 30;
	private int targetTime = 1000/FPS;
	private TileMap tilemap;
	private Jugador jugador;
	
	
	//Se genera el constructor
	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setFocusable(true);
		requestFocus();
	}
	
	public void addNotify() {
		super.addNotify();
		if(hilo == null) {
		   hilo = new Thread(this);
		   hilo.start();
		}
		addKeyListener(this);
	}
	
	@Override
	public void run() {
		init();
		long startTime;
		long urdTime;
		long waitTime;
		
		while(running) {
			startTime = System.nanoTime();		
			update();
			render();
		    draw();
		    urdTime = (System.nanoTime() - startTime) / 1000000;
		    waitTime = targetTime -urdTime;
		    
		    try {
				Thread.sleep(waitTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}	
	
	private void init() {		
		running = true;
		imagen =  new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) imagen.getGraphics();		
		tilemap = new TileMap("E:\\JAVA LAB\\Proto\\src\\Prototipo\\testmap.txt", 32);
		jugador = new Jugador(tilemap); 
		jugador.setx(50);
		jugador.sety(50);
	}

	private void update() {
		tilemap.update();
		jugador.update();
	}
	
	private void render() {
	
	// Traemos a la clase principal el dibujo con las caracteristicas
	// de los objetos	
		tilemap.draw(g);
		jugador.draw(g);
	}
	
	private void draw() {
		Graphics g2 = getGraphics();
		g2.drawImage(imagen, 0, 0, null);
		g2.dispose();
	}

	@Override
	public void keyPressed(KeyEvent tecla) {
		int code = tecla.getKeyCode();
		if (code == KeyEvent.VK_LEFT) {
			jugador.setIzquierda(true);
		}
		if (code == KeyEvent.VK_RIGHT) {
			jugador.setDerecha(true);
		}
		if (code == KeyEvent.VK_UP) {
			jugador.setSaltando(true);
		}
	}

	@Override
	public void keyReleased(KeyEvent tecla) {
		int code = tecla.getKeyCode();
		if (code == KeyEvent.VK_LEFT) {
			jugador.setIzquierda(false);
		}
		if (code == KeyEvent.VK_RIGHT) {
			jugador.setDerecha(false);
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
