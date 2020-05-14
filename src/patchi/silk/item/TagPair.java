package patchi.silk.item;

public class TagPair {

	private String tag;
	private String data;
	
	public TagPair(String tag, String data) {
		this.tag = tag;
		this.data = data;
	}
	

	public boolean tagEquals(String t) {
		return tag.equals(t);
	}
	
	public boolean dataEquals(String d) {
		return data.equals(d);
	}
	
	public String[] getData(String regex){
		return data.split(regex);
	}
	
	public String getData() {
		return data;
	}
	
	public String getTag() {
		return tag;
	}
	
}
