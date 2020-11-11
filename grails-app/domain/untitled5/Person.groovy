package untitled5

import groovy.time.TimeCategory

class Person {

    String firstName
    String lastName
    String gender
    String email
    Date dateOfBirth
    String idNumber

    static hasMany = [addresses: Address]

    /*   static transients = ['age']*/


    /*  Integer getAge() {
        return (new Date() - this.dateOfBirth)
    }*/


    static constraints = {
        firstName nullable: false, blank: false
        lastName nullable: false, blank: false
        gender inList: ["Female", "Male"]
        email email: true
        idNumber nullable: false, blank: false

        dateOfBirth validator: {
            use(TimeCategory) {
                it?.after(18.years.ago)
            }



        }
        /*  int age = obj.age
        if (age < 18) {
            errors.rejectValue("date_of_birth", "dare-----")
            return false
        }
        return true*/
    }

    static mapping = {
        tablePerHierarchy false
    }

}

