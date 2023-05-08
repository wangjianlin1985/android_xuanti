package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.SubjectTypeService;
import com.mobileclient.service.TeacherService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class SubjectSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;

    public SubjectSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.subject_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_subjectId = (TextView)convertView.findViewById(R.id.tv_subjectId);
	  holder.tv_subjectName = (TextView)convertView.findViewById(R.id.tv_subjectName);
	  holder.tv_subjectTypeObj = (TextView)convertView.findViewById(R.id.tv_subjectTypeObj);
	  holder.tv_content = (TextView)convertView.findViewById(R.id.tv_content);
	  holder.tv_studentNumber = (TextView)convertView.findViewById(R.id.tv_studentNumber);
	  holder.tv_teacherObj = (TextView)convertView.findViewById(R.id.tv_teacherObj);
	  holder.tv_addTime = (TextView)convertView.findViewById(R.id.tv_addTime);
	  /*设置各个控件的展示内容*/
	  holder.tv_subjectId.setText("题目编号：" + mData.get(position).get("subjectId").toString());
	  holder.tv_subjectName.setText("题目名称：" + mData.get(position).get("subjectName").toString());
	  holder.tv_subjectTypeObj.setText("题目类型：" + (new SubjectTypeService()).GetSubjectType(Integer.parseInt(mData.get(position).get("subjectTypeObj").toString())).getSubjectTypeName());
	  holder.tv_content.setText("题目内容：" + mData.get(position).get("content").toString());
	  holder.tv_studentNumber.setText("限选人数：" + mData.get(position).get("studentNumber").toString());
	  holder.tv_teacherObj.setText("指导老师：" + (new TeacherService()).GetTeacher(mData.get(position).get("teacherObj").toString()).getName());
	  holder.tv_addTime.setText("发布时间：" + mData.get(position).get("addTime").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_subjectId;
    	TextView tv_subjectName;
    	TextView tv_subjectTypeObj;
    	TextView tv_content;
    	TextView tv_studentNumber;
    	TextView tv_teacherObj;
    	TextView tv_addTime;
    }
} 
