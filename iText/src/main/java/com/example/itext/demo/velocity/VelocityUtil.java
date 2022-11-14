package com.example.itext.demo.velocity;


import org.apache.velocity.VelocityContext;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.MethodInvocationException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

public class VelocityUtil {
    public VelocityUtil() {
    }

    public static VelocityContext createContext(Map<String, Object> args) {
        VelocityContext context = new VelocityContext();
        if (args != null && !args.isEmpty()) {
            Iterator var2 = args.keySet().iterator();

            while(var2.hasNext()) {
                String key = (String)var2.next();
                context.put(key, args.get(key));
            }

            return context;
        } else {
            return context;
        }
    }


    public static String evaluateString(String s, Map<String, Object> params) throws Exception {
        VelocityContext context = createContext(params);
        StringWriter writer = new StringWriter();

        String var4;
        try {
            VelocityEngineInstance.getInstance().getEngine().evaluate(context, writer, "VelocityUtil", s);
            var4 = writer.toString();
        } catch (ParseErrorException var16) {
            throw new Exception("TEMPLATE_ERROR", var16);
        } catch (MethodInvocationException var17) {
            throw new Exception("TEMPLATE_ERROR", var17);
        } catch (ResourceNotFoundException var18) {
            throw new Exception("TEMPLATE_FILE_NOT_FOUND", var18);
        } catch (Exception var19) {
            throw new Exception(var19);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException var15) {
                    writer = null;
                }
            }

        }

        return var4;
    }
}