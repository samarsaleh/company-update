package Lookups

import untitled5.Education

class Degree {
    String degrees

    static constraints = {
        degrees inList: ["Bachelor","Master","PHD"]
    }
}
