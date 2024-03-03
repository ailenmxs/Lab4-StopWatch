package co.edu.unipiloto.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //Determinar si el cronómetro está en ejecución
    private boolean running;
    //Contador con los segundos cuando se ejecuta
    private int segundos = 0;
    private ArrayList<String> vueltas = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Invocar un hilo de cronometrar
        runTimer();
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                vueltas);
        ListView listView = findViewById(R.id.lista_vueltas);
        listView.setAdapter(adapter);
    }
    private void runTimer() {
        //Relación un objeto textview con el elemento gráfico
        TextView timeview = (TextView) findViewById(R.id.time_view);
        //Declarar un handler para manejar el tiempo en un hilo de ejecución
        Handler handler = new Handler();
        //Invocar el método post e instanciar un objeto runnable
        handler.post(new Runnable() {
            @Override
            public void run() {
                int horas= segundos / 3600;
                int minutos=(segundos % 3600)/60;
                int secs = segundos % 60;
                //Formato de salida
                String tiempo = String.format(Locale.getDefault(),"%d:%02d:%02d",horas,minutos,secs);
                timeview.setText(tiempo);
                if(running)
                    segundos++;
                handler.postDelayed(this,1000);
            }
        });
    }
    public void onClickStart(View view) {
        running=true;
    }
    public void onClickPause(View view) {
        running=false;

    }
    public void OnClickReset(View view) {
        running=false;
        segundos=0;
        vueltas.clear();
        adapter.notifyDataSetChanged();
    }
    // Método para registrar una vuelta
    public void onMarkLap(View view) {
        if(running && vueltas.size() < 5) {
            int horas = segundos / 3600;
            int minutos = (segundos % 3600) / 60;
            int secs = segundos % 60;
            String tiempo = String.format(Locale.getDefault(), "%d:%02d:%02d", horas, minutos, secs);
            vueltas.add(tiempo);
            adapter.notifyDataSetChanged();
        }
    }
}