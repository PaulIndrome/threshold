package LiveBaum;


public class MeinBaum {
	private TreeNode wurzel;
	
	public MeinBaum(){
		wurzel=null;
	}
	public MeinBaum(TreeNode wurzel){
		this.wurzel=wurzel;
	}
	
	public void insert(Object data){
		TreeNode neu = new TreeNode(data);
		if(wurzel == null){
			wurzel=neu;
		}
		else{
			insert(wurzel, neu);
		}
	}
	private void insert(TreeNode wurzel, TreeNode neu){
		if(wurzel.equals(neu)){
			//links einfuegen
			if(wurzel.getLinkerTeilbaum()==null){
				wurzel.setLinkerTeilbaum(neu);
			}else{
				insert(wurzel.getLinkerTeilbaum(), neu);
			}
		}else{
			if(wurzel.getRechterTeilbaum()==null){
				wurzel.setRechterTeilbaum(neu);
			}else{
				insert(wurzel.getRechterTeilbaum(), neu);
			}
			}
			
		}
	
	public String toString(){
		String s ="leer";
		if(wurzel !=null){
			MeinBaum linkerTeil = new MeinBaum(wurzel.getLinkerTeilbaum());
			MeinBaum rechterTeil = new MeinBaum (wurzel.getRechterTeilbaum());
			s="Baum("+wurzel.getData()+", "+linkerTeil.toString()+", "+rechterTeil.toString()+")";
		}
		
		return s;
	}
}
