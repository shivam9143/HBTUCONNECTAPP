/*package info.androidhive.slidingmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
 
public class ListViewAct extends ArrayAdapter<String> {
 
	private final Context context;
	private final String[] values1;
	private final String[] values2;
	
	public ListViewAct(Context context, String[] object1, String[] object2) {
		super(context, R.layout.activity_main, object2);
		this.context = context;
		this.values1 = object1;
		this.values2 = object2;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 
		View rowView = inflater.inflate(R.layout.activity_main, parent, false);
		
		TextView textView1 = (TextView) rowView.findViewById(R.id.list1);
		TextView textView2 = (TextView) rowView.findViewById(R.id.list2);
		textView1.setText(values1[position]);
		textView2.setText(values2[position]);
	 	 	 
		return rowView;
	}
}
*/