package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {
	
	public static final int WIDTH = 640;
	public static final int HEIGHT = 360;
	public static final int FPS_CAP = 600;
	public static String TITLE = "JAVA GAME ENGINE TEST ";
	public static String VER = "v0.0.1";
	
	public static void createDisplay() {
		
		ContextAttribs attribs = new ContextAttribs(3,2)
		.withForwardCompatible(true)
		.withProfileCore(true);
		
		
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setLocation(WIDTH / 2, HEIGHT / 2);
			Display.setTitle(TITLE + VER);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		
	}
	
	public static void updateDisplay() {
		Display.sync(FPS_CAP);
		Display.update();
	}

	public static void closeDisplay() {
		
		Display.destroy();
		
	}
	
}
