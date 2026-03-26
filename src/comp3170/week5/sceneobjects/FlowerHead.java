package comp3170.week5.sceneobjects;

import static comp3170.Math.TAU;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import comp3170.GLBuffers;
import comp3170.SceneObject;
import comp3170.Shader;
import comp3170.ShaderLibrary;
public class FlowerHead extends SceneObject {
	
	private static final String VERTEX_SHADER = "vertex.glsl";
	private static final String FRAGMENT_SHADER = "fragment.glsl";
	private Shader shader;

	private Vector3f petalColour = new Vector3f(1.0f,1.0f,1.0f);

	private Vector4f[] vertices;
	private int vertexBuffer;
	private int[] indices;
	private int indexBuffer;

	public FlowerHead(int nPetals, Vector3f colour) {
		
		// TODO: Create the flower head. (TASK 1)
		// Consider the best way to draw the mesh with the nPetals input. 
		// Note that this may involve moving some code OUT of this class!
		
	
		//@formatter:off
		// construct a 'circle' (i.e. a polygon with NSIDES) as a list of triangles
		//
		// Vertex 0 is at the centre and the others are anticlockwise around the outside.
		// E.g. for NSIDES = 6
		//
		//   3---2
		//  / \ / \
		// 4---0---1
		//  \ / \ /
		//   5---6
		//@formatter:on

		vertices = new Vector4f[nPetals + 1];

		vertices[0] = new Vector4f(0, 0, 0, 1); // centre

		Matrix4f rotate = new Matrix4f();

		// construct each vertex by rotating the vector (1,0) by fraction i/n of a full
		// turn

		for (int i = 0; i < nPetals; i++) {
			float angle = i * TAU / nPetals;

			rotate.rotationZ(angle); // R = R(angle)
			vertices[i + 1] = new Vector4f(1, 0, 0, 1); // v = (1,0,0)
			vertices[i + 1].mul(rotate); // v = R v
		}
		vertexBuffer = GLBuffers.createBuffer(vertices);

		indices = new int[nPetals * 3]; // each side creates 1 triangle with 3 vertices

		int k = 0;
		for (int i = 1; i <= nPetals; i++) {
			indices[k++] = 0;
			indices[k++] = i;
			indices[k++] = (i % nPetals) + 1; // wrap around when i = NSIDES
		}

		indexBuffer = GLBuffers.createIndexBuffer(indices);
		
		shader = ShaderLibrary.instance.compileShader(VERTEX_SHADER, FRAGMENT_SHADER);		
		
		petalColour = colour;
	}

	public void update(float dt) {
		// TODO: Make the flower head rotate. (TASK 5)
	}

	public void drawSelf(Matrix4f mvpMatrix) {
		// TODO: Add any appropriate draw code. (TASK 1)
		shader.enable();
		shader.setUniform("u_mvpMatrix", mvpMatrix);
		shader.setAttribute("a_position", vertexBuffer);
		shader.setUniform("u_colour", petalColour);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
		glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
	}
}
