package android.thesoccerbox.smartbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mRoomOneButton;
    private Button mRoomTwoButton;
    private Button mRoomThreeButton;
    private Button mRoomFourButton;
    private Button mNextButton;

    private Button[] roomButtons = {mRoomOneButton, mRoomTwoButton, mRoomThreeButton, mRoomFourButton};
    int numSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRoomOneButton = (Button) findViewById(R.id.room_one_button);
        mRoomTwoButton = (Button) findViewById(R.id.room_two_button);
        mRoomThreeButton = (Button) findViewById(R.id.room_three_button);
        mRoomFourButton = (Button) findViewById(R.id.room_four_button);
        mNextButton = (Button) findViewById(R.id.next_button);

        mNextButton.setEnabled(false);

        // Change status of button 1 when selected
        mRoomOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRoomOneButton.isSelected()) {
                    mRoomOneButton.setSelected(false);
                    numSelected--;

                    if(numSelected == 0) {
                        mNextButton.setEnabled(false);
                    }
                }
                else {
                    mRoomOneButton.setSelected(true);
                    numSelected++;

                    mNextButton.setEnabled(true);
                }
            }
        });

        // Change status of button 2 when selected
        mRoomTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRoomTwoButton.isSelected()) {
                    mRoomTwoButton.setSelected(false);
                    numSelected--;

                    if(numSelected == 0) {
                        mNextButton.setEnabled(false);
                    }
                }
                else {
                    mRoomTwoButton.setSelected(true);
                    numSelected++;

                    mNextButton.setEnabled(true);
                }
            }
        });

        // Change status of button 3 when selected
        mRoomThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRoomThreeButton.isSelected()) {
                    mRoomThreeButton.setSelected(false);
                    numSelected--;

                    if(numSelected == 0) {
                        mNextButton.setEnabled(false);
                    }
                }
                else {
                    mRoomThreeButton.setSelected(true);
                    numSelected++;

                    mNextButton.setEnabled(true);
                }
            }
        });

        // Change status of button 4 when selected
        mRoomFourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRoomFourButton.isSelected()) {
                    mRoomFourButton.setSelected(false);
                    numSelected--;

                    if(numSelected == 0) {
                        mNextButton.setEnabled(false);
                    }
                }
                else {
                    mRoomFourButton.setSelected(true);
                    numSelected++;

                    mNextButton.setEnabled(true);
                }
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to next page
                Toast.makeText(MainActivity.this, "Go to next page", Toast.LENGTH_SHORT).show();
            }
        });


    }
}