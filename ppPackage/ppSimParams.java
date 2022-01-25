package ppPackage;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;

/**
 * This class contains the constant definitions used across the program.
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

public class ppSimParams {

	// 1. Parameters defined in screen coordinates (pixels, acm coordinates)
	public static final int WIDTH = 1280; // n.b. screen coordinates
	public static final int HEIGHT = 600;
	public static final int OFFSET = 200;

	// 2. Ping-pong table parameters
	public static final double ppTableXlen = 2.74; // Length
	public static final double ppTableHgt = 1.52; // Ceiling
	public static final double XwallL = 0.05; // Position of left wall
	public static final double XwallR = 2.69; // Position of right wall

	// 3. Parameters defined in simulation coordinates
	public static final double g = 9.8; // MKS
	public static final double k = 0.1316; // Vt constant
	public static final double Pi = 3.1416; // Value of pi
	public static final double bSize = 0.02; // pp ball radius
	public static final double bMass = 0.0027; // pp ball mass
	public static final double TICK = 0.01; // Clock tick duration (sec)
	public static final double ETHR = 0.001; // Minimum ball energy
	public static final double Xmin = 0.0; // Minimum value of X (pp table)
	public static final double Xmax = ppTableXlen; // Maximum value of X
	public static final double Ymin = 0.0; // Minimum value of Y
	public static final double Ymax = ppTableHgt; // Maximum value of Y
	public static final int xmin = 0; // Minimum value of x
	public static final int xmax = WIDTH; // Maximum value of x
	public static final int ymin = 0; // Minimum value of y
	public static final int ymax = HEIGHT; // Maximum value of y
	public static final double Xs = (xmax - xmin) / (Xmax - Xmin); // Scale factor X
	public static final double Ys = (ymax - ymin) / (Ymax - Ymin); // Scale factor Y
	public static final double Xinit = XwallL; // Initial ball location (X)
	public static final double Yinit = Ymax / 2; // Initial ball location (Y)
	public static final double VoxMAX = 9.0; // Maximum ball velocity in component X
	public static final double PD = 1; // Trace point diameter
	public static final double TSCALE = 2000; // Scaling parameter for pause()

	// 4. Paddle Parameters
	static final double ppPaddleH = 8 * 2.54 / 100; // Paddle height
	static final double ppPaddleW = 0.5 * 2.54 / 100; // Paddle width
	static final double RPaddleXinit = XwallR - ppPaddleW / 2; // Initial X position of player paddle
	static final double RPaddleYinit = Yinit; // Initial Y position of player paddle
	static final double RPaddleXgain = 2.0; // Vx gain on Rpaddle hit
	static final double RPaddleYgain = 0.5; // Vy gain on Rpaddle hit
	static final double LPaddleXinit = XwallL + ppPaddleW / 2; // Initial X position of Agent paddle
	static final double LPaddleYinit = Yinit; // Initial Y position of Agent paddle
	static final double LPaddleXgain = 2.0; // Vx gain on Lpaddle hit
	static final double LPaddleYgain = 1.0; // Vy gain on Lpaddle hit

	// 5. Parameters used by the ppSim class
	static final double YinitMAX = 0.75 * Ymax; // Max initial height at 75% of range
	static final double YinitMIN = 0.25 * Ymax; // Min initial height at 25% of range
	static final double EMIN = 0.2; // Minimum loss coefficient
	static final double EMAX = 0.2; // Maximum loss coefficient
	static final double VoMIN = 5.0; // Minimum velocity
	static final double VoMAX = 5.0; // Maximum velocity
	static final double ThetaMIN = 0.0; // Minimum launch angle
	static final double ThetaMAX = 20.0; // Maximum launch angle
	static final long RSEED = 8976232; // Random number generator seed value
	public static JToggleButton traceButton; // Trace button
	public static JLabel scoreBoard; // Score board
	public static JSlider timeScaleSlider; // Slider for time scale
	public static JSlider agentLagScaleSlider; // Slider for agent lag
	public static int playerScore; // Score of player
	public static int agentScore; // Score of agent

	// 6. Miscellaneous
	public static final boolean DEBUG = false; // Debug message. and single step if true
	public static final boolean MESG = true; // Enable status messages on console
	public static final int STARTDELAY = 1000; // Delay between setup and start

}
