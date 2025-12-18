package com.tracker.zen.component;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

	@Autowired
	private LogModuleInfo logInfo;

	@Autowired
	CommonExtends commonExtends = new CommonExtends();

	// メソッド実行前にログを出力
	@Before("execution(* com.kuyu.controller.*.*(..))")
	public void logStart(JoinPoint joinPoint) {

		String className = joinPoint.getSignature().getDeclaringTypeName();
		String methodName = joinPoint.getSignature().getName();
		logInfo.LOGIC_START(className, methodName); // 開始ログを出力
	}
}
