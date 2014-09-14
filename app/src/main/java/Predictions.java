
public class Predictions {

    public static Predictions predictions;

    private Predictions() {

    }

    public static Predictions get() {
        if(predictions == null){
            predictions = new Predictions();
        }
        return predictions;
    }
}
