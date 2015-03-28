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
import com.quinn.xmpp.smack.Smack;
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
	RecyclerView recyclerView;
	private RecyclerView.Adapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private MainActivity activity;
	private ArrayList<ContactsDataItem> contactDataItems;
	//
	private int dividerHeight;
	private int dividerColor;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = (MainActivity) activity;
		setRetainInstance(true);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contactDataItems = new ArrayList<ContactsDataItem>();

		for (RosterEntry rosterEntry : activity.smack.getAllRosterEntry()) {
			String jid = rosterEntry.getUser();
			contactDataItems.add(activity.smack.getContactData(jid));
		}

		dividerHeight = activity.getResources().getDimensionPixelSize(
				R.dimen.recyclerView_small_divider);
		dividerColor = activity.getResources().getColor(R.color.color_gray);
		adapter = new ContactsAdapter(activity, contactDataItems);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_contacts, container,
				false);
		ButterKnife.inject(this, view);
		recyclerView.setAdapter(adapter);
		recyclerView.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(getActivity());
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.addItemDecoration(new SimpleDividerItemDecoration(activity
				.getApplicationContext(), dividerColor, dividerHeight));

		return view;
	}

}