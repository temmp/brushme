package com.technotricks.paint.ui.common;

import java.util.ArrayList;

import com.technotricks.paint.R;
import com.technotricks.paint.adapter.ImageGridAdapter;
import com.technotricks.paint.baseactivity.BaseActivity;
import com.technotricks.paint.constants.IIntentConstants;
import com.technotricks.paint.constants.IResultConstants;
import com.technotricks.paint.manager.AlertManager;
import com.technotricks.paint.manager.AppPreferenceManager;
import com.technotricks.paint.manager.Utils;
import com.technotricks.paint.model.ImagesGridModel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class ImageListActivity extends BaseActivity implements IResultConstants,IIntentConstants{
	
	private Context context;

	private Intent i;
	
	private GridView gridImages;
	
	private ImageGridAdapter gridAdapter; ;
	
	private ArrayList<ImagesGridModel> imagesGridList;
	
	//Type..
		String ACTIVITY_TYPE="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.listimages);
		context=this;
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			ACTIVITY_TYPE = extras.getString(INTENT_IMAGE_TYPE);
		}
		
		intializeUI();
		setListners();
		setSelectionManager();
		
		setData();
		
	}

	private void intializeUI() {
		gridImages=(GridView)findViewById(R.id.gridImages);
		
		
		
		
		
	}
	private void setListners() {
		gridImages.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				
				
				if (ACTIVITY_TYPE.equals(INTENT_IMAGE_RESULT_BACK)) {
					
					i=new Intent();
					i.putExtra(RESULT_NEW_STRING, imagesGridList.get(pos).getImageName_OR_Path());
			
					setResult(RESULT_NEW_IMAGE, i);
					finish();
					
				}
				else if (ACTIVITY_TYPE.equals(INTENT_IMAGE_LIST)){
					i=new Intent(context,PaintPanalActivity.class);
					i.putExtra(RESULT_NEW_STRING, imagesGridList.get(pos).getImageName_OR_Path());
					i.putExtra(INTENT_IMAGE_TYPE, ACTIVITY_TYPE);
					startActivity(i);
					finish();
				}
				else if (ACTIVITY_TYPE.equals(INTENT_IMAGE_SAVE_LIST)) {
					
					i=new Intent(context,FullScreenActivity.class);
					i.putExtra(RESULT_NEW_STRING, imagesGridList.get(pos).getImageName_OR_Path());
					//i.putExtra(INTENT_IMAGE_TYPE, ACTIVITY_TYPE);
					
					//i.putExtra(RESULT_COVERFLOW_VALUE, imagesGridList);
					startActivity(i);
					//finish();
					
					
				}
			
				
			/*	Intent intent=new Intent(context,PaintPanalActivity.class);
				intent.putExtra(RESULT_NEW_STRING, imagesGridList.get(pos).getImageName_OR_Path());
				intent.putExtra(INTENT_IMAGE_TYPE, ACTIVITY_TYPE);
				startActivity(intent);*/
			}
		});
		
	}
	private void setSelectionManager() {
		// TODO Auto-generated method stub
		
	}
	
	private void setData() {
		
		if (ACTIVITY_TYPE.equals(INTENT_IMAGE_SAVE_LIST)) {
			imagesGridList=Utils.getSdCardFileList(context);
			
			if (imagesGridList.size()==0) {
				AlertManager.shorttoastMessage(context, "No Saved Images..");
				finish();
			}
			
			
		}
		else if ((ACTIVITY_TYPE.equals(INTENT_IMAGE_LIST)) || (ACTIVITY_TYPE.equals(INTENT_IMAGE_RESULT_BACK))) {
			
			imagesGridList=AppPreferenceManager.getImages(context);
		}
		
		
		gridAdapter= new ImageGridAdapter(context, 0, imagesGridList, 2,ACTIVITY_TYPE);
		gridImages.setNumColumns(2);
		
		gridImages.setAdapter(gridAdapter);
		
	}

}
