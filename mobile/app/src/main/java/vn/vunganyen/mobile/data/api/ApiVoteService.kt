package vn.vunganyen.mobile.data.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import vn.vunganyen.mobile.data.model.VoteReq
import vn.vunganyen.mobile.data.model.VoteRes

interface ApiVoteService {
    @POST("jokes/vote")
    fun voteJokes(@Header("Cookie") cookie: String, @Body req : VoteReq ) : Call<VoteRes>

    object Api{
        val api: ApiVoteService by lazy { RetrofitSetting().retrofit.create(ApiVoteService::class.java) }
    }
}