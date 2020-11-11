package untitled5

import Lookups.Level
import Lookups.Position
import javafx.geometry.Pos

class EmpPosition {

   // Employee employee //ok but why
  //  Position position
   // Level level

    String type
    //joineddate

    static belongsTo = [employee:Employee,position:Position,level:Level]

    static constraints = {
        type inList: ["Part Time","Full Time"]
    }
}
