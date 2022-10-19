
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        SymbolTable st = new SymbolTable(19);
        String symbol;
        for(int i = 0; i<44; i++){
            symbol = Integer.toString(i);
            st.addSymbol(symbol);
        }
        System.out.println(st);
    }
}