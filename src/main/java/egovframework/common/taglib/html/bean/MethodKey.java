package egovframework.common.taglib.html.bean;

public class MethodKey {
    private Class objectClass;
    private String propertyName;
    private Class parameterType;
    private int hashCode;
	
	public MethodKey(Class objectClass, String propertyName) {
	    this(objectClass, propertyName, null);
	}
	
	public MethodKey(Class objectClass, String propertyName, Class parameterType) {
	    this.objectClass = objectClass;
	    this.propertyName = propertyName;
	    this.parameterType = parameterType;
	    this.hashCode = propertyName.hashCode() + objectClass.hashCode()
	            + (null != parameterType ? parameterType.hashCode() : 0);
	}
	
	public boolean equals(Object obj) {
	    boolean result = false;
	    if (obj instanceof MethodKey) {
	        MethodKey other = (MethodKey) obj;
	        if (propertyName.equals(other.propertyName)
	                && objectClass.equals(other.objectClass)) {
	            if (parameterType == null) {
	                result = other.parameterType == null;
                } else if(parameterType.equals(other.parameterType)) {
                    result = true;
                }
	        }
	    }
	
	    return result;
	}
	
	public int hashCode() {
	    return hashCode;
	}
}
