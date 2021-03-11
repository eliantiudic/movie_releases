//Elian Tiudic TY2
//Extra credit question: "Did World War 2 lead to an increase in war genre releases?"
import java.io.*;
import java.util.*;
public class Assignment4 {

	public static void main(String[] args) throws IOException {
		
		//creating an input file
		File input=new File("input.txt");
		Scanner scan=new Scanner(input);
				
		//creating an output file
		File output=new File("output.txt");
		PrintWriter out=new PrintWriter(output);
		
		HashMap <Integer,List<String>> movieGenre= new HashMap<Integer,List<String>>();
		
		String[] newTokens=new String[2];
		String[] genres=new String[10];
		
		//parsing the movie titles
		while(scan.hasNextLine()) {
			String s = scan.nextLine();
			//splitting the line using the comma as a separator
			String[] tokens=s.split("[,]");
			
			//some entries were not valid, so I dealt with them with a try catch block
			try {
				
				// parsing entries whose title doesn't start with "
				if(tokens[1].charAt(0)!='"') {
					
					// parsing the release year
					Integer releaseYear=new Integer(tokens[1].substring
							(tokens[1].length()-5,tokens[1].length()-1));
					
					// if there is no arraylist corresponding to a particular year, we create one
					movieGenre.putIfAbsent(releaseYear, new ArrayList<String>());
					
					//parsing and separating the different genres of a movie
					genres=tokens[2].split("[|]");
					
					// putting the genres released each distinct year in an arraylist
					for(int i=0;i<genres.length;i++)
						movieGenre.get(releaseYear).add(genres[i]);
				}
				// parsing entries whose title starts with "
				else {
					
					//the code below does exactly the same thing as the code of the if statement
					// however, the tokens of the line are split with " instead
					newTokens=s.split("[\"]");
					
					Integer releaseYear=new Integer(newTokens[1].substring
							(newTokens[1].length()-5,newTokens[1].length()-1));

					movieGenre.putIfAbsent(releaseYear, new ArrayList<String>());
					
					genres=newTokens[2].substring(1).split("[|]");
					
					for(int i=0;i<genres.length;i++)
						movieGenre.get(releaseYear).add(genres[i]);

				}
				//if the program catches a parsing error, it will print an error message
			    } catch (Exception e) {
			      System.out.println("Something went wrong, the entry could not be processed.");
			      System.out.println();
			    }
			
		}
		
		//iterating through the map
		//for every set of key and value in the map, we get a map element containing both
		for (Map.Entry<Integer,List<String>> mapElement : movieGenre.entrySet()) { 
			
			//we get the value of the key
            Integer key = (Integer)mapElement.getKey(); 
            
            //we get the value corresponding to the key, in this case a whole arraylist
            ArrayList<String> value = ((ArrayList<String>)mapElement.getValue()); 
            
            //we print the key, which in our case is the release year
            out.println(key + ":"); 
            
            //create a set that will help us print distinct genres,
            //without printing the appearance of a genre multiple times
            Set<String> added = new HashSet<>();
			    
            for (String s : value) {
            	//the if statement ensures that if an element was already added to the list, and thus printed
            	//its frequency cannot be printed again
            	if (added.add(s))
            		//uses the frequency method of Collections to find the number of times a genre appears
			    	out.println(s+ " releases: " + Collections.frequency(value, s));
			    }
            out.println();
            out.println();
        } 
		
		//closing input and output files
		scan.close();
		out.close();
	}

}
