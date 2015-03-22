/**
 * 2015-2-28
 * 2015-2-28
 */
package com.quinn.xmpp.ui.contacts;

import java.util.ArrayList;

import org.jivesoftware.smack.RosterEntry;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.quinn.xmpp.R;
import com.quinn.xmpp.ui.main.MainActivity;
import com.quinn.xmpp.ui.widget.SimpleDividerItemDecoration;

/**
 * @author Quinn
 * @date 2015-2-28
 * 
 * @description A Fragment to show the contacts:person or group or tags(groups)
 */
public class ContactsFragment extends Fragment {

	@InjectView(R.id.contacts_recycle_view)
	RecyclerView mRecyclerView;
	private RecyclerView.Adapter mAdapter;
	private RecyclerView.LayoutManager mLayoutManager;
	private MainActivity mActivity;
	private ArrayList<ContactsDataItem> contactDataItems;
	//
	private int dividerHeight;
	private int dividerColor;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (MainActivity) activity;
		setRetainInstance(true);
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contactDataItems = new ArrayList<ContactsDataItem>();

		/* test data */
		for(RosterEntry rosterEntry: mActivity.smack.getAllRosterEntry()){
			System.out.println("rosterEntry name = " + rosterEntry.getName() );
			System.out.println("rosterEntry user name  = " + rosterEntry.getUser());

			contactDataItems.add(new ContactsDataItem(rosterEntry));
		}
		/* test data */
		
		dividerHeight = mActivity.getResources().getDimensionPixelSize(R.dimen.recyclerView_small_divider);
		dividerColor = mActivity.getResources().getColor(R.color.color_gray);
		mAdapter = new ContactsAdapter(mActivity,contactDataItems);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_contacts, container,
				false);
		ButterKnife.inject(this, view);
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setHasFixedSize(true);
		mLayoutManager = new LinearLayoutManager(getActivity());
		mRecyclerView.setLayoutManager(mLayoutManager);
		mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(
				mActivity.getApplicationContext(),dividerColor,dividerHeight
	        ));

		//System.out.println("all roster = " + mActivity.smack.getAllRosterEntry());

		return view;
	}

}