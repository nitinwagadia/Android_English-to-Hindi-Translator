package com.example.angrelishsem82;

import java.util.Stack;
import java.util.Vector;

public class S {


	static String s(Vector<demo> v) throws Exception
	{
		
		int j = 0;
		String ContentOfElements = "";
		demo data[] = new demo[20];
		Stack st = new Stack();
		Stack tag = new Stack();
		System.out.println("Size of Vector is : "+v.size());
		for (int i = 0; i < v.size(); i++) 
		{	System.out.println("I AM Coming here for the time "+i+1);
			demo z = (demo) v.elementAt(i);
			String s = (String) z.content.trim();
			System.out.println("Processing sentence!!!!!!!!!!!!!!!! "+s);

			String temp[] = s.split(" ");
			System.out.println("String is : dsdaahdahdada" + s + temp.length);
			if (temp.length == 2) {
				data[j] = new demo();
				data[j].tag = z.tag;
				data[j].content = z.content;
				System.out.println("Check***" + data[j].tag + "***********"+ data[j].content);
				j++;
			} else {
				for (int m = 0; m < s.length(); m++) {
					String temps = "";
					if (s.charAt(m) == '(') {
						st.push(s.charAt(m));
						m++;

						while (!(s.charAt(m) == ' ')) {
							temps = temps + s.charAt(m);
							m++;
						}
						String kk = temps + " " + m;
						tag.push(kk);
					} // end of ( if

					if (s.charAt(m) == ')') // if outer
					{
						st.pop();
						String temp1 = tag.pop().toString();

						if (st.isEmpty()) {
							System.out.println("Elements are :" + temp1);

							int index = Integer.parseInt(temp1.substring(temp1.indexOf(" ")).trim());

							for (int k = index; k < m; k++)
							ContentOfElements = ContentOfElements+ s.charAt(k);
							temp1 = temp1.substring(0, temp1.indexOf(" ")).trim();
							System.out.println("The Contents of phrase "+ temp1 + " is : " + ContentOfElements);
							// storage
							System.out.println("Storing : " + ContentOfElements+ " at " + j);
							data[j] = new demo();
							data[j].tag = temp1;
							data[j].content = ContentOfElements;
							j++;
							ContentOfElements = "";
						}// if end
					}// enf of OUTER IF
				}// end of FOR LOOP

			} // end of else

		} // end of for
		
		
		System.out.println("this");
		for(int k=0;k<j;k++)			System.out.println("TAG is : "+data[k].tag+"|| Content is :   "+data[k].content);
		
		String retn=reordering(data,j);
//		for(int k=0;k<j;k++)			System.out.print(data[k].tag+"  ");
   return retn;
	}

	static String reordering(demo data[],int j)throws Exception
	{
	  String retn="";
		String temp1[]=data[j-1].content.trim().split(" ");
		System.out.println("length= "+temp1.length+" "+data[j-1].content );
		if(temp1.length>0)
		//	if(temp1.length)
		if(temp1.length<=6)
		{
			String pos[] = new String[4];
			String temp[]=data[0].content.trim().split(" ");
			pos[0]=temp[1].substring(0,temp[1].indexOf(")"));
			pos[1]=temp1[5].substring(0,temp1[5].indexOf(')'));
			pos[2]=temp1[2].substring(0,temp1[2].indexOf(')'));
			pos[3]=data[1].content;
			for (int i = 0; i < pos.length; i++)
				retn=retn+pos[i] + " ";
                 return retn; 			

		}else
		{

			String pos[] = new String[6];
			String temp[]=data[0].content.trim().split(" ");
			pos[0]=temp[1].substring(0,temp[1].indexOf(")"));
			pos[1]=temp1[temp1.length-1].substring(0,temp1[temp1.length-1].indexOf(')'));
			System.out.println("fsf     "+temp1[8]);
			pos[2]=temp1[5].substring(0,temp1[5].indexOf(')'));
			pos[3]=temp1[2].substring(0,temp1[2].indexOf(')'));
			pos[4]=data[1].content;
			for (int i = 0; i < pos.length; i++)
				if(pos[i]==null)
				{
					retn=retn+" ";
				}else
				{
				retn=retn+pos[i] + " ";
				}
			return retn; 
			
			
		}
		
return retn;
		
	}			
		
	
}
