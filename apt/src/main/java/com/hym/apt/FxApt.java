package com.hym.apt;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

public class FxApt extends AbstractProcessor
{
	@Override
	public synchronized void init( ProcessingEnvironment processingEnv )
	{
		super.init( processingEnv );
	}
	
	@Override
	public boolean process( Set<? extends TypeElement> annotations ,
			RoundEnvironment roundEnv )
	{
		return false;
	}
}
