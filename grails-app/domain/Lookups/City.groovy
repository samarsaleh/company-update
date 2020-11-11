package Lookups

class City {

    String cityName
    static constraints = {

        cityName inList: ["Ramallah","Jericho","Nablus","Hebron"]
    }
}
