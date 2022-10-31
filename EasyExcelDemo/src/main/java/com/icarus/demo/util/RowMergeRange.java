package com.icarus.demo.util;


import lombok.Data;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 行合并
 */
@Data
public  class RowMergeRange extends MergeRange{
    private int columnIndex;

    public RowMergeRange(int columnIndex,int start,int end) {
        this.start=start;
        this.end=end;
        this.columnIndex = columnIndex;
    }

    @Override
    public CellRangeAddress toCellRangeAddress() {
        return  new CellRangeAddress(start,
                end, columnIndex, columnIndex);
    }
}
