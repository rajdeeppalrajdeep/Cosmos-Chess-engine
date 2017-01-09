import java.util.Scanner;


public class test 
{

	public static void main(String[] args) 
	{
		//INSERT INTO student (Student_name,Student_email,Student_password,Student_branch,Student_university,Student_street,Student_city,Student_country,Student_zip,Student_phone) VALUES ('$name','$email','$pass','$branch','$university','$street','$city','$country','$zip','$phone');
	/*
	    String name = "student";
	    String email = "@gmail.com";
	    String password = "password";
	    String[] street = {"Sahakarnagar","jayanagar","Mg road"};
	    String[] phone = {"9855","8754","7545"};
	    for(int i = 1; i<10 ; i++ )
	    {
	    	int branch = (int)(Math.random()*10);
	    	int university = 1;
	    	String str = street[(int)(Math.random()*3)];
	    	int zip = 10+(int)(Math.random()*90);
	    	int phone1 = (int)(Math.random()*2);
	    	int phone2 =(int)(Math.random()*2);
	    	System.out.println("INSERT INTO student (Student_name,Student_email,Student_password,Student_branch,Student_university,Student_street,Student_city,Student_country,Student_zip,Student_phone) VALUES ('"+name+i+"','"+name+i+email+"','password','"+branch+"','"+university+"',"+str+",'Bangalore','India','5600"+zip+"','"+phone[phone1]+phone[phone2]+zip+"');");
	    	
	    }*/
		String input = "a8a7";
		int from=(input.charAt(0)-'a')+(8*('8'-input.charAt(1)));
		int to=(input.charAt(2)-'a')+(8*('8'-input.charAt(3)));
		 System.out.println(from);
		 System.out.println(to);
	}
}
