package com.android.developer.moviedatalist;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayMovie extends AppCompatActivity {

    private DataBaseHelper mydb;
    TextView name;
    TextView director;
    TextView year;
    TextView nation;
    TextView rating;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_movie);
        name = findViewById(R.id.editTextName);
        director = findViewById(R.id.editTextDirector);
        year = findViewById(R.id.editTextYear);
        nation = findViewById(R.id.editTextNation);
        rating = findViewById(R.id.editTextRating);

        mydb = new DataBaseHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if(Value > 0) {
                Cursor rs = mydb.getData(Value);
                id = Value;
                rs.moveToFirst();
                String n = rs.getString(rs.getColumnIndex(DataBaseHelper.MOVIES_COLUMN_NAME));
                String d = rs.getString(rs.getColumnIndex(DataBaseHelper.MOVIES_COLUMN_DIRECTOR));
                String y = rs.getString(rs.getColumnIndex(DataBaseHelper.MOVIES_COLUMN_YEAR));
                String na = rs.getString(rs.getColumnIndex(DataBaseHelper.MOVIES_COLUMN_NATION));
                String r = rs.getString(rs.getColumnIndex(DataBaseHelper.MOVIES_COLUMN_RATING));
                if (!rs.isClosed()) {
                    rs.close();
                }
                Button b = findViewById(R.id.button1);
                b.setVisibility(View.INVISIBLE);

                name.setText(n);
                name.setText(d);
                name.setText(y);
                name.setText(na);
                name.setText(r);
            }
        }
    }

    public void insert(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if(Value > 0) {
                if(mydb.updateMovie(id, name.getText().toString(), director
                .getText().toString(), year.getText().toString(),
                        nation.getText().toString(), rating.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "수정되었습니다",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),
                            com.android.developer.moviedatalist.MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "수정되지 않았습니다",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                if(mydb.insertMovie(name.getText().toString(), director
                .getText().toString(), year.getText().toString(),
                        nation.getText().toString(), rating.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "추가되었습니다",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "추가되지 않았습니다",
                            Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        }
    }

    public void delete(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if(Value > 0) {
                mydb.deleteMovie(id);
                Toast.makeText(getApplicationContext(), "삭제되었습니다",
                        Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "삭제되지 않았습니다",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void edit(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int value = extras.getInt("id");
            if (value > 0) {
                if (mydb.updateMovie(id, name.getText().toString(), director
                .getText().toString(), year.getText().toString(),
                        nation.getText().toString(), rating.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "수정되었습니다",
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "수정되지 않았습니다",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
