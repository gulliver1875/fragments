package com.gravity.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import android.util.Log;

public class MainActivity extends FragmentActivity
	implements MenuFragment.OnMenuSelectedListener
{

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
    	Log.d("MYTAG", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		if(findViewById(R.id.fragment_container) != null) {

			if(savedInstanceState != null) {
				return;
			}

			MenuFragment firstFragment = new MenuFragment();
			firstFragment.setArguments(getIntent().getExtras());

			getSupportFragmentManager().beginTransaction()
				.add(R.id.fragment_container, firstFragment).commit();

		}
    }
	
	public void onContentSelected(int position) {
		ContentFragment contentFragment = (ContentFragment)
			getSupportFragmentManager().findFragmentById(R.id.content_fragment);

		if (contentFragment != null){
			// 2 pane layout
			contentFragment.updateContentView(position);
		}
		else {
			// Single pane layout
			
			// Create fragment and give it an argument for the selected content
			ContentFragment newFragment = new ContentFragment();
			Bundle args = new Bundle();
			args.putInt(ContentFragment.ARG_POSITION, position);
			newFragment.setArguments(args);
			
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			
			// Replace what's in fragment_container with new fragment
			transaction.replace(R.id.fragment_container, newFragment);
			
			// Add to back stack
			transaction.addToBackStack(null);
			
			transaction.commit();
		}
	}
	
}
