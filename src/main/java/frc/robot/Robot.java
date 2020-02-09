/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.cameraserver.CameraServer;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  public static RobotContainer m_robotContainer;

  private Command m_autonomousCommand;

  // Declare the server (this will allow us to display the footage)
  private VideoSink server;

  // Declare the two cameras
  private UsbCamera camera1;
  private UsbCamera camera2;

  // Displays camera1 when true, otherwise it displays camera2 when false
  private boolean allowCam;

  CvSource outputStream;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */

  // Initialize all global variables to ensure we don't encounter any null pointer exceptions
  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer(); // Initialize the robot container
      allowCam = false; // Displays camera2 by default

      // Initialize both cameras and set their resolutions and FPS
      camera1 = CameraServer.getInstance().startAutomaticCapture(0);
      camera1.setResolution(240, 160);
      camera1.setFPS(60);
      camera2 = CameraServer.getInstance().startAutomaticCapture(1);
      camera2.setResolution(240, 160);
      camera2.setFPS(60);

      // Initialize the server
      server = CameraServer.getInstance().getServer();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */

  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */

  @Override
  public void teleopPeriodic() {
    // If button three is pressed, toggle the camera shown by inverting allowCam (button number is displayed on the joystick)
      if (m_robotContainer.getJoy().getRawButtonPressed(3)) {
        allowCam = !allowCam;
      }
    
      // Displays camera1 if allowCam is true and camera2 if allowCam is false
      if (allowCam) {
        server.setSource(camera1);
      } else {
        server.setSource(camera2);
      }
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
