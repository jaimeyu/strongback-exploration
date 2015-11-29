/* Created Sat Nov 28 15:22:52 EST 2015 */
package ca.team2706;


import org.strongback.Strongback;
import org.strongback.components.Motor;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.FlightStick;
import org.strongback.components.ui.Gamepad;
import org.strongback.drive.TankDrive;
import org.strongback.hardware.Hardware;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    

    private ContinuousRange driveSpeed;
    private ContinuousRange turnSpeed;
    
    @Override
    public void robotInit() {
    	/* Disable event recorder */
    	Strongback.configure().recordNoEvents().initialize();
    	
    	Motor left = Motor.compose(Hardware.Motors.talon(LF_MOTOR_PORT),
    			Hardware.Motors.talon(LR_MOTOR_PORT));
    	
    	/* Motors on the right side need to drive reverse direction
    	 * to match the left side
    	 */
    	Motor right = Motor.compose(Hardware.Motors.talon(RF_MOTOR_PORT),
    			Hardware.Motors.talon(RR_MOTOR_PORT)).invert();
    	
    	drive = new TankDrive(left,right);
    	
    	/* Let's nmot use the flight stick semantics 
    	 * Plus, I only have xbox controllers
    	 * */
    	//FlightStick joystick0 = Hardware.HumanInterfaceDevices.driverStationJoystick();
    	Gamepad gamepad0 = Hardware.HumanInterfaceDevices.logitechF310(JOYSTICK_PORT);
    	/* I see in the stronback example, they use the flight
    	 * stick throttle to handle sensitivity.
    	 * Since I don't have a flight stick, let's not use it.
    	 * But we can use some simple values
    	 */
    	
    	ContinuousRange sensitivity = gamepad0.getLeftTrigger().map(t -> (t + 1.0) / 2.0);
        driveSpeed = gamepad0.getAxis(0).scale(sensitivity::read); // scaled
        turnSpeed = gamepad0.getAxis(1).scale(sensitivity::read).invert(); // scaled and inverted
	
    	
    	
    	/* Hey, strongback has some a data recorder feature, lets use it */
    	Strongback.dataRecorder()
    	.register("Left Motors", left)
    	.register("Right Motors", right)
    	.register("Gamepad Axis 0", gamepad0.getAxis(0).scaleAsInt(1000));
    	
    }

    @Override
    public void teleopInit() {
    	// Kill anything running if it is ...
        Strongback.disable();
        // Start Strongback functions if not already running...
        Strongback.start();
    }

    @Override
    public void teleopPeriodic() {
        drive.arcade(driveSpeed.read(), turnSpeed.read());
        SmartDashboard.putNumber("Gamepad axis0", driveSpeed.read());
        SmartDashboard.putNumber("Gamepad axis1", turnSpeed.read());
        
        
    }

    @Override
    public void disabledInit() {
        // Tell Strongback that the robot is disabled so it can flush and kill commands.
        Strongback.disable();
    }
    
    @Override
    public void autonomousInit() {
    	/* Stop old strongback instances */
    	Strongback.disable();
        // Start Strongback functions ...
        Strongback.start();
        Strongback.submit(new AutonomouseTimedDriveCommand(drive, 0.5, 0.5, false, 5.0));
    }
    
    @Override
    public void autonomousPeriodic() {

        SmartDashboard.putNumber("Gamepad axis0", driveSpeed.read());
        SmartDashboard.putNumber("Gamepad axis1", turnSpeed.read());
    }

}
