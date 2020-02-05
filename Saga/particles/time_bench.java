package particles;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class time_bench {
	public static Pattern pattern;
	public static Matcher match;
	public static ArrayList<SimpleDateFormat> formats = new ArrayList<SimpleDateFormat>();
	public static void main(String[] args) {
		formats.add(new SimpleDateFormat("yyyy/MM/dd")); //0
		formats.add(new SimpleDateFormat("yyyy/M/d"));	//1
		formats.add(new SimpleDateFormat("dd/MM/yyyy"));//2
		formats.add(new SimpleDateFormat("d/M/yyyy"));//3
		formats.add(new SimpleDateFormat("yyyy-MM-dd"));//4
		formats.add(new SimpleDateFormat("yyyy-M-d"));//5
		formats.add(new SimpleDateFormat("dd-MM-yyyy"));//6
		formats.add(new SimpleDateFormat("d-M-yyyy"));//7
		formats.add(new SimpleDateFormat("yyyy/MM/dd hh:mm")); //8
		formats.add(new SimpleDateFormat("yyyy/M/d hh:mm"));
		formats.add(new SimpleDateFormat("dd/MM/yyyy hh:mm"));//10
		formats.add(new SimpleDateFormat("d/M/yyyy hh:mm"));
		formats.add(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss"));//12
		formats.add(new SimpleDateFormat("yyyy/M/d hh:mm:ss"));
		formats.add(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss"));//14
		formats.add(new SimpleDateFormat("d/M/yyyy hh:mm:ss"));
		formats.add(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));//16
		formats.add(new SimpleDateFormat("yyyy-M-d hh:mm:ss"));
		formats.add(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss"));//18
		formats.add(new SimpleDateFormat("d-M-yyyy hh:mm:ss"));
		formats.add(new SimpleDateFormat("dd MMMM yyyy"));//20
		formats.add(new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss"));
		
		String[] times = { "01-22-2018", "01/22/2018","1/22/18","1-2-18","1/2/2018","1-2-2018","1/2/18 22:14:34" , "11/23/2017 12:12", "Tue, 02 Jan 2018 18:07:59"};
		
		int year,month,day,hour,min,sec;
		String form = "^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$";

		pattern = Pattern.compile(form);
		for(int i =0; i <times.length;i++) {
			parse(times[i]);
		}

	}
	public static boolean val(String time){
		int cnt = 0;
		for(SimpleDateFormat i : formats) {
			try {

				System.out.println(time + " FORMATTED: "+  i.parse(time) + " USED: "+cnt);
				return true;
			}catch(ParseException e){}
			cnt++;
		}
		return false;
	    	    
	  }
	public static void parse(String time) {
		String[] arr = time.split("[/ -.:]+");
		for(String i : arr) {
			System.out.print(i + " ");
		}
		System.out.println();
		
	}

}
