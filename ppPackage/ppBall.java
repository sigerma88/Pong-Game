package ppPackage;

import static ppPackage.ppSimParams.*;
import java.awt.Color;
import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;

/**
 * This class creates a ball and runs the simulation of Newton's equations of
 * motion on it while checking for collisions or for out of bounds.
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

public class ppBall extends Thread {

	// Instance variables
	private double Xinit; // Initial position of ball - X
	private double Yinit; // Initial position of ball - Y
	private double Vo; // Initial velocity (Magnitude)
	private double theta; // Initial direction
	private double loss; // Energy loss on collision
	double X, Xo, Y, Yo; // Position of ball variables
	double Vx, Vy; // Velocity of ball variables
	private Color color; // Color of ball
	private ppTable myTable; // Instance of ppTable class
	private ppPaddle RPaddle; // Instance of ppPaddle class
	private ppPaddle LPaddle; // Instance of ppPaddleAgent class
	private GraphicsProgram GProgram; // Instance of ppSim class
	GOval myBall; // Graphics object representing ball
	boolean running; // Boolean qualifying if simulation runs

	/**
	 * The constructor for the ppBall class copies parameters to instance variables,
	 * creates an instance of a GOval to represent the ping-pong ball, and adds it
	 * to the display.
	 * 
	 * @param Xinit    - starting position of the ball X (meters)
	 * @param Yinit    - starting position of the ball Y (meters)
	 * @param Vo       - initial velocity (meters/second)
	 * @param theta    - initial angle to the horizontal (degrees)
	 * @param loss     - loss on collision ([0,1])
	 * @param color    - ball color (Color)
	 * @param myTable  - a reference to the ppTable class
	 * @param GProgram - a reference to the ppSim class used to manage the display
	 */

	public ppBall(double Xinit, double Yinit, double Vo, double theta, double loss, Color color, ppTable myTable,
			GraphicsProgram GProgram) {
		// Copy constructor parameters to instance variables
		this.Xinit = Xinit;
		this.Yinit = Yinit;
		this.Vo = Vo;
		this.theta = theta;
		this.loss = loss;
		this.color = color;
		this.myTable = myTable;
		this.GProgram = GProgram;
	}

	/**
	 * Entry point for the simulation.
	 */

	public void run() {

		// Create Ball
		GPoint p = myTable.W2S(new GPoint(Xinit, Yinit + bSize));
		double ScrX = p.getX(); // Convert simulation to screen coordinates
		double ScrY = p.getY();
		GOval myBall = new GOval(ScrX, ScrY, 2 * bSize * Xs, 2 * bSize * Ys);
		myBall.setColor(color);
		myBall.setFilled(true);
		GProgram.add(myBall);

		// Initialize simulation parameters
		Xo = Xinit; // Set initial X position
		Yo = Yinit; // Set initial Y position
		double time = 0; // Time starts at 0 and counts up
		double Vt = bMass * g / (4 * Pi * bSize * bSize * k); // Terminal velocity
		double Vox = Vo * Math.cos(theta * Pi / 180); // X component of velocity
		double Voy = Vo * Math.sin(theta * Pi / 180); // Y component of velocity
		double KEx; // X component of kinetic energy
		double KEy; // Y component of kinetic energy
		double PE; // Potential energy

		// Main simulation loop
		running = true;

		while (running) {

			// Update relative position
			X = Vox * Vt / g * (1 - Math.exp(-g * time / Vt));
			Y = Vt / g * (Voy + Vt) * (1 - Math.exp(-g * time / Vt)) - Vt * time;

			// Update velocity
			Vx = Vox * Math.exp(-g * time / Vt);
			Vy = (Voy + Vt) * Math.exp(-g * time / Vt) - Vt;

			// Check Collision with Ground
			if (Y + Yo - bSize <= 0 && Vy < 0) {

				// Reinitialize Energy
				KEx = 0.5 * bMass * Vx * Vx * (1 - loss);
				KEy = 0.5 * bMass * Vy * Vy * (1 - loss);
				PE = 0;

				// Reinitialize Speed Parameters
				Vox = Math.sqrt(2 * KEx / bMass);
				Voy = Math.sqrt(2 * KEy / bMass);
				if (Vx < 0)
					Vox = -Vox;

				// Reinitialize Other Parameters
				time = 0;
				Xo += X;
				Yo = bSize;
				X = 0;
				Y = 0;

				// Energy Threshold
				if ((KEx + KEy + PE) < ETHR) {
					// Increment scores if agent or user makes program terminate because energy
					// becomes too low
					if (Vx > 0)
						incrementPlayerScore();
					if (Vx < 0)
						incrementAgentScore();

					// Terminate simulation
					kill();
				}

			}

			// Check at position (component X) of user paddle
			if ((Xo + X) >= RPaddle.getP().getX() - ppPaddleW / 2 - bSize && Vx > 0) {

				// Check collision with paddle
				if (RPaddle.contact(Y + Yo)) {

					// Reinitialize Energy
					KEx = 0.5 * bMass * Vx * Vx * (1 - loss);
					KEy = 0.5 * bMass * Vy * Vy * (1 - loss);
					PE = bMass * g * (Yo + Y);

					// Reinitialize Speed Parameters
					Vox = -Math.sqrt(2 * KEx / bMass);
					Voy = Math.sqrt(2 * KEy / bMass);
					if (Vox > -VoxMAX) // Check for max speed in X
						Vox = Vox * RPaddleXgain; // Scale X component of velocity after contact with paddle
					else
						Vox = -VoxMAX; // Set speed to max speed in X
					Voy = Voy * RPaddleYgain * RPaddle.getV().getY(); // Scale Y component of velocity to Y component
																		// of velocity of paddle after contact with
																		// paddle

					// Reinitialize Other Parameters
					time = 0;
					Xo = RPaddle.getP().getX() - ppPaddleW / 2 - bSize;
					Yo += Y;
					X = 0;
					Y = 0;

				}

				// Check if ball goes past paddle
				else {
					incrementAgentScore(); // Increment score for Agent
					kill(); // Terminate simulation
				}
			}

			// Check at position (component X) of agent paddle
			if ((X + Xo) <= LPaddle.getP().getX() + ppPaddleW / 2 + bSize && Vx < 0) {

				// Check collision with paddle
				if (LPaddle.contact(Y + Yo)) {

					// Reinitialize Energy
					KEx = 0.5 * bMass * Vx * Vx * (1 - loss);
					KEy = 0.5 * bMass * Vy * Vy * (1 - loss);
					PE = bMass * g * (Yo + Y);

					// Reinitialize Speed Parameters
					Vox = Math.sqrt(2 * KEx / bMass);
					Voy = Math.sqrt(2 * KEy / bMass);
					if (Vox < VoxMAX) // Check for max speed in X
						Vox = Vox * LPaddleXgain; // Scale X component of velocity after contact with paddle
					else
						Vox = VoxMAX; // Set speed to max speed in X
					Voy = Voy * LPaddleYgain * LPaddle.getV().getY(); // Scale Y component of velocity to Y component
																		// of velocity of paddle after contact with
																		// paddle

					// Reinitialize Other Parameters
					time = 0;
					Xo = LPaddle.getP().getX() + ppPaddleW / 2 + bSize;
					Yo += Y;
					X = 0;
					Y = 0;

				}

				// Check if ball goes past paddle
				else {
					incrementPlayerScore(); // Implement score for player
					kill(); // Terminate simulation
				}

			}

			// Check for out of bounds (ceiling). Player or agent loses points for making
			// the ball go out of bounds
			if (Yo + Y > Ymax) {
				// Increment score for player
				if (Vx > 0)
					incrementPlayerScore();
				// Increment score for agent
				if (Vx < 0)
					incrementAgentScore();
				kill(); // terminate program
			}

			// Update the position of the ball
			p = myTable.W2S(new GPoint(Xo + X - bSize, Yo + Y + bSize));
			ScrX = p.getX();
			ScrY = p.getY();
			myBall.setLocation(ScrX, ScrY);

			// Plot tick mark
			if (traceButton.isSelected())
				trace(Xo + X, Yo + Y);

			// Passage of time
			time += TICK;

			// Pause display
			GProgram.pause(TICK * timeScaleSlider.getValue());

		}

	}

	/**
	 * A simple method to plot a dot at the current location in screen coordinates.
	 * 
	 * @param X - location of point (world coordinates)
	 * @param Y - location of point (world coordinates)
	 */
	public void trace(double X, double Y) {
		GPoint p = myTable.W2S(new GPoint(X, Y));
		double ScrX = p.getX();
		double ScrY = p.getY();
		GOval pt = new GOval(ScrX, ScrY, PD, PD);
		pt.setColor(Color.BLACK);
		pt.setFilled(true);
		GProgram.add(pt);
	}

	/**
	 * Set a link between myBall and RPaddle.
	 * 
	 * @param myPaddle - Instance of ppPaddle class
	 */
	public void setRightPaddle(ppPaddle myPaddle) {
		this.RPaddle = myPaddle;
	}

	/**
	 * Set a link between myBall and LPaddle.
	 * 
	 * @param myPaddle - Instance of ppPaddle class
	 */
	public void setLeftPaddle(ppPaddle myPaddle) {
		this.LPaddle = myPaddle;
	}

	/**
	 * Getter for absolute position of ball
	 * 
	 * @return GPoint - Position of the ball
	 */
	public GPoint getP() {
		return new GPoint(Xo + X, Yo + Y);
	}

	/**
	 * Getter for velocity of ball
	 * 
	 * @return GPoint - Velocity of the ball
	 */
	public GPoint getV() {
		return new GPoint(Vx, Vy);
	}

	/**
	 * Method to increment the player score on the score board
	 */
	public void incrementPlayerScore() {
		playerScore++;
		scoreBoard.setText("Agent: " + agentScore + " | Player: " + playerScore);
	}

	/**
	 * Method to increment the agent score on the score board
	 */
	public void incrementAgentScore() {
		agentScore++;
		scoreBoard.setText("Agent: " + agentScore + " | Player: " + playerScore);
	}

	/**
	 * Method to kill the simulation
	 */
	void kill() {
		running = false;
	}
}
