package com.mobileclient.activity;

import java.util.Date;
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
public class TeacherDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明教师编号控件
	private TextView TV_teacherNumber;
	// 声明登录密码控件
	private TextView TV_password;
	// 声明姓名控件
	private TextView TV_name;
	// 声明性别控件
	private TextView TV_sex;
	// 声明出生日期控件
	private TextView TV_birthday;
	// 声明教师照片图片框
	private ImageView iv_photo;
	// 声明职称控件
	private TextView TV_professName;
	// 声明联系电话控件
	private TextView TV_telephone;
	// 声明家庭地址控件
	private TextView TV_address;
	// 声明入职日期控件
	private TextView TV_inDate;
	// 声明教师简介控件
	private TextView TV_introduce;
	/* 要保存的教师信息信息 */
	Teacher teacher = new Teacher(); 
	/* 教师信息管理业务逻辑层 */
	private TeacherService teacherService = new TeacherService();
	private String teacherNumber;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.teacher_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看教师信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_teacherNumber = (TextView) findViewById(R.id.TV_teacherNumber);
		TV_password = (TextView) findViewById(R.id.TV_password);
		TV_name = (TextView) findViewById(R.id.TV_name);
		TV_sex = (TextView) findViewById(R.id.TV_sex);
		TV_birthday = (TextView) findViewById(R.id.TV_birthday);
		iv_photo = (ImageView) findViewById(R.id.iv_photo); 
		TV_professName = (TextView) findViewById(R.id.TV_professName);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_address = (TextView) findViewById(R.id.TV_address);
		TV_inDate = (TextView) findViewById(R.id.TV_inDate);
		TV_introduce = (TextView) findViewById(R.id.TV_introduce);
		Bundle extras = this.getIntent().getExtras();
		teacherNumber = extras.getString("teacherNumber");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				TeacherDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    teacher = teacherService.GetTeacher(teacherNumber); 
		this.TV_teacherNumber.setText(teacher.getTeacherNumber());
		this.TV_password.setText(teacher.getPassword());
		this.TV_name.setText(teacher.getName());
		this.TV_sex.setText(teacher.getSex());
		Date birthday = new Date(teacher.getBirthday().getTime());
		String birthdayStr = (birthday.getYear() + 1900) + "-" + (birthday.getMonth()+1) + "-" + birthday.getDate();
		this.TV_birthday.setText(birthdayStr);
		byte[] photo_data = null;
		try {
			// 获取图片数据
			photo_data = ImageService.getImage(HttpUtil.BASE_URL + teacher.getPhoto());
			Bitmap photo = BitmapFactory.decodeByteArray(photo_data, 0,photo_data.length);
			this.iv_photo.setImageBitmap(photo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_professName.setText(teacher.getProfessName());
		this.TV_telephone.setText(teacher.getTelephone());
		this.TV_address.setText(teacher.getAddress());
		Date inDate = new Date(teacher.getInDate().getTime());
		String inDateStr = (inDate.getYear() + 1900) + "-" + (inDate.getMonth()+1) + "-" + inDate.getDate();
		this.TV_inDate.setText(inDateStr);
		this.TV_introduce.setText(teacher.getIntroduce());
	} 
}
