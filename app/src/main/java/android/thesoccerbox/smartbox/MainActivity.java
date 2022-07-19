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
    private boolean[] roomButtonValues = new boolean[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRoomOneButton = (Button) findViewById(R.id.room_one_button);
        mRoomTwoButton = (Button) findViewById(R.id.room_two_button);
        mRoomThreeButton = (Button) findViewById(R.id.room_three_button);
        mRoomFourButton = (Button) findViewById(R.id.room_four_button);
        mNextButton = (Button) findViewById(R.id.next_button);

        mRoomOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, R.string.test, Toast.LENGTH_SHORT).show();

                if(roomButtonValues[0]) {

                }
                else {

                }
            }
        });


    }
}