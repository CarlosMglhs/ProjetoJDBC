package MainJDBC;

import Enum.entities.WorkerLevel;
import db.DB;
import model.DAO.DaoFactory;
import model.DAO.DepartmentDAO;
import model.DAO.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import static Enum.entities.WorkerLevel.*;

///
public class Main {

    Scanner sc = new Scanner(System.in);
    DepartmentDAO depDao = DaoFactory.createDepDAO();
    SellerDAO sellerDao = DaoFactory.createSellerDAO();

    public static void main(String[] args) throws ParseException {
        Scanner sc = new Scanner(System.in);
        int num = 0;
        do {
            System.out.println("\n=-=-=-=-= MENU GERENCIADOR =-=-=-=-=-=\n");
            System.out.println("1. Gerenciador de Departamentos");
            System.out.println("2. Gerenciador de Vendedores");
            System.out.println("3. Sair do Sistema");
            try {
                num = sc.nextInt();
                switch (num) {
                    case 1:
                        menuDepartment();
                        break;
                    case 2:
                        menuSeller();
                        break;
                    case 3:
                        closeConnect();
                        System.out.println("\n=-=-=-=-=-=- SAINDO DO SISTEMA -=-=-=-=-=-");
                        break;
                    default:
                        System.out.println("Opção inválida, digite novamente!!");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("ERRO: Por favor, digite apenas números inteiros! " + e.getMessage());
                sc.nextLine();
                num = 0;
            }
        } while (num != 3);

    }

    private static void menuDepartment() {

        DepartmentDAO depDao = DaoFactory.createDepDAO();
        Scanner sc = new Scanner(System.in);
        int opcao = 0;
        do {
            System.out.println("\n=-=-=-=-=-=-= Gerenciador de Departamentos =-=-=-=-=-=-=\n");
            System.out.println("1. Cadastrar Departamento");
            System.out.println("2. Atualizar dados do departamento");
            System.out.println("3. Deletar Departamento");
            System.out.println("4. Pesquisar pelo ID");
            System.out.println("5. Listar todos os departamentos\n");
            System.out.println("6. Voltar ao menu principal");
            try {
                opcao = sc.nextInt();
                switch (opcao) {
                    case 1:
                        System.out.println("=-=-=-=-= INSERINDO NOVO DEPARTAMENTO =-=-=-=-=\n");
                        System.out.println("DIGITE O ID DO DEPARTAMENTO: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.println("DIGITE O NOME DO DEPARTAMENTO: ");
                        String nome = sc.nextLine();
                        Department depNovo = new Department(id, nome);
                        depDao.insertDep(depNovo);
                        System.out.println("\n =-=-=-=-=-=-= Retornando para as opções =-=-=-=-=-=-=");
                        break;
                    case 2:
                        System.out.println("\nDigite o id do departamento que deseja alterar: ");
                        id = sc.nextInt();
                        sc.nextLine();
                        System.out.println("\nDigite o novo nome do departamento: ");
                        String novoNome = sc.nextLine();
                        Department depUpdate = new Department(id, novoNome);
                        depDao.updateDep(depUpdate);
                        System.out.println("Retornando para o Menu principal");
                        break;
                    case 3:
                        System.out.println("\nDigite o ID do departamento que deseja deletar: ");
                        id = sc.nextInt();
                        depDao.deleteDep(id);
                        break;
                    case 4:
                        System.out.println("Digite o ID que deseja buscar: ");
                        id = sc.nextInt();
                        depDao.searchById(id);
                        break;
                    case 5:
                        depDao.findAll();
                        break;
                    case 6:
                        System.out.println("Retornando para o Menu principal");
                        break;
                    default:
                        System.out.println("Opção inválida, digite novamente!!");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("ERRO: Por favor, digite apenas números inteiros!" + e.getMessage());
                sc.nextLine();
                opcao = 0;
            }
        } while (opcao != 6);
    }

    private static void menuSeller() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SellerDAO sell = DaoFactory.createSellerDAO();
        Scanner sc = new Scanner(System.in);
        int opcao = 0;
        do {
            System.out.println("\n=-=-=-=-=-=-= Gerenciador de Departamentos =-=-=-=-=-=-=\n");
            System.out.println("1. Cadastrar Vendedor");
            System.out.println("2. Atualizar dados do vendedor");
            System.out.println("3. Deletar vendedor");
            System.out.println("4. Pesquisar pelo ID");
            System.out.println("5. Listar todos os vendedores");
            System.out.println("6. Listar todos os vendedores do departamento");
            System.out.println("7. Voltar a tela inicial");
            opcao = sc.nextInt();
            try {
                switch (opcao) {
                    case 1:
                        System.out.println("Digite o nome completo do vendedor: ");
                        String nome = sc.nextLine();
                        System.out.println("Digite o email: ");
                        String email = sc.nextLine();
                        System.out.println("Digite a data de aniversário: ");
                        Date date = sdf.parse(sc.nextLine());
                        System.out.println("Digite o salário do vendedor: ");
                        Double salario = sc.nextDouble();
                        System.out.println("Digite sua Categoria" + "\n JUNIOR" + "\n PLENO" + "\n SENIOR");
                        WorkerLevel senioridade = WorkerLevel.valueOf(sc.nextLine());
                        System.out.println("Digite o ID do departamento deste vendedor: ");
                        int depId = sc.nextInt();
                        sc.nextLine();
                        Department dep = new Department(depId, null);
                        Seller sellDao = new Seller(null, nome, email, date, salario, senioridade, dep);
                        sell.insert(sellDao);
                        break;
                    case 2:
                        sc.nextLine();
                        System.out.println("\n=-=-=-=-= ATUALIZANDO VENDEDOR =-=-=-=-=\n");

                        System.out.println("\nDigite o ID do vendedor que deseja atualizar: ");
                        int idUpdate = sc.nextInt();
                        sc.nextLine();
                        Seller vendedorAtual = sell.findById(idUpdate);

                        System.out.println("\nNome atual do vendedor: " + vendedorAtual.getName());
                        System.out.println("Digite o novo nome (ou pressione ENTER para manter o atual): ");
                        String novoNome = sc.nextLine();
                        if (!novoNome.trim().isEmpty()) {//se não estiver vazio.
                            vendedorAtual.setName(novoNome); // Só altera se o usuário digitou algo
                        }

                        System.out.println("\nSalário atual do vendedor: " + vendedorAtual.getBaseSalary());
                        System.out.println("Digite o novo salário (ou pressione ENTER para manter o atual): ");
                        String novoSalario = sc.nextLine();
                        if (!novoSalario.trim().isEmpty()) {
                            vendedorAtual.setBaseSalary(Double.parseDouble(novoSalario));
                        }

                        System.out.println("\nCategoria atual do vendedor: " + vendedorAtual.getSenioridade());
                        System.out.println("\nDigite a nova categoria [1.JUNIOR, 2.PLENO, 3.SENIOR] (ou ENTER para manter): ");
                        String inputOpcao = sc.nextLine();
                        WorkerLevel categoriaAtual = tipoCategoria(inputOpcao);
                        vendedorAtual.setSenioridade(categoriaAtual);

                        sell.update(vendedorAtual);
                        System.out.println("\n=-=-=-=-=-= DADOS ATUALIZADOS =-=-=-=-=-");
                        System.out.println("\nNome: " + vendedorAtual.getName() + "\nSalário: " + vendedorAtual.getBaseSalary()
                                + "\nCategoria: " + vendedorAtual.getSenioridade());
                        break;

                    case 3:
                        System.out.println("\nDigite o id do vendedor que deseja deletar: ");
                        int id = sc.nextInt();
                        sell.deleteById(id);
                        break;

                    case 4:
                        System.out.println("\nDigite o id para buscar vendedor: ");
                        id = sc.nextInt();
                        sell.findById(id);
                        break;

                    case 5:
                        sell.findAll();
                        break;

                    case 6:
                        System.out.println("\nListar todos os vendedores de determinado departamento: ");
                        int idDep = sc.nextInt();
                        Department depFiltro = new Department();
                        depFiltro.setId(idDep);
                        sell.findByDepartment(depFiltro);
                        break;

                    case 7:
                        System.out.println("Voltando ao Menu principal..");
                        break;

                    default:
                        System.out.println("Opção inválida, digite novamente!!");
                }

            } catch (InputMismatchException | ParseException e) {
                System.out.println("ERRO: Por favor, digite apenas números inteiros!" + e.getMessage());
                sc.nextLine();
                opcao = 0;
            }

        } while (opcao != 7);
    }

    private static WorkerLevel tipoCategoria(String input, WorkerLevel categoriaAtual) {
        // Se o usuário apenas apertou ENTER ou digitou espaços, mantém a atual
        if (input == null || input.trim().isEmpty()) {
            return categoriaAtual;
        }

        switch (input.trim()) {
            case "1":
                return JUNIOR;
            case "2":
                return PLENO;
            case "3":
                return SENIOR;
            default:
                System.out.println("OPÇÃO INVÁLIDA...MATENDO A CATEGORIA ATUAL");
                return categoriaAtual;
        }
    }

    private static void closeConnect() {
        DB.closeConnection();
    }

}
