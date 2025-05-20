package com.drmiaji.tajweed

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drmiaji.tajweed.activity.About
import com.drmiaji.tajweed.activity.SettingsActivity
import com.drmiaji.tajweed.ui.ChapterListActivity
import com.drmiaji.tajweed.ui.theme.MyAppTheme
import com.drmiaji.tajweed.ui.theme.topBarColors
import kotlinx.coroutines.launch
import androidx.core.net.toUri
import com.drmiaji.tajweed.ui.WebViewActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyAppTheme {
                MainScreen(
                    onNavigateToContents = { goToContents() },
                    onNavigateToSettings = { goToSettings() },
                    onLogoClick = { goToContents() }
                )
            }
        }
    }

    private fun goToContents() {
        startActivity(Intent(this, ChapterListActivity::class.java)) // ← replace with your actual Activity
    }
    private fun goToSettings() {
        startActivity(Intent(this, SettingsActivity::class.java)) // ← replace with your actual Activity
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToContents: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onLogoClick: () -> Unit = {}
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val menuItems = listOf(
        DrawerItem("হোম", "সূচীপত্র: বিষয়বস্তু দেখুন", Icons.Default.Home, activityClass = ChapterListActivity::class.java),
        DrawerItem("সেটিংস", "এপ নিয়ন্ত্রণ করুন", Icons.Default.Settings, activityClass = SettingsActivity::class.java),
        DrawerItem("এবাউট", "আমাদের সম্পর্কে", Icons.Default.Person, activityClass = About::class.java),
        DrawerItem("আমাদের ওয়েবসাইট", "ওয়েবসাইটে যান", Icons.Default.Public, linkUrl = "https://www.drmiaji.com")
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(300.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.primary
                            )
                        )
                    ),
                drawerContainerColor = Color.Transparent
            )
            {
                MyLogo(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 24.dp)
                )

                HorizontalDivider()

                var selectedItem by remember { mutableStateOf<DrawerItem?>(null) }

                menuItems.forEach { item ->
                    DrawerCardItem(
                        item = item,
                        selected = item == selectedItem,
                        onClick = {
                            selectedItem = item
                            when {
                                item.activityClass != null -> {
                                    context.startActivity(Intent(context, item.activityClass))
                                }
                                item.linkUrl != null -> {
                                    // Reuse existing WebViewActivity for external links
                                    val intent = Intent(context, WebViewActivity::class.java).apply {
                                        putExtra("title", item.title)
                                        putExtra("url", item.linkUrl) // New parameter for external URL
                                    }
                                    context.startActivity(intent)
                                }
                            }
                            scope.launch { drawerState.close() }
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.app_name)) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        Box {
                            IconButton(onClick = { showMenu = !showMenu }) {
                                Icon(Icons.Default.MoreVert, contentDescription = "More")
                            }
                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest = { showMenu = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("About") },
                                    onClick = { context.startActivity(Intent(context,
                                        ChapterListActivity::class.java)); showMenu = false }
                                )
                                DropdownMenuItem(
                                    text = { Text("Settings") },
                                    onClick = { context.startActivity(Intent(context,
                                        SettingsActivity::class.java)); showMenu = false }
                                )
                            }
                        }
                    },
                    colors = topBarColors()
                )
            }
        ) { innerPadding ->
            val colors = MaterialTheme.colorScheme
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                colors.secondaryContainer,  // softer shade for top
                                colors.secondary            // stronger shade for bottom
                            )
                        )
                    )
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tajweed01),
                    contentDescription = "App Logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(24.dp))
                        .clickable { onLogoClick() }
                        .border(2.dp, Color.White, RoundedCornerShape(24.dp))
                        .shadow(8.dp, RoundedCornerShape(24.dp))
                )
            }
        }
    }
}

// Data class for drawer items
data class DrawerItem(
    val title: String,
    val subtitle: String = "",
    val icon: ImageVector,
    val activityClass: Class<out Activity>? = null,
    val linkUrl: String? = null
)

@Composable
fun MyLogo(modifier: Modifier = Modifier) {
    val colors = MaterialTheme.colorScheme
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = CardDefaults.outlinedShape,
        colors = CardDefaults.cardColors(containerColor = colors.primary),  // Use primary or any appropriate color
        border = CardDefaults.outlinedCardBorder(),
        elevation = CardDefaults.outlinedCardElevation()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.icon),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                contentScale = ContentScale.Inside
            )
            Spacer(Modifier.width(8.dp))
            val colors = MaterialTheme.colorScheme
            Column {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.solaimanlipi))
                    ),
                    color = colors.background // instead of Color.LightGray
                )
                Text(
                    text = "ডক্টর আব্দুল বাতেন মিয়াজী",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = FontFamily(Font(R.font.solaimanlipi))
                    ),
                    color = colors.background
                )
            }
        }
    }
}

@Composable
fun DrawerCardItem(
    item: DrawerItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean = false
) {
    val shape = RoundedCornerShape(12.dp)

    // Animate background color
    val backgroundColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceVariant,
        animationSpec = tween(durationMillis = 300),
        label = "bgColor"
    )

// Border and icon tint colors
    val borderColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    val iconTint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = BorderStroke(1.dp, borderColor),
        shape = shape,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = iconTint,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = item.title,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.solaimanlipi))
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (item.subtitle.isNotEmpty()) {
                        Text(
                            text = item.subtitle,
                            style = TextStyle(
                                fontSize = 13.sp,
                                fontFamily = FontFamily(Font(R.font.solaimanlipi))
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Navigate",
                tint = iconTint
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MyAppTheme {
        MainScreen(onLogoClick = {})
    }
}
