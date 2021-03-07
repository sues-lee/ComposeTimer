/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.element.EggSize
import com.example.androiddevchallenge.ui.element.TargetSelector
import com.example.androiddevchallenge.ui.theme.MyTheme
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.StopCircle

import androidx.compose.runtime.getValue

import androidx.compose.runtime.livedata.observeAsState

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("Egg Timer")
                            }
                        )
                    },

                    ) {

                    MyApp(viewModel)
                }
            }
        }
    }

}

// Start building your app here!
@Composable
fun MyApp(viewModel: MainViewModel) {

    val second: Long by viewModel.currentSecond.observeAsState(0)
    val displaySecondString by viewModel.currentSecondDisplayString.observeAsState("")
    val state by viewModel.timerState.observeAsState(TimerState.STOPPED)
    val currentEgg by viewModel.currentEgg.observeAsState(EggSize.SMALL)


    Surface(color = MaterialTheme.colors.background) {

        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {

                Crossfade(targetState = state) { state ->

                    when (state) {
                        TimerState.STOPPED -> {
                            TargetSelector(second) {
                                viewModel.startTimer(it)
                            }
                        }
                        TimerState.STARTED, TimerState.PAUSED -> {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "${currentEgg.label}",
                                    fontSize = 36.sp
                                )

                                Spacer(modifier = Modifier.size(36.dp))

                                Crossfade(targetState = displaySecondString) { it ->
                                    Text(
                                        text = "$it",
                                        style = MaterialTheme.typography.h2
                                    )
                                }

                                Spacer(modifier = Modifier.size(36.dp))

                                Row {
                                    IconButton(onClick = {
                                        if (state == TimerState.STARTED) {
                                            //need pause
                                            viewModel.pauseTimer()
                                        } else {
                                            //need resume
                                            viewModel.resumeTimer()
                                        }
                                    }) {
                                        Crossfade(state) {
                                            if (it == TimerState.STARTED) {
                                                //need pause
                                                Icon(
                                                    modifier = Modifier.size(48.dp),
                                                    imageVector = Icons.Filled.PauseCircle,
                                                    contentDescription = null,
                                                    tint = MaterialTheme.colors.primary,
                                                )
                                            } else {
                                                //need resume and stop
                                                Row {
                                                    Icon(
                                                        modifier = Modifier.size(48.dp),
                                                        imageVector = Icons.Filled.PlayCircle,
                                                        contentDescription = null,
                                                        tint = MaterialTheme.colors.primary,
                                                    )

                                                }
                                            }
                                        }
                                    }

                                    //stop button

                                    Spacer(modifier = Modifier.size(16.dp))
                                    Crossfade(state) {
                                        if (it != TimerState.STARTED) {
                                            IconButton(onClick = { viewModel.stopTimer() }) {
                                                Icon(
                                                    modifier = Modifier.size(48.dp),
                                                    imageVector = Icons.Filled.StopCircle,
                                                    contentDescription = null,
                                                    tint = MaterialTheme.colors.primary,
                                                )
                                            }
                                        }
                                    }

                                }

                            }
                        }
                    }

                }
            }

        }

    }
}

//@Preview("Light Theme", widthDp = 360, heightDp = 640)
//@Composable
//fun LightPreview() {
//    MyTheme {
//        MyApp()
//    }
//}
//
//@Preview("Dark Theme", widthDp = 360, heightDp = 640)
//@Composable
//fun DarkPreview() {
//    MyTheme(darkTheme = true) {
//        MyApp()
//    }
//}
