package osFinal;

import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.RecursiveAction;

public class ChildProcess extends RecursiveAction {

	public LinkedList<Word> list;
	public char sortType;
	
	public ChildProcess(LinkedList<Word> list, char sortType) {
		this.list = list;
		this.sortType = sortType;
	}

	@Override
	protected void compute() {
		this.sort();
		this.writeToFile();
	}
	
	//idea for this implementation comes from https://stackoverflow.com/questions/2784514/sort-arraylist-of-custom-objects-by-property
	public void sort() {
		switch (sortType){
		case 'l':	//level
			Collections.sort(list, (o1, o2) -> o1.level.compareTo(o2.level));
			break;
		case 'c':	//character
			Collections.sort(list, (o1, o2) -> o1.character.compareTo(o2.character));
			break;
		case 'm':	//meaning
			Collections.sort(list, (o1, o2) -> o1.meaning.compareTo(o2.meaning));  
			break;
		case 'o':	//onyomi reading
			Collections.sort(list, (o1, o2) -> o1.onyomi.compareTo(o2.onyomi)); 
			break;
		case 'k':	//kunyomi reading
			Collections.sort(list, (o1, o2) -> o1.kunyomi.compareTo(o2.kunyomi));
			break;
		default: 
			System.out.printf("Error: sort type (%c) not found\n", sortType);
			break;
		}
	}
	
	public void writeToFile() {
		System.out.printf("writing child with sort method %c to file...\n", sortType);
		
	}
	
}
