package com.aliex.aptlib;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import com.aliex.aptlib.processor.ApiFactoryProcessor;
import com.aliex.aptlib.processor.InstanceProcessor;
import com.aliex.aptlib.processor.RouterProcessor;
import com.google.auto.service.AutoService;

@AutoService(Processor.class) // 自动生成 javax.annotation.processing.IProcessor 文件
@SupportedSourceVersion(SourceVersion.RELEASE_8) // java版本支持
@SupportedAnnotationTypes({ // 标注注解处理器支持的注解类型
        "com.apt.annotation.apt.InstanceFactory", "com.apt.annotation.apt.ApiFactory",
        "com.apt.annotation.apt.Router" })
public class AnnotationProcessor extends AbstractProcessor {
    public Filer mFiler; // 文件相关的辅助类
    public Elements mElements; // 元素相关的辅助类
    public Messager mMessager; // 日志相关的辅助类

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
        mElements = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        new InstanceProcessor().process(roundEnv, this);
        new RouterProcessor().process(roundEnv, this);
        new ApiFactoryProcessor().process(roundEnv, this);
        return true;
    }
}
