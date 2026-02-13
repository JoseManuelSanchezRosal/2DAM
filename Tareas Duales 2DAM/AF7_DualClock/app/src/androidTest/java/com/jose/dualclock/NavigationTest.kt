package com.jose.dualclock

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigationTest {

    // Regla para inyectar Hilt en el test
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    // Regla para lanzar la MainActivity y controlar la UI
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        // Inyectamos las dependencias antes de cada test
        hiltRule.inject()

        // NOTA: No hacemos setContent aquí porque MainActivity ya lo hace al arrancar.
        // Así probamos la app tal cual es en realidad.
    }

    @Test
    fun verifyStartDestination() {
        // Al arrancar, deberíamos ver los botones de la pantalla principal
        composeTestRule.onNodeWithText("ENTRAR").assertExists()
        composeTestRule.onNodeWithText("SALIR").assertExists()
    }

    @Test
    fun navigateToSettings() {
        // Simulamos clic en "Config"
        composeTestRule.onNodeWithText("Config").performClick()

        // Verificamos que aparece el texto único de la pantalla de configuración
        composeTestRule.onNodeWithText("Configuración").assertExists()
        composeTestRule.onNodeWithText("Nombre Completo").assertExists()
    }

    @Test
    fun navigateToReports() {
        // Simulamos clic en "Reportes"
        composeTestRule.onNodeWithText("Reportes").performClick()

        // Verificamos que aparece el título de la pantalla de reportes
        composeTestRule.onNodeWithText("Reporte Mensual").assertExists()
    }
}