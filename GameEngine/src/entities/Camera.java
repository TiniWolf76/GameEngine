package entities;
 
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import terrains.Terrain;
 
public class Camera {
     
	private float distanceFromPlayer = 10;
	private float angleAroundPlayer = 0;
	private static float TERRAIN_HEIGHT = 0.5f;
	
    private Vector3f position = new Vector3f(0,0,0);
    private float pitch = 20;
    private float yaw;
    private float roll;
    
    private Player player;
     
    public Camera(Player player) {
    	this.player = player;
    }
    
    public void move(Terrain terrain) {
    	calculateZoom();
    	calculatePitch();
    	calculateAngleAroundPlayer();
    	float horizontalDistance = calculateHorizontalDistance();
    	float verticalDistance = calculateVerticalDistance();
    	calculatePosition(horizontalDistance, verticalDistance, angleAroundPlayer);
    	this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
		float terrainHeight = terrain.getHeightOfTerrain(getPosition().x, getPosition().z);
    	if(this.getPosition().y<terrainHeight) {
    		position.y = terrainHeight + 0.5f;
    	}
    }
 
    public Vector3f getPosition() {
        return position;
    }
 
    public float getPitch() {
        return pitch;
    }
 
    public float getYaw() {
        return yaw;
    }
 
    public float getRoll() {
        return roll;
    }
    
    private void calculatePosition(float horizDistance, float verticDistance, float angle) {
    	float theta = player.getRotY() + angle;
    	float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
    	float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
    	position.x = player.getPosition().x - offsetX;
    	position.y = (player.getPosition().y + verticDistance)+2;
    	position.z = player.getPosition().z - offsetZ;
    }
    
    private float calculateHorizontalDistance() {
    	return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }
    private float calculateVerticalDistance() {
    	return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }
    
    private void calculateZoom() {
    	float zoomLevel = Mouse.getDWheel() * 0.01f;
    	distanceFromPlayer -= zoomLevel;
    }
    
    private void calculatePitch() {
    	if(Mouse.isButtonDown(1)) {
    		float pitchChange = Mouse.getDY() * 0.1f;
    		pitch -= pitchChange;
    	}
    }
     
    private void calculateAngleAroundPlayer() {
    	if(Mouse.isButtonDown(1)) {
    		float angleChange = Mouse.getDX() * 0.3f;
    		angleAroundPlayer -= angleChange;
    	}
    }
     
 
}