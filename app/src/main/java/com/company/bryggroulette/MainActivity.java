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
    private static final String[] sectors = { "32. Er det ingen på tapp, men vi kan anbefale Bourbon & Rye Barrel Aged PECANISHER", "15. Field Trip",
            "19. Hytta", "4. Cocobänger ", "21. Schous Pils", "2. Er det ingen på tapp, men vi kan anbefale Tønnelagra 001", "25. Juicy Liu (2021)", "17 Rhubarb Foudre Saison", "34. Er det ingen på tapp, men vi anbefaler Kveika Rugøl",
            "6. ERLING Hazy Pale Ale", "27. Oslove","13. Fantom", "36. Er det ingen på tapp, men vi kan anbefale MELON HUSK - WATERMELON SUGAR SOUR", "11. Guinness Draught (Nitro)", "30. Er det ingen på tapp, men vi kan anbefale Bacchus Frambozenbier", "8. Wormhole 2.0 ",
            "23. Lorpan IPA", "10. Sauna", "5. Darkwing", "24. Simcoe Maverick", "16. Fast Pace - DEFCON 2", "33. Er det ingen på tapp, men vi kan anbefale Ayinger Urweisse",
            "1. BFF Blueberry Fluff Forever Gose Brewski X Omnipollo", "20. Frydenlund Bayer", "14. Hopshake", "31. Er det ingen på tapp, men vi kan anbefale Fløien IPA", "9. SURE-GYDA Trippelfruktet Tranebær/Granateple Surøl", "22. Brooklyn Lager",
            "18. 1664 Blanc", "29. Er det ingen på tapp, men vi kan anbefale Syndefloden", "7. Slam Dunk", "28. The Very Best of Canny Rogers", "12. Fosskodde 3.0", "35. Frydenlund pilsner",
            "3. Noa Pecan Mud Cake Stout", "26. Dead Cat Double IPA", "0. Du ville ikke ha øl du?"
    };
    @BindView(R.id.spinBtn)
    Button spinBtn;
    @BindView(R.id.resultTv)
    TextView resultTv;
    @BindView(R.id.wheel)
    ImageView wheel;
    // We create a Random instance to make our wheel spin randomly
    private static final Random RANDOM = new Random();
    private int degree = 0, degreeOld = 0;
    // We have 37 sectors on the wheel, we divide 360 by this value to have angle for each sector
    // we divide by 2 to have a half sector
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
        // we calculate random angle for rotation of our wheel
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
                // we empty the result text view when the animation start
                resultTv.setText("");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // we display the correct sector pointed by the triangle at the end of the rotate animation
                resultTv.setText(getSector(360 - (degree % 360)));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        // we start the animation
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
            // now we can test our Android Roulette Game :)
            // That's all !
            // In the second part, you will learn how to add some bets on the table to play to the Roulette Game :)
            // Subscribe and stay tuned !

        } while (text == null  &&  i < sectors.length);

        return text;
    }
}
