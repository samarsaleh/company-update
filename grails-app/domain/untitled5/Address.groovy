package untitled5

import Lookups.City
import Lookups.Town

class Address {

    City city
    Town town
    String street
    String type

    static belongsTo = [person:Person]

    static constraints = {

        type inList: ["Home","Business"] // if it's home --> enter home address , else enter another address new table
        street nullable: false,blank: false
    }
}
