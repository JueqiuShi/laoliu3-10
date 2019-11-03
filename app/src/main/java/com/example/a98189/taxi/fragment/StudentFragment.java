package com.example.a98189.taxi.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.a98189.taxi.Utils.Util;
import com.example.a98189.taxi.base.BaseFragment;
import com.example.a98189.taxi.R;
import com.example.a98189.taxi.client.SetLocation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by  liangjiachneg.Date: 2019/10/7.Time: 16:19
 * <p>
 * 这个类的作用是：
 */
public class StudentFragment extends BaseFragment {
    //选择日期Dialog
    private DatePickerDialog dialog;
    // 用来装日期的
    private Calendar calendar;
    //选择时间Dialog
    private TimePickerDialog timePicker;
    private int hour;
    private int minute;
    //记录各列表项的状态
    private boolean[] checkedItems;
    //各列表项要显示的内容
    private String[] items;
    //判读点击标志
    boolean signdate = false;
    boolean signtime = false;
    boolean signpeoplenumble = false;
    boolean signplace = false;
    //地点判断
    int signnumber = 1;
    //日期
    int trueyear;
    int truemonth;
    int trueday;
    int truehour;
    int trueminute;
    int truesecond;
    String truepeoplenumber,trueplace;
    //字符串转date类型
    String a,b,c;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_student,null);

        //在fragment中获取组件，需要先定义view
        ImageButton button1 = view.findViewById(R.id.exchange);
        final TextView getDate = view.findViewById(R.id.a);
        final TextView getTime = view.findViewById(R.id.c);
        final TextView textView1 = view.findViewById(R.id.textview1);
        final TextView textView2 = view.findViewById(R.id.textview2);
        final TextView textView3 = view.findViewById(R.id.b);
        final TextView peoplenumble = view.findViewById(R.id.peoplenumble);
        final String[] a = {textView1.getText().toString()};
        final String b = textView2.getText().toString();
        final TextView setid = view.findViewById(R.id.setid);
        Button sharing = view.findViewById(R.id.sharing);

        //地点互换具体实现
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signnumber++;
                setid.setText("选择上车点");
                if(textView1.getText().toString()== a[0] &textView2.getText().toString()==b)
                {
                    textView1.setText(getString(R.string.b));
                    textView2.setText(getString(R.string.a));
                }else if(textView1.getText().toString()==b&textView2.getText().toString()== a[0]){
                    textView1.setText(getString(R.string.a));
                    textView2.setText(getString(R.string.b));
                }else{
                    Toast.makeText(getActivity(), "您手速贼快，我跟不上了", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //日期选择具体实现
        getDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar startDate = Calendar.getInstance();
                Calendar endDate = Calendar.getInstance();

                calendar = Calendar.getInstance();
                final int year=calendar.get(Calendar.YEAR);
                final int month=calendar.get(Calendar.MONTH)+1;
                final int day=calendar.get(Calendar.DAY_OF_MONTH);
                //正确设置方式 原因：注意事项有说明
                int year1 = year;
                int month1 = month-1;
                int day1 = day;
                startDate.set(year1,month1,day1);
                day1 = day1+7;
                if (day1>29){
                    month1++;
                    if(month1>12){
                        year1++;
                    }
                }
                endDate.set(year1,month1,day1);
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override

                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        String s3 = getTime(date);
                        getDate.setText(s3);
                        //分离数组
                        String[] temp = null;
                        temp = s3.split("[-]");
                        trueyear  = Integer.parseInt(temp[0]);
                        truemonth = Integer.parseInt(temp[1]);
                        trueday = Integer.parseInt(temp[2]);
                        signdate = true;
                        textView3.setText(timeset(trueyear,truemonth,trueday));


                    }
                    //判断时间为今天或明天
                    private String timeset(int yearOfOurself,int monthOfYear,int dayOfMonth) {
                        String time = "";

                        if(year==yearOfOurself) {
                            //month当前时间，monthOfYear设置时间
                            if(month==monthOfYear) {
                                //day当前时间，dayOfMonth设置时间
                                if (day == dayOfMonth) {
                                    time = "今天";
                                } else if (day == dayOfMonth - 1) {
                                    time = "明天";
                                } else if (day == dayOfMonth + 1) {
                                    time = "昨天";
                                } else if (day > dayOfMonth + 1) {
                                    time = (day - dayOfMonth) + "天前";
                                } else if (dayOfMonth > day - 1) {
                                    time = (dayOfMonth - day) + "天后";
                                }
                            } else if (month>monthOfYear){
                                time = (month-monthOfYear)+"月前";
                            } else if(month<monthOfYear){
                                time = (monthOfYear-month)+"月后";
                            }
                        }else if(year>yearOfOurself){
                            time = (year-yearOfOurself)+"年前";
                        }else if(year<yearOfOurself)
                            time = (yearOfOurself-year)+"年后";
                        return time;
                    }

                    private String getTime(Date date) {

                        SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd");
                        String dateString = formatter.format(date);
                        return dateString;
                    }


                })
                        .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                        .setTitleText("选择日期")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                        .setRangDate(startDate,endDate)//起始终止年月日设定
                        .build();
                // pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
            }
        });

        //时间选择具体实现
        getTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        getTime.setText(getTime(date));
                        String s3 = getTime(date);
                        //分离数组
                        String[] temp = null;
                        temp = s3.split("[:]");
                        truehour  = Integer.parseInt(temp[0]);
                        trueminute = Integer.parseInt(temp[1]);
                        truesecond = Integer.parseInt(temp[2]);
                        signtime = true;
                    }

                    private String getTime(Date date) {

                        @SuppressLint("SimpleDateFormat")
                        SimpleDateFormat formatter  = new SimpleDateFormat("HH:mm:ss");
                        String dateString = formatter.format(date);
                        return dateString;
                    }
                })
                        .setType(new boolean[]{false, false, false, true, true, true})// 默认全部显示
                        .setTitleText("选择时间点")//标题文字
                        .isCyclic(true)//是否循环滚动
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .build();
                pvTime.show();

            }
        });

        //乘坐人数具体实现
        peoplenumble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //j记录各列表项的状态
                checkedItems = new boolean[]{false,true,false,true};
                //各列表项显示的内容
                items = new String[]{"1","2","3","4"};
                //显示带单选列表项的对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                //设置对话框的图标
                builder.setIcon(R.drawable.toolsbutton);
                //设置对话框的标题
                builder.setTitle("请选择乘坐人数：");
                builder.setSingleChoiceItems(items, 4, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //选择结果
                        peoplenumble.setText(items[which]);
                        signpeoplenumble = true;
                        truepeoplenumber = items[which];
                    }
                });
                //添加确认按钮
                builder.setPositiveButton("确认",null);
                //创建对话框并显示
                builder.create().show();


            }
        });

        //开始拼车按钮具体实现
        sharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(signplace){
                    if(signdate) {
                        if (signtime) {
                            if (signpeoplenumble) {
                                String location = null;
                                //出车地点判断
                                if(signnumber%2 ==0){
                                    location = "西城→官渡";
                                } else{
                                    location = "官渡→西城";
                                }
                                //跳转到Module6
                                Intent intent=new Intent(getActivity(), SetLocation.class);
                                intent.putExtra("trueyear",trueyear);
                                intent.putExtra("truemonth",truemonth);
                                intent.putExtra("trueday",trueday);
                                intent.putExtra("truehour",truehour);
                                intent.putExtra("trueminute",trueminute);
                                intent.putExtra("truesecond",truesecond);
                                intent.putExtra("truepeoplenumber",truepeoplenumber);
                                intent.putExtra("trueplace",trueplace);
                                intent.putExtra("location",location);

                                startActivity(intent);
                            } else {
                                Util.showToast(mContext, "请设置乘坐人数~");
                            }
                        } else {
                            Util.showToast(mContext, "请设置时间点~");
                        }
                    }else {
                        Util.showToast(mContext, "请设置日期~");
                    }
                }else {
                    Util.showToast(mContext, "请设置上车点~");
                }

            }

        });
        //设置上车点
        setid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //记录各列表项的状态
                checkedItems = new boolean[]{false,true,false,true};
                //各列表项显示的内容
                if(signnumber%2 ==0){
                    items = new String[]{"北门","教学楼","南门"};
                } else{
                    items = new String[]{"南一门"};
                }

                //显示带单选列表项的对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                //设置对话框的图标
                builder.setIcon(R.drawable.toolsbutton);
                //设置对话框的标题
                builder.setTitle("请选择上车点：");
                builder.setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //选择结果
                        setid.setText(items[which]);
                        signplace = true;
                        trueplace = items[which];
                    }
                });
                //添加确认按钮
                builder.setPositiveButton("确认",null);
                //创建对话框并显示
                builder.create().show();
            }
        });


        return view;
    }




    @Override
    protected View initView() {
        return null;
    }
}
