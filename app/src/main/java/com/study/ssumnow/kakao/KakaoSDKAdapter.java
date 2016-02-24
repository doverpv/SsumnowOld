package com.study.ssumnow.kakao;

import android.app.Activity;
import android.content.Context;

import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;

/**
 * @author leoshin on 15. 9. 15.
 */
public class KakaoSDKAdapter extends KakaoAdapter {

    /**
     * Session Config????ï¥?úÎäî defaultÍ∞íÎì§??Ï°¥Ïû¨?úÎã§.
     * ?ÑÏöî???ÅÌô©?êÏÑúÎß?override?¥ÏÑú ?¨Ïö©?òÎ©¥ ??
     * @return Session???§Ï†ïÍ∞?
     */
    @Override
    public ISessionConfig getSessionConfig() {
        return new ISessionConfig() {
            @Override
            public AuthType[] getAuthTypes() {
                return new AuthType[] {AuthType.KAKAO_LOGIN_ALL};
            }

            @Override
            public boolean isUsingWebviewTimer() {
                return false;
            }

            @Override
            public ApprovalType getApprovalType() {
                return ApprovalType.INDIVIDUAL;
            }

            @Override
            public boolean isSaveFormData() {
                return true;
            }
        };
    }

    @Override
    public IApplicationConfig getApplicationConfig() {
        return new IApplicationConfig() {
            @Override
            public Activity getTopActivity() {
                return GlobalApplication.getCurrentActivity();
            }

            @Override
            public Context getApplicationContext() {
                return GlobalApplication.getGlobalApplicationContext();
            }
        };
    }
}
