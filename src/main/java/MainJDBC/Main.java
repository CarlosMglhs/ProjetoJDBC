package MainJDBC;

import Enum.entities.WorkerLevel;
import db.DB;
import db.DbException;
import db.InputException;
import model.DAO.DaoFactory;
import model.DAO.DepartmentDAO;
import model.DAO.Impl.DepartmentDaoJDBC;
import model.DAO.Impl.SellerDaoJDBC;
import model.DAO.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import java.lang.classfile.instruction.SwitchCase;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static Enum.entities.WorkerLevel.*;

public class Main {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
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
                switch(num){
                    case 1:
                        menuDepartment();
                        break;
                    case 2:

                        break;

                    case 3:
                        closeConnect();
                        System.out.println("\n=-=-=-=-=-=- SAINDO DO SISTEMA -=-=-=-=-=-");
                        break;
                    default:
                        System.out.println("Opção inválida, digite novamente!!");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("ERRO: Por favor, digite apenas números inteiros!");
                sc.nextLine();
                num = 0;
            }
        }while (num != 3);

    }

    private static void menuDepartment(){
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
                switch (opcao){
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
                System.out.println("ERRO: Por favor, digite apenas números inteiros!");
                sc.nextLine();
                opcao = 0;
            }
        }while (opcao != 6);
    }






    private static void closeConnect(){
        DB.closeConnection();
    }

}
