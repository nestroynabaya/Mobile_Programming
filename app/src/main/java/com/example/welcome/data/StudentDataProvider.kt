// com/example/welcome/data/StudentDataProvider.kt
package com.example.welcome.data
import com.example.welcome.R
import com.example.welcome.model.Student
object StudentDataProvider {
    val students: List<Student> = listOf(
        Student(
            id = 2,
            name = "NABAYA NESTROY",
            regNo = "24/2/306/D/053",
            course = "BSc. Computer Science",
            photoRes = R.drawable.person1,
            isVerified = false
        ),
        Student(
            id = 3,
            name = "MBOINEREKU CYRUS",
            regNo = "24/2/314/03",
            course = "BBA – Finance",
            photoRes = R.drawable.cryus,
            isVerified = true
        ),
        Student(
            id = 4,
            name = "WANDERA EDWIS",
            regNo = "24/2/314/04",
            course = "BSc. Software Engineering",
            photoRes = R.drawable.person3,
            isVerified = true
        ),
        Student(
            id = 5,
            name = "KABBASIITA MASIKO",
            regNo = "24/2/314/05",course = "BSc. INFORMATION SYSTEMS",
            photoRes = R.drawable.person4,
            isVerified = false
        ),
        Student(
            id = 7,
            name = "Namutebi Grace",
            regNo = "24/2/314/07",
            course = "BSc. Information Technology",
            photoRes = R.drawable.person2,
            isVerified = true
        )
    )
}