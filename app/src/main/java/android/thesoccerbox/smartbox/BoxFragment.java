package android.thesoccerbox.smartbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class BoxFragment extends Fragment {

    private Button mRoomOneButton;
    private Button mRoomTwoButton;
    private Button mRoomThreeButton;
    private Button mRoomFourButton;
    private Button mNextButton;

    int numSelected = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_boxes, container, false);

        mRoomOneButton = (Button) v.findViewById(R.id.room_one_button);
        mRoomTwoButton = (Button) v.findViewById(R.id.room_two_button);
        mRoomThreeButton = (Button) v.findViewById(R.id.room_three_button);
        mRoomFourButton = (Button) v.findViewById(R.id.room_four_button);
        mNextButton = (Button) v.findViewById(R.id.next_button);

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
                Intent intent = new Intent(getActivity(), GameTypeActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }
}
