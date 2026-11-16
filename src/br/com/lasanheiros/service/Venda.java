package br.com.lasanheiros.service;

import br.com.lasanheiros.model.Carros;
import br.com.lasanheiros.model.Cliente;

import java.time.LocalDate;

public class Venda implements Transacao {

    //Não percebemos que apenas uma classe implementando uma interface faz com que a inteface não tenha sentido em existir, já que era só
    //adicionar os métodos aqui direto, mas só percebemos isso com 90% do projeto feito.


    private float valorTotal = 0;
    private LocalDate data = LocalDate.now();
    private Carros[] carrosVendidos;
    private Cliente novoCliente;
    private int totalCarrosVendidos;

    //O construtor recebe as informações definidas na opção 3 do Main
    public Venda(LocalDate data, Carros[] carrosVendidos, Cliente novoCliente, int totalCarrosVendidos){

        this.data = data;
        this.carrosVendidos = carrosVendidos;
        this.novoCliente = novoCliente;
        this.totalCarrosVendidos = totalCarrosVendidos;
    }

    //Implementado um metodo que a interface pede
    @Override
    public void calcularTotal(){
        for(int i=0 ; i<totalCarrosVendidos ; i++){
            this.valorTotal += carrosVendidos[i].getPrecoFinal();
        }
    }

    //Implementando um metodo que a interface pede
    @Override
    public void gerarRecibo(){

        System.out.println("\n==========================================================RECIBO DE VENDA========================================================\n");

        System.out.println("NOME: " + this.novoCliente.getNome());
        System.out.println("CPF: " + this.novoCliente.getCpf());

        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("CARROS COMPRADOS: " + this.totalCarrosVendidos);
        System.out.println("DATA DA COMPRA: " + this.data);

        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("VEÍCULOS COMPRADOS");

        for(int i=0 ; i<totalCarrosVendidos ; i++){
            if(carrosVendidos[i] != null){
                System.out.println(i+1 + ". " + carrosVendidos[i].getMarca() + " | " + carrosVendidos[i].getModelo() + " | " + carrosVendidos[i].getCor());
            }
        }

        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");

        System.out.println("TOTAL: R$" + this.valorTotal);


    }

}

