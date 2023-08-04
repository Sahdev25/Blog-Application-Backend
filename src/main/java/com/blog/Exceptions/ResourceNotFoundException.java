package com.blog.Exceptions;

public class ResourceNotFoundException extends RuntimeException {

	private String resourcename;
	   private String resourceField;
	   private int resourceValue;
	   public ResourceNotFoundException(String resourceName,String resourceField,int resourceValue)
	   {
		   super(resourceName+" with this "+resourceField+" of "+resourceValue+"is not found.");
		   this.resourceField=resourceField;
		   this.resourcename=resourceName;
		   this.resourceValue=resourceValue;
	   }
}
