/* Created Sat Nov 28 15:22:52 EST 2015 */
package ca.team2706;

import org.strongback.Strongback;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.drive.TankDrive;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	// Driverstation port 1
	// Wait, 1? Are we sure its not zero?
	private static final int JOYSTICK_PORT = 1; 
	
	/* Motor ports */
	private static final int LF_MOTOR_PORT = 1;
    private static final int LR_MOTOR_PORT = 2;
    private static final int RF_MOTOR_PORT = 3;
    private static final int RR_MOTOR_PORT = 4;

    /* tank drive example */
    private TankDrive drive;
    
    /* hmmm, not sure what these are for yet. */
    private ContinuousRange driveSpeed;
    private ContinuousRange turnSpeed;
	
    @Override
    public void robotInit() {
    	
    	
    }

    @Override
    public void teleopInit() {
        // Start Strongback functions ...
        Strongback.start();
    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void disabledInit() {
        // Tell Strongback that the robot is disabled so it can flush and kill commands.
        Strongback.disable();
    }

}
