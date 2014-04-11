package main;
/*
 * Corey Ramirez Gomez
 * CS 311 - Automata and Language Translation
 * Lexical Analyzer
 * lexical analyzer object class - create an object that will perform the
 * operations to split up the inputs into tokens and print them out accordingly.
 */
 import java.io.*;
 import java.util.EmptyStackException;
 import java.util.Scanner;
 import java.util.Stack;
 import java.util.StringTokenizer;
public class lexicalAnalyzer 
{
    private Stack rawInputLines;/*temporary storage for array objects of each 
               line from the input file which are tokenized (array of strings)*/
    //array of string arrays for each input line
    private StringTokenizer tokenedInputLines[];
    /*This array corresponds to the tokenizedInputLines
    where it holds arrays, but the array is of
    Integer objects that correspond to each string
    from the tokenizedInputLines.*/
    private Object processedInput[];
    //pre-defined token strings
    private String [] tokenImage = {"DOUBLE", "ELSE", "IF", "INT", "RETURN", 
                                    "VOID", "WHILE", "plus", "minus", 
                                    "multiplication", "division", "less", 
                                    "lessOrEqual", "greater",
                                    "greaterOfEqual", "equal", "notEqual", 
                                    "assignOp",
                                    "semicolon", "comma", "period", "leftparen",
                                    "rightparen", "leftbracket", "rightbracket",
                                    "leftbrace","rightbrace", "id", "num"};
    private boolean lineCommentFlag = false;
    private boolean blockCommentFlag = false;

    public lexicalAnalyzer(File input) throws IOException
    {
        Scanner in = new Scanner(input);
        //initialize stack for getting lines from the file.       
        rawInputLines = new Stack();
        while(in.hasNext())//while the file has lines push them to the stack
        {
            //while pushing lines to the stack, tokenize them.
            rawInputLines.push(new StringTokenizer(in.nextLine()));
        }
        //initialize a StringTokenizer array to begin accepting the tokened 
        //lines from the stack.
        tokenedInputLines = new StringTokenizer [rawInputLines.size()];
        //initialize an object array to begin accepting the corresponding array
        // of token
        //image integers so they may be easily referenced and printed later
        //with the help of the tokenImage array.
        processedInput = new Object[tokenedInputLines.length];
        //pop the tokened lines into an array. This allows for the lines to be
        //in the proper order now, since earlier they were pushed and in a
        //reversed order.
        for(int i = rawInputLines.size()-1; i > -1; i--)
        {
            tokenedInputLines[i] = (StringTokenizer)rawInputLines.pop();
        }
    }
    public void processTokens()
    {
        //process each line of the file, at this point each line is saved
        //as tokens so white spaces and newlines will not be a problem.
        for(int i = 0; i < tokenedInputLines.length; i++)
        {
            //for temporarily storing the image token integer while each
            //token is being processed.
            Stack tempInputLineValues = new Stack();
            //process each token individually
            while(tokenedInputLines[i].hasMoreTokens())
            {
                convertToCharacters(tempInputLineValues,
                        new StringReader(tokenedInputLines[i].nextToken()));
            }
            //load the resulting image integer tokens into a readable array 
            //to be printed later when needed.
            Integer inputLineValues [] = 
                    new Integer [tempInputLineValues.size()];
            for(int j = tempInputLineValues.size() - 1; j > -1; j--)
            {
                inputLineValues[j] = (Integer)tempInputLineValues.pop();
            }
            //end of a single line being processed
            //save that processed line array of image integer tokens into the 
            //the processedInput array and proceed to the next inputted line.
            processedInput[i] = inputLineValues;
            lineCommentFlag = false;
        }
    }
    private void convertToCharacters(Stack imageTokenIntegers, StringReader sr)
    {
        //temporary stack to save each character as they are extracted from
        //the string token.
        Stack s = new Stack();
        int in = -1;
        //extract the characters from the string
        do
        {
            try
            {
                in = sr.read();
            }
            catch(IOException e)
            {
                break;
            }
            s.push(in);
        }
        while(in != -1);
        //character arry to step through and analyze(easier to do than with
        //the stringreader.
        char characters [] = new char [s.size()];        
        //load the character array with the elements from the stack
        for(int i = characters.length-1; i > -1; i-- )
        {
            int a = (Integer)s.pop();
            //allows for skipping of the null character in the string.
            if(i < characters.length-1)
            {
                Character b = (char)a;
                characters[i] = b;            
            }
        }        
        //process that character array
        processCharacters(imageTokenIntegers, characters);
    }
    private void processCharacters(Stack imageTokenIntegers, char chars [])
    {
        boolean possibleIdentifier = false;        
        
        boolean possibleKeyword = false;      
        
        String record = new String();
        for(int i = 0; i < chars.length-1; i++)
        {
            if(lineCommentFlag)
                break;
            char input = chars[i];
            record += input;
            switch(input)
            {
                //following cases aren't involved in keywords so perform the
                //same function at the end.
                case 'a':
                case 'A':
                case 'b':                    
                case 'B':
                case 'c':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                case 'g':
                case 'G':
                case 'H':
                case 'I':
                case 'j':
                case 'J':
                case 'k':
                case 'L':
                case 'm':
                case 'M':
                case 'N':
                case 'O':
                case 'p':
                case 'P':
                case 'q':
                case 'Q':
                case 'R':
                case 'S':
                case 'T':
                case 'U':
                case 'V':
                case 'W':
                case 'x':
                case 'X':
                case 'y':
                case 'Y':
                case 'z':
                case 'Z':
                case 'h':
                case 'i':
                case 'l':                    
                case 'u':
                case 'v':
                case 'w':  
                case 'o':
                case 'r':
                case 's':
                case '_':
                    if(possibleKeyword)
                    {
                        imageTokenIntegers.pop();
                        imageTokenIntegers.push(27);
                        possibleKeyword = false;
                        break;
                    }
                    try
                    {
                        if(imageTokenIntegers.peek() == (Integer)27)
                        {
                            possibleIdentifier = true;
                            possibleKeyword = false;
                            break;
                        }
                        else
                        {
                            imageTokenIntegers.push(27);
                            possibleIdentifier = true;
                            possibleKeyword = false;
                            break;
                        } 
                    }
                    catch(EmptyStackException e)
                    {
                        imageTokenIntegers.push(27);
                        possibleIdentifier = true;
                        possibleKeyword = false;
                        break;
                    }
                                       
                //following cases involve keywords, depending on the case
                //a different action will be taken.
                case 'd':
                    if(record.compareTo("void") == 0)
                    {
                        possibleKeyword = true;
                        if(possibleIdentifier)
                            imageTokenIntegers.pop();                        
                        imageTokenIntegers.push(5);
                        possibleIdentifier = false;
                    }
                    possibleIdentifier = true;
                    try
                    {
                        if(imageTokenIntegers.peek() == (Integer)27)
                        {
                            break;
                        }     
                        imageTokenIntegers.push(27);
                    }
                    catch(EmptyStackException e)
                    {
                        imageTokenIntegers.push(27);
                        break;
                    }
                    break;
                case 'e':
                    if(record.compareTo("else") == 0)
                    {
                        possibleKeyword = true;     
                        if(possibleIdentifier)
                            imageTokenIntegers.pop();                        
                        imageTokenIntegers.push(1);                        
                        possibleIdentifier = false;
                    }
                    if(record.compareTo("double") == 0)
                    {
                        possibleKeyword = true;
                        if(possibleIdentifier)
                            imageTokenIntegers.pop();                        
                        imageTokenIntegers.push(0);                        
                        possibleIdentifier = false;
                    }
                    if(record.compareTo("while") == 0)
                    {
                        possibleKeyword = true;
                        if(possibleIdentifier)
                            imageTokenIntegers.pop();                        
                        imageTokenIntegers.push(6);                        
                        possibleIdentifier = false;
                    }
                    possibleIdentifier = true;
                    try
                    {
                        if(imageTokenIntegers.peek() == (Integer)27)
                        {
                            break;
                        }     
                        imageTokenIntegers.push(27);
                    }
                    catch(EmptyStackException e)
                    {
                        imageTokenIntegers.push(27);
                        break;
                    }
                    break;
                case 'f': 
                    if(record.compareTo("if") == 0)
                    {
                        possibleKeyword = true;
                        if(possibleIdentifier)
                            imageTokenIntegers.pop();                        
                        imageTokenIntegers.push(2);                        
                        possibleIdentifier = false;
                    }    
                    possibleIdentifier = true;
                    try
                    {
                        if(imageTokenIntegers.peek() == (Integer)27)
                        {
                            break;
                        }     
                        imageTokenIntegers.push(27);
                    }
                    catch(EmptyStackException e)
                    {
                        imageTokenIntegers.push(27);
                        break;
                    }
                    break;
                case 'n': 
                    if(record.compareTo("return") == 0)
                    {
                        possibleKeyword = true;
                        if(possibleIdentifier)
                            imageTokenIntegers.pop();
                        imageTokenIntegers.push(4);                        
                        possibleIdentifier = false;
                    }      
                    possibleIdentifier = true;
                    try
                    {
                        if(imageTokenIntegers.peek() == (Integer)27)
                        {
                            break;
                        }     
                        imageTokenIntegers.push(27);
                    }
                    catch(EmptyStackException e)
                    {
                        imageTokenIntegers.push(27);
                        break;
                    }
                    break;
                case 't':  
                    if(record.compareTo("int") == 0)
                    {
                        possibleKeyword = true;
                        if(possibleIdentifier)
                            imageTokenIntegers.pop();                        
                        imageTokenIntegers.push(3);                        
                        possibleIdentifier = false;
                    }    
                    possibleIdentifier = true;
                    try
                    {
                        if(imageTokenIntegers.peek() == (Integer)27)
                        {
                            break;
                        }     
                        imageTokenIntegers.push(27);
                    }
                    catch(EmptyStackException e)
                    {
                        imageTokenIntegers.push(27);
                        break;
                    }
                    break;
                //number cases
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9': 
                    if(!possibleIdentifier)
                    {
                        if(possibleKeyword)
                        {
                            possibleIdentifier = true;
                            imageTokenIntegers.pop();
                            imageTokenIntegers.push(27);
                            break;
                        }
                        try
                        {
                            if(!(imageTokenIntegers.peek() == (Integer)28))
                            {    
                                imageTokenIntegers.push(28);
                                break;
                            }                   
                            break;
                        }
                        catch(EmptyStackException e)
                        {
                            imageTokenIntegers.push(28);
                            break;
                        }
                    }
                    break;
                case '+':
                    imageTokenIntegers.push(7);
                    possibleIdentifier = false;        
                    possibleKeyword = false;
                    record = "";
                    break;
                case '-':
                    imageTokenIntegers.push(8);
                    possibleIdentifier = false;        
                    possibleKeyword = false;  
                    record = "";                    
                    break;                    
                case '*':
                    imageTokenIntegers.push(9);
                    possibleIdentifier = false;        
                    possibleKeyword = false;    
                    record = "";                    
                    break;                    
                case '/':   
                    try
                    {
                        if(imageTokenIntegers.peek() == (Integer)10)
                        {
                            imageTokenIntegers.pop();
                            lineCommentFlag = true;
                            break;
                        }
                    }
                    catch(EmptyStackException e)
                    {
                        imageTokenIntegers.push(10);
                        possibleIdentifier = false;        
                        possibleKeyword = false;     
                        record = "";                       
                        break;
                    }                    
                case '<':
                    imageTokenIntegers.push(11);
                    possibleIdentifier = false;        
                    possibleKeyword = false;  
                    record = "";                    
                    break;       
                case '>':
                    imageTokenIntegers.push(13);
                    possibleIdentifier = false;        
                    possibleKeyword = false; 
                    record = "";                    
                    break;
                case '=':
                    try
                    {
                        if(imageTokenIntegers.peek() == (Integer)11)
                        {
                            imageTokenIntegers.pop();
                            imageTokenIntegers.push(12);
                            possibleIdentifier = false;        
                            possibleKeyword = false;                            
                            break;
                        }
                    }
                    catch(EmptyStackException e)
                    {
                        imageTokenIntegers.push(17);
                        possibleIdentifier = false;        
                        possibleKeyword = false;    
                        record = "";                        
                        break;
                    }
                    try
                    {
                        if(imageTokenIntegers.peek() == (Integer)13)
                        {
                            imageTokenIntegers.pop();
                            imageTokenIntegers.push(14);
                            possibleIdentifier = false;        
                            possibleKeyword = false;
                            break;
                        }
                    }
                    catch(EmptyStackException e)
                    {
                        imageTokenIntegers.push(17);
                        possibleIdentifier = false;        
                        possibleKeyword = false;  
                        record = "";                        
                        break;
                    }
                    try
                    {
                        if(imageTokenIntegers.peek() == (Integer)17)
                        {
                            imageTokenIntegers.pop();
                            imageTokenIntegers.push(15);
                            possibleIdentifier = false;        
                            possibleKeyword = false;
                            break;                        
                        }
                    }
                    catch(EmptyStackException e)
                    {
                        imageTokenIntegers.push(17);
                        possibleIdentifier = false;        
                        possibleKeyword = false;
                        record = "";                        
                        break;
                    }
                    imageTokenIntegers.push(17);
                    possibleIdentifier = false;        
                    possibleKeyword = false;
                    record = "";
                    break;
                case '!':
                    imageTokenIntegers.push(16);
                    possibleIdentifier = false;        
                    possibleKeyword = false;
                    record = "";
                    i++;
                    break;
                case ';':
                    imageTokenIntegers.push(18);
                    break;
                case ',':
                    imageTokenIntegers.push(19);
                    possibleIdentifier = false;        
                    possibleKeyword = false;  
                    record = "";                    
                    break;
                case '.':
                    try
                    {
                        if(imageTokenIntegers.peek() == (Integer)28)
                            break;
                    }
                    catch(EmptyStackException e)
                    {
                        imageTokenIntegers.push(20);
                        break;
                    }
                    imageTokenIntegers.push(20);
                    break;                    
                case '(':
                    imageTokenIntegers.push(21);
                    possibleIdentifier = false;        
                    possibleKeyword = false;  
                    record = "";                    
                    break;                    
                case ')':
                    imageTokenIntegers.push(22);
                    possibleIdentifier = false;        
                    possibleKeyword = false;   
                    record = "";                    
                    break;                    
                case '[':
                    imageTokenIntegers.push(23);
                    possibleIdentifier = false;        
                    possibleKeyword = false;  
                    record = "";                    
                    break;                    
                case ']':
                    imageTokenIntegers.push(24);
                    possibleIdentifier = false;        
                    possibleKeyword = false;  
                    record = "";                    
                    break;                    
                case '{':
                    imageTokenIntegers.push(25);
                    possibleIdentifier = false;        
                    possibleKeyword = false;   
                    record = "";                    
                    break;                    
                case '}':               
                    imageTokenIntegers.push(26);
                    possibleIdentifier = false;        
                    possibleKeyword = false; 
                    record = "";
                    break;                    
                default:
                    System.out.println("unrecognized character");
            }
        }
    }
    public String toString()
    {
        String out = new String();
        //Step through the array accessing each object which is an Integer array
        for(int i = 0; i < processedInput.length; i++)
        {
            //convert the element at i to an Integer array so it may be accessed
            Integer in [] = (Integer[])processedInput[i];
            //step through that Integer array and input the int at position j
            //into the tokenImage array so the proper keyword, identifier or 
            //token
            for(int j = 0; j < in.length ; j++)
            {
                //print out the tokenImage and a space
                out += tokenImage[in[j]] + " ";                
            }
            //start a new line and proceed to the next array of integers.
            out += "\n";
        }        
        return out;
    }
}
