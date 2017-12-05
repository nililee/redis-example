package com.lotte.excel.controller;

import static com.lotte.lps.core.excel.ExcelConfig.*;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.lotte.excel.domain.*;
import com.lotte.lps.core.excel.*;

@RestController
@RequestMapping("download")
public class DownloadExcelController {

    @Autowired
    ExcelXlsView xlsView;

    @Autowired
    ExcelXlsxStreamingView streamingXlsxView;

    @Autowired
    ExcelXlsxView xlsxView;

    //curl -v http://localhost:8888/download/excel-xls --output excel-xls.xls
    @GetMapping("excel-xls")
    public ModelAndView xlsView(Model model) {

        ModelAndView mav = new ModelAndView();
        mav.setView(xlsView);
        mav.addObject(EXCEL_DOWNLOAD_KEY, getTestExcelDownloadData());

        return mav;
    }

    //curl -v http://localhost:8888/download/excel-xlsx --output excel-xlsx.xlsx
    @GetMapping("excel-xlsx")
    public ModelAndView xlsxView() {

        ModelAndView mav = new ModelAndView();
        mav.setView(xlsxView);
        mav.addObject(EXCEL_DOWNLOAD_KEY, getTestExcelDownloadData());

        return mav;
    }

    // get hard-coded data for demo
    private ExcelDownloadData<SampleEntry> getTestExcelDownloadData() {

        // define the template for downloading excel file
        ExcelDownloadData<SampleEntry> data = new ExcelDownloadData<>();

        // specify excel file name
        data.setFileName("sample_download.xlsx");

        // specify name of the first sheet
        data.setSheetName("Sample Data Sheet");

        // set list of header label
        data.setHeaderList(Arrays.asList(
            "Segment",
            "Country",
            "Product",
            "Discount Band",
            "Units Sold",
            "Manufacturing Price",
            "Sale Price",
            "Gross Sales",
            "Discounts",
            "Sales",
            "COGS",
            "Profit",
            "Date",
            "Month Number",
            "Month Name",
            "Year"
        ));

        // hard-coded data to download
        data.setBodyList(Arrays.asList(
            SampleEntry.builder()
                .segment           ( "Government"  )
                .country           ( "Canada"      )
                .product		   ( "Carretera"   )
                .discountBand	   ( "None"        )
                .unitsSold		   ( 1618.5        )
                .manufacturingPrice( 3.00          )
                .salePrice		   ( 20.00         )
                .grossSales        ( 32370.00      )
                .discounts         ( 0             )
                .sales             ( 32370.00      )
                .cogs              ( 16185.00      )
                .profit            ( 16185.00      )
                .date              ( "2014-01-01"  )
                .monthNumber       ( 1             )
                .monthName         ( "January"     )
                .year              ( 2014          )
                .build(),
            SampleEntry.builder()
                .segment           ( "Government"  )
                .country           ( "Germany"     )
                .product		   ( "Carretera"   )
                .discountBand	   ( "None"        )
                .unitsSold		   ( 1321          )
                .manufacturingPrice( 3.00          )
                .salePrice		   ( 20.00         )
                .grossSales        ( 26420.00      )
                .discounts         ( 0             )
                .sales             ( 26420.00      )
                .cogs              ( 13210.00      )
                .profit            ( 13210.00      )
                .date              ( "2014-01-01"  )
                .monthNumber       ( 1             )
                .monthName         ( "January"     )
                .year              ( 2014          )
                .build()
             // ...
         ));

        // specify the mapping function which knows how to handle each row of data
        data.setMapFunc(SampleEntry::setRow);

        return data;
    }

}
