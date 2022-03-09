package com.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Tile {
	static final int WIDTH = 100;
	static final int HEIGHT = 100;
	static final int SLIDE_SPEED = 30;
	static final int ARC_WIDTH = 15;
	static final int ARC_HEIGHT = 15;
	
	private int value;
	private BufferedImage tileImage;
	private Color color;
	private Color text;
	private Font font;
	private Point slideTo;
	private int x;
	private int y;
	
	private boolean beginningAnimation = true;
	private double scaleFirst = 0.1;
	private BufferedImage beginningImage;
	
	private boolean combineAnimation = false;
	private double scaleCombine = 1.2; //makes your animation of combination a little more popped up look
	private BufferedImage combineImage;
	
	private boolean canCombine = true;
	
	public Tile (int value, int x, int y) {
		this.value = value; 
		this.x = x;
		this.y = y;
		slideTo = new Point(x,y);
		tileImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		beginningImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		combineImage = new BufferedImage(WIDTH*2, HEIGHT*2, BufferedImage.TYPE_INT_ARGB);
		drawImage();
	}
	
	private void drawImage() {
		Graphics2D g = (Graphics2D)tileImage.getGraphics();
		
		if(value == 2) {
			color = new Color(0xe9e9e9);
			text = new Color(0x000000);
		}
		else if(value == 4) {
			color = new Color(0xe6daab);
			text = new Color(0x000000);
		}
		else if(value == 8) {
			color = new Color(0xf79d3d);
			text = new Color(0x000000);
		}
		else if(value == 16) {
			color = new Color(0xe6daab);
			text = new Color(0x000000);
		}
		else if(value == 32) {
			color = new Color(0xf28007);
			text = new Color(0xffffff);
		}
		else if(value == 64) {
			color = new Color(0xf55e3b);
			text = new Color(0xffffff);
		}
		else if(value == 128) {
			color = new Color(0xff0000);
			text = new Color(0xffffff);
		}
		else if(value == 256) {
			color = new Color(0xf6e873);
			text = new Color(0xffffff);
		}
		else if(value == 512) {
			color = new Color(0xf5e455);
			text = new Color(0xffffff);
		}
		else if(value == 1024) {
			color = new Color(0xf7e12c);
			text = new Color(0xffffff);
		}
		else if(value == 2048) {
			color = new Color(0xffe400);
			text = new Color(0xffffff);
		}
		else {
			color = Color.BLACK;
			text = Color.WHITE;
		}
		
		g.setColor(new Color(0,0,0,0));
		g.fillRect(0,0,WIDTH,HEIGHT);
		
		g.setColor(color);
		g.fillRoundRect(0, 0, WIDTH, HEIGHT, ARC_WIDTH, ARC_HEIGHT);
		
		g.setColor(text);
		
		if(value<=64) {
			font = Game.font.deriveFont(36f);
		}
		else {
			font = Game.font;
		}
		g.setFont(font);
		
		int drawX = WIDTH / 2 - DrawUtils.getMessageWidth(""+value, font, g) / 2;
		int drawY = HEIGHT / 2 + DrawUtils.getMessageHeight(""+value, font, g) / 2;
		g.drawString(""+value, drawX, drawY);
		g.dispose();
	}
	
	public void update() {
		if(beginningAnimation) {
			AffineTransform transform = new AffineTransform();
			transform.translate(WIDTH / 2 - scaleFirst * WIDTH / 2, HEIGHT / 2 - scaleFirst * HEIGHT / 2);
			transform.scale(scaleFirst, scaleFirst);
			Graphics2D g2d = (Graphics2D) beginningImage.getGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g2d.setColor(new Color(0,0,0,0));
			g2d.fillRect(0, 0, WIDTH, HEIGHT);
			g2d.drawImage(tileImage, transform, null);
			scaleFirst += 0.1;
			g2d.dispose();
			if(scaleFirst >= 1) beginningAnimation = false;
		}
		else if(combineAnimation) {
			AffineTransform transform = new AffineTransform();
			transform.translate(WIDTH / 2 - scaleCombine * WIDTH / 2, HEIGHT / 2 - scaleCombine * HEIGHT / 2);
			transform.scale(scaleCombine, scaleCombine);
			Graphics2D g2d = (Graphics2D) combineImage.getGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g2d.setColor(new Color(0,0,0,0));
			g2d.fillRect(0, 0, WIDTH, HEIGHT);
			g2d.drawImage(tileImage, transform, null);
			scaleCombine -= 0.1;
			g2d.dispose();
			if(scaleCombine <= 1) combineAnimation = false;
		}
	}
	
	public void render(Graphics2D g) {
		if(beginningAnimation) {
			g.drawImage(beginningImage, null, x, y);
		}
		else if(combineAnimation) {
			g.drawImage(combineImage, null, (int)(x + WIDTH / 2 - scaleCombine * WIDTH / 2), (int)(y + HEIGHT / 2 - scaleCombine * HEIGHT / 2));
		}
		else {
			g.drawImage(tileImage, x, y, null);
		}
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
		drawImage();
	}

	public boolean canCombine() {
		return canCombine;
	}

	public void setCanCombine(boolean canCombine) {
		this.canCombine = canCombine;
	}

	public Point getSlideTo() {
		return slideTo;
	}

	public void setSlideTo(Point slideTo) {
		this.slideTo = slideTo;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isCombineAnimation() {
		return combineAnimation;
	}

	public void setCombineAnimation(boolean combineAnimation) {
		this.combineAnimation = combineAnimation;
		if(combineAnimation) scaleCombine = 1.3;
	}
}
