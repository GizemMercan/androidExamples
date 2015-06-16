package info.androidhive.slidingmenu.model;

public class WebServiceParameterItem {

	public WebServiceParameterItem(String Name, Object Value)
	{
		this.Name = Name;
		this.Value = Value;
	
	}
	    private String Name;
	    private Object Value;

	    public String getName() {
	        return Name;
	    }

	    public void setName(String Name) {
	        this.Name = Name;
	    }

	    public Object getValue() {
	        return Value;
	    }

	    public void setValue(Object Value) {
	        this.Value = Value;
	    }
}
