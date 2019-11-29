package cookingconsultant.app.datenhaltung.impl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cookingconsultant.app.R;
import cookingconsultant.app.datenhaltung.entities.Rezept;
import cookingconsultant.app.datenhaltung.entities.Zutat;
import cookingconsultant.app.datenhaltung.services.RezeptService;

public class RezeptServiceImpl implements RezeptService {

    private static final String URL_GET_REZEPT_BY_ID = "http://10.49.223.166/getRezept.php";

    @Override
    public Rezept getRezeptByID(Integer rezid) throws IOException, JSONException {
        /*List<Zutat> zutatList = new ArrayList<>();
        zutatList.add(new Zutat(3,"Wasser","Liter",""));
        List<String> mockSchritte = new ArrayList<>();
        mockSchritte.add("Hände waschen");
        mockSchritte.add("2 Eier mit 500gr Mehl und 1L Milch umrühren");
        mockSchritte.add("500mL Kokos-Milch einrühren");
        Rezept mock = new Rezept(rezid,"Kokos Torte","Kokos Torten sind einfach der Hammer.",mockSchritte,"Snack","Party","Amerikanisch","20min","./bilder/rezepte/kokostorte.png");
        mock.setZutaten(zutatList);
        return mock;*/
        URL myurl = new URL(URL_GET_REZEPT_BY_ID);
        HttpURLConnection httpURLConnection = (HttpURLConnection)myurl.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.connect();

        OutputStream os = httpURLConnection.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
        String postData = URLEncoder.encode("rezid", "UTF-8") + "=" + URLEncoder.encode(""+rezid, "UTF-8");
        bw.write(postData);
        bw.flush();
        os.close();


        InputStream is = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        String line ="";

        StringBuilder stringBuilder = new StringBuilder();
        while ((line = bufferedReader.readLine())!=null){
            stringBuilder.append(line);
        }
        String data = stringBuilder.toString();

        JSONArray jsonArray = new JSONArray(data);
        JSONObject jobj = jsonArray.getJSONObject(0);


        List<String> schritte = new ArrayList<>();
        schritte = Arrays.asList(jobj.getString("schritte").split(";"));

        Rezept rezept = null;
        if(jobj!=null) {
            rezept = new Rezept(jobj.getInt("rezid"), jobj.getString("name"), jobj.getString("beschreibung"), schritte,
                    jobj.getString("art"), jobj.getString("anlass"), jobj.getString("praeferenz"), jobj.getString("kochzeit") + "min",
                    jobj.getString("bild"), jobj.getString("menge"));
            rezept.setZutaten(new ArrayList<Zutat>());
        }

        return rezept;
    }

    @Override
    public List<Rezept> getRezepteByQuery(String query) throws IOException, JSONException {
/*
        List<Zutat> zutatList = new ArrayList<>();
        zutatList.add(new Zutat(3,"Wasser","Liter",""));

        List<Rezept> mockRezepte = new ArrayList<>();
        List<String> mockSchritte = new ArrayList<>();
        mockSchritte.add("Hände waschen");
        mockSchritte.add("2 Eier mit 500gr Mehl und 1L Milch umrühren");
        mockSchritte.add("500mL Kokos-Milch einrühren");
        Rezept mock1 = new Rezept(4,"Kokos Torte","Kokos Torten sind lecker.",mockSchritte,"Snack","Party","Amerikanisch","50min","./bilder/rezepte/kokostorte.png");
        mock1.setZutaten(zutatList);
        mockSchritte = new ArrayList<>();
        mockSchritte.add("1Liter Milch mit 500gr Zucker einrühren");
        mockSchritte.add("500gr Mehl hinzugügen");
        mockSchritte.add("Eine Packung Backpulver einschütteln.");
        Rezept mock2 = new Rezept(6,"Bananenkuchen","Bananenkuchen sind einfach der Hammer",mockSchritte,"Snack","Party","Amerikanisch","20min","./bilder/rezepte/bananenkuchen.png");
        mock2.setZutaten(zutatList);
        mockSchritte = new ArrayList<>();
        mockSchritte.add("Den Ofen auf 200 Grad vorheizen.");
        mockSchritte.add("Die Zucchini waschen und in etwa 2 cm dicke Scheiben schneiden.");
        mockSchritte.add("Das Backpapier mit Öl einpinseln");
        Rezept mock3 = new Rezept(5,"Zucchini Parmesan Craker","Immer wieder gern.",mockSchritte,"Snack","Party","Amerikanisch","55min","./bilder/rezepte/zucchini-cracker.png");
        mock3.setZutaten(zutatList);
        mockRezepte.add(mock1);
        mockRezepte.add(mock2);
        mockRezepte.add(mock3);
        return mockRezepte;*/
        List<Rezept> list = new ArrayList<>();
        list.add(getRezeptByID(1));
        list.add(getRezeptByID(1));
        return list;
    }
}
