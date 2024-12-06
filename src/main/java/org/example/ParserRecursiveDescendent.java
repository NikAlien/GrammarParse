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

    public String getState() {
        return state;
    }

    public int getPosition() {
        return position;
    }

    public Stack<Pair<String, Integer>> getWorkingStack() {
        return workingStack;
    }

    public Stack<String> getInputStack() {
        return inputStack;
    }

    public ParserRecursiveDescendent(Grammar grammar) {
        this.grammar = grammar;
    }

    public void config() {
        this.state = "q";
        this.position = 0;
        this.inputStack.push(grammar.initialState);
    }

    public void  recursiveDescendentParsing(String w) {
        // TODO: week 10
    }

    public void Expand() {
        String currentSymbol = this.inputStack.pop();
        String firstProd = this.grammar.getSetOfProductions().get(currentSymbol).getFirst();
        this.inputStack.push(firstProd);
        this.workingStack.push(new Pair<>(currentSymbol, 0));

    }

    public void Advance() {
        this.position++;
        String elem = this.inputStack.pop();
        List<String> auxiliar = Arrays.stream(elem.split("\\s+")).toList();
        this.workingStack.push(new Pair<>(auxiliar.getFirst(), -1));

        auxiliar = auxiliar.subList(1, auxiliar.size());
        this.inputStack.push(String.join(" ", auxiliar));
    }

    public void MomentaryInsuccess() {
        this.state = "b";
    }

    public void Back() {
        this.position--;
        Pair<String, Integer> elem = this.workingStack.pop();
        String head = this.inputStack.pop();
        this.inputStack.push(elem.getFirstElement() + " " + head);
    }

    public void AnotherTry() {
        Pair<String, Integer> head = this.workingStack.pop();
        String value = head.getFirstElement();
        Integer index = head.getSecondElement();

        this.inputStack.pop();
        List<String> prods = this.grammar.setOfProductions.get(value);

        if(index + 1 < prods.size()) {
            this.state = "q";
            head.setSecondElement(index + 1);
            this.workingStack.push(head);
            this.inputStack.push(prods.get(index));
        } else if (this.position == 0 && value.equals(grammar.initialState)) {
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
