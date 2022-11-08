package com.example.praktikum_papb_d_modul7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    ImageView png1, png2, png3;
    TextView status;
    Button tombol;
    boolean begin = true;
    ArrayList<String> url = new ArrayList<>();
    int img1, img2, img3;
    Random acak = new Random();
    ExecutorService exe1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        png1 = findViewById(R.id.png1);
        png2 = findViewById(R.id.png2);
        png3 = findViewById(R.id.png3);
        status = findViewById(R.id.status);
        tombol = findViewById(R.id.tombol);
        exe1 = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        tombol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == tombol.getId()) {
                    tombol.setText("Berhenti");
                    status.setText("Judi slot sedang berjalan");
                    if (!begin) {
                        begin = !begin;
                        exe1.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    final String link =
                                            browsImg("https://mocki.io/v1/821f1b13-fa9a-43aa-ba9a-9e328df8270e");
                                    try {
                                        JSONArray jsonArray = new
                                                JSONArray(link);
                                        for (int i = 0; i <
                                                jsonArray.length(); i++) {
                                            JSONObject jsonObject =
                                                    jsonArray.getJSONObject(i);
                                            url.add(jsonObject.getString("url"));
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    while (begin) {
                                        img1 = acak.nextInt(3);
                                        img2 = acak.nextInt(3);
                                        img3 = acak.nextInt(3);
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {

                                                Glide.with(MainActivity.this).load(url.get(img1)).into(png2);
                                                Glide.with(MainActivity.this).load(url.get(img2)).into(png1);
                                                Glide.with(MainActivity.this).load(url.get(img3)).into(png3);
                                            }
                                        });
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException
                                                e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else {
                        begin = false;
                        if ((img1 == img2) && (img1 == img3)) {
                            Toast.makeText(MainActivity.this,"Jackpot, selamat anda menang", Toast.LENGTH_SHORT).show();
                            tombol.setText("Mulai");
                            status.setText("Judi slot berhenti");
                        } else {
                            Toast.makeText(MainActivity.this, "Kalah, silakan coba lagi", Toast.LENGTH_SHORT).show();
                            tombol.setText("Mulai");
                            status.setText("Judi slot berhenti");
                        }
                    }
                }
            }
        });
    }

    private String browsImg(String jpg) throws IOException {
        final URL link = new URL(jpg);
        final InputStream went = link.openStream();
        final StringBuilder out = new StringBuilder();
        final byte[] buffer = new byte[1024];
        try {
            for (int ctr; (ctr = went.read(buffer)) != -1; ) {
                out.append(new String(buffer, 0, ctr));
            }
        } catch (IOException e) {
            throw new RuntimeException("Gagal mendapatkan text", e);
        }
        final String file = out.toString();
        return file;
    }
}
