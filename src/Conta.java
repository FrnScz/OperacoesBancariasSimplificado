public class Conta {
    private String numeroConta;
    private String numeroAgencia;
    private int saldoEmConta;


    // CONSTRUTOR
    public Conta(String numeroConta, String numeroAgencia, int saldoEmConta) {
        this.numeroConta = numeroConta;
        this.numeroAgencia = numeroAgencia;
        this.saldoEmConta = saldoEmConta;
    }

    // GETTERS
    public String getNumeroConta() {
        return numeroConta;
    }

    public String getNumeroAgencia() {
        return numeroAgencia;
    }

    public int getSaldoEmConta() {
        return saldoEmConta;
    }

}
