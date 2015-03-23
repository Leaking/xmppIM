package com.quinn.xmpp.ui.drawer;

import java.util.ArrayList;

import org.jivesoftware.smackx.packet.VCard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.quinn.xmpp.R;
import com.quinn.xmpp.ui.BaseDataItem;
import com.quinn.xmpp.util.ImageUtils;

/**
 * @author Quinn
 * @date 2015-3-18
 */
public class DrawerAdapter extends BaseAdapter{

	private final static int TYPE_NUM = 2;

	private ArrayList<DrawerDataItem> dataItems;
	private Context context;
	private LayoutInflater layoutInflater;


	public DrawerAdapter(Context context, ArrayList<DrawerDataItem> dataItems){
		this.dataItems = dataItems;
		this.context = context;
		this.layoutInflater = LayoutInflater.from(context);
	}

	
	
	public static class ViewHolder {
		public ImageView functionIconOrUserPortrait;
		public TextView functionNameOrUsername;
		public ImageView userLoginState;
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		switch (getItemViewType(position)) {
		case BaseDataItem.DRAWERITEM_TYPE_HEADER:
			convertView =  getDrawerHeaderItemView(position, convertView, parent);
			break;
		case BaseDataItem.DRAWERITEM_TYPE_FUNCTION:
			convertView =  getDrawerFunctionItemView(position, convertView, parent);
			break;
		}
		return convertView;
	}


	public View getDrawerHeaderItemView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewholder = null;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.item_drawer_header,
					null);
			viewholder = new ViewHolder();
			viewholder.functionIconOrUserPortrait = (ImageView) convertView
					.findViewById(R.id.icon);
			viewholder.functionNameOrUsername = (TextView) convertView
					.findViewById(R.id.name);
			convertView.setTag(viewholder);
		} else {
			viewholder = (ViewHolder) convertView.getTag();
		}
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_chziroy);
		viewholder.functionIconOrUserPortrait.setImageBitmap(ImageUtils.toRoundBitmap(bitmap, true));
		viewholder.functionNameOrUsername.setText("Quinn");
		

		
		
		return convertView;
	}
	
	public View getDrawerFunctionItemView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewholder = null;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.item_drawer_body,
					null);
			viewholder = new ViewHolder();
			viewholder.functionIconOrUserPortrait = (ImageView) convertView
					.findViewById(R.id.icon);
			viewholder.functionNameOrUsername = (TextView) convertView
					.findViewById(R.id.name);
			convertView.setTag(viewholder);
		} else {
			viewholder = (ViewHolder) convertView.getTag();
		}

		viewholder.functionNameOrUsername.setText(dataItems.get(position).getFunctionName());	
		IconicFontDrawable iconfont = ImageUtils.buildIconFont(context,  dataItems.get(position).getFunctionIconId(), Color.BLACK);
		viewholder.functionIconOrUserPortrait.setBackgroundDrawable(iconfont);
		return convertView;
	}
	
	
	
	@Override
	public int getItemViewType(int position) {
		return dataItems.get(position).getItemType();
	}


	@Override
	public int getViewTypeCount() {
		return TYPE_NUM;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}


	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public int getCount() {
		return dataItems.size();
	}
}


