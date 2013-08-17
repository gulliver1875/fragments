package com.gravity.fragments;

import android.app.*;
import android.os.*;
import android.support.v4.app.*;
import android.view.*;
import android.widget.*;

import android.support.v4.app.ListFragment;

public class MenuFragment extends ListFragment
{
	OnMenuSelectedListener mCallback;

	public interface OnMenuSelectedListener{
		public void onContentSelectedListener(int position);
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We need to use a different list item layout for devices older than Honeycomb
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
			android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        // Create an array adapter for the list view, using the Ipsum headlines array
        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, Ipsum.Headlines));
    }

    @Override
    public void onStart() {
        super.onStart();

        // When in two-pane layout, set the listview to highlight the selected list item
        // (We do this during onStart because at this point the listview is available.)
        if (getFragmentManager().findFragmentById(R.id.content_fragment) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		mCallback.onContentSelectedListener(position);

        // Set the item as checked to be highlighted when in two-pane layout
        getListView().setItemChecked(position, true);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			mCallback = (OnMenuSelectedListener) activity;
		}
		catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
			+ " Must implement OnMenuSelectedListener");
		}
	}

    /** Called when the activity is first created. */
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
//	{
//        return inflater.inflate(R.layout.menu_view, container, false);
//    }

}
