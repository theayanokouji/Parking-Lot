package parking

import java.util.*

class ParkingLot {
    // stores the spots in the parking lot
    private val spots = mutableListOf<String>()
    // stores the registration number for each car in the parking lot
    private val registrationNumbers = mutableListOf<String>()
    // stores the color for each car in the parking lot
    private val colors = mutableListOf<String>()
    private val scanner = Scanner(System.`in`)

    // function to create parking spots
    private fun createParkingSpots(line: String) {
        spots.clear() // clear the list everytime a new create command is entered
        registrationNumbers.clear()
        colors.clear()
        val (_, strNumber) = line.split(" ")
        val number = strNumber.toInt()
        for (i in 0 until number) {
            spots.add("free")
        }
        println("Created a parking lot with $number spots.")
    }

    // parks the car
    private fun parkCar(line: String) {
        val (_, registrationNumber, color) = line.split(" ")
        // add the registration number and color to corresponding lists
        registrationNumbers.add(registrationNumber)
        colors.add(color)
        // check if there is space in the parking lot
        when {
            "free" in spots -> {
                // park the car
                val index = spots.indexOf("free")
                spots[index] = "occupied"
                println("$color car parked in spot ${index + 1}.")
            }
            else -> println("Sorry, the parking lot is full.")
        }
    }

    // takes the car out of the parking lot
    private fun leaveParkingLot(line: String) {
        val (_, number) = line.split(" ")
        val index = number.toInt() - 1
        // check if index exists
        if (index in 0..spots.lastIndex) {
            // check if car exists at that parking spot
            if (spots[index] == "occupied") {
                // now take the car out of the parking lot
                spots[index] = "free"
                // now empty out the color and reg number
                colors[index] = ""
                registrationNumbers[index] = ""
                println("Spot ${index + 1} is ${spots[index]}.")
            }
        }
    }

    // gets the status of the parking lot
    private fun getParkingStatus() {
        if (spots.isEmpty()) {
            println("Sorry, a parking lot has not been created.")
        } else if ("occupied" !in spots) {
            println("Parking lot is empty.")
        } else {
            // Now print the status of all occupied spots
            for (i in 0 until spots.size) {
                if (spots[i] == "occupied") {
                    println("${i + 1} ${registrationNumbers[i]} ${colors[i]}")
                }
            }
        }
    }

    /**
     * @param line - the line from the user
     */
    private fun printRegistrationNumbers(line: String) {
        val (_, color) = line.split(" ") // extract color
        // go through all cars with that color in the parking lot
        var response = "" // to concatenate all the registration numbers
        var colorExists = false
        for (i in 0..colors.lastIndex) {
            if (color.lowercase() == colors[i].lowercase()) {
                colorExists = true
                break // exit loop
            }
        }
        if (!colorExists) {
            println("No cars with color $color were found.")
            return // exit function
        }
        for (i in 0..spots.lastIndex) {
            if (colors[i].lowercase() == color.lowercase()) {
                // found the car with that color, now print it
                response += "${registrationNumbers[i]}, "
            }
        }
        // now fix the string by removing the last occurence of ", "
        response = response.removeSuffix(", ")
        println(response.trim())
    }

    /**
     * @param line - the line from the user
     */
    private fun printParkingSpaceNumbers(line: String) {
        val (_, color) = line.split(" ") // extract color
        // go through all cars with that color in the parking lot
        var response = "" // to concatenate all the registration numbers
        var colorExists = false
        for (i in 0..colors.lastIndex) {
            if (colors[i].lowercase() == color.lowercase()) {
                colorExists = true
                break // exit loop
            }
        }
        if (!colorExists) {
            println("No cars with color $color were found.")
            return // exit function
        }
        for (i in 0..spots.lastIndex) {
            if (colors[i].lowercase() == color.lowercase()) {
                // found the car with that color, now print it
                response += "${i + 1}, "
            }
        }
        // now fix the string by removing the last occurrence of ", "
        response = response.removeSuffix(", ")
        println(response.trim())
    }

    /**
     * @param registrationNumber - the registration number of the car
     * @return number of the spots where a car is located based on its registration number
     */
    private fun getNumberOfSpots(registrationNumber: String): Int {
        // now go through all registration numbers and find the ones that match with the input
        for (i in 0..registrationNumbers.lastIndex) {
            // split up the two registration numbers
            if (registrationNumbers[i].lowercase() == registrationNumber.lowercase()) return i + 1
        }
        return -1
    }

    // handle user requests
    fun handleRequest() {
        do {
            // check if the line contains the command "park" or "leave"
            val line = scanner.nextLine()
            when {
                "create" in line.lowercase() -> createParkingSpots(line)
                "park" in line.lowercase() && spots.isNotEmpty() -> parkCar(line)
                "leave" in line.lowercase() && spots.isNotEmpty() -> leaveParkingLot(line)
                "status" in line.lowercase() -> getParkingStatus()
                "reg_by_color" in line.lowercase() && spots.isNotEmpty() -> printRegistrationNumbers(line)
                "spot_by_color" in line.lowercase() && spots.isNotEmpty() -> printParkingSpaceNumbers(line)
                "spot_by_reg" in line.lowercase() && spots.isNotEmpty() -> {
                    val (_, registrationNumber) = line.split(" ")
                    val answer = getNumberOfSpots(registrationNumber)
                    // check if there were no cars found
                    if (answer == -1) {
                        println("No cars with registration number $registrationNumber were found.")
                    } else {
                        println(answer)
                    }
                }
                "exit" in line.lowercase() -> break
                else -> println("Sorry, a parking lot has not been created.")
            }
        } while ("exit" !in line)
        // free memory resources
        scanner.close()
    }


}