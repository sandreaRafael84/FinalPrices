package com.grupobnc.itx.checkprices.common;

import org.springframework.stereotype.Component;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface UseCase {

	// just an alias to abstract us from Fw spring boot
	@AliasFor(annotation = Component.class)
	String value() default "";

}
