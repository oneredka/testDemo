package com.icarus.demo.util;

import lombok.Data;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 列合并
 */
@Data
public class ColMergeRange extends MergeRange{
    private int rowIndex;

    public ColMergeRange(int rowIndex,int start,int end) {
        this.start=start;
        this.end=end;
        this.rowIndex = rowIndex;
    }

    @Override
    public CellRangeAddress toCellRangeAddress() {
        return  new CellRangeAddress(rowIndex,
                rowIndex, start, end);
    }
}