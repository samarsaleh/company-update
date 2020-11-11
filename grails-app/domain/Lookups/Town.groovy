package Lookups

class Town {

    String townName

    static constraints = {
        townName blank: false, nullable: false

        //if city is ramallah ,show ramallah's towns and nighbors

    }
}
