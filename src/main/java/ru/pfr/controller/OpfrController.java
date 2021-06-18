package ru.pfr.controller;

import org.apache.poi.util.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.pfr.Application;
import ru.pfr.model.asv.LegalEntity;
import ru.pfr.model.asv.PhisikalEntity;
import ru.pfr.model.pdsvvrr.Tray;
import ru.pfr.model.pdsvvrr.User;
import ru.pfr.service.asv.LegalEntityService;
import ru.pfr.service.asv.PhisikalEntityService;
import ru.pfr.service.pdsvvrr.LogiService;
import ru.pfr.service.pdsvvrr.TrayService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/pdsv/opfr")
public class OpfrController {

    @Autowired
    LegalEntityService legalEntityService;

    @Autowired
    PhisikalEntityService phisikalEntityService;

    @Autowired
    TrayService trayService;

    @Autowired
    LogiService logiService;
    private static final Logger logger = LogManager.getLogger(Application.class);

    @GetMapping()
    public String startupfr(
            @AuthenticationPrincipal User user,
            Model model) {

        Iterable<Tray> trays = trayService.findAll();
        //Iterable<LegalEntity> statistikaVievs = legalEntityService.findAll();
        List<LegalEntity> LE = null;
        List<PhisikalEntity> PE = null;

        Date date1 = new Date();
        Date date2 = new Date();

        LocalDate first = LocalDate.now().withDayOfMonth(1);
        LocalDate last = first.plusMonths(1).minusDays(1);
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            date1 = new SimpleDateFormat("dd.MM.yyyy").parse(first.format(formatter));
            date2 = new SimpleDateFormat("dd.MM.yyyy").parse(last.format(formatter));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        LE = legalEntityService.findAll(date1, date2);
        PE = phisikalEntityService.findAll(date1, date2);

        trays = ftrays(trays,LE,PE);

        double sumChecksums = 0;
        double sumFl = 0;
        double sumJul = 0;
        double sumItog = 0;

        for (Tray tray :
                trays) {
            sumChecksums += tray.getChecksum();
            sumFl += tray.getFl();
            sumJul += tray.getJul();
            sumItog += tray.getItog();
        }

        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(2);


        model.addAttribute("sumChecksums", df.format(sumChecksums));
        model.addAttribute("sumFl", df.format(sumFl));
        model.addAttribute("sumJul", df.format(sumJul));
        model.addAttribute("sumItog", df.format(sumItog));

        model.addAttribute("sumProzent", df.format(sumItog / sumChecksums * 100) + "%");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        model.addAttribute("date11", dateFormat1.format(date1));
        model.addAttribute("date21", dateFormat1.format(date2));
        model.addAttribute("date1", dateFormat.format(date1));
        model.addAttribute("date2", dateFormat.format(date2));
        model.addAttribute("trays", poriadoc(trays));
        model.addAttribute("user", user);
        return "opfr";
    }

    @GetMapping("/filter")
    public String filter(
            @RequestParam(value = "dat1") String dat1,
            @RequestParam(value = "dat2") String dat2,
            @AuthenticationPrincipal User user,
            Model model) {
        logger.info("User = " + user.getLogin() + " OpfrController rb()");

        Iterable<Tray> trays = trayService.findAll();

        //Iterable<LegalEntity> statistikaVievs = legalEntityService.findAll();
        List<LegalEntity> LE = null;
        List<PhisikalEntity> PE = null;

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = format.parse(dat1);
            date2 = format.parse(dat2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        LE = legalEntityService.findAll(date1, date2);
        PE = phisikalEntityService.findAll(date1, date2);

        trays = ftrays(trays,LE,PE);

        double sumChecksums = 0;
        double sumFl = 0;
        double sumJul = 0;
        double sumItog = 0;

        for (Tray tray :
                trays) {
            sumChecksums += tray.getChecksum();
            sumFl += tray.getFl();
            sumJul += tray.getJul();
            sumItog += tray.getItog();
        }

        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(2);

        model.addAttribute("sumChecksums", df.format(sumChecksums));
        model.addAttribute("sumFl", df.format(sumFl));
        model.addAttribute("sumJul", df.format(sumJul));
        model.addAttribute("sumItog", df.format(sumItog));
        model.addAttribute("sumProzent", df.format(sumItog / sumChecksums * 100) + "%");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        model.addAttribute("date1", dateFormat.format(date1));
        model.addAttribute("date2", dateFormat.format(date2));
        model.addAttribute("trays", poriadoc(trays));
        model.addAttribute("user", user);

        return "fragmentopfr/fragment :: table";
    }


    @GetMapping(
            value = "/documentpechat",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody
    byte[] getDocumentPechat(
            @RequestParam(value = "date1") String dat1,
            @RequestParam(value = "date2") String dat2,
            @AuthenticationPrincipal User user,
            HttpServletResponse resp,
            Model model) throws IOException {

        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date date1 = null;
        Date date2 = null;

        LocalDate now = LocalDate.now();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            if (dat1 == null || dat2 == null) {
                LocalDate first = LocalDate.now().withDayOfMonth(1);
                LocalDate last = first.plusMonths(1).minusDays(1);

                date1 = new SimpleDateFormat("dd.MM.yyyy").parse(first.format(formatter));
                date2 = new SimpleDateFormat("dd.MM.yyyy").parse(last.format(formatter));
            } else {
                date1 = format.parse(dat1);
                date2 = format.parse(dat2);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Iterable<Tray> trays = trayService.findAll();
        //Iterable<LegalEntity> statistikaVievs = legalEntityService.findAll();

        List<LegalEntity> LE = legalEntityService.findAll(date1, date2);
        List<PhisikalEntity> PE = phisikalEntityService.findAll(date1, date2);

        trays = ftrays(trays,LE,PE);

        double sumChecksums = 0;
        double sumFl = 0;
        double sumJul = 0;
        double sumItog = 0;

        for (Tray tray :
                trays) {
            sumChecksums += tray.getChecksum();
            sumFl += tray.getFl();
            sumJul += tray.getJul();
            sumItog += tray.getItog();
        }

        // создание самого excel файла в памяти
        HSSFWorkbook workbook = new HSSFWorkbook();
        // создание листа с названием "Просто лист"
        HSSFSheet sheet = workbook.createSheet("Просто лист");

        InputStream in = null;

        try {
            Row row0 = sheet.createRow(0);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            row0.createCell(0).setCellValue("Платежи ДСВ  в разрезе районов за период с " +
                    dateFormat.format(date1) + " по " + dateFormat.format(date2) +
                    " сформирован на " + formatter.format(now));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

            // создаем подписи к столбцам (это будет первая строчка в листе Excel файла)
            Row row1 = sheet.createRow(1);
            row1.createCell(0).setCellValue("№");
            row1.createCell(1).setCellValue("Наименование района");
            row1.createCell(2).setCellValue("№ районов");
            row1.createCell(3).setCellValue("контрольный показатель по району");
            row1.createCell(4).setCellValue("сумма ФЛ");
            row1.createCell(5).setCellValue("сумма ЮЛ");
            row1.createCell(6).setCellValue("итого");
            row1.createCell(7).setCellValue("% выполнения контр.показателей");

            int rowNum = 1;
            //int cellNum = 1;
            for (Tray tray :
                    poriadoc(trays)) {
                Row row = sheet.createRow(++rowNum);
                row.createCell(0).setCellValue(tray.getIds());
                row.createCell(1).setCellValue(tray.getName());
                row.createCell(2).setCellValue(tray.getNums());
                //row.createCell(2).setCellValue(tray.getChecksum());
                try{
                    row.createCell(3).setCellValue(Double.valueOf(tray.getChecksums()));
                }catch (Exception e){
                    row.createCell(3).setCellValue("");
                }
                try{
                    row.createCell(4).setCellValue(Double.valueOf(tray.getFls()));
                }catch (Exception e){
                    row.createCell(4).setCellValue("");
                }
                try{
                    row.createCell(5).setCellValue(Double.valueOf(tray.getJuls()));
                }catch (Exception e){
                    row.createCell(5).setCellValue("");
                }

                //cellNum++;

                if (rowNum + 1 != 3 && rowNum + 1 != 5 && rowNum + 1 != 8 && rowNum + 1 != 12 &&
                        rowNum + 1 != 15 && rowNum + 1 != 19 && rowNum + 1 != 25 && rowNum + 1 != 29) {
                    Cell sum = row.createCell(6);
                    sum.setCellFormula("SUM(E" + (rowNum + 1) + ":F" + (rowNum + 1) + ")");
                    if(rowNum + 1 != 33){
                        sum = row.createCell(7);
                        sum.setCellFormula("G" + (rowNum + 1) + "/D" + (rowNum + 1) + "*100");
                    }
                    //sum.setCellValue("=ЕСЛИ(D" + (rowNum + 1) + "<>0;G" + (rowNum + 1) + "/D" + (rowNum + 1) + "*100;0)");
                }

            }

            Row row25 = sheet.createRow(33);
            row25.createCell(2).setCellValue("итого");

            Cell sum = row25.createCell(3);
            sum.setCellFormula("SUM(D3:D33)");

            sum = row25.createCell(4);
            sum.setCellFormula("SUM(E3:E33)");
            sum = row25.createCell(5);
            sum.setCellFormula("SUM(F3:F33)");
            sum = row25.createCell(6);
            sum.setCellFormula("SUM(F3:F33)");
            sum = row25.createCell(7);
            sum.setCellFormula("F26/C26*100");

            ByteArrayOutputStream b = new ByteArrayOutputStream();
            workbook.write(b);

            resp.setContentType("application/octet-stream");
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", "document.xls");
            resp.setHeader(headerKey, headerValue);
            resp.setContentLength(b.size());
            resp.getOutputStream().write(b.toByteArray());

            in = new ByteArrayInputStream(b.toByteArray());
        } catch (Exception e) {
        }
        return IOUtils.toByteArray(in);
    }

    private Iterable<Tray> ftrays(Iterable<Tray> trays, List<LegalEntity> LE, List<PhisikalEntity> PE){
        for (Tray t :
                trays) {
            String num = t.getNum();

            for (LegalEntity L :
                    LE) {
                try {
                    if (L.getINSURER_REG_NUM().equals(num)) {
                        t.setJul(L.getPAYMENT_SUM());
                        break;
                    }
                } catch (Exception e) {
                    t.setJul(0);
                }
            }

            for (PhisikalEntity P :
                    PE) {
                try {
                    if (P.getINSURER_REG_NUM().equals(num)) {
                        t.setFl(P.getPAYMENT_SUM());
                        break;
                    }
                } catch (Exception e) {
                    t.setFl(0);
                }
            }
        }
        return trays;
    }

    private ArrayList<Tray> poriadoc(Iterable<Tray> iterable){
        ArrayList<Tray> trays = new ArrayList<>();
        String[] S = {"01","02","26","05","24","19","03","20","06","22","23",
                "16","14","15","17","27","04","13","25","07","21","18","28"};
        for (String s:
             S) {
            switch (s){
                case "01": trays.add(new Tray("УПФР в г.Белгороде")); break;
                case "02": trays.add(new Tray("МРУ в Старооскольском районе")); break;
                case "05": trays.add(new Tray("МРУ в Алексеевском районе")); break;
                case "03": trays.add(new Tray("МРУ в Губкинском районе")); break;
                case "06": trays.add(new Tray("МРУ в Валуйском районе")); break;
                case "16": trays.add(new Tray("МРУ в Яковлевском районе")); break;
                case "04": trays.add(new Tray("МРУ в Шебекинском районе")); break;
                case "07": trays.add(new Tray("МРУ в Белгородском районе")); break;
            }
            for (Tray t:
                    iterable) {
                if(t.getNum().equals("0410"+s) || t.getNum().equals("99000")){
                    trays.add(t);
                    break;
                }
            }
        }

        return trays;
    }


}
