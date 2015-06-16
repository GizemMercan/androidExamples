package info.androidhive.slidingmenu;

public class Food {

    private String name;
    private int quantity;
    private String pictureUrl;
    private String leftDay;

    public  Food(String name,int quantity,String pictureUrl){
        this.name = name;
        this.quantity = quantity;
        this.pictureUrl = pictureUrl;
    }
    public  Food(String name,String pictureUrl,String leftDay){
        this.name = name;
       
        this.pictureUrl = pictureUrl;
        this.leftDay=leftDay;
    }
    
    public  Food(String name,String pictureUrl){
        this.name = name;

        this.pictureUrl = pictureUrl;
    }
 
    public  Food(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
    public String getLeftDay(){
        return leftDay;
    }

    public int getQuantity(){
        return quantity;
    }
   
    public String getpictureUrl(){
        return pictureUrl;
    }
}