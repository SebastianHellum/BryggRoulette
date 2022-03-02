package com.company.bryggroulette;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    // sectors of our wheel (look at the image to see the sectors)
    private static final String[] sectors = {
            "32. 8013",
            "15. Hafgufa",
            "19. Sure Thing!",
            "4. Margarita Baby!",
            "21. Schous Pils",
            "2. Stoking the Furnace",
            "25. Gralla 3 - Beyond Thunderdome",
            "17. Cali Kush",
            "34. Šumava",
            "6. What’s Up Dok?",
            "27. Kissing Trains",
            "13. Brothers In Arms",
            "36. Er det ingen på tapp, men vi kan anbefale MELON HUSK - WATERMELON SUGAR SOUR",
            "11. Norwegian Cuvee",
            "30. Gapahuk",
            "8. Skur 4 Cuvee",
            "23. Underkastelse",
            "10. Akevitt Porter",
            "5. Isac Keller",
            "24. Svelvik Sour",
            "16. Haand To Hand",
            "33. Fløien IPA",
            "1. Edel Sider med Stikkelsbær",
            "20. Frydenlund Bayer",
            "14. Haandbakk",
            "31. Of Wood & Smoke",
            "9. Haandverkspils Glutenfri",
            "22. Brooklyn Lager",
            "18. 1664 Blanc",
            "29. Cuvee Sundbytunet",
            "7. Lørdag",
            "28. Summer Bliss",
            "12. The North Sea",
            "35. Frydenlund pilsner",
            "3. Vårbokk",
            "26. Odins Tipple",
            "0. Du ville ikke ha øl du?",
    };
    @BindView(R.id.spinBtn)
    Button spinBtn;
    @BindView(R.id.resultTv)
    TextView resultTv;
    @BindView(R.id.wheel)
    ImageView wheel;
    // create a Random instance to make our wheel spin randomly
    private static final Random RANDOM = new Random();
    private int degree = 0, degreeOld = 0;
    // 37 sectors on the wheel, divide 360 by this value to have angle for each sector
    // divide by 2 to have a half sector
    private static final float HALF_SECTOR = 360f / 37f / 2f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.spinBtn)
    public void spin(View v) {
        degreeOld = degree % 360;
        // calculate random angle for rotation of our wheel
        degree = RANDOM.nextInt(360) + 720;
        // rotation effect on the center of the wheel
        RotateAnimation rotateAnim = new RotateAnimation(degreeOld, degree,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnim.setDuration(12000);
        final MediaPlayer wheelSoundMP = MediaPlayer.create(this, R.raw.spinn);
        wheelSoundMP.start();
        rotateAnim.setFillAfter(true);
        rotateAnim.setInterpolator(new DecelerateInterpolator());
        rotateAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // empty the result text view when the animation start
                resultTv.setText("");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // display the correct sector pointed by the triangle at the end of the rotate animation
                resultTv.setText(getSector(360 - (degree % 360)));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        wheel.startAnimation(rotateAnim);
    }
    private String getSector(int degrees) {
        int i = 0;
        String text = null;

        do {
            // start and end of each sector on the wheel
            float start = HALF_SECTOR * (i * 2 + 1);
            float end = HALF_SECTOR * (i * 2 + 3);

            if (degrees >= start && degrees < end) {
                // degrees is in [start;end[
                // so text is equals to sectors[i];
                text = sectors[i];
            }

            i++;

        } while (text == null  &&  i < sectors.length);

        return text;
    }
}
