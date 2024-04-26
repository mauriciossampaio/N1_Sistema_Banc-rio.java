
public class SistemaBancario {
    public static void main(String[] args) {
        int numContas = 5;
        double saldoInicial = 1000;

        Banco banco = new Banco(numContas, saldoInicial);

        Thread[] threadsClientes = new Thread[numContas];
        for (int i = 0; i < numContas; i++) {
            threadsClientes[i] = new Thread(new Cliente(i, banco));
            threadsClientes[i].start();
        }

        for (Thread thread : threadsClientes) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        banco.imprimirSaldos();
    }
}
