package interpreter;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;
import interpreter.repository.Repository;
import interpreter.view.Examples;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Toy Interpreter running...");

        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("Select the option(0 for closing)");
            System.out.println("1. int v; v=2; print(v)");
            System.out.println("2. int a; int b; a=2+3*5; b=a+1; print(b)");
            System.out.println("3. bool a; int v; a=true; (if a then v=2 else v=3); print(v)");

            int choice = scanner.nextInt();
            if (choice == 0) break;

            IStmt selectedProgram ;
            if(choice == 1)
                 selectedProgram = Examples.EX1;
            else if(choice == 2)
                selectedProgram = Examples.EX2;
            else if(choice == 3)
                selectedProgram = Examples.EX3;
            else
                selectedProgram  = null;

            if(selectedProgram != null)
                runProgram(selectedProgram);
            else
                System.out.println("Invalid option");


        }
        scanner.close();
    }

    private static void runProgram(IStmt program){
        try {
            PrgState prg = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(),  program);
            IRepository repo = new Repository(prg);
            Controller controller = new Controller(repo, true);
            System.out.println("Starting execution");
            controller.allStep();
            System.out.println("Execution done");
        }
        catch (MyException e) {
            System.out.println("Runtime error: " + e.getMessage());
        }


        }
    }

