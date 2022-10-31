package com.icarus.demo.util;

import lombok.Data;
import org.apache.poi.ss.util.CellRangeAddress;

@Data
public abstract class MergeRange{
    public int start;
    public int end;

    public abstract CellRangeAddress toCellRangeAddress();
}
