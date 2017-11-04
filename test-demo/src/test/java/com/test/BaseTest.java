package com.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author jinyouming
 * @email  jinyouming@tuandai.com
 * @date   2017-11-4 下午5:40:47
 * @Copyright Copyright (c) 2017 Niiwoo Inc. All Rights Reserved.
 * @desc 测试基类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-context.xml"})
public abstract class BaseTest extends AbstractJUnit4SpringContextTests {

}

