package Lookups

import untitled5.EmployeeSkill

class Skill {
    String skillsList
    String note
    String validation

   // EmployeeSkill employeeSkills


    static constraints = {
        skillsList inList: ["MS","reading"]
        validation inList: ["valid","InValid"]
    }
}
