package com.jimmy.designpatterns.activity.test;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jimmy.designpatterns.R;
import com.jimmy.designpatterns.databinding.EncryptBinding;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by Jimmy on 2017/10/29.
 */

public class EncryptDemoActivity extends Activity implements View.OnClickListener {
    /* 密钥内容 base64 code */
    private static String PUCLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCfRTdcPIH10gT9f31rQuIInLwe"
            + "\r" + "7fl2dtEJ93gTmjE9c2H+kLVENWgECiJVQ5sonQNfwToMKdO0b3Olf4pgBKeLThra" + "\r"
            + "z/L3nYJYlbqjHC3jTjUnZc0luumpXGsox62+PuSGBlfb8zJO6hix4GV/vhyQVCpG" + "\r"
            + "9aYqgE7zyTRZYX9byQIDAQAB" + "\r";
    private static String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ9FN1w8gfXSBP1/"
            + "\r" + "fWtC4gicvB7t+XZ20Qn3eBOaMT1zYf6QtUQ1aAQKIlVDmyidA1/BOgwp07Rvc6V/" + "\r"
            + "imAEp4tOGtrP8vedgliVuqMcLeNONSdlzSW66alcayjHrb4+5IYGV9vzMk7qGLHg" + "\r"
            + "ZX++HJBUKkb1piqATvPJNFlhf1vJAgMBAAECgYA736xhG0oL3EkN9yhx8zG/5RP/" + "\r"
            + "WJzoQOByq7pTPCr4m/Ch30qVerJAmoKvpPumN+h1zdEBk5PHiAJkm96sG/PTndEf" + "\r"
            + "kZrAJ2hwSBqptcABYk6ED70gRTQ1S53tyQXIOSjRBcugY/21qeswS3nMyq3xDEPK" + "\r"
            + "XpdyKPeaTyuK86AEkQJBAM1M7p1lfzEKjNw17SDMLnca/8pBcA0EEcyvtaQpRvaL" + "\r"
            + "n61eQQnnPdpvHamkRBcOvgCAkfwa1uboru0QdXii/gUCQQDGmkP+KJPX9JVCrbRt" + "\r"
            + "7wKyIemyNM+J6y1ZBZ2bVCf9jacCQaSkIWnIR1S9UM+1CFE30So2CA0CfCDmQy+y" + "\r"
            + "7A31AkB8cGFB7j+GTkrLP7SX6KtRboAU7E0q1oijdO24r3xf/Imw4Cy0AAIx4KAu" + "\r"
            + "L29GOp1YWJYkJXCVTfyZnRxXHxSxAkEAvO0zkSv4uI8rDmtAIPQllF8+eRBT/deD" + "\r"
            + "JBR7ga/k+wctwK/Bd4Fxp9xzeETP0l8/I+IOTagK+Dos8d8oGQUFoQJBAI4Nwpfo" + "\r"
            + "MFaLJXGY9ok45wXrcqkJgM+SN6i8hQeujXESVHYatAIL/1DgLi+u46EFD69fw0w+" + "\r" + "c7o0HLlMsYPAzJw="
            + "\r";
    private static final int AES = 0;
    private static final int RSA = 1;
    private EncryptBinding binding;
    private int type = AES;
    private String encryptedContent;
    private String afterencrypt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_encrypt);
        binding.setOnClick(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left:
                encrypt();
                break;
            case R.id.btn_right:
                unEncrypt();
                break;
            case R.id.btn_change_type:
                if (type == AES) {
                    type = RSA;
                } else {
                    type = AES;
                }
                break;
        }
    }

    //解密
    private void unEncrypt() {
        if (type == AES) {
            try {
                String decrypt = Utils.decrypt("key", encryptedContent);
                binding.tvShow.setText("AES解密后： " + decrypt);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (type == RSA) {
            try
            {
                // 从字符串中得到私钥
                 PrivateKey privateKey = Utils.loadPrivateKey(PRIVATE_KEY);
                // 从文件中得到私钥
//                InputStream inPrivate = getResources().getAssets().open("pkcs8_rsa_private_key.pem");
//                PrivateKey privateKey = RSAUtils.loadPrivateKey(inPrivate);
                // 因为RSA加密后的内容经Base64再加密转换了一下，所以先Base64解密回来再给RSA解密
                byte[] decryptByte = Utils.decryptData(Utils.decode(afterencrypt), privateKey);
                String decryptStr = new String(decryptByte);
                binding.tvShow.setText(decryptStr);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    //加密
    private void encrypt() {
        if (type == AES) {
            try {
                encryptedContent = Utils.encrypt("key", binding.etContent.getText().toString());
                binding.tvShow.setText("AES加密后： " + encryptedContent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (type == RSA) {

            try {
                PublicKey publicKey = Utils.loadPublicKey(PUCLIC_KEY);
//            InputStream inPublic = getResources().getAssets().open(" _public_key.pem");
//            PublicKey publicKey = Utils.loadPublicKey(inPublic);
                // 加密
                byte[] encryptByte = Utils.encryptData(binding.etContent.toString().getBytes(), publicKey);
                // 为了方便观察吧加密后的数据用base64加密转一下，要不然看起来是乱码,所以解密是也是要用Base64先转换
                afterencrypt = Utils.encode(encryptByte);
                binding.tvShow.setText(afterencrypt);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
