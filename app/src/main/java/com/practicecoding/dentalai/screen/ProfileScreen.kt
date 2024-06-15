package com.practicecoding.dentalai.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicecoding.dentalai.R
import com.practicecoding.dentalai.ui.theme.darkBlue
import com.practicecoding.dentalai.ui.theme.lightBlue

@Composable
fun ProfileScreen(){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(lightBlue)) {
        HeaderSection()
        Column(
            modifier = Modifier
                .background(lightBlue)
                .verticalScroll(rememberScrollState())
        ) {
            GetMorePerksSection()
            MyActivitySection(txt1 = "My activity",txt2="Recorded scans",txt3="My\nconsults",image1 = R.drawable.teethreport,image2=R.drawable.profile)
            MyActivitySection(txt1 = "Information",txt2="Creator's\nVision",txt3="Term's &\nConditions",image1 = R.drawable.profile,image2=R.drawable.reportready)
            MyActivitySection(txt1 = "Refer to a Friend",txt2="Wallet",txt3="Orders",image1 = R.drawable.profile,image2=R.drawable.reportready)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box (modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.White)
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 10.dp, horizontal = 66.dp)){
                    Text(
                        text = "Saved Addresses",
                        color = Color(0xFF4A5AFF),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )        }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { /* TODO: Handle get premium click */ },
                    colors = ButtonDefaults.buttonColors(containerColor = darkBlue),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Logout", color = Color.White)
                }
            }

        }
    }

}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
            .background(darkBlue)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.profileimage), // Replace with your drawable resource
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(Color.White)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = "Welcome!",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Utkarsh Zole",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "UtkarshZole@gmail.com",
                color = Color.White,
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Box (modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(10.dp)){
            Image(
                painter = painterResource(id = R.drawable.crown), // Replace with your drawable resource
                contentDescription = "Crown Icon",
                modifier = Modifier.size(32.dp)
            )
        }

    }
}

@Composable
fun GetMorePerksSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White, shape = MaterialTheme.shapes.medium)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Get more perks!",
            color = Color(0xFF4A5AFF),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Image(
            painter = painterResource(id = R.drawable.crown), // Replace with your drawable resource
            contentDescription = "Crown Icon",
            modifier = Modifier.size(42.dp)
        )
        Text(
            text = "Get access to unlimited scans and\nAI Diagnosis crossed check by our dentists",
            color = Color(0xFF4A5AFF),
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { /* TODO: Handle get premium click */ },
            colors = ButtonDefaults.buttonColors(containerColor = darkBlue)
        ) {
            Text(text = "Get Premium", color = Color.White)
        }
    }
}

@Composable
fun MyActivitySection(txt1:String,txt2:String,txt3:String,image1:Int,image2:Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box (modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White)
            .align(Alignment.CenterHorizontally)
            .padding(vertical = 10.dp, horizontal = 66.dp)){
            Text(
                text = txt1,
                color = Color(0xFF4A5AFF),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            ActivityCard(txt2, image1) // Replace with your drawable resource
            ActivityCard(txt3, image2) // Replace with your drawable resource
        }
    }
}

@Composable
fun ActivityCard(title: String, iconRes: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(Color.White, shape = MaterialTheme.shapes.medium)
            .size(width = 120.dp, height = 122.dp)
            .padding(8.dp)
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = title,
            tint = Color(0xFF4A5AFF),
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            color = Color(0xFF4A5AFF),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
   ProfileScreen()
}


