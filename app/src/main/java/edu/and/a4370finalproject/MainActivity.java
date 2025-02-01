package edu.and.a4370finalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private GameController gameController;
    private Button changeButton;
    private ImageView[] playerCardsViews;
    private ImageView[] cpuCardsViews;
    private ImageView[] flopCardsViews;
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameController = new GameController(this);

        playerCardsViews = new ImageView[]{
                findViewById(R.id.imagep1),
                findViewById(R.id.imagep2),
                findViewById(R.id.imagep3)
        };

        cpuCardsViews = new ImageView[]{
                findViewById(R.id.imagecp1),
                findViewById(R.id.imagecp2),
                findViewById(R.id.imagecp3)
        };

        flopCardsViews = new ImageView[]{
                findViewById(R.id.imageflop1),
                findViewById(R.id.imageflop2),
                findViewById(R.id.imageflop3)
        };


        setCardClickListeners(playerCardsViews);
        setCardClickListeners(flopCardsViews);


        changeButton = findViewById(R.id.Change);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGameRound();
            }
        });

        confirm = findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleConfirmButtonClick();
            }
        });

        updateCardViews();
    }

    private void setCardClickListeners(ImageView[] cardViews) {
        for (final ImageView cardView : cardViews) {
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleCardSelection(cardView);
                }
            });
        }
    }

    private void toggleCardSelection(ImageView cardView) {
        // Toggle selection state of the clicked card
        cardView.setSelected(!cardView.isSelected());
    }

    private void handleConfirmButtonClick() {
        List<Integer> selectedCardIndices = getSelectedIndices(playerCardsViews);
        if (selectedCardIndices.size() == 5) {
            String result = gameController.judgeRound(gameController.getPlayerCards(), gameController.getCpuCards(), gameController.getFlopCards());
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            gameController.startNewRound();
            updateCardViews();
        } else {
            Toast.makeText(MainActivity.this, "not fully implemented", Toast.LENGTH_SHORT).show();
        }
    }

    private List<Integer> getSelectedIndices(ImageView[] cardViews) {
        List<Integer> selectedIndices = new ArrayList<>();
        for (int i = 0; i < cardViews.length; i++) {
            if (cardViews[i].isSelected()) {
                selectedIndices.add(i);
            }
        }
        return selectedIndices;
    }

    private void updateCardViews() {

        displayCards(gameController.getPlayerCards(), playerCardsViews);
        displayCards(gameController.getCpuCards(), cpuCardsViews);
        displayCards(gameController.getFlopCards(), flopCardsViews);
    }

    private void displayCards(List<Card> cards, ImageView[] cardViews) {

        for (int i = 0; i < cardViews.length; i++) {
            if (i < cards.size()) {
                cardViews[i].setImageBitmap(cards.get(i).getSprite());
                cardViews[i].setVisibility(View.VISIBLE);
            } else {
                cardViews[i].setImageBitmap(null);
                cardViews[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    private void startGameRound() {

        List<Integer> selectedIndices = getSelectedIndices(playerCardsViews);

        if (selectedIndices.size() > 0 && selectedIndices.size() <= 3) {

            gameController.swapPlayerCards(selectedIndices);


            updateCardViews();


            String result = gameController.judgeRound(gameController.getPlayerCards(), gameController.getCpuCards(), gameController.getFlopCards());
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();


            gameController.startNewRound();
            updateCardViews();
        } else {
            Toast.makeText(MainActivity.this, "Please select at least one and at most three cards", Toast.LENGTH_SHORT).show();
        }
    }
}