package br.com.leo.handlebars;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.JsonNodeValueResolver;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.context.FieldValueResolver;
import com.github.jknack.handlebars.context.JavaBeanValueResolver;
import com.github.jknack.handlebars.context.MapValueResolver;
import com.github.jknack.handlebars.context.MethodValueResolver;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase{
	
	private static final String ORIGINAL_JSON = StringUtils.normalizeSpace("{\n" + 
			"   \"pedidos\":[\n" + 
			"      {\n" + 
			"         \"codigo\":1,\n" + 
			"         \"descricao\":\"Pedido de consumidor\",\n" + 
			"         \"itens\":[\n" + 
			"            {\n" + 
			"               \"codigo\":1515,\n" + 
			"               \"descricao\":\"sapato\"\n" + 
			"            },\n" + 
			"            {\n" + 
			"               \"codigo\":9015,\n" + 
			"               \"descricao\":\"bolsa\"\n" + 
			"            }\n" + 
			"         ]\n" + 
			"      },\n" + 
			"      {\n" + 
			"         \"codigo\":2,\n" + 
			"         \"descricao\":\"Pedido de lojista\",\n" + 
			"         \"itens\":[\n" + 
			"            {\n" + 
			"               \"codigo\":3254,\n" + 
			"               \"descricao\":\"cabide\"\n" + 
			"            }\n" + 
			"         ]\n" + 
			"      }\n" + 
			"   ]\n" + 
			"}");
	
	private static final String FINAL_JSON = StringUtils.normalizeSpace("[\n" + 
			"  {\n" + 
			"    \"cod-pedido\": 1,\n" + 
			"    \"produtos\": [\n" + 
			"      {\n" + 
			"        \"cod\": 1515,\n" + 
			"        \"desc\": \"sapato\"\n" + 
			"      },\n" + 
			"      {\n" + 
			"        \"cod\": 9015,\n" + 
			"        \"desc\": \"bolsa\"\n" + 
			"      }\n" + 
			"    ]\n" + 
			"  },\n" + 
			"  {\n" + 
			"    \"cod-pedido\": 2,\n" + 
			"    \"produtos\": [\n" + 
			"      {\n" + 
			"        \"cod\": 3254,\n" + 
			"        \"desc\": \"cabide\"\n" + 
			"      }\n" + 
			"    ]\n" + 
			"  }\n" + 
			"]");

	
	public AppTest( String testName ){
		super( testName );
	}

	public static Test suite(){
		return new TestSuite( AppTest.class );
	}

	public void testApp(){
		
		try {
			Handlebars handlebars = new Handlebars();
			Template template = handlebars.compile("/json");
			
			ObjectMapper mapper = new ObjectMapper();
			
			JsonNode originalJson = mapper.readValue(ORIGINAL_JSON, JsonNode.class);
			
			Context context = Context
					.newBuilder(originalJson)
					.resolver(JsonNodeValueResolver.INSTANCE, 
							  MapValueResolver.INSTANCE, 
							  JavaBeanValueResolver.INSTANCE,
							  MethodValueResolver.INSTANCE,
							  FieldValueResolver.INSTANCE)
					.build();

			String resultJson = StringUtils.normalizeSpace(template.apply(context));
			
			System.out.println("-----ORIGINAL JSON-----");
			System.out.println(originalJson.toString());
			
			System.out.println("-----RESULT JSON-----");
			System.out.println(resultJson);
			System.out.println("-----FINAL JSON-----");
			System.out.println(FINAL_JSON);
			
			assertEquals(FINAL_JSON, resultJson);
			
		}    
		catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
