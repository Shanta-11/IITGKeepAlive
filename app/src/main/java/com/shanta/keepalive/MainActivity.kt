package com.shanta.keepalive

import android.content.IntentFilter
import android.graphics.drawable.PaintDrawable
import android.net.wifi.WifiManager
import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.shanta.keepalive.ui.theme.KeepAliveTheme

class MainActivity : ComponentActivity() {
    val wifiStateChangeReciever = NetworkChangeReciever()
    val viewModel: MainViewModel by viewModels()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        registerReceiver(wifiStateChangeReciever, IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION))

        setContent {
            KeepAliveTheme {
                var openDialog by remember { mutableStateOf(false) }
                Scaffold (
                    topBar = {
                             TopAppBar(
                                 title = { Text(text = "KeepAlive") },

                             )
                    },
                    floatingActionButtonPosition = FabPosition.End,
                    floatingActionButton = {
                        ExtendedFloatingActionButton(
                            text = { Text(text = "Add Credentials") },
                            icon = { Icon(imageVector = Icons.Filled.Add, contentDescription = "Add") },
                            onClick = { openDialog = !openDialog })
                    },
                    content = {

                        Column(
                            modifier = Modifier
                                .padding(it)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "hello")
                            if (openDialog){
                                InputCredDialog(mainViewModel = viewModel) {
                                    openDialog = false
                                }
                            }
                        }
                    }

                )
            }
        }
    }

    override fun onStop() {
        super.onStop()
//        unregisterReceiver(wifiStateChangeReciever)
    }
    
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun InputCredDialog(mainViewModel: MainViewModel, onDismiss: () -> Unit){
        var user by remember{ mutableStateOf(TextFieldValue(""))}
        val focusManager = LocalFocusManager.current
        val showPassword = remember { mutableStateOf(false) }
        Dialog(
            onDismissRequest = { onDismiss() }
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                shadowElevation = 4.dp,
                shape = RoundedCornerShape(28.dp),
                color = MaterialTheme.colorScheme.secondaryContainer

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Input New Credentials",
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = user,
                        onValueChange = { user = it },
                        label = { Text(text = "IITG Username")},
                        leadingIcon = { Icon(imageVector = Icons.Filled.Person, contentDescription = "User")},
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            focusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            focusedLeadingIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            focusedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            autoCorrect = true,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                            }
                        ),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = user,
                        onValueChange = { user = it },
                        label = { Text(text = "Password")},
                        leadingIcon = { Icon(painter = painterResource(id = R.drawable.dial_pad), contentDescription = "Pass", tint = MaterialTheme.colorScheme.onSecondaryContainer)},
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            focusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            focusedLeadingIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            focusedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer
                        ),
                        visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            autoCorrect = true,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                            }
                        ),
                        trailingIcon = {
                            val icon = if(showPassword.value){
                                R.drawable.visible
                            }
                            else{
                                R.drawable.visibleoff
                            }

                            IconButton(onClick = { showPassword.value = !showPassword.value }) {
                                Icon(painter = painterResource(id = icon), contentDescription = "Visibility", tint = MaterialTheme.colorScheme.onSecondaryContainer)
                            }
                        },
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onSecondaryContainer
                        ),
                        onClick = {
                            mainViewModel.setCredentials(applicationContext, user.text, "")
                            onDismiss()
                        }
                    ) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }
}

