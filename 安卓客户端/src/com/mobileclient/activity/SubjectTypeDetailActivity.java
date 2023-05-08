package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.SubjectType;
import com.mobileclient.service.SubjectTypeService;
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
public class SubjectTypeDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明类型编号控件
	private TextView TV_subjectTypeId;
	// 声明类型名称控件
	private TextView TV_subjectTypeName;
	/* 要保存的题目类型信息 */
	SubjectType subjectType = new SubjectType(); 
	/* 题目类型管理业务逻辑层 */
	private SubjectTypeService subjectTypeService = new SubjectTypeService();
	private int subjectTypeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.subjecttype_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看题目类型详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_subjectTypeId = (TextView) findViewById(R.id.TV_subjectTypeId);
		TV_subjectTypeName = (TextView) findViewById(R.id.TV_subjectTypeName);
		Bundle extras = this.getIntent().getExtras();
		subjectTypeId = extras.getInt("subjectTypeId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SubjectTypeDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    subjectType = subjectTypeService.GetSubjectType(subjectTypeId); 
		this.TV_subjectTypeId.setText(subjectType.getSubjectTypeId() + "");
		this.TV_subjectTypeName.setText(subjectType.getSubjectTypeName());
	} 
}
