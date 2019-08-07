package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.*;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		
		Loader loader = new Loader();

		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("ground"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("1476"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("sandstone"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("cinder-blocks"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture,rTexture,gTexture,bTexture);
		
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("splatMap"));
		
		//Player
		ModelData playerData = OBJFileLoader.loadOBJ("user");
		RawModel playerModel = loader.loadToVAO(playerData.getVertices(), playerData.getTextureCoords()
				, playerData.getNormals(), playerData.getIndices());
		TexturedModel playerM = new TexturedModel(playerModel, new ModelTexture(loader.loadTexture("CharacterTexture")));
		
		
		//Environment Stuff
		RawModel model = OBJLoader.loadObjModel("TheTree", loader);
		RawModel model1 = OBJLoader.loadObjModel("grassGround", loader);
		RawModel fern = OBJLoader.loadObjModel("Fern", loader);
		RawModel rock = OBJLoader.loadObjModel("Rock", loader);
		
		TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("TreeTexture2")));
		TexturedModel staticModel1 = new TexturedModel(model1,new ModelTexture(loader.loadTexture("grass")));
		TexturedModel staticModel2 = new TexturedModel(fern,new ModelTexture(loader.loadTexture("sprites/n_grass_diff_0_05")));
		TexturedModel staticModel3 = new TexturedModel(rock,new ModelTexture(loader.loadTexture("rock")));
		
		staticModel1.getTexture().setTransparent(true);
		staticModel2.getTexture().setTransparent(true);
		staticModel1.getTexture().setUseFakeLighting(true);
		staticModel2.getTexture().setUseFakeLighting(true);
		
		List<Entity> entities = new ArrayList<Entity>();
		List<Entity> entities1 = new ArrayList<Entity>();
		List<Entity> entities2 = new ArrayList<Entity>();
		List<Entity> entities3 = new ArrayList<Entity>();
        Random random = new Random();
        for(int i=0;i<500;i++){
            entities.add(new Entity(staticModel, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,3));
        }
        for(int i=0;i<5000;i++){
            entities1.add(new Entity(staticModel1, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,random.nextFloat()*-600,0,3));
            entities2.add(new Entity(staticModel2, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,random.nextFloat()*-600,0,3));
            entities3.add(new Entity(staticModel3, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),random.nextFloat()*800 - 400,random.nextFloat()*-600,random.nextFloat()*800 - 400,0.2f));
        }
        //Scene setup
		
		Light light = new Light(new Vector3f(5000,4000,100),new Vector3f(1,1,0.88f));
		
		Terrain terrain1  = new Terrain(0,1,loader, texturePack, blendMap, "noise");
		
		Player player = new Player(playerM, new Vector3f(25, 0, -95), 0,0,0,1.5f);
		Camera camera = new Camera(player);
		
		MasterRenderer renderer = new MasterRenderer();
		while(!Display.isCloseRequested()) {
			camera.move(terrain1);
			player.move(terrain1);
			renderer.processEntity(player);
			renderer.processTerrain(terrain1);
			for(Entity entity:entities){
                renderer.processEntity(entity);
            }			
			for(Entity entity:entities1){
                renderer.processEntity(entity);
            }
			for(Entity entity:entities2){
                renderer.processEntity(entity);
            }
			for(Entity entity:entities3){
                renderer.processEntity(entity);
            }
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
			
		}
		
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
		
	}

}
