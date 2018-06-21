package br.edu.fadergs.parsejson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DisplayListView extends AppCompatActivity {

    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ContactsAdapter contactsAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_listview_layout);
        listView = findViewById(R.id.listView);

        contactsAdapter = new ContactsAdapter(this, R.layout.row_layout);
        listView.setAdapter(contactsAdapter);
        json_string = getIntent().getExtras().getString("json_data");
        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            String name, email, mobile;

            while (count < jsonArray.length()){

                JSONObject JO = jsonArray.getJSONObject(count);
                name = JO.getString("name");
                email = JO.getString("email");
                mobile = JO.getString("mobile");
                Contacts contacts = new Contacts(name, email, mobile);
                contactsAdapter.add(contacts);

                count++;

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
