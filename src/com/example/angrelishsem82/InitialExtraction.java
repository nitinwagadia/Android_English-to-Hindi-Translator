package com.example.angrelishsem82;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.Vector;

import android.util.Log;

class demo
{
String tag="",content="";
}


public class InitialExtraction 
{
	static String trans="";
	static demo d;
	static Vector<demo> v;
    
	static String Extraction(String Structure, String TOS) throws  Exception
	{	
	String typeOfSentence=TOS;
	String ContentOfElements="";
	Stack st=new Stack();
	Stack tag=new Stack();
	v=new Vector<demo>();
	for(int i=0;i<Structure.length();i++)
	{
		String temp="";
	if(Structure.charAt(i)=='(')
	{
	st.push(Structure.charAt(i));
	i++;

	while(!(Structure.charAt(i)==' '))
	{
		temp= temp+Structure.charAt(i);
		i++;
	}
	String kk=temp+" "+i;
	tag.push(kk);
	} //end of ( if

	if(Structure.charAt(i)==')') //if outer
	{
		st.pop();
		String temp1=tag.pop().toString();
		
	if(st.isEmpty())
	{
		  d=new demo();
		int index=Integer.parseInt(temp1.substring(temp1.indexOf(" ")).trim());
	for(int j=index;j<i;j++)
	ContentOfElements=ContentOfElements+Structure.charAt(j);
	temp1=temp1.substring(0,temp1.indexOf(" ")).trim();
	
	//storage

	d.tag=temp1;
	d.content=ContentOfElements;
	v.add(d);
	ContentOfElements="";
	}//if end
	}// enf of OUTER IF
	}// end of FOR LOOP
	
	
		if(typeOfSentence.equalsIgnoreCase("SBARQ"))
	{
	
		trans=SBAR.sbarq(v);
		v=null;
		d=null;
	
	}
		if(typeOfSentence.equalsIgnoreCase("S"))
		{
			trans=S.s(v);
			v=null;
			d=null;
			
		}
	



return trans;
	
	}//end extraction

}
