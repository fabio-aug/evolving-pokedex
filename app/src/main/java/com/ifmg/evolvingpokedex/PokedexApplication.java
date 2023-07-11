package com.ifmg.evolvingpokedex;

import android.app.Application;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokedexApplication extends Application {

    public Executor executor = Executors.newFixedThreadPool(1);
    private PokemonRepo pokemonRepo;

    public PokemonRepo getPokemonRepo() {
        if (pokemonRepo != null) return pokemonRepo;

        Pokeapi api = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Pokeapi.class);

        pokemonRepo = new PokemonRepo(api);
        return pokemonRepo;
    }
}
