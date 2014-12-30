package com.example.angrelishsem82;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Dictionary extends Activity 
{	
	
	SQLiteDatabase db;
	float x1,x2;
    float y1, y2;
	Cursor c;
	ListView lv;
	int pos;
	ArrayAdapter<String> ad;
	Toast t=null;
	ArrayList<String> english=new ArrayList<String>();
	ArrayList<String> hindi=new ArrayList<String>();
@Override
protected void onCreate(Bundle savedInstanceState) 
{
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_dictionary);
	Intent in=getIntent();
	loadData();
	Listcall();
	registerForContextMenu(lv);
}
private void Listcall() 
{
	lv=(ListView)findViewById(R.id.dictionarylistView1);
	ad=new ArrayAdapter<String>(Dictionary.this,R.layout.activity_testlistview,R.id.textViewlv,english);
	lv.setAdapter(ad);
	
	lv.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int pos1,
				long arg3) 
		{
			t=Toast.makeText(Dictionary.this,"Meaning of  "+english.get(pos1)+" is "+hindi.get(pos1).toString(),Toast.LENGTH_LONG);
			t.show();	
			
		}
	});
	
	
	lv.setOnItemLongClickListener(new OnItemLongClickListener() {

		
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int position, long id) 
		{
			// TODO Auto-generated method stub
			pos=position;
			return false;
		}
	});
	
}
private void loadData() 
{
	
	
			db=openOrCreateDatabase("Dictionary",SQLiteDatabase.CREATE_IF_NECESSARY,null);
			String query1="Select * from data order by english";
	        c=db.rawQuery(query1, null);
	        
				while(c.moveToNext())
				{
						
					english.add(c.getString(0));
					hindi.add(c.getString(1));
				}
				db.close();
				c.close();
	
}
	public void Add(View v) 
{
	// Forming DialogBox for Addition
	 AlertDialog.Builder x =new AlertDialog.Builder(this);
	   x.setTitle("Add");
	   LinearLayout ll=new LinearLayout(this);
	   ll.setOrientation(LinearLayout.VERTICAL);
	   TextView tv=new TextView(this);
	   tv.setText("Enter English word");
       ll.addView(tv);
       final EditText et=new EditText(this);
       ll.addView(et);
       final TextView tv1=new TextView(this);
       tv1.setText("Enter Hindi Translation");
      ll.addView(tv1);
       final EditText et1=new EditText(this);
       ll.addView(et1);
       x.setView(ll); //Setting Linear Layout to the Dialog Box
     
		
	       
      x.setPositiveButton("OK",new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) 
		{
			
			db=openOrCreateDatabase("Dictionary",SQLiteDatabase.CREATE_IF_NECESSARY,null);
			String eng=et.getText().toString().trim();
			Log.i("ENglish WOrd is ",eng);
			String hin=et1.getText().toString().trim();
			eng=eng.substring(0,1).toUpperCase()+eng.substring(1);
			hin=hin.substring(0,1).toUpperCase()+hin.substring(1);
			if(eng.equals("") || hin.equals(""))
		       {
		    	   Toast.makeText(Dictionary.this, "Please enter data in all fields",Toast.LENGTH_SHORT).show();
		       //return;
		       }
			else
		       {
			String query="Insert into data values('"+eng+"','"+hin+"')";
			db.execSQL(query);
			Toast.makeText(Dictionary.this, eng+ " Added",Toast.LENGTH_LONG).show();
			db.close();
			finish();
			startActivity(getIntent());
		       }
		}
	});
	       
      x.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			return;
			
		}
	});
       x.show();

	
	
}

	
	@Override
	public void onCreateContextMenu(ContextMenu menu,View view,ContextMenuInfo menuinfo)
	{
		super.onCreateContextMenu(menu, view, menuinfo);
		menu.setHeaderTitle("Menu");
		
		menu.add(0, view.getId(),0,"Edit");
		menu.add(0, view.getId(),0,"Delete");
		
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		if(item.getTitle()=="Edit")
			Edit(item.getItemId());
		
		else if(item.getTitle()=="Delete")
			Delete(item.getItemId());
		
	
		return false;
		
		
	}
	
	
	public void Delete(int id)

	{
	
		String query="";
		 AlertDialog.Builder x =new AlertDialog.Builder(this);
		   x.setTitle("Delete");
		   LinearLayout ll=new LinearLayout(this);
		   ll.setOrientation(LinearLayout.VERTICAL);
		   TextView tv=new TextView(this);
		   tv.setText("Do you really want to Delete "+english.get(pos).toString()+" ?");
		   ll.addView(tv);
	       x.setView(ll);
	      
	       x.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
			db=openOrCreateDatabase("Dictionary",SQLiteDatabase.CREATE_IF_NECESSARY,null);
			Log.i("My position is", pos+"");
			String txt="";
			txt=english.get(pos);
			Log.i("My name is :", txt);
			String query="delete from data where english='"+txt+"'";
			db.execSQL(query);
			Toast.makeText(Dictionary.this,txt+" deleted",Toast.LENGTH_SHORT).show();
			db.close();
			finish();
			startActivity(getIntent());
			}
			
			
		});
	       
	       x.setNegativeButton("Cancel",new DialogInterface.OnClickListener() 
	       {
	   		
	   		@Override
	   		public void onClick(DialogInterface dialog, int which) {
	   			return;
	   			
	   		}
	   	});
	       
		x.show();
	}
	
	
	public void Edit(int id)
	{
		
		//*-********************************
		final String eng=english.get(pos).toString();
		AlertDialog.Builder x =new AlertDialog.Builder(this);
		   x.setTitle("Edit");
		   LinearLayout ll=new LinearLayout(this);
		   ll.setOrientation(LinearLayout.VERTICAL);
		   TextView tv=new TextView(this);
		   tv.setText("English Word");
	       ll.addView(tv);
	       TextView tv1=new TextView(this);
		   tv1.setText(eng);
	       final TextView tv2=new TextView(this);
	       tv2.setText("Enter new Hindi Translation");
	       tv2.setId(1);
	      ll.addView(tv1);
	       final EditText et1=new EditText(this);
	       et1.setId(1);
	       ll.addView(et1);
	      x.setView(ll);
	      
	      x.setPositiveButton("OK",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{	
				String hin="";
				hin=et1.getText().toString().trim();
				if(hin.equals(""))
				{
					Toast.makeText(Dictionary.this,"Field Cannot be Blank",Toast.LENGTH_SHORT).show();
				}
				else
				{
				db=openOrCreateDatabase("Dictionary",SQLiteDatabase.CREATE_IF_NECESSARY,null);
				String query="Update data set hindi= '"+hin+"' where english= '"+eng+"'";
				db.execSQL(query);
				Toast.makeText(Dictionary.this, eng+" changed from "+hindi.get(pos)+ " to "+ hin,Toast.LENGTH_LONG).show();
				db.close();
				finish();
				startActivity(getIntent());
				}
			}
		});
	      
	      x.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				return;
				
			}
		});
	       x.show();
	 	
	}

	
	public boolean onTouchEvent(MotionEvent touchevent) 
	  {
		  switch (touchevent.getAction())
        {
               // when user first touches the screen we get x and y coordinate
                case MotionEvent.ACTION_DOWN: 
                {
                    x1 = touchevent.getX();
                    y1 = touchevent.getY();
                    break;
               }
                case MotionEvent.ACTION_UP: 
                {
                    x2 = touchevent.getX();
                    y2 = touchevent.getY(); 

                     //if left to right sweep event on screen
                    if (x1 < x2) 
                    {
                  	
                  	  Intent in=new Intent(Dictionary.this,MainActivity.class);
      		    	  in.putExtra("new1","hey");
      		    	finish();
      		    	  startActivity(in);
                        
                     }
                             
                   
                    break;
                }
        }
		  
		  
		return false;
		  
		  
	  }

	
}
