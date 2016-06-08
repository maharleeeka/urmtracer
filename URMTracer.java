import java.io.*;
import java.util.*;

class URMTracer{
	static ArrayList <Instruction> instructionList = new ArrayList ();


	public static void main(String[] args) {
		String fileName = "C://mp1.in";
		String line = null;
		
		try{	
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			/* getting the first line to as the first state*/
			String first_line = bufferedReader.readLine();
			String[] str = first_line.split("\\s+");
       		ArrayList <Integer> current_state = new ArrayList();
      
      		/* adding the numbers in the first state to an arraylist */
       		for (int i = 0; i < str.length; i++ ) {
       			current_state.add(Integer.parseInt(str[i]));
       		}
       		
       		/*adding the remaining lines (for raw instructions) to a temporary arraylist*/
       		ArrayList <String> a = new ArrayList();
       		while((line = bufferedReader.readLine()) != null) {
                a.add(line);
            }

            /*cleaning the instructions and calling the method to call the function that makes the clean instructions*/
            for (int i = 0, ctr = 1; i <= a.size()-1; i++, ctr++) {
            	str = a.get(i).split(" ");
            	makeInstructions(ctr, a.get(i).substring(0,1), a.get(i).substring(1,a.get(i).length()));
            }
            /* these lines just print the whole given
            // for (int i = 0; i < instructionList.size(); i++) {
            // 	Instruction d = instructionList.get(i);
            // 	System.out.print(d.lineNumber + "\t" + d.command + "\t" + d.arguments);
            // 	System.out.println("");
            // }
			*/


            //calls the main execution function
	        trace(current_state,instructionList);
        	
	    }catch(FileNotFoundException ex){
	    	System.out.println("Unable to open " + "'" + fileName + "'");
	    }catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
     	
	}

    static void makeInstructions(int l_number, String comm, String to_do){ //puts the instructions to an arraylist
    	Instruction a = new Instruction(l_number, comm, to_do);
    	instructionList.add(a);
    }

    static void split(ArrayList <Instruction> instructionList){ //splitter (to get rid of the first space character)
    	Instruction a;
    	String[] str;
    	String newArgument;
    	for (int i = 0; i <= instructionList.size() - 1; i++) {
    		a = instructionList.get(i);
    		str = (a.arguments).split(" ");
    		newArgument = (a.arguments).substring(1,(a.arguments.length()));
    		a.arguments = newArgument;
    	}
    }

    static void trace(ArrayList <Integer> current_state, ArrayList <Instruction> instructionList) throws IOException{ //computing and writing for the output file
     	File file = new File("mp1.out");
        file.createNewFile();        
        FileWriter writer = new FileWriter(file);
        split(instructionList);
    	for (int i = 0; i < instructionList.size() ;) {
    		Instruction ins = instructionList.get(i);
    		int index, index2, number;
    		
    		if((ins.command).equals("S")){
    			index = Integer.parseInt(ins.arguments);
    			current_state.set(index, current_state.get(index) + 1);
    			i++; 
    		}
    		else if((ins.command).equals("Z")){
    			index = Integer.parseInt(ins.arguments);
    			current_state.set(index, 0);
    			i++;
    		}
    		else if((ins.command).equals("C")){
    			String[] str = (ins.arguments).split(" ");
    			index = Integer.parseInt(str[0]);
    			index2 = Integer.parseInt(str[1]);
    			current_state.set(index2, current_state.get(index));
    			i++;
    		}
    		else if((ins.command).equals("J")){    			
    			String[] str = (ins.arguments).split(" ");
    			index = Integer.parseInt(str[0]);
    			index2 = Integer.parseInt(str[1]);
    			number = Integer.parseInt(str[2]);
    			if(current_state.get(index) == current_state.get(index2)){
    				i = number - 1;}
    			else{
    				i++;}
    		}
    		System.out.println(current_state);
    		int c = 0;
    		for (c = 0; c < current_state.size(); c++) {
    			writer.write(String.valueOf(current_state.get(c)) + " ");
    		}
    		writer.write("\n");
    	}
    	writer.flush();
    	writer.close();

    }
}

class Instruction{
	int lineNumber;
	String arguments;
	String command;
	public Instruction(){
		lineNumber = 0;
		command = null;
		arguments = null;
	}
	public Instruction (int lineNumber, String command, String arguments){
		this.lineNumber = lineNumber;
		this.command = command;
		this.arguments = arguments;
	}
}