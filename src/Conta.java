public class Conta {
    private String numeroConta;
    private String numeroAgencia;
    private int saldoEmConta;
    private String senha;


    // CONSTRUTOR

    public Conta(String numeroConta, String numeroAgencia, int saldoEmConta, String senha) {
        this.numeroConta = numeroConta;
        this.numeroAgencia = numeroAgencia;
        this.saldoEmConta = saldoEmConta;
        this.senha = senha;
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

    public String getSenha() {
        return senha;
    }

}
