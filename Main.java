import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Оксана on 18.11.2014.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Field field = new Field(3);
        field.showField();
        field.playGame();

    }
}
