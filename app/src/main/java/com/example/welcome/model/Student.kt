package com.example.welcome.model

data class Student (
    val id: Int,
    val name: String,
    val regNo: String,
    val course: String,
    val photoRes: Int,
    val isVerified: Boolean = true
    )