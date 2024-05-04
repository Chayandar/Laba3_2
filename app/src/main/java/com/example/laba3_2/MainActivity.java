package com.example.laba3_2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int secretNumber;
    private int previousGuess = -1;
    private int attempts = 0;
    private int remainingAttempts = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        secretNumber = generateRandomNumber();

        Button submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkGuess();
            }
        });

        Button hint1Btn = findViewById(R.id.hint1Btn);
        hint1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHint1();
            }
        });

        Button hint2Btn = findViewById(R.id.hint2Btn);
        hint2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHint2();
            }
        });

        Button hint3Btn = findViewById(R.id.hint3Btn);
        hint3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHint3();
            }
        });

        updateAttemptsTextView();
    }

    private int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(100);
    }

    private void checkGuess() {
        EditText guessEditText = findViewById(R.id.guessEditText);
        String guessString = guessEditText.getText().toString();

        if (guessString.isEmpty()) {
            showToast("Введите число");
            return;
        }

        int currentGuess = Integer.parseInt(guessString);

        if (currentGuess < 0 || currentGuess > 99) {
            showToast("Введите число от 0 до 99");
            return;
        }

        attempts++;

        if (attempts >= remainingAttempts) {
            showToast("Игра окончена. Попытки закончились. Загаданное число: " + secretNumber);
            resetGame();
            return;
        }

        remainingAttempts = remainingAttempts - attempts;
        updateAttemptsTextView();

        if (previousGuess == -1) {
            showToast("Угадайте число от 0 до 99");
        } else {
            int prevDifference = Math.abs(secretNumber - previousGuess);
            int currentDifference = Math.abs(secretNumber - currentGuess);

            if (currentDifference < prevDifference) {
                showToast("Теплее");
            } else if (currentDifference > prevDifference) {
                showToast("Холоднее");
            } else {
                showToast("Так же как и предыдущее");
            }
        }

        previousGuess = currentGuess;
    }

    private void showHint1() {
        boolean isEven = secretNumber % 2 == 0;
        showToast(isEven ? "Четное число" : "Нечетное число");
        remainingAttempts = Math.max(0, remainingAttempts - 2);
        updateAttemptsTextView();
        checkRemainingAttempts();
    }

    private void showHint2() {
        boolean isInRange = secretNumber >= 0 && secretNumber <= 49;
        showToast(isInRange ? "Число в диапазоне 0-49" : "Число в диапазоне 50-99");
        remainingAttempts = Math.max(0, remainingAttempts - 2);
        updateAttemptsTextView();
        checkRemainingAttempts();
    }

    private void showHint3() {
        int sumOfDigits = calculateSumOfDigits(secretNumber);
        showToast(sumOfDigits < 10 ? "Сумма цифр меньше 10" : "Сумма цифр больше или равна 10");
        remainingAttempts = Math.max(0, remainingAttempts - 2);
        updateAttemptsTextView();
        checkRemainingAttempts();
    }

    private int calculateSumOfDigits(int number) {
        int sum = 0;
        while (number != 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void updateAttemptsTextView() {
        TextView attemptsTextView = findViewById(R.id.attemptsTextView);
        attemptsTextView.setText("Попыток: " + remainingAttempts);
    }

    private void resetGame() {
        secretNumber = generateRandomNumber();
        previousGuess = -1;
        attempts = 0;
        remainingAttempts = 10;
        updateAttemptsTextView();
    }

    private void checkRemainingAttempts() {
        if (remainingAttempts <= 0) {
            showToast("Игра окончена. Попытки закончились. Загаданное число: " + secretNumber);
            resetGame();
        }
    }
}
