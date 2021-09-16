package com.example.dice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button rollBotton;
    private ImageView dice1Image;
    private ImageView dice2Image;
    private TextView messageText;

    public static int getRandom(int min, int max) {
        // Returns a random number between min and max
        // Math.random generates a pseudorandom number between 0.0 and 1.0
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons and other views
        rollBotton = (Button) findViewById(R.id.button);
        dice1Image = (ImageView) findViewById(R.id.die1);
        dice2Image = (ImageView) findViewById(R.id.die2);
        messageText = (TextView) findViewById(R.id.message);

        rollBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val1 = getRandom(1, 6);
                int val2 = getRandom(1, 6);
                String message;
                if (val1 > val2) {
                    message = "Player 1 won! \uD83D\uDEA9";
                }
                else if(val1 < val2) {
                    message = "Player 2 won! \uD83D\uDEA9";
                }
                else{
                    message = "Draw!";
                }
                // Find the resource id (R.drawable.filename)
                // getResources returns a Resources instance for the application's package.
                // getIdentifier returns a resource identifier for the given resource name.
                int resourceID1 = getResources().getIdentifier("dice" + Integer.toString(val1), "drawable", getPackageName());
                int resourceID2 = getResources().getIdentifier("dice" + Integer.toString(val2), "drawable", getPackageName());
                dice1Image.setImageResource(resourceID1);
                dice2Image.setImageResource(resourceID2);
                messageText.setText(message);
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}