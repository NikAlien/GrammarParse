package org.example;

import java.util.List;
import java.util.Objects;
import java.util.Stack;

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

    public void  recursiveDescendentParsing(String w) {
        // TODO: week 10
    }

    public void Expand() {
        String currentState = this.inputStack.pop();
        String firstProd = this.grammar.getSetOfProductions().get(currentState).getFirst();
        this.inputStack.push(firstProd);
        this.workingStack.push(new Pair<>(currentState, 0));
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

    public void AnotherTry() {
        Pair<String, Integer> head = this.workingStack.pop();
        this.inputStack.pop();
        List<String> prods = this.grammar.setOfProductions.get(head.getFirstElement());

        if(head.getSecondElement() + 1 < prods.size()) {
            this.state = "q";
            head.setSecondElement(head.getSecondElement() + 1);
            this.workingStack.push(head);
            this.inputStack.push(prods.get(head.getSecondElement()));
        } else if (this.position == 1 && head.getFirstElement().equals(grammar.initialState)) {
            this.state = "e";
        }
        else {
            this.inputStack.push(head.getFirstElement());
        }
    }

    public void Success() {
        this.state = "f";
    }
}
