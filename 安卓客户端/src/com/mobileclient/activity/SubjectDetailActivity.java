package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Subject;
import com.mobileclient.service.SubjectService;
import com.mobileclient.domain.SubjectType;
import com.mobileclient.service.SubjectTypeService;
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
public class SubjectDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明题目编号控件
	private TextView TV_subjectId;
	// 声明题目名称控件
	private TextView TV_subjectName;
	// 声明题目类型控件
	private TextView TV_subjectTypeObj;
	// 声明题目内容控件
	private TextView TV_content;
	// 声明限选人数控件
	private TextView TV_studentNumber;
	// 声明指导老师控件
	private TextView TV_teacherObj;
	// 声明发布时间控件
	private TextView TV_addTime;
	/* 要保存的题目信息信息 */
	Subject subject = new Subject(); 
	/* 题目信息管理业务逻辑层 */
	private SubjectService subjectService = new SubjectService();
	private SubjectTypeService subjectTypeService = new SubjectTypeService();
	private TeacherService teacherService = new TeacherService();
	private int subjectId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.subject_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看题目信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_subjectId = (TextView) findViewById(R.id.TV_subjectId);
		TV_subjectName = (TextView) findViewById(R.id.TV_subjectName);
		TV_subjectTypeObj = (TextView) findViewById(R.id.TV_subjectTypeObj);
		TV_content = (TextView) findViewById(R.id.TV_content);
		TV_studentNumber = (TextView) findViewById(R.id.TV_studentNumber);
		TV_teacherObj = (TextView) findViewById(R.id.TV_teacherObj);
		TV_addTime = (TextView) findViewById(R.id.TV_addTime);
		Bundle extras = this.getIntent().getExtras();
		subjectId = extras.getInt("subjectId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SubjectDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    subject = subjectService.GetSubject(subjectId); 
		this.TV_subjectId.setText(subject.getSubjectId() + "");
		this.TV_subjectName.setText(subject.getSubjectName());
		SubjectType subjectTypeObj = subjectTypeService.GetSubjectType(subject.getSubjectTypeObj());
		this.TV_subjectTypeObj.setText(subjectTypeObj.getSubjectTypeName());
		this.TV_content.setText(subject.getContent());
		this.TV_studentNumber.setText(subject.getStudentNumber() + "");
		Teacher teacherObj = teacherService.GetTeacher(subject.getTeacherObj());
		this.TV_teacherObj.setText(teacherObj.getName());
		this.TV_addTime.setText(subject.getAddTime());
	} 
}
