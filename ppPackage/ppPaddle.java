package ppPackage;

import static ppPackage.ppSimParams.*;
import java.awt.Color;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

/**
 * 
 * This class creates the paddle and contains the methods used by the rest of
 * the program to interact with the paddle.
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

public class ppPaddle extends Thread {

	// Instance variables
	double X; // Initial position of paddle - X
	double Y; // Initial position of paddle - Y
	double Vx; // Velocity - X
	double Vy; // Velocity - Y
	Color myColor; // Color of paddle
	GRect myPaddle; // Graphics object representing paddle
	GraphicsProgram GProgram; // Instance of ppSim class
	ppTable myTable; // Instance of ppTable class

	/**
	 * Create Grect representing user paddle and add to the display.
	 * 
	 * @param X        - starting position of the paddle X (meters)
	 * @param Y        - starting position of the paddle Y (meters)
	 * @param myColor  - color of the paddle
	 * @param myTable  - a reference to the ppTable class
	 * @param GProgram - a reference to the ppSim class used to manage the display
	 */

	public ppPaddle(double X, double Y, Color myColor, ppTable myTable, GraphicsProgram GProgram) {

		// Copy constructor parameters to instance variables
		this.X = X;
		this.Y = Y;
		this.myColor = myColor;
		this.myTable = myTable;
		this.GProgram = GProgram;

		// World coordinates to screen coordinates
		double upperLeftX = X - ppPaddleW / 2;
		double upperLeftY = Y + ppPaddleH / 2;
		GPoint p = myTable.W2S(new GPoint(upperLeftX, upperLeftY));
		double Scrx = p.getX();
		double Scry = p.getY();

		// Create paddle
		myPaddle = new GRect(Scrx, Scry, ppPaddleW * Xs, ppPaddleH * Ys);
		myPaddle.setFilled(true);
		myPaddle.setColor(myColor);
		GProgram.add(myPaddle);

	}

	/**
	 * Entry point to calculate velocity and position of the paddle
	 */
	public void run() {

		// Initial position
		double lastX = X;
		double lastY = Y;

		// Update velocity and position
		while (true) {
			Vx = (X - lastX) / TICK;
			Vy = (Y - lastY) / TICK;
			lastX = X;
			lastY = Y;
			GProgram.pause(TICK * TSCALE);
		}
	}

	/**
	 * Sets and moves paddle to (X,Y)
	 * 
	 * @param P - world coordinates of paddle
	 */
	public void setP(GPoint P) {

		// Update instance variables
		X = P.getX();
		Y = P.getY();

		// World coordinates to screen coordinates
		double upperLeftX = X - ppPaddleW / 2;
		double upperLeftY = Y + ppPaddleH / 2;
		GPoint p = myTable.W2S(new GPoint(upperLeftX, upperLeftY));
		double Scrx = p.getX();
		double Scry = p.getY();

		// Moving the GRect instance
		myPaddle.setLocation(Scrx, Scry);
	}

	/**
	 * Returns paddle location (X,Y)
	 * 
	 * @return GPoint - X and Y world coordinates of the paddle
	 */
	public GPoint getP() {
		return new GPoint(X, Y);
	}

	/**
	 * Returns paddle velocity (Vx,Vy)
	 * 
	 * @return GPoint - Paddle velocity in X and in Y
	 */
	public GPoint getV() {
		return new GPoint(Vx, Vy);
	}

	/**
	 * Used for the ball to return true if a surface at Sy is deemed to be in
	 * contact with the paddle and return false if not.
	 * 
	 * @param Sy - Position of surface (Y)
	 * @return boolean - If ball is in contact with paddle
	 */
	public boolean contact(double Sy) {
		return ((Sy >= getP().getY() - ppPaddleH / 2) && (Sy <= getP().getY() + ppPaddleH / 2));
	}

}
