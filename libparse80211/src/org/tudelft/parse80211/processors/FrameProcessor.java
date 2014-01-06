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

import org.tudelft.parse80211.annotations.Mapped;
import org.tudelft.parse80211.annotations.Bit;
import org.tudelft.parse80211.annotations.Bits;
import org.tudelft.parse80211.annotations.U16;
import org.tudelft.parse80211.annotations.U8;
import org.tudelft.parse80211.gen.BitFieldGenerator;
import org.tudelft.parse80211.gen.BitsFieldGenerator;
import org.tudelft.parse80211.gen.ClassGenerator;
import org.tudelft.parse80211.gen.FrameClassGenerator;
import org.tudelft.parse80211.gen.MappedFieldGenerator;
import org.tudelft.parse80211.gen.U16FieldGenerator;
import org.tudelft.parse80211.gen.U8FieldGenerator;

@SupportedAnnotationTypes("org.tudelft.parse80211.annotations.FrameTemplate")
public class FrameProcessor extends AbstractProcessor
{

	@Override
	public boolean process(Set<? extends TypeElement> elements, RoundEnvironment environment)
	{
		note("Entering process: ", environment.processingOver());
		
		// Iterate over all supported annotations
		for (TypeElement annotation : elements)
			for (Element element : environment.getElementsAnnotatedWith(annotation))
				if (element.getKind()==ElementKind.CLASS)
					process((TypeElement)element);
		
		return false;
	}
	
	private void process(TypeElement classElement)
	{
		note(classElement.getQualifiedName());
		
		Filer filer = processingEnv.getFiler();
		
		try
		{

			ClassGenerator gen = new FrameClassGenerator(classElement);
			
			JavaFileObject jfo = filer.createSourceFile(gen.getClassName());
			
			PrintWriter writer = new PrintWriter(jfo.openWriter());
			
			// Add field generators
			for (Element element : classElement.getEnclosedElements())
				if (element.getKind() == ElementKind.FIELD)
				{
					
					// Single-bit field 
					if (element.getAnnotation(Bit.class)!=null)
						gen.addGenerator(new BitFieldGenerator(element, element.getAnnotation(Bit.class)));
					
					// Multi-bit field 
					if (element.getAnnotation(Bits.class)!=null)
						gen.addGenerator(new BitsFieldGenerator(element, element.getAnnotation(Bits.class)));
					
					// Signed byte 
					if (element.getAnnotation(U8.class)!=null)
						gen.addGenerator(new U8FieldGenerator(element, element.getAnnotation(U8.class)));
					
					// Signed short 
					if (element.getAnnotation(U16.class)!=null)
						gen.addGenerator(new U16FieldGenerator(element, element.getAnnotation(U16.class)));
					
					// Address					
					if (element.getAnnotation(Mapped.class)!=null)
						gen.addGenerator(new MappedFieldGenerator(element,
								element.getAnnotation(Mapped.class),
								processingEnv.getTypeUtils().asElement(element.asType()))
						);
					
				}
					
			
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
