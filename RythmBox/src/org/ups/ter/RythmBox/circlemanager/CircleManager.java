package org.ups.ter.RythmBox.circlemanager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class CircleManager {

	private int gridCellWidth;
	private int gridCellHeight;
	
	private Texture[] numberTextures = new Texture[6];
	private List<Circle> circles = new ArrayList<Circle>();
	private int nextCircle;
	private int currentScore;
	
	private int currentNumberOfCircles = 0;
	private int removeAllAt = 0;
	private int lastDisplay = 0;
	ArrayList<Integer> usedX = new ArrayList<Integer>();
	ArrayList<Integer> usedY = new ArrayList<Integer>();
	


	public CircleManager(int screenWidth, int screenHeight) {
		gridCellWidth = screenWidth / 8;
		gridCellHeight = screenWidth / 4;
		currentScore = 0;
		
		initTextures();
	}
	
	public List<Circle> getCircles() {
		return circles;
	}
	
	public int getScore() {
		return currentScore;
	}
	
	private void initTextures() {
		for(int i = 0; i < 6; i++) {
			String imageFile = "data/numbers/number-" + (i+1) + ".png";
			numberTextures[i] = new Texture(Gdx.files.internal(imageFile));
			numberTextures[i].setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}		
	}
	

	public List<Circle> generateNewCircles() {
		int circlesNumber = MathUtils.random(1, 5);
		
		for(Circle c : circles) {
			c.remove();
		}
		
		circles.clear();
		
		usedX = new ArrayList<Integer>();
		usedY = new ArrayList<Integer>();
		
		for(int i = 0; i <= circlesNumber; i++) {
			int cellX, cellY;
			do {
				cellX = MathUtils.random(1, 6);
				cellY = MathUtils.random(0, 2);
			} while (usedX.contains(cellX) && usedY.contains(cellY));

			usedX.add(cellX);
			usedY.add(cellY);
			
			int circlePosX = gridCellWidth * cellX;
			int circlePosY = gridCellHeight * cellY;
			
			Circle tmpCircle = new Circle(this, i, numberTextures[i], circlePosX, circlePosY);
			tmpCircle.setTouchable(Touchable.enabled);
			tmpCircle.setZIndex(99);

			circles.add(tmpCircle);
			
			// System.out.println("Generated circle #" + i + "   posX=" + circlePosX + " posY=" + circlePosY);
		}
		
		nextCircle = 0;
		
		return circles;
	}
	
	public Circle generateCircles(){
		if (currentNumberOfCircles == removeAllAt) {
			for(Circle c : circles) {
				c.remove();
			}
			circles.clear();
			currentNumberOfCircles = -1;
		}
		
		if (currentNumberOfCircles == -1) {
			usedX.clear();
			usedY.clear();
			nextCircle = 0;
			lastDisplay=0;
			removeAllAt = MathUtils.random(3, 5);
		}
		
		if (lastDisplay > 1 || MathUtils.random(1, 2) == 1){
			lastDisplay=0;
			currentNumberOfCircles ++;
			return createCircle();
		} else lastDisplay++;	
		
		return null;
	}
	
	// genere un nouveau cercle
	private Circle createCircle(){
		int cellX, cellY;
		do {
			cellX = MathUtils.random(1, 6);
			cellY = MathUtils.random(0, 2);
		} while (usedX.contains(cellX) && usedY.contains(cellY));

		usedX.add(cellX);
		usedY.add(cellY);
		
		int circlePosX = gridCellWidth * cellX;
		int circlePosY = gridCellHeight * cellY;
		
		Circle tmpCircle = new Circle(this, currentNumberOfCircles, numberTextures[currentNumberOfCircles], circlePosX, circlePosY);
		tmpCircle.setTouchable(Touchable.enabled);
		tmpCircle.setZIndex(99);	
		circles.add(tmpCircle);
		
		return tmpCircle;
	}

	public void circleTouched(int number) {
		if(number == nextCircle) {
			circles.get(nextCircle).remove();
			nextCircle++;
			currentScore += 10*(nextCircle+1);
		} else {
			// TODO
			// Game over
		}
		
	}

}
