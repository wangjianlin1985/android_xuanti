package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.GuestBook;
import com.mobileclient.service.GuestBookService;
import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;
import com.mobileclient.domain.Teacher;
import com.mobileclient.service.TeacherService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class GuestBookDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录id控件
	private TextView TV_guestBookId;
	// 声明标题控件
	private TextView TV_qustion;
	// 声明提问学生控件
	private TextView TV_student;
	// 声明提问时间控件
	private TextView TV_questionTime;
	// 声明老师回复控件
	private TextView TV_reply;
	// 声明解答老师控件
	private TextView TV_teacherObj;
	// 声明回复时间控件
	private TextView TV_replyTime;
	// 声明回复标志控件
	private TextView TV_replyFlag;
	/* 要保存的留言交流信息 */
	GuestBook guestBook = new GuestBook(); 
	/* 留言交流管理业务逻辑层 */
	private GuestBookService guestBookService = new GuestBookService();
	private StudentService studentService = new StudentService();
	private TeacherService teacherService = new TeacherService();
	private int guestBookId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.guestbook_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看留言交流详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_guestBookId = (TextView) findViewById(R.id.TV_guestBookId);
		TV_qustion = (TextView) findViewById(R.id.TV_qustion);
		TV_student = (TextView) findViewById(R.id.TV_student);
		TV_questionTime = (TextView) findViewById(R.id.TV_questionTime);
		TV_reply = (TextView) findViewById(R.id.TV_reply);
		TV_teacherObj = (TextView) findViewById(R.id.TV_teacherObj);
		TV_replyTime = (TextView) findViewById(R.id.TV_replyTime);
		TV_replyFlag = (TextView) findViewById(R.id.TV_replyFlag);
		Bundle extras = this.getIntent().getExtras();
		guestBookId = extras.getInt("guestBookId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				GuestBookDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    guestBook = guestBookService.GetGuestBook(guestBookId); 
		this.TV_guestBookId.setText(guestBook.getGuestBookId() + "");
		this.TV_qustion.setText(guestBook.getQustion());
		Student student = studentService.GetStudent(guestBook.getStudent());
		this.TV_student.setText(student.getName());
		this.TV_questionTime.setText(guestBook.getQuestionTime());
		this.TV_reply.setText(guestBook.getReply());
		Teacher teacherObj = teacherService.GetTeacher(guestBook.getTeacherObj());
		this.TV_teacherObj.setText(teacherObj.getName());
		this.TV_replyTime.setText(guestBook.getReplyTime());
		this.TV_replyFlag.setText(guestBook.getReplyFlag() + "");
	} 
}
