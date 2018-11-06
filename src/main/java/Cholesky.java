import java.lang.Math;

public class Cholesky {
    LentMatrix lent;
    double relativeErr;

    Cholesky(int N, int L) {
        lent = new LentMatrix(N, L);
        relativeErr = 0;
    }

    //Относительная погрешность
    void relativeError() {
        double max;
        double q = 0.001;
        if (Math.abs(lent.exactX[0]) > q)
            max = Math.abs((lent.x[0] - lent.exactX[0]) / lent.exactX[0]);
        else
            max = Math.abs(lent.x[0] - lent.exactX[0]);
        for (int i = 1; i <= lent.N; i++) {
            double temp;
            if (Math.abs(lent.exactX[i]) > q)
                temp = Math.abs((lent.x[i] - lent.exactX[i]) / lent.exactX[i]);
            else
                temp = Math.abs(lent.x[i] - lent.exactX[i]);
            if (temp > max)
                max = temp;

        }
        relativeErr = max;
    }
    //функция решения,заполнения матриц,вычисление погрешности
    void solve(int left, int right) {
        do {
            lent.resetData();
            lent.fillA(left, right);
            lent.fillExactX(left, right);
            lent.mulByExact();

            while (!(solveBC(0.0001) && solveY(0.0001) && solveX()))
            {

                 lent.resetData();
                 lent.fillA(left,right);
                 lent.fillExactX(left,right);
                 lent.mulByExact();

             }


             relativeError();
        }while (relativeErr == 0);
        return;
    }

    //решение для плохо обусловленной матрицы
    void solveBadMatrix(int left,int right,int k){
        double q=0.00001;
        if(k==6)
            q=0;
        do{
            lent.resetData();
            lent.fillBadMatrix(left,right,k);
            lent.fillExactX(left,right);
            lent.mulByExact();
            while (!(solveBC(q) && solveY(q) && solveX()))
            {
                // задаем новую матрицу, если решение было нудачным
                lent.resetData(); // удаляем данные предыдущей матрицы
                lent.fillBadMatrix(left, right, k);
                lent.fillExactX(left, right);
                lent.mulByExact();
            }
            relativeError();
        } while (relativeErr == 0); //если = 0, то пересчитываем снова
        return;

    }

    //Вычисление BC
    boolean solveBC(double q) {
        for (int j = 1; j <= lent.N; j++) {
            for (int i = j; i <= kn(j); i++) {
                double S = lent.getA(i, j);
                for (int k = k0(i); k <= j - 1; k++)
                    S -= lent.getBC(i, k) * lent.getBC(k, j);
                lent.setBC(S, i, j);
            }

            for (int i = j + 1; i <= kn(j); i++) {
                double S = lent.getA(j, i);
                for (int k = k0(i); k <= j - 1; k++) {
                    S -= lent.getBC(j, k) * lent.getBC(k, i);
                }
                if (Math.abs(lent.getBC(j, j)) > q) {
                    lent.setBC(S / lent.getBC(j, j), j, i);
                } else {//плохой знаменатель
                    return false;
                }
            }

        }
return true;
    }

    boolean solveY(double q){
        for(int i=1; i<=lent.N;i++){
            lent.y[i] = lent.f[i];
            for(int k=k0(i);k<=i-1;k++)
                lent.y[i] -= lent.getBC(i,k)*lent.y[k];
            if(Math.abs(lent.getBC(i,i))>q)
                lent.y[i] /=lent.getBC(i,i);
            else
                return false;
        }
        return true;
    }

    boolean solveX(){
        for(int i=lent.N;i>=1;i--){
            lent.x[i] = lent.y[i];
            for(int k=i+1;k<=kn(i);k++){
                lent.x[i] -=lent.getBC(i,k)*lent.x[k];
            }
        }
        return true;
    }

    int k0(int i) {
        if (i <= lent.L)
            return 1;
        return i - lent.L + 1;
    }

    int kn(int i) {
        if (i <= lent.N - lent.L + 1)
            return i + lent.L - 1;
        return lent.N;
    }

}