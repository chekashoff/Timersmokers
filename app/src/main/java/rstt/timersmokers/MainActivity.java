package rstt.timersmokers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final String LOG_TAG = "myLogs";
    int seconds, minutes;
    TextView text1;
    private static final String FORMAT = "%02d:%02d:%02d";
    private ImageButton button;
    private ImageButton button1;
    private Button button2;
    private boolean timerHasStarted = false;
    public CountDownTimer CountDownTimer;
    public TextView siga;
    public TextView siga1;
    public SeekBar seekBar;
    public TextView textView;
    public TextView timeLast;
    public TextView weCan;
    boolean isRunning = false;
    private boolean isCanceled = false;
    Context context;
    private NotificationManager notificationManager;
    private static final int NOTIFY_ID = 101;
    private static final String CHANNEL_ID = "CHANNEL_ID";
    DBHelper dbHelper;
    public String[] texts;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(this);
        setContentView(R.layout.activity_main);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        TextView textSeek = (TextView) findViewById(R.id.textSeek);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                textSeek.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });


        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener ((v) -> {
            Intent intent2 = new Intent(MainActivity.this, ChartActivity.class);
            startActivity(intent2);

        });
        timeLast = (TextView) findViewById(R.id.timeLast);
        siga = (TextView) findViewById(R.id.siga);
        context = this;
        text1 = (TextView) findViewById(R.id.text1);
        button = (ImageButton) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button.setOnClickListener(this);
        weCan = (TextView) findViewById(R.id.weCan);
        texts = new String[]{"«Whatever the mind can conceive and believe, it can achieve»– Napoleon Hill",
                "«There is only one thing that makes a dream impossible to achieve: the fear of failure» – Paulo Coelho.",
                "«Too many of us are not living our dreams because we are living our fears» – Les Brown.",
                "«Our greatest glory is not in never falling, but in rising every time we fall» – Confucius.",
                "«Most of the important things in the world have been accomplished by people who have kept on trying when there seemed to be no hope at all» – Dale Carnegie",
                "«Twenty years from now you will be more disappointed by the things you didn’t do than by the things you did» – Mark Twain.",
                "«Only those who dare to fail greatly can ever achieve greatly» ― Robert F. Kennedy.",
                "«Challenges are what make life interesting and overcoming them is what makes life meaningful» – Joshua Marine.",
                "«Success is stumbling from failure to failure with no loss of enthusiasm» – Winston S. Churchill.",
                "«Never, never, never give up» – Winston Churchill.",
                "«If not us, who? If not now, when?» – John F. Kennedy.",
                "«Change your life today. Don’t gamble on the future, act now, without delay. Simone de Beauvoir»",
                "«Success doesn’t come to you. You go to it» ",
                "«The future belongs to those, who believe in beauty of their dreams»",
                "«You miss 100% of the shots you don’t take» Wayne Gretzky",
                "«Fall seven times and stand up eight» Japanese Proverb",
                "«There are no shortcuts to any place worth going» Helen Keller",
                "«Nothing is impossible, the word itself says, I’m possible!» Audrey Hepburn",
                "«You yourself, as much as anybody in the entire universe, deserve your love and affection» Buddha",
                "«It does not matter how slowly you go as long as you do not stop» Confucius",
                "«All our dreams can come true, if we have the courage to pursue them» Walt Disney",
                "«If you don't design your own life plan, chances are you'll fall into someone else's plan. And guess what they have planned for you? Not much» Jim Rohn",
                "«Change your life today. Don't gamble on the future, act now, without delay» Simone de Beauvoir",
                "«Opportunity does not knock, it presents itself when you beat down the door» Kyle Chandler"};


        }
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        textView.setText(String.valueOf(seekBar.getProgress()));
    }



    public void timerStarter() {


        Integer f = Integer.parseInt(String.valueOf(seekBar.getProgress()));
        Integer b = 60000;
        Integer c = f * b;

        new CountDownTimer(c, 1000) { // adjust the milli seconds here{
            public void onTick(long millisUntilFinished) {
                isRunning = true;
                if (isCanceled) {
                    CountDownTimer = null;
                    text1.setText("00:00:00");
                    button.setImageResource(R.drawable.on);
                    button.setEnabled(true);
                    cancel();
                } else {
                    text1.setText("" + String.format(FORMAT,
                            TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                    TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onFinish() {
                isRunning = false;
                    SimpleDateFormat asd = new SimpleDateFormat("HH:mm dd/MM/yy");
                    String dateLast = asd.format(Calendar.getInstance().getTime());
                    text1.setText("00:00:00");
                    timeLast.setText(dateLast);
                    button.setEnabled(true);
                    button.setImageResource(R.drawable.on);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    NotificationCompat.Builder notificationBuilder =
                            new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                                    .setAutoCancel(false)
                                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                                    .setWhen(System.currentTimeMillis())
                                    .setContentIntent(pendingIntent)
                                    .setPriority(6)
                                    .setContentTitle("Time's up!")
                                    .setContentText("You can smoke");


                    createChannelIfNeeded(notificationManager);
                    notificationManager.notify(NOTIFY_ID, notificationBuilder.build());
                }

            }.start();


        }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override

    public void onClick(View v) {
        SimpleDateFormat iddqd = new SimpleDateFormat("YYYY-MM-DD");
        String datesq = iddqd.format(Calendar.getInstance().getTime());
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String sigars = siga.getText().toString();
        String lastdate1 = timeLast.getText().toString();
        isCanceled = false;
        Random random = new Random ();
        int pos = random.nextInt(texts.length);
        weCan.setText(texts[pos]);

        switch (v.getId())  //get the id of the view clicked. (in this case button)
        {
            case R.id.button: // if its button
                timerStarter();
                contentValues.put(DBHelper.KEY_NAME, sigars);
                contentValues.put(DBHelper.KEY_MAIL, lastdate1);
                contentValues.put(DBHelper.KEY_DATE, datesq);
                database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
                Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                    int emailIndex = cursor.getColumnIndex(DBHelper.KEY_MAIL);
                    int dateIndex =  cursor.getColumnIndex(DBHelper.KEY_DATE);
                    do {

                        siga.setText(new StringBuilder().append(cursor.getCount()).toString());
                    } while (cursor.moveToNext());
                } else
                cursor.close();

                if (isRunning = true){
                    button.setEnabled(false);
                    button.setImageResource(R.drawable.off);
                }
                if(CountDownTimer!=null)   {
                    button.setEnabled(true);
                    isCanceled = true;
                    button.setImageResource(R.drawable.off);
                }
                break;

        }
        dbHelper.close();

    }


    public static void createChannelIfNeeded(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }


}



