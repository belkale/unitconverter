package com.example.unitconverter

enum class Unit {
    Centimeters, Meters, Feet, Millimeters;

    fun conversionFactor() = when (this) {
        Centimeters -> 0.01
        Meters -> 1.0
        Feet -> 0.3048
        Millimeters -> 0.001
    }
}