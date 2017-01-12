
public class SimpleListTester {
	public static void main(String[] args){
		SimpleList testlist = new SimpleList(new ListElement("1. Objekt")); 
		for(int i = 2; i<=20;i++){
			testlist.insertFirst(i+". Objekt");
		}
		System.out.println(testlist.toString());
		
		for(int i = 1;i<=3;i++){
			System.out.println(testlist.deleteFirst() + " gelöscht.");
		}
		
		System.out.println(testlist.toString());
	}
}
