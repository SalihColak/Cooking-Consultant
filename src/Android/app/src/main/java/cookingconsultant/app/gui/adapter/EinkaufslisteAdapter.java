package cookingconsultant.app.gui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cookingconsultant.app.R;
import cookingconsultant.app.fachlogik.grenz.EinkaufslisteGrenz;
import cookingconsultant.app.gui.services.OnNoteListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class EinkaufslisteAdapter extends RecyclerView.Adapter<EinkaufslisteAdapter.ViewHolder> {

    private Context mContext;
    private List<EinkaufslisteGrenz> einkaufslisteGrenzList;
    private OnNoteListener onNoteListener;


    public EinkaufslisteAdapter(Context mContext, List<EinkaufslisteGrenz> einkaufslisteGrenzList, OnNoteListener onNoteListener) {
        this.mContext = mContext;
        this.einkaufslisteGrenzList = einkaufslisteGrenzList;
        this.onNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public EinkaufslisteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.einkaufliste_list_adapter,parent,false);
        ViewHolder viewHolder = new ViewHolder(view,onNoteListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EinkaufslisteAdapter.ViewHolder holder, int position) {
        CircleImageView image = holder.circleImageView;
        TextView rezeptName = holder.rezeptName;
        TextView status = holder.status;
        LinearLayout linearLayout = holder.linearLayout;

        String bild = einkaufslisteGrenzList.get(position).getRezept().getBild();
        String name = einkaufslisteGrenzList.get(position).getRezept().getName();
        String state = einkaufslisteGrenzList.get(position).getZustand();

        Picasso.get().load(R.string.ip_server+bild).into(image);
        rezeptName.setText(name);
        status.setText(state);

        if(state.equals("Abgeschlossen")){
            status.setTextColor(status.getResources().getColor(R.color.colorAccent));
        }

        if(position %2 == 0){
            linearLayout.setBackgroundColor(linearLayout.getResources().getColor(R.color.white));
        }
        else {
            linearLayout.setBackgroundColor(linearLayout.getResources().getColor(R.color.grey));
        }
    }

    @Override
    public int getItemCount() {
        return einkaufslisteGrenzList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout linearLayout;
        private CircleImageView circleImageView;
        private TextView rezeptName;
        private TextView status;
        private OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener){
            super(itemView);
            linearLayout = itemView.findViewById(R.id.parent);
            circleImageView = itemView.findViewById(R.id.einkaufsliste_rezept_image);
            rezeptName = itemView.findViewById(R.id.einkaufsliste_rezept_name);
            status = itemView.findViewById(R.id.einkaufsliste_status);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }
}
