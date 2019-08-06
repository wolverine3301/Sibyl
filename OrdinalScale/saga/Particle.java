package saga;

public class Particle<T> {
	public T value;
	public String type;
	/**
	 * particle generic object for dynamic resolving of dtypes
	 * @param value
	 */
	public Particle(T value) {
		resolveType(value);
		
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
				else{
					Double d = Double.parseDouble((String) value);
					this.value = (T) d;
					this.type = "Double";
					
				}//
			//its actually a string
			}
			else{
				String s = (String) value;
				//check for blank or null values
				if(s.isBlank() || s.toUpperCase().contentEquals("NAN") || s.toUpperCase().contentEquals("NULL")) {
					this.value = (T) "NAN";
					this.type = "NAN";	
				}else {
					this.value = value;
					this.type = "String";
				}
			}//end string stuff
		
		}else {
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
	        Integer d = Integer.parseInt((String) strNum);
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
	@SuppressWarnings("unused")
	private boolean isDouble(T strNum) {
	    try {
	        double d = Double.parseDouble((String) strNum);
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
	        double d = Double.parseDouble((String) strNum);
	    } catch (NumberFormatException | NullPointerException nfe) {
	        return false;
	    }
	    return true;
	}
	

}
