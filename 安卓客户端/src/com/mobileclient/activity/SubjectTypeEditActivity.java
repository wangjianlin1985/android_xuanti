package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.SubjectType;
import com.mobileclient.service.SubjectTypeService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class SubjectTypeEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明类型编号TextView
	private TextView TV_subjectTypeId;
	// 声明类型名称输入框
	private EditText ET_subjectTypeName;
	protected String carmera_path;
	/*要保存的题目类型信息*/
	SubjectType subjectType = new SubjectType();
	/*题目类型管理业务逻辑层*/
	private SubjectTypeService subjectTypeService = new SubjectTypeService();

	private int subjectTypeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.subjecttype_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑题目类型信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_subjectTypeId = (TextView) findViewById(R.id.TV_subjectTypeId);
		ET_subjectTypeName = (EditText) findViewById(R.id.ET_subjectTypeName);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		subjectTypeId = extras.getInt("subjectTypeId");
		/*单击修改题目类型按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取类型名称*/ 
					if(ET_subjectTypeName.getText().toString().equals("")) {
						Toast.makeText(SubjectTypeEditActivity.this, "类型名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_subjectTypeName.setFocusable(true);
						ET_subjectTypeName.requestFocus();
						return;	
					}
					subjectType.setSubjectTypeName(ET_subjectTypeName.getText().toString());
					/*调用业务逻辑层上传题目类型信息*/
					SubjectTypeEditActivity.this.setTitle("正在更新题目类型信息，稍等...");
					String result = subjectTypeService.UpdateSubjectType(subjectType);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    subjectType = subjectTypeService.GetSubjectType(subjectTypeId);
		this.TV_subjectTypeId.setText(subjectTypeId+"");
		this.ET_subjectTypeName.setText(subjectType.getSubjectTypeName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
