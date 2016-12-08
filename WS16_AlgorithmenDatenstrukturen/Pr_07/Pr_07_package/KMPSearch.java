package Pr_07_package;

import vorgaben.praktikum7.StringSearcher;

public class KMPSearch implements StringSearcher {

	@Override
	public int search(String text, String key) {
		char[] texts = text.toCharArray();
		char[] keys = key.toCharArray();
		
		int i = 0;
		int j = 0;
		int[] T = kmpTable(keys);
		
		while(i<texts.length){
			while(j>=0 && texts[i]!=keys[j]) j = T[j];
				i++;
				j++;
				if (j==keys.length){
					return i-j;
					//j=T[j];
				}
			
		}
		
		return -1;
		
	}
	
	public int[] kmpTable(char[] keys){
		int[] T = new int[keys.length+1];
		
		int i = 0;
		int j = -1;
		T[i] = j;
		while(i<keys.length){
			while(j>=0 && keys[i]!=keys[j]) j = T[j];
				i++;
				j++;
				T[i]=j;
		}
		
		return T;
		
	}
	
	public int ronjaSearch(String text, String key){
		int progress = 0;
		char[] keys = key.toCharArray();
		for (int i = 0; i < text.length(); i++) {
			if(i+keys.length > text.length())
				break;
			if (text.charAt(i) == key.charAt(progress)) {
				if (progress == key.length() - 1)
					return i - progress;
				progress++;
			} else {
				i -= progress;
				progress = 0;
			}
		}
		return -1;
	}

}
