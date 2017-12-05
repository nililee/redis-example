package com.lotte.excel.domain;

import static com.lotte.lps.core.excel.ExcelUploadUtils.*;

import java.io.*;

import org.apache.commons.lang3.builder.*;
import org.apache.poi.ss.usermodel.*;

import lombok.*;
import lombok.Builder;

@Data
@Builder
@AllArgsConstructor
public class SampleEntry implements Serializable {

    private static final long serialVersionUID = 1265570859819966951L;

    String segment;
    String country;
    String product;
    String discountBand;
    double unitsSold;
    double manufacturingPrice;
    double salePrice;
    double grossSales;
    double discounts;
    double sales;
    double cogs;
    double profit;
    String date;
    int    monthNumber;
    String monthName;
    int    year;

    public static SampleEntry rowOf(Row row) {
        return SampleEntry.builder()
            .segment           ( getStringCellValue (row.getCell(0))  )
            .country           ( getStringCellValue (row.getCell(1))  )
            .product		   ( getStringCellValue (row.getCell(2))  )
            .discountBand	   ( getStringCellValue (row.getCell(3))  )
            .unitsSold		   ( getDoubleCellValue (row.getCell(4))  )
            .manufacturingPrice( getDoubleCellValue (row.getCell(5))  )
            .salePrice		   ( getDoubleCellValue (row.getCell(6))  )
            .grossSales        ( getDoubleCellValue (row.getCell(7))  )
            .discounts         ( getDoubleCellValue (row.getCell(8))  )
            .sales             ( getDoubleCellValue (row.getCell(9))  )
            .cogs              ( getDoubleCellValue (row.getCell(10)) )
            .profit            ( getDoubleCellValue (row.getCell(11)) )
            .date              ( getStringCellValue (row.getCell(12)) )
            .monthNumber       ( getIntegerCellValue(row.getCell(13)) )
            .monthName         ( getStringCellValue (row.getCell(14)) )
            .year              ( getIntegerCellValue(row.getCell(15)) )
            .build();
    }

    public static void setRow(Row row, SampleEntry entry) {
        row.createCell(0).setCellValue(entry.getSegment());
        row.createCell(1).setCellValue(entry.getCountry());
        row.createCell(2).setCellValue(entry.getProduct());
        row.createCell(3).setCellValue(entry.getDiscountBand());
        row.createCell(4).setCellValue(entry.getUnitsSold());
        row.createCell(5).setCellValue(entry.getManufacturingPrice());
        row.createCell(6).setCellValue(entry.getSalePrice());
        row.createCell(7).setCellValue(entry.getGrossSales());
        row.createCell(8).setCellValue(entry.getDiscounts());
        row.createCell(9).setCellValue(entry.getSales());
        row.createCell(10).setCellValue(entry.getCogs());
        row.createCell(11).setCellValue(entry.getProfit());
        row.createCell(12).setCellValue(entry.getDate());
        row.createCell(13).setCellValue(entry.getMonthNumber());
        row.createCell(14).setCellValue(entry.getMonthName());
        row.createCell(15).setCellValue(entry.getYear());
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
