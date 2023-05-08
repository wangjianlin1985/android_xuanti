package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
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

public class TeacherEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明教师编号TextView
	private TextView TV_teacherNumber;
	// 声明登录密码输入框
	private EditText ET_password;
	// 声明姓名输入框
	private EditText ET_name;
	// 声明性别输入框
	private EditText ET_sex;
	// 出版出生日期控件
	private DatePicker dp_birthday;
	// 声明教师照片图片框控件
	private ImageView iv_photo;
	private Button btn_photo;
	protected int REQ_CODE_SELECT_IMAGE_photo = 1;
	private int REQ_CODE_CAMERA_photo = 2;
	// 声明职称输入框
	private EditText ET_professName;
	// 声明联系电话输入框
	private EditText ET_telephone;
	// 声明家庭地址输入框
	private EditText ET_address;
	// 出版入职日期控件
	private DatePicker dp_inDate;
	// 声明教师简介输入框
	private EditText ET_introduce;
	protected String carmera_path;
	/*要保存的教师信息信息*/
	Teacher teacher = new Teacher();
	/*教师信息管理业务逻辑层*/
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
		setContentView(R.layout.teacher_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑教师信息信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_teacherNumber = (TextView) findViewById(R.id.TV_teacherNumber);
		ET_password = (EditText) findViewById(R.id.ET_password);
		ET_name = (EditText) findViewById(R.id.ET_name);
		ET_sex = (EditText) findViewById(R.id.ET_sex);
		dp_birthday = (DatePicker)this.findViewById(R.id.dp_birthday);
		iv_photo = (ImageView) findViewById(R.id.iv_photo);
		/*单击图片显示控件时进行图片的选择*/
		iv_photo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(TeacherEditActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_photo);
			}
		});
		btn_photo = (Button) findViewById(R.id.btn_photo);
		btn_photo.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_photo.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_photo);  
			}
		});
		ET_professName = (EditText) findViewById(R.id.ET_professName);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_address = (EditText) findViewById(R.id.ET_address);
		dp_inDate = (DatePicker)this.findViewById(R.id.dp_inDate);
		ET_introduce = (EditText) findViewById(R.id.ET_introduce);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		teacherNumber = extras.getString("teacherNumber");
		/*单击修改教师信息按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取登录密码*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(TeacherEditActivity.this, "登录密码输入不能为空!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					teacher.setPassword(ET_password.getText().toString());
					/*验证获取姓名*/ 
					if(ET_name.getText().toString().equals("")) {
						Toast.makeText(TeacherEditActivity.this, "姓名输入不能为空!", Toast.LENGTH_LONG).show();
						ET_name.setFocusable(true);
						ET_name.requestFocus();
						return;	
					}
					teacher.setName(ET_name.getText().toString());
					/*验证获取性别*/ 
					if(ET_sex.getText().toString().equals("")) {
						Toast.makeText(TeacherEditActivity.this, "性别输入不能为空!", Toast.LENGTH_LONG).show();
						ET_sex.setFocusable(true);
						ET_sex.requestFocus();
						return;	
					}
					teacher.setSex(ET_sex.getText().toString());
					/*获取出版日期*/
					Date birthday = new Date(dp_birthday.getYear()-1900,dp_birthday.getMonth(),dp_birthday.getDayOfMonth());
					teacher.setBirthday(new Timestamp(birthday.getTime()));
					if (!teacher.getPhoto().startsWith("upload/")) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						TeacherEditActivity.this.setTitle("正在上传图片，稍等...");
						String photo = HttpUtil.uploadFile(teacher.getPhoto());
						TeacherEditActivity.this.setTitle("图片上传完毕！");
						teacher.setPhoto(photo);
					} 
					/*验证获取职称*/ 
					if(ET_professName.getText().toString().equals("")) {
						Toast.makeText(TeacherEditActivity.this, "职称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_professName.setFocusable(true);
						ET_professName.requestFocus();
						return;	
					}
					teacher.setProfessName(ET_professName.getText().toString());
					/*验证获取联系电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(TeacherEditActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					teacher.setTelephone(ET_telephone.getText().toString());
					/*验证获取家庭地址*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(TeacherEditActivity.this, "家庭地址输入不能为空!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					teacher.setAddress(ET_address.getText().toString());
					/*获取出版日期*/
					Date inDate = new Date(dp_inDate.getYear()-1900,dp_inDate.getMonth(),dp_inDate.getDayOfMonth());
					teacher.setInDate(new Timestamp(inDate.getTime()));
					/*验证获取教师简介*/ 
					if(ET_introduce.getText().toString().equals("")) {
						Toast.makeText(TeacherEditActivity.this, "教师简介输入不能为空!", Toast.LENGTH_LONG).show();
						ET_introduce.setFocusable(true);
						ET_introduce.requestFocus();
						return;	
					}
					teacher.setIntroduce(ET_introduce.getText().toString());
					/*调用业务逻辑层上传教师信息信息*/
					TeacherEditActivity.this.setTitle("正在更新教师信息信息，稍等...");
					String result = teacherService.UpdateTeacher(teacher);
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
	    teacher = teacherService.GetTeacher(teacherNumber);
		this.TV_teacherNumber.setText(teacherNumber);
		this.ET_password.setText(teacher.getPassword());
		this.ET_name.setText(teacher.getName());
		this.ET_sex.setText(teacher.getSex());
		Date birthday = new Date(teacher.getBirthday().getTime());
		this.dp_birthday.init(birthday.getYear() + 1900,birthday.getMonth(), birthday.getDate(), null);
		byte[] photo_data = null;
		try {
			// 获取图片数据
			photo_data = ImageService.getImage(HttpUtil.BASE_URL + teacher.getPhoto());
			Bitmap photo = BitmapFactory.decodeByteArray(photo_data, 0, photo_data.length);
			this.iv_photo.setImageBitmap(photo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.ET_professName.setText(teacher.getProfessName());
		this.ET_telephone.setText(teacher.getTelephone());
		this.ET_address.setText(teacher.getAddress());
		Date inDate = new Date(teacher.getInDate().getTime());
		this.dp_inDate.init(inDate.getYear() + 1900,inDate.getMonth(), inDate.getDate(), null);
		this.ET_introduce.setText(teacher.getIntroduce());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_CAMERA_photo  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_photo.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_photo.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_photo.setImageBitmap(booImageBm);
				this.iv_photo.setScaleType(ScaleType.FIT_CENTER);
				this.teacher.setPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_photo && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(filepath, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
			opts.inJustDecodeBounds = false; 
			try { 
				Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
				this.iv_photo.setImageBitmap(bm); 
				this.iv_photo.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			teacher.setPhoto(filename); 
		}
	}
}
