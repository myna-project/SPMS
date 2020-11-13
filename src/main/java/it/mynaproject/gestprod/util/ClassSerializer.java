package it.mynaproject.gestprod.util;

import java.lang.reflect.Field;

public class ClassSerializer {
	
	private Object obj;

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	// WARNING: could serialize important user data (password)
	// if cases added as mitigation 
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(obj.getClass().getName());
		builder.append(" [");
		for(Field f: obj.getClass().getDeclaredFields()) {
			if(f.getName().equals("password") ||
			   f.getName().equals("oldPassword")) continue;
			builder.append(f.getName());
			builder.append(" = ");
			try {
				builder.append(f.get(obj).toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			builder.append(", ");
		}
		return builder.toString();
	}
}
