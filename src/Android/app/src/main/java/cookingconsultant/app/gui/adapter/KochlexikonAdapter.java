package cookingconsultant.app.gui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cookingconsultant.app.R;
import cookingconsultant.app.fachlogik.grenz.BeitragGrenz;
import cookingconsultant.app.gui.services.OnNoteListener;

public class KochlexikonAdapter extends RecyclerView.Adapter<KochlexikonAdapter.ViewHolder> {

    private Context mContext;
    private List<BeitragGrenz> list;
    private  List<BeitragGrenz> itemsCopy;
    private OnNoteListener onNoteListener;

    public KochlexikonAdapter(Context context, List<BeitragGrenz> list, OnNoteListener onNoteListener){
        mContext = context;
        this.list = list;
        this.onNoteListener = onNoteListener;
        itemsCopy = new ArrayList<>();
        for(BeitragGrenz beitragGrenz : list){
            itemsCopy.add(beitragGrenz);
        }
    }


    public void filter(String text) {
        list.clear();
        if(text.isEmpty()){
            list.addAll(itemsCopy);
        } else{
            text = text.toLowerCase();
            for(BeitragGrenz item: itemsCopy){
                if(item.getTitel().toLowerCase().contains(text)){
                    list.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.kochbeitrage_list_adapter,parent,false);
        ViewHolder viewHolder = new ViewHolder(view,onNoteListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView titel = holder.titel;
        LinearLayout parent = holder.parent;

        titel.setText(list.get(position).getTitel());

        if(position %2 == 0){
            parent.setBackgroundColor(parent.getResources().getColor(R.color.white));
        }
        else {
            parent.setBackgroundColor(parent.getResources().getColor(R.color.grey));
        }
    }

    @Override
    public int getItemCount() {
        if (list== null)return 0;
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView titel;
        private LinearLayout parent;
        private OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            parent = itemView.findViewById(R.id.kochlex_parent);
            titel = itemView.findViewById(R.id.kochlex_titel);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }
}
