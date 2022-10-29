
public class Main {
    public static void main(String[] args) {

        SymbolTable identifST1 = new SymbolTable(19);
        SymbolTable constST1 = new SymbolTable(19);
        PIF pifTable1 = new PIF();

        SymbolTable identifST2 = new SymbolTable(19);
        SymbolTable constST2 = new SymbolTable(19);
        PIF pifTable2 = new PIF();

        SymbolTable identifST3 = new SymbolTable(19);
        SymbolTable constST3 = new SymbolTable(19);
        PIF pifTable3 = new PIF();

        SymbolTable identifSTErr = new SymbolTable(19);
        SymbolTable constSTErr = new SymbolTable(19);
        PIF pifTableErr = new PIF();

        Scanner scanner1 = new Scanner(identifST1,constST1,pifTable1,"F:\\An3 Sem1\\FLCD\\labs\\labFLCD\\lab2\\src\\programs\\in\\p1.txt");
        Scanner scanner2 = new Scanner(identifST2,constST2,pifTable2,"F:\\An3 Sem1\\FLCD\\labs\\labFLCD\\lab2\\src\\programs\\in\\p2.txt");
        Scanner scanner3 = new Scanner(identifST3,constST3,pifTable3,"F:\\An3 Sem1\\FLCD\\labs\\labFLCD\\lab2\\src\\programs\\in\\p3.txt");
        Scanner scannerErr = new Scanner(identifSTErr,constSTErr,pifTableErr,"F:\\An3 Sem1\\FLCD\\labs\\labFLCD\\lab2\\src\\programs\\in\\p1err.txt");

        scanner1.scan(); scanner1.outputScanner("F:\\An3 Sem1\\FLCD\\labs\\labFLCD\\lab2\\src\\programs\\out\\p1.out");
        scanner1.outputTable(identifST1.toString(), "F:\\An3 Sem1\\FLCD\\labs\\labFLCD\\lab2\\src\\programs\\tablesOut\\p1-IST.out");
        scanner1.outputTable(constST1.toString(), "F:\\An3 Sem1\\FLCD\\labs\\labFLCD\\lab2\\src\\programs\\tablesOut\\p1-CST.out");
        scanner1.outputTable(pifTable1.toString(), "F:\\An3 Sem1\\FLCD\\labs\\labFLCD\\lab2\\src\\programs\\tablesOut\\p1-PIF.out");

        scanner2.scan(); scanner2.outputScanner("F:\\An3 Sem1\\FLCD\\labs\\labFLCD\\lab2\\src\\programs\\out\\p2.out");
        scanner2.outputTable(identifST2.toString(), "F:\\An3 Sem1\\FLCD\\labs\\labFLCD\\lab2\\src\\programs\\tablesOut\\p2-IST.out");
        scanner2.outputTable(constST2.toString(), "F:\\An3 Sem1\\FLCD\\labs\\labFLCD\\lab2\\src\\programs\\tablesOut\\p2-CST.out");
        scanner2.outputTable(pifTable2.toString(), "F:\\An3 Sem1\\FLCD\\labs\\labFLCD\\lab2\\src\\programs\\tablesOut\\p2-PIF.out");


        scanner3.scan(); scanner3.outputScanner("F:\\An3 Sem1\\FLCD\\labs\\labFLCD\\lab2\\src\\programs\\out\\p3.out");
        scanner3.outputTable(identifST3.toString(), "F:\\An3 Sem1\\FLCD\\labs\\labFLCD\\lab2\\src\\programs\\tablesOut\\p3-IST.out");
        scanner3.outputTable(constST3.toString(), "F:\\An3 Sem1\\FLCD\\labs\\labFLCD\\lab2\\src\\programs\\tablesOut\\p3-CST.out");
        scanner3.outputTable(pifTable3.toString(), "F:\\An3 Sem1\\FLCD\\labs\\labFLCD\\lab2\\src\\programs\\tablesOut\\p3-PIF.out");

        scannerErr.scan(); scannerErr.outputScanner("F:\\An3 Sem1\\FLCD\\labs\\labFLCD\\lab2\\src\\programs\\out\\p1err.out");
        scannerErr.outputTable(identifSTErr.toString(), "F:\\An3 Sem1\\FLCD\\labs\\labFLCD\\lab2\\src\\programs\\tablesOut\\p1err-IST.out");
        scannerErr.outputTable(constSTErr.toString(), "F:\\An3 Sem1\\FLCD\\labs\\labFLCD\\lab2\\src\\programs\\tablesOut\\p1err-CST.out");
        scannerErr.outputTable(pifTableErr.toString(), "F:\\An3 Sem1\\FLCD\\labs\\labFLCD\\lab2\\src\\programs\\tablesOut\\p1err-PIF.out");

        //System.out.println(scanner1.toString() + scanner2.toString() + scanner3.toString() + scannerErr.toString());
    }
}