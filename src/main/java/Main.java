import java.util.Random;
//Строгонов Вячеслав 8 группа Вариант 1
public class Main {
    public static void main(String[] args) {
        Random rand = new Random();
        System.out.println("Пример работы");
        Cholesky example = new Cholesky(10, 4);
        example.solve(-10, 10);
        example.lent.showExactX();
        example.lent.showX();
        System.out.println("Относительная ошибка: " + example.relativeErr);
        example = null;
        System.out.println(" ");
        //Тест 1. Случайная матрица, с особым соотношением L/N
        System.out.println("Tест 1. Случайная матрица, с особым соотношением L/N");
        System.out.println("-----------------------------------------------------");
        for (int i = 10; i <= 100; i *= 10) {
            System.out.println("Размер (N): [" + i + "-" + i * 10 + "]");
            //1/10
            int randN = rand.nextInt(i * 9) + i;
            System.out.println("L/N=1/10 ");
            example = new Cholesky(randN, randN / 10);
            example.solve(-10, 10);
            System.out.println("Относительная ошибка: " + example.relativeErr);
            example = null;

            //1/4
            randN = rand.nextInt(i * 9) + i;
            System.out.println("L/N=1/4 ");
            example = new Cholesky(randN, randN / 4);
            example.solve(-10, 10);
            System.out.println("Относительная ошибка: " + example.relativeErr);
            example = null;
        }
        System.out.println(" ");
        //Тест 2. Случайная матрица N=L
        System.out.println("Тест 2. Случайная матрица N=L");
        System.out.println("------------------------------");
        for (int i = 10; i <= 100; i *= 10) {
            System.out.println("Размер (N): [" + i + "-" + i * 10 + "]");
            for (int j = 0; j < 2; j++) {

                int randN = rand.nextInt(i * 9) + i;
                System.out.println("L/N=1/10 ");
                example = new Cholesky(randN, randN);
                example.solve(-10, 10);
                System.out.println("Относительная ошибка: " + example.relativeErr);
                example = null;
            }

        }
        System.out.println(" ");
        //Тест 3. Плохо обусловленная случайная матрица

        System.out.println("Тест 3.  Плохо обусловленная случайная матрица");
        System.out.println("-----------------------------------------------");
        System.out.println("Размер (N): [10-100]");
        for(int k=2; k<=6;k+=2){
            for(int i=0;i<2;i++){
                System.out.print("K: ");
                int randN = rand.nextInt(90)+10;
                example = new Cholesky(randN,randN/2);
                example.solveBadMatrix(-10,10,k);
                System.out.println("Относительная ошибка: " + example.relativeErr);
                example=null;

            }
        }

    }}
