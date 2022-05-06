package com.example.itaem.untils;


/**
 * 邮件发送的格式:
 */
public class MailFormatUtil {

    /**
     * 用于发送验证码
     * @return content
     */
    public static String sendForActivation(String toWho, String code) {
        return "<html>\n"+"<body>\n" +
                "<h3>HEY @" + toWho + " 同学!</h3>\n" +
                "<h4>你刚刚在ITAEM官网上进行你的邮箱认证:</h4>\n" +
                "<h4>下面是你的验证码:</h4>\n" +
                "<h2 style='color: green'>\n" + code +
                "</h2>" +
                "<br>" +
                "*注意：当本邮箱被验证使用之后，你在ITAEM官网上的操作对你进行提醒哦" +
                "<hr>" +
                " \n如果不是本人的操作，请私信站长:wuyuanjian@stu.gdou.edu.cn" +
                "</body>\n"+"</html>";

    }

    /**
     * 用于登录验证
     * @return content
     */
    public static String sendWithCodeForLogin(String toWho, String code) {
        return "<html>\n"+"<body>\n" +
                "<h3>HEY @" + toWho + " 同学!</h3>\n" +
                "<h4>你刚刚在ITAEM官网上进行登录:</h4>\n" +
                "<h4>下面是你的登录验证码:</h4>\n" +
                code +
                "</h4>" +
                "<br>" +
                "<hr>" +
                " \n如果不是本人的操作，请私信站长:wuyuanjian@stu.gdou.edu.cn" +
                "</body>\n"+"</html>";
    }

    /**
     * 用于提示操作
     */
    public static String sendForReminding(String toWho, String OP, String remark) {
        return "<html>\n"+"<body>\n" +
                "<h3>HEY @" + toWho + " 同学!</h3>\n" +
                "<h4>你刚刚在ITAEM官网上进行了:" +
                OP +
                "操作</h4>\n" +
                "</h4>" +
                "<br>" +
                remark +
                "<hr>" +
                " \n如果不是本人的操作，请私信站长:wuyuanjian@stu.gdou.edu.cn" +
                "</body>\n"+"</html>";
    }




}
