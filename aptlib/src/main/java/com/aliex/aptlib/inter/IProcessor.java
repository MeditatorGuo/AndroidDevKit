package com.aliex.aptlib.inter;


import javax.annotation.processing.RoundEnvironment;

import com.aliex.aptlib.AnnotationProcessor;

/**
 * 注解处理器接口
 */

public interface IProcessor {
    void process(RoundEnvironment roundEnv, AnnotationProcessor mAbstractProcessor);
}
