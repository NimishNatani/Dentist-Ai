package com.practicecoding.dentalai.screen

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Teleport
import com.exyte.animatednavbar.animation.indendshape.Height
import com.practicecoding.dentalai.R
import com.practicecoding.dentalai.Screens
import com.practicecoding.dentalai.general.noRippleClickable
import com.practicecoding.dentalai.ui.theme.darkBlue
import com.practicecoding.dentalai.ui.theme.lightBlue

@Composable
fun MainScreen(navHostController: NavHostController) {
    var selectedScreen by remember { mutableStateOf(NavigationItem.Home) }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedItem = selectedScreen,
                onItemSelected = { selectedScreen = it }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedScreen) {
                NavigationItem.Home -> HomeScreen(navHostController)
                NavigationItem.Book -> Text("Book Screen")  // Placeholder for BookScreen
                NavigationItem.Message -> Text("Message Screen")  // Placeholder for MessageScreen
                NavigationItem.Profile -> ProfileScreen()  // Placeholder for ProfileScreen
            }
        }
    }
}

@Composable
fun HomeScreen(navHostController: NavHostController) {
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(lightBlue)
                .verticalScroll(scrollState),
        ) {
            TopSection()
            CategoriesSection(navHostController)
            SnapAndEarnSection()
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun TopSection() {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp))
            .background(darkBlue)
            .padding(vertical = 16.dp, horizontal = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.profileimage),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
                Column {
                    Text(
                        text = "Welcome!",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "Utkarsh Zole",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
            Button(
                onClick = { /* TODO: Handle premium button click */ },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(text = "Premium", color = darkBlue, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        SearchSection()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchSection() {
    TextField(
        value = "",
        onValueChange = { /* TODO: Handle search text change */ },
        placeholder = { Text("Search") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp)),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun CategoriesSection(navHostController: NavHostController) {
    Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Categories",
                fontSize = 18.sp,
                modifier = Modifier.padding(12.dp),
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            TextButton(onClick = { /* TODO: Handle show all click */ }) {
                Text(text = "Show All", color = Color.Black, fontWeight = FontWeight.SemiBold)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            CategoryCard(
                iconResId = R.drawable.teethwithplus,
                title = "Scan Teeth",
                navHostController
            ) { navHostController.navigate(Screens.ScanTeethScreen.route) }
            CategoryCard(
                iconResId = R.drawable.teethreport, title = "Report", navHostController
            ) {navHostController.navigate(Screens.ReportScreen.route)}
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            CategoryCard(
                iconResId = R.drawable.chat, title = "Consultations", navHostController
            ) {}
            CategoryCard(
                iconResId = R.drawable.search, title = "Symptom Tracker", navHostController
            ) {navHostController.navigate(Screens.SymptomsTracker.route)}
        }
    }
}

@Composable
fun CategoryCard(
    iconResId: Int,
    title: String,
    navHostController: NavHostController,
    onClick: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .size(width = 150.dp, height = 130.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
//            .noRippleClickable(onClick())
            .clickable(
                onClick = onClick

//                enabled = true, onClickLabel = "click", role = Role.Button
            )
//            { navHostController.navigate(Screens.ScanTeethScreen.route) }
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = title,
            modifier = Modifier.size(60.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = title, fontSize = 14.sp, color = Color.Gray, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun SnapAndEarnSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Snap And Earn",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(16.dp)
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.cupnew),
                    contentDescription = "cup",
                    modifier = Modifier
                        .size(100.dp)
                        .clickable { },
                    contentScale = ContentScale.Inside
                )

                Text(
                    text = "Earn rewards for\ncompleting your weekly\nselfies",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            FeatureCard(iconResId = R.drawable.videoroll, title = "Videos")
            FeatureCard(iconResId = R.drawable.message, title = "FAQ's")
            FeatureCard(iconResId = R.drawable.reportready, title = "Articles")
            FeatureCard(iconResId = R.drawable.trolly, title = "Shop")
        }
    }
}

@Composable
fun FeatureCard(iconResId: Int, title: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .size(width = 75.dp, height = 90.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(vertical = 10.dp, horizontal = 10.dp)
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = title,
            modifier = Modifier
                .size(40.dp)
                .clickable { }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = title, fontSize = 16.sp, color = darkBlue)
    }
}

@Composable
fun BottomNavBar(
    selectedItem: NavigationItem,
    onItemSelected: (NavigationItem) -> Unit
) {
    val bottomBarItems = NavigationItem.entries.toTypedArray()
    AnimatedNavigationBar(
        modifier = Modifier
            .height(65.dp)
            .background(Color.White)
            .fillMaxWidth(),
        selectedIndex = selectedItem.ordinal,
        barColor = darkBlue,
        ballAnimation = Teleport(tween(durationMillis = 300)),
        ballColor = darkBlue,
        indentAnimation = Height(tween(durationMillis = 300)),
    ) {
        bottomBarItems.forEach { item ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .noRippleClickable { onItemSelected(item) },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = "Icon",
                        modifier = Modifier
                            .size(44.dp)
                            .padding(top = 2.dp),
                        tint = if (selectedItem == item) Color.White else Color.Gray
                    )
                    Text(
                        text = item.iconName, modifier = Modifier,
                        color = if (selectedItem == item) Color.White else Color.Gray
                    )
                }
            }
        }
    }
}

enum class NavigationItem(val icon: Int, val iconName: String) {
    Home(R.drawable.home, "Home"),
    Book(R.drawable.notification, "Alerts"),
    Message(R.drawable.location, "Dentists"),
    Profile(R.drawable.profile, "Profile")
}