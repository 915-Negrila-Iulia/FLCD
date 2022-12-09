import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Grammar {
    private List<String> setOfTerminals;
    private List<String> setOfNonTerminals;
    private List<Production> listOfProductions;
    private String startingSymbol;
    private String filename;

    public Grammar(String filename){
        this.filename = filename;
        this.setOfNonTerminals = new ArrayList<>();
        this.setOfTerminals = new ArrayList<>();
        this.listOfProductions = new ArrayList<>();
        this.startingSymbol = "";
    }

    public List<String> getSetOfTerminals() {
        return setOfTerminals;
    }

    public List<String> getSetOfNonTerminals() {
        return setOfNonTerminals;
    }

    public List<Production> getListOfProductions() {
        return listOfProductions;
    }

    public String getStartingSymbol() {
        return startingSymbol;
    }

    public String getFilename() {
        return filename;
    }

    public List<String> getProductionsForNonTerminal(String nonTerminal) throws Exception {
        if(!setOfNonTerminals.contains(nonTerminal)){
            throw new Exception("Given nonTerminal does not exist");
        }
        List<String> prodValues = new ArrayList<>();
        for(Production production: listOfProductions){
            if(Objects.equals(production.getKey(), nonTerminal)){
                prodValues.addAll(production.getValues());
            }
        }
        return prodValues;
    }

    public List<String> getSymbolsOfRHS(String productionRHS){
        String[] tokens = productionRHS.split(" ");
        return Arrays.asList(tokens);
    }

    public void readSet(String line, List<String> set){
        String[] tokens = line.split(" ");
        set.addAll(Arrays.asList(tokens));
    }

    public void readProductions(String line) throws Exception {
        String[] prodSplit = line.split(" -> ");
        Production production = new Production();
        production.setKey(prodSplit[0]);
        String[] statesSplit = prodSplit[1].split(" \\| ");
        production.setValues(new ArrayList<>(Arrays.asList(statesSplit)));
        listOfProductions.add(production);
    }

    public void readFromFile(){
        File file=new File(filename);
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            int countLine = 0;
            while ((line = br.readLine()) != null) {
                if(countLine == 0){
                    readSet(line, setOfNonTerminals);
                    countLine++;
                }
                else if(countLine == 1){
                    readSet(line, setOfTerminals);
                    countLine++;
                }
                else if(countLine == 2){
                    startingSymbol = line;
                    countLine++;
                }
                else if(countLine == 3){
                    readProductions(line);
                }
            }
            fr.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public boolean isCFG(){
        // All productions should have a single nonTerminal in the LHS
        for(Production production: listOfProductions){
            String[] lhsNonTerminals = production.getKey().split(" ");
            String firstNonTerminal = lhsNonTerminals[0];
            if(lhsNonTerminals.length != 1 || !setOfNonTerminals.contains(firstNonTerminal)){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Grammar{" +
                "setOfTerminals=" + setOfTerminals +
                ", setOfNonTerminals=" + setOfNonTerminals +
                ", listOfProductions=" + listOfProductions +
                ", startingSymbol='" + startingSymbol + '\'' +
                ", filename='" + filename + '\'' +
                '}';
    }
}
