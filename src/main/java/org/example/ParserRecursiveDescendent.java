package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.stream.Collectors;

public class ParserRecursiveDescendent {
    //TODO: Tests!!!!
    private Grammar grammar;
    private String state;
    private int position;
    private Stack<Pair<String, Integer>> workingStack = new Stack<>();
    private Stack<String> inputStack = new Stack<>();


    public ParserRecursiveDescendent(Grammar grammar) {
        this.grammar = grammar;
    }

    public void config() {
        this.state = "q";
        this.position = 0;
        this.inputStack.push(grammar.initialState);
    }

    public void  recursiveDescendentParsing(String w) throws Exception {
        config();
        int n = w.length();
        while(!state.equals("f") && !state.equals("e")) {
            if (state.equals("q")) {
                if(position == n && inputStack.isEmpty()) {
                    Success();
                }
                else {
                    if(grammar.isNonTerminal(inputStack.peek())) {
                        Expand();
                    }
                    else{
                        if(position != n && inputStack.peek().equals(String.valueOf(w.charAt(position)))) {
                            Advance();
                        }
                        else {
                            MomentaryInsuccess();
                        }
                    }
                }
            }
            else if(state.equals("b")) {
                if(grammar.isTerminal(workingStack.peek().getFirstElement())) {
                    Back();
                }
                else {
                    AnotherTry();
                }
            }
        }
        if(state.equals("e")) {
            System.out.println("Error!");
        }
        else{
            System.out.println(this.state);
            System.out.println(this.position);
            System.out.println(this.workingStack.toString());
            System.out.println(this.inputStack.toString());
        }
    }

    public void Expand() {
        String currentSymbol = this.inputStack.pop();
        List<String> firstProd = Arrays.stream(this.grammar.getSetOfProductions().get(currentSymbol).getFirst().split("\\s+")).toList().reversed();
        firstProd.forEach(this.inputStack::push);
        this.workingStack.push(new Pair<>(currentSymbol, 0));
    }

    public void Advance() {
        this.position++;
        String elem = this.inputStack.pop();
        this.workingStack.push(new Pair<>(elem, -1));
    }

    public void MomentaryInsuccess() {
        this.state = "b";
    }

    public void Back() {
        this.position--;
        Pair<String, Integer> elem = this.workingStack.pop();
        this.inputStack.push(elem.getFirstElement());
    }

    public void AnotherTry() throws Exception {
        Pair<String, Integer> head = this.workingStack.pop();
        String value = head.getFirstElement();
        Integer index = head.getSecondElement();

        List<String> prods = this.grammar.setOfProductions.get(value);
        List<String> currentProduction = Arrays.stream(prods.get(index).split("\\s+")).toList();

        for(String prod : currentProduction) {
            if(prod.equals(inputStack.peek()))
                inputStack.pop();
            else
                throw new Exception("This should not be possible");
        }

        if(index + 1 < prods.size()) {
            this.state = "q";
            head.setSecondElement(index + 1);
            this.workingStack.push(head);
            Arrays.stream(prods.get(index + 1).split("\\s+")).toList().reversed().forEach(this.inputStack::push);
        } else if (this.position == 1 && value.equals(grammar.initialState)) {
            this.state = "e";
        }
        else {
            this.inputStack.push(value);
        }
    }

    public void Success() {
        this.state = "f";
    }
}
