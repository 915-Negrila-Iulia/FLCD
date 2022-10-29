import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Objects;

public class SymbolTable {

    private final ArrayList<ArrayList<String>> elements;
    private final int size; // should be prime

    public SymbolTable(int size) {
        this.elements = new ArrayList<>();
        for(int i=0; i<size; i++){
            this.elements.add(new ArrayList<>());
        }
        this.size = size;
    }

    private int hash(String key) {
        int sumOfAscii = 0;
        for(int i=0;i<key.length();i++){
            sumOfAscii += key.charAt(i);
        }
        return sumOfAscii % this.size;
    }

    private boolean isKey(String key){
        int hashKey = hash(key);
        return elements.get(hashKey).contains(key);
    }

    public AbstractMap.SimpleEntry<Integer, Integer> addSymbol(String symbol){
        if(isKey(symbol)){
            int hashPosition = hash(symbol);
            int listPosition = 0;
            for(int i=0; i<elements.get(hashPosition).size(); i++){
                if(Objects.equals(elements.get(hashPosition).get(i), symbol)){
                    return new AbstractMap.SimpleEntry<>(hashPosition,listPosition);
                }
                listPosition++;
            }
        }
        else{
            int hashPosition = hash(symbol);
            elements.get(hashPosition).add(symbol);
        }
        return new AbstractMap.SimpleEntry<>(-1,-1);
    }

    @Override
    public String toString() {
        String stString = "";
        for (int i=0; i<size; i++) {
            stString += "( ";
            for(String item: elements.get(i)){
                stString += item + ", ";
            }
            stString += " )\n";
        }
        return stString;
    }
}
