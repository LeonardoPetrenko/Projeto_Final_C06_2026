package br.com.lasanheiros.repository;

import br.com.lasanheiros.model.CarroNovo;
import br.com.lasanheiros.model.CarroUsado;
import br.com.lasanheiros.model.Carros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Estoque {

    private Carros[] estoque;  //A concessionária tem um galpão que cabem 100 carros
    private int totalCarros;   //Vai armazenar a quantidade de carros presetes no estoque

    //Inicializa as únicas duas variáveis disponíveis para Estoque
    public Estoque(){
        this.estoque = new Carros[100];
        this.totalCarros = 0;
    }


    //Esse metodo identifica todos os carros presentes no arquivo e faz o instanciamento (cria um objeto) para cada um.
    //Se na posição 0 (antes do primeiro split) de cada linha do arquivo estiver escrito "Novo", o metodo vai instanciar aquele carro como CarroNovo.
    //Se na posição 0 (antes do primeiro split) de cada linha do arquivo estiver escrito "Usado", o metodo vai instanciar aquele carro como CarroUsado
    public void carregarEstoque(){

        try(BufferedReader leitor = new BufferedReader((new FileReader("estoque.txt")))){

            String linha;
            while((linha = leitor.readLine()) != null && totalCarros < estoque.length){

                String[] dados = linha.split("-");
                String tipo = dados[1];

                if(tipo.equals("Novo")){

                    int id = Integer.parseInt(dados[0]);
                    int ano = Integer.parseInt(dados[5]);
                    int garantiaFabricaDias = Integer.parseInt(dados[6]);
                    float precoMercado = Float.parseFloat(dados[7]);

                    CarroNovo novo = new CarroNovo(id, dados[2], dados[3], dados[4], ano, garantiaFabricaDias, precoMercado);

                    estoque[totalCarros] = novo;
                    totalCarros++;
                }
                else if(tipo.equals("Usado")){

                    int id = Integer.parseInt(dados[0]);
                    int ano = Integer.parseInt(dados[5]);
                    int odometro = Integer.parseInt(dados[6]);
                    boolean revisado = Boolean.parseBoolean(dados[7]);
                    float precoFipe = Float.parseFloat(dados[8]);
                    boolean garantiaExtendida = Boolean.parseBoolean(dados[9]);

                    CarroUsado usado = new CarroUsado(id, dados[2], dados[3], dados[4], ano, odometro, revisado, precoFipe, garantiaExtendida);

                    estoque[totalCarros] = usado;
                    totalCarros++;
                }
            }

            System.out.println("Estoque disponível: " + totalCarros);
        }
        catch(Exception e){
            System.out.println("Erro ao ler estoque: " + e.getMessage());
        }
    }



    public void removerCarroVendidoDoEstoque(Carros[] carrosVendidos, int totalCarrosVendidos){

        boolean removidos = false;

        //Percorrendo o vetor de carros vendidos
        for(int i=0 ; i<totalCarrosVendidos ; i++){
            Carros carroEscolhido = carrosVendidos[i];

            int encontrado = -1;

            //Com o carro a ser removido armazenado em carroEscolhido, temos que procurá-lo no estoque
            for(int j=0 ; j<totalCarros ; j++){
                if(estoque[j] == carroEscolhido){

                    encontrado = j;
                    break;
                }
            }

            //Tendo encontrado o carro a ser removido no estoque, isso é, encontrado a sua posição dentro do vetor estoque (encontrado = j), precisamos
            //remove-lo e reorganizar o vetor estoque, já que é um vetor de tamanho fixo (100)
            if(encontrado != -1){

                for(int j=encontrado ; j<totalCarros-1 ; j++){
                    estoque[j] = estoque[j + 1];
                }

                estoque[totalCarros - 1] = null;
                totalCarros--;
                removidos = true;
            }
        }

        if(removidos){
            atualizarArquivoEstoque();
        }
    }


    //Esse metodo sincroniza o vetor estoque[] (que existe apenas na memoria RAM durante a execução do programa) com o arquivo "estoque.txt" (salvo no armazenamento interno da máquina)
    //Toda que vez que removemos um carro (usando o metodo de remoção) ou adicionamos um carro (usando salvarNoVetorEArquivo(...)). Isso ajuda em caso de ocorrência de um erro (arquivo aberto em outro lugar),
    //o programa não finalizar sozinho, já que temos um try-catch aqui dentro. Basicamente, sem esse metodo de regravação do arquivo, toda midificação feita só existiria durante a execução do programa, depois de
    //finalizado, o arquivo permaneceria intacto, como se nada tivesse mudado (tendando explicar com minhas palavras).
    private void atualizarArquivoEstoque(){

        try(BufferedWriter escritor = new BufferedWriter(new FileWriter("estoque.txt"))){

            for(int i = 0; i< totalCarros; i++){

                Carros carro = estoque[i];

                if(carro instanceof CarroNovo){
                    CarroNovo newCar = (CarroNovo) carro;

                    escritor.write(newCar.getIdDoVeiculo() + "-Novo-" + newCar.getMarca() + "-" + newCar.getModelo() + "-" + newCar.getCor() + "-" + newCar.getAnoFabricacao() + "-" + newCar.getGarantiaDeFabricaDias() + "-" + newCar.getPrecoMercado());
                }
                else if(carro instanceof CarroUsado){
                    CarroUsado oldCar = (CarroUsado) carro;

                    escritor.write(oldCar.getIdDoVeiculo() + "-Usado-" + oldCar.getMarca() + "-" + oldCar.getModelo() + "-" + oldCar.getCor() + "-" + oldCar.getAnoFabricacao() + "-" + oldCar.getOdometro() + "-" + oldCar.isRevisado() + "-" + oldCar.getPrecoFipe() + "-" + oldCar.isGarantiaExtendida());
                }

                escritor.newLine();
            }
        }
        catch (Exception e){
            System.out.println("Erro ao atualizar o estoque: " + e.getMessage());
        }

    }


    //Os dois metodos abaixo usam polimorfismo por sobrecarga (overload)
    //Esse metodo, junto com o metodo logo abaixo dele, formam uma sobrecarga de metodos usada para adicionar um carro ao estoque. Esse aqui é para carros novos
    public void adicionarCarroAoEstoque(int id, String marca, String modelo, String cor, int ano, int garantiaDeFabricaDias, float precoMercado){
        CarroNovo novoCarro = new CarroNovo(id, marca, modelo, cor, ano, garantiaDeFabricaDias, precoMercado);

        salvarNoVetorEArquivo(novoCarro);
    }

    //Esse é para carros usados
    public void adicionarCarroAoEstoque(int id, String marca, String modelo, String cor, int anoFabricacao, int odometro, boolean revisado, float precoFipe, boolean garantiaExtendida){
        CarroUsado novoCarro = new CarroUsado(id, marca, modelo, cor, anoFabricacao, odometro, revisado, precoFipe, garantiaExtendida);

        salvarNoVetorEArquivo(novoCarro);
    }


    //Esse metodo serve para salvar um carro no vetor estoque (quando for adicionado um) e também sincroniza o arquivo usando atualizarArquivoEstoque()
    private void salvarNoVetorEArquivo(Carros carroAdicionado){

        if(totalCarros < estoque.length){
            estoque[totalCarros] = carroAdicionado;
            totalCarros++;

            atualizarArquivoEstoque();

            System.out.println("\nCarro adicionado com sucesso!");
            System.out.println("ID gerado para esse veículo: " + carroAdicionado.getIdDoVeiculo());
        }
        else{
            System.out.println("ERRO: O estoque da concessionária está cheio");
        }
    }


    //Esse metodo recebe um ID e encontra o carro a qual ele pertence
    //Usado para verificar se o ID gerado aleatoriamente na opção 2 (adicionar carro ao estoque) do Main já não existe em outro carro
    //Usado também para buscar as informações do carro escolhido para compra antes de inserir esse carro no vetor de carrosVendidos (dentro da opção 3 do Main)
    public Carros buscarCarroPeloID(int id){

        for(int i = 0; i< totalCarros; i++){

            if(estoque[i].getIdDoVeiculo() == id){
                return estoque[i];
            }
        }

        return null; //Caso o ID recebido no parametro não exista
    }

    //Metodos Getters e Setters (se necessários)

    public Carros[] getEstoque(){  //usado em Main para mostrar os carros disponíveis no estoque dentro da opção 3 (venda de carro)
        return this.estoque;
    }
    public int getTotalCarros(){  //usado para verificar se existem carros no estoque antes de vender e também como limite do for que printa os detalhes de cada carro do estoque (no mesmo for onde tem getEstoque)
        return this.totalCarros;
    }


}

