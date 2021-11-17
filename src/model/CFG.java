package model;

import com.sun.javafx.scene.control.skin.VirtualFlow;

import java.util.*;

public class CFG {

    private char[] variables;
    private char[] symbols;
    private char initialSymbol;
    private HashMap<Character, String> productions;

    char [][] cykTable;


    public CFG(String inputVariables, String inputSymbols, char inputInitialSymbol) {
        variables = inputVariables.toCharArray();
        symbols = inputSymbols.toCharArray();
        initialSymbol = inputInitialSymbol;
    }

    public char [] getVariables() {
        return variables;
    }

    public void setVariables(char [] variables) {
        this.variables = variables;
    }

    public char [] getSymbols() {
        return symbols;
    }

    public void setSymbols(char [] symbols) {
        this.symbols = symbols;
    }

    public char getInitialSymbol() {
        return initialSymbol;
    }

    public void setInitialSymbol(char initialSymbol) {
        this.initialSymbol = initialSymbol;
    }

    public HashMap<Character, String> getProductions() {
        return productions;
    }

    public void setProductions(HashMap<Character, String> productions) {
        this.productions = productions;
    }

    public char[][] getCykTable() {
        return cykTable;
    }

    public boolean cykAlgorithm (String w) {
        initializeTable(w);
        int n = w.length();
        for (int j = 1; j < n; j++) {
            for (int i = 0; i < n - j; i++) {
                for (int k = 0; k < j; k++){
                    String productionToSearch = "" + cykTable[i][k] + cykTable[i + k + 1][j - k - 1];
                    for (char key : productions.keySet()) {
                        if (productions.get(key).contains(productionToSearch)) {
                            cykTable[i][j] = key;
                            break;
                        }
                    }
                }
            }
        }
        return cykTable[0][n - 1] == initialSymbol;
    }

    public boolean isCNF () {
        return hasBinaryOrSimpleProductions() && reachableVariables() && terminableVariables() && !hasLambdaProductions();
    }

    private boolean hasBinaryOrSimpleProductions () {
        for (String production : productions.values()) {
            String [] productionsArray = production.split("\\|");
            for (String s : productionsArray) {
                if (s.length() > 2) {
                    return false;
                }else if (s.length() == 2 && s.equals(s.toUpperCase())) {
                    break;
                }else if (s.length() == 1 && s.equals(s.toLowerCase())) {
                    break;
                }else {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean reachableVariables () {
        ArrayList<Character> unreachableVariables = toCharacterArrayList();
        for (Character variable : productions.keySet()) {
            for (String production : productions.values()){
                if (!productions.get(variable).equals(production)) {
                    if (production.contains("" + variable)) {
                        if (unreachableVariables.remove(variable))
                        break;
                    }
                }
            }
        }
        return unreachableVariables.isEmpty();
    }

    private boolean terminableVariables () {
        ArrayList<Character> generating = new ArrayList<>();
        ArrayList<Character> variableArrayList = toCharacterArrayList();
        variableArrayList.add(initialSymbol);
        for (Character variable : productions.keySet()) {
            String [] production = productions.get(variable).split("\\|");
            for (String s : production) {
                if (s.equals(s.toLowerCase())) {
                    generating.add(variable);
                    break;
                }
            }
        }
        for (Character variable : variableArrayList) {
            for (Character generatingVariable : generating) {
                if (productions.get(variable).contains("" + generatingVariable) && !generating.contains(variable)) {
                    generating.add(variable);
                    break;
                }
            }
        }
        return generating.size() == variables.length;
    }

    private boolean hasLambdaProductions () {
        String initialProduction = productions.get(initialSymbol);
        for (String production : productions.values()) {
            if (production.contains("λ") && production.length() > 1 && !production.equals(initialProduction)) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<Character> toCharacterArrayList () {
        ArrayList<Character> charactersArrayList = new ArrayList<>();
        for (char variable : variables) {
            if (variable != initialSymbol)
                charactersArrayList.add(variable);
        }
        return charactersArrayList;
    }

    private void initializeTable (String w) {
        cykTable  = new char[w.length()][w.length()];
        //Fill the first column
        for (int i = 0; i < w.length(); i++) {
            for (char key : productions.keySet()){
                if (productions.get(key).contains("" + w.charAt(i))) {
                    cykTable[i][0] = key;
                    break;
                }
            }
        }
    }

    public String cykTableToString () {
        String message = "";
        for (int i = 0; i < cykTable.length; i++) {
            for (int j = 0; j < cykTable[i].length; j++) {
                message += cykTable[i][j];
            }
            message += "\n";
        }
        return message;
    }
}
