package example.herve.com.magicnote;

import android.content.Context;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import example.herve.com.magicnote.adapter.RoadAdapter;
import example.herve.com.magicnote.listener.VolumeListener;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout activityMain;
    private Button btnStart;
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
        btnStart = (Button) findViewById(R.id.btn_start);
        rvRoad = (RecyclerView) findViewById(R.id.rv_road);
        ivNoteBoy = (ImageView) findViewById(R.id.iv_note_boy);

        audioRecordDemo = new AudioRecordDemo();
        mediaRecorderDemo = new MediaRecorderDemo();

        initData();
        initListener();

    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            data.add(i);
        }
        roadAdapter = new RoadAdapter(data, mContext);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);

        rvRoad.setLayoutManager(layoutManager);
        rvRoad.setAdapter(roadAdapter);
    }

    private void initListener() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                audioRecordDemo.getNoiseLevel();
                if (start) {
                    stop();
                } else {
                    start();
                }
            }
        });

        rvRoad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                stop();
                return false;
            }
        });

        audioRecordDemo.setVolumeListener(new VolumeListener() {
            @Override
            public void volumeChangeListener(double volumeValue) {
                if (volumeValue > 30) {
                    start();
                    ivNoteBoy.setTranslationY(-(float) volumeValue);
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
                                if (view.getX() < (ivNoteBoy.getX() + ivNoteBoy.getWidth() * 2 / 3) && view.getX() + view.getWidth() > ivNoteBoy.getX() - ivNoteBoy.getWidth() * 2 / 3) {
                                    Log.i(TAG, "volumeChangeListener:死了 ");
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            stop();
                                            Toast.makeText(mContext, "游戏失败！", Toast.LENGTH_SHORT).show();
                                            rvRoad.scrollBy(-tranX, (int) rvRoad.getY());
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

    private int tranX = 0;
    private boolean start = false;

    private Runnable goon = new Runnable() {
        @Override
        public void run() {
            rvRoad.scrollBy((int) rvRoad.getX() + 1, (int) rvRoad.getY());
            tranX = +1;
            start();
        }
    };

    private void start() {
        rvRoad.postDelayed(goon, 50);
        start = true;
    }
}
