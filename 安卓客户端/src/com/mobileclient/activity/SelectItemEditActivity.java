package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.SelectItem;
import com.mobileclient.service.SelectItemService;
import com.mobileclient.domain.Subject;
import com.mobileclient.service.SubjectService;
import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;
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

public class SelectItemEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明选题idTextView
	private TextView TV_selectItemId;
	// 声明题目下拉框
	private Spinner spinner_subjectObj;
	private ArrayAdapter<String> subjectObj_adapter;
	private static  String[] subjectObj_ShowText  = null;
	private List<Subject> subjectList = null;
	/*题目管理业务逻辑层*/
	private SubjectService subjectService = new SubjectService();
	// 声明学生下拉框
	private Spinner spinner_studentObj;
	private ArrayAdapter<String> studentObj_adapter;
	private static  String[] studentObj_ShowText  = null;
	private List<Student> studentList = null;
	/*学生管理业务逻辑层*/
	private StudentService studentService = new StudentService();
	// 声明选题时间输入框
	private EditText ET_selectTime;
	protected String carmera_path;
	/*要保存的学生选题信息*/
	SelectItem selectItem = new SelectItem();
	/*学生选题管理业务逻辑层*/
	private SelectItemService selectItemService = new SelectItemService();

	private int selectItemId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.selectitem_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑学生选题信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_selectItemId = (TextView) findViewById(R.id.TV_selectItemId);
		spinner_subjectObj = (Spinner) findViewById(R.id.Spinner_subjectObj);
		// 获取所有的题目
		try {
			subjectList = subjectService.QuerySubject(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int subjectCount = subjectList.size();
		subjectObj_ShowText = new String[subjectCount];
		for(int i=0;i<subjectCount;i++) { 
			subjectObj_ShowText[i] = subjectList.get(i).getSubjectName();
		}
		// 将可选内容与ArrayAdapter连接起来
		subjectObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, subjectObj_ShowText);
		// 设置图书类别下拉列表的风格
		subjectObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_subjectObj.setAdapter(subjectObj_adapter);
		// 添加事件Spinner事件监听
		spinner_subjectObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				selectItem.setSubjectObj(subjectList.get(arg2).getSubjectId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_subjectObj.setVisibility(View.VISIBLE);
		spinner_studentObj = (Spinner) findViewById(R.id.Spinner_studentObj);
		// 获取所有的学生
		try {
			studentList = studentService.QueryStudent(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int studentCount = studentList.size();
		studentObj_ShowText = new String[studentCount];
		for(int i=0;i<studentCount;i++) { 
			studentObj_ShowText[i] = studentList.get(i).getName();
		}
		// 将可选内容与ArrayAdapter连接起来
		studentObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, studentObj_ShowText);
		// 设置图书类别下拉列表的风格
		studentObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_studentObj.setAdapter(studentObj_adapter);
		// 添加事件Spinner事件监听
		spinner_studentObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				selectItem.setStudentObj(studentList.get(arg2).getStudentNumber()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_studentObj.setVisibility(View.VISIBLE);
		ET_selectTime = (EditText) findViewById(R.id.ET_selectTime);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		selectItemId = extras.getInt("selectItemId");
		/*单击修改学生选题按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取选题时间*/ 
					if(ET_selectTime.getText().toString().equals("")) {
						Toast.makeText(SelectItemEditActivity.this, "选题时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_selectTime.setFocusable(true);
						ET_selectTime.requestFocus();
						return;	
					}
					selectItem.setSelectTime(ET_selectTime.getText().toString());
					/*调用业务逻辑层上传学生选题信息*/
					SelectItemEditActivity.this.setTitle("正在更新学生选题信息，稍等...");
					String result = selectItemService.UpdateSelectItem(selectItem);
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
	    selectItem = selectItemService.GetSelectItem(selectItemId);
		this.TV_selectItemId.setText(selectItemId+"");
		for (int i = 0; i < subjectList.size(); i++) {
			if (selectItem.getSubjectObj() == subjectList.get(i).getSubjectId()) {
				this.spinner_subjectObj.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < studentList.size(); i++) {
			if (selectItem.getStudentObj().equals(studentList.get(i).getStudentNumber())) {
				this.spinner_studentObj.setSelection(i);
				break;
			}
		}
		this.ET_selectTime.setText(selectItem.getSelectTime());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
