package com.example.midiaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPLayer;
    private SeekBar seekVolume;
    private AudioManager audioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPLayer = MediaPlayer.create(getApplicationContext(), R.raw.teste);
        inicializarSeekBar();
    }

    private void inicializarSeekBar(){
        seekVolume = findViewById(R.id.seekVolume);

        // configurar o audio manager
        audioManager = (AudioManager)  getSystemService(Context.AUDIO_SERVICE);

        // recuperar os valores de volume máximo e volume atual
        int volumeMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volumeAtual = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        // configura os valores máximos para o seekbar
        seekVolume.setMax( volumeMax );

        // configura o progresso atual do seekBar
        seekVolume.setProgress( volumeAtual );

        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                // progress = valor que esta sendo retornado do volume para nossa seekbar
                // flag = Consigo fazer configurações adicionais. Ex: AudioManager.FLAG_SHOW_UI , faz que ao arrastar o seekbar, mostre o volume do dispositivo raiz para a UI do usuario
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void executarSom(View view ){
        if (mediaPLayer != null){
            mediaPLayer.start();
        }
    }

    public void pauseMusica(View view){
        if ( mediaPLayer.isPlaying() ){
            mediaPLayer.pause();
        }
    }

    public void stopMusica(View view ){
        if (mediaPLayer.isPlaying()){
            mediaPLayer.stop();
            //paramos a musica e configuramos o objeto midiaplayer com o objeto da musica
            mediaPLayer = MediaPlayer.create(getApplicationContext(), R.raw.teste);
        }
    }

    // OTIMIZANDO E LIMPANDO A MEMORIA DO CELULAR AO ENCERRAR O APLICATIVO
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ( mediaPLayer != null && mediaPLayer.isPlaying() ){
            mediaPLayer.stop();
            mediaPLayer.release(); // libera recursos de midia que esta executando com a classe midia player, libera memoria do celular.
            mediaPLayer = null;
        }
    }

        // Método de ciclo de vida que ao sair ou mudar de app para parar a Musica
    @Override
    protected void onStop() {
        super.onStop();
        if ( mediaPLayer.isPlaying() ){
            mediaPLayer.pause();
        }
    }
}