
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello world!");
        SymbolTable st = new SymbolTable(19);
        String symbol;
        for(int i = 0; i<2; i++){
            symbol = Integer.toString(i);
            st.addSymbol(symbol);
        }
        st.addSymbol("27");
        st.addSymbol("54");
        for(int i = 0; i<2; i++){
            symbol = Integer.toString(i);
            st.addSymbol(symbol);
        }
        System.out.println(st);
        //System.out.println(scanner.isToken(" "));
        SymbolTable identifST = new SymbolTable(19);
        SymbolTable constST = new SymbolTable(19);
        PIF pifTable = new PIF();
        Scanner scanner = new Scanner(identifST,constST,pifTable,"F:\\An3 Sem1\\FLCD\\labs\\labFLCD\\lab2\\p1.txt");
//        scanner.addPIF("a");
//        scanner.addPIF("==");
//        scanner.addPIF("abc");
//        scanner.addPIF(";");
//        scanner.addPIF("if");
//        scanner.addPIF("abc");
//        scanner.addPIF("56");
        scanner.scan();
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println(identifST);
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println(constST);
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println(pifTable.toString());
    }
}