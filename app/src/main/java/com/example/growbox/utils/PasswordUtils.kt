package com.example.growbox.utils

fun isPasswordValid(password: String): Boolean {

    if (password.length < 8) {
        return false
    }
    val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#\$%^&+=]).{8,}\$")

    return passwordRegex.matches(password)
}