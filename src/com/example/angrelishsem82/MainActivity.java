package com.example.angrelishsem82;



import java.io.StringReader;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.Tree;

public class MainActivity extends Activity
{
	 float x1,x2;
     float y1, y2;
     Button b1;
     String typeOfSentence,Structure;
     SQLiteDatabase db;
	Cursor c;
	boolean flag=false;
	AssetManager as;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent ins=getIntent();
		b1=(Button)findViewById(R.id.button1);
		
		
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
                    	
                    	  Intent in=new Intent(MainActivity.this,PreLoadedSentence.class);
        		    	  in.putExtra("new1","hey");
        		    	  startActivity(in);
                          
                       }
                     
                      // if right to left sweep event on screen
                      if (x1 > x2)
                      {
                    	  Intent in=new Intent(MainActivity.this,Dictionary.class);
        		    	  in.putExtra("new1","hey");
        		    	  startActivity(in);
                    	  
                    	  
                      }
                     
                     
                      break;
                  }
          }
		  
		  
		return false;
		  
		  
	  }

	

	  public void Clicker(View v) throws Exception 
	{
		TextView tv=(TextView)findViewById(R.id.textView3);
		EditText et=(EditText)findViewById(R.id.editText1);
		String st=et.getText().toString();
		String s=et.getText().toString().trim();
		AsyncTask at=new AsyncTask();
		tv.setText("");
		String x=at.execute(st).get();
		x=InitialExtraction.Extraction(Structure,typeOfSentence);
		x=LookUp(x);
		tv.setText(x);
		String etv=et.getText().toString().trim();
		
		db=openOrCreateDatabase("Dictionary",SQLiteDatabase.CREATE_IF_NECESSARY,null);
		db.execSQL("Create table if not exists history(English varchar,Hindi varchar)");
		String query1="Select * from history";
        c=null;
		c=db.rawQuery(query1, null);
		int count=c.getCount();
		
		if(count<=0)
		{
			db.execSQL("Insert into history values('"+etv.trim()+"','"+x.trim()+"')");
		}
		else
		{
	outer :	while(c.moveToNext())
		{
			if(etv.equalsIgnoreCase(c.getString(0)))
			{
				flag=true;
				break outer;
			}
		}
		
		if(flag==false)
		{
			db.execSQL("Insert into history values('"+etv.trim()+"','"+x.trim()+"')");
			flag=true;
		}
		
		}
		
}
	  private String LookUp(String x) 
	  {
		  db=openOrCreateDatabase("Dictionary",SQLiteDatabase.CREATE_IF_NECESSARY,null);
		String x1[]=x.split(" ");
		String output="";
		
		for(int i=0;i<x1.length;i++)
		{
			boolean flag=false;
			String token=x1[i].trim();
		String query1="Select * from data";
        c=null;
		c=db.rawQuery(query1, null);
		while(c.moveToNext())
		{
			String x2=c.getString(0).trim();
			if(token.equalsIgnoreCase("to") || token.equalsIgnoreCase("the") || token.equalsIgnoreCase("an") || token.equalsIgnoreCase("a"))
			{
				flag=true;
			continue;	
			}
			else
			{
			if(x2.equalsIgnoreCase(token))
			{
				output=output+" "+c.getString(1); 
				flag=true;
				break;
			}
			}
		}
		if(flag==false)
		{
			output=output+" "+token+" ";  
		}
		
		}
		
		return output;
	}
	
	  class AsyncTask extends android.os.AsyncTask<String, Void, String>
	  {

		  @Override
		protected String doInBackground(String... params) 
		{
			String x ="";
			String sent2=params[0];
			  LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
		      TokenizerFactory<CoreLabel> tokenizerFactory =PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
		      Tokenizer<CoreLabel> tok =tokenizerFactory.getTokenizer(new StringReader(sent2));
		      List<CoreLabel> rawWords2 = tok.tokenize();
		     Tree parse = lp.apply(rawWords2);
		     x=parse.toString();
		     TagsReplacement tr=new TagsReplacement();
		     x=tr.replace(x);
		     x=x.substring(x.indexOf(" ")+1,x.length()-1); //removing root
		     typeOfSentence = x.substring(1,x.indexOf(" "));
		     Structure = x.substring(x.indexOf(" ")+1,x.length()-1);
			return Structure;
		}

		

		  
		  
	  }
	  
	  
	  @Override
		public boolean onKeyDown(int keyCode, KeyEvent event) 
		{
			// TODO Auto-generated method stub
			if(keyCode==KeyEvent.KEYCODE_BACK)
			{
				AlertDialog.Builder x =new AlertDialog.Builder(this);
				   x.setTitle("Exit");
				   LinearLayout ll=new LinearLayout(this);
				   ll.setOrientation(LinearLayout.VERTICAL);
				   TextView tv=new TextView(this);
				   tv.setText("Do you Really Want to Exit?");
				   tv.setId(0);
			       ll.addView(tv);
			       
			       x.setPositiveButton("Exit",new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) 
						{
						System.exit(0);
				
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
			return super.onKeyDown(keyCode, event);
		}

	

}
