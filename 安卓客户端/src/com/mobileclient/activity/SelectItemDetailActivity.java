package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.SelectItem;
import com.mobileclient.service.SelectItemService;
import com.mobileclient.domain.Subject;
import com.mobileclient.service.SubjectService;
import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;
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
public class SelectItemDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明选题id控件
	private TextView TV_selectItemId;
	// 声明题目控件
	private TextView TV_subjectObj;
	// 声明学生控件
	private TextView TV_studentObj;
	// 声明选题时间控件
	private TextView TV_selectTime;
	/* 要保存的学生选题信息 */
	SelectItem selectItem = new SelectItem(); 
	/* 学生选题管理业务逻辑层 */
	private SelectItemService selectItemService = new SelectItemService();
	private SubjectService subjectService = new SubjectService();
	private StudentService studentService = new StudentService();
	private int selectItemId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.selectitem_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看学生选题详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_selectItemId = (TextView) findViewById(R.id.TV_selectItemId);
		TV_subjectObj = (TextView) findViewById(R.id.TV_subjectObj);
		TV_studentObj = (TextView) findViewById(R.id.TV_studentObj);
		TV_selectTime = (TextView) findViewById(R.id.TV_selectTime);
		Bundle extras = this.getIntent().getExtras();
		selectItemId = extras.getInt("selectItemId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SelectItemDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    selectItem = selectItemService.GetSelectItem(selectItemId); 
		this.TV_selectItemId.setText(selectItem.getSelectItemId() + "");
		Subject subjectObj = subjectService.GetSubject(selectItem.getSubjectObj());
		this.TV_subjectObj.setText(subjectObj.getSubjectName());
		Student studentObj = studentService.GetStudent(selectItem.getStudentObj());
		this.TV_studentObj.setText(studentObj.getName());
		this.TV_selectTime.setText(selectItem.getSelectTime());
	} 
}
