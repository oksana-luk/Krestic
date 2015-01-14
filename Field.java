import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static jdk.nashorn.internal.objects.NativeString.charAt;

/**
 * Created by Оксана on 18.11.2014.
 */
public class Field {

    public int fieldSize;

    public int winSize;

    public static final int DEFOLT_FIELD_SIZE = 3;

    //public final int  DEFOLT_WIN_SIZE = 3;

    //public final int WIN_SIZE = 5;

    public char[][] Field;

    public static final String GAMER1 = "Игрок 1";

    public static final String GAMER2 = "Игрок 2";

    public static final char KRESTIK = 'X';

    public static final char NOLIK = 'O';

    public Field() {
        this(DEFOLT_FIELD_SIZE);
    }

    public Field(int size) {
        fieldSize = size;
        Field = new char[fieldSize][fieldSize];
        /*if (fieldSize == DEFOLT_FIELD_SIZE) {
            winSize = DEFOLT_WIN_SIZE;
        }
        else{
            winSize = WIN_SIZE;
        }*/
    }

    public void playGame() throws IOException {
        for (int k = 2; k <= fieldSize * fieldSize + 1; k++) {
            if ((k & 1) == 0) {
                System.out.println(GAMER1 + ", ваш ход.");
                doStep(KRESTIK);
            } else {
                System.out.println(GAMER2 + ", ваш ход.");
                doStep(NOLIK);
            }
        }
        System.out.print("Ничья");
        System.exit(0);
    }

    public void doStep(char step) throws IOException {
        System.out.print("Введите номер строки: ");
        int x = inputXY(step);
        System.out.print("Введите номер столбца: ");
        int y = inputXY(step);
        isEmptyField(x, y, step);
        Field[x][y] = step;
        showField();
        testWin(Field, step);
    }

    public int inputXY(char step) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String s = reader.readLine();
        isDigitInput(s, step);
        int i = Integer.parseInt(s);
        illegalInput(i, step);
        return i;
    }

    public void isDigitInput(String s, char step) throws IOException {
        char[] input = s.toCharArray();
        char ch1 = input[0];
        if (Character.isDigit(ch1)) {
            return;
        } else {
            System.out.println("Неверный ввод. Повторите:");
            doStep(step);
        }
    }

    public void illegalInput(int k, char step) throws IOException {
        if (k >= 0 && k < fieldSize) {
            return;
        } else {
            System.out.println("Неверный ввод.");
            doStep(step);
        }

    }

    public void isEmptyField(int x, int y, char step) throws IOException {
        if (Field[x][y] == '\u0000') {
            return;
        } else {
            System.out.print("Занято");
            doStep(step);
        }
    }

    public void showField() {
        System.out.print(" ");
        for (int k = 0; k < fieldSize; k++) {
            System.out.print(" " + k + " ");
        }
        System.out.println();
        for (int i = 0; i < fieldSize; i++) {
            System.out.print(i);
            for (int j = 0; j < fieldSize; j++) {
                System.out.print("[" + Field[i][j] + "]");
            }
            System.out.println();
        }
    }

    public void testWin(char[][] chars, char step) {
        testWinLine(chars, step);
        testWinColum(chars, step);
        testWinDiagonal1(chars, step);
        testWinDiagonal2(chars, step);
    }

    public void testWinLine(char[][] ch, char step) {
        for (int i = 0; i < ch.length; i++) {
            int j = 0;
            char char1 = ch[j][i];
            char char2 = ch[j + 1][i];
            char char3 = ch[j + 2][i];
            testWinCombinatoin(char1, char2, char3, step);
        }
    }

    public void testWinColum(char[][] ch, char step) {
        for (int i = 0; i < ch.length; i++) {
            int j = 0;
            char char1 = ch[i][j];
            char char2 = ch[i][j + 1];
            char char3 = ch[i][j + 2];
            testWinCombinatoin(char1, char2, char3, step);
        }
    }

    public void testWinDiagonal1(char[][] ch, char step) {
        int i = 0;
        int j = 0;
        char char1 = ch[i][j];
        char char2 = ch[i + 1][j + 1];
        char char3 = ch[i + 2][j + 2];
        testWinCombinatoin(char1, char2, char3, step);
    }

    public void testWinDiagonal2(char[][] ch, char step) {
        int i = 2;
        int j = 0;
        char char1 = ch[i][j];
        char char2 = ch[i - 1][j + 1];
        char char3 = ch[i - 2][j + 2];
        testWinCombinatoin(char1, char2, char3, step);
    }

    public void testWinCombinatoin(char char1, char char2, char char3, char step) {
        if (char1 != '\u0000' & char2 != '\u0000' & char3 != '\u0000') {
            if (char1 == char2 && char2 == char3) {
                printWinner(step);
            }
        }
    }

    public void printWinner(char step) {
        if (step == 'X') {
            System.out.println(GAMER1 + " победил");
            System.exit(0);
        } else {
            System.out.println(GAMER2 + " победил");
            System.exit(0);
        }
    }

    public void erazeField() {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                Field[i][j] = ' ';
            }
        }
    }
}
   /* public void testWinLine2(char[][] ch, char step) {
        for (int i = 0; i < ch.length; i++) {
            char[] winArray = new char[ch.length]; //массив из всех элементов строки
            for (int j =0; j < ch.length; j++){
                winArray[j] = ch[j][i];
            }
            if (winSize == DEFOLT_WIN_SIZE){
                if (winArray[0] != '\u0000' & winArray[1] != '\u0000' & winArray[2] != '\u0000') {
                    if (winArray[0] == winArray[1] && winArray[1] == winArray[2]) {
                        printWinner(step);
                    }
                }
            }
            else {
                char[] winComb = new char[WIN_SIZE];
                int j = 0;
                while (j < (ch.length - WIN_SIZE)){
                    for (int i = 0; i < WIN_SIZE; i++){
                         winComb[i] = winArray[j + i];
                    }
                    winComb.testWinComb5();
                    j++;
                }
            }
        }
   }*/
//                6     8     10
// 00 10 20 30 40 50 60 70 80 90
// 01 11 21 31 41 51 61 71 81 91
// 02 12 22 32 42 52 62 72 82 92