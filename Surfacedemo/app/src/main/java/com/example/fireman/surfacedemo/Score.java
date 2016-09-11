package com.example.fireman.surfacedemo;

import java.util.Comparator;

/**
 * Created by Fireman on 2015/12/6.
 */
public class Score {

    private String _name;
    private int _score, _time;
    //private Uri _imageURI;
    private int _id;

    public Score (String name, int score) {
        _name = name;
        _score = score;
    }



    public static Comparator<Score> ConNameComparator = new Comparator<Score>() {

        public int compare(Score c1, Score c2) {
            int score1 = c1.getScore();
            int score2 = c2.getScore();

            //ascending order
            return score2 - score1;

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};



    public String getName() {
        return _name;
    }

    public int getScore() {
        return _score;
    }

    public int getTime() {
        return _time;
    }



    //public Uri getImageURI() { return _imageURI; }

}
