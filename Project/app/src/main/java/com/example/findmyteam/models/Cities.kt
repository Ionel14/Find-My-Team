package com.example.findmyteam.models

enum class Cities(val cityNumber: Int) {
    Bucharest(1),
    ClujNapoca(2),
    Timisoara(3),
    Iasi(4),
    Constanta(5),
    Brasov(6),
    Craiova(7),
    Galati(8),
    Oradea(9),
    Sibiu(10),
    Ploiesti(11),
    Arad(12),
    Pitesti(13),
    Bacau(14),
    BaiaMare(15);

    override fun toString(): String {
        return when (this) {
            Bucharest -> "Bucharest"
            ClujNapoca -> "Cluj-Napoca"
            Timisoara -> "Timisoara"
            Iasi -> "Iasi"
            Constanta -> "Constanta"
            Brasov -> "Brasov"
            Craiova -> "Craiova"
            Galati -> "Galati"
            Oradea -> "Oradea"
            Sibiu -> "Sibiu"
            Ploiesti -> "Ploiesti"
            Arad -> "Arad"
            Pitesti -> "Pitesti"
            Bacau -> "Bacau"
            BaiaMare -> "Baia Mare"
        }
    }

    companion object {
        fun fromNumber(number: Int): Cities? {
            return values().find { it.cityNumber == number }
        }
    }
}
