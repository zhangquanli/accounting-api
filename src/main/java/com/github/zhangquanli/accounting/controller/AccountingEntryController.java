package com.github.zhangquanli.accounting.controller;

import com.github.zhangquanli.accounting.entity.AccountingEntry;
import com.github.zhangquanli.accounting.entity.Label;
import com.github.zhangquanli.accounting.entity.Subject;
import com.github.zhangquanli.accounting.entity.Type;
import com.github.zhangquanli.accounting.query.AccountingEntryQuery;
import com.github.zhangquanli.accounting.query.PageableQuery;
import com.github.zhangquanli.accounting.query.SubjectQuery;
import com.github.zhangquanli.accounting.service.AccountingEntryService;
import com.github.zhangquanli.accounting.service.SubjectService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.*;

/**
 * 会计分录管理
 *
 * @author zhangquanli
 * @since 2022/1/5 10:05:00
 */
@Slf4j
@RequestMapping("/accountingEntries")
@RestController
public class AccountingEntryController {
    private final SubjectService subjectService;
    private final AccountingEntryService accountingEntryService;

    public AccountingEntryController(SubjectService subjectService, AccountingEntryService accountingEntryService) {
        this.subjectService = subjectService;
        this.accountingEntryService = accountingEntryService;
    }

    @GetMapping
    public Page<AccountingEntry> selectPage(AccountingEntryQuery accountingEntryQuery, @Valid PageableQuery pageableQuery) {
        return accountingEntryService.selectPage(accountingEntryQuery, pageableQuery);
    }

    @GetMapping("/export")
    public void export(AccountingEntryQuery accountingEntryQuery, HttpServletResponse response) {
        long time1 = System.currentTimeMillis();
        List<AccountingEntry> accountingEntries = accountingEntryService.selectList(accountingEntryQuery);
        Map<String, Object> header = excelHeader(accountingEntries);
        List<Map<String, Object>> contents = excelContents(accountingEntries);
        long time2 = System.currentTimeMillis();
        log.info("导出会计分录数据=>查询数据，花费{}毫秒", (time2 - time1));
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet();
            // 标题行
            {
                Row row = sheet.createRow(0);
                int columnIndex = 0;
                for (String key : header.keySet()) {
                    Cell cell = row.createCell(columnIndex);
                    cell.setCellValue((String) header.get(key));
                    header.put(key, columnIndex);
                    columnIndex++;
                }
            }
            // 内容行
            for (int i = 0; i < contents.size(); i++) {
                Row row = sheet.createRow(i + 1);
                Map<String, Object> content = contents.get(i);
                for (String key : content.keySet()) {
                    Integer columnIndex = (Integer) header.get(key);
                    Cell cell = row.createCell(columnIndex);
                    Object value = content.get(key);
                    if (value instanceof String) {
                        cell.setCellValue((String) value);
                    } else if (value instanceof LocalDate) {
                        cell.setCellValue((LocalDate) value);
                    } else if (value instanceof BigDecimal) {
                        cell.setCellValue(((BigDecimal) value).doubleValue());
                    }
                }
            }
            // 写入响应流
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            String filename = URLEncoder.encode("会计分录_" + System.currentTimeMillis() + ".xlsx", "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            OutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        long time3 = System.currentTimeMillis();
        log.info("导出会计分录数据=>生成excel，花费{}毫秒", (time3 - time2));
    }

    private Map<String, Object> excelHeader(List<AccountingEntry> accountingEntries) {
        Map<String, Object> header = new LinkedHashMap<>();
        header.put("voucherDate", "凭证日期");
        header.put("voucherNum", "凭证编号");
        header.put("subject1Name", "一级科目");
        header.put("subject2Name", "二级科目");
        header.put("subject3Name", "三级科目");
        header.put("subject4Name", "四级科目");
        header.put("debitAmount", "借方");
        header.put("creditAmount", "贷方");
        header.put("balance", "余额");
        header.put("summary", "摘要");
        for (AccountingEntry accountingEntry : accountingEntries) {
            for (Label label : accountingEntry.getLabels()) {
                header.put(label.getName(), label.getName());
            }
        }
        return header;
    }

    private List<Map<String, Object>> excelContents(List<AccountingEntry> accountingEntries) {
        List<Map<String, Object>> contents = new ArrayList<>();
        List<Subject> subjects = subjectService.selectList(new SubjectQuery());
        for (AccountingEntry accountingEntry : accountingEntries) {
            Map<String, Object> content = new HashMap<>();
            content.put("voucherDate", accountingEntry.getVoucher().getAccountDate());
            content.put("voucherNum", accountingEntry.getVoucher().getNum());
            String num = accountingEntry.getSubjectBalance().getSubject().getNum();
            content.put("subject1Name", getSubjectName(subjects, num, 1));
            content.put("subject2Name", getSubjectName(subjects, num, 2));
            content.put("subject3Name", getSubjectName(subjects, num, 3));
            content.put("subject4Name", getSubjectName(subjects, num, 4));
            if (accountingEntry.getType() == Type.DEBIT) {
                content.put("debitAmount", accountingEntry.getAmount());
            }
            if (accountingEntry.getType() == Type.CREDIT) {
                content.put("creditAmount", accountingEntry.getAmount());
            }
            content.put("balance", accountingEntry.getBalance());
            content.put("summary", accountingEntry.getSummary());
            for (Label label : accountingEntry.getLabels()) {
                content.computeIfAbsent(label.getName(), k -> label.getValue());
            }
            contents.add(content);
        }
        return contents;
    }

    private String getSubjectName(List<Subject> subjects, String num, int level) {
        String[] ids = num.split("-");
        if (ids.length > level) {
            int id = Integer.parseInt(ids[level]);
            Subject subject = subjects.stream().filter(item -> item.getId().equals(id))
                    .findAny().orElse(null);
            return subject == null ? null : subject.getName();
        } else {
            return null;
        }
    }

}
