package com.study.ssumnow;

import android.content.Intent;
import android.os.Bundle;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.usermgmt.LoginButton;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

//[reference]You want to start a new activity and destroy the previous one? If this is what you need, you can use: startActivity(new Intent(this, myActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

public class MainActivity extends BaseActivity {

    private Session session;
    private LoginButton loginButton;
    private SessionCallback callback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //KakaoSDK.init(new KakaoSDKAdapter());

        callback = new SessionCallback();
        session = session.getCurrentSession();
        session.addCallback(callback);

        if (!Session.getCurrentSession().checkAndImplicitOpen()) {
            setContentView(R.layout.kakao_login);
        }

        loginButton = (LoginButton)findViewById(R.id.com_kakao_login);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            MainActivity.this.onSessionOpened();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Logger.e(exception);
            }

            setContentView(R.layout.kakao_login);
        }
    }

    protected void onSessionOpened() {
        Intent homeIntent = new Intent(this, HomeActivity.class);
        startActivity(homeIntent);
        finish();
    }
}
