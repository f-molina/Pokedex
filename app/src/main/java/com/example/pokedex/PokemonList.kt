package com.example.pokedex


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.adapters.PokemonAdapter
import com.example.pokedex.common.Common
import com.example.pokedex.api.PokemonService
import com.example.pokedex.api.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PokemonList : Fragment() {

    var compositeDisposable = CompositeDisposable()
    var iPokeService:PokemonService

    private lateinit var pokeRecycler: RecyclerView

    private  var offset:Int = 0
    private var aptoParaCargar:Boolean = false

    init{
        val retrofit = RetrofitClient.instance
        iPokeService = retrofit.create(PokemonService::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val itemView = inflater.inflate(R.layout.fragment_pokemon_list, container, false)

        initRecycler(itemView)
        fetchPokemon(offset)

        return itemView
    }

    private fun initRecycler(itemView: View){
        pokeRecycler = itemView.findViewById(R.id.rv_pokemon_list)
        pokeRecycler.setHasFixedSize(true)
        var layoutManager = GridLayoutManager(activity, 1)
        pokeRecycler.layoutManager = layoutManager

        /*pokeRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy>0){
                    var visibleItemCount = layoutManager.childCount
                    var totalItemCount = layoutManager.itemCount
                    var pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                    if(aptoParaCargar){
                        if((visibleItemCount+pastVisibleItems)>=totalItemCount){
                            aptoParaCargar = false
                            offset+=20
                            fetchPokemon(offset)
                        }
                    }
                }
            }
        })*/
    }

    private fun fetchPokemon(offset: Int) {
        compositeDisposable.add(iPokeService.listPokemon(807, offset)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ pokemonDex ->
                Common.pokemonList = pokemonDex.results!!
                val adapter = PokemonAdapter(activity!!, Common.pokemonList)

                pokeRecycler.adapter = adapter
            }
        )
    }
}
