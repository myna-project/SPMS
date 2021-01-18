package it.mynaproject.gestprod.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ClassSerializer {
	
	private Object obj;
	private Map<String, Boolean> _exclude;
	
	public ClassSerializer() {
		this._exclude = new HashMap<String, Boolean>();
		this.exclude("password");
		this.exclude("oldPassword");
		this.exclude("productionOrders");
		this.exclude("productionOrder");
		this.exclude("additiveProductionOrderList");
		this.exclude("workingPhaseMeasureList");
		this.exclude("workingPhaseUserList");
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	public void exclude(String attrName)
	{
		if(!isExcluded(attrName))
			this._exclude.put(attrName, true);
	}
	
	private Boolean isExcluded(String attrName)
	{
		return this._exclude.containsKey(attrName);
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(obj.getClass().getName());
		builder.append(" [");
		for(Field f: obj.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			String name = f.getName();
			if(isExcluded(name)) continue;
			builder.append(name);
			builder.append(" = ");
			try {
				builder.append(f.get(obj).toString());
			} catch (Exception e) {
//				e.printStackTrace();
				builder.append("null");
			}
			builder.append(", ");
		}
		builder.append("]");
		return builder.toString();
	}
}
