package com.yinhai.cxtj.front.util;

import com.yinhai.core.common.api.util.ValidateUtil;
import com.yinhai.core.common.ta3.dto.TaParamDto;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaohs on 2019/7/29.
 */
public class ExportExcel {

    // 插入行
    final static String INSERTROW = "insertrow";
    // 合并单元格
    final static String MERGECELL = "mergecell";
    // 单元格样式
    final static String CELLSTYLE = "cellstyle";

    final static String TYPENEW = "new";

    final static String TYPEOLD = "old";

    /**
     * 设置导出值
     *
     * @author 何涛
     * @param sheetlst
     *            导出每页数据列表
     * @param rownum
     *            设置哪一行数据，从0开始
     * @param colnum
     *            从哪一列开始设置数据，从0开始
     * @param cellval
     *            单位格数据
     * @throws Exception
     */
    public static void setExcelRowDataOld(List sheetlst, int rownum, int colnum, String... cellval) throws Exception {
        if (!ValidateUtil.isEmpty(cellval) && sheetlst != null) {
            if (rownum < 0) {
                rownum = 0;
            }
            if (colnum < 0) {
                colnum = 0;
            }

            TaParamDto celldto = new TaParamDto();
            celldto.append("t", TYPEOLD);
            celldto.append("r", rownum);
            celldto.append("c", colnum);
            celldto.append("v", cellval);
            sheetlst.add(celldto);
        }
    }


    /**
     * 下载附件并设置值
     *
     * @param rules
     *            Sheets填充规则
     * @param filename
     *            附件名称
     * @param ya9502
     *            附件自定义名称
     * @param lists
     *            Sheets填充值
     */
    public static void downloadExcel(List rules, String filename, String ya9502, HttpServletResponse response, List... lists) throws Exception {

        if (ValidateUtil.isNotEmpty(filename)) {
            String filePath = ExportExcel.class.getResource("/").getPath()+"cxtj/" + filename;
            if (ValidateUtil.isEmpty(ya9502)) {
                ya9502 = filename;
            }
            // 输出文件
            if (filePath.endsWith(".xls")) {
                downloadXLSExcel(rules, filePath, ya9502,response,lists);
            } else if (filePath.endsWith(".xlsx")) {
                downloadXLSXExcel(rules, filePath, ya9502,response,lists);
            }
        }
    }


    /**
     * 下载附件并设置值
     *
     * @param rules
     *            Sheets填充规则
     * @param url
     *            下载文件路径
     * @param ya9502
     *            附件名称
     * @param lists
     *            Sheets填充值
     */
    public static void downloadXLSExcel(List rules, String url, String ya9502,HttpServletResponse response, List... lists) throws Exception {
        // 写文件
        FileInputStream inp = new FileInputStream(url);
        HSSFWorkbook workbook = new HSSFWorkbook(inp);

        // 处理规则
        Map map = null;
        HSSFSheet sheet = null;
        HSSFRow row = null;
        HSSFCell cell = null;
        HSSFRow stylerow = null;
        Map stylemap = null;
        List stylelst = new ArrayList();
        String rule = null;
        String border = null;
        String lock = null;
        boolean isb = false;
        int sheetnum = 0;
        int rownum = 0;
        int cellnum = 0;
        int num = 0;
        int startrow = 0;
        int endrow = 0;
        int startcell = 0;
        int endcell = 0;
        boolean copystyle = false;
        if (ValidateUtil.isNotEmpty(rules)) {
            for (int i = 0; i < rules.size(); i++) {
                map = (Map) rules.get(i);
                rule = "" + map.get("rule");
                if (INSERTROW.equals(rule)) { // 插入行
                    sheetnum = ((Integer) map.get("sheetnum")).intValue();
                    rownum = ((Integer) map.get("rownum")).intValue();
                    num = ((Integer) map.get("num")).intValue();
                    copystyle = (Boolean) map.get("copystyle");

                    // 获取样式
                    sheet = workbook.getSheetAt(sheetnum);
                    if (copystyle) {
                        stylerow = sheet.getRow(rownum - 1);
                        for (int j = 0; j < stylerow.getPhysicalNumberOfCells(); j++) {
                            stylemap = new HashMap();
                            stylemap.put("style", stylerow.getCell(j).getCellStyle());
                            stylemap.put("type", stylerow.getCell(j).getCellType());
                            stylelst.add(stylemap);
                        }
                    }
                    // 开始行，结束行，插入几行，复制行高，重置行高
                    sheet.shiftRows(rownum, sheet.getLastRowNum(), num, true, false);
                    // 复制样式
                    if (copystyle) {
                        for (int j = 0; j < num; j++) {
                            row = sheet.createRow(rownum + j);
                            row.setHeight(stylerow.getHeight());
                            for (int k = 0; k < stylelst.size(); k++) {
                                stylemap = (Map) stylelst.get(k);
                                cell = row.createCell(k);
                                cell.setCellStyle((HSSFCellStyle) stylemap.get("style"));
                                cell.setCellType(((Integer) stylemap.get("type")).intValue());
                            }
                        }
                    }
                } else if (MERGECELL.equals(rule)) { // 合并单位格
                    sheetnum = ((Integer) map.get("sheetnum")).intValue();
                    startrow = ((Integer) map.get("startrow")).intValue();
                    endrow = ((Integer) map.get("endrow")).intValue();
                    startcell = ((Integer) map.get("startcell")).intValue();
                    endcell = ((Integer) map.get("endcell")).intValue();

                    // 开始行，结束行，开始列，结束列
                    CellRangeAddress range = new CellRangeAddress(startrow, endrow, startcell, endcell);
                    sheet = workbook.getSheetAt(sheetnum);
                    sheet.addMergedRegion(range);
                }
            }
        }

        // 设置数据
        List celllst = null;
        TaParamDto celldto = null;
        String t = null;
        int r = 0;
        int c = 0;
        String[] v = null;
        if (!ValidateUtil.isEmpty(lists)) {
            for (int i = 0; i < lists.length; i++) {
                celllst = lists[i];
                if (ValidateUtil.isNotEmpty(celllst)) {
                    sheet = workbook.getSheetAt(i);
                    for (int j = 0; j < celllst.size(); j++) {
                        celldto = (TaParamDto) celllst.get(j);
                        if (!ValidateUtil.isEmpty(celldto)) {
                            t = celldto.getAsString("t");
                            r = celldto.getAsInteger("r").intValue();
                            c = celldto.getAsInteger("c").intValue();
                            v = celldto.getAsStringArray("v");

                            if (TYPENEW.equals(t)) {
                                row = sheet.createRow(r);
                                for (int k = 0; k < v.length; k++) {
                                    cell = row.createCell(k + c);
                                    cell.setCellValue(v[k]);
                                }
                            } else if (TYPEOLD.equals(t)) {
                                row = sheet.getRow(r);
                                if (ValidateUtil.isEmpty(row)) {
                                    row = sheet.createRow(r);
                                }
                                for (int k = 0; k < v.length; k++) {
                                    cell = row.getCell(k + c);
                                    if (ValidateUtil.isEmpty(cell)) {
                                        cell = row.createCell(k + c);
                                    }
                                    cell.setCellValue(v[k]);
                                }
                            }
                        }
                    }
                }
            }
        }

        // 设置样式
        if (ValidateUtil.isNotEmpty(rules)) {
            for (int i = 0; i < rules.size(); i++) {
                map = (Map) rules.get(i);
                rule = "" + map.get("rule");
                if (CELLSTYLE.equals(rule)) { // 单位格样式
                    sheetnum = ((Integer) map.get("sheetnum")).intValue();
                    rownum = ((Integer) map.get("rownum")).intValue();
                    cellnum = ((Integer) map.get("cellnum")).intValue();

                    sheet = workbook.getSheetAt(sheetnum);
                    row = sheet.getRow(rownum);
                    if (ValidateUtil.isEmpty(row)) {
                        row = sheet.createRow(rownum);
                    }
                    cell = row.getCell(cellnum);
                    if (ValidateUtil.isEmpty(cell)) {
                        cell = row.createCell(cellnum);
                    }

                    HSSFCellStyle style = workbook.createCellStyle();
                    border = "" + map.get("border"); // 边框样式
                    if (ValidateUtil.isNotEmpty(border) && border.length() == 4) {
                        char[] bor = border.toCharArray();
                        if ('1' == bor[0]) { // 上边框
                            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                        }
                        if ('1' == bor[1]) { // 下边框
                            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                        }
                        if ('1' == bor[2]) { // 左边框
                            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                        }
                        if ('1' == bor[3]) { // 右边框
                            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                        }
                    }

                    lock = "" + map.get("lock"); // 位置样式
                    if (ValidateUtil.isNotEmpty(lock)) {
                        if ("1".equals(lock) || "2".equals(lock) || "3".equals(lock)) {
                            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM); // 下
                            if ("1".equals(lock)) {
                                style.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 左
                            } else if ("2".equals(lock)) {
                                style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 中
                            } else if ("3".equals(lock)) {
                                style.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 右
                            }
                        } else if ("4".equals(lock) || "5".equals(lock) || "6".equals(lock)) {
                            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 中
                            if ("4".equals(lock)) {
                                style.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 左
                            } else if ("5".equals(lock)) {
                                style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 中
                            } else if ("6".equals(lock)) {
                                style.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 右
                            }
                        } else if ("7".equals(lock) || "8".equals(lock) || "9".equals(lock)) {
                            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP); // 上
                            if ("7".equals(lock)) {
                                style.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 左
                            } else if ("8".equals(lock)) {
                                style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 中
                            } else if ("9".equals(lock)) {
                                style.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 右
                            }
                        }
                    }

                    isb = ((Boolean) map.get("isb")).booleanValue(); // 是否加粗
                    if (isb) {
                        HSSFFont font = workbook.createFont();
                        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 加粗
                        style.setFont(font);
                    }

                    cell.setCellStyle(style);
                }
            }
        }

        // 输出文件
//        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("*/*");
        response.setHeader("Content-disposition", "attachment;  filename=" + java.net.URLEncoder.encode((String) ya9502, "UTF-8"));
        OutputStream out = response.getOutputStream();

        workbook.write(out);
        out.flush();
        out.close();
        inp.close();
    }

    /**
     * 下载附件并设置值
     *
     * @param rules
     *            Sheets填充规则
     * @param url
     *            下载文件路径
     * @param ya9502
     *            附件名称
     * @param lists
     *            Sheets填充值
     */
    public static void downloadXLSXExcel(List rules, String url, String ya9502,HttpServletResponse response, List... lists) throws Exception {
        // 写文件
        FileInputStream inp = new FileInputStream(url);
        XSSFWorkbook workbook = new XSSFWorkbook(inp);

        // 处理规则
        Map map = null;
        XSSFSheet sheet = null;
        XSSFRow row = null;
        XSSFCell cell = null;
        XSSFRow stylerow = null;
        Map stylemap = null;
        List stylelst = new ArrayList();
        String rule = null;
        String border = null;
        String lock = null;
        boolean isb = false;
        int sheetnum = 0;
        int rownum = 0;
        int cellnum = 0;
        int num = 0;
        int startrow = 0;
        int endrow = 0;
        int startcell = 0;
        int endcell = 0;
        boolean copystyle = false;
        if (ValidateUtil.isNotEmpty(rules)) {
            for (int i = 0; i < rules.size(); i++) {
                map = (Map) rules.get(i);
                rule = "" + map.get("rule");
                if (INSERTROW.equals(rule)) { // 插入行
                    sheetnum = ((Integer) map.get("sheetnum")).intValue();
                    rownum = ((Integer) map.get("rownum")).intValue();
                    num = ((Integer) map.get("num")).intValue();
                    copystyle = (Boolean) map.get("copystyle");

                    // 获取样式
                    sheet = workbook.getSheetAt(sheetnum);
                    if (copystyle) {
                        stylerow = sheet.getRow(rownum - 1);
                        for (int j = 0; j < stylerow.getPhysicalNumberOfCells(); j++) {
                            stylemap = new HashMap();
                            stylemap.put("style", stylerow.getCell(j).getCellStyle());
                            stylemap.put("type", stylerow.getCell(j).getCellType());
                            stylelst.add(stylemap);
                        }
                    }
                    // 开始行，结束行，插入几行，复制行高，重置行高
                    sheet.shiftRows(rownum, sheet.getLastRowNum(), num, true, false);
                    // 复制样式
                    if (copystyle) {
                        for (int j = 0; j < num; j++) {
                            row = sheet.createRow(rownum + j);
                            row.setHeight(stylerow.getHeight());
                            for (int k = 0; k < stylelst.size(); k++) {
                                stylemap = (Map) stylelst.get(k);
                                cell = row.createCell(k);
                                cell.setCellStyle((XSSFCellStyle) stylemap.get("style"));
                                cell.setCellType(((Integer) stylemap.get("type")).intValue());
                            }
                        }
                    }
                } else if (MERGECELL.equals(rule)) { // 合并单位格
                    sheetnum = ((Integer) map.get("sheetnum")).intValue();
                    startrow = ((Integer) map.get("startrow")).intValue();
                    endrow = ((Integer) map.get("endrow")).intValue();
                    startcell = ((Integer) map.get("startcell")).intValue();
                    endcell = ((Integer) map.get("endcell")).intValue();

                    // 开始行，结束行，开始列，结束列
                    CellRangeAddress range = new CellRangeAddress(startrow, endrow, startcell, endcell);
                    sheet = workbook.getSheetAt(sheetnum);
                    sheet.addMergedRegion(range);
                }
            }
        }

        // 设置数据
        List celllst = null;
        TaParamDto celldto = null;
        String t = null;
        int r = 0;
        int c = 0;
        String[] v = null;
        if (!ValidateUtil.isEmpty(lists)) {
            for (int i = 0; i < lists.length; i++) {
                celllst = lists[i];
                sheet = workbook.getSheetAt(i);
                for (int j = 0; j < celllst.size(); j++) {
                    celldto = (TaParamDto) celllst.get(j);
                    if (!ValidateUtil.isEmpty(celldto)) {
                        t = celldto.getAsString("t");
                        r = celldto.getAsInteger("r").intValue();
                        c = celldto.getAsInteger("c").intValue();
                        v = celldto.getAsStringArray("v");

                        if (TYPENEW.equals(t)) {
                            row = sheet.createRow(r);
                            for (int k = 0; k < v.length; k++) {
                                cell = row.createCell(k + c);
                                cell.setCellValue(v[k]);
                            }
                        } else if (TYPEOLD.equals(t)) {
                            row = sheet.getRow(r);
                            if (ValidateUtil.isEmpty(row)) {
                                row = sheet.createRow(r);
                            }
                            for (int k = 0; k < v.length; k++) {
                                cell = row.getCell(k + c);
                                if (ValidateUtil.isEmpty(cell)) {
                                    cell = row.createCell(k + c);
                                }
                                cell.setCellValue(v[k]);
                            }
                        }
                    }
                }
            }
        }

        // 设置样式
        if (ValidateUtil.isNotEmpty(rules)) {
            for (int i = 0; i < rules.size(); i++) {
                map = (Map) rules.get(i);
                rule = "" + map.get("rule");
                if (CELLSTYLE.equals(rule)) { // 单位格样式
                    sheetnum = ((Integer) map.get("sheetnum")).intValue();
                    rownum = ((Integer) map.get("rownum")).intValue();
                    cellnum = ((Integer) map.get("cellnum")).intValue();

                    sheet = workbook.getSheetAt(sheetnum);
                    row = sheet.getRow(rownum);
                    if (ValidateUtil.isEmpty(row)) {
                        row = sheet.createRow(rownum);
                    }
                    cell = row.getCell(cellnum);
                    if (ValidateUtil.isEmpty(cell)) {
                        cell = row.createCell(cellnum);
                    }

                    XSSFCellStyle style = workbook.createCellStyle();
                    border = "" + map.get("border"); // 边框样式
                    if (ValidateUtil.isNotEmpty(border) && border.length() == 4) {
                        char[] bor = border.toCharArray();
                        if ('1' == bor[0]) { // 上边框
                            style.setBorderTop(XSSFCellStyle.BORDER_THIN);
                        }
                        if ('1' == bor[1]) { // 下边框
                            style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
                        }
                        if ('1' == bor[2]) { // 左边框
                            style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
                        }
                        if ('1' == bor[3]) { // 右边框
                            style.setBorderRight(XSSFCellStyle.BORDER_THIN);
                        }
                    }

                    lock = "" + map.get("lock"); // 位置样式
                    if (ValidateUtil.isNotEmpty(lock)) {
                        if ("1".equals(lock) || "2".equals(lock) || "3".equals(lock)) {
                            style.setVerticalAlignment(XSSFCellStyle.VERTICAL_BOTTOM); // 下
                            if ("1".equals(lock)) {
                                style.setAlignment(XSSFCellStyle.ALIGN_LEFT); // 左
                            } else if ("2".equals(lock)) {
                                style.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 中
                            } else if ("3".equals(lock)) {
                                style.setAlignment(XSSFCellStyle.ALIGN_RIGHT); // 右
                            }
                        } else if ("4".equals(lock) || "5".equals(lock) || "6".equals(lock)) {
                            style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 中
                            if ("4".equals(lock)) {
                                style.setAlignment(XSSFCellStyle.ALIGN_LEFT); // 左
                            } else if ("5".equals(lock)) {
                                style.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 中
                            } else if ("6".equals(lock)) {
                                style.setAlignment(XSSFCellStyle.ALIGN_RIGHT); // 右
                            }
                        } else if ("7".equals(lock) || "8".equals(lock) || "9".equals(lock)) {
                            style.setVerticalAlignment(XSSFCellStyle.VERTICAL_TOP); // 上
                            if ("7".equals(lock)) {
                                style.setAlignment(XSSFCellStyle.ALIGN_LEFT); // 左
                            } else if ("8".equals(lock)) {
                                style.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 中
                            } else if ("9".equals(lock)) {
                                style.setAlignment(XSSFCellStyle.ALIGN_RIGHT); // 右
                            }
                        }
                    }

                    isb = ((Boolean) map.get("isb")).booleanValue(); // 是否加粗
                    if (isb) {
                        XSSFFont font = workbook.createFont();
                        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD); // 加粗
                        style.setFont(font);
                    }

                    cell.setCellStyle(style);
                }
            }
        }

        // 输出文件
//        HttpServletResponse response = ServletActionContext.getResponse();
        response.reset();
        response.setContentType("*/*");
        response.setHeader("Content-disposition", "attachment;  filename=" + java.net.URLEncoder.encode((String) ya9502, "UTF-8"));
        OutputStream out = response.getOutputStream();

        workbook.write(out);
        out.flush();
        out.close();
        inp.close();
    }

}
