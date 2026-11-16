package br.com.lasanheiros.model;

public class CarroUsado extends Carros {

    private int odometro;
    private boolean revisado;
    private float precoFipe;  //Fipe é a tabela de preços médios para veículos seminovos/usados no Brasil
    private boolean garantiaExtendida;


    //Construtor da classe
    public CarroUsado(int idDoVeiculo, String marca, String modelo, String cor, int anoFabricacao, int odometro, boolean revisado, float precoFipe, boolean garantiaExtendida) {

        //Atributos herdados de classe mãe
        super(idDoVeiculo, marca, modelo, cor, anoFabricacao);

        //Atribuindo os atributos específicos dessa classe e o ID para a compra
        this.idDoVeiculo = idDoVeiculo;
        this.odometro = odometro;
        this.revisado = revisado;
        this.precoFipe = precoFipe;
        this.garantiaExtendida = garantiaExtendida;

    }

    //Metodo para exibir os detalhes da compra
    //O metodo usa a parte criada na classe mãe (que vale para CarroNovo também) junto com infomações específicas úteis para essa classe
    @Override
    public void exibirDetalhes() {

        System.out.println("<<<USADO>>>");
        System.out.println("ID: " + this.idDoVeiculo);

        //Trazendo os detalhes da classe mãe
        super.exibirDetalhes();

        //Detalhes específicos dessa classe
        System.out.println("ODÔMETRO: " + this.odometro);

        //Fiz isso para exibir um texto diferente de false ou true, que é meio estranho
        if (this.revisado) {
            System.out.println("REVISÃO: Realizada pela concessionária");
        } else {
            System.out.println("REVISÃO: Não realizada");
        }

        //Também para a garantia extendia
        if (this.garantiaExtendida) {
            System.out.println("GARANTIA EXTENDIDA: Sim (24 meses)");
        } else {
            System.out.println("GARANTIA EXTENDIDA: Não aplicável");
        }

        System.out.println("PREÇO: R$" + this.getPrecoFinal());


        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
    }


    //Definindo a lógica do preço para essa classe específica (que é diferente da classe CarroNovo)
    @Override
    public float getPrecoFinal() { //usado para exibir detalhes do carro (dentro dessa classe) e para calcular o total a ser pago (dentro de Venda)

        if (this.garantiaExtendida) {
            return (precoFipe + (precoFipe * 0.03f) + 2000f);
        } else {
            return (precoFipe + (precoFipe * 0.03f));
        }
    }

    //Metodos Getters e Setters (sé necessários)

    public int getOdometro(){    //Usado no metodo atualizarArquivoEstoque() quando for preciso reescrever o arquivo novamente (toda vez que algo for alterado)
        return this.odometro;
    }
    public boolean isRevisado(){  //Usado no metodo atualizarArquivoEstoque() quando for preciso reescrever o arquivo novamente (toda vez que algo for alterado)
        return this.revisado;
    }
    public float getPrecoFipe(){  //Usado no metodo atualizarArquivoEstoque() quando for preciso reescrever o arquivo novamente (toda vez que algo for alterado)
        return this.precoFipe;
    }
    public boolean isGarantiaExtendida(){  //Usado no metodo atualizarArquivoEstoque() quando for preciso reescrever o arquivo novamente (toda vez que algo for alterado)
        return this.garantiaExtendida;
    }

}


