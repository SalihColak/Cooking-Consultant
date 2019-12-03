package cookingconsultant.app.gui.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cookingconsultant.app.R;
import cookingconsultant.app.gui.activities.ActivityRezept;
import de.hdodenhof.circleimageview.CircleImageView;

public class RezeptFragment extends Fragment implements View.OnClickListener {

    private View view;
    private boolean pressable = false;
    private Button search;

    private List<String> rezeptartList = new ArrayList<>();
    private List<String> rezeptAnlassList = new ArrayList<>();
    private List<String> rezeptPraeferenzList = new ArrayList<>();
    private final String BACKGROUND_COLOR = "#6BAD67";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.rezept_fragment,container,false);
        CircleImageView fruehstueck = (CircleImageView) view.findViewById(R.id.fruehstueck_id);
        CircleImageView snack = (CircleImageView) view.findViewById(R.id.snack_id);
        CircleImageView abendessen = (CircleImageView) view.findViewById(R.id.abendessen_id);
        CircleImageView mittagessen = (CircleImageView) view.findViewById(R.id.mittagessen_id);
        CircleImageView kaffeetafel = (CircleImageView) view.findViewById(R.id.kafeetafel_id);
        CircleImageView brunch = (CircleImageView) view.findViewById(R.id.brunch_id);

        CircleImageView familie = (CircleImageView) view.findViewById(R.id.familienessen_id);
        CircleImageView freunde = (CircleImageView) view.findViewById(R.id.freunde_id);
        CircleImageView feier = (CircleImageView) view.findViewById(R.id.feier_id);
        CircleImageView kindergeburtstag = (CircleImageView) view.findViewById(R.id.kindergeburtstag_id);
        CircleImageView geburtstag = (CircleImageView) view.findViewById(R.id.geburtstag_id);
        CircleImageView essenzuzweit = (CircleImageView) view.findViewById(R.id.essenzuzweit_id);

        CircleImageView italienisch = (CircleImageView) view.findViewById(R.id.italienisch_id);
        CircleImageView asiatisch = (CircleImageView) view.findViewById(R.id.asiatisch_id);
        CircleImageView orientalisch = (CircleImageView) view.findViewById(R.id.orientalisch_id);
        CircleImageView tuerkisch = (CircleImageView) view.findViewById(R.id.tuerkisch_id);
        CircleImageView deutsch = (CircleImageView) view.findViewById(R.id.deutsch_id);
        CircleImageView amerikanisch = (CircleImageView) view.findViewById(R.id.amerikanisch_id);
        CircleImageView indisch = (CircleImageView) view.findViewById(R.id.indisch_id);
        CircleImageView hausmannskost = (CircleImageView) view.findViewById(R.id.hausmannskost_id);
        CircleImageView international = (CircleImageView) view.findViewById(R.id.international_id);

        fruehstueck.setOnClickListener(this);
        snack.setOnClickListener(this);
        abendessen.setOnClickListener(this);
        mittagessen.setOnClickListener(this);
        kaffeetafel.setOnClickListener(this);
        brunch.setOnClickListener(this);

        familie.setOnClickListener(this);
        freunde.setOnClickListener(this);
        feier.setOnClickListener(this);
        kindergeburtstag.setOnClickListener(this);
        geburtstag.setOnClickListener(this);
        essenzuzweit.setOnClickListener(this);

        italienisch.setOnClickListener(this);
        asiatisch.setOnClickListener(this);
        orientalisch.setOnClickListener(this);
        tuerkisch.setOnClickListener(this);
        deutsch.setOnClickListener(this);
        amerikanisch.setOnClickListener(this);
        indisch.setOnClickListener(this);
        hausmannskost.setOnClickListener(this);
        international.setOnClickListener(this);

        search = view.findViewById(R.id.rezepte_suchen_id);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pressable) {
                    String query = "\"";
                    for(int i = 0; i< rezeptartList.size();i++){
                        if(i != rezeptartList.size()-1) {
                            query += rezeptartList.get(i) + "\",\"";
                        }else{
                            query += rezeptartList.get(i) + "\";\"";
                        }
                    }
                    for(int i = 0; i< rezeptAnlassList.size();i++){
                        if(i != rezeptAnlassList.size()-1) {
                            query += rezeptAnlassList.get(i) + "\",\"";
                        }else{
                            query += rezeptAnlassList.get(i) + "\";\"";
                        }
                    }
                    for(int i = 0; i< rezeptPraeferenzList.size();i++){
                        if(i != rezeptPraeferenzList.size()-1) {
                            query += rezeptPraeferenzList.get(i) + "\",\"";
                        }else{
                            query += rezeptPraeferenzList.get(i) + "\"";
                        }
                    }
                    Intent intent = new Intent(getContext(), ActivityRezept.class);
                    intent.putExtra("query",query);
                    startActivity(intent);
                }
            }
        });

        search.setBackgroundColor(search.getResources().getColor(R.color.grey));
        search.setClickable(false);
        return view;
    }

    public void selectArt(View view){

    }

    @Override
    public void onClick(View v) {

        CircleImageView circleImageView = (CircleImageView)v;
        if(circleImageView.getCircleBackgroundColor() == Color.parseColor(BACKGROUND_COLOR)){
            if(circleImageView.getId() == R.id.fruehstueck_id || circleImageView.getId() == R.id.brunch_id || circleImageView.getId() == R.id.kafeetafel_id ||
                    circleImageView.getId() == R.id.mittagessen_id || circleImageView.getId() == R.id.snack_id || circleImageView.getId() == R.id.abendessen_id) {
                rezeptartList.remove(circleImageView.getTag().toString());
            }else if (circleImageView.getId() == R.id.familienessen_id || circleImageView.getId() == R.id.freunde_id || circleImageView.getId() == R.id.feier_id ||
                    circleImageView.getId() == R.id.kindergeburtstag_id || circleImageView.getId() == R.id.geburtstag_id || circleImageView.getId() == R.id.essenzuzweit_id){
                rezeptAnlassList.remove(circleImageView.getTag().toString());
            }
            else if (circleImageView.getId() == R.id.italienisch_id || circleImageView.getId() == R.id.asiatisch_id || circleImageView.getId() == R.id.orientalisch_id ||
                    circleImageView.getId() == R.id.tuerkisch_id || circleImageView.getId() == R.id.deutsch_id || circleImageView.getId() == R.id.amerikanisch_id ||
                    circleImageView.getId() == R.id.indisch_id || circleImageView.getId() == R.id.hausmannskost_id || circleImageView.getId() == R.id.international_id){
                rezeptPraeferenzList.remove(circleImageView.getTag().toString());
            }
            circleImageView.setCircleBackgroundColor(Color.parseColor("#E4E4E4"));
            circleImageView.setBorderColor(Color.parseColor("#E4E4E4"));
        }
        else{
            if(circleImageView.getId() == R.id.fruehstueck_id || circleImageView.getId() == R.id.brunch_id || circleImageView.getId() == R.id.kafeetafel_id ||
                    circleImageView.getId() == R.id.mittagessen_id || circleImageView.getId() == R.id.snack_id || circleImageView.getId() == R.id.abendessen_id) {
                rezeptartList.add(circleImageView.getTag().toString());
            }else if (circleImageView.getId() == R.id.familienessen_id || circleImageView.getId() == R.id.freunde_id || circleImageView.getId() == R.id.feier_id ||
                    circleImageView.getId() == R.id.kindergeburtstag_id || circleImageView.getId() == R.id.geburtstag_id || circleImageView.getId() == R.id.essenzuzweit_id){
                rezeptAnlassList.add(circleImageView.getTag().toString());
            }
            else if (circleImageView.getId() == R.id.italienisch_id || circleImageView.getId() == R.id.asiatisch_id || circleImageView.getId() == R.id.orientalisch_id ||
                    circleImageView.getId() == R.id.tuerkisch_id || circleImageView.getId() == R.id.deutsch_id || circleImageView.getId() == R.id.amerikanisch_id ||
                    circleImageView.getId() == R.id.indisch_id || circleImageView.getId() == R.id.hausmannskost_id || circleImageView.getId() == R.id.international_id){
                rezeptPraeferenzList.add(circleImageView.getTag().toString());
            }

            circleImageView.setBorderColor(Color.parseColor("#ffffff"));
            circleImageView.setCircleBackgroundColor(Color.parseColor(BACKGROUND_COLOR));
        }
        //Kontrollausgabe
        Toast.makeText(getContext(),rezeptartList.toString()+" | "+rezeptAnlassList.toString()+" | "+rezeptPraeferenzList.toString(),Toast.LENGTH_SHORT).show();
        if(!rezeptartList.isEmpty() && !rezeptAnlassList.isEmpty() && !rezeptPraeferenzList.isEmpty()){
            pressable = true;
            search.setBackgroundColor(search.getResources().getColor(R.color.colorAccent));
            search.setClickable(true);
        }
        else{
            pressable = false;
            search.setBackgroundColor(search.getResources().getColor(R.color.grey));
            search.setClickable(false);
        }
    }


}
