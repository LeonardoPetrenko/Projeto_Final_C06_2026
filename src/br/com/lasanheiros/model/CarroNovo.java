package br.com.lasanheiros.model;

import java.util.Random;

//Herança de Carros
public class CarroNovo extends Carros {

    private int garantiaDeFabricaDias;
    private int tempoDeEsperaDias;     //Por ser carro zero é bem comum no Brasil não haver estoque na própria loja. Normalmente entre 30 a 60 dias
    private float precoMercado;        //preço cobrado pela montadora


    //Esse construtor é usado dentro de Estoque quando adicionamos um carro novo (carro zero km) ao estoque e também para reescrever o arquivo em atualizarArquivoEstoque()
    public CarroNovo(int idDoVeiculo, String marca, String modelo, String cor, int anoFabricacao, int garantiaDeFabricaDias, float precoMercado) {

        super(idDoVeiculo, marca, modelo, cor, anoFabricacao); //chamando o construtor da classe mãe para inicializar os atributos comuns a todas as filhas

        //Inicializando os atributos exclusivos dessa classe
        this.garantiaDeFabricaDias = garantiaDeFabricaDias;
        this.precoMercado = precoMercado;

        //Gera um número aleátorio entre 30 e 60 que representa o tempo, em dias, que o cliente deve esperar para o seu carro novo chegar à concessionária
        Random random = new Random();
        this.tempoDeEsperaDias = random.nextInt((60 - 30) + 1) + 30;
    }


    //Exibindo os detalhes da compra
    @Override
    public void exibirDetalhes() {

        System.out.println("<<<NOVO>>>");
        System.out.println("ID: " + this.idDoVeiculo);

        super.exibirDetalhes();

        System.out.println("GARANTIA DE FÁBRICA: " + this.garantiaDeFabricaDias + " dias");
        System.out.println("PRAZO PARA ENTREGA: " + this.tempoDeEsperaDias + " dias");
        System.out.println("PREÇO: R$" + this.getPrecoFinal());

        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
    }

    //diferente de CarroUsado, aqui o preço a ser pago é o preço que a montadora cobra
    @Override
    public float getPrecoFinal() {  //usado para exibir detalhes do carro (dentro dessa classe) e para calcular o total a ser pago (dentro de Venda)
        return this.precoMercado;
    }


    //Metodos Getters e Setters (apenas os necessários)

    public int getGarantiaDeFabricaDias(){  //É usado no metodo de sincronizar o vetor e o arquivo lá em Estoque. Necessário para trazer a informação que precisa
        return this.garantiaDeFabricaDias;
    }
    public float getPrecoMercado(){  //É usado no metodo de sincronizar o vetor e o arquivo lá em Estoque. Necessário para trazer a informação que precisa
        return this.precoMercado;
    }

}


