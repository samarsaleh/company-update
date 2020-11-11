package Lookups

class Level {
    String levelName
    static constraints = {
        levelName inList: ["worker","Team leader","Manager"]
    }
}
