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
    public void teste(String pokemonName) {
        PokedexApplication app = (PokedexApplication) getApplication();

        app.executor.execute(() -> {
            try {
                int idSpecie = -1;
                int idEvolutionChain = -1;
                Pokemon pokemon = app.getPokemonRepo().getPokemonByName(pokemonName);
                String[] aux1 = pokemon.species.url.split("/");
                if (aux1.length >= 1) {
                    idSpecie = Integer.parseInt(aux1[aux1.length - 1]);

                    if (idSpecie != - 1) {
                        PokemonSpecies specie = app.getPokemonRepo().getSpecieById(idSpecie);
                        String[] aux2 = specie.evolutionChain.url.split("/");

                        if (aux2.length >= 1) {
                            idEvolutionChain = Integer.parseInt(aux2[aux2.length - 1]);

                            if (idEvolutionChain != - 1) {
                                PokemonEvolution evolution = app.getPokemonRepo().getEvolutionChainById(idEvolutionChain);
                                for (Evolution evo : evolution.chain.evolves_to) {
                                    Log.d("POKEDEX-PRINT", evo.species.name);
                                }
                            }
                        }
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

        teste("eevee");
    }
}