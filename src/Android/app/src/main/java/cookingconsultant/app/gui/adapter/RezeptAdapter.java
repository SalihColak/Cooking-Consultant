package cookingconsultant.app.gui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cookingconsultant.app.R;
import cookingconsultant.app.fachlogik.grenz.RezeptGrenz;
import cookingconsultant.app.fachlogik.services.Constants;
import cookingconsultant.app.gui.services.OnNoteListener;

public class RezeptAdapter extends RecyclerView.Adapter<RezeptAdapter.ViewHolder> {

    private OnNoteListener onNoteListener;
    private List<RezeptGrenz> list;
    private Context mContext;

    public RezeptAdapter(Context context, List<RezeptGrenz>list, OnNoteListener onNoteListener){
        mContext = context;
        this.list = list;
        this.onNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.rezepte_anzeige_list_adapter,parent,false);
        ViewHolder viewHolder = new ViewHolder(view,onNoteListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ImageView image = holder.item_image;
        TextView name = holder.item_name;
        CardView cardView = holder.rezept_background;
        TextView kochzeit = holder.item_kochzeit;
        String bild = list.get(position).getBild();
        Picasso.get().load(Constants.IP_SERVER+bild).into(image);
        name.setText(list.get(position).getName());
        kochzeit.setText(list.get(position).getKochzeit());


        switch (position%4){
            case 0: cardView.setCardBackgroundColor(cardView.getResources().getColor(R.color.card_background0));
            break;
            case 1: cardView.setCardBackgroundColor(cardView.getResources().getColor(R.color.card_background1));
            break;
            case 2: cardView.setCardBackgroundColor(cardView.getResources().getColor(R.color.card_background2));
            break;
            case 3: cardView.setCardBackgroundColor(cardView.getResources().getColor(R.color.card_background3));
        }


    }

    @Override
    public int getItemCount() {
        if (list== null)return 0;
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView rezept_background;
        private ImageView item_image;
        private TextView item_name,item_kochzeit;
        private OnNoteListener onNoteListener;

            public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
                super(itemView);
                item_kochzeit = itemView.findViewById(R.id.kochzeit_id);
                rezept_background = itemView.findViewById(R.id.cardview_parent_id);
                item_image = itemView.findViewById(R.id.rezept_bild_id);
                item_name = itemView.findViewById(R.id.rezept_name_id);

                this.onNoteListener = onNoteListener;
                itemView.setOnClickListener(this);
            }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }
}
