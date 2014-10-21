package android.mccreightm.crystal_ball;

import android.media.MediaPlayer;

import java.util.Random;

public class Predictions {

    private static Predictions predictions;
    private String[] answers;

    private Predictions() {
        answers = new String[]{
                "Your wishes will come true",
//                "Chellie is the best",
                "The future is uncertain",
                "You are going to die",
//                "Ana is lame",
                "No",
                "Yes",
                "Why are you asking a cookie?",
                "Join the App Academy!!!"
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
        int n = rand.nextInt(answers.length) + 1;
        return answers[n];
    }
}
