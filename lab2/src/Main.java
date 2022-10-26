
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello world!");
        SymbolTable st = new SymbolTable(19);
        String symbol;
        for(int i = 0; i<44; i++){
            symbol = Integer.toString(i);
            st.addSymbol(symbol);
        }
        //System.out.println(st);
        //System.out.println(scanner.isToken(" "));
        SymbolTable identifST = new SymbolTable(19);
        SymbolTable constST = new SymbolTable(19);
        PIF pifTable = new PIF();
        Scanner scanner = new Scanner(identifST,constST,pifTable);
        scanner.addPIF("a");
        scanner.addPIF("==");
        scanner.addPIF("abc");
        scanner.addPIF(";");
        scanner.addPIF("if");
        scanner.addPIF("abc");
        scanner.addPIF("56");
        System.out.println(pifTable.toString());
    }
}