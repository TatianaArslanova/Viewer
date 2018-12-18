package com.example.ama.viewer.data.entity

import com.example.ama.viewer.data.api.dto.GithubUserDTO
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class GithubUser(
        @PrimaryKey
        var login: String?,
        var avatar: String?,
        var name: String?,
        var company: String?,
        var blog: String?,
        var location: String?,
        var email: String?,
        var bio: String?
) : RealmObject() {
    constructor() : this(null, null, null, null, null, null, null, null)
    constructor(user: GithubUserDTO) : this(
            user.login,
            user.avatar,
            user.name,
            user.company,
            user.blog,
            user.location,
            user.email,
            user.bio)
}