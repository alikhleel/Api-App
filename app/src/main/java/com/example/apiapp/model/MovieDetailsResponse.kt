package com.example.apiapp.model

import com.google.gson.annotations.SerializedName


data class MovieDetailsResponse(
    @SerializedName("adult") val adult: Boolean? = null,
    @SerializedName("backdrop_path") val backdropPath: String? = null,
    @SerializedName("genres") val genres: List<Genres>? = null,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("imdb_id") val imdbId: String? = null,
    @SerializedName("origin_country") val originCountry: List<String>? = null,
    @SerializedName("original_language") val originalLanguage: String? = null,
    @SerializedName("original_title") val originalTitle: String? = null,
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("popularity") val popularity: Double? = null,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompanies>? = null,
    @SerializedName("production_countries") val productionCountries: List<ProductionCountries>? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("revenue") val revenue: Int? = null,
    @SerializedName("runtime") val runtime: Int? = null,
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguages>? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("tagline") val tagline: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("video") val video: Boolean? = null,
    @SerializedName("vote_average") val voteAverage: Double? = null,
    @SerializedName("vote_count") val voteCount: Int? = null
)


data class ProductionCompanies(

    @SerializedName("id") val id: Int? = null,
    @SerializedName("logo_path") val logoPath: String,
    @SerializedName("name") val name: String,
    @SerializedName("origin_country") val originCountry: String
)


data class ProductionCountries(
    @SerializedName("iso_3166_1") val isoName: String, @SerializedName("name") val name: String
)


data class SpokenLanguages(
    @SerializedName("english_name") val englishName: String,
    @SerializedName("iso_639_1") val isoName: String,
    @SerializedName("name") val name: String
)


data class Genres(
    @SerializedName("id") val id: Int, @SerializedName("name") val name: String
)