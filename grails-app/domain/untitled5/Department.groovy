package untitled5

class Department {

    String departmentName


    static hasMany = [employees:Employee]

    static constraints = {
        departmentName nullable: false,blank: false
    }
}
