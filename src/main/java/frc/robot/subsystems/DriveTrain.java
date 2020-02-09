/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.*;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrain extends SubsystemBase {
  /**
   * Creates a new DriveTrain.
   */
  private WPI_TalonSRX left;
  private WPI_TalonSRX right;
  public DriveTrain() {
    left = new WPI_TalonSRX(2);
    left.setInverted(true);
    right = new WPI_TalonSRX(3);
  }
  public void arcadeDrive(double l, double r) {
    left.set(ControlMode.PercentOutput, l);
    right.set(ControlMode.PercentOutput, r);
  }
  @Override
  public void periodic() {
    arcadeDrive(Robot.m_robotContainer.getJoy().getY()-Robot.m_robotContainer.getJoy().getZ(), Robot.m_robotContainer.getJoy().getY()+Robot.m_robotContainer.getJoy().getZ());
  }
}
