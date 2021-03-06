package jwwu.com.dotabuddy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import jwwu.com.dotabuddy.adapters.Lexikon.LexikonChildWrapper;
import jwwu.com.dotabuddy.R;
import jwwu.com.dotabuddy.adapters.Lexikon.LexikonParentListItem;
import jwwu.com.dotabuddy.adapters.Lexikon.LexikonAdapter;
import jwwu.com.dotabuddy.dota_logic.DotaSingleton;
import jwwu.com.dotabuddy.dota_logic.Hero;
import jwwu.com.dotabuddy.dota_logic.HeroAbility;
import jwwu.com.dotabuddy.dota_logic.Stat;

public class LexikonActivity extends AppCompatActivity {

    private LexikonAdapter mAdapter;

    public static final String LEXIKONHEROPREF = "lexhero";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lexikon);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setVisibility(View.INVISIBLE);



        Intent intent = getIntent();
        SharedPreferences settings = getSharedPreferences(MainActivity.SHAREDPREFS, 0);

        String heroName;
        if(intent.getStringExtra("hero") != null) {     //can be null if not called from HeroChoosterActivity
            heroName = intent.getStringExtra("hero");
            settings.edit().putString(LEXIKONHEROPREF,heroName).apply();
        }
        else {
            heroName = settings.getString(LEXIKONHEROPREF,"Abaddon");
        }


        DotaSingleton.getInstance().init(this);

        Hero hero = DotaSingleton.getInstance().getHero(heroName);
        ((ImageView) findViewById(R.id.imageView)).setImageBitmap(hero.mPortrait);
        setTitle(hero.mName);


        ArrayList<LexikonChildWrapper> stats = new ArrayList<>();
        for(Stat stat : hero.mHeroStats.getStatList()) {
            stats.add(new LexikonChildWrapper(stat));
        }
        LexikonParentListItem statsRecipe = new LexikonParentListItem("Stats",stats);

        ArrayList<LexikonChildWrapper> abilities = new ArrayList<>();
        for(HeroAbility hab : hero.mHeroAbilities) {
            abilities.add(new LexikonChildWrapper(hab));
        }
        LexikonParentListItem abilitiesRecipe = new LexikonParentListItem("Abilities",abilities);



        final List<LexikonParentListItem> recipes = new ArrayList<>();
        recipes.add(statsRecipe);
        recipes.add(abilitiesRecipe);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        mAdapter = new LexikonAdapter(this, recipes);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
