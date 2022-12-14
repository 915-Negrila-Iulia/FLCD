import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.stream.Collectors;

public class Parser {
    private String grammarFilename;
    private Grammar grammar;
    private String output;
    private Stack<Pair<String,Integer>> workingStack; /// alpha - stores the way the parse is built
    private Stack<String> inputStack; /// beta - part of output to be built
    private String state; /// q-normal, b-back, f-final, e-error
    private int index; /// position of current symbol in input sequence

    public Parser(String grammarFilename, String output) {
        this.grammarFilename = grammarFilename;
        this.grammar = new Grammar(grammarFilename);
        this.grammar.readFromFile(); // read grammar from file !!!
        this.output = output;
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
        // WHEN: head of input stack is a terminal = current symbol from input
        String terminal = inputStack.pop();
        workingStack.push(new Pair<>(terminal,-1));
        index++;
    }

    private void momentaryInsuccess(){
        // WHEN: head of input stack is a terminal ≠ current symbol from input
        state = "b";
    }

    private void back(){
        // WHEN: head of working stack is a terminal
        String terminal = workingStack.pop().getKey();
        inputStack.push(terminal);
        index--;
    }

    private void anotherTry() throws Exception {
        // WHEN: head of working stack is a nonterminal
        Pair<String,Integer> pair = workingStack.pop(); // (nonterminal, production number) get last production (e.g. S3)
        String nonterminal = pair.getKey();
        Integer productionNumber = pair.getValue();
        List<String> productions = grammar.getProductionsForNonTerminal(nonterminal); // get set of productions for nonterminal
        int noOfProds = productions.size();
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
        } else { // state is "b"
            int oldProductionSize = productions.get(productionNumber).length();
            while(oldProductionSize != 0){
                inputStack.pop(); // delete all symbols from the old production
                oldProductionSize--;
            }
            inputStack.push(nonterminal); // push nonterminal back
        }
    }

    private void success(){
        state = "f";
    }

    public void descRecParsing(List<String> inputSeq) throws Exception {
        while(!Objects.equals(state, "f") && !Objects.equals(state, "e")){
            if(Objects.equals(state, "q")){
                if(index == inputSeq.size() && inputStack.isEmpty()){
                    this.success();
                } else {
                    if(grammar.getSetOfNonTerminals().contains(inputStack.peek())){
                        this.expand();
                    } else if(index < inputSeq.size() && Objects.equals(inputStack.peek(), inputSeq.get(index))) {
                        this.advance();
                    } else {
                        this.momentaryInsuccess();
                    }
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
        displayFinalProductionsList();
    }

    private void displayFinalProductionsList(){
        List<String> productionsString = workingStack.stream()
                .filter((elem)->(grammar.getSetOfNonTerminals().contains(elem.getKey())))
                .map((elem)->(elem.getKey()+elem.getValue().toString()))
                .collect(Collectors.toList());
        System.out.println("productions string: "+productionsString);
    }

    public List<Pair<String,Integer>> getFinalProductionsList(){
        return workingStack.stream()
                .filter((elem)->(grammar.getSetOfNonTerminals().contains(elem.getKey())))
                .collect(Collectors.toList());
    }
}
