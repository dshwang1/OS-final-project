package osFinal;


public class Word {
	
	Integer level;
	String character, meaning, onyomi, kunyomi;
	
	public Word() {
		this.level = 0;
		this.character = null;
		this.meaning = null;
		this.onyomi = null;
		this.kunyomi = null;		
	}
	public Word(int lev, String cha, String mean, String on, String kun) {
		this.level = lev;
		this.character = cha;
		this.meaning = mean;
		this.onyomi = on;
		this.kunyomi = kun;
	}

}

