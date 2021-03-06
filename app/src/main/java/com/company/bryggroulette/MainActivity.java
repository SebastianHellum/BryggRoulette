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
            "32. Utflykt",
            "15. What’s Up Dok?",
            "19. SURE-KIRSTEN Trippelfruktet Kirsebær Surøl",
            "4. Dry Hop Sour",
            "21. Schous Pils",
            "2. Er det ingen på tapp, men vi kan anbefale MELON HUSK - WATERMELON SUGAR SOUR",
            "25. Underkastelse",
            "17. Lupulicious",
            "34. Bear Hug (2021)",
            "6. Tail Stall",
            "27. Blastpipe",
            "13. Stromboli",
            "36. Er det ingen på tapp, men vi kan anbefale MELON HUSK - WATERMELON SUGAR SOUR",
            "11. Er det ingen på tapp, men vi kan anbefale MELON HUSK - WATERMELON SUGAR SOUR",
            "30. Gårdsgutt",
            "8. SURE-SOLVEIG Trippelfruktet Solbær Surøl",
            "23. Chocolate Passion",
            "10. Guinness Draught",
            "5. Er det ingen på tapp, men vi kan anbefale MELON HUSK - WATERMELON SUGAR SOUR",
            "24. Judge Fudge",
            "16. Overload",
            "33. Le Mois Demi-Sec",
            "1. Cali Kush",
            "20. Frydenlund Bayer",
            "14. Bitter Sweet",
            "31. And Dance The Blues",
            "9. Blub",
            "22. Brooklyn Lager",
            "18. 1664 Blanc",
            "29. Odins Tipple",
            "7. Tapawera",
            "28. Er det ingen på tapp, men vi kan anbefale MELON HUSK - WATERMELON SUGAR SOUR",
            "12. MIDGARD Incognito Mosaic IPA",
            "35. Frydenlund pilsner",
            "3. Flash Crash",
            "26. Pilsner",
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
