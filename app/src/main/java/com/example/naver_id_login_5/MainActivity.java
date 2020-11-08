package com.example.naver_id_login_5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

public class MainActivity extends AppCompatActivity {

    private static String OAUTH_CLIENT_ID = "lJzNQOK_TmyT9y5SNeFI";
    private static String OAUTH_CLIENT_SECRET = "VMXCZ6hgUx";
    private static String OAUTH_CLIENT_NAME = "naver_id_login_5";

    public static OAuthLoginButton mOAthLoginButton;
    public static OAuthLogin mOAthLoginInstance;

    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // context 저장
        mContext = MainActivity.this;

        // 1. 초기화
        mOAthLoginInstance = OAuthLogin.getInstance();
        mOAthLoginInstance.showDevelopersLog(true);
        mOAthLoginInstance.init(mContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);

        // 2. 로그인 버튼 세팅
        mOAthLoginButton = (OAuthLoginButton) findViewById(R.id.buttonOAuthLoginImg);
        mOAthLoginButton.setOAuthLoginHandler(mOAthLoginHandler);
    }

    private OAuthLoginHandler mOAthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            // 로그인 인증 성공
            if (success) {
                // 사용자 정보 가져오기
                String accessToken = mOAthLoginInstance.getAccessToken(mContext);
                String refreshToken = mOAthLoginInstance.getRefreshToken(mContext);
                long expriresAt = mOAthLoginInstance.getExpiresAt(mContext);
                String tokenType = mOAthLoginInstance.getTokenType(mContext);

                redirectSecondActivity();

            } else {
                String errorCode = mOAthLoginInstance.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAthLoginInstance.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        }
    };

    protected void redirectSecondActivity() {
        final Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
        finish();
    }
}