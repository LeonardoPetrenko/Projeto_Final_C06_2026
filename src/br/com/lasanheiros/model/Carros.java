package br.com.lasanheiros.model;

public abstract class Carros {

    protected int idDoVeiculo;
    protected String marca;
    protected String modelo;
    protected String cor;
    protected int anoFabricacao;

    //Construtor que vai ser usado no super(...) das classes filhas
    public Carros(int idDoVeiculo, String marca, String modelo, String cor, int anoFabricacao) {

        this.idDoVeiculo = idDoVeiculo;
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
        this.anoFabricacao = anoFabricacao;
    }


    //Metodo para mostrar as informações dos carros. Vai ser incrementado com mais infomações em cada classe filha (polimorfismo)
    public void exibirDetalhes() {

        System.out.println("MARCA: " + this.marca);
        System.out.println("MODELO: " + this.modelo);
        System.out.println("ANO: " + this.anoFabricacao);
        System.out.println("COR: " + this.cor);
    }


    //Metodo abstrato que vai ser usado pelas classes filhas (polimorfismo)
    public abstract float getPrecoFinal();

    //metodos getters e setters (se necessários)
    public String getMarca(){  //Usado em Estoque no metodo que atualiza o arquivo e em Venda para mostrar o recibo
        return this.marca;
    }
    public String getModelo(){ //Usado em Estoque no metodo que atualiza o arquivo e em Venda para mostrar o recibo
        return this.modelo;
    }
    public String getCor(){  //Usado em Estoque no metodo que atualiza o arquivo e em Venda para mostrar o recibo
        return this.cor;
    }
    public int getAnoFabricacao(){  //Usado em Estoque no metodo que atualiza o arquivo
        return this.anoFabricacao;
    }
    public int getIdDoVeiculo(){  //Usado em Estoque no metodo que atualiza o arquivo, para buscar um carro pelo ID em Estoque e mostrar o ID do carro adicionado ao Estoque em salvarNoVetorEArquivo(Carros carroAdicionado)
        return this.idDoVeiculo;
    }

}



