package cookingconsultant.app.gui.activities;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import cookingconsultant.app.R;
import cookingconsultant.app.gui.adapter.FirstStartAdapter;
import cookingconsultant.app.gui.entities.Model;

public class ActivityFirstStart extends AppCompatActivity {

    ViewPager viewPager;
    FirstStartAdapter adapter;
    List<Model> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_first_start);

        models = new ArrayList<>();
        models.add(new Model(R.drawable.gericht, "Rezpte durchsuchen", "Finde deine Lieblingsrezepte durch Angabe von Art, Anlass und Präferenzen"));
        models.add(new Model(R.drawable.einkaufsliste_start, "Einkaufsliste erstellen", "Lass dir automatisch eine Einkaufsliste generieren und verschwende keine Zeit"));
        models.add(new Model(R.drawable.location, "Shops finden", "Finde sofort das nächste Lebensmittelgeschäft in deiner Gegend"));
        models.add(new Model(R.drawable.lexikon, "Kochlexikon entdecken", "Erweitere dein Wissen übers Kochen."));

        adapter = new FirstStartAdapter(models, this);

        viewPager = findViewById(R.id.viewPagerStart);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130, 0, 130, 0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.card_background5),
                getResources().getColor(R.color.card_background1),
                getResources().getColor(R.color.card_background2),
                getResources().getColor(R.color.card_background3)
        };

        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < (adapter.getCount() -1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(

                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                }

                else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void startApp(View view) {
        Intent intent = new Intent(this,ActivityLogin.class);
        SharedPreferences sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstUse",false);
        editor.commit();
        startActivity(intent);
        finish();
    }
}
