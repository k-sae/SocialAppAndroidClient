package com.example.kemo.socializer.View.MainActivity.Authentication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import com.example.kemo.socializer.Control.ClientLoggedUser;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.SocialAppGeneral.LoginInfo;
import com.example.kemo.socializer.SocialAppGeneral.RegisterInfo;
import com.example.kemo.socializer.SocialAppGeneral.UserInfo;
import com.example.kemo.socializer.View.MainActivityFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class RegisterFragment extends MainActivityFragment implements DatePickerDialog.OnDateSetListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Calendar myCalendar;
    private EditText birthDate;
    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        myCalendar  = Calendar.getInstance();
        birthDate = (EditText) view.findViewById(R.id.register_birthDate_editText);
       birthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar myCalendar = Calendar.getInstance();
                new DatePickerDialog(getActivity(), RegisterFragment.this, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        final EditText name = (EditText) view.findViewById(R.id.register_name_editText);
        final EditText email = (EditText) view.findViewById(R.id.register_mail_editText);
        final EditText password = (EditText) view.findViewById(R.id.register_password_editText);
        view.findViewById(R.id.register_register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginInfo loginInfo = new LoginInfo();
                loginInfo.setEmail(email.getText().toString());
                loginInfo.setPassword(password.getText().toString());
                RegisterInfo registerInfo = new RegisterInfo();
                registerInfo.setLoginInfo(loginInfo);
                UserInfo userInfo= new UserInfo();
                userInfo.setFullName(name.getText().toString());
                registerInfo.setUserInfo(userInfo);
                new ClientLoggedUser.Register(registerInfo) {
                    @Override
                    public void onServerReply(final String reply) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(),reply,Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                };
            }
        });
        return view;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        // TODO Auto-generated method stub
        myCalendar.set(Calendar.YEAR, i);
        myCalendar.set(Calendar.MONTH, i1);
        myCalendar.set(Calendar.DAY_OF_MONTH, i2);
        updateLabel();
    }
    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        birthDate.setText(sdf.format(myCalendar.getTime()));
    }
}
