package com.example.pokemon_application.presentation.list

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pokemon_application.data.local.PokiAPI
import com.example.pokemon_application.domain.entity.Pokemon
import com.example.pokemon_application.domain.entity.RestPokemonResponse
import com.example.pokemon_application.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PokemonListActivity : AppCompatActivity() {
    private val BASE_URL : String = "https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/master/"


    private var recyclerView: RecyclerView? = null
    private var mAdapter: ListAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var mySwipeRefreshLayout: SwipeRefreshLayout? = null
    private var sharedPreferences: SharedPreferences? = null
    private var gson: Gson? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        sharedPreferences = getSharedPreferences("Pokemon_application", Context.MODE_PRIVATE)
        gson = GsonBuilder()
            .setLenient()
            .create()

        //refreshCall()

        val pokemonList: MutableList<Pokemon?>? = getDataFromCache()
        if (pokemonList != null){
            showList(pokemonList)
        }else{
            makeApiCall()
        }

    }

    private fun getDataFromCache(): MutableList<Pokemon?>? {
        val jsonPokemon = sharedPreferences!!.getString("jsonPokemonList", null)
        return if (jsonPokemon == null) {
            null
        } else {
            val listType = object : TypeToken<MutableList<Pokemon?>?>() {}.type
            gson!!.fromJson(jsonPokemon, listType)
        }
    }



    fun showList(pokemonList: MutableList<Pokemon?>?){
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        recyclerView!!.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = layoutManager


        mAdapter = ListAdapter(
            pokemonList,
            applicationContext,
            object : ListAdapter.OnItemClickListener {
                override fun onItemClick(item: Pokemon?) {
                     navigateToDetails(item)
                }
            })
        recyclerView!!.adapter = mAdapter
        Toast.makeText(getApplicationContext(), "List is here !", Toast.LENGTH_SHORT).show()
    }



    fun makeApiCall() {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val pokiApi: PokiAPI = retrofit.create(PokiAPI::class.java)

        val call: Call<RestPokemonResponse> = pokiApi.breedResponse()
        call.enqueue(object : Callback<RestPokemonResponse> {
            override fun onResponse(
                call: Call<RestPokemonResponse?>?,
                response: Response<RestPokemonResponse?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val pokemonList: MutableList<Pokemon?>? = response.body()!!.getInformation()
                    showList(pokemonList)
                    saveList(pokemonList)
                }
            }
            override fun onFailure(call: Call<RestPokemonResponse?>?, t: Throwable?) {
                showError()
            }
        })
    }
    private fun saveList(pokemonList: MutableList<Pokemon?>?){
        val jsonString: String = gson!!.toJson(pokemonList)

        sharedPreferences
            ?.edit()
            ?.putString("PokemonList", jsonString)
            ?.apply()
        Toast.makeText(getApplicationContext(), "List saved !", Toast.LENGTH_SHORT).show()
    }

    private fun showError(){
        AlertDialog.Builder(this)
            .setTitle("API Error !")
            .setMessage("Loading failed, please check network connection.")
            .setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()
            }
            .show()
        Toast.makeText(getApplicationContext(), "List not saved !!!!", Toast.LENGTH_SHORT).show()
    }

    fun navigateToDetails(pokemon: Pokemon?) {
        AlertDialog.Builder(this)
            .setTitle(pokemon?.height.toString())
            .setMessage(pokemon?.weight.toString())
            .setPositiveButton("OK") { dialog, which -> dialog.dismiss()
            }
            .show()
    }

}



