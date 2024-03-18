package com.example.simpletextapp

fun sortDigits(number: String): String {

    // initial approach:
    //val digits = number.toString().toCharArray().sortedBy { it.toInt() }
    // however: "'toInt(): Int' is deprecated. Conversion of Char to Number is deprecated. Use Char.code property instead."

    // functional programming rocks! very simple solution :D
    // if we want to turn things around: .sortedByDescending{ it.code }

    // turn integer argument into a char array:
    val digits = number.toCharArray()

    //val sortedDigits2 = digits.sortedBy { it.code }
    // sort even digits in ascending order:
    val evenDigits = digits.filter { it.isDigit() && it.digitToInt() % 2 == 0 }
    val sortedEvenDigits = evenDigits.sortedBy { it.digitToInt() }

    // sort odd digits in ascending order:
    val oddDigits = digits.filter { it.isDigit() && it.digitToInt() % 2 != 0 }
    val sortedOddDigits = oddDigits.sortedBy { it.digitToInt() }

    // concatenate the char arrays:
    val sortedDigits = sortedEvenDigits + sortedOddDigits

    // a bit inefficient to check for invalid input here but it works
    return if (sortedDigits.size != number.length) {
        "Not a valid number"
    } else {
        //sortedDigits.joinToString("")
        sortedDigits.joinToString("")
    }

}