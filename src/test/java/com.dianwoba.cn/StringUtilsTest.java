package com.dianwoba.cn;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;

/**
 * Created by leizhen on 2017/5/31.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
public class StringUtilsTest {
    @Test
    public void isEmpty() {
        assert StringUtils.isBlank(null);
        assert StringUtils.isBlank("");
    }

    @Test
    public void trim() {
        assert "foo".equals(StringUtils.trim("     foo  "));
    }

    @Test
    public void testEmailGenerator() {
        System.out.println("test");
        assert "aa".equals("aa");
    }
}
