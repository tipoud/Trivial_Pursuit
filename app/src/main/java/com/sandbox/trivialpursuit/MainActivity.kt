package com.sandbox.trivialpursuit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sandbox.presentation.CategoryScreen
import com.sandbox.presentation.CategoryViewModel
import com.sandbox.trivialpursuit.Route.Categories
import com.sandbox.trivialpursuit.Route.Profile
import com.sandbox.trivialpursuit.Route.StartPlay
import com.sandbox.trivialpursuit.ui.theme.TrivialPursuitTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel


// https://opentdb.com/api_config.php
// https://medium.com/@mohammedkhudair57/mvi-architecture-pattern-in-android-0046bf9b8a2e

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)


val topLevelRoutes = listOf(
    TopLevelRoute("Play", StartPlay, Icons.Filled.PlayArrow),
    TopLevelRoute("Categories", Categories, Icons.Filled.List),
    TopLevelRoute("Profile", Profile, Icons.Filled.AccountCircle)
)

@Serializable
sealed class Route(val name: String) {

    @Serializable
    data object StartPlay : Route("StartPlay")

    @Serializable
    data object Categories : Route("Categories")

    @Serializable
    data object Profile : Route("Profile")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrivialPursuitTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = { BottomNavBar(navController, topLevelRoutes) }
                ) { innerPadding ->
                    BottomNavGraph(navController, innerPadding)
                }
            }
        }
    }
}


@Composable
fun BottomNavBar(navController: NavController, items: List<TopLevelRoute<out Route>>) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { topLevelRoute ->
            NavigationBarItem(
                icon = { Icon(topLevelRoute.icon, contentDescription = topLevelRoute.name) },
                label = { Text(topLevelRoute.name) },
                selected = currentDestination?.hierarchy?.any { it.hasRoute(topLevelRoute.route::class) } == true,
                onClick = {
                    navController.navigate(topLevelRoute.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

// Navigation Graph
@Composable
fun BottomNavGraph(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(navController, startDestination = StartPlay) {
        composable<StartPlay> { backStackEntry ->
            val play = backStackEntry.toRoute<StartPlay>()
            PlayScreen(
                play = play,
                onNavigateToCategories = {
                    navController.navigate(route = Categories)
                }
            )
        }
        composable<Categories> {

            val viewModel = koinViewModel<CategoryViewModel>()
            CategoryScreen(viewModel)
        }
        composable<Profile> { backStackEntry ->
            val profile = backStackEntry.toRoute<Profile>()
            ProfileScreen(
                profile = profile,
                onNavigateToPlay = {
                    navController.navigate(
                        route = StartPlay
                    )
                }
            )
        }
    }
}


class GetBasketUseCase() {

    operator fun invoke(): Flow<String> {
        return flowOf("BASKET")
    }

}


class GetUserProfileUseCase() {

    operator fun invoke(): Flow<String> {
        return flowOf("USERID")
    }
}

class AddToBasketUseCase {

    operator fun invoke(productId: String) {
        Log.d("AddToBasket", "Adding $productId to basket")
    }
}

@Immutable
data class BasketViewState(
    val basketId: String,
    val customerId: String
)

class BasketViewModel(
    val getBasket: GetBasketUseCase = GetBasketUseCase(),
    val getUser: GetUserProfileUseCase = GetUserProfileUseCase(),
    val addToBasket: AddToBasketUseCase = AddToBasketUseCase(),
) : ViewModel() {


    private val _viewState = MutableStateFlow(
        BasketViewState(
            "", ""
        )
    )

    fun onIntent(intent: String) {
        if (intent == "ADDTOBASKET") {
            addToBasket("1")
        }
    }


    init {
        viewModelScope.launch {
            getBasket()
                .combine(getUser()) { basket, user ->
                    BasketViewStateFactory.create(basket, user)
                }
                .collectLatest {
                    _viewState.update {
                        it
                    }
                }
        }
    }
}

object BasketViewStateFactory {

    fun create(basket: String, user: String): BasketViewState {
        return BasketViewState(basket, user)
    }
}


@Composable
fun PlayScreen(play: StartPlay, onNavigateToCategories: () -> Unit, vm: BasketViewModel = BasketViewModel()) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Playing game ID: ${play}", style = MaterialTheme.typography.bodyLarge)
        Button(onClick = { onNavigateToCategories() }, modifier = Modifier.padding(top = 16.dp)) {
            Text("Go to Categories")
        }
    }
}


// Profile Screen Composable
@Composable
fun ProfileScreen(profile: Profile, onNavigateToPlay: (StartPlay) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Profile for $profile", style = MaterialTheme.typography.bodyLarge)
        Button(
            onClick = { onNavigateToPlay(StartPlay) },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Go to Play")
        }
    }
}