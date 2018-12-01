package com.example.ama.viewer.data.model

import com.google.gson.annotations.SerializedName

data class GithubUser(
        val login: String?,
        val name: String?,
        @SerializedName("avatar_url")
        val avatar: String?,
        val company: String?,
        val blog: String?,
        val location: String?,
        val email: String?,
        val bio: String?)