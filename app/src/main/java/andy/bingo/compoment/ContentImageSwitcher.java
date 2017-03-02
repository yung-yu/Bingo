package andy.bingo.compoment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import andy.bingo.R;


/**
 * Created by andyli on 2017/3/1.
 */

public class ContentImageSwitcher extends ImageSwitcher implements ImageSwitcher.ViewFactory {
	private Context context;
	private List<Bitmap> Images;
	private Handler mhandler;
	private static final int DEFAULT_DURTION = 1000;
	private static final int UPDATE_IMAGE = 1;
	private Timer timer;
	private int curIndex = 0;
	private ImageView iv;


	public ContentImageSwitcher(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ContentImageSwitcher(Context context) {
		super(context);
		init(context);

	}

	public void init(Context context) {
		this.context = context;
		setInAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom));
		setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_up));
		mhandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
					case UPDATE_IMAGE:
						updateImageView();
						break;
				}
			}
		};
		setFactory(this);
	}

	public void setCurIndex(int index) {
		if (index < Images.size()) {
			curIndex = index;
			setImageBitmap(Images.get(curIndex));
		}

	}
	public void setImageBitmap(Bitmap bmp){
		ImageView image = (ImageView)this.getNextView();
		image.setImageBitmap(bmp);
		showNext();
	}
	public void setImages(List<Bitmap> datas) {
		this.Images = datas;
	}


	public void start() {
		start(DEFAULT_DURTION);
	}

	public void start(int time) {
		if (timer == null) {
			timer = new Timer();
		}
		timer.schedule(new timerTask(), 1, time);
	}

	public void stopDelay(int time) {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				stop();
			}
		}, time);
	}

	public void stop() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	private class timerTask extends TimerTask {

		@Override
		public void run() {   //不能在这里创建任何UI的更新，toast也不行
			// TODO Auto-generated method stub
			Message msg = new Message();
			msg.what = UPDATE_IMAGE;
			mhandler.sendMessage(msg);
		}
	}

	;

	public void updateImageView() {
		if (Images != null && Images.size() > 0) {
			if (curIndex < Images.size()) {
				setImageBitmap(Images.get(curIndex));
				curIndex++;
				if (curIndex >= Images.size()) {
					curIndex = 0;
				}
			}

		}
	}

	@Override
	public View makeView() {

		iv = new ImageView(context);
		iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
		return iv;
	}
}
