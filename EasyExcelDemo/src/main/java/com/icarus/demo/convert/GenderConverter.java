package com.icarus.demo.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

public class GenderConverter implements Converter<Integer> {
    private static final String MAN = "男";
    private static final String WOMAN = "女";
    private static final Integer MAN_ONE = 1;
    private static final Integer WOMAN_TWO = 2;



    @Override
    public Class<?> supportJavaTypeKey() {
        // 实体类中对象属性类型
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        // Excel中对应的CellData属性类型
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Integer convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty,
                                     GlobalConfiguration globalConfiguration) {
        // 从Cell中读取数据
        String gender = cellData.getStringValue();
        // 判断Excel中的值，将其转换为预期的数值
        if (MAN.equals(gender)) {
            return MAN_ONE;
        } else if (WOMAN.equals(gender)) {
            return WOMAN_TWO;
        }
        return null;
    }

    @Override
    public CellData<?> convertToExcelData(Integer  integer, ExcelContentProperty excelContentProperty,
                                          GlobalConfiguration globalConfiguration) {
        // 判断实体类中获取的值，转换为Excel预期的值，并封装为CellData对象
        if (integer == null) {
            return new CellData<>("");
        } else if (integer.equals(MAN_ONE)) {
            return new CellData<>(MAN);
        } else if (integer.equals(WOMAN_TWO)) {
            return new CellData<>(WOMAN);
        }
        return new CellData<>("");
    }
}
