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
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PreLoadedSentence extends Activity 
{
	ListView lv;
	ArrayAdapter <String> ad;
	float x1,x2;
    float y1, y2;
    int pos;
	ArrayList<String> english=new ArrayList<String>();
	ArrayList<String> hindi=new ArrayList<String>();
	SQLiteDatabase db;
	Cursor c=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preloadedsentence);
		loadData();
		listCall();
		registerForContextMenu(lv);
	}
	
	 private void listCall() 
	 {
		 lv=(ListView)findViewById(R.id.listView1);
			ad=new ArrayAdapter<String>(this,R.layout.activity_testlistview,R.id.textViewlv,english);
			lv.setAdapter(ad);
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position,long id) 
				{
					Toast t=Toast.makeText(getApplicationContext(),hindi.get(position),Toast.LENGTH_LONG);
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
	        String query1="Select * from history";
	        c=null;
			c=db.rawQuery(query1, null);
			int count=c.getCount();
			if(count<=0)
			{
				Toast.makeText(PreLoadedSentence.this,"No History", Toast.LENGTH_SHORT).show();
			}
			else
				
			{
				int i=0;
			while(c.moveToNext())
			{
				english.add(c.getString(0));
				hindi.add(c.getString(1));
				i++;
			}
			db.close();
			c.close();
			}
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
                     if (x2 < x1) 
                     {
                   	
                   	  Intent in=new Intent(PreLoadedSentence.this,MainActivity.class);
       		    	  in.putExtra("new1","hey");
       		    	finish();
       		    	  startActivity(in);
                         
                      }
                    
                    
                    
                     break;
                 }
         }
		  
		  
		return false;
		  
		  
	  }

	
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu,View view,ContextMenuInfo menuinfo)
	{
		super.onCreateContextMenu(menu, view, menuinfo);
		menu.setHeaderTitle("Menu");
		
		menu.add(0, view.getId(),0,"Delete");
		
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		
		
		if(item.getTitle()=="Delete")
			Delete(item.getItemId());
		
	
		return false;
		
	}

	private void Delete(int id) 
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
			String txt="";
			txt=english.get(pos);
			String query="delete from history where english='"+txt+"'";
			db.execSQL(query);
			Toast.makeText(PreLoadedSentence.this,txt+" deleted",Toast.LENGTH_SHORT).show();
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
	
}
