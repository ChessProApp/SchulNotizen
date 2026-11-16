package com.example.schulnotizen.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.schulnotizen.data.NoteEntity
import com.example.schulnotizen.ui.components.TabBarRow

@Composable
fun EditorScreen(
    currentNote: NoteEntity,
    openTabs: List<NoteEntity>,
    onTabSelected: (NoteEntity) -> Unit,
    onTabClose: (NoteEntity) -> Unit,
    onNoteChanged: (NoteEntity) -> Unit,
    onBackToDashboard: () -> Unit
) {
    var title by remember(currentNote.id) { mutableStateOf(currentNote.title) }
    var content by remember(currentNote.id) { mutableStateOf(currentNote.content) }

    BackHandler {
        onNoteChanged(currentNote.copy(title = title, content = content))
        onBackToDashboard()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Bar mit Zurück-Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    onNoteChanged(currentNote.copy(title = title, content = content))
                    onBackToDashboard()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Zurück",
                        tint = Color.White
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = currentNote.subject,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        maxLines = 1
                    )
                }
            }

            // Browser-Style Tabs Leiste
            TabBarRow(
                openTabs = openTabs,
                activeNoteId = currentNote.id,
                onTabSelected = {
                    onNoteChanged(currentNote.copy(title = title, content = content))
                    onTabSelected(it)
                },
                onTabClose = {
                    onTabClose(it)
                }
            )

            // Kontextuelle Formatierungs-Toolbar
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Button(
                        onClick = {
                            title = "# $title"
                            onNoteChanged(currentNote.copy(title = title, content = content))
                        },
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Icon(Icons.Default.Title, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Überschrift", fontSize = 12.sp, color = Color.White)
                    }
                }
                item {
                    Button(
                        onClick = {
                            content += "\n- [ ] Hausaufgabe: "
                            onNoteChanged(currentNote.copy(title = title, content = content))
                        },
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Icon(Icons.Default.CheckBox, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Checkliste", fontSize = 12.sp, color = Color.White)
                    }
                }
                item {
                    Button(
                        onClick = {
                            content += "\n> 💡 INFO-BOX: "
                            onNoteChanged(currentNote.copy(title = title, content = content))
                        },
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Icon(Icons.Default.Info, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Info-Box", fontSize = 12.sp, color = Color.White)
                    }
                }
            }

            // Notizen Eingabebereich
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        onNoteChanged(currentNote.copy(title = title, content = content))
                    },
                    singleLine = true,
                    placeholder = { Text("Titel der Notiz...") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = content,
                    onValueChange = {
                        content = it
                        onNoteChanged(currentNote.copy(title = title, content = content))
                    },
                    placeholder = { Text("Schreibe deine Mitschrift oder Hausaufgaben hier hin...") },
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }
    }
}
