package com.example.apiapp.model

import com.example.apiapp.R

sealed class TipPage(
    val title: String, val description: String, val image: Int
) {
    data object FirstPage : TipPage(
        title = "Welcome to CineSpectra!",
        description = "Explore the latest movies, reserve the perfect seats, and experience the cinema in a whole new way.",
        image = R.drawable.film_rolls_amico
    )

    data object SecondPage : TipPage(
        title = "Quick and Easy Booking",
        description = "Reserve your favorite seat in just a few steps. No waiting, no hassle!",
        image = R.drawable.home_cinema_amico
    )

    data object ThirdPage : TipPage(
        title = "Tailored Just for You",
        description = "Personalize your experience! With movie recommendations and exclusive offers, enjoy the cinema your way.",
        image = R.drawable.horror_movie_amico
    )

    data object FourthPage : TipPage(
        title = "Experience the cinema",
        description = "Experience the cinema in a whole new way.",
        image = 0
    )

}