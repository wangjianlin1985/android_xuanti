package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.GuestBook;
import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;
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
public class GuestBookQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明标题输入框
	private EditText ET_qustion;
	// 声明提问学生下拉框
	private Spinner spinner_student;
	private ArrayAdapter<String> student_adapter;
	private static  String[] student_ShowText  = null;
	private List<Student> studentList = null; 
	/*学生信息管理业务逻辑层*/
	private StudentService studentService = new StudentService();
	// 声明解答老师下拉框
	private Spinner spinner_teacherObj;
	private ArrayAdapter<String> teacherObj_adapter;
	private static  String[] teacherObj_ShowText  = null;
	private List<Teacher> teacherList = null; 
	/*教师信息管理业务逻辑层*/
	private TeacherService teacherService = new TeacherService();
	/*查询过滤条件保存到这个对象中*/
	private GuestBook queryConditionGuestBook = new GuestBook();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.guestbook_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置留言交流查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_qustion = (EditText) findViewById(R.id.ET_qustion);
		spinner_student = (Spinner) findViewById(R.id.Spinner_student);
		// 获取所有的学生信息
		try {
			studentList = studentService.QueryStudent(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int studentCount = studentList.size();
		student_ShowText = new String[studentCount+1];
		student_ShowText[0] = "不限制";
		for(int i=1;i<=studentCount;i++) { 
			student_ShowText[i] = studentList.get(i-1).getName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		student_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, student_ShowText);
		// 设置提问学生下拉列表的风格
		student_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_student.setAdapter(student_adapter);
		// 添加事件Spinner事件监听
		spinner_student.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionGuestBook.setStudent(studentList.get(arg2-1).getStudentNumber()); 
				else
					queryConditionGuestBook.setStudent("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_student.setVisibility(View.VISIBLE);
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
		// 设置解答老师下拉列表的风格
		teacherObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_teacherObj.setAdapter(teacherObj_adapter);
		// 添加事件Spinner事件监听
		spinner_teacherObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionGuestBook.setTeacherObj(teacherList.get(arg2-1).getTeacherNumber()); 
				else
					queryConditionGuestBook.setTeacherObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_teacherObj.setVisibility(View.VISIBLE);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionGuestBook.setQustion(ET_qustion.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionGuestBook", queryConditionGuestBook);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
