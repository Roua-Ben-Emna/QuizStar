package com.nom.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class difficult extends AppCompatActivity {

    private int currentQuestion, currentOption1, currentOption2, currentOption3, currentOption4;
    private int  questionCount = 0,wrongAnswer=0,correctAnswer=0;
    private String[] question, answers, option1, option2, option3, option4;
    private Button answerButton,cancel;
    private TextView titre,questionView, resultView, answerview, score;
    private RadioButton r1, r2, r3, r4;
    private RadioGroup radioGroup;
    private String answer;


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
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            //on affecte a answer la reponse choisie par l'user
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton=findViewById(checkedId);
                //resultView.setText(radioButton.getText().toString());
                answer=radioButton.getText().toString();

            }
        });

        //verifier :  radio button ( "button next question" )
        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (r1.isChecked() || r2.isChecked() || r3.isChecked() || r4.isChecked()) {
                    checkAnswer();

                } else {
                    Toast.makeText(getApplicationContext(), "please select any option", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void init() {
        //tableau des questions
        question = new String[] {
                "1.For the purposes of defining data needs, a responsibility area is–",
                "2.The problem statement should include all of the following except–",
                "3. What is the name of first super computer of India ?",
                "4.A collection of 4 binary digits is known as–",
                "5. Which of the following is not a part of the CPU?",
                "6.  How many options does a binary choice offer?",
                "7. Who is also known as Father of Computer ?",
                "8.rojan-horse programs–",
                "9.What is the transfer rate of a standard USB 2.0 Device?",
                "10.How many common file type are there–" };
//tableau des reponses correctes
        answers = new String[] { "personnel", "storage", "PARAM 8000",
                "Nibble", "program Unit", "Two", "Charles Babbage",
                "are legitimate programs that allow unauthorized access", "480 Mbit/s",
                "six" };
//la 1ére option de chaque question
        option1 = new String[] { "marketing", "input", "Saga 220",
                "Half Bit", "Arithmetic and Logic Unit", "None", "Tim Berner Lee",
                "are legitimate programs that allow unauthorized access", "100 Mbit/s",
                "one" };
//la 2éme option de chaque question
        option2 = new String[] { "personnel", "output", "PARAM 8000",
                "1/2 KB", "storage Unit", "One", "Vint Cerf", "are hacker programs that do not show up on the system",
                "480 Mbit/s",
                "six" };
//la 3éme option de chaque question
        option3 = new String[] { "administration", "processing", "ENIAC",
                "Byte", "program Unit", "Two", "Charles Babbage",
                "really do not usually work", "1 Gbit/s",
                "five" };
////la 4éme option de chaque question
        option4 = new String[] { "finance", "storage", "Dual Core",
                "Nibble", "control Unit", "It depends on the amount of memory in the computer", "Charlie Babbage",
                "usually are immediately discovered", "250 Mbit/s", "two" };

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
        titre.setText("Level Difficult");
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

    public boolean iscorrect(String answer) {
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

            Intent intent = new Intent(getApplicationContext(), congratulations.class);
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
        /*on dernier question les pointeurs  sont en nb 10 alors lorsque
        on click sur next question pounteur devient 11
        et c'est faux alors il faux les reinitialiser */

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
