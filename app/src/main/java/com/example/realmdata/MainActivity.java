package com.example.realmdata;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.realmdata.Model.Database;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends Activity implements View.OnClickListener {
    EditText name;
    EditText marks;
    Button save;
    TextView result;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.name);
        marks = (EditText) findViewById(R.id.marks);
        save = (Button) findViewById(R.id.save);
        result = (TextView) findViewById(R.id.result);
        realm = Realm.getDefaultInstance();
        save.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {
        saveToDataBase(name.getText().toString().trim(), Integer.parseInt(marks.getText().toString().trim()));
        displayDataFromDataBase();

    }

    private void displayDataFromDataBase() {
        RealmResults<Database> realmResults = realm.where(Database.class).findAll();
        String data = "";
        realm.beginTransaction();
        for (Database realmResultsIterator : realmResults) {
            data += realmResultsIterator.toString();
        }
        realm.commitTransaction();
        result.setText(data);
    }

    private void saveToDataBase(final String name, final int marks) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Database database = realm.createObject(Database.class);
                database.setName(name);
                database.setMarks(marks);
            }


        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.i("Database", "onSuccess: Data successfully saved");

            }

        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e("Database", "onError: Data saving failed", error);

            }

        });
    }
}
