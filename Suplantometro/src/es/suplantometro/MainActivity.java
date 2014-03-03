package es.suplantometro;

import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	Button btn1, btn2, btn3, btn4, btn5;
	LinearLayout linearLayout1, linearLayout2, linearLayout3, linearLayout4, linearLayout5;
	
	private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;

    private MediaRecorder mRecorder = null;

    private MediaPlayer   mPlayer = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Display display=getWindowManager().getDefaultDisplay();
        int width=display.getWidth();
        int height=display.getHeight();
        
        Toast.makeText(this, "Bienvenido a SUPLANTOMETRO", Toast.LENGTH_SHORT).show();
        
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";
        
        btn1 = (Button) findViewById(R.id.bnt1);
        btn2 = (Button) findViewById(R.id.bnt2);
        btn3 = (Button) findViewById(R.id.bnt3);
        btn4 = (Button) findViewById(R.id.bnt4);
        btn5 = (Button) findViewById(R.id.bnt5);
        
        linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
        
        android.view.ViewGroup.LayoutParams params = btn1.getLayoutParams();
        params.height = width/5;
        params.width = width/5;
        btn1.setLayoutParams(params);
        
        
        // ACTIONS        
        OnClickListener btn1listener = new OnClickListener() {
	        boolean mStartRecording = true;
			public void onClick(View v) {
				onRecord(mStartRecording);
                if (mStartRecording) {
                    btn1.setText("Stop recording");
                } else {
                	btn1.setText("Start recording");
                }
                mStartRecording = !mStartRecording;
			}
		};
        
		btn1.setOnClickListener(btn1listener);
		
        btn2.setOnClickListener(new OnClickListener() {			
	        boolean mStartPlaying = true;
			public void onClick(View v) {
				 onPlay(mStartPlaying);
	                if (mStartPlaying) {
	                    btn2.setText("Stop playing");
	                } else {
	                    btn2.setText("Start playing");
	                }
	                mStartPlaying = !mStartPlaying;
			}
		});
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }
    
    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }
    
    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    
}
