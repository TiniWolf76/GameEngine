package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entity.Camera;
import entity.Entity;
import models.RawModel;
import models.TexturedModel;
import renderEngine.*;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);

		
		
		
		RawModel model = OBJLoader.loadObjModel("GTR", loader);
		
		TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("Texture")));
		
		Entity entity = new Entity(staticModel, new Vector3f(0,0,-10),0,0,0,1);
		
		Camera camera = new Camera();
		
		
		while(!Display.isCloseRequested()) {
			renderer.prepare();
			camera.move();
			entity.increasePosition(0, 0, 0);
			entity.increaseRotation(0, .01f, 0);
			shader.start();
			shader.loadViewMatrix(camera);
			renderer.render(entity,shader);
			shader.stop();
			DisplayManager.updateDisplay();
			
		}
		
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
		
	}

}
