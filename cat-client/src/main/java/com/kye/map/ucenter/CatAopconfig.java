package com.kye.map.ucenter;

import com.dianping.cat.Cat;
import com.dianping.cat.annotation.CatAnnotation;
import com.dianping.cat.message.Transaction;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import java.lang.reflect.Method;
import org.springframework.context.annotation.Configuration;

/**
 cat监控切面
 */
@Aspect
@Configuration
public class CatAopconfig {

	@Around(value = "@annotation(com.dianping.cat.annotation.CatAnnotation)")
	public void aroundMethod(ProceedingJoinPoint pjp) {
		System.out.println("===============================进入到aop切面了================================");
		MethodSignature joinPointObject = (MethodSignature) pjp.getSignature();
		Method method = joinPointObject.getMethod();
		boolean flag = method.isAnnotationPresent(CatAnnotation.class);

		if (flag) {
			Transaction t = Cat.newTransaction("method", method.getName());

			try {
				pjp.proceed();
				t.setSuccessStatus();
			} catch (Throwable e) {
				t.setStatus(e);
				Cat.logError(e);
			} finally {
				t.complete();
			}
		} else {
			try {
				pjp.proceed();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

}
