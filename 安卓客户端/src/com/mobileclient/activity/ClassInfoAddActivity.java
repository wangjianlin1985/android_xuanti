package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.ClassInfo;
import com.mobileclient.service.ClassInfoService;
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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class ClassInfoAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明班级编号输入框
	private EditText ET_classNumber;
	// 声明所在专业输入框
	private EditText ET_specialName;
	// 声明班级名称输入框
	private EditText ET_className;
	// 出版成立日期控件
	private DatePicker dp_startDate;
	// 声明班主任输入框
	private EditText ET_headTeacher;
	protected String carmera_path;
	/*要保存的班级信息信息*/
	ClassInfo classInfo = new ClassInfo();
	/*班级信息管理业务逻辑层*/
	private ClassInfoService classInfoService = new ClassInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.classinfo_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加班级信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_classNumber = (EditText) findViewById(R.id.ET_classNumber);
		ET_specialName = (EditText) findViewById(R.id.ET_specialName);
		ET_className = (EditText) findViewById(R.id.ET_className);
		dp_startDate = (DatePicker)this.findViewById(R.id.dp_startDate);
		ET_headTeacher = (EditText) findViewById(R.id.ET_headTeacher);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加班级信息按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取班级编号*/
					if(ET_classNumber.getText().toString().equals("")) {
						Toast.makeText(ClassInfoAddActivity.this, "班级编号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_classNumber.setFocusable(true);
						ET_classNumber.requestFocus();
						return;
					}
					classInfo.setClassNumber(ET_classNumber.getText().toString());
					/*验证获取所在专业*/ 
					if(ET_specialName.getText().toString().equals("")) {
						Toast.makeText(ClassInfoAddActivity.this, "所在专业输入不能为空!", Toast.LENGTH_LONG).show();
						ET_specialName.setFocusable(true);
						ET_specialName.requestFocus();
						return;	
					}
					classInfo.setSpecialName(ET_specialName.getText().toString());
					/*验证获取班级名称*/ 
					if(ET_className.getText().toString().equals("")) {
						Toast.makeText(ClassInfoAddActivity.this, "班级名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_className.setFocusable(true);
						ET_className.requestFocus();
						return;	
					}
					classInfo.setClassName(ET_className.getText().toString());
					/*获取成立日期*/
					Date startDate = new Date(dp_startDate.getYear()-1900,dp_startDate.getMonth(),dp_startDate.getDayOfMonth());
					classInfo.setStartDate(new Timestamp(startDate.getTime()));
					/*验证获取班主任*/ 
					if(ET_headTeacher.getText().toString().equals("")) {
						Toast.makeText(ClassInfoAddActivity.this, "班主任输入不能为空!", Toast.LENGTH_LONG).show();
						ET_headTeacher.setFocusable(true);
						ET_headTeacher.requestFocus();
						return;	
					}
					classInfo.setHeadTeacher(ET_headTeacher.getText().toString());
					/*调用业务逻辑层上传班级信息信息*/
					ClassInfoAddActivity.this.setTitle("正在上传班级信息信息，稍等...");
					String result = classInfoService.AddClassInfo(classInfo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
