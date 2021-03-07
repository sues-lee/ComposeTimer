package com.example.androiddevchallenge.ui.element

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TargetSelector(initSecond: Long, onStart: (EggSize) -> Unit) {

    val targetEggSize = mutableStateOf(EggSize.SMALL)

    Card(
        modifier = Modifier.padding(8.dp),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Egg Size", style = MaterialTheme.typography.h4)
            Spacer(modifier = Modifier.size(16.dp))
            Row {
                EggItem(size = EggSize.SMALL) {
                    targetEggSize.value = it
                }
                EggItem(size = EggSize.MEDIUM) {
                    targetEggSize.value = it
                }
                EggItem(size = EggSize.LARGE) {
                    targetEggSize.value = it
                }
            }
            Spacer(modifier = Modifier.size(16.dp))

            Text(
                text = "Current Egg size: ${targetEggSize.value.label}",
                style = MaterialTheme.typography.h6
            )

            Spacer(modifier = Modifier.size(16.dp))
            Button(onClick = { onStart(targetEggSize.value) }) {
                Text("START TIMER")
            }

        }
    }
}

enum class EggSize(val time: Long, val label: String) {
    SMALL((60 * 2.5).toLong(), "Small"),
    MEDIUM(60 * 3L, "Medium"),
    LARGE((60 * 3.5).toLong(), "Large")
}

@Composable
fun EggItem(size: EggSize, onSelect: (EggSize) -> Unit) {

    val text = size.label

    Box(modifier = Modifier.padding(8.dp)) {
        Button(onClick = {
            onSelect(size)
        }) {
            Text(text = text)
        }
    }
}