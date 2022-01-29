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
            "0. Du ville ikke ha øl du?",
            "1. Underkastelse",
            "2. Er det ingen på tapp, men vi kan anbefale Tønnelagra 001",
            "3. Hidden In the Green",
            "4. Sasion larsen",
            "5. Something Good 16",
            "6. Immolator",
            "7. North X Bundobust Salted Lime Sour",
            "8. Without A Word",
            "9. Sour Cherry - FARMER's RESERVE",
            "10. Baltic Porter Day 2022",
            "11. Guinness Draught (Nitro)",
            "12. Co-Sharenting",
            "13. STEADY ROLLING TRIANGLES",
            "14. The Great Stavanger Oreo Crisis",
            "15. SHAUN OF THE DEATH",
            "16. Infinite Reflections",
            "17. SOUP SHOWERS",
            "18. 1664 Blanc",
            "19. Kittens, Puppies & Hops ",
            "20. Frydenlund Bayer",
            "21. Er det ingen på tapp, men vi kan anbefale Syndefloden",
            "22. Brooklyn Lager",
            "23. Grimecreep",
            "24. Glögg Sour",
            "25. Here In the Morning",
            "26. Peachy Ape",
            "27. In Bloom",
            "28. Twolips",
            "29. Black And Blue",
            "30. Tropic Thunder",
            "31. Hans Gruber - Yippee Ki Yay",
            "32. Tikitukan",
            "33. Earth Past",
            "34. Plaguewing",
            "35. Frydenlund pilsner",
            "36. Er det ingen på tapp, men vi kan anbefale MELON HUSK - WATERMELON SUGAR SOUR",
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
