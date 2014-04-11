package support;
import java.io.*;
import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

public class FileTokenizer
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
	
	public FileTokenizer(File input) throws FileNotFoundException
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
	
	public StringTokenizer[] getAllTokens()
	{
		return tokenedInputLines;
	}
	/*
	public String toString()
	{
		String out;
		
		for(int i = 0; i < tokenedInputLines.length; i++)
		{
			out = tokenedInputLines[0];
		}
		
		return out;
	}
	*/
}	
