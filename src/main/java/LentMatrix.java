import java.util.Random;

public class LentMatrix {

    double[][] A; //Лента N*(2L-1)
    double [][]BС; //нижне и верхнетреугольные матрицы N*(2L-1)
    double []x; //вектор решения
    double []y; //вектор решения
    double []f; //правая часть
    double []exactX; //точное решение
    int N,L; //размерность системы и половина ширины ленты
    Random rand = new Random();

    LentMatrix(int N, int L) {
        this.N = N;
        this.L = L;
        f=new double[N+1];
        x = new double[N+1];
        y = new double[N+1];
        exactX = new double[N+1];
        A=new double[N+1][2*L];
        BС= new double[N+1][2*L];


    }


 //верхне и нижнетреугольная матрица A, используется для fillBadMatrix
    double getLow(int i,int j){
        if (Math.abs(j - i+L) >= 2*L) //если вышли за границы ленты
            return 0;
        if (Math.abs(i - j+L) >= 2*L) //если вышли за границы ленты
            return 0;

        if (j - i > 0) //если обращение к верхней части
            return 0;
        else //если нижняя часть
            return A[j][i - j + L];
    }

    double getHigh(int i,int j) {
        if (Math.abs(j - i + L) >= 2 * L) //если вышли за границы ленты
            return 0;
        if (Math.abs(i - j + L) >= 2 * L) //если вышли за границы ленты
            return 0;
        if (j - i >= 0) //если обращение к верхней части
            return A[i][j - i + L];
        else //если нижняя часть
            return 0;
    }

    //заполнение плохо обусловленной матрицы
    void fillBadMatrix(int left,int right,int k){
        fillA(left,right);
        double k1=1;
        for(int i=0; i<k;i++){
            setA(getA(i,i)/k1,i,i);
        }
        for(int i=1;i<=N;i++){
            for(int j=1;j<=N;j++){
                setBC(0,i,j);
                for(int p=1; p<=N;p++){
                    setBC(getBC(i,j) + getLow(i,p)*getHigh(p,j),i,j);
                }
            }
        }
        for(int i=1; i<=N;i++){
            for(int j=1;j<=N;j++){
                setA(getBC(i,j),i,j);
            }
        }
        BС=null;
        BС= new double[N+1][2*L];
    }
    //Побочные функции
    double randomDouble(int left,int right){

        double z=0;
        double z1=0;

        while(z==0.0){
               z=rand.nextDouble();
           }
            while(z1==0){
                z1 = rand.nextInt(right)+left;
            }
        return z+z1;
    }
   //Умножение матрицы A на заданный вектор точного решения
    void mulByExact()
    {
        for (int i = 1; i <= N; i++)
        {
           f[i]=0;
            for (int j = 1; j <= N; j++)
               f[i] += exactX[j] * getA(i, j);
        }
    }


    //Обращения к матрицам
    double getA(int i, int j){
        if (Math.abs(j - i+L) >= 2*L) //если вышли за границы ленты
            return 0;
        if (Math.abs(i - j+L) >= 2*L) //если вышли за границы ленты
            return 0;
        if (j - i >= 0) //если обращение к верхней части
            return A[i][j - i + L];
        else //если нижняя часть
            return A[j][i - j + L];
}

 void setA(double value,int i, int j){
     if (Math.abs(j - i+L) >= 2*L) //если вышли за границы ленты
         return;
     if (Math.abs(i - j+L) >= 2*L) //если вышли за границы ленты
         return ;
     if (j - i >= 0) //если обращение к верхней части
         A[i][j - i + L] = value;
     else //если нижняя часть
         A[j][i - j + L] = value;
 }

 double getBC(int i,int j){
        if(Math.abs(j-i+L) >= 2*L)
            return 0;
     if (Math.abs(i - j+L) >= 2*L) //если вышли за границы ленты
         return 0 ;
        return BС[i][j-i+L];
 }

 void setBC(double value, int i, int j){
        if(Math.abs(j-i+L) >= 2*L){
            return;
        }
     if (Math.abs(i - j+L) >= 2*L) //если вышли за границы ленты
         return  ;
        BС[i][j-i+L]=value;
 }

    void fillA(int left,int right){
        for(int i=1;i<=N;i++){
            for(int j=1;j<=(2*L-1);j++){
                A[i][j]=randomDouble(left,right);
            }
        }
    }

    void fillExactX(int left,int right){
        for(int i=1; i<=N;i++){
            exactX[i]=randomDouble(left,right);
        }
    }

    //Вывод на экран
    void showA(){
        System.out.println("A matrix: ");
        for(int i=1;i<=N;i++){
            for(int j=1; j<=N; j++){
                System.out.print(getA(i,j) + " ");
            }
            System.out.println();
        }
    }
    void showBC(){
        System.out.println("BC matrix: ");
        for (int i=1; i<=N;i++){
            for(int j=1; j<=N;j++){
                System.out.print(getBC(i,j) + " ");
            }
        }
    }

    void showExactX()
    {
        System.out.println("Exact X: ");
        for (int i = 1; i <= N; i++)
            System.out.println(exactX[i]);

    }
    void showX()
    {
        System.out.println("Solution: " );
        for (int i = 1; i <= N; i++)
            System.out.println(x[i]);

    }

    void resetData(){ //сброс данных
        f = null;
        x = null;
        y = null;
        exactX = null;
        A=null;
        BС =  null;
        x = new double[N+1];
        y = new double[N+1];
        exactX = new double[N+1];
        A=new double[N+1][2*L];
        BС= new double[N+1][2*L];

    }




}

