package com.ifmg.evolvingpokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.ifmg.evolvingpokedex.classes.getEvolution.Evolution;
import com.ifmg.evolvingpokedex.classes.getEvolution.PokemonEvolution;
import com.ifmg.evolvingpokedex.classes.getPokemon.Pokemon;
import com.ifmg.evolvingpokedex.classes.getSpecie.PokemonSpecies;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public int getPokemon(String pokemonName) throws IOException {
        int idSpecie = -1;

        PokedexApplication app = (PokedexApplication) getApplication();

        Pokemon pokemon = app.getPokemonRepo().getPokemonByName(pokemonName);
        String[] aux = pokemon.species.url.split("/");

        if (aux.length >= 1) {
            idSpecie = Integer.parseInt(aux[aux.length - 1]);
        }

        return idSpecie;
    }

    public int getSpecie(int idSpecie) throws IOException {
        int idEvolutionChain = -1;

        PokedexApplication app = (PokedexApplication) getApplication();

        PokemonSpecies specie = app.getPokemonRepo().getSpecieById(idSpecie);
        String[] aux = specie.evolutionChain.url.split("/");

        if (aux.length >= 1) {
            idEvolutionChain = Integer.parseInt(aux[aux.length - 1]);
        }

        return idEvolutionChain;
    }

    public void getEvolutions(int idEvolutionChain) throws IOException {
        PokedexApplication app = (PokedexApplication) getApplication();
        PokemonEvolution evolution = app.getPokemonRepo().getEvolutionChainById(idEvolutionChain);
        for (Evolution evo : evolution.chain.evolves_to) {
            Log.d("POKEDEX-PRINT", evo.species.name);
        }
    }

    public void fetchData(String pokemonName) {
        PokedexApplication app = (PokedexApplication) getApplication();

        app.executor.execute(() -> {
            try {
                int idSpecie = getPokemon(pokemonName);

                if (idSpecie != -1) {
                    int idEvolutionChain = getSpecie(idSpecie);

                    if (idEvolutionChain != -1) {
                        getEvolutions(idEvolutionChain);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetchData("mudkip");
    }
}