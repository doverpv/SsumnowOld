/**
 * Copyright 2014 Daum Kakao Corp.
 *
 * Redistribution and modification in source or binary forms are not permitted without specific prior written permission.Β 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.study.ssumnow;

import android.content.Intent;
import android.os.Bundle;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;
import com.study.ssumnow.kakao.BaseActivity;

/**
 * ?ν?μ ?¬μ©?κ² ??λ‘κ·Έ???μ΄μ§? * ?Έμ???€ν????action??override?΄μ ?¬μ©?λ€.
 *
 * @author MJ
 */
public class SampleLoginActivity extends BaseActivity {
    private SessionCallback callback;

    /**
     * λ‘κ·Έ??λ²νΌ???΄λ¦­ ?μ??access token???μ²­?λλ‘??€μ ?λ€.
     *
     * @param savedInstanceState κΈ°μ‘΄ session ?λ³΄κ°???₯??κ°μ²΄
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        if (!Session.getCurrentSession().checkAndImplicitOpen()) {
            setContentView(R.layout.layout_common_kakao_login);
        }
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
            redirectSignupActivity();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Logger.e(exception);
            }

            setContentView(R.layout.layout_common_kakao_login);
        }
    }
}
