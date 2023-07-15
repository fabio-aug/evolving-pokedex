package com.ifmg.evolvingpokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ifmg.evolvingpokedex.classes.getEvolution.Evolution;
import com.ifmg.evolvingpokedex.classes.getEvolution.PokemonEvolution;
import com.ifmg.evolvingpokedex.classes.getPokemon.Pokemon;
import com.ifmg.evolvingpokedex.classes.getSpecie.PokemonSpecies;
import com.ifmg.evolvingpokedex.databinding.ActivityMainBinding;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    public void openViewEvolution(String text) {
        Intent intent = new Intent(this, ViewPokemonActivity.class);
        intent.putExtra(ViewPokemonActivity.LIST_POKEMON_NAMES, text);
        startActivity(intent);
    }

    private String getNames(Evolution[] evolves) {
        StringBuilder compose = new StringBuilder(this.binding.nomePokemon.getText().toString());
        for (Evolution evo1 : evolves) {
            compose.append("\n").append(evo1.species.name);
            for (Evolution evo2 : evo1.evolves_to) {
                compose.append("\n").append(evo2.species.name);
            }
        }
        return compose.toString();
    }

    public String getEvolutions(int idEvolutionChain) throws IOException {
        PokedexApplication app = (PokedexApplication) getApplication();
        PokemonEvolution evolution = app.getPokemonRepo().getEvolutionChainById(idEvolutionChain);

        return getNames(evolution.chain.evolves_to);
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

    public void fetchData(String pokemonName) {
        PokedexApplication app = (PokedexApplication) getApplication();

        app.executor.execute(() -> {
            try {
                int idSpecie = getPokemon(pokemonName);

                if (idSpecie != -1) {
                    int idEvolutionChain = getSpecie(idSpecie);

                    if (idEvolutionChain != -1) {
                        openViewEvolution(getEvolutions(idEvolutionChain));
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void onClickButton() {
        String pokemonName = this.binding.nomePokemon.getText().toString();
        fetchData(pokemonName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.binding.button.setOnClickListener((v) -> onClickButton());
    }
}