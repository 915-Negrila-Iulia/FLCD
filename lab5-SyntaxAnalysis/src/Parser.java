import java.util.List;
import java.util.Stack;

public class Parser {
    private String grammarFilename;
    private Grammar grammar;
    private String sequence; /// input sequence
    private String output;
    private Stack<Pair<String,Integer>> workingStack; /// alpha - stores the way the parse is built
    private Stack<String> inputStack; /// beta - part of output to be built
    private String state; /// q-normal, b-back, f-final, e-error
    private int i; /// position of current symbol in input sequence

    public Parser(String grammarFilename, String sequence, String output, int i) {
        this.grammarFilename = grammarFilename;
        this.grammar = new Grammar(grammarFilename);
        this.sequence = sequence;
        this.output = output;
        this.workingStack = new Stack<>();
        this.inputStack = new Stack<>();
        this.inputStack.push(grammar.getStartingSymbol());
        this.state = "q";
        this.i = 1;
    }

    private void expand() throws Exception {
        // WHEN: head of input stack is a nonterminal
        String nonterminal = inputStack.pop();
        workingStack.push(new Pair<>(nonterminal,1));
        String newProduction = grammar.getProductionsForNonTerminal(nonterminal).get(0);
        inputStack.push(newProduction);
    }

    private void advance(){
        // WHEN: head of input stack is a terminal = current symbol from input
        String terminal = inputStack.pop();
        workingStack.push(new Pair<>(terminal,0));
        i++;
    }

    private void momentaryInsuccess(){
        // WHEN: head of input stack is a terminal ≠ current symbol from input
        state = "b";
    }

    private void back(){
        // WHEN: head of working stack is a terminal
        String terminal = workingStack.pop().getKey();
        inputStack.push(terminal);
        i--;
    }

    private void anotherTry() throws Exception {
        // WHEN: head of working stack is a nonterminal
        Pair<String,Integer> pair = workingStack.pop(); // (nonterminal, production number)
        String nonterminal = pair.getKey();
        Integer productionNumber = pair.getValue();
        int noOfProds = grammar.getProductionsForNonTerminal(nonterminal).size();
        if(productionNumber+1 <= noOfProds){
            state = "q";
            Pair<String,Integer> newPair = new Pair<>(nonterminal,productionNumber+1);
            workingStack.push(newPair);
            inputStack.pop();

        }

    }

}
