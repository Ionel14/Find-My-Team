package com.example.findmyteam.models

enum class Categories(val value: Int) {
    Others(0),
    Football(1),
    Basketball(2),
    Tennis(3);

    override fun toString(): String {
        return when (this) {
            Football -> "Football"
            Basketball -> "Basketball"
            Tennis -> "Tennis"
            Others -> "Others"
        }
    }

    companion object {
        fun fromValue(value: Int): Categories? {
            return values().find { it.value == value }
        }
    }
}

