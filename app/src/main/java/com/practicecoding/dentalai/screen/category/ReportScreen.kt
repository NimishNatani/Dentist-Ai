package com.practicecoding.dentalai.screen.category

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicecoding.dentalai.R
import com.practicecoding.dentalai.screen.HeaderSection
import com.practicecoding.dentalai.ui.theme.darkBlue
import com.practicecoding.dentalai.ui.theme.dividerBlue
import com.practicecoding.dentalai.ui.theme.lightBlue
import com.practicecoding.dentalai.ui.theme.purple
import com.practicecoding.dentalai.ui.theme.red
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReportScreen() {
    var selectedImage by remember { mutableIntStateOf(R.drawable.girlimage1) }
    var scroll = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
            .background(lightBlue)
    ) {
        HeaderSection()
        Column(
            modifier = Modifier
                .background(lightBlue)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.White)
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 10.dp, horizontal = 66.dp)
            ) {
                Text(
                    text = "My Reports",
                    color = Color(0xFF4A5AFF),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            CalendarApp()

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ImageSelectionBox("Lower", selectedImage == R.drawable.girlimage1) {
                    selectedImage = R.drawable.girlimage1
                }
                ImageSelectionBox("Upper", selectedImage == R.drawable.girlimage2) {
                    selectedImage = R.drawable.girlimage2
                }
                ImageSelectionBox("Smile", selectedImage == R.drawable.girlimage3) {
                    selectedImage = R.drawable.girlimage3
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color.White)
            ) {
                Image(
                    painter = painterResource(id = selectedImage),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillBounds
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            DiagnosisCard()

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                CariesMeterScore(txt = "Caries Meter Score", image = R.drawable.meter_score)
                CariesMeterScore(txt = "Oral Cavity Score", image = R.drawable.round_score)
            }
        }
    }
}

@Composable
fun ImageSelectionBox(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) Color(0xFF3D5AFE) else Color.White)
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = if (isSelected) Color.White else Color.Black,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun DiagnosisCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "AI Diagnosis Card",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = purple
        )

        Spacer(modifier = Modifier.height(2.dp))
        HorizontalDivider(thickness = 2.dp, color = dividerBlue)
        Spacer(modifier = Modifier.height(6.dp))

        Row {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(red)
                    .padding(horizontal = 30.dp, vertical = 10.dp)
            ) {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.broken_teeth),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Alert!\nCaries\nDetected",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold
                    )
                }

            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Hey Utkarsh! You've got miniature cavity monsters in your lower teeth, learn about how to fight them.",
                fontSize = 14.sp,
                color = purple,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CariesMeterScore(txt: String, image: Int) {
    Card(
        modifier = Modifier.background(Color.White),
    ) {
        Text(text = txt)
        HorizontalDivider(thickness = 2.dp, color = dividerBlue)
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarApp() {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var currentYearMonth by remember { mutableStateOf(YearMonth.now()) }
    val confirmedDates = listOf(
        LocalDate.of(2024, 6, 5),
        LocalDate.of(2024, 6, 15),
        LocalDate.of(2024, 6, 22),
        LocalDate.of(2024, 6, 25),
        LocalDate.of(2024, 7, 10),
        LocalDate.of(2024, 7, 20)
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(420.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White)
            .border(width = 2.dp, color = darkBlue, shape = RoundedCornerShape(15.dp))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
//            .padding(16.dp)
//            .border(width = 2.dp, color = darkBlue)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    currentYearMonth = currentYearMonth.minusMonths(1)
                    selectedDate = LocalDate.of(currentYearMonth.year, currentYearMonth.month, 1)

                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous Month")
                }

                Text(
                    text = "${selectedDate.dayOfMonth} ${
                        selectedDate.month.getDisplayName(
                            TextStyle.FULL,
                            Locale.getDefault()
                        )
                    } ${selectedDate.year}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                IconButton(onClick = {
                    currentYearMonth = currentYearMonth.plusMonths(1)
                    selectedDate = LocalDate.of(currentYearMonth.year, currentYearMonth.month, 1)
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next Month")
                }
            }

            // Days of the Week
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, darkBlue),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
                for (day in daysOfWeek) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, darkBlue)
                            .padding(2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = day,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Calendar Grid
            val firstDayOfMonth = currentYearMonth.atDay(1)
            val dayOfWeek =
                firstDayOfMonth.dayOfWeek.value % 7 // Make Sunday = 0, Monday = 1, ..., Saturday = 6
            val daysInMonth = currentYearMonth.lengthOfMonth()

            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Empty cells before the first day of the month
                items(dayOfWeek) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .border(1.dp, darkBlue)
                    )

                }

                // Days of the month
                items(daysInMonth) { index ->
                    val day = index + 1
                    val currentDate =
                        LocalDate.of(currentYearMonth.year, currentYearMonth.month, day)
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .border(width = 1.dp, color = darkBlue)
                            .clickable {
                                selectedDate = currentDate
                            },
                        contentAlignment = Alignment.TopStart
                    ) {
                        Text(
                            text = day.toString(),
                            fontSize = 16.sp,
                            fontWeight = if (selectedDate.dayOfMonth == day && selectedDate.month == currentYearMonth.month || confirmedDates.contains(
                                    currentDate
                                )
                            ) FontWeight.Bold else FontWeight.Normal,
                            color = if (confirmedDates.contains(currentDate)) Color.Black else Color.Black,
                            modifier = Modifier.padding(start = 3.dp, top = 3.dp)
                        )
                        if (confirmedDates.contains(currentDate)) {
                            Image(
                                painter = painterResource(id = R.drawable.confirmed),
                                contentDescription = "check icon",
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(top = 5.dp, start = 5.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}