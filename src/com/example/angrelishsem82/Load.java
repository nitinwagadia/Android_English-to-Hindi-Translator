package com.example.angrelishsem82;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;



import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

public class Load extends Activity 
{

	ProgressBar pb1;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loadasync);
		Intent ins=getIntent();
		pb1=(ProgressBar)findViewById(R.id.progressBar1);
		AsyncTest at=new AsyncTest();
		at.execute();
	
	}
	
	class AsyncTest extends AsyncTask<Void, Void, String[]>
	{	
		String d[][];
		String lists[],a[]=new String[1000];
		InputStream is=null;
		SQLiteDatabase db;
		Cursor c;
		AssetManager as;
		String s="";
		
		int i=0,count=0,j=0;
		@Override
		protected String[] doInBackground(Void... params) 
		{
			
		
			//Load.this.deleteDatabase("Acronym");
			db=openOrCreateDatabase("Dictionary",SQLiteDatabase.CREATE_IF_NECESSARY,null);
			db.execSQL("Create table if not exists data(English varchar,Hindi varchar)");
	        String query1="Select * from data";
	        c=null;
			c=db.rawQuery(query1, null);
			count=c.getCount();
			Log.i("Count is:",count+"");
			if(count<=0)
			{
				as=getAssets();
				
				try {
					lists=as.list("");
					is=as.open("dictionary.txt");
					BufferedReader br=new BufferedReader(new InputStreamReader(is),8192);
					s=br.readLine();
				 while(s!=null)
				{
					a[i]=s.substring(0,1).toUpperCase()+s.substring(1);
					s=br.readLine();
					i++;
				}
				 br.close();
				 
				 Log.i("Eveything fine", "uptill here");
				 for(int j=0;j<i;j++)
				{
					 
					 String temp[]=a[j].split(" ");
					db.execSQL("Insert into data values('"+temp[0].trim()+"','"+temp[1]+"')");
					
				}
				br.close();
				
				
			}
				 
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//c.close();
				//db.close();
			} //end of if
			else
			{	Log.i("I have Data", "Pushing to other Activity");
				i=0;
				while(c.moveToNext())
				{
					a[i]=c.getString(0);
					i++;
							
				}
				c.close();
				db.close();
			}
	        
			return a;
					}
		
		 protected void onPostExecute(String[] params) 
		 { 
			Log.i("I am Here For PAssing Intent","I am doing my work");
			 Intent in=new Intent(Load.this,MainActivity.class);
				in.putExtra("Data",params);
				startActivity(in);
				Log.i("DOne!!!","My work is done!!!");
				finish();
			 return ;
		 }
				
	
	
}
		
}