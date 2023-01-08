import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.stream.Collectors;

public class Parser {
    private String grammarFilename;
    private Grammar grammar;
    private Stack<Pair<String,Integer>> workingStack; /// alpha - stores the way the parse is built
    private Stack<String> inputStack; /// beta - part of output to be built
    private String state; /// q-normal, b-back, f-final, e-error
    private int index; /// position of current symbol in input sequence

    public Parser(String grammarFilename) {
        this.grammarFilename = grammarFilename;
        this.grammar = new Grammar(grammarFilename);
        this.grammar.readFromFile(); // read grammar from file !!!
        this.workingStack = new Stack<>();
        this.inputStack = new Stack<>();
        this.inputStack.push(grammar.getStartingSymbol());
        this.state = "q";
        this.index = 0;
    }

    public Grammar getGrammar() {
        return grammar;
    }

    private void expand() throws Exception {
        System.out.println("expand");
        // WHEN: head of input stack is a nonterminal
        String nonterminal = inputStack.pop();
        workingStack.push(new Pair<>(nonterminal,0));
        String newProduction = grammar.getProductionsForNonTerminal(nonterminal).get(0);
        List<String> symbolsOfRHS = grammar.getSymbolsOfRHS(newProduction);
        for(int i=symbolsOfRHS.size()-1; i>=0; i--){
            inputStack.push(symbolsOfRHS.get(i)); // push all symbols of the new production
        }
    }

    private void advance(){
        System.out.println("advance");
        // WHEN: head of input stack is a terminal = current symbol from input
        String terminal = inputStack.pop();
        workingStack.push(new Pair<>(terminal,-1));
        index++;
    }

    private void momentaryInsuccess(){
        System.out.println("momentary insuccess");
        // WHEN: head of input stack is a terminal ≠ current symbol from input
        state = "b";
    }

    private void back(){
        System.out.println("back");
        // WHEN: head of working stack is a terminal
        String terminal = workingStack.pop().getKey();
        inputStack.push(terminal);
        index--;
    }

    private void anotherTry() throws Exception {
        System.out.println("another try");
        // WHEN: head of working stack is a nonterminal
        Pair<String,Integer> pair = workingStack.pop(); // (nonterminal, production number) get last production (e.g. S3)
        String nonterminal = pair.getKey();
        Integer productionNumber = pair.getValue();
        List<String> productions = grammar.getProductionsForNonTerminal(nonterminal); // get set of productions for nonterminal
        int noOfProds = productions.size();
        System.out.println("nonterminal: "+nonterminal);
        System.out.println("noOfProds: "+noOfProds);
        if(productionNumber+1 < noOfProds){ // check if there is another production to try with
            state = "q";
            Pair<String,Integer> newPair = new Pair<>(nonterminal,productionNumber+1);
            workingStack.push(newPair); // push the next production that will be tried
            String oldProduction = productions.get(productionNumber);
            List<String> symbolsOfOldProd = grammar.getSymbolsOfRHS(oldProduction);
            int oldProductionSize = symbolsOfOldProd.size();
            while(oldProductionSize != 0){
                inputStack.pop(); // delete all symbols from the old production
                oldProductionSize--;
            }
            String newProduction = productions.get(productionNumber+1);
            List<String> symbolsOfNewProd = grammar.getSymbolsOfRHS(newProduction);
            for(int i=symbolsOfNewProd.size()-1; i>=0; i--){
                inputStack.push(symbolsOfNewProd.get(i)); // push all symbols of the new production
            }
        } else if(index == 0 && Objects.equals(nonterminal, grammar.getStartingSymbol())){
            state = "e";
            System.out.println(nonterminal+"===="+grammar.getStartingSymbol());
        } else { // state is "b"
            System.out.println("old prod: "+productions.get(productionNumber));
            int oldProductionSize = productions.get(productionNumber).split(" ").length;
            while(oldProductionSize != 0){
                System.out.println("prod: "+productions.get(productionNumber));
                System.out.println("old prod size: "+oldProductionSize);
                inputStack.pop(); // delete all symbols from the old production
                oldProductionSize--;
            }
            inputStack.push(nonterminal); // push nonterminal back
        }
    }

    private void success(){
        System.out.println("success");
        state = "f";
    }

    public void descRecParsing(List<String> inputSeq) throws Exception {
        while(!Objects.equals(state, "f") && !Objects.equals(state, "e")){
            System.out.println(inputStack.toString());
            System.out.println(state);
            if(Objects.equals(state, "q")){
                if(index == inputSeq.size() && inputStack.isEmpty()){
                    this.success();
                } else if(!inputStack.isEmpty()){
                    if(grammar.getSetOfNonTerminals().contains(inputStack.peek())){
                        this.expand();
                    } else if(index < inputSeq.size() && Objects.equals(inputStack.peek(), inputSeq.get(index))) {
                        this.advance();
                    } else {
                        this.momentaryInsuccess();
                    }
                } else {
                    state = "b";
                }
            } else if(Objects.equals(state, "b")) {
                if(grammar.getSetOfTerminals().contains(workingStack.peek().getKey())){
                    this.back();
                } else {
                    this.anotherTry();
                }
            }
        }
        if(Objects.equals(state, "e")){
            System.out.println("Error");
        } else {
            System.out.println("Sequence accepted");
        }

    }

    public List<String> displayFinalProductionsList(){
        List<String> result = new ArrayList<>();
        result =  workingStack.stream()
                .filter((elem)->(grammar.getSetOfNonTerminals().contains(elem.getKey())))
                .map((elem)->(elem.getKey()+elem.getValue().toString()))
                .collect(Collectors.toList());
        return result;
    }

    public List<Pair<String,Integer>> getFinalProductionsList(){
        return workingStack.stream()
                .filter((elem)->(grammar.getSetOfNonTerminals().contains(elem.getKey())))
                .collect(Collectors.toList());
    }
}
