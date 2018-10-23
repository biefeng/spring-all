package com.cloud;

import org.apache.shiro.codec.Base64;
import org.bouncycastle.jcajce.provider.digest.MD5;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author : BieFeNg
 * @DATE : 2018/10/9 0:04
 */
public class SimpleTest {
    private static final Pattern passPattern = Pattern.compile("^(?:(?=\\w*[a-z])(?=\\w*[A-Z])(?=\\w*[a-zA-Z0-9])).{8,}$");

    @Test
    public void test3() {
        Matcher matcher = passPattern.matcher("ieAddddddddddd");
        System.out.println(matcher.matches());
    }

    @Test
    public void testDecode() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String str = "2018101820181018";

        System.out.println(new String(Base64.encode(str.getBytes())));
    }

    @Test
    public void name() throws UnsupportedEncodingException {
        System.out.println(new String(Base64.decode("biedsadsadsadsadsa".getBytes("utf-8"))));
    }

    @Test
    public void test2() {
        String str="select  \n             " +
                " username ,password,passwordsalt from shiro_user where username=?";
        System.out.println(str.replaceAll("\\?","{}").replaceAll("[\\n\\s\\t]+"," "));
    }
}
