package com.ifmg.evolvingpokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ifmg.evolvingpokedex.databinding.ActivityViewPokemonBinding;

public class ViewPokemonActivity extends AppCompatActivity {
    public static String LIST_POKEMON_NAMES = "ViewPokemonActivity_LIST_POKEMON_NAMES";
    private ActivityViewPokemonBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityViewPokemonBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        Intent intent = getIntent();
        String pokemonNames = intent.getStringExtra(LIST_POKEMON_NAMES);
        binding.pokemonNamesList.setText(pokemonNames);
    }
}