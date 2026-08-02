package com.optibike.fitting.presentation.screens.bike

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.optibike.fitting.R
import com.optibike.fitting.domain.model.Bike
import com.optibike.fitting.domain.model.BikeType
import com.optibike.fitting.presentation.theme.OptiBikeColors
import com.optibike.fitting.presentation.viewmodel.BikeViewModel

/**
 * Add Bike Screen
 * Form to create a new bike profile
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBikeScreen(
    navController: NavController,
    viewModel: BikeViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(BikeType.ROAD) }
    var frameSize by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.add_bike_title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = OptiBikeColors.BackgroundDark,
                    titleContentColor = OptiBikeColors.TextPrimary,
                    navigationIconContentColor = OptiBikeColors.PrimaryCyan
                )
            )
        },
        containerColor = OptiBikeColors.BackgroundDark
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Bike Name
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(id = R.string.bike_name_label)) },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors()
            )

            // Bike Type
            Text(
                text = stringResource(id = R.string.bike_type_label),
                color = OptiBikeColors.TextPrimary,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                BikeTypeOption(
                    type = BikeType.ROAD,
                    isSelected = type == BikeType.ROAD,
                    onClick = { type = BikeType.ROAD },
                    modifier = Modifier.weight(1f)
                )
                BikeTypeOption(
                    type = BikeType.GRAVEL,
                    isSelected = type == BikeType.GRAVEL,
                    onClick = { type = BikeType.GRAVEL },
                    modifier = Modifier.weight(1f)
                )
            }

            // Frame Size
            OutlinedTextField(
                value = frameSize,
                onValueChange = { if (it.isEmpty() || it.toDoubleOrNull() != null) frameSize = it },
                label = { Text(stringResource(id = R.string.frame_size_label)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                colors = textFieldColors()
            )

            // Notes
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text(stringResource(id = R.string.notes_label)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                colors = textFieldColors()
            )

            Spacer(modifier = Modifier.weight(1f))

            // Save Button
            Button(
                onClick = {
                    viewModel.addBike(
                        Bike(
                            name = name,
                            type = type,
                            frameSize = frameSize.toDoubleOrNull(),
                            notes = notes
                        )
                    )
                    navController.navigateUp()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = name.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = OptiBikeColors.PrimaryCyan,
                    contentColor = OptiBikeColors.BackgroundDark
                )
            ) {
                Text(
                    text = stringResource(id = R.string.save_bike_button),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun BikeTypeOption(
    type: BikeType,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) OptiBikeColors.PrimaryCyan else OptiBikeColors.SurfaceDark,
            contentColor = if (isSelected) OptiBikeColors.BackgroundDark else OptiBikeColors.TextPrimary
        ),
        border = if (!isSelected) androidx.compose.foundation.BorderStroke(1.dp, OptiBikeColors.CardStroke) else null
    ) {
        Text(
            text = if (type == BikeType.ROAD) stringResource(id = R.string.form_bike_type_road)
                   else stringResource(id = R.string.form_bike_type_gravel)
        )
    }
}

@Composable
fun textFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = OptiBikeColors.PrimaryCyan,
    unfocusedBorderColor = OptiBikeColors.CardStroke,
    focusedLabelColor = OptiBikeColors.PrimaryCyan,
    unfocusedLabelColor = OptiBikeColors.TextSecondary,
    cursorColor = OptiBikeColors.PrimaryCyan,
    focusedTextColor = OptiBikeColors.TextPrimary,
    unfocusedTextColor = OptiBikeColors.TextPrimary
)
