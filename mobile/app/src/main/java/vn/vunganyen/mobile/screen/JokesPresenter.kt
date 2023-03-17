package vn.vunganyen.mobile.screen

import okhttp3.Headers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.mobile.data.api.ApiJokeService
import vn.vunganyen.mobile.data.api.ApiVoteService
import vn.vunganyen.mobile.data.model.JokeRes
import vn.vunganyen.mobile.data.model.VoteReq
import vn.vunganyen.mobile.data.model.VoteRes
import java.net.HttpCookie


class JokesPresenter {
    var jokesInterface : JokesInterface

    constructor(jokesInterface: JokesInterface) {
        this.jokesInterface = jokesInterface
    }

    fun getJokesRandom(){
        ApiJokeService.Api.api.getJokesRandom(JokesActivity.cookie).enqueue(object :Callback<JokeRes>{
            override fun onResponse(call: Call<JokeRes>, response: Response<JokeRes>) {
                if(response.isSuccessful){
                    val headers: Headers = response.headers()
                    val cookies: List<String> = headers.values("Set-Cookie")
                    JokesActivity.cookie = "userId="+getUserIdFromCookie(cookies[0]).toString()
                    var id = response.body()!!.id
                    var joke = response.body()!!.joke
                    jokesInterface.jokesRes(id,joke)
                }
            }

            override fun onFailure(call: Call<JokeRes>, t: Throwable) {
                println("error "+call)
                t.printStackTrace()
            }

        })
    }

    fun getUserIdFromCookie(cookie: String?): String? {
        var userId: String? = null
        if (cookie != null) {
            val cookies: List<HttpCookie> = HttpCookie.parse(cookie)
            for (httpCookie in cookies) {
                if (httpCookie.getName().equals("userId")) {
                    userId = httpCookie.getValue()
                    break
                }
            }
        }
        return userId
    }

    fun votesJoke(req : VoteReq){
        ApiVoteService.Api.api.voteJokes(JokesActivity.cookie,req).enqueue(object : Callback<VoteRes>{
            override fun onResponse(call: Call<VoteRes>, response: Response<VoteRes>) {
                if(response.isSuccessful){
                    var message = response.body()!!.messege
                    jokesInterface.message(message)
                }
            }

            override fun onFailure(call: Call<VoteRes>, t: Throwable) {
                println("error "+call)
                t.printStackTrace()
            }

        })
    }

}