package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Subject;
import com.mobileclient.domain.SubjectType;
import com.mobileclient.service.SubjectTypeService;
import com.mobileclient.domain.Teacher;
import com.mobileclient.service.TeacherService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class SubjectQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明题目名称输入框
	private EditText ET_subjectName;
	// 声明题目类型下拉框
	private Spinner spinner_subjectTypeObj;
	private ArrayAdapter<String> subjectTypeObj_adapter;
	private static  String[] subjectTypeObj_ShowText  = null;
	private List<SubjectType> subjectTypeList = null; 
	/*题目类型管理业务逻辑层*/
	private SubjectTypeService subjectTypeService = new SubjectTypeService();
	// 声明指导老师下拉框
	private Spinner spinner_teacherObj;
	private ArrayAdapter<String> teacherObj_adapter;
	private static  String[] teacherObj_ShowText  = null;
	private List<Teacher> teacherList = null; 
	/*教师信息管理业务逻辑层*/
	private TeacherService teacherService = new TeacherService();
	// 声明发布时间输入框
	private EditText ET_addTime;
	/*查询过滤条件保存到这个对象中*/
	private Subject queryConditionSubject = new Subject();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.subject_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置题目信息查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_subjectName = (EditText) findViewById(R.id.ET_subjectName);
		spinner_subjectTypeObj = (Spinner) findViewById(R.id.Spinner_subjectTypeObj);
		// 获取所有的题目类型
		try {
			subjectTypeList = subjectTypeService.QuerySubjectType(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int subjectTypeCount = subjectTypeList.size();
		subjectTypeObj_ShowText = new String[subjectTypeCount+1];
		subjectTypeObj_ShowText[0] = "不限制";
		for(int i=1;i<=subjectTypeCount;i++) { 
			subjectTypeObj_ShowText[i] = subjectTypeList.get(i-1).getSubjectTypeName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		subjectTypeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, subjectTypeObj_ShowText);
		// 设置题目类型下拉列表的风格
		subjectTypeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_subjectTypeObj.setAdapter(subjectTypeObj_adapter);
		// 添加事件Spinner事件监听
		spinner_subjectTypeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionSubject.setSubjectTypeObj(subjectTypeList.get(arg2-1).getSubjectTypeId()); 
				else
					queryConditionSubject.setSubjectTypeObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_subjectTypeObj.setVisibility(View.VISIBLE);
		spinner_teacherObj = (Spinner) findViewById(R.id.Spinner_teacherObj);
		// 获取所有的教师信息
		try {
			teacherList = teacherService.QueryTeacher(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int teacherCount = teacherList.size();
		teacherObj_ShowText = new String[teacherCount+1];
		teacherObj_ShowText[0] = "不限制";
		for(int i=1;i<=teacherCount;i++) { 
			teacherObj_ShowText[i] = teacherList.get(i-1).getName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		teacherObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, teacherObj_ShowText);
		// 设置指导老师下拉列表的风格
		teacherObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_teacherObj.setAdapter(teacherObj_adapter);
		// 添加事件Spinner事件监听
		spinner_teacherObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionSubject.setTeacherObj(teacherList.get(arg2-1).getTeacherNumber()); 
				else
					queryConditionSubject.setTeacherObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_teacherObj.setVisibility(View.VISIBLE);
		ET_addTime = (EditText) findViewById(R.id.ET_addTime);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionSubject.setSubjectName(ET_subjectName.getText().toString());
					queryConditionSubject.setAddTime(ET_addTime.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionSubject", queryConditionSubject);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
