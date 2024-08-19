package com.example.droidchat.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Left
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Right
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.droidchat.navigation.extemsion.slideInTo
import com.example.droidchat.navigation.extemsion.slideOutTo
import com.example.droidchat.ui.feature.signin.SignInRoute
import com.example.droidchat.ui.feature.splash.SplashRoute
import kotlinx.serialization.Serializable

@Serializable
object SplashRoute

@Serializable
object SignInRoute

@Serializable
object SignUpRoute

@Composable
fun ChatNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SplashRoute) {
        composable<SplashRoute>{
            SplashRoute(
                onNavigateToSignIn = {
                    navController.navigate(
                        route = SignInRoute,
                        navOptions = navOptions {
                            popUpTo(SplashRoute) {
                                inclusive = true
                            }
                        }
                    )
                }
            )
        }

        composable<SignInRoute> (
            enterTransition = {
                slideIntoContainer(
                    towards = Right,
                    animationSpec = tween(durationMillis = 300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = Left,
                    animationSpec = tween(durationMillis = 300)
                )
            }
        ){
            SignInRoute(
                navigateToSignUp = {
                    navController.navigate(SignUpRoute)
                }
            )
        }

        composable<SignUpRoute> (
            enterTransition = {
                this.slideInTo(Right)
            },
            exitTransition = {
                this.slideOutTo(Left)
            }
        ){
        }
    }
}
