package Lookups

import untitled5.EmpPosition
import untitled5.Employee

class Position {

    String positionName



    static constraints = {
        positionName inList: ["Network Engineer","Technical support","QA","Marketing","Developer"]
    }
}
