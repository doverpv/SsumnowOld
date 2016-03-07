package com.study.ssumnow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hhylu on 2016-03-06.
 */
public class SignUpActivity extends BaseActivity {
    protected static final String NAME_KEY = "name";
    protected static final String AGE_KEY = "age";
    protected static final String GENDER_KEY = "gender";
    protected static final String PROFILEIMAGE_KEY = "profile image";

    private EditText name;
    private EditText age;
    private RadioGroup gender;

    private UserProfile userProfileValue;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestMe();
    }

    protected void showSignup() {
        setContentView(R.layout.user_signup);

        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        gender = (RadioGroup) findViewById(R.id.gender);
        Button signupButton = (Button) findViewById(R.id.buttonSignup);

        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                requestSignUp(getProperties());
            }
        });
    }

    private HashMap<String, String> getProperties(){
        final String nickNameValue = name.getText().toString();
        final String ageValue = age.getText().toString();

        int genderID = gender.getCheckedRadioButtonId();
        final String genderValue;
        switch (genderID) {
            case (R.id.male):
                genderValue = "male";
                break;
            case (R.id.female):
                genderValue = "female";
                break;
            default:
                genderValue = null;
                break;
        }

        HashMap<String, String> properties = new HashMap<String, String>();
        if(nickNameValue != null)
            properties.put(NAME_KEY, nickNameValue);
        if(ageValue != null)
            properties.put(AGE_KEY, ageValue);
        if(genderValue != null)
            properties.put(GENDER_KEY, genderValue);

        return properties;
    }

    private void requestSignUp(final Map<String, String> properties) {
        UserManagement.requestSignup(new ApiResponseCallback<Long>() {
            @Override
            public void onNotSignedUp() {
            }

            @Override
            public void onSuccess(Long result) {
                requestMe();
            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                final String message = "UsermgmtResponseCallback : failure : " + errorResult;
                com.kakao.util.helper.log.Logger.w(message);
                Toast.makeText(self, message, Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
            }
        }, properties);
    }

    /**
     * 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
     */
    protected void requestMe() {
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_message_for_service_unavailable), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    redirectLoginActivity();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                redirectLoginActivity();
            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                Logger.d("UserProfile : " + userProfile);
                System.out.println("vincent : requestME onSuccess");

                userProfileValue = userProfile;
                redirectMainActivity();
            }

            @Override
            public void onNotSignedUp() {
                System.out.println("vincent : requestME showSignup");
                showSignup();
            }
        });
    }

    //Login/SignUp completed, run HomeActivity
    private void redirectMainActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        Map<String, String> map = userProfileValue.getProperties();
        HashMap<String, String> hashMap = new HashMap<>(map);

        String profileImage = userProfileValue.getProfileImagePath();
        String name = hashMap.get(NAME_KEY);
        String age = hashMap.get(AGE_KEY);
        String gender = hashMap.get(GENDER_KEY);

        intent.putExtra(PROFILEIMAGE_KEY, profileImage);
        intent.putExtra(NAME_KEY, name);
        intent.putExtra(AGE_KEY, age);
        intent.putExtra(GENDER_KEY, gender);

        startActivity(intent);

        finish();
    }
}
