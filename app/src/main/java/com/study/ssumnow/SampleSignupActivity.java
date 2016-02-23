/**
 * Copyright 2014 Daum Kakao Corp.
 *
 * Redistribution and modification in source or binary forms are not permitted without specific prior written permission.¬†
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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.study.ssumnow.kakao.BaseActivity;
import com.study.ssumnow.kakao.ExtraUserPropertyLayout;
import com.study.ssumnow.kakao.KakaoServiceListActivity;
import com.study.ssumnow.kakao.KakaoToast;
import com.study.ssumnow.kakao.Logger;

import java.util.Map;

/**
 * ?†Ìö®???∏ÏÖò???àÎã§??Í≤?¶ù ?? * meÎ•??∏Ï∂ú?òÏó¨ Í∞?ûÖ ?¨Î????∞Îùº Í∞?ûÖ ?òÏù¥Ïß?? Í∑∏Î¶¨?òÏ? Main ?òÏù¥Ïß?°ú ?¥Îèô ?úÌÇ®??
 */
public class SampleSignupActivity extends BaseActivity {
    /**
     * Main?ºÎ°ú ?òÍ∏∏Ïß?Í∞?ûÖ ?òÏù¥Ïß?? Í∑∏Î¶¥Ïß??êÎã®?òÍ∏∞ ?ÑÌï¥ meÎ•??∏Ï∂ú?úÎã§.
     * @param savedInstanceState Í∏∞Ï°¥ session ?ïÎ≥¥Í∞???û•??Í∞ùÏ≤¥
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestMe();
    }

    protected void showSignup() {
        setContentView(R.layout.layout_usermgmt_signup);
        final ExtraUserPropertyLayout extraUserPropertyLayout = (ExtraUserPropertyLayout) findViewById(R.id.extra_user_property);
        Button signupButton = (Button) findViewById(R.id.buttonSignup);
        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                requestSignUp(extraUserPropertyLayout.getProperties());
            }
        });
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
                KakaoToast.makeToast(self, message, Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
            }
        }, properties);
    }

    /**
     * ?¨Ïö©?êÏùò ?ÅÌÉúÎ•??åÏïÑ Î≥¥Í∏∞ ?ÑÌï¥ me API ?∏Ï∂ú???úÎã§.
     */
    protected void requestMe() {
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    KakaoToast.makeToast(getApplicationContext(), getString(R.string.error_message_for_service_unavailable), Toast.LENGTH_SHORT).show();
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
                redirectMainActivity();
            }

            @Override
            public void onNotSignedUp() {
                showSignup();
            }
        });
    }

    private void redirectMainActivity() {
        startActivity(new Intent(this, KakaoServiceListActivity.class));
        finish();
    }
}
