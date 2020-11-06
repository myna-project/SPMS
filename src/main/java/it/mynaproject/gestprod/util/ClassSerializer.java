package it.mynaproject.gestprod.util;

import java.lang.reflect.Field;

public class ClassSerializer {
	
	Object obj;
	Class cls;

	public void setObj(Object obj) {
		this.cls = obj.getClass();
		this.obj = obj;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(cls.getName());
		builder.append(" [");
		for(Field f: cls.getDeclaredFields()) {
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
