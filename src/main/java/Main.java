public class Main {
    public static void main(String[] args) {
       Cholesky matr = new Cholesky(10,4);
       matr.solve(-10,10);
       matr.lent.showExactX();
       matr.lent.showX();


    }
}
