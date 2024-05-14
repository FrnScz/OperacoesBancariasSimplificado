import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Random;

public class Principal {
    public static void main(String[] args) throws IOException {
        JOptionPane.showMessageDialog(null, "Bem vindo(a) ao menú do sistema bancário simplificado!");

        // VARIÁVEL QUE CRIA O CAMINHO DO ARQUIVO CONTAS.CSV
        var contas = Path.of("Contas.csv");

        // ESTRUTURA CONDICIONAL QUE CRIA UM ARQUIVO CSV (ARQUIVO DE TEXTO) CASO ELE FOR APAGADO OU NÃO EXISTIR
        if (!Files.exists(contas)) {
            Files.createFile(contas);
        }

        // VARIÁVEL QUE SERVE PARA O LOOP ABAIXO EXECUTAR O ALGORITMO DE SELEÇÃO DO MENU
        var opcaoDigitada = 0;

        // LOOP QUE EXECUTA AS FUNÇÕES COM A OPÇÃO DIGITADA
        while (opcaoDigitada != 7) { // Atualizado para incluir nova opção de exibição do código
            opcaoDigitada = exibirMenu();
            switch (opcaoDigitada) {
                case 1:
                    cadastrarContas(contas);
                    break;
                case 2:
                    adicionarSaldoConta(contas);
                    break;
                case 3:
                    realizarSaque(contas);
                    break;
                case 4:
                    listarContas(contas);
                    break;
                case 5:
                    removerConta(contas);
                    break;
                case 6:
                    transferirSaldo(contas);
                    break;
                case 7:
                    JOptionPane.showMessageDialog(null, "Operação finalizada!");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Digite uma opção válida");
                    break;
            }
        }
    }

    // MÉTODO QUE CADASTRA CONTAS E ATUALIZA O ARQUIVO CONTAS.CSV
    private static void cadastrarContas(Path arquivo) throws IOException {
        JOptionPane.showMessageDialog(null, "Você escolheu a opção: Cadastrar Contas");

        var numero = JOptionPane.showInputDialog("Digite o numero da conta");
        var agencia = JOptionPane.showInputDialog("Digite o numero da agência");

        var saldoEmConta = new Random().nextInt(10000);
        JOptionPane.showMessageDialog(null, "Saldo em conta: " + saldoEmConta);

        var contas = new Conta(numero, agencia, saldoEmConta);
        Files.writeString(arquivo, contas.getNumeroConta() + "," + contas.getNumeroAgencia() + "," + contas.getSaldoEmConta() + "\n", StandardOpenOption.APPEND);
    }

    // MÉTODO QUE IMPRIME TODAS AS INFORMAÇÕES DENTRO DO ARQUIVO CONTAS.CSV NO CONSOLE
    private static void listarContas(Path arquivo) throws IOException {
        var listaDeContas = Files.readAllLines(arquivo);

        var builder = new StringBuilder("Você escolheu a opção: Listar Contas\n\n");
        for (var listaContas : listaDeContas) {
            var linhas = listaContas.split(",");
            builder.append("Número da conta: ").append(linhas[0]).append(" - Agência: ").append(linhas[1]).append(" - Saldo em conta: R$ ").append(linhas[2]).append("\n");
        }
        JOptionPane.showMessageDialog(null, builder.toString());
    }

    // MÉTODO PARA REALIZAR SAQUE DE UMA CONTA DENTRO DO ARQUIVO CONTAS.CSV
    private static void realizarSaque(Path arquivo) throws IOException {
        JOptionPane.showMessageDialog(null, "Você escolheu a opção: Realizar Saque");

        var numeroConta = JOptionPane.showInputDialog("Digite o número da conta");
        var valorSaque = Integer.parseInt(JOptionPane.showInputDialog("Digite o valor a ser sacado"));

        var listaDeContas = Files.readAllLines(arquivo);
        for (int i = 0; i < listaDeContas.size(); i++) {
            var linha = listaDeContas.get(i).split(",");
            if (linha[0].equals(numeroConta)) {
                var saldoConta = Integer.parseInt(linha[2]);
                if (saldoConta >= valorSaque) {
                    saldoConta -= valorSaque;
                    listaDeContas.set(i, linha[0] + "," + linha[1] + "," + saldoConta);
                    Files.write(arquivo, listaDeContas);
                    JOptionPane.showMessageDialog(null, "Saque realizado com sucesso. Novo saldo: R$ " + saldoConta);
                    return;
                } else {
                    JOptionPane.showMessageDialog(null, "Saldo insuficiente.");
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Conta não encontrada");
    }

    // MÉTODO QUE REMOVE A CONTA DO ARQUIVO CONTAS.CSV E ATUALIZA O ARQUIVO
    private static void removerConta(Path arquivo) throws IOException {
        JOptionPane.showMessageDialog(null, "Você escolheu a opção: Remover conta");

        var numeroConta = JOptionPane.showInputDialog("Digite o numero da conta que deseja remover");

        var listaDeContas = Files.readAllLines(arquivo);
        for (int i = 0; i < listaDeContas.size(); i++) {
            var linha = listaDeContas.get(i).split(",");
            if (linha[0].equals(numeroConta)) {
                listaDeContas.remove(i);
                Files.write(arquivo, listaDeContas);
                JOptionPane.showMessageDialog(null, "Conta removida com sucesso!");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Conta não encontrada");
    }

    // MÉTODO PARA DEPOSITAR UM VALOR NA CONTA DIGITADA DO ARQUIVO CONTAS.CSV
    private static void adicionarSaldoConta(Path arquivo) throws IOException {
        JOptionPane.showMessageDialog(null, "Você escolheu a opção: Adicionar Saldo em Conta");

        var numeroConta = JOptionPane.showInputDialog("Digite o número da conta que deseja adicionar saldo");
        var valorDeposito = Integer.parseInt(JOptionPane.showInputDialog("Digite o valor que deseja depositar"));

        var listaDeContas = Files.readAllLines(arquivo);
        for (int i = 0; i < listaDeContas.size(); i++) {
            var linha = listaDeContas.get(i).split(",");
            if (linha[0].equals(numeroConta)) {
                var saldoConta = Integer.parseInt(linha[2]);
                saldoConta += valorDeposito;
                listaDeContas.set(i, linha[0] + "," + linha[1] + "," + saldoConta);
                Files.write(arquivo, listaDeContas);
                JOptionPane.showMessageDialog(null, "Depósito realizado com sucesso! Novo saldo: R$ " + saldoConta);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Conta não encontrada");
    }

    // MÉTODO PARA REALIZAR TRANSFERÊNCIA ENTRE CONTAS
    private static void transferirSaldo(Path arquivo) throws IOException {
        JOptionPane.showMessageDialog(null, "Você escolheu a opção: Transferir Saldo");

        var numeroContaOrigem = JOptionPane.showInputDialog("Digite o número da conta de origem");
        var numeroContaDestino = JOptionPane.showInputDialog("Digite o número da conta de destino");
        var valorTransferencia = Integer.parseInt(JOptionPane.showInputDialog("Digite o valor a ser transferido"));

        var listaDeContas = Files.readAllLines(arquivo);
        int indiceOrigem = -1, indiceDestino = -1;
        int saldoOrigem = 0, saldoDestino = 0;

        // Encontrar as contas de origem e destino
        for (int i = 0; i < listaDeContas.size(); i++) {
            var linha = listaDeContas.get(i).split(",");
            if (linha[0].equals(numeroContaOrigem)) {
                indiceOrigem = i;
                saldoOrigem = Integer.parseInt(linha[2]);
            }
            if (linha[0].equals(numeroContaDestino)) {
                indiceDestino = i;
                saldoDestino = Integer.parseInt(linha[2]);
            }
        }

        // Verificar se ambas as contas foram encontradas e se a transferência é possível
        if (indiceOrigem != -1 && indiceDestino != -1) {
            if (saldoOrigem >= valorTransferencia) {
                saldoOrigem -= valorTransferencia;
                saldoDestino += valorTransferencia;

                listaDeContas.set(indiceOrigem, numeroContaOrigem + "," + listaDeContas.get(indiceOrigem).split(",")[1] + "," + saldoOrigem);
                listaDeContas.set(indiceDestino, numeroContaDestino + "," + listaDeContas.get(indiceDestino).split(",")[1] + "," + saldoDestino);
                Files.write(arquivo, listaDeContas);

                JOptionPane.showMessageDialog(null, "Transferência realizada com sucesso!");
                JOptionPane.showMessageDialog(null, "Novo saldo da conta de origem: R$ " + saldoOrigem);
                JOptionPane.showMessageDialog(null, "Novo saldo da conta de destino: R$ " + saldoDestino);
            } else {
                JOptionPane.showMessageDialog(null, "Saldo insuficiente na conta de origem.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Conta de origem ou destino não encontrada.");
        }
    }

    // MÉTODO PARA EXIBIR O MENU PRINCIPAL
    private static int exibirMenu() {
        String menu = "Menu Principal\n" +
                "1 - Cadastrar Conta\n" +
                "2 - Adicionar saldo\n" +
                "3 - Sacar\n" +
                "4 - Listar contas\n" +
                "5 - Remover conta\n" +
                "6 - Transferir saldo\n" +
                "7 - Sair\n" +
                "Escolha a opção desejada:";
        return Integer.parseInt(JOptionPane.showInputDialog(menu));
    }
}