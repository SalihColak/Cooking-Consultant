package cookingconsultant.app.gui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import cookingconsultant.app.R;
import cookingconsultant.app.fachlogik.grenz.EinkaufslisteGrenz;
import cookingconsultant.app.fachlogik.grenz.ZutatStateGrenz;
import cookingconsultant.app.fachlogik.impl.EinkaufslisteVerwaltungImpl;
import cookingconsultant.app.fachlogik.services.EinkaufslisteVerwaltung;
import cookingconsultant.app.gui.services.OnNoteListener;

public class EinkaufslisteZutatenAdapter extends RecyclerView.Adapter<EinkaufslisteZutatenAdapter.ViewHolder> {

    private EinkaufslisteGrenz einkaufslisteGrenz;
    private Context context;
    private OnNoteListener onNoteListener;
    private List<String> mengeList;

    public EinkaufslisteZutatenAdapter(EinkaufslisteGrenz einkaufslisteGrenz, List<String> mengeList,Context context, OnNoteListener onNoteListener) {
        this.einkaufslisteGrenz = einkaufslisteGrenz;
        this.context = context;
        this.onNoteListener = onNoteListener;
        this.mengeList = mengeList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.einkaufsliste_zutaten_adapter,parent,false);
        ViewHolder viewHolder = new ViewHolder(view,onNoteListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final LinearLayout background = holder.background;
        final TextView menge = holder.menge;
        final TextView name = holder.name;
        final ImageView imageView = holder.imageView;

        menge.setText(mengeList.get(position)+" "+einkaufslisteGrenz.getZutatStateList().get(position).getZutatGrenz().getEinheit());
        name.setText(einkaufslisteGrenz.getZutatStateList().get(position).getZutatGrenz().getName());

        if(einkaufslisteGrenz.getZutatStateList().get(position).isStatus()){
            imageView.setImageResource(R.drawable.ic_done_black_24dp);
            menge.setPaintFlags(menge.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            name.setPaintFlags(name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            background.setBackgroundColor(background.getResources().getColor(R.color.card_background6));
        }

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menge.getPaintFlags() == (menge.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG)){ //noch zu kaufen
                    background.setBackgroundColor(background.getResources().getColor(R.color.colorAccent));
                    menge.setPaintFlags(menge.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    name.setPaintFlags(name.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    final ChangeState changeState1 = new ChangeState();
                    changeState1.execute(einkaufslisteGrenz.getZutatStateList().get(position).getId(),0);
                }else { //gekauft
                    imageView.setImageResource(R.drawable.ic_done_black_24dp);
                    menge.setPaintFlags(menge.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    name.setPaintFlags(name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    background.setBackgroundColor(background.getResources().getColor(R.color.card_background6));
                    final ChangeState changeState2 = new ChangeState();
                    changeState2.execute(einkaufslisteGrenz.getZutatStateList().get(position).getId(),1);
                }

            }
        });

        /*if(position % 2 == 0){
            background.setBackgroundColor(background.getResources().getColor(R.color.card_background5));
        }
        else {
            background.setBackgroundColor(background.getResources().getColor(R.color.white));
        }*/
    }

    @Override
    public int getItemCount() {
        if(einkaufslisteGrenz == null) return 0;
        return einkaufslisteGrenz.getZutatStateList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private OnNoteListener onNoteListener;
        private LinearLayout background;
        private TextView menge;
        private TextView name;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            background = itemView.findViewById(R.id.einkaufsliste_zutat_background);
            menge = itemView.findViewById(R.id.menge_zutat_id);
            name = itemView.findViewById(R.id.zutat_name_id);
            imageView = itemView.findViewById(R.id.image_status_id);

            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    private class ChangeState extends AsyncTask<Integer,Void,Void>{

        @Override
        protected Void doInBackground(Integer... ints) {
            boolean state = false;
            if(ints[1] == 1)state = true;
            EinkaufslisteVerwaltung einkaufslisteVerwaltung = new EinkaufslisteVerwaltungImpl();
            try {
                einkaufslisteVerwaltung.changeZutatState(ints[0],state);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
