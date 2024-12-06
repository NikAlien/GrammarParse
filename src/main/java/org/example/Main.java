package org.example;

public class Main {
    public static void main(String[] args) throws Exception {

        Grammar grammar = new Grammar("src/main/resources/g3.txt");

        ParserRecursiveDescendent parser = new ParserRecursiveDescendent(grammar);

        parser.recursiveDescendentParsing("aacbc");

    }
}