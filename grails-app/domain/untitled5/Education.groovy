package untitled5

import Lookups.Degree
import Lookups.HighSchool
import Lookups.Skill
import Lookups.Specialization
import Lookups.University

class Education {

/*    University university
    Degree degree
    Specialization specialization
    HighSchool highschool*/

    Date joinDate
    Date graduationDate
    //main education /latest degree

    static belongsTo = [
            employees:Employee ,
            degree:Degree,
            specialization:Specialization,
            university:University,
            highschool:HighSchool
    ]

    static constraints = {
        graduationDate(validator: {val, obj ->
            val?.after(obj.joinDate)

        })
    }
}
