package com.example.casasapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.casasapp.ui.CasaViewModel
import com.example.casasapp.ui.pantallas.PantallaDetalle
import com.example.casasapp.ui.pantallas.PantallaFormulario
import com.example.casasapp.ui.pantallas.PantallaGaleria
import com.example.casasapp.ui.pantallas.PantallaInicio

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel: CasaViewModel = viewModel()

            NavHost(navController = navController, startDestination = "inicio") {
                composable("inicio") {
                    PantallaInicio(navController)
                }
                composable("galeria") {
                    PantallaGaleria(navController, viewModel)
                }
                composable(
                    route = "formulario?id={id}",
                    arguments = listOf(navArgument("id") { defaultValue = 0 })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getInt("id") ?: 0
                    PantallaFormulario(navController, viewModel, id)
                }
                composable(
                    route = "detalle/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getInt("id") ?: 0
                    // AQUI ESTÁ EL CAMBIO: Pasamos también 'navController'
                    PantallaDetalle(id, viewModel, navController)
                }
            }
        }
    }
}