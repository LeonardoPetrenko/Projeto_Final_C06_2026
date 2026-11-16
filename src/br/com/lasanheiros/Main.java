package br.com.lasanheiros;

import br.com.lasanheiros.model.Carros;
import br.com.lasanheiros.model.Cliente;
import br.com.lasanheiros.repository.Estoque;
import br.com.lasanheiros.service.Venda;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        //Instanciando o estoque lendo o arquivo
        Estoque estoqueDoDia = new Estoque();
        estoqueDoDia.carregarEstoque();

        Cliente clienteAtual = null;  //Instanciando um cliente

        int opcao;


        do{

            System.out.println("\n===============================================SISTEMA DA CONCESSIONÁRIA LASANHEIROS=============================================\n");
            System.out.println("1 - Registrar novo cliente");
            System.out.println("2 - Adicionar carro ao estoque");
            System.out.println("3 - Vender carro");
            System.out.println("4 - Encerrar sistema");

            //Observação: Não sei se faz sentido usar try-catch para todas essas verificações de tipo, talvez apenas um if-else resolveria. Porém, como o uso de try-catch é uma
            //exigência, decidimos usar aqui também para garantir que haja bastante tratamento de erros, e não somente na classe Estoque. Usamos apenas três try-catch que julgamos
            //essenciais: um para ler as entradas do menu (logo abaixo do comentário), outro quando vamos inserir o ID do carro que será vendido e outro para verificar a entrada
            //da opção 2
            try{
                System.out.print("\nDigita a opção desejada: ");
                opcao = entrada.nextInt();
                entrada.nextLine();   //Limpa o ENTER no buffer para evitar algum problema futuro
            }
            catch(InputMismatchException e){

                //Caso seja inserido algo diferente de int, o catch entra para impedir o erro, pois ele identifica o InputMismatchException
                System.out.println("ERRO: Digite um número inteiro correspondente às opções do menu.");
                entrada.nextLine();  //Limpa a entrada inválida do buffer. Isso impede um loop eterno que pode acontecer caso a última entrada não seja apagada
                opcao = 0;           //Garante que a variável não fique com algum valor antigo e errado

                continue;  //Força o programa a parar e voltar ao início do loop, pedindo para inserir opcao novamente (do-while é o laço de repetição mais próximo,
                           //por isso ele volta para lá)
            }

            if(opcao == 1){
                System.out.println("\n-----------------------------------------------------REGISTRAR NOVO CLIENTE------------------------------------------------------\n");

                //Esse if verifica se o cliente já não foi cadastrado antes. Decidi manter um cadastro de cliente a cada execução do programa
                if(clienteAtual != null){
                    System.out.println("Cliente já cadastrado para esta seção.\nReinicie o sistema para cadastrar um novo");
                    System.out.println("\nENTER para voltar ao menu");
                    entrada.nextLine();
                }
                else{

                    System.out.print("NOME: ");
                    String nome = entrada.nextLine();

                    System.out.print("CPF: ");
                    String cpf = entrada.nextLine();

                    //Depois de verificar e inserir as informações do cliente, finalmente instanciamos um
                    clienteAtual = new Cliente(nome, cpf);
                    clienteAtual.confirmacaoDeRegistroDeCliente();  //Isso apenas nos dá um retorno indicando que o cliente foi cadastrado

                    System.out.println("\nENTER para voltar ao menu");
                    entrada.nextLine();
                }
            }

            else if(opcao == 2){
                System.out.println("\n-----------------------------------------------------ADICIONAR NOVO CARRO---------------------------------------------------------\n");

                //Esse try-catch garante que a entrada seja int
                try{
                    System.out.println("Qual o estado do automóvel?");
                    System.out.println("1 - Usado/Seminovo");
                    System.out.println("2 - Novo");

                    System.out.print("R: ");
                    int estadoCarro = entrada.nextInt();
                    entrada.nextLine();  //Limpa o buffer que foi ocupado com o ENTER apos inserir estadoCarro

                    //Se digitar um int, vai ser aprovado pelo try-catch, mas não necessariamente é um valor válido. Esse if verifica se a entrada foi
                    //uma das opções válidas (1 ou 2)
                    if(estadoCarro != 1 && estadoCarro != 2){
                        System.out.println("ERRO: Opção inválida");

                        continue;  //Esse continue encerra o try-catch e volta para o menu. Ele volta para o laço de repetição mais próximo, que é o do-while, verifica a condição
                                   // opcao != 4 (no momento temos opcao == 2) e refaz o do-while
                                   //Ele não volta para o começo do try, pois try-catch não é um laço de repetição como for e while/do-while
                    }

                    //Gera um número aleátorio para ser o ID do carro
                    Random rand = new Random();
                    int idAleatorio = rand.nextInt((101 - 1) + 1) + 1;

                    while(estoqueDoDia.buscarCarroPeloID(idAleatorio) != null){
                        idAleatorio = rand.nextInt((101 - 1) + 1) + 1;
                    }

                    //Características comuns a carros usados ou novos (classe mãe)
                    System.out.print("MARCA: ");
                    String marca = entrada.nextLine();

                    System.out.print("MODELO: ");
                    String modelo = entrada.nextLine();

                    System.out.print("COR: ");
                    String cor = entrada.nextLine();

                    System.out.print("ANO: ");
                    int anoFabricacao = entrada.nextInt();

                    //Se o carro a ser adicionado for usado, ele terá essas coisas a mais
                    if(estadoCarro == 1){

                        System.out.print("ODÔMETRO: ");
                        int odometro = entrada.nextInt();
                        entrada.nextLine();

                        boolean revisado = false;
                        System.out.print("REVISADO PELA CONCESSIONÁRIA (s/n): ");
                        String resposta = entrada.nextLine();

                        if(resposta.equals("s")){
                            revisado = true;
                        }

                        System.out.print("PREÇO NA TABELA FIPE: ");
                        float precoFipe = entrada.nextFloat();


                        //Não dá para oferecer uma garantia a mais se já houve gasto com a revisão do carro
                        boolean garantiaExtendida;
                        if(!revisado){
                            garantiaExtendida = true;
                        }
                        else{
                            garantiaExtendida = false;
                        }

                        //Adicionando o carro ao estoque
                        estoqueDoDia.adicionarCarroAoEstoque(idAleatorio, marca, modelo, cor, anoFabricacao, odometro, revisado, precoFipe, garantiaExtendida);
                    }

                    //Se for novo, então vai ter essas coisas específicas de carro novo
                    else {

                        int garantiaDeFabricaDias = 90; //Por lei são 90 dias, então a concessionária não escolhe. Poderiamos só passar 90 como parâmetro quando chamarmos o metodo também

                        System.out.print("PREÇO DE MERCADO: ");
                        float precoMercado = entrada.nextFloat();

                        //Adicionando o carro ao estoque
                        estoqueDoDia.adicionarCarroAoEstoque(idAleatorio, marca, modelo, cor, anoFabricacao, garantiaDeFabricaDias, precoMercado);
                    }
                }
                catch(InputMismatchException e){
                    System.out.println("\nERRO: Digite valores válidos para cada entrada");
                    entrada.nextLine();  //Limpa o buffer
                }

                entrada.nextLine();  //Limpa o buffer com o último ENTER precionado para poder precionar novamente e não passar direto por aqui
                System.out.println("\nENTER para voltar ao menu");
                entrada.nextLine();  //Limpa novamente para chegar ao menu sem "lixo"

            }

            else if(opcao == 3){
                System.out.println("\n--------------------------------------------------------VENDER CARROS-------------------------------------------------------------\n");

                //Aqui não vimos tanta necessidade de usar try-catch. Primeiro que apenas um if-else resolve sem problemas e, também, que clienteAtual já começa como null, então não há
                //um erro na compilação
                if(clienteAtual == null){
                    System.out.println("Registre o cliente na opção 1 para continuar");

                    System.out.println("\nENTER para voltar ao menu");
                    entrada.nextLine();
                }

                //Verifica se existem carros no estoque para serem comprados
                else if (clienteAtual != null && estoqueDoDia.getTotalCarros() == 0){
                    System.out.println("Não há estoque disponível hoje");

                    System.out.println("\nENTER para voltar ao menu");
                    entrada.nextLine();
                }

                else{
                    System.out.println("                                                    VEICULOS DISPONÍVEIS                                                      ");

                    //Declarar o vetor dentro desse Else garante que o vetor sempre vai ser inicializado a cada venda, não havendo a possibilidade de deixar carros (informações) de vendas (loops) anteriores
                    //armazenados nele. O que atrapalharia tudo. Não sei se isso realmente poderia acontecer, mas fazer assim é melhor de qualquer forma.
                    Carros[] carrosVendidos = new Carros[100];

                    //Mostrando os carros disponíveis para poder escolher qual será comprado
                    Carros[] vetorDeCarros = estoqueDoDia.getEstoque();
                    for(int i=0 ; i<estoqueDoDia.getTotalCarros() ; i++){
                        vetorDeCarros[i].exibirDetalhes();
                    }


                    //Aqui é onde vamos inserir o ID do carro desejado. O ponto principal é o uso do metodo de buscar um carro pelo seu ID da classe Estoque, que retorna
                    //um valor do tipo Carros (contendo toda as informações do carro) e o armazena na variável carroSelecionado, que também é do tipo Carros

                    int totalCarrosVendidos = 0;            //Vai servir para remover os carros do estoque depois
                    LocalDate dataAtual = LocalDate.now();  //Pega a data do computador (não consegui colocar no padrão pt-br)

                    System.out.println("\"Digite o(s) ID(s) do(s) carro(s) desejado(s)\n(Digitar 0 encerra a seleção)");

                    //A capacidade max do estoque é 100 carros, então não tem como comprar mais que 100 (não necessariamente tem 100 carros no estoque no momento)
                    while(totalCarrosVendidos < 100){
                        int valorID;

                        //Esse try-catch verifica se a entrada é int, se não for vai dar o erro InputMismatchExecption e entrar no catch
                        try{
                            System.out.print("C: ");
                            valorID = entrada.nextInt();
                        }
                        catch(InputMismatchException e){
                            System.out.println("ERRO: O ID deve ser um número inteiro.");
                            entrada.nextLine();  //Apaga o valor inserido antes

                            continue;  //Esse continue volta para o laço de repetição mais próximo, que dessa vez é o while que estamos dentro
                                       //Isso é bom porque não volta para o menu, o que obrigaria a entar na opção 3 tudo de novo
                                       //Não pensei dessa forma, mas quando percebi estava funcionando assim, então está bom
                        }

                        //Como o while que estamos não tem uma condição de parada clara, já que na maioria dos casos totalCarrosVendidos sempre vai ser
                        //menor que 100, defini valorID == 0 como sendo a condição. O ID gerado aleatoriamente vai de 1 a 101 por causa disso (se fosse 0 a 100
                        //poderia haver Id = 0, o que quebraria tudo)
                        //Dava para ter usado -1, mas -1 são duas teclas e 0 apenas uma. É mais fácil para digitar se estiver com preguiça
                        if(valorID == 0){
                            break;
                        }

                        //Tendo inserido o ID do carro desejado, buscamos todas as outras infos do tal carro
                        Carros carroSelecionado = estoqueDoDia.buscarCarroPeloID(valorID);

                        //Se não encontrar nenhum carro com o tal ID, ele não está no estoque
                        if(carroSelecionado == null){
                            System.out.println("Carro com ID " + valorID + " não encontrado");
                        }
                        else{
                            //Caso esteja no estoque, adicione-o ao vetor que armazena os carros vendidos e incrementa o total
                            carrosVendidos[totalCarrosVendidos] = carroSelecionado;
                            totalCarrosVendidos++;
                        }

                    }


                    //A condição indica que pelo menos um carro foi vendido, então podemos passas as informações para a classe Venda e remover os carros vendidos do estoque
                    if(totalCarrosVendidos > 0){
                        Venda novaVenda = new Venda(dataAtual, carrosVendidos, clienteAtual, totalCarrosVendidos);

                        estoqueDoDia.removerCarroVendidoDoEstoque(carrosVendidos, totalCarrosVendidos);

                        novaVenda.calcularTotal();
                        novaVenda.gerarRecibo();

                        entrada.nextLine();  //Limpa o buffer do último nextInt() usado
                        System.out.println("\nENTER para voltar ao menu");
                        entrada.nextLine();  //Limpa o ENTER para antes de voltar ao menu

                    }
                    else{
                        entrada.nextLine(); //Bom... Se o cliente não comprou nada, ao menos o buffer vai ficar vazio caso tenha diitado zero de primeira para não atrapalhar o loop do do-while (se é que atrapalha)
                    }
                }
            }
        }while(opcao != 4);
    }
}