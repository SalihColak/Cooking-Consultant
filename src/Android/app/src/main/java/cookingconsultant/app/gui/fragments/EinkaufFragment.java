package cookingconsultant.app.gui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import cookingconsultant.app.R;
import cookingconsultant.app.fachlogik.grenz.EinkaufslisteGrenz;
import cookingconsultant.app.fachlogik.grenz.ZutatStateGrenz;
import cookingconsultant.app.fachlogik.impl.EinkaufslisteVerwaltungImpl;
import cookingconsultant.app.fachlogik.services.EinkaufslisteVerwaltung;
import cookingconsultant.app.gui.activities.ActivityEinkaufslisteAnzeige;
import cookingconsultant.app.gui.adapter.EinkaufslisteAdapter;
import cookingconsultant.app.gui.services.OnNoteListener;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class EinkaufFragment extends Fragment implements OnNoteListener{

    private View view;
    private RecyclerView recyclerView;
    private List<EinkaufslisteGrenz> einkaufslisteGrenzList;
    private EinkaufslisteGrenz deletedEinkaufslist;
    EinkaufslisteAdapter einkaufslisteAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.einkauf_fragment,container,false);

        recyclerView = view.findViewById(R.id.einkaufliste_rv_id);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager layoutManager = linearLayoutManager;
        recyclerView.setLayoutManager(layoutManager);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        LoadEinkaufsliste loadEinkaufsliste = new LoadEinkaufsliste();
        loadEinkaufsliste.execute();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadEinkaufsliste loadEinkaufsliste = new LoadEinkaufsliste();
        loadEinkaufsliste.execute();
    }

    public void setStates(){
        for(EinkaufslisteGrenz einkaufslisteGrenz : einkaufslisteGrenzList){
            boolean state = true;
            List<ZutatStateGrenz> zutatStateGrenzList = einkaufslisteGrenz.getZutatStateList();
            for(int i = 0; i<zutatStateGrenzList.size();i++){
                if(!zutatStateGrenzList.get(i).isStatus()) state = false;
            }
            if(state) {
                if(einkaufslisteGrenz.getZustand().equals("Bearbeitung")){
                    einkaufslisteGrenz.setZustand("Abgeschlossen");
                    ChangeEinkaufslisteZustand changeEinkaufslisteZustand = new ChangeEinkaufslisteZustand();
                    changeEinkaufslisteZustand.execute(""+einkaufslisteGrenz.getEinkid(),"Abgeschlossen");
                }

            }
            else {
                if(einkaufslisteGrenz.getZustand().equals("Abgeschlossen")){
                    einkaufslisteGrenz.setZustand("Bearbeitung");
                    ChangeEinkaufslisteZustand changeEinkaufslisteZustand = new ChangeEinkaufslisteZustand();
                    changeEinkaufslisteZustand.execute(""+einkaufslisteGrenz.getEinkid(),"Bearbeitung");
                }

            }
        }
        einkaufslisteAdapter.notifyDataSetChanged();
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int pos = viewHolder.getAdapterPosition();
            deletedEinkaufslist = einkaufslisteGrenzList.get(pos);
            einkaufslisteGrenzList.remove(pos);
            einkaufslisteAdapter.notifyItemRemoved(pos);
            DeleteEinkaufsliste deleteEinkaufsliste = new DeleteEinkaufsliste();
            deleteEinkaufsliste.execute(deletedEinkaufslist);
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(getContext(), R.color.card_background0))
                    .addActionIcon(R.drawable.ic_delete_white_24dp)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


    private class DeleteEinkaufsliste extends AsyncTask<EinkaufslisteGrenz,Void,Void>{

        @Override
        protected Void doInBackground(EinkaufslisteGrenz... voids) {
            try {
                EinkaufslisteVerwaltung einkaufslisteVerwaltung = new EinkaufslisteVerwaltungImpl();
                einkaufslisteVerwaltung.deleteEinkaufslisteByID(voids[0].getEinkid());

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }



    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(getContext(), ActivityEinkaufslisteAnzeige.class);
        intent.putExtra("einkaufsliste",einkaufslisteGrenzList.get(position));
        startActivity(intent);
    }

    public class LoadEinkaufsliste extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            EinkaufslisteVerwaltung einkaufslisteVerwaltung = new EinkaufslisteVerwaltungImpl();

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userData", Context.MODE_PRIVATE);
            int userid = sharedPreferences.getInt("userid",-1);
            try {
                einkaufslisteGrenzList = einkaufslisteVerwaltung.getEinkaufslistenByUserID(userid);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            einkaufslisteAdapter = new EinkaufslisteAdapter(getContext(),einkaufslisteGrenzList,EinkaufFragment.this);
            recyclerView.setAdapter(einkaufslisteAdapter);
            if(einkaufslisteGrenzList!=null)setStates();
        }
    }

    private class ChangeEinkaufslisteZustand extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... strings) {
            EinkaufslisteVerwaltung einkaufslisteVerwaltung = new EinkaufslisteVerwaltungImpl();
            try {
                einkaufslisteVerwaltung.changeZustandEinkaufslisteByID(Integer.parseInt(strings[0]),strings[1]);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
