package com.test.demo.elasticsearch;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author jinyouming
 * @email  jinyouming@tuandai.com
 * @date   2017-11-4 下午6:07:29
 * @Copyright Copyright (c) 2017 Niiwoo Inc. All Rights Reserved.
 * @desc
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ESearchTypeColumn {

    /**
     * 字段类型
     *
     * @return
     */
    String type() default "string";
  
    /**
     * 是否分词
     *
     * @return
     */
    boolean analyze() default false;
    
}

