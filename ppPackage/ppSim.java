package ppPackage;

import static ppPackage.ppSimParams.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

/**
 * Assignment 4 of course ECSE 202
 * 
 * Using the code from Assignment 3 as a starting point, the left wall is
 * replaced by a computer-controlled paddle, and interactive buttons, labels and
 * sliders are added. This is a Java program to simulate Newton's equations of
 * motion for a ping-pong ball executing a parabolic trajectory from launch and
 * calculating the position and velocity of a simulated ping-pong ball as a
 * function of time. The program was re-implemented by adding the class
 * ppPaddleAgent. The user can interact with the program by moving the paddle
 * with the mouse and making the ball bounce on it and by clicking on the
 * buttons. This is a game of Pong where the user plays against the computer.
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

public class ppSim extends GraphicsProgram {

	// Instance variable
	ppTable myTable;
	ppPaddle RPaddle;
	ppPaddleAgent LPaddle;
	ppBall myBall;
	RandomGenerator rgen;

	/**
	 * Main entry point for the program.
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		new ppSim().start();
	}

	/**
	 * Initial starting point of the program
	 */

	public void init() {

		// Resize window
		this.resize(ppSimParams.WIDTH + OFFSET, ppSimParams.HEIGHT + OFFSET);

		// Create JButtons, JToggleButtons and JSliders for the interface
		JButton newServeButton = new JButton("New Serve"); // New Serve Button
		traceButton = new JToggleButton("Trace", false); // Trace button
		JButton restartMatchButton = new JButton("Restart Match"); // Clear scores button
		JButton quitButton = new JButton("Quit"); // Quit program button
		JLabel fasterTime = new JLabel("+t");
		timeScaleSlider = new JSlider(500, 10000, 2000); // Time scale slider
		JLabel slowerTime = new JLabel("-t");
		JLabel fasterReactionTime = new JLabel("-lag");
		agentLagScaleSlider = new JSlider(0, 10, 5); // Agent lag slider
		JLabel slowerReactionTime = new JLabel("+lag");
		scoreBoard = new JLabel("Agent: " + agentScore + " | Player: " + playerScore); // Score board label
		scoreBoard.setFont(new Font("Arial", Font.BOLD, 40));
		scoreBoard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

		// Add JButtons, JToggleButtons and JSliders to canvas
		add(newServeButton, SOUTH); // New Serve Button
		add(traceButton, SOUTH); // Trace button
		add(restartMatchButton, SOUTH); // Clear scores button
		add(quitButton, SOUTH); // Quit program button
		add(fasterTime, SOUTH);
		add(timeScaleSlider, SOUTH); // Time scale slider
		add(slowerTime, SOUTH);
		add(fasterReactionTime, SOUTH);
		add(agentLagScaleSlider, SOUTH); // Agent lag slider
		add(slowerReactionTime, SOUTH);
		add(scoreBoard, NORTH); // Score board label

		// Action listeners
		addMouseListeners();
		addActionListeners();

		// Random number generator
		rgen = RandomGenerator.getInstance();
		// rgen.setSeed(RSEED); // Seed used to debug

		// Begin the game
		myTable = new ppTable(this);
		newGame();

	}

	/**
	 * Generates random parameters to create an instance of ppBall.
	 * 
	 * @return Instance of ppBall
	 */
	ppBall newBall() {

		// Parameters for ppBall
		double iYinit = rgen.nextDouble(YinitMIN, YinitMAX);
		double iVel = rgen.nextDouble(VoMIN, VoMAX);
		double iTheta = rgen.nextDouble(ThetaMIN, ThetaMAX);
		double iLoss = rgen.nextDouble(EMIN, EMAX);
		Color iColor = Color.RED;

		// Create ball
		return new ppBall(Xinit, iYinit, iVel, iTheta, iLoss, iColor, myTable, this);

	}

	/**
	 * Start a new game of Pong
	 */
	public void newGame() {
		if (myBall != null)
			myBall.kill(); // stop current game in play
		myTable.newScreen();
		myBall = newBall();
		RPaddle = new ppPaddle(RPaddleXinit, RPaddleYinit, Color.GREEN, myTable, this);
		LPaddle = new ppPaddleAgent(LPaddleXinit, LPaddleYinit, Color.BLUE, myTable, this);
		LPaddle.attachBall(myBall);
		myBall.setRightPaddle(RPaddle);
		myBall.setLeftPaddle(LPaddle);
		pause(STARTDELAY);
		myBall.start();
		LPaddle.start();
		RPaddle.start();
	}

	/**
	 * Mouse Handler - a moved event moves the paddle up and down in Y
	 */
	public void mouseMoved(MouseEvent e) {

		if (myTable == null || RPaddle == null)
			return;

		// Convert mouse position to point in screen coordinates
		GPoint Pm = myTable.S2W(new GPoint(e.getX(), e.getY()));
		double PaddleX = RPaddle.getP().getX();
		double PaddleY = Pm.getY();

		// Update paddle position
		RPaddle.setP(new GPoint(PaddleX, PaddleY));

	}

	/**
	 * Button Handler - Tracks if buttons are pressed and what actions are performed
	 * when pressed
	 */
	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();

		// New Serve button
		if (command.equals("New Serve")) {
			newGame();
		}
		// Quit button
		else if (command.equals("Quit")) {
			System.exit(0);
		}
		// Restart Match button
		else if (command.equals("Restart Match")) {
			playerScore = 0;
			agentScore = 0;
			scoreBoard.setText("Agent: " + agentScore + " | Player: " + playerScore);
			newGame();
		}

	}

}
