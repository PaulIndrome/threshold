package LiveBaum;


public class TestBaum {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MeinBaum baum = new MeinBaum();
		System.out.println(baum);
		baum.insert(new Integer(5));
		System.out.println(baum);
		baum.insert(new Integer(4));
		System.out.println(baum);
		baum.insert(new Integer(6));
		System.out.println(baum);
		baum.insert(new Integer(3));
		System.out.println(baum);
	}

}
