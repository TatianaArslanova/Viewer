package com.example.ama.viewer.data.repo

import com.example.ama.viewer.data.api.dto.GithubUserDTO
import com.example.ama.viewer.data.entity.GithubUser
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmConfiguration

class DBRepositoryImpl(
        private val configuration: RealmConfiguration
) : DBRepository {

    companion object {
        private const val PRIMARY_KEY = "login"
    }

    var realm: Realm? = null

    override fun getUserFromDb(login: String): Observable<GithubUserDTO> =
            Observable.defer {
                Observable.fromCallable<GithubUser>
                { realm?.where(GithubUser::class.java)?.equalTo(PRIMARY_KEY, login)?.findFirst() }
            }
                    .doOnSubscribe { tryToOpenRealm() }
                    .doFinally { this.tryToCloseRealm() }
                    .map { user -> GithubUserDTO(user) }
                    .subscribeOn(Schedulers.io())

    override fun saveUserToDb(user: GithubUserDTO): Completable =
            Completable.fromAction { save(GithubUser(user)) }
                    .doOnSubscribe { tryToOpenRealm() }
                    .doFinally { this.tryToCloseRealm() }
                    .subscribeOn(Schedulers.io())

    private fun save(user: GithubUser) {
        realm?.beginTransaction()
        realm?.insertOrUpdate(user)
        realm?.commitTransaction()
    }

    private fun tryToOpenRealm() {
        if (realm == null) {
            realm = Realm.getInstance(configuration)
        }
    }

    private fun tryToCloseRealm() {
        if (realm != null && !realm!!.isClosed) {
            realm!!.close()
            realm = null
        }
    }
}