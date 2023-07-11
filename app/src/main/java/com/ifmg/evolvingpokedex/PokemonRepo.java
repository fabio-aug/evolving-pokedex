package com.ifmg.evolvingpokedex;

import com.ifmg.evolvingpokedex.classes.getEvolution.PokemonEvolution;
import com.ifmg.evolvingpokedex.classes.getPokemon.Pokemon;
import com.ifmg.evolvingpokedex.classes.getSpecie.PokemonSpecies;

import java.io.IOException;

public class PokemonRepo {
    private Pokeapi api;

    public PokemonRepo(Pokeapi api) {
        this.api = api;
    }

    public Pokemon getPokemonByName(String pokemonName) throws IOException {
        return api.getPokemonByName(pokemonName).execute().body();
    }

    public PokemonSpecies getSpecieById(int specieId) throws IOException {
        return api.getSpecieById(specieId).execute().body();
    }

    public PokemonEvolution getEvolutionChainById(int evolutionChainId) throws IOException {
        return api.getEvolutionChainById(evolutionChainId).execute().body();
    }
}
