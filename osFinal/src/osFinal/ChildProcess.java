package osFinal;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChildProcess extends RecursiveAction {
	private static final long serialVersionUID = 1L;
	public LinkedList<Word> list = new LinkedList<Word>();
	public char sortType;
	private final Lock mlock = new ReentrantLock(true);
	
	public ChildProcess(LinkedList<Word> oldList, char sortType) {
		this.sortType = sortType;
		mlock.lock();
		for(Word w : oldList) {
			this.list.add(w);
		}
		mlock.unlock();
	}

	@Override
	protected void compute() {
		try {
			this.sort();
		} catch (NonsupportedSortByException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		try {
			this.writeToFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//idea for this implementation comes from https://stackoverflow.com/questions/2784514/sort-arraylist-of-custom-objects-by-property
	public void sort() throws NonsupportedSortByException  {
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
			throw new NonsupportedSortByException("Error: sort type (" + sortType + ") not found. Aborting...");
		}
	}
	
	public void writeToFile() throws IOException {
		FileWriter result = new FileWriter("sortedBy" + sortType + ".txt");
		for(Word w : list) {
			result.write(w.toString() + "\n");
		}
		
		result.close();
	}
	
}
