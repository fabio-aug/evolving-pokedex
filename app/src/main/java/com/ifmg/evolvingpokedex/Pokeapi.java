package com.ifmg.evolvingpokedex;

import com.ifmg.evolvingpokedex.classes.getEvolution.PokemonEvolution;
import com.ifmg.evolvingpokedex.classes.getPokemon.Pokemon;
import com.ifmg.evolvingpokedex.classes.getSpecie.PokemonSpecies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Pokeapi {

    @GET("pokemon/{name}")
    Call<Pokemon> getPokemonByName(@Path("name") String pokemonName);

    @GET("pokemon-species/{id}")
    Call<PokemonSpecies> getSpecieById(@Path("id") int specieId);

    @GET("evolution-chain/{id}")
    Call<PokemonEvolution> getEvolutionChainById(@Path("id") int evolutionChainId);
}
