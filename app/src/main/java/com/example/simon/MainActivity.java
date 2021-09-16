package com.example.simon;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button[] tiles;
    String[] colors = {"red", "green", "yellow", "blue"};
    Button start;
    TextView message;
    ArrayList<Integer> moves = new ArrayList<Integer>();
    int turn = 0;
    Handler handler = new Handler();

    public static int getRandom(int min, int max) {
        // Returns a random number between min and max
        // Math.random generates a pseudorandom number between 0.0 and 1.0
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    public void playAudio(String id){
        int resourceID = getResources().getIdentifier(id, "raw", getPackageName());
        MediaPlayer sound = MediaPlayer.create(MainActivity.this, resourceID);
        sound.start();
        sound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }

    public void getNextMove(){
        // The next random move
        final int newMove = getRandom(0, 3);
        moves.add(newMove);
        Log.d("MOVES", moves.toString());
        playAudio(colors[newMove]);
        // Button press animation
        tiles[newMove].setPressed(true);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //called after 1000 milliseconds / 1 second
                tiles[newMove].setPressed(false);
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    public void getNextMoveDelayed(){
        enableTiles(false);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //called after 1000 milliseconds / 1 second
                getNextMove();
                enableTiles(true);
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    public void enableTiles(boolean enable){
        // Enables or disables all tiles
        for(Button tile : tiles){
            tile.setEnabled(enable);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // moves = new ArrayList<Integer>();

        tiles = new Button[] {(Button) findViewById(R.id.red), (Button) findViewById(R.id.green),
                            (Button) findViewById(R.id.yellow), (Button) findViewById(R.id.blue)};
        enableTiles(false);
        start = (Button) findViewById(R.id.start);
        message = (TextView) findViewById(R.id.message);

//        NOTE:
//        You are creating an anonymous class (View.OnClickListener) for each button.
//        The onClick() method within that class has a different scope than the for loop,
//        therefore it has no access to local variable i.
//        Solution: Declare a new Final variable j.

        for(int i = 0; i < tiles.length; i++){
            final int j = i;
            tiles[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Play audio associated with each tile.
                    playAudio(colors[j]);
                    if (j == moves.get(turn)){
                        // Player hits the tiles right one by one
                        turn++;
                        if (turn == moves.size()){
                            // Player completed a level
                            playAudio("ding");
                            turn = 0;
                            message.setText("Level "+(moves.size() + 1));
                            getNextMoveDelayed();
                        }
                    }
                    else {
                        // Player gets one tile wrong
                        playAudio("wrong");
                        message.setText("OOP! Try again.");
                        enableTiles(false);
                        start.setEnabled(true);
                    }
                    //Toast.makeText(MainActivity.this, "Clicked! "+colors[j], Toast.LENGTH_SHORT).show();
                }
            });
        }

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.setText("Level 1");
                turn = 0;
                moves.clear();
                enableTiles(true);
                getNextMoveDelayed();
                start.setEnabled(false);
                //Toast.makeText(MainActivity.this, "Clicked!"+v.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}