package egovframework.common.taglib.html.bean;


import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>Bean 관련 유틸 클래스</b><p>
 * 새로운 객체를 생성하거나, 값을 할당 또는 가져오는 기능을 포함.<br/>
 * 최소한의 기능만을 가지고 있다. 복잡한것은 추가하도록!
 * 
 * @author 김강우
 * @version 1.0, 2005/07/11
 * @since PCN-COMMON 1.0
 */
public class BeanUtil {

    private static Map writeMethodMap = new HashMap();
    private static Map readMethodMap = new HashMap();

	/**
	 * 객체의 변수중에서 해당하는 이름의 변수에 값을 할당한다.<p>
	 * 그 변수에 직접 값을 할당한다.
	 * 
	 * @param object 값을 할당할 변수를 가지고 있는 객체
	 * @param name 변수명
	 * @param value 변수에 할당할 값
	 */
	public static void setDeclaredFieldValue(Object object, String name, String value) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		Class clazz = object.getClass();
		Field field = clazz.getDeclaredField(name);
		Class type = field.getType();
		if (type == boolean.class) {
			field.setBoolean(object, TypeConvertUtil.convertBoolean(value, false));
		} else if (type == byte.class) {
			field.setByte(object, TypeConvertUtil.convertByte(value, (byte)0));
		} else if (type == char.class) {
			field.setChar(object, TypeConvertUtil.convertChar(value, ' '));
		} else if (type == double.class) {
			field.setDouble(object, TypeConvertUtil.convertDouble(value, 0));
		} else if (type == float.class) {
			field.setFloat(object, TypeConvertUtil.convertFloat(value, 0));
		} else if (type == int.class) {
			field.setInt(object, TypeConvertUtil.convertInt(value, 0));
		} else if (type == long.class) {
			field.setLong(object, TypeConvertUtil.convertLong(value, 0));
		} else if (type == short.class) {
			field.setShort(object, TypeConvertUtil.convertShort(value, (short)0));
		} else {
			field.set(object, value);
		}
	}
	
	/**
	 * 객체의 변수중에서 해당하는 이름의 변수에 값을 할당한다.<p>
	 * 그 변수에 직접 값을 할당한다.
	 * 
	 * @param object 값을 할당할 변수를 가지고 있는 객체
	 * @param name 변수명
	 * @param value 변수에 할당할 값
	 */
	public static void setDeclaredFieldValue(Object object, String name, Object value) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		Class clazz = object.getClass();
		Field field = clazz.getDeclaredField(name);
//		Class type = field.getType();
		field.set(object, value);
	}

	/**
	 * 객체의 변수중에서 해당하는 이름의 변수에 값을 할당한다.<p>
	 * 그 변수에 직접 값을 할당한다.
	 * 
	 * @param object 값을 할당할 변수를 가지고 있는 객체
	 * @param name 변수명
	 * @param value 변수에 할당할 값
	 */
	public static void setFieldValue(Object object, String name, Object value) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		Class clazz = object.getClass();
		Field field = clazz.getField(name);
//		Class type = field.getType();
		field.set(object, value);
	}

	
	/**
	 * 두개의 클래스 타입 배열이 일치하면 true, 아니면 false를 반환한다.
	 * 
	 * @param types0 첫번째 클래스타입
	 * @param types1 두번째 클래스 타입
	 * @return 일치 여부
	 */
    public static boolean isEquals(Class[] types0, Class[] types1) {
        boolean result = false;
        if (types0 == null) {
            if (types1 == null) {
                result = true;
            }
        } else {
            if (types1 != null && types0.length == types1.length) {
                result = true;
                for (int i = 0; i < types0.length; i++) {
                    if (!types0[i].equals(types1[i])) {
                        result = false;
                        break;
                    }
                }
            }
        }
        return result;
    }
    
    /**
     * set method추출
     * @param objectClass
     * @param propertyName
     * @return
     * @throws IntrospectionException
     * @throws NoSuchMethodException
     */
	public static Method getWriteMethod(Class objectClass, String propertyName) throws IntrospectionException, NoSuchMethodException {
	    Method method = null;
	    MethodKey key = new MethodKey(objectClass, propertyName);
	    method = (Method)writeMethodMap.get(key);
	    if (method == null) {
	        BeanInfo info = Introspector.getBeanInfo(objectClass);
	        PropertyDescriptor[] pd = info.getPropertyDescriptors();
	        if (pd != null) {
	            for (int i = 0; i < pd.length; i++) {
                    if (propertyName.equals(pd[i].getName())) {
                        method = pd[i].getWriteMethod();
                        break;
                    } else {
                        String tName = new StringBuffer().append(propertyName.substring(0, 1).toUpperCase()).append(propertyName.substring(1)).toString();
                        if (tName.equals(pd[i].getName())) {
                            method = pd[i].getWriteMethod();
                            break;                            
                        }
                    }
	            }
	        }
	        if (method == null) {
	            String methodName = new StringBuffer("set").append(propertyName.substring(0, 1).toUpperCase()).append(propertyName.substring(1)).toString(); 
	            method = objectClass.getMethod(methodName, null);
	        }
	        if (method == null) {
	            throw new NoSuchMethodException(new StringBuffer(objectClass.getName()).append("클래스의 set").append(propertyName.substring(0, 1).toUpperCase()).append(propertyName.substring(1)).append(" 메소드를 찾을 수 없습니다.").toString());
	        }
	        writeMethodMap.put(key, method);
	        
	    }
	    return method;
	}
	
	/**
	 * get/is method추출
	 * @param objectClass
	 * @param propertyName
	 * @return
	 * @throws IntrospectionException
	 * @throws NoSuchMethodException
	 */
	public static Method getReadMethod(Class objectClass, String propertyName) throws IntrospectionException, NoSuchMethodException {
	    Method method = null;
	    MethodKey key = new MethodKey(objectClass, propertyName);
	    method = (Method)readMethodMap.get(key);
	    if (method == null) {
	        BeanInfo info = Introspector.getBeanInfo(objectClass);
	        PropertyDescriptor[] pd = info.getPropertyDescriptors();
	        if (pd != null) {
	            for (int i = 0; i < pd.length; i++) {
                    if (propertyName.equals(pd[i].getName())) {
                        method = pd[i].getReadMethod();
                        break;
                    }
	            }
	        }
	        if (method == null) {
	            String methodName = new StringBuffer("get").append(propertyName.substring(0, 1).toUpperCase()).append(propertyName.substring(1)).toString(); 
	            method = objectClass.getMethod(methodName, null);
		        if (method == null) {
		            methodName = new StringBuffer("is").append(propertyName.substring(0, 1).toUpperCase()).append(propertyName.substring(1)).toString(); 
		            method = objectClass.getMethod(methodName, null);
		        }
	        }
	        if (method == null) {
	            throw new NoSuchMethodException(new StringBuffer(objectClass.getName()).append("클래스의 get").append(propertyName.substring(0, 1).toUpperCase()).append(propertyName.substring(1)).append(" 메소드를 찾을 수 없습니다.").toString());
	        }
	        readMethodMap.put(key, method);
	        
	    }
	    return method;
	}
	
	/**
	 * 객체의 변수중에서 해당하는 이름의 변수에 값을 할당한다.<p>
	 * 그 변수의 setMethod를 찾은다음 setMethod를 이용해 값을 할당한다.
	 * 
	 * @param object 값을 할당할 변수를 가지고 있는 객체
	 * @param name 변수명
	 * @param value 변수에 할당할 값
	 */
//	public static void setMethodValue(Object object, String propertyName, String value) throws NoSuchFieldException, NoSuchMethodException, IllegalArgumentException, IntrospectionException, IllegalAccessException, InvocationTargetException {
//		Class clazz = object.getClass();
//	    Field field = null;
//		try {
//			field = clazz.getDeclaredField(propertyName);
//		} catch (NoSuchFieldException e) {
//			field = clazz.getSuperclass().getDeclaredField(propertyName);
//		}
//		Class type = field.getType();
//		PropertyDescriptor descriptor = new PropertyDescriptor(propertyName, clazz);
//		Method setter = getWriteMethod(clazz, propertyName, null);
//		if(setter != null) {
//			Object args[] = new Object[1];
//			args[0] = TypeConvertUtil.convert(value, type);
//			setter.invoke(object, args);			
//		}
//	}

	/**
	 * 객체의 변수중에서 해당하는 이름의 변수에 값을 할당한다.<p>
	 * 그 변수의 setMethod를 찾은다음 setMethod를 이용해 값을 할당한다.
	 * 
	 * @param object 값을 할당할 변수를 가지고 있는 객체
	 * @param propertyName 변수명
	 * @param value 변수에 할당할 값
	 */
	public static void setMethodValue(Object object, String propertyName, Object value) throws IllegalArgumentException, IntrospectionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method setter = getWriteMethod(object.getClass(), propertyName);
        if(setter != null) {
            setter.invoke(object, new Object[] { value });    
        }
	}
	/**
	 * 객체의 변수중에서 해당하는 이름의 변수에 값을 할당한다.
	 * @param object
	 * @param propertyName
	 * @param values
	 * @throws NoSuchFieldException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void setMethodValue(Object object, String propertyName, Object[] values) throws NoSuchFieldException, NoSuchMethodException, IllegalArgumentException, IntrospectionException, IllegalAccessException, InvocationTargetException {
		Method setter = getWriteMethod(object.getClass(), propertyName);
		if(setter != null) {
			setter.invoke(object, values);			
		}
	}

	/**
	 * 
	 * @param object
	 * @param propertyName
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IntrospectionException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Object getMethodValue(Object object, String propertyName) throws IllegalArgumentException, IntrospectionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Object result = null;
        
        if (object != null) {
            int dotIndex = propertyName.indexOf('.');
            if (dotIndex > -1) {
                String varName1 = propertyName.substring(0, dotIndex);
                String varName2 = propertyName.substring(dotIndex+1);
                
                Method getter = getReadMethod(object.getClass(), varName1);
                if(getter != null) {
                    Object value1 =  getter.invoke(object, null);
                    result = getMethodValue(value1, varName2);  
                }            
            } else {
                Method getter = getReadMethod(object.getClass(), propertyName);
                if(getter != null) {
                    result =  getter.invoke(object, null);          
                }            
            } 
        }

		return result;		
	}

	/**
	 * 
	 * @param object
	 * @param propertyName
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public static Class getType(Object object, String propertyName) throws SecurityException, NoSuchFieldException {
		Class result = null;
		Field field = object.getClass().getDeclaredField(propertyName);
		result = field.getType();
		return result;		
	}	
	
	/**
	 * 해당하는 클래스의 새로운 인스턴스를 생성한다
	 * 
	 * @param className 생성할 클래스 이름
	 * @return 생성된 인스턴스
	 * @throws ClassNotFoundException 이름에 해당하는 클래스를 찾을수 없을때
	 * @throws InstantiationException 기본 생성자를 호출할수 없을때
	 * @throws IllegalAccessException 생성자에 접근을 할수 없을때
	 */
	public static Object newInstance(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class clazz = Class.forName(className);
		return clazz.newInstance();
	}	

}
