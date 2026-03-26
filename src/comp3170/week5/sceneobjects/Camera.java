package comp3170.week5.sceneobjects;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Matrix4f;

import comp3170.SceneObject;
import comp3170.InputManager;

public class Camera extends SceneObject {

	private float zoom = 20.0f; // You'll need this when setting up your projection matrix...
	private Matrix4f projectionMatrix = new Matrix4f();
	private Matrix4f viewMatrix = new Matrix4f();
	
	public Camera() {
		
	}
	float width; 
	float height; 
	public void resize(int w, int h) {
		//TODO: Change the projection matrix when the window is resized. (TASK 2)
		width = w; 
		height = h; 
		
		float aspect = w/h; 
		projectionMatrix.scaling(zoom*aspect,zoom,1f); 
	}
	
	public Matrix4f GetViewMatrix(Matrix4f dest) {
		viewMatrix = getMatrix();
		return viewMatrix.invert(dest);
	}
	
	public Matrix4f GetProjectionMatrix(Matrix4f dest) {
		//      [ sx  0   0 ]
		// MS = [ 0   sy  0 ]
		//      [ 0   0   1 ]
//		projectionMatrix = getMatrix(); 
//		projectionMatrix.m00(width / zoom);
//		projectionMatrix.m11(height / zoom);
		
		return projectionMatrix.invert(dest);
	}
	
// TODO: Make the camera zoom in-and-out based on user input. (TASK 4)
// You'll need to move some code around!
	
	public void update(InputManager input, float deltaTime) {
		if (input.isKeyDown(GLFW_KEY_UP)) {
			// TODO: Zoom the camera in
		}
			
		if (input.isKeyDown(GLFW_KEY_DOWN)) {
			// TODO: Zoom the camera out
		}
	}
}