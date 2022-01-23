package com.nom.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.os.Handler;
public class easy extends AppCompatActivity {

    private int currentQuestion, currentOption1, currentOption2, currentOption3, currentOption4;
    private int  questionCount = 0,wrongAnswer=0,correctAnswer=0;
    private String[] question, answers, option1, option2, option3, option4;
    private Button answerButton,cancel;
    private TextView titre,questionView, resultView, answerview, score;
    private RadioButton r1, r2, r3, r4;
    private RadioGroup radioGroup;
    private String answer;
    int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions);
        r1 = findViewById(R.id.computer_radio0);
        r2 = findViewById(R.id.computer_radio1);
        r3 = findViewById(R.id.computer_radio2);
        r4 = findViewById(R.id.computer_radio3);
        titre = findViewById(R.id.title_computer);
        answerButton = findViewById(R.id.next_question);
        questionView = findViewById(R.id.computer_question_textview);
        answerview = findViewById(R.id.computer_answer_text_view);
        resultView = findViewById(R.id.computer_result_text);
        score = findViewById(R.id.computer_score);
        radioGroup = findViewById(R.id.computer_radio_group);
        cancel = findViewById(R.id.cancel_button);


        init();
        //initialisation des tableaux(question, answers, option1, option2, option3, option4)
        final ProgressBar bar = (ProgressBar) findViewById(R.id.progress);
        final Handler handler = new Handler();
        Thread worker = new Thread(new Runnable() {
            public void run() {
                while (progress < 11) {
                    try {
// simule un traitement long
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        public void run() {
                            bar.setProgress(++progress);
                        }
                    });
                }
            }
        });
        worker.start();

        //verifier :  radio button ( "button next question" )
        answerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (r1.isChecked() || r2.isChecked() || r3.isChecked() || r4.isChecked()) {

                    checkAnswer();

                } else {
                    Toast.makeText(getApplicationContext(), "please select any option", Toast.LENGTH_SHORT).show();
                }

            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            //on affecte a answer la reponse choisie par l'user
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton=findViewById(checkedId);
                //resultView.setText(radioButton.getText().toString());
                answer=radioButton.getText().toString();


            }
        });
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                answerview.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                answerview.setText("Time over");
                Intent otherActivity = new Intent(getApplicationContext(), timeOver.class);
                startActivity(otherActivity);
            }
        }.start();

    }

    public void init() {
        //tableau des questions
        question = new String[] {
                "1.Which of the following is not Computer Hardware?",
                "2.Internet Explorer is a:",
                "3. A desktop computer is also known as",
                "4.CPU is the --------- of computer",
                "5.The CPU and Primary memory are located on the",
                "6. ----------- is used in first generation computer.",
                "7. Which one of the following is a java keyword?",
                "8. Java declaration statement must end with",
                "9. Which of the following device cannot be shared in Network?",
                "10.Computer is a/an ----------" };
//tableau des reponses correctes
        answers = new String[] { "Antivirus", "Web Browser", "PC",
                "Brain", "motherboard", "Vacuum tubes", "Public",
                "A semicolon", "mouse",
                "electronic device" };
//la 1ére option de chaque question
        option1 = new String[] { "Mouse", "Any person browsing the net", "PC",
                "Brain", "output device", "Transistors", "Switch",
                "Comma", "printer",
                "battery" };
//la 2éme option de chaque question
        option2 = new String[] { "Monitor", "Web Browser", "Laptop",
                "Ear", "storage device", "Vacuum tubes", "While", "A colon",
                "hard disk",
                "input device" };
//la 3éme option de chaque question
        option3 = new String[] { "Printer", "Graphing Package", " Mainframe",
                "Eye", "motherboard", "Microprocessor", "Public",
                "A semicolon", "cd drive",
                "monitoring device" };
//la 4éme option de chaque question
        option4 = new String[] { "Antivirus", "Computer", "Palmtop",
                "All above these", "expansion board", "Integrated circuit", "Void",
                "Full stop", "mouse", "electronic device" };


        currentQuestion= 0;
        currentOption1 = 0;
        currentOption2 = 0;
        currentOption3 = 0;
        currentOption4 = 0;
        r1.setText(option1[currentOption1]);
        r2.setText(option2[currentOption2]);
        r3.setText(option3[currentOption3]);
        r4.setText(option4[currentOption4]);
        questionView.setText(question[currentQuestion]);
        titre.setText("Level Easy");
        answerview.setText("");

// cancel button

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), game.class);
                startActivity(otherActivity);
                finish();
            }
        });

    }

    public boolean iscorrect( String answer) {
        //La méthode equalsIgnoreCase() compare deux chaînes en ignorant les différences entre minuscules et majuscules et renvoie « true » si les chaînes sont égales sinon renvoie « false »
        return (answer.equalsIgnoreCase(answers[currentQuestion]));
    }

    public void checkAnswer() {
        questionCount++;

        if (iscorrect(answer)) {
            nextQuestion();
            correctAnswer++;
            score.setText("score " + correctAnswer);
            resultView.setText("Correct Answer");
            resultView.setBackgroundColor(getResources().getColor(R.color.green));
            unCheckRadioButton();
        } else {
            nextQuestion();
            wrongAnswer++;
            resultView.setBackgroundColor(getResources().getColor(R.color.red));
            resultView.setText("Wrong Answer");
            unCheckRadioButton();
        }

        if (questionCount == 10) {

                Intent intent = new Intent(getApplicationContext(),congratulations.class);
                intent.putExtra("wrongAnswer",""+wrongAnswer);
                intent.putExtra("correctAnswer",""+correctAnswer);
                startActivity(intent);
                finish();

//intent.putExtra(name,value) : envoyer des valeurs vers congratulations
        }
    }

    private void nextQuestion() {

        currentQuestion++;
        currentOption1++;
        currentOption2++;
        currentOption3++;
        currentOption4++;
        /*dans la derniere question les pointeurs sont en pos 10 ,
        lorsque on click sur next question les pointeur devient 11
        => faux ==>  reinitialisation */

        if (currentOption1 == option1.length)
            currentOption1 = 0;
        if (currentOption2 == option2.length)
            currentOption2 = 0;
        if (currentOption3 == option3.length)
            currentOption3 = 0;
        if (currentOption4 == option4.length)
            currentOption4 = 0;
        if (currentQuestion == question.length)
            currentQuestion = 0;

        r1.setText(option1[currentOption1]);
        r2.setText(option2[currentOption2]);
        r3.setText(option3[currentOption3]);
        r4.setText(option4[currentOption4]);
        questionView.setText(question[currentQuestion]);


    }

//pour descocher les reponses avant de suivre la question suivante
    private void unCheckRadioButton() {
        r1.setChecked(false);
        r2.setChecked(false);
        r3.setChecked(false);
        r4.setChecked(false);

    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(getApplicationContext(), "Welcome Back", Toast.LENGTH_LONG).show();
        // ...
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(), "onResumed called", Toast.LENGTH_LONG).show();
        // ...
    }
}
















/*

 */