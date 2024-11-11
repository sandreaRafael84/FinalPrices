package com.grupobnc.itx.finalprices.common;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
public @interface RestApi {

	// just an alias to abstract us from Fw spring boot
	@AliasFor(annotation = RestController.class)
	String value() default "";

}
