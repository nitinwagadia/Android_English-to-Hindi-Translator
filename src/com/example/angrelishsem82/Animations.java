package com.example.angrelishsem82;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


public class Animations extends Activity

{
	
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_anim);
	    
				 TextView img=(TextView)findViewById(R.id.textView1);
			    Animation an=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bottom_out);
			    img.startAnimation(an);	
		
		
		TextView im=(TextView)findViewById(R.id.textView2);
	     Animation a=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.trans);
	    im.startAnimation(a);
	   TextView tv=(TextView)findViewById(R.id.textView3);
	     Animation ae=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
	    tv.startAnimation(ae);
	    ae.setAnimationListener(new AnimationListener() {
			
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				Intent in=new Intent(Animations.this,Load.class);
		    	  in.putExtra("new1","hey");
		    	  startActivity(in);
			}
		});
	    }   
	    
	}
