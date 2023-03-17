package vn.vunganyen.mobile.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import vn.vunganyen.mobile.R
import vn.vunganyen.mobile.data.model.VoteReq
import vn.vunganyen.mobile.databinding.ActivityMainBinding
import kotlin.properties.Delegates

class JokesActivity : AppCompatActivity(), JokesInterface {
    lateinit var binding: ActivityMainBinding
    lateinit var jokesPresenter: JokesPresenter
    var joke_id = 0
    companion object{
        var cookie : String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        jokesPresenter = JokesPresenter(this)
        binding.btnVoteAgain.visibility = View.GONE
        getData()
        setEvent()
    }

    fun getData(){
        jokesPresenter.getJokesRandom()
    }
    fun setEvent(){
        binding.btnLike.setOnClickListener{
            var voteReq = VoteReq(joke_id,"likes")
            jokesPresenter.votesJoke(voteReq)
        }
        binding.btnDislike.setOnClickListener{
            var voteReq = VoteReq(joke_id,"dislikes")
            jokesPresenter.votesJoke(voteReq)
        }
        binding.btnVoteAgain.setOnClickListener{
            cookie = ""
            binding.btnVoteAgain.visibility = View.GONE
            binding.btnLike.visibility = View.VISIBLE
            binding.btnDislike.visibility = View.VISIBLE
            getData()
        }
    }

    override fun jokesRes(id: Int, str: String) {
        joke_id = id
        binding.tvBodyJoke.text = str
        if(id == 0){
            binding.btnVoteAgain.visibility = View.VISIBLE
            binding.btnLike.visibility = View.GONE
            binding.btnDislike.visibility = View.GONE
        }
    }

    override fun message(str: String) {
        getData()
    }

}