// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.team670.libs.Auto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj2.command.Command;

public final class MustangAutoBuilder {


  public static List<AutoInstance> autos;
  public static AutoInstance currentEdit;

  public static Map<String, Command> namedCommands = new HashMap<String,Command>();
  
  public static AutoInstance createAutoRefrance(){
    autos.add(new AutoInstance());
    currentEdit = autos.get(autos.size()-1);
    currentEdit.index = autos.size()-1;
    return currentEdit;
  }

  public static AutoInstance getAuto(int index){
    return autos.get(index);
  }

  public static void AddFollowChoreo(String choreoName){
    try {
      PathPlannerPath path = PathPlannerPath.fromChoreoTrajectory(choreoName);
      currentEdit.commands.add(new ChoreoCommand(path));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void FromName(String name){
    Command command = namedCommands.get(name);
    currentEdit.commands.add(command);
  }

  public static void Define(Command command, String name){
    namedCommands.put(name, command);
  }
  
  public static void AddCommand(Command ...additon){
    for (Command c : additon){
      currentEdit.commands.add(c);
    }
  }

  public static void EditAuto(AutoInstance refrance){
    for (AutoInstance a : autos){
      //this is to check for the same object not equality
      if (a == refrance){
        currentEdit = a;
        return;
      }
    }
    currentEdit = refrance;
    autos.add(refrance);
    refrance.index = autos.size() - 1;
  }


}
