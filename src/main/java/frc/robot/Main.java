/*    X        X                                                               */
/*    X        X                              X     XX                             */
/*    X        X       X                     YYY   X X                             */
/*    X        X                            X  X XX  X        XXXX                 */
/*    XXXXXXXXXX       X                   XX   XX   X   X XX                      */
/*    X        X       X                  XX         X   YY                       */
/*    X        X       X                  XX         X   XX      X                 */
/*                                                                                 */
/*                                                                                 */
/*                                                                                 */
/*  XXXXXX       XX    X    X                                                     */
/* XX           XX      X    X X                                                   */
/* X           XX       X    X XX               X   X     XXXX  XXXXXXXXX XXXXXXXXX */
/* XX          X        X    X XX             XX   XX    X  XX    X           XXXX */
/*   XXXXXXX   X        XXXXXX  XX      XX   XX  XX XX   XXXXX    X         XX     */
/*         XX  X        X    X    X    YYY  X   XXXXXXX  YYY      X       YYY      */
/*        XX   X        X    X     XX XX X XX  XX     XX X YYY    X      XX        */
/*      YYY     X       X    X      YYY  XX    X       X X   X    X     XXXXXXXXXX */
/*  XXXX         XXXX                                                             */
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;

/**
 * Do NOT add any static variables to this class, or any initialization at all. Unless you know what
 * you are doing, do not modify this file except to change the parameter class to the startRobot
 * call.
 */
public final class Main {
  private Main() {}

  /**
   * Main initialization function. Do not perform any initialization here.
   *
   * <p>If you change your main robot class, change the parameter type.
   */
  public static void main(String... args) {
    RobotBase.startRobot(Robot::new);
  }
}