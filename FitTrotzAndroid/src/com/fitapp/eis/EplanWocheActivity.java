package com.fitapp.eis;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/*Zeigt das Ernährungsplan für Woche ( Nur das Layout)
*/
public class EplanWocheActivity extends Activity  {

	//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_eplan_woche);

		/*Button btn_tag = (Button) findViewById(R.id.nbt_Tag);
		btn_tag.setOnClickListener(this);*/

	}

	//@Override
	//public void onClick(View v) {
	/*switch(v.getId()){
		case R.id.nbt_Tag:
		Fragment fragment = new EplanFragment();
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
			.replace(R.id.content_frame, fragment).commit();
		break;

	}*/
	//}

}
