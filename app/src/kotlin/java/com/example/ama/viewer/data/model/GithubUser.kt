package com.example.ama.viewer.data.model

import com.google.gson.annotations.SerializedName

data class GithubUser(
        @SerializedName("avatar_url")
        val avatar: String?,

        @SerializedName("login")
        private val _login: String?,

        @SerializedName("name")
        private val _name: String?,

        @SerializedName("company")
        private val _company: String?,

        @SerializedName("blog")
        private val _blog: String?,

        @SerializedName("location")
        private val _location: String?,

        @SerializedName("email")
        private val _email: String?,

        @SerializedName("bio")
        private val _bio: String?) {

    val login: String
        get() = _login ?: NOT_SPECIFIED

    val name: String
        get() = _name ?: NOT_SPECIFIED

    val company: String
        get() = _company ?: NOT_SPECIFIED

    val location: String
        get() = _location ?: NOT_SPECIFIED

    val blog: String
        get() = _blog ?: NOT_SPECIFIED

    val email: String
        get() = _email ?: NOT_SPECIFIED

    val bio: String
        get() = _bio ?: NOT_SPECIFIED

    companion object {
        const val NOT_SPECIFIED = "Not specified"
    }
}