package cookingconsultant.app.gui;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cookingconsultant.app.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class RezeptFragment extends Fragment implements View.OnClickListener {

    View view;

    private List<String> rezeptartList = new ArrayList<>();
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
        fruehstueck.setOnClickListener(this);
        snack.setOnClickListener(this);
        abendessen.setOnClickListener(this);
        mittagessen.setOnClickListener(this);
        kaffeetafel.setOnClickListener(this);
        brunch.setOnClickListener(this);
        return view;
    }

    public void selectArt(View view){

    }

    @Override
    public void onClick(View v) {



        CircleImageView circleImageView = (CircleImageView)v;
        if(circleImageView.getCircleBackgroundColor() == Color.parseColor(BACKGROUND_COLOR)){
            rezeptartList.remove(circleImageView.getTag().toString());
            circleImageView.setActivated(false);
            circleImageView.setCircleBackgroundColor(Color.parseColor("#E4E4E4"));
            circleImageView.setBorderColor(Color.parseColor("#E4E4E4"));
        }
        else{
            rezeptartList.add(circleImageView.getTag().toString());
            circleImageView.setActivated(true);
            circleImageView.setBorderColor(Color.parseColor("#000000"));
            circleImageView.setCircleBackgroundColor(Color.parseColor(BACKGROUND_COLOR));
        }

        Toast.makeText(getContext(),rezeptartList.toString(),Toast.LENGTH_SHORT).show();

        /*if(circleImageView.isActivated()){
            Toast.makeText(getContext(),v.getTag().toString()+" ist ausgewaehlt.",Toast.LENGTH_SHORT).show();
        }*/
    }
}
