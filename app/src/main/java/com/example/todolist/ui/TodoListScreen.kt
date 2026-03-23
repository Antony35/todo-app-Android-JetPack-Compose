package com.example.todolist.ui

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolist.domain.model.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel = viewModel()
) {
    val tasks by viewModel.tasks.collectAsState()

    var isAddingTask by remember { mutableStateOf(false) }
    var editingTask by remember { mutableStateOf<Task?>(null) }
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val filteredTasks = tasks.filter { if (selectedTabIndex == 0) !it.isDone else it.isDone }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ma Todo List", fontWeight = FontWeight.ExtraBold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        },
        floatingActionButton = {
            if (selectedTabIndex == 0 && editingTask == null && !isAddingTask) {
                FloatingActionButton(
                    onClick = { isAddingTask = true },
                    containerColor = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Ajouter")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primaryContainer.copy(
                                0.1f
                            ), Color.White
                        )
                    )
                )
        ) {
            TabRow(selectedTabIndex = selectedTabIndex, containerColor = Color.Transparent) {
                listOf("À faire", "Terminées").forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                title,
                                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }

            AnimatedVisibility(
                visible = isAddingTask || editingTask != null,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut(),
                modifier = Modifier.padding(16.dp)
            ) {
                TaskForm(
                    initialTitle = editingTask?.title ?: "",
                    initialDescription = editingTask?.description ?: "",
                    submitButtonText = if (editingTask != null) "Modifier" else "Ajouter",
                    onSave = { title, desc ->
                        if (editingTask != null) viewModel.updateTask(
                            editingTask!!.id,
                            title,
                            desc
                        ) else viewModel.addTask(title, desc)
                        isAddingTask = false; editingTask = null
                    },
                    onCancel = { isAddingTask = false; editingTask = null }
                )
            }

            if (filteredTasks.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "Aucune tâche ici ✨",
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.5f)
                    )
                }
            } else {
                LazyColumn(
                    Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredTasks, key = { it.id }) { task ->
                        TaskItem(
                            task = task,
                            onToggleStatus = { viewModel.toggleTaskStatus(task.id) },
                            onEditClick = { isAddingTask = false; editingTask = task },
                            onDeleteClick = { viewModel.deleteTask(task.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TaskForm(
    initialTitle: String,
    initialDescription: String,
    submitButtonText: String,
    onSave: (String, String) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf(initialTitle) }
    var description by remember { mutableStateOf(initialDescription) }

    Card(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(Modifier.padding(20.dp)) {
            Text(
                if (submitButtonText == "Modifier") "Modifier la tâche" else "Nouvelle tâche",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Titre") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(Modifier.height(20.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(onClick = onCancel, Modifier.weight(1f)) { Text("Annuler") }
                Button(
                    onClick = { if (title.isNotBlank()) onSave(title, description) },
                    enabled = title.isNotBlank(),
                    modifier = Modifier.weight(1f)
                ) { Text(submitButtonText) }
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onToggleStatus: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = task.isDone, onCheckedChange = { onToggleStatus() })
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    task.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        textDecoration = if (task.isDone) TextDecoration.LineThrough else TextDecoration.None
                    )
                )
                if (task.description.isNotBlank()) Text(
                    task.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.8f)
                )
            }
            Row {
                if (!task.isDone) IconButton(onClick = onEditClick) {
                    Icon(
                        Icons.Default.Edit,
                        "Modifier",
                        tint = MaterialTheme.colorScheme.primary.copy(0.7f)
                    )
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        Icons.Default.Delete,
                        "Supprimer",
                        tint = MaterialTheme.colorScheme.error.copy(0.7f)
                    )
                }
            }
        }
    }
}
