package droplet;

import java.util.ArrayList;

import processing.core.PApplet;

public class Droplet extends PApplet {
	
	DropletBlueprint droplet = new DropletBlueprint (this, random(100, 400), 0, 25, 25, 0, random(1, 5), random(255), random(255), random(255));
	BucketBlueprint bucket = new BucketBlueprint(this, mouseX, 450, 100, 50, 1, 0, 0, 255);
	ArrayList<DropletBlueprint> d = new ArrayList<DropletBlueprint>();
	int oldtime = 0;
	int newtime;
	int gamestate = 0; // 0: run, 1: win, 2: lose
	int counter1 = 0;
	int counter2 = 0;
	
	public void setup() {
		size (500, 500);
		background (255, 255, 255);
		d.add(droplet);
		gamestate = 1;
		
	}
	
	public void draw() {
		if (gamestate == 1) {
			if (counter2 == 0) {
				fill (255, 255, 255);
				rect (0, 0, width, height);
				background (255, 255, 255);
				textSize (50);
				fill (0, 0, 0);
				text ("Droplet", 150, 250);
				textSize (25);
				fill (0, 0, 0);
				text ("Move Bucket to Catch Droplets", 50, 275);
				textSize (25);
				fill (0, 0, 0);
				text ("Press Mouse to Start", 125, 300);
				textSize (25);
				fill (0, 0, 0);
				text ("Press Key to Restart", 125, 325);
				newtime = millis();
				if (newtime - oldtime > 1000) {
					d.add(new DropletBlueprint (this, random(100, 400), 0, 25, 25, 0, random(1, 5), random(255), random(255), random(255)));
					oldtime = newtime;
				}
				bucket.display();
				game();
			if (mousePressed) {
				counter1++;
			}
			
			if (counter1 >= 1) {
				bucket.drive();
				catchdroplet();
				updatedroplet();
			}
			
			if (keyPressed) {
				d.clear();
				setup();
			}
		}
	}
		else if (gamestate == 2) {
			droplet.vx = 0;
			droplet.vy = 0;
			textSize (25);
			fill (0, 0, 0);
			text ("You Lost", 200, 350);
			if (keyPressed) {
				d.clear();
				setup();
			}
		}
	}
	
	private void catchdroplet() {
		for (int i = 0; i < d.size(); i++) {
			droplet = d.get(i);
			if (droplet.getx() > bucket.getx() && droplet.getx() < (bucket.getx() + 100) 
					&& droplet.gety() + 25 > bucket.gety()) {
				d.remove(i);
			}
		}
	}
	
	private void updatedroplet() {
		for (int i = 0; i < d.size(); i++) {
				droplet = d.get(i);
				droplet.display();
				droplet.drive();
		}
	}
	
	private void game() {
		for (int i = 0; i < d.size(); i++) {
			if (d.get(i).gety() > 500) {
				gamestate = 2;
			}
		}
	}
}