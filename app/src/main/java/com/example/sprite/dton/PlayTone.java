package com.example.sprite.dton;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class PlayTone {
    private static int             duration    =   3;
    private static int             sampleRate  =   8000;
    private static int             numSamples  =   duration * sampleRate;
    private static AudioTrack      track;
    //private AudioManager    audioBoss;

    public static void play(double freq) {
        track = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, (2 * numSamples), AudioTrack.MODE_STATIC);
        //audioBoss = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        track = buildTrack(initSound(freq));
        track.play();
    }

    private static byte[] initSound(double frequency) {
        double  sampleData[]    =   new double[numSamples];
        byte  sampleInProgress[] = new byte[2 * numSamples];  //redundant, yeah :P

        for (int ouah = 0; ouah < numSamples; ++ouah) {
            sampleData[ouah] = Math.sin(2 * Math.PI * ouah / (sampleRate / frequency));
        }

        int ouah = 0;
        for (final double singleSample : sampleData) {
            final short nang = (short) ((singleSample * 32767));    //max amplitude
            //16-bit wav PCM; first byte is low order
            sampleInProgress[ouah++] = (byte) (nang & 0x00ff);
            sampleInProgress[ouah++] = (byte) ((nang & 0xff00) >>> 8);
        }

        return sampleInProgress;
    }

    private static AudioTrack buildTrack(byte samples[]) {
        //fix deprecated AudioTrack issue
        AudioTrack track = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, samples.length,
                AudioTrack.MODE_STATIC);

        track.write(samples, 0, samples.length);
        return track;
    }

}
