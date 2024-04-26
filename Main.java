class ContaBancaria {
	private double saldo;
    public ContaBancaria(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    public void sacar(double valor) {
        if (valor > 0 && valor <= saldo) {
            saldo -= valor;
        } else {
            System.out.println("Valor de saque inválido.");
        }
    }

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
        } else {
            System.out.println("Valor de depósito inválido.");
        }
    }

    public double getSaldo() {
        return saldo;
    }
}


class Banco {
    private final ContaBancaria[] contas;   
    public double saldo;
    public Banco(int numContas, double saldoInicial) {
        this.contas = new ContaBancaria[numContas]; // Inicializa o array de contas
        for (int i = 0; i < numContas; i++) {
            contas[i] = new ContaBancaria(saldoInicial);
            saldo += saldoInicial; // Adiciona o saldo inicial ao saldo total do banco
        }
    }


    public int numContas() {
        return contas.length;
    }
      public synchronized void transferencia(int contaOrigem, int contaDestino, double valor) {
        if (contaOrigem < 0 || contaOrigem >= contas.length ||
            contaDestino < 0 || contaDestino >= contas.length) {
            System.out.println("Conta(s) inválida(s)!");
            return;
        }

        if (contas[contaOrigem].getSaldo() >= valor) {
            contas[contaOrigem].sacar(valor);
            contas[contaDestino].depositar(valor);
            System.out.println("Transferência de R$ " + valor + " da conta " + contaOrigem + " para a conta " + contaDestino + " realizada com sucesso.");
        } else {
            System.out.println("Saldo insuficiente na conta " + contaOrigem + " para realizar a transferência.");
        }
    }
     public void imprimirSaldos() {
        for (int i = 0; i < contas.length; i++) {
            System.out.println("Saldo da conta " + i + ": " + contas[i].getSaldo());
        }
    }
}


class Cliente implements Runnable {
    private final int id;
    private final Banco banco;

    public Cliente(int id, Banco banco) {
        this.id = id;
        this.banco = banco;
    }

    @Override
    public void run() {
        int contaOrigem = id % banco.numContas();
        int contaDestino = (id + 1) % banco.numContas();
        double valor = 100; // Valor fixo para transferência

        banco.transferencia(contaOrigem, contaDestino, valor);
    }
}