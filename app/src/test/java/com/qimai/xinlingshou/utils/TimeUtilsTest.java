package com.qimai.xinlingshou.utils;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TimeUtilsTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getCurrentPhpTimeStamp() {

        long timeStampSec = System.currentTimeMillis();
        String timestamp = String.format("%010d", timeStampSec/1000);

        System.out.println("timeStampSec= "+timeStampSec+"  timestamp= "+timestamp);
    }
}