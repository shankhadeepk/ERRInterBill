package com.ericsson.ibp.template.processor;

import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * @author ejyoban
 * 
 */

public class TestTemplate {
	Logger logger = Logger.getLogger(TestTemplate.class);
	public void test()
	{
		try{
			System.out.println("Now within the test method... ");
			VelocityContext context = new VelocityContext();
			context.put("name", "Jyotirmoy");
			context.put("surname", "Banerjee");
			StringWriter w = new StringWriter();
			Template t = Velocity.getTemplate("test.vm");
			if(t == null)
				throw new NullPointerException();
			t.merge(context, w);
			System.out.println("The result of the merge is >>>>>>>>>>>>>" + w);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}


