/**
 * 2015-2-28
 * 2015-2-28
 */
package com.quinn.xmpp.ui.contacts;

import java.util.ArrayList;

import android.app.Activity;
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
	private ActionBarActivity mActivity;
	private ArrayList<ContactsDataItem> contactDataItems;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contactDataItems = new ArrayList<ContactsDataItem>();

		/* test data */
		for (int i = 0; i < 20; i++) {
			contactDataItems.add(new ContactsDataItem(null, "hahahah"));
			contactDataItems.add(new ContactsDataItem(null, "ttttt"));
		}
		/* test data */

		mAdapter = new ContactsAdapter(contactDataItems);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (ActionBarActivity) activity;
		setRetainInstance(true);
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
		return view;
	}

}