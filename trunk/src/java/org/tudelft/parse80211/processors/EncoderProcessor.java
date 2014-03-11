package org.tudelft.parse80211.processors;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import org.tudelft.parse80211.annotations.FrameType;
import org.tudelft.parse80211.gen.EncoderClassGenerator;

@SupportedAnnotationTypes("org.tudelft.parse80211.annotations.Encoder")
public class EncoderProcessor extends Processor
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
		note("Generating Encoder class");
		
		Filer filer = processingEnv.getFiler();
		
		try
		{

			EncoderClassGenerator gen = new EncoderClassGenerator(classElement);
			
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

}
