package vn.vunganyen.mobile.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import vn.vunganyen.mobile.data.model.JokeRes
import vn.vunganyen.mobile.data.model.VoteRes

interface ApiJokeService {
    @GET("jokes/random")
    fun getJokesRandom(@Header("Cookie") cookie: String) : Call<JokeRes>

    object Api{
        val api: ApiJokeService by lazy { RetrofitSetting().retrofit.create(ApiJokeService::class.java) }
    }
}