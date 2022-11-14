/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with AsiaInspection. This 
 * information shall not be distributed or copied without written 
 * permission from the AsiaInspection.
 *
 ***************************************************************************/

package com.example.itext.demo.velocity;

import com.itextpdf.text.Font;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;

/***************************************************************************
 *<PRE>
 *  Project Name    : psi-service
 *
 *  Package Name    : com.ai.inspection.util
 *
 *  File Name       : MyFontProvider.java
 *
 *  Creation Date   : Sep 22, 2017
 *
 *  Author          : Gino Zhao
 *
 *</PRE>
 ***************************************************************************/
public class MyFontProvider extends XMLWorkerFontProvider {

    public MyFontProvider() {
        super(null, null);
    }

    @Override
    public Font getFont(final String fontname, String encoding, float size, final int style) {
        String fntname = fontname;
        if (fntname == null) {
            fntname = "\u5B8B\u4F53";
        }
        if (size == 0) {
            size = 4;
        }
        return super.getFont(fntname, encoding, size, style);
    }
}
