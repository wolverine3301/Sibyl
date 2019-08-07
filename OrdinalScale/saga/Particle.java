package saga;

import java.lang.reflect.InvocationTargetException;

public class Particle<T> implements Cloneable{
	public T value;
	public String type;
	
	/**
	 * particle generic object for dynamic resolving of dtypes
	 * @param value
	 */
	public Particle(T value) {
		resolveType(value);
	}
	
	/**
	 * Copy constructor.
	 * @param value the value of the particle.
	 * @param type the data type of the particle. 
	 */
	public Particle(Particle<T> theParticle) {
	    type = theParticle.type;
	    value = theParticle.value;
	    // Tried to force a copy constuctor, didnt work lol
//	    try {
//	        Class<? extends Object> c = theParticle.value.getClass();
//            @SuppressWarnings("unchecked")
//            T newData = (T) c.getConstructor(c).newInstance(theParticle.value);
//            value = newData;
//        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
//                | NoSuchMethodException | SecurityException e) {
//            System.out.println("ERROR COPYING PARTICLE: " + e.getClass());
//        }
	}
	
	@SuppressWarnings("unchecked")
	private void resolveType(T value) {
		// is it a string?
		if (value instanceof String) {
			// is the string a number?
			if(isNumeric(value)) {
				//is the number an integer?
				if(isInteger(value)) {
					Integer n = Integer.parseInt((String) value);
					this.value = (T) n;
					this.type = "Integer";
				}
				// is the string a double
				else {
					Double d = Double.parseDouble((String) value);
					this.value = (T) d;
					this.type = "Double";
				}//
			//its actually a string
			} else{
				String s = (String) value;
				//check for blank or null values
				if(s.isBlank() || s.toUpperCase().contentEquals("NAN") || s.toUpperCase().contentEquals("NULL")) {
					this.value = (T) "NAN";
					this.type = "NAN";	
				} else {
					this.value = value;
					this.type = "String";
				}
			}//end string stuff
		
		} else {
			this.value = value;
			this.type = value.getClass().getName();
		}//end object typs
		
	}
	/**
	 * is it an integer
	 * @param strNum
	 * @return
	 */
	private boolean isInteger(T strNum) {
	    try {
	        Integer.parseInt((String) strNum);
	    } catch (NumberFormatException | NullPointerException nfe) {
	        return false;
	    }
	    return true;
	}
	
	/**
	 * is it a double
	 * @param strNum
	 * @return
	 */
	private boolean isDouble(T strNum) {
	    try {
	        Double.parseDouble((String) strNum);
	    } catch (NumberFormatException | NullPointerException nfe) {
	        return false;
	    }
	    return true;
	}
	/**
	 * is it a number
	 * @param strNum
	 * @return
	 */
	private boolean isNumeric(T strNum) {
	    try {
			Double.parseDouble((String) strNum);
	    } catch (NumberFormatException | NullPointerException nfe) {
	        return false;
	    }
	    return true;
	}
	
	public String getType() {
	    return type;
	}
	
	/**
	 * returns value
	 * @return
	 */
	public T getValue() {
		return value;
	}
	
	public String toString() {
	    return type + "=" + value;
	}

}
