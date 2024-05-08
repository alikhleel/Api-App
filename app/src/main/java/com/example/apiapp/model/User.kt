package com.example.apiapp.model

import com.google.gson.annotations.SerializedName


data class UserTokenResponse(
    @SerializedName("success") var isSuccess: Boolean? = null,
    @SerializedName("expires_at") var expiresAt: String? = null,
    @SerializedName("request_token") var requestToken: String? = null,
    @SerializedName("session_id") var sessionId: String? = null,
)


data class UserAccount(
    @SerializedName("avatar") var avatar: Avatar? = Avatar(),
    @SerializedName("id") var id: Int? = null,
    @SerializedName("iso_639_1") var iso6391: String? = null,
    @SerializedName("iso_3166_1") var iso31661: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("include_adult") var includeAdult: Boolean? = null,
    @SerializedName("username") var username: String? = null
)

data class Gravatar(
    @SerializedName("hash") var hash: String? = null
)

data class Avatar(

    @SerializedName("gravatar") var gravatar: Gravatar? = Gravatar(),
    @SerializedName("tmdb") var tmdb: Tmdb? = Tmdb()
)

data class Tmdb(
    @SerializedName("avatar_path") var avatarPath: String? = null
)
