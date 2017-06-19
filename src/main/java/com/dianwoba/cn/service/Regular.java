package com.dianwoba.cn.service;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by leizhen on 2017/6/5.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
public class Regular {

    private static final Logger l = Logger.getLogger(Regular.class.getName());

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("[Aa].*string");
        Matcher matcher = pattern.matcher("A String");
        boolean didMatch = matcher.matches();
        Logger.getAnonymousLogger().info(String.valueOf(didMatch));

        String line = "This order was placed for QT3000! OK?";

        Pattern r = Pattern.compile("(\\D*)(\\d+)(.*)");

        Matcher m = r.matcher(line);
        m.pattern();

        if (m.find()) {
            System.out.println("Found value: " + m.group());
//            System.out.println("Found value: " + m.group(1));
//            System.out.println("Found value: " + m.group(2));
//            System.out.println("Found value: " + m.group(3));
        } else {
            System.out.println("No Match");
        }

        pattern = Pattern.compile("\\d+");
        String[] str = pattern.split("I am:123,and my phone is1233");
        for (String item : str) {
            System.out.println(item);
        }

        System.out.println(Pattern.matches("\\d+", "2233"));

        pattern = Pattern.compile("\\d+");
        matcher = pattern.matcher("22bb33");
        System.out.println(matcher.matches());
        System.out.println(matcher.lookingAt());
        System.out.println(matcher.find());

        matcher = pattern.matcher("2223bb");
        System.out.println(matcher.find());

        pattern = Pattern.compile("([a-z]+)(\\d+)");
        matcher = pattern.matcher("aaa222bbb");
        System.out.println(matcher.find());
        System.out.println(matcher.groupCount());
        System.out.println(matcher.group(0));
        System.out.println(matcher.group(1));
        System.out.println(matcher.group(2));

        pattern = Pattern.compile("\\d+");
        matcher = pattern.matcher("my name is:2346 my phone is:4644 my email is:asdf");
        while (matcher.find()) {
            System.out.println(matcher.group());
        }

        pattern = Pattern.compile(".*");
        matcher = pattern.matcher("adfasd");
        while (matcher.find()) {
            System.out.println(matcher.group());
        }

        pattern = Pattern.compile("a{2,5}");
        matcher = pattern.matcher("ssaaabxc");
        matcher.find();
        l.info(matcher.group());
        l.info(String.valueOf(matcher.start()));
        l.info(String.valueOf(matcher.end()));


        Pattern p = Pattern.compile("cat");
        matcher = p.matcher("one cat two cats in the yard");
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "dog");
            l.info(sb.toString());
        }
        matcher.appendTail(sb);
        l.info(sb.toString());
        l.info(matcher.replaceAll("pig"));
    }
}
