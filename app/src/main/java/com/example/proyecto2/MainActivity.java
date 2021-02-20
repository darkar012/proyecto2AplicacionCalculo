package com.example.proyecto2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView pregunta;
    private EditText respuesta;
    private Button okBtn;
    private Button retry;
    private pregunta p;
    private TextView puntaje;
    private TextView counter;
    private boolean counterplay = true;
    private boolean textPressed = false;
    private int counter1 = 30;
    private int punto = 0;
    private int tiempo = 1500;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pregunta = findViewById(R.id.pregunta);
        respuesta = findViewById(R.id.respuestaUser);
        okBtn = findViewById(R.id.okBtn);
        puntaje = findViewById(R.id.puntaje);
        counter = findViewById(R.id.counter);
        retry = findViewById(R.id.retry);

        p = new pregunta();
        pregunta.setText(p.getPregunta());

        playCounter();

        okBtn.setOnClickListener(
                v -> {
                    responder();
                }
        );

        retry.setOnClickListener(
                v -> {
                    restart();
                }
        );

        pregunta.setOnTouchListener(
                (view, event) -> {
                    //mousePressed, mousseDragged y mouseRelease

                    switch (event.getAction()) {

                        case MotionEvent.ACTION_DOWN:
                            textPressed = true;
                            new Thread(
                                    () -> {
                                        for (int i = 0; i < 20; i++) {
                                            try {
                                                Thread.sleep(75);
                                                if (textPressed == false) {
                                                    return;
                                                }
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        counterplay = false;
                                        runOnUiThread(
                                                () -> {
                                                    nuevaPregunta();
                                                    retry.setVisibility(View.GONE);
                                                    respuesta.setText("");
                                                }
                                        );

                                    }

                            ).start();
                            break;

                        case MotionEvent.ACTION_UP:
                            textPressed = false;
                            break;

                    }
                    return true;
                }
        );

    }

    private void restart() {
        counterplay = false;
        nuevaPregunta();
        punto = 0;
        puntaje.setText("Puntaje: " + punto);
        retry.setVisibility(View.GONE);
        okBtn.setEnabled(true);
        respuesta.setText("");
    }

    private void responder() {
        String res = respuesta.getText().toString();
        res.trim();
        if (res.equals("")) {
            Toast.makeText(this, "no ha escrito una respuestas", Toast.LENGTH_SHORT).show();
        } else {
            try {
                int resInt = Integer.parseInt(res);

                int correcta = p.getRespuesta();
                if (resInt == correcta) {
                    Toast.makeText(this, "correcto", Toast.LENGTH_SHORT).show();
                    punto = punto + 5;
                    puntaje.setText("Puntaje: " + punto);
                    nuevaPregunta();
                    respuesta.setText("");
                    counterplay = false;
                    Toast.makeText(this, "respuesta: " + p.getRespuesta(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Pendejo", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "No ha escrito un valor valido, solo se permiten numeros", Toast.LENGTH_SHORT).show();
                respuesta.setText("");
            }
        }

    }

    private void nuevaPregunta() {
        p = new pregunta();
        pregunta.setText(p.getPregunta());
    }

    public void playCounter() {
        counter1 = 30;
        new Thread(
                () -> {
                    while (true) {
                        try {
                            Thread.sleep(1000);
                            if (counterplay == false) {
                                counter1 = 30;
                                counterplay = true;
                            }
                            if (counter1 > 0 && counter1 <= 30) {
                                counter1--;
                            }
                            runOnUiThread(
                                    () -> {
                                        counter.setText("" + counter1);
                                    }
                            );
                            if (counter1 == 0) {
                                runOnUiThread(
                                        () -> {
                                            okBtn.setEnabled(false);
                                            retry.setVisibility(View.VISIBLE);
                                        }
                                );
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
    }

}
