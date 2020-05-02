package com.cheirmin.config;

import org.springframework.context.annotation.Configuration;

/**
 * @author
 * @createTime 2019.12.09.16:57
 */

@Configuration
public class AliPayConfig {
    public final String APP_ID = "2016101500695918";
    public final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC+yJ5U1SCFV/Zjtypp79b1YsOKK3F7zO7l2JzeTv2+jHrlNNTde8ZZPW9sYqVWLURL+eCPQql3loqqmyEDEpjr20u1iEQwl9aaDuujosXH5/vdf/CQcTqsrInpti25QGNI29cC2Skx8No/NzPnBAJUmHMIU8b9FVAjSwad7izWjBnDNJPCpo2VXG44Lbsuzgt0Z+0giz3XFcOypONmvM+s9RNcdcxqEisadEfAd9fCI2vimRS51yiyMkv7AKLHMa+xcg4vp+3+Lv9ax/E+Kn9IYp1f5fYwmCedfX4wvDBnP8JWl2a/5tw9Ij8gpIiYMWGwTmvFhPHklYtqx2orBU0dAgMBAAECggEBAJR6FZ7370NXXc3k8wFXQvfeUUfq33r11sbSeeMZnqj5M3vGThPBDHDPOzQi4YHb4NcwNO/JdZI4etqjuGFny9Ni69zvnAIlaUKkcgb72ujlRkLwGwyO0pcoAHz+5z7ROXOug/sXftP7Gh8wmxgjKp0juAK3EJpOR/vvm8q1Xffp35cHNATVw5U9GL1k0sDW7ugzctwJNsENJxt8qFeNdSarPdKxHXjCOaAu26c6tcUxHBtmY8qsduQLe8jgEvH9kyftDZqCWdeTtXR0fkc5lLSShrSPIAzksOSE2T1dNMuRlA2vI49OdIyZ21Tj1aNslrIaQD5Je+GH78j8BJk/2QECgYEA8Z67qU01uQ4rPe/GIpGxhtM0Wd9WSJUIN6abJBTKvaLcWzQUj2jQcQLEzxgSnlAoC8wjTCQq883w/rLiScvq5+xC4CwfacQ3VPTf3qmp/RXEQJMbnzyYaDjGrExwdEUTdPvPyDKtLXgPHx00u8VNeCuyjZOdjPSe1sVWZHRP/40CgYEAyiNapQ2IuDRkS1qgabSxeDTa3DyWvQgNJQyuhNrw5wi/21KbVwPeRjeGX/PppXWPrTNMSeoYNlWoRuU9oNOFjg/UOVG7nD+uevSkqS2RkwLmV3bP5dwvI988BWC4TLkeeJqWi5Y6zsbuBfrjXq+VgFyr1ly9PfkeocT2kMzAF9ECgYBMr1RgJ2ElmpmezrrTvenaIK481+VKpAI3p2kVyoOOLGJDkAP2gA2n0HkmQT2ngUk0dVW+lLwNJQ+Xxq6LPW0QrquK+nX924i0LgPfZ3nMGVxwIkBiCZlVeq+Tf68xN7s1JbaMk4jjJXenKF3UOoqb2UKSLIeUdQ70my0QNNovAQKBgDdO7SJ0TZB5ZWfo6DOEKXsGm0vfmC8o4M/eZHIdpgTRNA3C0JZCjLMRxkbC6o9HV4TAoIMo9m8HiP+9AdvbhJOWaS812euq0Qb7oYJY4ZgkFvqfm8r9MDjUgAvIpyT3EgtV4RNYi8bAT6h7OXNS/8kbkjkZLv6iB2ukBQMhwv3BAoGAB3vJEivChnTy8O7Cst1c7kukcMGyJhtVqRknSFaUdIG2V8EqPYcO5okfwXVvb+o0qXPaPXDPtexs5l0j1o/89u7Paci7gJzBhEg6X2VSMzFMOgaIx3GCQmY+WG9RCXqnUO1DhqGlFYRNbijgZ4piWt2REHBnPe3mroPhv/FtPJw=";
    public final String CHARSET = "UTF-8";
    public final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlvsvAg6CjLHP+DusXNv8AXmX7iuoWn/YkS+RniaGj/8VciRuv4aoDCN8F8VyYVMwEzlfklCAg1gydJoR4vSV189o9Ch/feMz8vlt9hkCGa0o8cOR0IPDydzzV1INfAZDEpiAKcPMmMG9GVbU6lTk7CAj4WGgCSyjtDZT5wIKTGMP9X9Mi53ZIhcrm71ZpARAUJvSA4M7MO3HJInbDPG2nh4RPCtpupH+hSuhg46bKC/zo7XrCK41Jgx6uag3xxhdfO3chlSG/8nhh0xkUdG0H2gQ4zzFc2lp0zAGDUwZuawyG2fFc/fmpS7Z02ZhuAf8SSK+H0vX7YRnrfnYngsCeQIDAQAB";
    //这是沙箱接口路径,正式路径为https://openapi.alipay.com/gateway.do
    public final String GATEWAY_URL ="https://openapi.alipaydev.com/gateway.do";
    public final String FORMAT = "JSON";
    //签名方式
    public final String SIGN_TYPE = "RSA2";
    //支付宝异步通知路径,付款完毕后会异步调用本项目的方法,必须为公网地址
    public final String NOTIFY_URL = "http://127.0.0.1/notifyUrl";
    //支付宝同步通知路径,也就是当付款完毕后跳转本项目的页面,可以不是公网地址
    public final String RETURN_URL = "http://127.0.0.1/returnUrl";
}
