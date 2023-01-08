
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static void displayMenu()
    {
        System.out.println("\n");
        System.out.println("1. Print set of NonTerminals");
        System.out.println("2. Print set of terminals");
        System.out.println("3. Print set of productions");
        System.out.println("4. Print productions for a given NonTerminal");
        System.out.println("5. Context Free Grammar check ");
        System.out.println("6. Print starting symbol");
        System.out.println("7. Print representation");
        System.out.println("0. Exit ");
        System.out.println("\n");
    }

    public static void main(String[] args) throws Exception {
        //Grammar gr = new Grammar("F:\\An3 Sem1\\FLCD\\labs\\labFLCD\\lab5-SyntaxAnalysis\\src\\g1.txt");
        Grammar gr = new Grammar("F:\\An3 Sem1\\FLCD\\labs\\labFLCD\\lab5-SyntaxAnalysis\\src\\g2.txt");
        //Grammar gr = new Grammar("F:\\An3 Sem1\\FLCD\\labs\\labFLCD\\lab5-SyntaxAnalysis\\src\\g3.txt");

        SymbolTable identifST1 = new SymbolTable(19);
        SymbolTable constST1 = new SymbolTable(19);
        PIF pifTable1 = new PIF();
        String programFile = "F:\\An3 Sem1\\FLCD\\labs\\labFLCD\\lab5-SyntaxAnalysis\\src\\g2prob.txt";
        String pifFile = "F:\\An3 Sem1\\FLCD\\labs\\labFLCD\\lab5-SyntaxAnalysis\\src\\g2PIF.txt";
        MyScanner scanner1 = new MyScanner(identifST1,constST1,pifTable1,programFile);
        scanner1.scan();
        scanner1.outputTable(pifTable1.toString(), pifFile);

        gr.readFromFile();

        Parser parser = new Parser(gr.getFilename());

        ParserOutput parserOutput = new ParserOutput(parser,parser.getFinalProductionsList());

        Scanner read = new Scanner(System.in);
        while(true) {
            displayMenu();
            System.out.println("Enter option: ");
            String option = read.nextLine();

            switch (option) {
                case "1":
                    System.out.println("NonTerminals:");
                    System.out.println(gr.getSetOfNonTerminals());
                    break;
                case "2":
                    System.out.println("Terminals: ");
                    System.out.println(gr.getSetOfTerminals());
                    break;
                case "3":
                    System.out.println("Productions:");
                    System.out.println(gr.getListOfProductions());
                    break;
                case "4":
                    System.out.println("Give NonTerminal:");
                    String nonTerminal = read.nextLine();
                    try {
                        System.out.println("Productions of a given NonTerminal:");
                        System.out.println(gr.getProductionsForNonTerminal(nonTerminal));
                    }catch (Exception exception){
                        System.out.println(exception.getMessage());
                    }
                    break;
                case "5":
                    System.out.println("Context Free grammar check");
                    System.out.println(gr.isCFG());
                    break;
                case "6":
                    System.out.println("Starting symbol");
                    System.out.println(gr.getStartingSymbol());
                    break;
                case "7":
                    try {
                        //parser.descRecParsing(Arrays.asList("0")); // gr1 example
                        List elems = scanner1.getElementsFromFile();
                        parser.descRecParsing(elems); // gr2 example
                        //parser.descRecParsing(Arrays.asList("a","a","c","b","c")); // gr3 example
                        System.out.println("Productions string: " + parser.displayFinalProductionsList());
                        System.out.println("Derivations string: " + parserOutput.getDerivations());
                    }catch(Exception e){
                        e.printStackTrace();
                        System.out.println("Not able to parse");
                    }
                    break;
                case "0":
                    System.exit(0);
                default:
                    System.out.println("Wrong option!");
            }
        }
    }
}