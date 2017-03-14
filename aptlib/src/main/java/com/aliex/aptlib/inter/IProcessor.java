package com.aliex.aptlib.inter;


import com.aliex.aptlib.AnnotationProcessor;

import javax.annotation.processing.RoundEnvironment;

/**
 * 注解处理器接口
 */

public interface IProcessor {
    void process(RoundEnvironment roundEnv, AnnotationProcessor mAbstractProcessor);
}
