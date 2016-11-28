package altroggen;

public class Seminar02HA {
	
	public static void main(String[] args){
		
		System.out.println("Wie ist dein Name?");		
		String name = Keyboard.readString();
		
		System.out.println("Wie alt bist du?");
		int alter = Keyboard.readint();
		
		System.out.println("Wo wohnst du?");
		String wohnort = Keyboard.readString();
		
		System.out.println("Wie ist dein Geschlecht? (1 = weiblich, 0 = männlich");
		int geschlecht = Keyboard.readint();
		
		System.out.println("Dein Name ist " + name);
		System.out.println("Du bist " + alter + " Jahre alt.");
		System.out.println("Du wohnst in " + wohnort + ".");
		
		if (geschlecht == 1){
			System.out.println("Du bist weiblich.");
			}
		else if (geschlecht == 0){
			System.out.println("Du bist männlich.");
			}
		else {
			System.out.println("Willst du mich verarschen?");
			}
			
		
		
		
		
	}

}
