package com.example.nuevoproyectog;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nuevoproyectog.interfaces.revistaAPI;
import com.example.nuevoproyectog.modelo.revista;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView datos;
    TextView txtTitel ;

    TextView id;
    TextView vol;
    TextView num;
    TextView fechaP;
    TextView titulo;
    TextView doi;
    TextView cover;
    Button btnVisualizar;



    Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //iniciar spiner
        sp = findViewById(R.id.spinner8);
        //convertir el xml y adaptarlo a un spiner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.lista, android.R.layout.simple_spinner_item);
        sp.setAdapter(adapter);

        datos = findViewById(R.id.txtdatos);
        datos.setMovementMethod(new ScrollingMovementMethod());

        txtTitel = findViewById(R.id.txtMensaje);

        id = findViewById(R.id.txtid);
        vol = findViewById(R.id.txtVol);
        num = findViewById(R.id.txtNum);
        fechaP = findViewById(R.id.txtFechaPubl);
        titulo = findViewById(R.id.txtTitle);
        doi = findViewById(R.id.txtDoi);
        cover = findViewById(R.id.txtCover);

//        btnVisualizar = findViewById(R.id.btnVisualizar);
//        btnVisualizar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });
    }

    private void findR(){

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://revistas.uteq.edu.ec/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        revistaAPI revistaAPI2 = retrofit.create(revistaAPI.class);
        Call <List<revista>> call = revistaAPI2.getrevista();

        call.enqueue(new Callback<List<revista>>() {
            @Override
            public void onResponse(Call<List<revista>> call, Response<List<revista>> response) {
                if(!response.isSuccessful())
                {

                    datos.setText("Código: " + response.code());
                    return;

                }
                List<revista> kushkiList = response.body();

                //Mostrar los datos en el TextView
                for (revista data: kushkiList)
                {
                    SpannableString myTextCode = new SpannableString("id: " + data.getIssue_id() + "\n");
                    SpannableString myTextName = new SpannableString("valumen: " + data.getVolume() + "\n\n");
                    StyleSpan bold = new StyleSpan(Typeface.BOLD);
                    StyleSpan bold2 = new StyleSpan(Typeface.BOLD);

                    myTextCode.setSpan(bold, 0 , 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    myTextName.setSpan(bold2, 0 , 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                    datos.append(myTextCode);
                    datos.append(myTextName);
                }



            }

            @Override
            public void onFailure(Call<List<revista>> call, Throwable t) {
                String msj = "Mensaje de error: " + t.getMessage();
                datos.setText(msj);

            }
        });
    }


    public void btnVisualizar_Click(View view)
    {
        txtTitel.setText("Utilizando la librería " + sp.getSelectedItem());
        datos.setText("");
        if(sp.getSelectedItem().toString().toUpperCase().equals("Retrofit".toUpperCase()))
        {
            Toast.makeText(this, "Su petición está siendo procesada.....", Toast.LENGTH_LONG).show();
            findR();
        }
        else if(sp.getSelectedItem().toString().toUpperCase().equals("Volley".toUpperCase()))
        {
            Toast.makeText(this, "Su petición está siendo procesada.....", Toast.LENGTH_LONG).show();
          //  getKushkipagoVolley();
        }
        else
        {
            txtTitel.setText("Título de librería");
            Toast.makeText(sp.getContext(), "Seleccionar una librería para mostrar datos", Toast.LENGTH_LONG).show();
        }

        //Toast.makeText(spOption.getContext(), "Selección: " + spOption.getSelectedItem(), Toast.LENGTH_LONG).show();
    }



}