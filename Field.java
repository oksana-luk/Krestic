import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
  Created by Оксана on 18.11.2014.
 */
public class Field {

    public int fieldSize;

    public int winSize;

    public static final int DEFOLT_FIELD_SIZE = 3;

    public final int DEFOLT_WIN_SIZE = 3;

    public final int WIN_SIZE = 5;

    public char[][] Field;

    public static final String GAMER1 = "Игрок 1";

    public static final String GAMER2 = "Игрок 2";

    public static final char KRESTIK = 'X';

    public static char step;

    public static int counter = 2;

    public static final char NOLIK = 'O';

    public Field() {
        this(DEFOLT_FIELD_SIZE);
    }

    public Field(int size) {
        fieldSize = size;
        Field = new char[fieldSize][fieldSize];
        if (fieldSize == DEFOLT_FIELD_SIZE | fieldSize == (DEFOLT_FIELD_SIZE + 1)) {
            winSize = DEFOLT_WIN_SIZE;
        } else {
            winSize = WIN_SIZE;
        }
    }

    public void playGame() throws IOException {
        for (int k = counter; k <= fieldSize * fieldSize + 1; k++) {
            if ((k & 1) == 0) {
                System.out.println(GAMER1 + ", ваш ход.");
                //System.out.println("Счетчик = " + counter);
                step = KRESTIK;
                doStep();
                counter++;
            } else {
                System.out.println(GAMER2 + ", ваш ход.");
                //System.out.println("Счетчик = " + counter);
                step = NOLIK;
                doStep();
                counter++;
            }
        }
        System.out.print("Ничья");
        System.exit(0);
    }

    public void doStep() throws IOException {
        System.out.print("Введите номер строки: ");
        int x = inputXY();
        System.out.print("Введите номер столбца: ");
        int y = inputXY();
        isEmptyField(x, y);
        Field[x][y] = step;
        showField();
        testWin();
    }

    public int inputXY() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String s = reader.readLine();
        if (s.isEmpty()) {
            System.out.println("Вы ничего не ввели.");
            playGame();
        }
        isDigitInput(s);
        int i = Integer.parseInt(s);
        illegalInput(i);
        return i;
    }

    //пррверка ввода на цифры
    public void isDigitInput(String s) throws IOException {
        char[] input = s.toCharArray();
        char ch1 = input[0];
        if (!Character.isDigit(ch1)) {
            System.out.println("Неверный ввод. Символы недопустимы.");
            playGame();
        }
    }

    //проверка ввода на допустимый диапазон кординат ячеек
    public void illegalInput(int k) throws IOException {
        if (k < 0 | k >= fieldSize) {
            System.out.println("Неверный ввод. Диапазон недопустимых чисел.");
            playGame();
        }

    }

    //проверка свободна ли ячейка
    public void isEmptyField(int x, int y) throws IOException {
        if (Field[x][y] != '\u0000') {
            System.out.println("Занято!");
            playGame();
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

    //запуск проверки на выйгрышные комбинации
    public void testWin() {
        testWinLine();
        testWinColum();
        testWinDiagonal();
    }

    //проверка пяти/трех элементов на равенство(победу)
    public void testWinCombination(char[] ch) {
        if (fieldSize == DEFOLT_FIELD_SIZE) {
            if (ch[0] == step && ch[0] == ch[1] && ch[1] == ch[2]) {
                printWinner();
            }
        } else {
            if (ch[0] == step && ch[0] == ch[1] && ch[1] == ch[2] && ch[2] == ch[3] && ch[3] == ch[4]) {
                printWinner();
            }
        }
    }

    // вывести на экран победителя
    public void printWinner() {
        if (step == KRESTIK) {
            System.out.println(GAMER1 + " победил!");
            System.exit(0);
        } else {
            System.out.println(GAMER2 + " победил!");
            System.exit(0);
        }
    }

    /*public void erazeField() {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                Field[i][j] = ' ';
            }
        }
    }*/

    //проверка строк на выйгрышную комбинацию для стандартного и большого поля
    public void testWinLine() {
        char[] winArray = new char[fieldSize]; //массив из всех элементов одной строки
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                winArray[j] = Field[j][i];
                //System.out.print(j + "" + i + " ");
            }
            test(winArray);
            //System.out.println();
        }
    }

    public void test(char[] ch) {
        if (fieldSize == DEFOLT_FIELD_SIZE) {
            testWinCombination(ch);
        } else {
            testWinInArray(ch);
        }

    }

    //выборка из массива элементов строки/столбца/диагонали
    // пяти подряд идущих элементов на проверку на выйгрышность
    public void testWinInArray(char[] ch) {
        char[] winComb = new char[WIN_SIZE];//массив из пяти подряд идущих элементов массива winArray
        int j = 0;
        while (j <= (ch.length - WIN_SIZE)) {
            for (int k = 0; k < WIN_SIZE; k++) {
                winComb[k] = ch[j + k];
            }
            testWinCombination(winComb);
            j++;
        }
    }

    //проверка столбцов на выйгрышную комбинацию для всех размеров поля
    public void testWinColum() {
        char[] winArray = new char[fieldSize]; //массив из всех элементов одного столбца
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                winArray[j] = Field[i][j];
            }
            test(winArray);
        }
    }

    //для стандартного поля только проверка диагоналей 2 и 3
    public void testWinDiagonal() {
        if (fieldSize == DEFOLT_FIELD_SIZE) {
            arrayOfDiagonal2();
            arrayOfDiagonal3();
        } else {
            arrayOfDiagonal1();
            arrayOfDiagonal2();
            arrayOfDiagonal3();
            arrayOfDiagonal4();
        }
    }

    //проверка части 1 диагоналей из правого верхнего угла на выйгрышную комбинацию для всех размеров поля
    public void arrayOfDiagonal1() {//(k=winSize-1; k< fieldSize; k++) (i=k;i>=0;i--)(j=0;j<=k;j++)
        for (int k = (winSize - 1); k < (fieldSize - 1); k++) {
            char[] winArray = new char[k + 1];//массив из элементов поля по одной диагонали
            int j = 0;
            for (int i = k; i >= 0; i--) {
                if (j <= k) {
                    winArray[j] = Field[i][j];
                    testWinInArray(winArray);
                    //System.out.print(i + "" + j);
                    j++;
                }
            }
            //System.out.println(1);
        }
    }

    //проверка части 2 диагоналей из правого верхнего угла на выйгрышную комбинацию для всех размеров поля
    public void arrayOfDiagonal2() {//(k=0; k <= (fieldSize-winSize); k++) (i=fieldSize-1; i>=k; i--) (j=k; j<fieldSize;j++)
        for (int k = 0; k <= (fieldSize - winSize); k++) {
            char[] winArray = new char[fieldSize - k];//массив из элементов поля по одной диагонали
            int j = k;
            for (int i = (fieldSize - 1); i >= k; i--) {
                if (j < (fieldSize)) {
                    winArray[j - k] = Field[i][j];
                    //System.out.print(i + "" + j + " ");
                    j++;
                }
            }
            test(winArray);
            //System.out.println();
        }
    }

    //проверка части 3 диагоналей из левого верхнего угла на выйгрышную комбинацию для всех размеров поля
    public void arrayOfDiagonal3() {//(k=fieldSize-win_size; k >= 0; k--)(i=k;i<fieldSize;i++)(j=0;j<fieldSize-k;j++)
        for (int k = (fieldSize - winSize); k >= 0; k--) {
            char[] winArray = new char[fieldSize - k];//массив из элементов поля по одной диагонали
            int j = 0;
            for (int i = k; i < fieldSize; i++) {
                if (j < (fieldSize - k)) {
                    winArray[j] = Field[i][j];
                    //System.out.print(i + "" + j);
                    j++;
                }
            }
            test(winArray);
            //System.out.println();
        }
    }

    //проверка части 4 диагоналей из левого верхнего угла на выйгрышную комбинацию для всех размеров поля
    public void arrayOfDiagonal4() {//(k=5;k>=0;k--)(i=0;i<fieldSize-k;i++) (j=k;j<fieldsize;j++)
        for (int k = (fieldSize - winSize); k > 0; k--) {
            char[] winArray = new char[fieldSize - k];//массив из элементов поля по одной диагонали
            int j = k;
            for (int i = 0; i < (fieldSize - k); i++) {
                if (j < (fieldSize)) {
                    winArray[j - k] = Field[i][j];
                    //System.out.print(i + "" + j + " ");
                    j++;
                }
            }
            test(winArray);
            //System.out.println();
        }
    }

    /*public void playGameAgain() throws IOException {
    }*/
}
//       3x3      6x6   8x8  10x10
// 00 10 20 30 40 50 60 70 80 90
// 01 11 21 31 41 51 61 71 81 91
// 02 12 22 32 42 52 62 72 82 92
// 03 13 23 33 43 53 63 73 83 93
// 04 14 24 34 44 54 64 74 84 94
// 05 15 25 35 45 55 65 75 85 95
// 06 16 26 36 46 56 66 76 86 96
// 07 17 27 37 47 57 67 77 87 97
// 08 18 28 38 48 58 68 78 88 98
// 09 19 29 39 49 59 69 79 89 99
//1
// (k=winSize-1; k< fieldSize; k++) (i=k;i>=0;i--)(j=0;j<=k;j++)
// 40 31 22 13 04 ij             (i=4; i>=0; i--) (j=0; j<=4; j++) 6x6
// 50 41 32 23 14 05             (i=5; i>=0; i--) (j=0; j<=5; j++) 6x6
// 60 51 42 33 24 15 06          (i=6; i>=0; i--) (j=0; j<=6; j++)
// 70 61 52 43 34 25 16 07       (i=7; i>=0; i--) (j=0; j<=7; j++)
// 80 71 62 53 44 35 26 17 08    (i=8; i>=0; i--) (j=0; j<=8; j++)
// 90 81 72 63 54 45 36 27 18 09 (i=9; i>=0; i--) (j=0; j<=9; j++)
//2
//(k=(fieldSize-winSize); k>0; k--) (i=fieldSize-1; i>=k; i--) (j=k; j<fieldSize;j++)
// 95 86 77 68 59             (i=9; i>=5; i--) (j=5; j<=9;j++)
// 94 85 76 67 58 49          (i=9; i>=4; i--) (j=4; j<=9; j++)
// 93 84 75 66 57 48 39       (i=9; i>=3; i--) (j=3; j<=9; j++)
// 92 83 74 65 56 47 38 29    (i=9; i>=2; i--) (j=2; j<=9; j++)
// 91 82 73 64 55 46 37 28 19 (i=9; i>=1; i--) (j=1; j<=9; j++)
//3
//(k=fieldSize-win_size; k >= 0; k--)(i=k;i/<fieldSize;i++)(j=0;j<fieldSize-k;j++)
// 50 61 72 83 94                (i=5; i<fieldSize; i++) (j=0; j<5;j++)
// 40 51 62 73 84 95             (i=4; i<fieldSize; i++) (j=0; j<6;;j++)
// 30 41 52 63 74 85 96          (i=3)(j=0;j<7)
// 20 31 42 53 64 75 86 97       (i=2)(j<8)
// 10 21 32 43 54 65 76 87 98    (i=1)(j<9)
// 00 11 22 33 44 55 66 77 88 99 (i=0)(j<10)
//4
//(k=5;k>=0;k--)(i=0;i<fieldSize-k;i++) (j=k;j<fieldsize;j++)
//05 16 27 38 49              (i=0;i<5;i++) (j=5;j<fieldSize;j++)
//04 15 26 37 48 59           (i=0;i<6;i++) (j=4;j<fieldSize;j++)
//03 14 25 36 47 58 69        (i=0;i<7;i++) (j=3;j<fieldSize;j++)
//02 13 24 35 46 57 68 79     (i=0;i<8;i++) (j=2;j<fieldSize;j++)
//01 12 23 34 45 56 67 78 89  (i=0;i<9;i++) (j=1;j<fieldSize;j++)(i=0;i<5;i++)(j=1;j<fieldSize;j++)
//00 11 22 33 44 55 66 77 99  (i=0;i<10;i++) (j=0;j<fieldSize;j++)(i=0;i<6;i++)(j=0;j<fieldSize;j++)