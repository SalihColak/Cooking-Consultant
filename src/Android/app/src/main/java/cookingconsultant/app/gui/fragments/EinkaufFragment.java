package cookingconsultant.app.gui;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import cookingconsultant.app.R;
import cookingconsultant.app.fachlogik.grenz.EinkaufslisteGrenz;
import cookingconsultant.app.fachlogik.impl.EinkaufslisteVerwaltungImpl;
import cookingconsultant.app.fachlogik.services.EinkaufslisteVerwaltung;
import cookingconsultant.app.gui.adapter.EinkaufslisteAdapter;
import cookingconsultant.app.gui.services.OnNoteListener;

public class EinkaufFragment extends Fragment implements OnNoteListener{

    private View view;
    private RecyclerView recyclerView;
    private List<EinkaufslisteGrenz> einkaufslisteGrenzList;
    private int userid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        userid = 1;

        view = inflater.inflate(R.layout.einkauf_fragment,container,false);

        recyclerView = view.findViewById(R.id.einkaufliste_rv_id);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager layoutManager = linearLayoutManager;
        recyclerView.setLayoutManager(layoutManager);

        LoadEinkaufsliste loadEinkaufsliste = new LoadEinkaufsliste();
        loadEinkaufsliste.execute();

        return view;
    }

    @Override
    public void onNoteClick(int position) {
        Toast.makeText(getContext(),einkaufslisteGrenzList.get(position).getZutaten().toString(),Toast.LENGTH_SHORT).show();
    }

    private class LoadEinkaufsliste extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            EinkaufslisteVerwaltung einkaufslisteVerwaltung = new EinkaufslisteVerwaltungImpl();

            try {
                einkaufslisteGrenzList = einkaufslisteVerwaltung.getEinkaufslistenByUserID(userid);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            EinkaufslisteAdapter einkaufslisteAdapter = new EinkaufslisteAdapter(getContext(),einkaufslisteGrenzList,EinkaufFragment.this);
            recyclerView.setAdapter(einkaufslisteAdapter);
            return null;
        }
    }

}
