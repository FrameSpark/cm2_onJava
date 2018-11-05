public class LentMatrix {

    double[][] A;
    double [][]B;
    double [][]C;
    double []x;
    double []y;
    double []f;
    double []exactX;
    int N,L;
    LentMatrix(int N, int L) {
        this.N = N;
        this.L = L;
        x = new double[N+1];
        y = new double[N+1];
        exactX = new double[N+1];
        A=new double[N+1][2*L];
        B= new double[N+1][2*L];
        C = new double[N+1][2*L];
    }
}
