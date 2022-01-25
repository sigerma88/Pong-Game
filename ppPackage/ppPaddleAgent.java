package ppPackage;

import acm.graphics.GPoint;
import acm.program.GraphicsProgram;

import static ppPackage.ppSimParams.TICK;
import static ppPackage.ppSimParams.TSCALE;

import java.awt.Color;

/**
 * This class creates the agent paddle and updates its position according to the
 * ball position.
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

public class ppPaddleAgent extends ppPaddle {

	// Instance variable
	ppBall myBall;

	/**
	 * Create Grect representing agent paddle and add to the display.
	 * 
	 * @param X        - starting position of the paddle X (meters)
	 * @param Y        - starting position of the paddle Y (meters)
	 * @param myColor  - color of the paddle
	 * @param myTable  - a reference to the ppTable class
	 * @param GProgram - a reference to the ppSim class used to manage the display
	 */

	public ppPaddleAgent(double X, double Y, Color myColor, ppTable myTable, GraphicsProgram GProgram) {
		super(X, Y, myColor, myTable, GProgram);
	}

	/**
	 * Entry point to calculate position of the paddle
	 */
	public void run() {

		// Local variables
		int AgentLag = ppSimParams.agentLagScaleSlider.getValue();
		int ballSkip = 0;
		double lastX = X;
		double lastY = Y;

		// Update position of paddle every AgentLag iterations of while loop
		while (true) {
			// Update velocity and position
			Vx = (X - lastX) / TICK;
			Vy = (Y - lastY) / TICK;
			lastX = X;
			lastY = Y;

			// Paddle response time will be 1/AgentLag of the ball move
			if (ballSkip++ >= AgentLag) {
				// Set paddle agent position
				this.setP(new GPoint(this.getP().getX(), myBall.getP().getY()));
				// Reset ballSkip
				ballSkip = 0;
			}

			GProgram.pause(TICK * TSCALE);

		}
	}

	/**
	 * Sets the value of the myBall instance variable in ppPaddleAgent.
	 * 
	 * @param myBall - Instance of ppBall
	 */
	public void attachBall(ppBall myBall) {
		this.myBall = myBall;
	}

}
