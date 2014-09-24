package android.mccreightm.crystal_ball;

import android.media.MediaPlayer;

import java.util.Random;

public class Predictions {

    private static Predictions predictions;
    private String[] answers;

    private Predictions() {
        answers = new String[]{
                "Your wishes will come true",
                "Your wishes will NEVER come true",
                "The future is uncertain",
                "You are going to die",
                "Ana is lame"
        };
    }

    public static Predictions get() {
        if(predictions == null){
            predictions = new Predictions();
        }
        return predictions;
    }

    public String getPrediction(){
        Random rand = new Random();
        int n = rand.nextInt(6);
        return answers[n];
    }
}
