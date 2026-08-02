package com.optibike.fitting.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Bike Fitting Step Model
 * Represents a single step in the 7-step bike fitting process
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
data class BikeFittingStep(
    val id: Int,
    val titleResId: Int,
    val descriptionResId: Int,
    val iconResId: Int? = null,
    val shortDescriptionResId: Int,
    val isCompleted: Boolean = false,
    val requiredMeasurements: List<MeasurementType> = emptyList()
)

/**
 * Measurement types required for bike fitting calculations
 */
enum class MeasurementType {
    HEIGHT,           // User height in cm
    INSEAM,           // Inseam length in cm
    SHOULDER_WIDTH,   // Shoulder width in cm
    ARM_LENGTH,       // Arm length in cm
    TORSO_LENGTH,     // Torso length in cm
    BIKE_TYPE,        // Road or Gravel
    FRAME_SIZE,       // Bike frame size
    SADDLE_HEIGHT,    // Current saddle height
    HANDLEBAR_HEIGHT, // Current handlebar height
    CRANK_LENGTH,      // Crank length in mm
    SHOE_SIZE         // Shoe size (for cleat positioning)
}

/**
 * Bike types supported by the app
 */
enum class BikeType {
    ROAD, GRAVEL
}

/**
 * Predefined bike fitting steps
 */
object BikeFittingSteps {
    // Step IDs
    const val STEP_SADDLE_HEIGHT = 1
    const val STEP_SADDLE_TILT = 2
    const val STEP_SADDLE_FORE_AFT = 3
    const val STEP_SADDLE_HANDLEBAR_DISTANCE = 4
    const val STEP_HANDLEBAR_HEIGHT = 5
    const val STEP_COCKPIT_ADJUSTMENT = 6
    const val STEP_CLEAT_POSITIONING = 7
    
    // List of all steps (will be populated with actual resource IDs)
    fun getAllSteps(): List<BikeFittingStep> {
        return listOf(
            BikeFittingStep(
                id = STEP_SADDLE_HEIGHT,
                titleResId = com.optibike.fitting.R.string.step_1_title,
                descriptionResId = com.optibike.fitting.R.string.step_1_description,
                shortDescriptionResId = com.optibike.fitting.R.string.step_1_short,
                requiredMeasurements = listOf(
                    MeasurementType.HEIGHT,
                    MeasurementType.INSEAM,
                    MeasurementType.BIKE_TYPE
                )
            ),
            BikeFittingStep(
                id = STEP_SADDLE_TILT,
                titleResId = com.optibike.fitting.R.string.step_2_title,
                descriptionResId = com.optibike.fitting.R.string.step_2_description,
                shortDescriptionResId = com.optibike.fitting.R.string.step_2_short,
                requiredMeasurements = listOf(MeasurementType.SADDLE_HEIGHT)
            ),
            BikeFittingStep(
                id = STEP_SADDLE_FORE_AFT,
                titleResId = com.optibike.fitting.R.string.step_3_title,
                descriptionResId = com.optibike.fitting.R.string.step_3_description,
                shortDescriptionResId = com.optibike.fitting.R.string.step_3_short,
                requiredMeasurements = listOf(
                    MeasurementType.HEIGHT,
                    MeasurementType.INSEAM,
                    MeasurementType.BIKE_TYPE
                )
            ),
            BikeFittingStep(
                id = STEP_SADDLE_HANDLEBAR_DISTANCE,
                titleResId = com.optibike.fitting.R.string.step_4_title,
                descriptionResId = com.optibike.fitting.R.string.step_4_description,
                shortDescriptionResId = com.optibike.fitting.R.string.step_4_short,
                requiredMeasurements = listOf(
                    MeasurementType.ARM_LENGTH,
                    MeasurementType.TORSO_LENGTH
                )
            ),
            BikeFittingStep(
                id = STEP_HANDLEBAR_HEIGHT,
                titleResId = com.optibike.fitting.R.string.step_5_title,
                descriptionResId = com.optibike.fitting.R.string.step_5_description,
                shortDescriptionResId = com.optibike.fitting.R.string.step_5_short,
                requiredMeasurements = listOf(
                    MeasurementType.HEIGHT,
                    MeasurementType.BIKE_TYPE
                )
            ),
            BikeFittingStep(
                id = STEP_COCKPIT_ADJUSTMENT,
                titleResId = com.optibike.fitting.R.string.step_6_title,
                descriptionResId = com.optibike.fitting.R.string.step_6_description,
                shortDescriptionResId = com.optibike.fitting.R.string.step_6_short,
                requiredMeasurements = listOf(
                    MeasurementType.SHOULDER_WIDTH,
                    MeasurementType.ARM_LENGTH
                )
            ),
            BikeFittingStep(
                id = STEP_CLEAT_POSITIONING,
                titleResId = com.optibike.fitting.R.string.step_7_title,
                descriptionResId = com.optibike.fitting.R.string.step_7_description,
                shortDescriptionResId = com.optibike.fitting.R.string.step_7_short,
                requiredMeasurements = listOf(
                    MeasurementType.SHOE_SIZE,
                    MeasurementType.CRANK_LENGTH
                )
            )
        )
    }
    
    fun getStepById(stepId: Int): BikeFittingStep? {
        return getAllSteps().find { it.id == stepId }
    }
}
