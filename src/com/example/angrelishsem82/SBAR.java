package com.example.angrelishsem82;

import java.util.Stack;
import java.util.Vector;

public class SBAR
{

	static String sbarq(Vector<demo> v) throws Exception 
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
		for(int k=0;k<j;k++)			
		System.out.println("TAG is : "+data[k].tag+"|| Content is :   "+data[k].content);
		
		String retn=reordering(data, j);

  return retn;
	}

	static String reordering(demo data[], int n) throws Exception 
	
	{
		String retn="";
			if(data[0].tag.equalsIgnoreCase("WHP"))
			{
				
			if(n==3)
			{//rule 1
				System.out.println("rule1");
				String pos[] = new String[3];
				String temp[]=data[2].content.trim().split(" ");
				for(int i=0;i<temp.length;i++)
					System.out.println(temp[i]);
				if(temp.length==4)
				{
					System.out.print("True that!!!");
					pos[0]=temp[1].substring(0,temp[1].indexOf(")"))+" "+temp[3].substring(0,temp[3].indexOf(")"));
				}
				else
				{
				pos[0]=temp[1].substring(0,temp[1].indexOf(")"));
				}
				String temp1[]=data[0].content.trim().split(" ");
				pos[1]=temp1[1].substring(0,temp1[1].indexOf(")"));
				pos[2] = data[1].content;		
				System.out.println("POS is **************: "+pos[0]);
				System.out.println("POS is **************: "+pos[1]);
				System.out.println("POS is **************: "+pos[2]);
				
				
				
			
				for (int i = 0; i < pos.length; i++)
					retn=retn+pos[i] + " ";
	                 return retn; 			
			}else
			{//rule2
				System.out.println("ruel2");
				String pos[] = new String[4];
				String a=data[2].content.trim();
				String temp[]=a.split(" ");
				pos[0] = temp[1].substring(0,temp[1].length()-1);
				 a=data[0].content.trim();
				 temp=a.split(" ");
				pos[1] = temp[1].substring(0,temp[1].length()-1);
				a=data[3].content.trim();
				 temp=a.split(" ");
				pos[2] = temp[1].substring(0,temp[1].length()-1);
				pos[3] = data[1].content;
				 retn="";
				for (int i = 0; i < pos.length; i++)
					retn=retn+pos[i] + " ";
				return retn;
			}
			}//end if
			else
			{	
				if(n==4)
				{//rule3
					System.out.println("rule 3");
					String pos[] = new String[4];
					pos[0] = data[0].content;
					pos[1] = data[1].content;
					String a=data[3].content.trim();
					String temp[]=a.split(" ");
					pos[2] = temp[1].substring(0,temp[1].length()-1);
					pos[3] = data[2].content;
				    retn="";
					for (int i = 0; i < pos.length; i++)
						retn=retn+pos[i] + " ";
					return retn;			
				}else
				{//rule4
					System.out.println("rule 4");
				//	System.out.println(((data[3].content.indexOf(" "))+data[3].content.substring((data[3].content.indexOf(" ")),data[3].content.length()-1));
					String pos[] = new String[5];
					String temp[]=data[3].content.trim().split(" ");
					pos[0]=temp[1].substring(0,temp[1].indexOf(")"));
					pos[1] = data[0].content;
					pos[2] = data[1].content;
					temp=data[4].content.trim().split(" ");
					pos[3] = temp[1].substring(0,temp[1].length()-1);
				    pos[4] = data[2].content;	
				    retn="";
					for (int i = 0; i < pos.length; i++)
						retn=retn+pos[i] + " ";
					return retn;
				}
				
			}//end else
			
		
	}
}