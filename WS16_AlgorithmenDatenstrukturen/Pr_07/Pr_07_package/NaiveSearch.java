package Pr_07_package;

import vorgaben.praktikum7.StringSearcher;

public class NaiveSearch implements StringSearcher {

	@Override
	public int search(String text, String key) {
		char[] keys = key.toCharArray();
		boolean found = false;
		for (int i = 0; i < text.length(); i++) {
			if(i+keys.length > text.length())
					break;
			for (int k = 0; k < keys.length; k++) {
				if (i + k < text.length()) {
					found = (keys[k] == text.charAt(i + k)) ? true : false;
					if (!found)
						break;
				} else {
					break;
				}
			}
			if (found) {
				return i;
			}
		}
		return -1;
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
