import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ParserOutput {
    private final Parser parser;
    private final List<Pair<String,Integer>> productions;
    private final String startingSymbol;

    public ParserOutput(Parser parser, List<Pair<String,Integer>> productions) {
        this.parser = parser;
        this.productions = productions;
        this.startingSymbol = parser.getGrammar().getStartingSymbol();
    }

    public int getPositionOfLeftMostSymbol(String productionKey, List<String> derivation){
        for(int i=0; i<derivation.size(); i++){
            if(Objects.equals(derivation.get(i), productionKey)){
                return i;
            }
        }
        return -1;
    }

    public void derivate(Pair<String,Integer> production, List<String> lastDerivation) throws Exception {
        int pos = getPositionOfLeftMostSymbol(production.getKey(), lastDerivation);
        List<String> producationById = parser.getGrammar().getSymbolsOfRHS(parser.getGrammar().getProductionsForNonTerminal(production.getKey()).get(production.getValue()));
        lastDerivation.remove(pos);
        lastDerivation.addAll(pos,producationById);
    }

    public List<String> getDerivations() throws Exception {
        List<String> outputDerivation = new ArrayList<>();
        outputDerivation.add(startingSymbol);
        for (Pair<String, Integer> production : productions) {
            derivate(production, outputDerivation);
        }
        return outputDerivation;
    }
}
