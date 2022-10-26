import java.io.*;
import java.util.AbstractMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scanner {

    private final String identifierRegex = "^([a-zA-Z][a-zA-Z\\d]*)$";
    private final String digitRegex = "-?[1-9]\\d*|0";
    private final String charRegex = "'[a-zA-z\\d]'";

    private final PIF pifTable;
    private final SymbolTable constSymbolTable;
    private final SymbolTable identifSymbolTable;
    private static final String ID = "id";
    private static final String CONST = "const";

    public Scanner(SymbolTable identifSymbolTable, SymbolTable constSymbolTable, PIF pifTable) {
        this.identifSymbolTable = identifSymbolTable;
        this.constSymbolTable = constSymbolTable;
        this.pifTable = pifTable;
    }

    public Boolean isIdentifier(String symbol){
        Pattern p = Pattern.compile(identifierRegex);
        if (symbol == null) {
            return false;
        }
        Matcher m = p.matcher(symbol);
        return m.matches();
    }

    public Boolean isConstant(String symbol){
        Pattern p;
        if (symbol == null) {
            return false;
        }
        // check if digit
        p = Pattern.compile(digitRegex);
        Matcher m1 = p.matcher(symbol);
        // check if letter
        p = Pattern.compile(charRegex);
        Matcher m2 = p.matcher(symbol);
        // check if word
        Matcher m3 = null;
        if(symbol.charAt(0) == '"' && symbol.charAt(symbol.length()-1) == '"'){
            StringBuffer sb= new StringBuffer(symbol);
            sb.deleteCharAt(0);
            sb.deleteCharAt(sb.length()-1);
            p = Pattern.compile(identifierRegex);
            m3 = p.matcher(sb);
        }
        return m1.matches() || m2.matches() || m3!=null && m3.matches();
    }

    public boolean isToken(String symbol) {
        File file=new File("F:\\An3 Sem1\\FLCD\\labs\\labFLCD\\lab1b\\token.in");
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                if (Objects.equals(line, symbol)) {
                    fr.close();
                    return true;
                }
            }
            fr.close();
            return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void addPIF(String symbol) throws Exception {
        if(this.isToken(symbol)){
            pifTable.add(new AbstractMap.SimpleEntry<>(-1,-1),symbol);
        }
        else if(this.isIdentifier(symbol)){
            AbstractMap.SimpleEntry<Integer, Integer> positionST = identifSymbolTable.addSymbol(symbol);
            pifTable.add(positionST,ID);
        }
        else if(this.isConstant(symbol)){
            AbstractMap.SimpleEntry<Integer, Integer> positionST = constSymbolTable.addSymbol(symbol);
            pifTable.add(positionST,CONST);
        }
        else{
            throw new Exception("Lexical error");
        }
    }
}
