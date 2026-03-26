package com.example.welcome
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.welcome.model.Student
import com.example.welcome.ui.theme.NdejjeWelcomeAppTheme
import com.example.welcome.data.StudentDataProvider
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NdejjeWelcomeAppTheme {
                AttendanceScreen()
            }
        }
    }
}// ─── Screen
@Composable
fun AttendanceScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var presentIds by remember { mutableStateOf(setOf<Int>()) }
    val allStudents = StudentDataProvider.students
    val filteredStudents by remember {
        derivedStateOf {
            if (searchQuery.isBlank()) allStudents
            else allStudents.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                        it.regNo.contains(searchQuery, ignoreCase = true)
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ){
// ── Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = {
                Text(
                    text = "Search student name...",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            shape = RoundedCornerShape(50.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )
// ── Student count summary
        Text(
            text = "${filteredStudents.size} student(s) · ${presentIds.size} present",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 2.dp)
        )
// ── Student List
        if (filteredStudents.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "No student found",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 16.dp, end = 16.dp,
                    top = 8.dp, bottom = 24.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
                ){
                items(
                    items = filteredStudents,
                    key = { it.id }
                ) { student ->
                    val isPresent = student.id in presentIds
                    StudentAttendanceCard(
                        student = student,
                        isPresent = isPresent,
                        onMarkPresent = {
                            presentIds = presentIds + student.id
                        }
                    )
                }
            }
        }
    }
}
// ─── Student Card
@Composable
fun StudentAttendanceCard(
    student: Student,
    isPresent: Boolean,
    onMarkPresent: () -> Unit
){
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (isPresent)
                Color(0xFFF1F8E9)
                        else
                            MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
// ── Photo
        Image(
            painter = painterResource(student.photoRes),
            contentDescription = "Photo of ${student.name}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(12.dp))
// ── Name
        Text(
            text = student.name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(2.dp))
// ── Reg Number
        Text(
            text = student.regNo,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
// ── Verified badge
        if (student.isVerified) {
            Text(
                text = "Verified",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF2E7D32))
        } else {
            Text(
                text = "Not Verified",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.error
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
// ── Mark Present Button
        Button(
            onClick = onMarkPresent,
            enabled = !isPresent,
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = Color(0xFF4CAF50),
                disabledContentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth(0.65f)
        ){
            Text(
                text = if (isPresent) "✓ Present" else "Mark Present",
                fontWeight = FontWeight.SemiBold
            )
        }
    }
    }
}
// ─── Preview
@Preview(showBackground = true)
@Composable
fun AttendancePreview() {
    NdejjeWelcomeAppTheme {
        AttendanceScreen()}
}