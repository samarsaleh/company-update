package Lookups

import untitled5.Education

class University {

    String uniNames

    static constraints = {
        uniNames inList: ["BZU","Alquds","Alquds open","AAU"]
    }
}
