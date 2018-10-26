package com.jskgmail.lifesaver;

import android.util.Log;

/**
 * Created by JASPREET SINGH on 18-10-2017.
 */

public class Friends {



    /**
     * Created by JASPREET SINGH on 03-08-2017.
     */


        //private variables
        int _id;
        String _name;
    String _name1;
    String _name2;
        // Empty constructor
      public Friends()
      {

      }
        // constructor
        public Friends(int id, String name,String _name1,String _name2){
            this._id = id;

            this._name = name;
            this._name1=_name1;
            this._name2=_name2;

        }

        // constructor
        public Friends(String name,String _name1,String _name2){


            this._name = name;
            this._name1=_name1;
            this._name2=_name2;

        }

        // getting ID
        public int getID(){
            return this._id;
        }

        // setting id
        public void setID(int id){
            this._id = id;
        }



        // getting name
        public String getName(){
            return this._name;
        }

        // setting name
        public void setName(String name){
            this._name = name;
            Log.i("sd","dsdsds");
        }
    public String getNameD(){
        return this._name1;
    }

    // setting name
    public void setNameD(String name1){
        this._name1 = name1;
        Log.i("sd","dsdsds");
    }
    public String getNameDD(){
        return this._name2;
    }

    // setting name
    public void setNameDD(String name2){
        this._name2 = name2;
        Log.i("sd","dsdsds");
    }

        // getting phone number



    }












