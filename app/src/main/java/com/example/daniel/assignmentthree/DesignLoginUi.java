package com.example.daniel.assignmentthree;

import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DesignLoginUi extends Fragment implements View.OnClickListener  {
    EditText edtName;
    EditText edtPasswrd;
    Button loginBtn;
    DataBase_Helper myDB;
    EditText edtUserName;
    EditText edtEmail;
    EditText edtSignPsword;
    Button signUpBtn;
    String userName;
    String password;
    String email;

    Cursor data;
    int user;
    int emAil;
    int pswrd;

    Controller controller;
    MainActivity mainActivity;

    public DesignLoginUi() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_design_login_ui, container, false);
        myDB = new DataBase_Helper(getActivity());
        mainActivity = new MainActivity();
        controller = MainActivity.controller;
        initialise(view);
        registerListener();
        return view;
    }


    private void initialise(View view) {
        edtName = (EditText) view.findViewById(R.id.et_id);
        edtPasswrd = (EditText) view.findViewById(R.id.et_pass);
        loginBtn = (Button) view.findViewById(R.id.LogInBtn);

        edtUserName = (EditText) view.findViewById(R.id.et_userName);
        edtSignPsword = (EditText) view.findViewById(R.id.et_signPassword);
        edtEmail = (EditText) view.findViewById(R.id.et_email);
        signUpBtn = (Button) view.findViewById(R.id.SignUpBtn);
    }

    private void registerListener() {
        loginBtn.setOnClickListener(this);
        signUpBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.LogInBtn:
                if (edtName.getText().toString().isEmpty() || edtPasswrd.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean bool = getUser(edtName.getText().toString(), edtPasswrd.getText().toString());

                    if (bool == true) {
                        controller.login();
                    } else {
                        Toast.makeText(getActivity(), "cannot identify the user", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), "please enter correct username and password or sign up if you havent", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.SignUpBtn:
                userName = edtUserName.getText().toString();
                email = edtEmail.getText().toString();
                password = edtSignPsword.getText().toString();
                if (userName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill in all the fields", Toast.LENGTH_SHORT);
                } else {
                    myDB.saveUser(userName, password, email);
                    controller.login();
                }
                break;
        }
    }

    public Boolean getUser(String name, String password) {

        data = myDB.getAllUsers();
        Boolean bool = null;
        if (data.getCount() == 0) {
            Toast.makeText(getActivity(), "There is no user in the database", Toast.LENGTH_LONG).show();
        }
        while (data.moveToNext()) {
            Log.d("getUser",data.getString(data.getColumnIndex(myDB.USER_ID_COL)));
            Log.d("getUser",data.getString(1));
            Log.d("getUser",data.getString(2));
            if ((name.matches(data.getString(data.getColumnIndex(myDB.USER_ID_COL))) ||
                    name.matches(data.getString((data.getColumnIndex(myDB.EMAIL_COL)))))
                    && (password.matches(data.getString((data.getColumnIndex(myDB.PASSWORD_COL)))))) {
                Toast.makeText(getActivity(),"matches",Toast.LENGTH_SHORT).show();
                bool = true;
                break;
            } else {
                bool = false;
            }
        }
        return bool;
    }

}

