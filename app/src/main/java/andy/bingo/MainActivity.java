package andy.bingo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import andy.bingo.compoment.ContentImageSwitcher;
import andy.bingo.utils.BitmapUtil;

public class MainActivity extends AppCompatActivity {
    private ContentImageSwitcher[] imageSwitchers = new ContentImageSwitcher[9];
	private Button button;
	private boolean isRunning = false;
	int stopPinter = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageSwitchers[0] = (ContentImageSwitcher) findViewById(R.id.imageSwicher1);
		imageSwitchers[1] = (ContentImageSwitcher) findViewById(R.id.imageSwicher2);
		imageSwitchers[2] = (ContentImageSwitcher) findViewById(R.id.imageSwicher3);
		imageSwitchers[3] = (ContentImageSwitcher) findViewById(R.id.imageSwicher4);
		imageSwitchers[4] = (ContentImageSwitcher) findViewById(R.id.imageSwicher5);
		imageSwitchers[5] = (ContentImageSwitcher) findViewById(R.id.imageSwicher6);
		imageSwitchers[6] = (ContentImageSwitcher) findViewById(R.id.imageSwicher7);
		imageSwitchers[7] = (ContentImageSwitcher) findViewById(R.id.imageSwicher8);
		imageSwitchers[8] = (ContentImageSwitcher) findViewById(R.id.imageSwicher9);
		button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isRunning) {

					if(stopPinter < imageSwitchers.length){
						imageSwitchers[stopPinter].stop();
						stopPinter++;
					} else {
						isRunning = false;
						button.setText("start");
					}
				} else {
					new Thread(){
						@Override
						public void run() {
							super.run();
							float count = 1;
							for (ContentImageSwitcher imageSwitcher : imageSwitchers) {
								imageSwitcher.start(200);
								try {
									Thread.sleep((long) (1*count));
									count = (int)(Math.random()* 100)+1;
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									isRunning = true;
									stopPinter = 0;
									button.setText("stop");
									button.setEnabled(true);
								}
							});
						}
					}.start();
				}
			}
		});

		List<Integer> images = Arrays.asList(new Integer[]{
				R.drawable.image1,
				R.drawable.image2,
				R.drawable.image3,
				R.drawable.image4,
				R.drawable.image5,
				R.drawable.image6,
				R.drawable.image7,
				R.drawable.image8,
				R.drawable.image9
		});
		List<Bitmap> bitmaps = new ArrayList<>();
		for (int i =0 ; i< images.size();i++){
			Bitmap bmp = BitmapUtil.decodeSampledBitmapFromResource(getResources(), images.get(i), 100, 100);
			bitmaps.add(bmp);
		}
		int i = 0;
		for (ContentImageSwitcher imageSwitcher : imageSwitchers) {
			imageSwitcher.setImages(bitmaps);
			imageSwitcher.setCurIndex(i);

		}

	}
}
