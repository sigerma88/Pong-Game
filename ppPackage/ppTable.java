package ppPackage;

import static ppPackage.ppSimParams.*;
import java.awt.Color;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

/**
 * This class creates the table on which the ball bounces.
 * 
 * @author Siger Ma
 * 
 *         Student ID: 261051828
 * 
 *         Due November 17th, 2021
 * 
 *         The code in this Java program contains lines from
 *         "Siger_MA-Assignment3" written by Siger Ma, lines from "ECSE-202
 *         F2021 Assignment 4" written by Prof. Ferrie and lines from the
 *         Tutorials written by Katrina Poulin.
 */

public class ppTable {

	// Instance variable
	GraphicsProgram GProgram;

	/**
	 * Constructor for the creation of a table.
	 * 
	 * @param GProgram - a reference to the ppSim class used to manage the display
	 */

	public ppTable(GraphicsProgram GProgram) {

		this.GProgram = GProgram;

		// Create the Ground Plane
		addGroundPlane();

	}

	/**
	 * Method to convert world coordinates to screen coordinates.
	 * 
	 * @param P - a point object in world coordinates
	 * @return p - the corresponding point object in screen coordinates
	 */

	public GPoint W2S(GPoint P) {
		return new GPoint((P.getX() - Xmin) * Xs, ymax - (P.getY() - Ymin) * Ys);
	}

	/**
	 * Method to convert screen coordinates to world coordinates
	 * 
	 * @param p - a point in screen coordinates
	 * @return P - the corresponding point object in world coordinates
	 */
	public GPoint S2W(GPoint p) {
		return new GPoint(p.getX() / Xs + Xmin, (ymax - p.getY()) / Ys + Ymin);
	}

	/**
	 * Erase all the objects on the canvas to draw a new ground plane
	 */
	public void newScreen() {
		// Erases screen
		GProgram.removeAll();

		// Create the Ground Plane
		addGroundPlane();
	}

	/**
	 * Create and draw ground plane
	 */
	public void addGroundPlane() {
		// Create the Ground Plane
		GRect gPlane = new GRect(0, HEIGHT, WIDTH + OFFSET, 3);
		gPlane.setColor(Color.BLACK);
		gPlane.setFilled(true);
		GProgram.add(gPlane);
	}
}
