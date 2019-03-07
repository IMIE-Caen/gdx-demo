package my.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Hello extends ApplicationAdapter {
	World world;
	Box2DDebugRenderer debugRenderer;
	OrthographicCamera cam;
	Body body;
	@Override
	public void create () {
		world = new World(new Vector2(0,-10f), true);
		cam = new OrthographicCamera(32,24);
		debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(0,0);


		// add it to the world
		body = world.createBody(bodyDef);

		// set the shape (here we use a box 50 meters wide, 1 meter tall )
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(1,1);

		// set the properties of the object ( shape, weight, restitution(bouncyness)
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1f;

		// create the physical object in our body)
		// without this our body would just be data in the world
		body.createFixture(fixtureDef);


		createFloor();
		// we no longer use the shape object here so dispose of it.
		shape.dispose();

	}





	@Override
	public void render () {
		world.step(Gdx.graphics.getDeltaTime(), 3, 3);
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		debugRenderer.render(world, cam.combined);

		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			if(! body.isActive())
				body.applyForceToCenter(0, 20, true);
		}
	}






	void createFloor(){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(0, -10);

		// add it to the world
		Body bodyd = world.createBody(bodyDef);

		// set the shape (here we use a box 50 meters wide, 1 meter tall )
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(50, 1);

		// create the physical object in our body)
		// without this our body would just be data in the world
		bodyd.createFixture(shape, 0.0f);

		// we no longer use the shape object here so dispose of it.
		shape.dispose();
	}

	@Override
	public void dispose () {
		world.dispose();
	}
}
