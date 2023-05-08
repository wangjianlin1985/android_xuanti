package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Subject;
import com.mobileclient.service.SubjectService;
import com.mobileclient.domain.SubjectType;
import com.mobileclient.service.SubjectTypeService;
import com.mobileclient.domain.Teacher;
import com.mobileclient.service.TeacherService;
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

public class SubjectEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明题目编号TextView
	private TextView TV_subjectId;
	// 声明题目名称输入框
	private EditText ET_subjectName;
	// 声明题目类型下拉框
	private Spinner spinner_subjectTypeObj;
	private ArrayAdapter<String> subjectTypeObj_adapter;
	private static  String[] subjectTypeObj_ShowText  = null;
	private List<SubjectType> subjectTypeList = null;
	/*题目类型管理业务逻辑层*/
	private SubjectTypeService subjectTypeService = new SubjectTypeService();
	// 声明题目内容输入框
	private EditText ET_content;
	// 声明限选人数输入框
	private EditText ET_studentNumber;
	// 声明指导老师下拉框
	private Spinner spinner_teacherObj;
	private ArrayAdapter<String> teacherObj_adapter;
	private static  String[] teacherObj_ShowText  = null;
	private List<Teacher> teacherList = null;
	/*指导老师管理业务逻辑层*/
	private TeacherService teacherService = new TeacherService();
	// 声明发布时间输入框
	private EditText ET_addTime;
	protected String carmera_path;
	/*要保存的题目信息信息*/
	Subject subject = new Subject();
	/*题目信息管理业务逻辑层*/
	private SubjectService subjectService = new SubjectService();

	private int subjectId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.subject_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑题目信息信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_subjectId = (TextView) findViewById(R.id.TV_subjectId);
		ET_subjectName = (EditText) findViewById(R.id.ET_subjectName);
		spinner_subjectTypeObj = (Spinner) findViewById(R.id.Spinner_subjectTypeObj);
		// 获取所有的题目类型
		try {
			subjectTypeList = subjectTypeService.QuerySubjectType(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int subjectTypeCount = subjectTypeList.size();
		subjectTypeObj_ShowText = new String[subjectTypeCount];
		for(int i=0;i<subjectTypeCount;i++) { 
			subjectTypeObj_ShowText[i] = subjectTypeList.get(i).getSubjectTypeName();
		}
		// 将可选内容与ArrayAdapter连接起来
		subjectTypeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, subjectTypeObj_ShowText);
		// 设置图书类别下拉列表的风格
		subjectTypeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_subjectTypeObj.setAdapter(subjectTypeObj_adapter);
		// 添加事件Spinner事件监听
		spinner_subjectTypeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				subject.setSubjectTypeObj(subjectTypeList.get(arg2).getSubjectTypeId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_subjectTypeObj.setVisibility(View.VISIBLE);
		ET_content = (EditText) findViewById(R.id.ET_content);
		ET_studentNumber = (EditText) findViewById(R.id.ET_studentNumber);
		spinner_teacherObj = (Spinner) findViewById(R.id.Spinner_teacherObj);
		// 获取所有的指导老师
		try {
			teacherList = teacherService.QueryTeacher(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int teacherCount = teacherList.size();
		teacherObj_ShowText = new String[teacherCount];
		for(int i=0;i<teacherCount;i++) { 
			teacherObj_ShowText[i] = teacherList.get(i).getName();
		}
		// 将可选内容与ArrayAdapter连接起来
		teacherObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, teacherObj_ShowText);
		// 设置图书类别下拉列表的风格
		teacherObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_teacherObj.setAdapter(teacherObj_adapter);
		// 添加事件Spinner事件监听
		spinner_teacherObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				subject.setTeacherObj(teacherList.get(arg2).getTeacherNumber()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_teacherObj.setVisibility(View.VISIBLE);
		ET_addTime = (EditText) findViewById(R.id.ET_addTime);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		subjectId = extras.getInt("subjectId");
		/*单击修改题目信息按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取题目名称*/ 
					if(ET_subjectName.getText().toString().equals("")) {
						Toast.makeText(SubjectEditActivity.this, "题目名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_subjectName.setFocusable(true);
						ET_subjectName.requestFocus();
						return;	
					}
					subject.setSubjectName(ET_subjectName.getText().toString());
					/*验证获取题目内容*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(SubjectEditActivity.this, "题目内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					subject.setContent(ET_content.getText().toString());
					/*验证获取限选人数*/ 
					if(ET_studentNumber.getText().toString().equals("")) {
						Toast.makeText(SubjectEditActivity.this, "限选人数输入不能为空!", Toast.LENGTH_LONG).show();
						ET_studentNumber.setFocusable(true);
						ET_studentNumber.requestFocus();
						return;	
					}
					subject.setStudentNumber(Integer.parseInt(ET_studentNumber.getText().toString()));
					/*验证获取发布时间*/ 
					if(ET_addTime.getText().toString().equals("")) {
						Toast.makeText(SubjectEditActivity.this, "发布时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_addTime.setFocusable(true);
						ET_addTime.requestFocus();
						return;	
					}
					subject.setAddTime(ET_addTime.getText().toString());
					/*调用业务逻辑层上传题目信息信息*/
					SubjectEditActivity.this.setTitle("正在更新题目信息信息，稍等...");
					String result = subjectService.UpdateSubject(subject);
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
	    subject = subjectService.GetSubject(subjectId);
		this.TV_subjectId.setText(subjectId+"");
		this.ET_subjectName.setText(subject.getSubjectName());
		for (int i = 0; i < subjectTypeList.size(); i++) {
			if (subject.getSubjectTypeObj() == subjectTypeList.get(i).getSubjectTypeId()) {
				this.spinner_subjectTypeObj.setSelection(i);
				break;
			}
		}
		this.ET_content.setText(subject.getContent());
		this.ET_studentNumber.setText(subject.getStudentNumber() + "");
		for (int i = 0; i < teacherList.size(); i++) {
			if (subject.getTeacherObj().equals(teacherList.get(i).getTeacherNumber())) {
				this.spinner_teacherObj.setSelection(i);
				break;
			}
		}
		this.ET_addTime.setText(subject.getAddTime());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
