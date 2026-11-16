package br.com.lasanheiros.model;

public class Cliente {

    private String nome;
    private String cpf;

    public Cliente(String nome, String cpf){
        this.nome = nome;
        this.cpf = cpf;
    }

    //Esse metodo só confirma se o cadastro do cliente deu certo.
    //Poderiamos só mostrar a mensagem "Cliente cadastrado com sucesso!" no Main depois de instanciar o clienteAtual, mas não haveria a verificação real disso
    public void confirmacaoDeRegistroDeCliente(){
        if(this.nome != null && this.cpf != null){
            System.out.println("Cliente cadastrado com sucesso!");
        }
    }

    //Metodos Getters e Setters (se necessários)

    public String getNome(){  //Usado para gerar o recibo em Venda
        return this.nome;
    }
    public String getCpf(){  //Usado para gerar o recibo em Venda
        return this.cpf;
    }
}

