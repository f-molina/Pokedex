package com.example.pokedex

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.common.Common
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    //Broadcast handler
    private val showDetail = object:BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent!!.action!!.toString() == Common.KEY_ENABLE_HOME){
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                supportActionBar!!.setDisplayShowHomeEnabled(true)

                //replace fragment
                val detailPokemonFragment = PokemonDetail.getInstance()
                val position = intent.getIntExtra("position", -1)
                val bundle = Bundle()
                bundle.putInt("position", position)
                detailPokemonFragment.arguments = bundle

                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.list_pokemon_fragment, detailPokemonFragment)
                fragmentTransaction.addToBackStack("detail")
                fragmentTransaction.commitAllowingStateLoss()

                val pokemon = Common.pokemonList[position]
                toolbar.title = pokemon.name!!.capitalize()

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = "Pokedex"
        setSupportActionBar(toolbar)

        //register broadcast
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(showDetail, IntentFilter(Common.KEY_ENABLE_HOME))

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> {
                toolbar.title = "Pokedex"
                //clear all fragment in stack with name detail
                supportFragmentManager.popBackStack("detail", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                supportActionBar!!.setDisplayShowHomeEnabled(false)
                supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            }
        }
        return true
    }

}
