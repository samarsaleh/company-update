package untitled5

import Lookups.Skill

class EmployeeSkill {


    String main

    static belongsTo = [employees:Employee,skills: Skill]
    static constraints = {
    }
}
