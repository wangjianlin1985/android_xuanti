package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.GuestBook;
import com.mobileclient.service.GuestBookService;
import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;
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

public class GuestBookEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明记录idTextView
	private TextView TV_guestBookId;
	// 声明标题输入框
	private EditText ET_qustion;
	// 声明提问学生下拉框
	private Spinner spinner_student;
	private ArrayAdapter<String> student_adapter;
	private static  String[] student_ShowText  = null;
	private List<Student> studentList = null;
	/*提问学生管理业务逻辑层*/
	private StudentService studentService = new StudentService();
	// 声明提问时间输入框
	private EditText ET_questionTime;
	// 声明老师回复输入框
	private EditText ET_reply;
	// 声明解答老师下拉框
	private Spinner spinner_teacherObj;
	private ArrayAdapter<String> teacherObj_adapter;
	private static  String[] teacherObj_ShowText  = null;
	private List<Teacher> teacherList = null;
	/*解答老师管理业务逻辑层*/
	private TeacherService teacherService = new TeacherService();
	// 声明回复时间输入框
	private EditText ET_replyTime;
	// 声明回复标志输入框
	private EditText ET_replyFlag;
	protected String carmera_path;
	/*要保存的留言交流信息*/
	GuestBook guestBook = new GuestBook();
	/*留言交流管理业务逻辑层*/
	private GuestBookService guestBookService = new GuestBookService();

	private int guestBookId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.guestbook_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑留言交流信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_guestBookId = (TextView) findViewById(R.id.TV_guestBookId);
		ET_qustion = (EditText) findViewById(R.id.ET_qustion);
		spinner_student = (Spinner) findViewById(R.id.Spinner_student);
		// 获取所有的提问学生
		try {
			studentList = studentService.QueryStudent(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int studentCount = studentList.size();
		student_ShowText = new String[studentCount];
		for(int i=0;i<studentCount;i++) { 
			student_ShowText[i] = studentList.get(i).getName();
		}
		// 将可选内容与ArrayAdapter连接起来
		student_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, student_ShowText);
		// 设置图书类别下拉列表的风格
		student_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_student.setAdapter(student_adapter);
		// 添加事件Spinner事件监听
		spinner_student.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				guestBook.setStudent(studentList.get(arg2).getStudentNumber()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_student.setVisibility(View.VISIBLE);
		ET_questionTime = (EditText) findViewById(R.id.ET_questionTime);
		ET_reply = (EditText) findViewById(R.id.ET_reply);
		spinner_teacherObj = (Spinner) findViewById(R.id.Spinner_teacherObj);
		// 获取所有的解答老师
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
				guestBook.setTeacherObj(teacherList.get(arg2).getTeacherNumber()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_teacherObj.setVisibility(View.VISIBLE);
		ET_replyTime = (EditText) findViewById(R.id.ET_replyTime);
		ET_replyFlag = (EditText) findViewById(R.id.ET_replyFlag);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		guestBookId = extras.getInt("guestBookId");
		/*单击修改留言交流按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取标题*/ 
					if(ET_qustion.getText().toString().equals("")) {
						Toast.makeText(GuestBookEditActivity.this, "标题输入不能为空!", Toast.LENGTH_LONG).show();
						ET_qustion.setFocusable(true);
						ET_qustion.requestFocus();
						return;	
					}
					guestBook.setQustion(ET_qustion.getText().toString());
					/*验证获取提问时间*/ 
					if(ET_questionTime.getText().toString().equals("")) {
						Toast.makeText(GuestBookEditActivity.this, "提问时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_questionTime.setFocusable(true);
						ET_questionTime.requestFocus();
						return;	
					}
					guestBook.setQuestionTime(ET_questionTime.getText().toString());
					/*验证获取老师回复*/ 
					if(ET_reply.getText().toString().equals("")) {
						Toast.makeText(GuestBookEditActivity.this, "老师回复输入不能为空!", Toast.LENGTH_LONG).show();
						ET_reply.setFocusable(true);
						ET_reply.requestFocus();
						return;	
					}
					guestBook.setReply(ET_reply.getText().toString());
					/*验证获取回复时间*/ 
					if(ET_replyTime.getText().toString().equals("")) {
						Toast.makeText(GuestBookEditActivity.this, "回复时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_replyTime.setFocusable(true);
						ET_replyTime.requestFocus();
						return;	
					}
					guestBook.setReplyTime(ET_replyTime.getText().toString());
					/*验证获取回复标志*/ 
					if(ET_replyFlag.getText().toString().equals("")) {
						Toast.makeText(GuestBookEditActivity.this, "回复标志输入不能为空!", Toast.LENGTH_LONG).show();
						ET_replyFlag.setFocusable(true);
						ET_replyFlag.requestFocus();
						return;	
					}
					guestBook.setReplyFlag(Integer.parseInt(ET_replyFlag.getText().toString()));
					/*调用业务逻辑层上传留言交流信息*/
					GuestBookEditActivity.this.setTitle("正在更新留言交流信息，稍等...");
					String result = guestBookService.UpdateGuestBook(guestBook);
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
	    guestBook = guestBookService.GetGuestBook(guestBookId);
		this.TV_guestBookId.setText(guestBookId+"");
		this.ET_qustion.setText(guestBook.getQustion());
		for (int i = 0; i < studentList.size(); i++) {
			if (guestBook.getStudent().equals(studentList.get(i).getStudentNumber())) {
				this.spinner_student.setSelection(i);
				break;
			}
		}
		this.ET_questionTime.setText(guestBook.getQuestionTime());
		this.ET_reply.setText(guestBook.getReply());
		for (int i = 0; i < teacherList.size(); i++) {
			if (guestBook.getTeacherObj().equals(teacherList.get(i).getTeacherNumber())) {
				this.spinner_teacherObj.setSelection(i);
				break;
			}
		}
		this.ET_replyTime.setText(guestBook.getReplyTime());
		this.ET_replyFlag.setText(guestBook.getReplyFlag() + "");
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
