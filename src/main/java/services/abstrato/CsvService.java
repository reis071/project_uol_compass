package services.abstrato;

import entities.fisico.produtos.Alimento;
import entities.fisico.produtos.Higiene;
import entities.fisico.produtos.Roupa;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvService {

    /*
     * Exporta uma lista de doações de roupas para um arquivo CSV.
     */
    public void exportarDoacoesRoupas(List<Roupa> roupas, String caminhoArquivo) throws IOException {
        FileWriter csvWriter = new FileWriter(caminhoArquivo);

        // Escreve o cabeçalho do CSV
        csvWriter.append("Descrição,Tamanho,Sexo\n");

        // Escreve cada roupa no arquivo CSV
        for (Roupa roupa : roupas) {
            csvWriter.append(roupa.getDescricao()).append(",");
            csvWriter.append(roupa.getTamanho().toString()).append(",");
            csvWriter.append(roupa.getSexo().toString()).append("\n");
        }

        // Finaliza a escrita e fecha o arquivo
        csvWriter.flush();
        csvWriter.close();
    }

    /*
     * Exporta uma lista de doações de produtos de higiene para um arquivo CSV.
     */
    public void exportarDoacoesHigiene(List<Higiene> higienes, String caminhoArquivo) throws IOException {
        FileWriter csvWriter = new FileWriter(caminhoArquivo);

        // Escreve o cabeçalho do CSV
        csvWriter.append("Descrição\n");

        // Escreve cada produto de higiene no arquivo CSV
        for (Higiene higiene : higienes) {
            csvWriter.append(higiene.getDescricao()).append("\n");
        }

        // Finaliza a escrita e fecha o arquivo
        csvWriter.flush();
        csvWriter.close();
    }

    /*
     * Exporta uma lista de doações de alimentos para um arquivo CSV.
     */
    public void exportarDoacoesAlimentos(List<Alimento> alimentos, String caminhoArquivo) throws IOException {
        FileWriter csvWriter = new FileWriter(caminhoArquivo);

        // Escreve o cabeçalho do CSV
        csvWriter.append("Descrição,Quantidade,Unidade de Medida,Data de Validade\n");

        // Escreve cada alimento no arquivo CSV
        for (Alimento alimento : alimentos) {
            csvWriter.append(alimento.getDescricao()).append(",");
            csvWriter.append(String.valueOf(alimento.getQuantidade())).append(",");
            csvWriter.append(alimento.getUnidadeMedida().toString()).append(",");
            csvWriter.append(alimento.getDataValidade().toString()).append("\n");
        }

        // Finaliza a escrita e fecha o arquivo
        csvWriter.flush();
        csvWriter.close();
    }
}
