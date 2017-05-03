package nongsa.agoto;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import nongsa.agoto.R;

public class LogoActivity extends Activity {
    private ImageView logoIMG;
    private TextView logoText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        final Animation animTransTwits = AnimationUtils.loadAnimation(
                this, R.anim.anim_logo);
        logoIMG = (ImageView) findViewById(R.id.mainLogoIMG);
        logoIMG.setAnimation(animTransTwits);

        final Animation animTransTwits2 = AnimationUtils.loadAnimation(
                this, R.anim.anim_logo_text);
        logoText = (TextView) findViewById(R.id.mainLogoText);
        logoText.setAnimation(animTransTwits2);

        final int welcomeScreenDisplay = 1500; //splash lasts for 3 sec.

        LogoShowing(welcomeScreenDisplay);

    }

    /**
     * void checkLogoShowing()
     * -
     */
    private void checkLogoShowing() {

    }

    /**
     * void nextActivity()
     * -
     */
    private void nextActivity() {
        //Intent intent = new Intent(this,LoginActivity.class);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * void LogoShowing(int time_sec)
     *
     * @param time_milsec : logo image showing time (The time to sleep in milliseconds)
     */
    private void LogoShowing(final int time_milsec) {
        Thread welcomeThread = new Thread() {
            int wait = 0;

            @Override
            public void run() {
                try {
                    super.run();
                    while (wait < time_milsec) {
                        sleep(100);
                        wait += 100;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    nextActivity();
                    finish();
                }
            }
        };
        welcomeThread.start();
    }
}
