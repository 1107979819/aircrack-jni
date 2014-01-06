package org.tudelft.parse80211.processors;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

import org.tudelft.parse80211.annotations.FrameType;
import org.tudelft.parse80211.gen.DecoderClassGenerator;

@SupportedAnnotationTypes("org.tudelft.parse80211.annotations.Decoder")
public class DecoderProcessor extends AbstractProcessor
{

	@Override
	public boolean process(Set<? extends TypeElement> elements, RoundEnvironment environment)
	{
		note("Entering process: ", environment.processingOver());
		
		for (TypeElement annotation : elements)
			for (Element element : environment.getElementsAnnotatedWith(annotation))
				if (element.getKind()==ElementKind.CLASS)
					generateDecoder((TypeElement)element, environment);
		
		return false;
	}
	
	private void generateDecoder(TypeElement classElement, RoundEnvironment environment)
	{
		Filer filer = processingEnv.getFiler();
		
		try
		{

			DecoderClassGenerator gen = new DecoderClassGenerator(classElement);
			
			JavaFileObject jfo = filer.createSourceFile(gen.getClassName());
			
			PrintWriter writer = new PrintWriter(jfo.openWriter());
			
			// Collect template classes marked with the @FrameType annotation
			for (Element elem : environment.getElementsAnnotatedWith(FrameType.class))
				gen.addFrameType((TypeElement)elem);
			
			gen.generate(writer);
			
			writer.close();
			
		} catch (IOException e)
		{
			note(e);
		}
	}

	private void note(Object ... msg)
	{
		StringBuilder builder = new StringBuilder();
		
		for (Object obj : msg)
		{
			builder.append(obj.toString());
			builder.append(" ");
		}
		
		this.processingEnv.getMessager().printMessage(Kind.NOTE, builder.toString());
	}
	
}
