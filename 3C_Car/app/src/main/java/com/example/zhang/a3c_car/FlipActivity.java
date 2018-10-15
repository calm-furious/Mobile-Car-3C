package com.example.zhang.a3c_car;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

public class FlipActivity extends Activity implements  android.view.GestureDetector.OnGestureListener
{
    //定义手势检测器实例
    GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip);
        //new一个手势检测器
        detector = new GestureDetector(this,this);
    }

    //用GestureDetector处理在该activity上发生的所有触碰事件
    public boolean onTouchEvent(MotionEvent me){
        return detector.onTouchEvent(me);
    }

    @Override
    public boolean onDown(MotionEvent arg0) {
        return false;
    }

    /**
     * 滑屏监测
     *
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        float minMove = 120;         //定义最小滑动距离
        float minVelocity = 0;      //定义最小滑动速度
        float beginX = e1.getX();
        float endX = e2.getX();
        float beginY = e1.getY();
        float endY = e2.getY();

        if(beginX-endX>minMove&&Math.abs(velocityX)>minVelocity){   //左滑
            Toast.makeText(this,"左滑", Toast.LENGTH_SHORT).show();  //此处可以更改为当前动作下你想要做的事情
        }else if(endX-beginX>minMove&&Math.abs(velocityX)>minVelocity){   //右滑
            Toast.makeText(this,"右滑",Toast.LENGTH_SHORT).show();  //此处可以更改为当前动作下你想要做的事情
        }else if(beginY-endY>minMove&&Math.abs(velocityY)>minVelocity){   //上滑
            Toast.makeText(this,"上滑",Toast.LENGTH_SHORT).show();  //此处可以更改为当前动作下你想要做的事情
        }else if(endY-beginY>minMove&&Math.abs(velocityY)>minVelocity){   //下滑
            Toast.makeText(this,"下滑",Toast.LENGTH_SHORT).show();  //此处可以更改为当前动作下你想要做的事情
        }

        return false;
    }

    @Override
    public void onShowPress(MotionEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onSingleTapUp(MotionEvent arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onLongPress(MotionEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float velocityX,
                            float velocityY) {

        return false;
    }

}
