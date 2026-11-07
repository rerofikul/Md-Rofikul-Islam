package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.HolidayVillage
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.HolidayVillage
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.AddInfoDialog
import com.example.ui.AdminPanelDialog
import com.example.ui.BloodDonorsScreen
import com.example.ui.DoctorsScreen
import com.example.ui.EducationScreen
import com.example.ui.HomeScreen
import com.example.ui.KrishiScreen
import com.example.ui.MarketScreen
import com.example.ui.ParishadScreen
import com.example.ui.VillagesScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.UnionEmergencyRed
import com.example.ui.theme.UnionGreenPrimary
import com.example.viewmodel.UnionViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainApp()
            }
        }
    }
}

data class NavItem(
    val title: String,
    val tabIndex: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(
    viewModel: UnionViewModel = viewModel()
) {
    val context = LocalContext.current
    val selectedTab by viewModel.selectedTab.collectAsState()
    val showAddDialog by viewModel.showAddDialog.collectAsState()
    val showAdminDialog by viewModel.showAdminDialog.collectAsState()
    val userMessage by viewModel.userMessage.collectAsState()
    val submissions by viewModel.submissions.collectAsState()

    val pendingCount = submissions.count { it.status == "PENDING" }

    // Request Notification permission on Android 13+ (API 33+)
    val notificationPermissionLauncher = androidx.activity.compose.rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        // Permission result handled
    }

    LaunchedEffect(Unit) {
        com.example.util.NotificationHelper.createNotificationChannels(context)
        com.example.util.NotificationHelper.subscribeToFcmTopics(context)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (androidx.core.content.ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != android.content.pm.PackageManager.PERMISSION_GRANTED
            ) {
                notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    // Show User Messages (Toasts)
    LaunchedEffect(userMessage) {
        userMessage?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            viewModel.clearUserMessage()
        }
    }

    // Android Hardware / Gesture Back Navigation
    BackHandler(enabled = selectedTab != 0) {
        viewModel.setSelectedTab(0)
    }

    val navItems = listOf(
        NavItem("হোম", 0, Icons.Filled.Home, Icons.Outlined.Home),
        NavItem("শিক্ষা", 1, Icons.Filled.School, Icons.Outlined.School),
        NavItem("গ্রামসমূহ", 2, Icons.Filled.HolidayVillage, Icons.Outlined.HolidayVillage),
        NavItem("ইউপি পরিষদ", 3, Icons.Filled.AccountBalance, Icons.Outlined.AccountBalance),
        NavItem("বাজার", 6, Icons.Filled.Storefront, Icons.Outlined.Storefront)
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "আমাদের সানন্দবাড়ী",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = Color(0xFF4CAF50),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "দেওয়ানগঞ্জ",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = "চর আমখাওয়া (আধখাওয়া) ইউনিয়ন পরিষদ, জামালপুর",
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.88f)
                        )
                    }
                },
                actions = {
                    // Quick Citizen Add Info Button
                    FilledTonalButton(
                        onClick = { viewModel.openAddDialog() },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .testTag("top_add_info_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Info",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("তথ্য যোগ", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0A4D2E)
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                navItems.forEach { item ->
                    val isSelected = selectedTab == item.tabIndex
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { viewModel.setSelectedTab(item.tabIndex) },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title,
                                tint = if (isSelected) UnionGreenPrimary else Color.Gray
                            )
                        },
                        label = {
                            Text(
                                text = item.title,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) UnionGreenPrimary else Color.Gray
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color(0xFFE8F5E9)
                        ),
                        modifier = Modifier.testTag("nav_tab_${item.tabIndex}")
                    )
                }
            }
        },
        floatingActionButton = {
            if (selectedTab != 0) {
                ExtendedFloatingActionButton(
                    onClick = { viewModel.openAddDialog() },
                    containerColor = UnionGreenPrimary,
                    contentColor = Color.White,
                    icon = { Icon(Icons.Default.Add, contentDescription = "Add") },
                    text = { Text("নতুন তথ্য যোগ করুন", fontWeight = FontWeight.Bold, fontSize = 12.sp) },
                    modifier = Modifier.testTag("fab_add_info")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> HomeScreen(
                    viewModel = viewModel,
                    context = context,
                    onNavigateToTab = { viewModel.setSelectedTab(it) }
                )
                1 -> EducationScreen(
                    viewModel = viewModel,
                    context = context
                )
                2 -> VillagesScreen(
                    viewModel = viewModel
                )
                3 -> ParishadScreen(
                    viewModel = viewModel,
                    context = context
                )
                4 -> DoctorsScreen(
                    viewModel = viewModel,
                    context = context
                )
                5 -> BloodDonorsScreen(
                    viewModel = viewModel,
                    context = context
                )
                6 -> MarketScreen(
                    viewModel = viewModel,
                    context = context
                )
                7 -> KrishiScreen(
                    viewModel = viewModel,
                    context = context
                )
            }
        }
    }

    // Citizen Add Info Dialog
    if (showAddDialog) {
        AddInfoDialog(
            viewModel = viewModel,
            onDismiss = { viewModel.closeAddDialog() }
        )
    }

    // Union Admin Panel Dialog
    if (showAdminDialog) {
        AdminPanelDialog(
            viewModel = viewModel,
            onDismiss = { viewModel.closeAdminDialog() }
        )
    }
}
