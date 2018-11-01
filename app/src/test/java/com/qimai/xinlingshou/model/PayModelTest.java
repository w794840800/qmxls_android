package com.qimai.xinlingshou.model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PayModelTest {

    @Before
    public void setUp() throws Exception {


    }

    @Test
    public void test1(){

        List mockList = mock(List.class);
        mockList.clear();

        when(mockList.get(0)).thenReturn("hello");
        //verify(mockList).add("one");
        verify(mockList).clear();
        mockList.add("one");
        System.out.println(mockList.get(0));



    }
}