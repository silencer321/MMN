package e.ricardo.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;
    private String[] lugares = {"Centro","La huerta","Av Camelinas"};
    private String[] centro = {"Tarascas","Catedrla","Callegon del romance"};
    private String[] camelinas = {"Zoologico","Plaza Las Americas","Cinepolis"};
    private String[] huerta = {"Sirloin","Cinepolis"};
    TinyDB tinyDB;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;

    private View mContentView;
    private boolean mVisible;
    private Timer t = new Timer();
    private int time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tinyDB = new TinyDB(getApplicationContext());

        if(tinyDB.getBoolean("existe")){

            setContentView(R.layout.activity_fullscreen);
            CountDownTimer cdt = new CountDownTimer(5000,1000){

                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    Intent nuevo = new Intent(FullscreenActivity.this,Inicio.class);
                    startActivity(nuevo);
                    finish();
                }

            };
            cdt.start();



            mVisible = true;
            mContentView = findViewById(R.id.fullscreen_content);

        }
        else {
            setContentView(R.layout.activity_fullscreen);
            CountDownTimer cdt = new CountDownTimer(5000,1000){

                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    Intent nuevo = new Intent(FullscreenActivity.this,MainActivity.class);
                    startActivity(nuevo);
                    finish();
                }

            };
            cdt.start();



            mVisible = true;
            mContentView = findViewById(R.id.fullscreen_content);
        }

        //if(getFirstTimeRun() == 0) {  new Insertar();    }

/*
        String img1="";

        if (getFirstTimeRun()==0){
            bd = new BDUsuario(getApplicationContext());
            SQLiteDatabase bdinsert = bd.getWritableDatabase();
            ContentValues valores = new ContentValues();
            ContentValues valores1 = new ContentValues();
            for (int j =0;j<lugares.length;j++){
                valores.put(EsquemaDB.Esquema.COLUMN_NOMBRE_SECCION,lugares[j]);
                Long id = bdinsert.insert(EsquemaDB.Esquema.TABLE_SECCION,null,valores);

                    switch (lugares[j]){
                        case "Centro":
                            for (int k=0; k<centro.length; k++){
                                String img = lugares[j].concat(centro[k]);
                                for (int s=0; s<img.length(); s++){
                                    if(img.charAt(s) != ' '){
                                        char c= img.charAt(s);
                                        img1 = img1 + c;
                                    }
                                }
                                img1 = img1.toLowerCase();
                                valores1.put(EsquemaDB.Esquema.COLUMN_NOMBRE_LUGAR,centro[k]);
                                valores1.put(EsquemaDB.Esquema.COLUMN_ID_SECCION_FK,id);
                                valores1.put(EsquemaDB.Esquema.COLUMN_IMG,img1);
                            }
                            break;
                        case "Camelinas":
                            for (int l=0; l<camelinas.length;l++){
                                String img = lugares[j].concat(camelinas[l]);
                                for (int s=0; s<img.length(); s++){
                                    if(img.charAt(s) != ' '){
                                        char c= img.charAt(s);
                                        img1 = img1 + c;
                                    }
                                }
                                img1 = img1.toLowerCase();
                                valores1.put(EsquemaDB.Esquema.COLUMN_NOMBRE_LUGAR,camelinas[l]);
                                valores1.put(EsquemaDB.Esquema.COLUMN_ID_SECCION_FK,id);
                                valores1.put(EsquemaDB.Esquema.COLUMN_IMG,img1);
                            }
                            break;
                        case "La Huerta":
                            for(int o=0; o<huerta.length;o++){
                                String img = lugares[j].concat(huerta[o]);
                                for (int s=0; s<img.length(); s++){
                                    if(img.charAt(s) != ' '){
                                        char c= img.charAt(s);
                                        img1 = img1 + c;
                                    }
                                }
                                img1 = img1.toLowerCase();
                                valores1.put(EsquemaDB.Esquema.COLUMN_NOMBRE_LUGAR,huerta[o]);
                                valores1.put(EsquemaDB.Esquema.COLUMN_ID_SECCION_FK,id);
                                valores1.put(EsquemaDB.Esquema.COLUMN_IMG,img1);

                            }
                            break;

                    }

                Log.i("INSERT","ID INSERTADO-> "+id);
            }

        }*/




        // Set up the user interaction to manually show or hide the system UI.


        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.

    }

    private int getFirstTimeRun() {
        SharedPreferences sp = getSharedPreferences("MYAPP", 0);
        int result, currentVersionCode = BuildConfig.VERSION_CODE;
        int lastVersionCode = sp.getInt("FIRSTTIMERUN", -1);
        if (lastVersionCode == -1) result = 0; else
            result = (lastVersionCode == currentVersionCode) ? 1 : 2;
        sp.edit().putInt("FIRSTTIMERUN", currentVersionCode).apply();
        return result;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }

        }
    };

    private final Handler mHideHandler = new Handler();
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
