package untitled5

import Lookups.Level
import Lookups.Position
import Lookups.Skill

class Employee extends Person{

    String workID
    Date joinDate
   // Address address

    Department department

//    Education education
  //  EmployeeSkill employeeSkill
  //  EmpPosition employeePosition

    static hasMany = [educations: Education ,empSkill:EmployeeSkill ,empPosition:EmpPosition]
    //static hasOne = [emp_pos:Emp_Position]  // NO NO NO NO
    static belongsTo = [department:Department]


    static constraints = {
        workID blank: false ,unique: true,nullable: false
    }




}
