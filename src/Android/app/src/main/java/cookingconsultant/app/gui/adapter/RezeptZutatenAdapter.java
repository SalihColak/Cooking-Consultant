package cookingconsultant.app.gui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cookingconsultant.app.R;
import cookingconsultant.app.fachlogik.grenz.ZutatGrenz;

public class RezeptZutatenAdapter extends RecyclerView.Adapter<RezeptZutatenAdapter.ViewHolder> {


    private List<String> mengeList;
    private List<ZutatGrenz> zutatGrenzList;
    private Context mContext;

    public RezeptZutatenAdapter(List<String> mengeList, List<ZutatGrenz> zutatGrenzList, Context mContext) {
        this.mengeList = mengeList;
        this.zutatGrenzList = zutatGrenzList;
        this.mContext = mContext;
    }

    public void setMengeList(List<String> mengeList) {
        this.mengeList = mengeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.rezept_zutaten_adapter,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView menge = holder.menge;
        TextView zutat = holder.zutat_name;
        LinearLayout background = holder.background;

        if(zutatGrenzList!=null){
            menge.setText(mengeList.get(position)+" "+zutatGrenzList.get(position).getEinheit());
            zutat.setText(zutatGrenzList.get(position).getName());
        }

        if(position % 2 == 0){
            background.setBackgroundColor(background.getResources().getColor(R.color.card_background5));
        }
        else {
            background.setBackgroundColor(background.getResources().getColor(R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        if (zutatGrenzList == null) return 0;
        return zutatGrenzList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout background;
        private TextView menge,zutat_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            zutat_name = itemView.findViewById(R.id.zutat_id);
            background = itemView.findViewById(R.id.zutat_background);
            menge = itemView.findViewById(R.id.menge_id);

        }
    }
}
