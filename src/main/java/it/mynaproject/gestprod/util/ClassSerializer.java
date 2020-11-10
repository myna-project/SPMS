package it.mynaproject.gestprod.util;

import java.lang.reflect.Field;

public class ClassSerializer {
	
	private Object obj;

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(obj.getClass().getName());
		builder.append(" [");
		for(Field f: obj.getClass().getDeclaredFields()) {
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
