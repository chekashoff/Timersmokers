package rstt.timersmokers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;


public class ChartActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button3;
    DBHelper dbHelper;
    Dialog dialog;
    Cursor userCursor;
    Cursor userCursor1;
    SQLiteDatabase database;
    SimpleCursorAdapter mCursorAd;
    ListView listView;
    Context context;
    TextView textsig;
    TextView textWecan;
    TextView textoday;
    GraphView graph;
    int xValue;
    int yValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        graph = (GraphView) findViewById(R.id.graph);
        listView = findViewById(R.id.listView);
        dbHelper = new DBHelper(this);
        button3 = (Button) findViewById(R.id.button3);
        textsig = (TextView) findViewById(R.id.textsig);
        button3.setOnClickListener(this);
        textoday = (TextView) findViewById(R.id.textoday);


    }

    public void onResume() {
        super.onResume();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        userCursor = database.rawQuery("select * from " + DBHelper.TABLE_CONTACTS, null);
        String[] headers = new String[]{DBHelper.KEY_MAIL};
        mCursorAd = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, userCursor, headers, new int[]{android.R.id.text1}, 0);
        listView.setAdapter(mCursorAd);
        textsig.setText(new StringBuilder().append("All cigarettes' ").append(userCursor.getCount()).toString());
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
    }




    @Override
    public void onClick(View v) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        switch (v.getId())  //get the id of the view clicked. (in this case button)
        {
            case R.id.button3:
                AlertDialog.Builder builder = new AlertDialog.Builder(ChartActivity.this);
                builder.setTitle("Delete all data")
                        .setIcon(R.drawable.ic_baseline_cancel_24)
                        .setCancelable(true)
                        .setPositiveButton("Delete",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                database.delete(DBHelper.TABLE_CONTACTS, null, null);
                AlertDialog alert = builder.create();
                alert.show();
                break;

        }
        dbHelper.close();

    }

    public void onDestroy(){
        super.onDestroy();

    }

}