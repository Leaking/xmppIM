package com.XMPP.mainview;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.XMPP.R;
import com.XMPP.R.color;

import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.EntypoIcon;


public class ContactsFragment extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        ExpandableListView expandableListView = (ExpandableListView)view.findViewById(R.id.contactExpandableList);  
        expandableListView.setAdapter(expandAdapter);  
        return view;
	}
	
	
	
	ExpandableListAdapter expandAdapter = new BaseExpandableListAdapter(){
    	//names of the groups
        private String[] groups = new String[] { "我的好友", "陌生人", "炮友","黑名单" };  
		//names of the items
        private String[][] items = new String[][]
		{
			{ "毛爷爷", "邓爷爷", "德玛西亚", "寒冰射手" },
			{ "梅西", "罗纳尔多", "卡卡", "奥巴马" },
			{ "苍老师", "小泽玛利亚" , "啦啦啦" },
			{ "薄熙来","周永康"}
		};
      //自己定义一个获得文字信息的方法  
//        TextView getTextView() {  
//            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(  
//                    ViewGroup.LayoutParams.MATCH_PARENT, 10);  
//            TextView textView = new TextView(MainActivity.this);  
//            textView.setLayoutParams(lp);  
//            textView.setGravity(Gravity.CENTER_VERTICAL);  
//            textView.setPadding(36, 0, 0, 0);  
//            textView.setTextSize(40);  
//            textView.setTextColor(Color.BLACK);  
//            return textView;  
//        }  

          
        //重写ExpandableListAdapter中的各个方法  
        @Override  
        public int getGroupCount() {  
            return groups.length;  
        }  

        @Override  
        public Object getGroup(int groupPosition) {  
            return groups[groupPosition];  
        }  

        @Override  
        public long getGroupId(int groupPosition) {  
            return groupPosition;  
        }  

        @Override  
        public int getChildrenCount(int groupPosition) {  
            return items[groupPosition].length;  
        }  

        @Override  
        public Object getChild(int groupPosition, int childPosition) {  
            return items[groupPosition][childPosition];  
        }  

        @Override  
        public long getChildId(int groupPosition, int childPosition) {  
            return childPosition;  
        }  

        @Override  
        public boolean hasStableIds() {  
            return true;  
        }  

        @Override  
        public View getGroupView(int groupPosition, boolean isExpanded,  
                View convertView, ViewGroup parent) {  
            LinearLayout ll = (LinearLayout)View.inflate(ContactsFragment.this.getActivity(),R.layout.expand_list_title,null);           
            ImageView arrowImage= (ImageView)ll.findViewById(R.id.listTitle_arrow);
            TextView groupName = (TextView)ll.findViewById(R.id.listTitle_groupname);
            groupName.setText(getGroup(groupPosition).toString());
            IconicFontDrawable iconicFontDrawable = new IconicFontDrawable(ContactsFragment.this.getActivity());
            if(!isExpanded){
                iconicFontDrawable.setIcon(EntypoIcon.CHEVRON_THIN_RIGHT);
                iconicFontDrawable.setIconColor(com.XMPP.R.color.pocket_gold);
            }else{
            	iconicFontDrawable.setIcon(EntypoIcon.CHEVRON_THIN_DOWN);
            	iconicFontDrawable.setIconColor(com.XMPP.R.color.pocket_gold);
            }
            arrowImage.setBackground(iconicFontDrawable);            
            return ll;  
        }  

        @Override  
        public View getChildView(int groupPosition, int childPosition,  
                boolean isLastChild, View convertView, ViewGroup parent) {  
            LinearLayout ll = (LinearLayout)View.inflate(ContactsFragment.this.getActivity(),R.layout.expand_list_item,null);           
            TextView itemName = (TextView)ll.findViewById(R.id.itemName);
            itemName.setText("德玛西亚");
            
            
            return ll;  
        }  

        @Override  
        public boolean isChildSelectable(int groupPosition,int childPosition) {  
            return true;  
        }  

    };  
}
