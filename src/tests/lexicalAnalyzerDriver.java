package tests;
/*
 * Corey Ramirez Gomez
 * CS 311 - Automata and Language Translation
 * Lexical Analyzer
 * Driver class - performs entry and initialization for a
 * lexical analyzer object.
 */
import java.io.*;

import main.lexicalAnalyzer;
public class lexicalAnalyzerDriver 
{
    public static void main(String[] args) throws IOException
    {     
        lexicalAnalyzer la;
        
        System.out.println("Program 1 Test: ");
        File in1 = new File("program1.txt");
        la = new lexicalAnalyzer(in1);
        la.processTokens();
        System.out.println(la);
        
        System.out.println("Program 2 Test: "); 
        File in2 = new File("program2.txt");
        la = new lexicalAnalyzer(in2);
        la.processTokens();
        System.out.println(la);
        
    }
}
