package andy.bingo;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;
import java.util.List;

import andy.bingo.compoment.ContentImageSwitcher;

public class MainActivity extends AppCompatActivity {
    private ContentImageSwitcher[] imageSwitchers = new ContentImageSwitcher[9];
	private Button button;
	private boolean isRunning = false;

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
					new Thread(){
						@Override
						public void run() {
							super.run();
							float count = 1;
							for (ContentImageSwitcher imageSwitcher : imageSwitchers) {
								imageSwitcher.stop();
								try {
									Thread.sleep((long) (10*count));
									count = (int)(Math.random()* 100)+1;
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									isRunning = false;
									button.setText("start");
									button.setEnabled(true);
								}
							});
						}
					}.start();

					button.setEnabled(false);
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
									button.setText("stop");
									button.setEnabled(true);
								}
							});
						}
					}.start();
				}
			}
		});
		int i = 0;
		List<Drawable> images = Arrays.asList(new Drawable[]{
				getResources().getDrawable(R.drawable.image1),
				getResources().getDrawable(R.drawable.image2),
				getResources().getDrawable(R.drawable.image3),
				getResources().getDrawable(R.drawable.image4),
				getResources().getDrawable(R.drawable.image5),
				getResources().getDrawable(R.drawable.image6),
				getResources().getDrawable(R.drawable.image7),
				getResources().getDrawable(R.drawable.image8),
				getResources().getDrawable(R.drawable.image9)
		});
		for (ContentImageSwitcher imageSwitcher : imageSwitchers) {
			imageSwitcher.setImages(images);
			imageSwitcher.setCurIndex(i);
			i++;
		}

	}
}
