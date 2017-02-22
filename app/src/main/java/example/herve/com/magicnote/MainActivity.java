package example.herve.com.magicnote;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import example.herve.com.magicnote.adapter.RoadAdapter;
import example.herve.com.magicnote.listener.VolumeListener;
import example.herve.com.magicnote.utils.DialogUtils;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout activityMain;
    private RecyclerView rvRoad;
    private ImageView ivNoteBoy;
    private AudioRecordDemo audioRecordDemo;
    private MediaRecorderDemo mediaRecorderDemo;
    private RoadAdapter roadAdapter;

    public String TAG = getClass().getSimpleName();

    private Context mContext;

    private ArrayList<Integer> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        activityMain = (RelativeLayout) findViewById(R.id.activity_main);
        rvRoad = (RecyclerView) findViewById(R.id.rv_road);
        ivNoteBoy = (ImageView) findViewById(R.id.iv_note_boy);

        audioRecordDemo = new AudioRecordDemo();
        mediaRecorderDemo = new MediaRecorderDemo();

        initData();
        initListener();

    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            data.add(i);
        }
        roadAdapter = new RoadAdapter(data, mContext);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);

        rvRoad.setLayoutManager(layoutManager);
        rvRoad.setAdapter(roadAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        audioRecordDemo.getNoiseLevel();

    }

    private void initListener() {

        rvRoad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        audioRecordDemo.setVolumeListener(new VolumeListener() {
            @Override
            public void volumeChangeListener(double volumeValue) {
                if (isShowMessage) {
                    return;
                }
                if (volumeValue > 40) {
                    if (!start) {
                        start();
                    }
                    float test = (float) (Math.sqrt(volumeValue - 30) * 10);
                    Log.i(TAG, "volumeChangeListener: test=" + test);
                    float tranY = test + (float) volumeValue;
                    tranY = (float) (volumeValue * volumeValue / (2 * 10));
                    Log.i(TAG, "volumeChangeListener: tranY=" + tranY);

                    ivNoteBoy.setTranslationY(-tranY);
                } else {
                    if (!start) {
                        return;
                    }
                    stop();
                    ivNoteBoy.setTranslationY(0);
                    if (rvRoad.getLayoutManager() instanceof LinearLayoutManager) {
                        int firstPosition = ((LinearLayoutManager) rvRoad.getLayoutManager()).findFirstVisibleItemPosition();
                        int lastPosition = ((LinearLayoutManager) rvRoad.getLayoutManager()).findLastVisibleItemPosition();
                        int count = lastPosition - firstPosition;
                        for (int i = 0; i < count; i++) {
                            View view = rvRoad.getChildAt(i);
                            if (view.getTag().equals("W")) {
                                if (view.getX() < (ivNoteBoy.getX() + ivNoteBoy.getWidth() * 2 / 3) && view.getX() + view.getWidth() > (ivNoteBoy.getX() + ivNoteBoy.getWidth() * 1 / 3)) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            stop();
                                            isShowMessage = true;
                                            DialogUtils.showDialog(mContext, "你已死亡！", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    isShowMessage = false;
                                                    rvRoad.scrollToPosition(0);
                                                }
                                            });
                                        }
                                    });
                                }
                            }
                        }
                    }
                }
            }
        });

    }

    private void stop() {
        rvRoad.removeCallbacks(goon);
        start = false;
    }

    private boolean start = false;
    private boolean isShowMessage = false;

    private Runnable goon = new Runnable() {
        @Override
        public void run() {
            rvRoad.scrollBy((int) rvRoad.getX() + 1, (int) rvRoad.getY());
            start();
        }
    };

    private void start() {
        rvRoad.postDelayed(goon, 2);
        start = true;
    }
}
