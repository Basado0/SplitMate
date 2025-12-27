package com.example.splitmate

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.splitmate.screens.HistoryScreen
import com.example.splitmate.screens.HomeScreen
import com.example.splitmate.screens.InputScreen
import com.example.splitmate.screens.ResultScreen
import com.example.splitmate.viewModel.SplitViewModel

sealed class SplitMateRoute(val route: String){

    data object HOME: SplitMateRoute("home")
    data object INPUT : SplitMateRoute("input")
    data object RESULT : SplitMateRoute("result/{id}"){
        const val ARG_ID = "id"
        fun createRoute(id : Long) : String = "result/$id"
    }
    data object HISTORY: SplitMateRoute(route = "history")
}

@Composable
fun SplitMateApp(){
    val holder : SplitViewModel = viewModel()
    val navController: NavHostController = rememberNavController()

    NavHost(navController=navController, startDestination = SplitMateRoute.HOME.route){
        composable(route = SplitMateRoute.HOME.route){

            HomeScreen(
                onNavigateToInput = {navController.navigate(SplitMateRoute.INPUT.route)},
                onNavigateToHistory = {navController.navigate(SplitMateRoute.HISTORY.route)}
            )
        }

        composable(route = SplitMateRoute.INPUT.route){
            val uiState = holder.uiState

            InputScreen(
                state = uiState,
                isInputValid = holder.isInputValid(),
                onAmountChange = holder::updateTotalAmount,
                onPeopleCountChange = holder::updatePeopleCount,
                onTipChange = holder::updateTipPercent,
                onCreateNewCalculation = holder::createNewCalculation,
                onNavigateResult = {calcId -> navController.navigate(SplitMateRoute.RESULT.createRoute(calcId))},
                onNavigateBack = {navController.navigate(SplitMateRoute.HOME.route)}
            )

        }

        composable(
            route = SplitMateRoute.RESULT.route,
            arguments = listOf(
                navArgument(SplitMateRoute.RESULT.ARG_ID){
                    type = NavType.LongType
                }
            )
        ){
            backStackEntry ->
            val calcId = backStackEntry.arguments?.getLong(SplitMateRoute.RESULT.ARG_ID) ?: 0L
            val calculation = holder.getCalculationById(calcId)

            ResultScreen(
                calculation = calculation,
                onNavigateInput = {navController.navigate(SplitMateRoute.INPUT.route)},
                onNavigateHome = {
                    holder.resetState()
                    navController.navigate(route = SplitMateRoute.HOME.route)
                }
            )
        }

        composable(route = SplitMateRoute.HISTORY.route){
            HistoryScreen(
                calculations = holder.uiState.history,
                onNavigateResult = { calcId ->
                    navController.navigate(SplitMateRoute.RESULT.createRoute(calcId))
                },
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}