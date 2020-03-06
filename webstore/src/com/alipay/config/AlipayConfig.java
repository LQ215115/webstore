package com.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016101800717696";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQChKmGpErIPtaIN92mnSW1MX9hrrWAZdx8rUFokTn07mI6R4+lNUOpeH0o5L27s/zz6lSWg63I0AcFjTlesc3xnuBzDRwujfBbBg1Q3/nghyRwmlC0yyQkggCPZHYKpv1LBXcjbH5XHtG5Pr2ArbPuO9jx06+NSqWCEikZqkhV1h91CTdVwbXdEweTR6AT5IjVsL0yJwWuGzUzUFS6+Ncv+p7y0RKYKurgF7gF/kd58Qg6j7sGsk7o8LSo5/82uvND64B9EEOGyirzPhs0FiPRF8hPXfuRX46ef64V3Bhq73ujfotw5aGaYcG1dvHNLG3UpjQobwFkzHha7ViAjX6+lAgMBAAECggEAV3MwxNYxKlcLVPrAKJauKPgE7gCImajKj+0szHCOgYz5J258xIg4c9QUqUz0qsKgavQNE/FN6axeLC1xY1DS21DKwqdFSHNnwZ8qTqvUsbh3fvN+RwVQ34LIcOITaVo4ZFYoS8Yqts9P4Aswar/bDCrOiT8J/WuMxL5nESKtZDT1STONOuYWdVtAdvtKhoLbXvfzgM37qFAaEYUvNh9xXhD6RFQxFCTFSNYXPapQa1SoZ1vNpBy6MDflejsUvfJ8dDx1H9AqghJmZ+xY34/X3be1A7VlvrKi0fqKWPzf9hSwpSm41brZ17N546RfzKXYM5QbH2s6pfeaxjc3t7MprQKBgQDMGunDOHzntu1Ut+rYaxiFOBO93ZqJzC2nw1dGzkNXSPsSmQSHBZK9b/upR36KFVSNhozf3R9gN7bgfrTQjwvxnC6jtYpV247ef0nv0tQYzEpSkpK64O1kpCZIqWkzQnZ8QS9zmeZqMkSgGZv12Qln1m/bfGRbHN2zSM/2NXD0ewKBgQDKJI5Cw79IH5TPjQlLGA8/Jz+DLXZRebD4RAoSgI3IcY5XH19EkwpS0Xi9gmDnh0se6ERz9ocH1dAGQzuei2KAoJBX8RzFQahkzQhMlldctoZpXiFNDFRC2XcRzOQz03nPxTqHu8QEbdmble7sJJOytYclFIasn26QZgi3XeQCXwKBgGkf94ToZFUIf+bhr6D2UDPVdT2WlREiS+WblClQ9ZhQa5jhnVZaBYFy2yEORNukLt+SDJ8FCjMJnzSzrje5ZcJ02gTR7UVZh5WL5C4U3c9D8YfkHJUKYtsyPNGyCBAxzEslW+Zjvyy1ZWk8p3qR5elIpmZbBGl1Ta8Y8pvyXJJfAoGBAMkcCXUfLsVgtjqwBJjzfOfF8ohdyKNSwOCfQK4bu/O6FnVTPwMIj3LJ+KgCeMqgbfp39vKwSLNg46/3X+9imUODe1gjCA6wPgrbefBIeu5AogVeox72rzY7CR1qjXeKZFxzysGT63bEm4URbD0APvnDZItdmodPrz52ZSGZheldAoGAVJ503psdmgoYzmYUErQVtxbIny1hG0kX1ftaa0YkC8z7IBGwmpbLOacOT1AkMGG8TnyaYveFViwMAr6WVcOPWUZuZuRIVTOyX6mxG7a8izKBAberQm24W1tY7YVg+3yisbG2ol9bGtr7n5vpXonK6lLRlojuxf1lFGOUy4OXHMU=";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjlDWxRM2LNAYnEyYu1S/NNHJUiKD9tAOhULOK0GbCIoiHwn+EZj1Jitv6Hy9nKdQfa/pHb7mHJd5RP2VSK2RFxgGi+BaYNHqfJGZZmBNOXgdRFMoqeMdJDZs24jqN9FF/7bBSsTDf3KcjkdYHiGMclqBlNaRO8lzMG6q1tRknZoJBiEft1tSoFMm1VE5MrbkFCNIAnZVnAhka7FBu/gXrXbbTX9BY6rRrBYF0KA+S8A6i5X1f2yC0YkT9SgRxO2gsqquOtjgRxCOtCke61zt/ctUXef2VqpkMDotgSNgfYQXAp+R+D7hiAGB8AkXnoY+LHsY+UlztUAUhRJhqOqwGwIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://29675ps673.zicp.vip/AliPayServlet";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://29675ps673.zicp.vip/AliPayServlet";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

