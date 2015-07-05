package funnytime.ru.whitemonkteam.funny_time.funnytime.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Book extends Item implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = -3462713489862843970L;

    @SerializedName("autor")
	 public String Author;

    @SerializedName("page")
     public int Page;

     public Integer Count;
     
     public Book(){}
     public Book ( Book b)
     {
    	 this.Name = b.Name;
    	 this.Count = b.Count;
     }
     @Override
    public boolean equals(Object o) 
     {
    	 Book b = (Book) o;
    	if ( this.Count > b.Count )
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    	
    	
    	
    }
     
     
    
}
