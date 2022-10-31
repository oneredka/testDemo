package com.icarus.demo.util;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

public class BizMergeStrategy extends AbstractMergeStrategy {

    private List<MergeRange> mergeSrategy;

    public BizMergeStrategy(List<MergeRange> list) {
        this.mergeSrategy = list;
    }

    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer integer) {
        for (MergeRange mergeRange : mergeSrategy) {
            sheet.addMergedRegionUnsafe(mergeRange.toCellRangeAddress());
        }
    }
}
