package com.maryang.fastrxjava.data.repository

import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.maryang.fastrxjava.data.db.AppDatabase
import com.maryang.fastrxjava.data.request.CreateIssueRequest
import com.maryang.fastrxjava.data.source.ApiManager
import com.maryang.fastrxjava.entity.GithubRepo
import com.maryang.fastrxjava.entity.Issue
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GithubRepository {

    private val api = ApiManager.githubApi
    private val dao = AppDatabase.get().githubDao()

    fun save(repo: GithubRepo) =
        dao.insert(repo)
            .subscribeOn(Schedulers.io())

    fun searchGithubRepos(q: String): Single<List<GithubRepo>> =
        api.searchRepos(q)
            .map {
                it.asJsonObject.getAsJsonArray("items")
            }.map {
                val type = object : TypeToken<List<GithubRepo>>() {}.type
                ApiManager.gson.fromJson(it, type) as List<GithubRepo>
            }.subscribeOn(Schedulers.io())

    fun checkStar(owner: String, repo: String): Completable =
        api.checkStar(owner, repo)
            .subscribeOn(Schedulers.io())

    fun star(owner: String, repo: String): Completable =
        api.star(owner, repo)
            .subscribeOn(Schedulers.io())

    fun unstar(owner: String, repo: String): Completable =
        api.unstar(owner, repo)
            .subscribeOn(Schedulers.io())

    fun issues(owner: String, repo: String): Single<List<Issue>> =
        api.issues(owner, repo)
            .subscribeOn(Schedulers.io())

    fun createIssue(owner: String, repo: String, title: String, body: String): Single<Issue> =
        api.createIssue(owner, repo, CreateIssueRequest(title, body))
            .subscribeOn(Schedulers.io())
}
