package com.icarus.demo.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.icarus.demo.Strategy.CommonCellStyleStrategy;
import com.icarus.demo.entity.SysUser;
import com.icarus.demo.handler.CustomCellWriteHandler;
import com.icarus.demo.handler.importListender.SysUserExcelListener;
import com.icarus.demo.repository.MPSysUserRepository;
import com.icarus.demo.util.BizMergeStrategy;
import com.icarus.demo.util.ColMergeRange;
import com.icarus.demo.util.MergeRange;
import com.icarus.demo.util.RowMergeRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@RestController
@RequestMapping("/sys_user")
public class SysUserController {

    @Autowired
    MPSysUserRepository sysUserRepository;

    @GetMapping("list")
    public List<SysUser> getList() {
        return sysUserRepository.list();
    }

    /**
     * 无模板导出
     * 注解版
     * 普通导出方式
     */
    @RequestMapping("/export1")
    public void export1(HttpServletResponse response) throws IOException {
        List<SysUser> users = sysUserRepository.list();

        // 设置文本内省
        response.setContentType("application/vnd.ms-excel");
        // 设置字符编码
        response.setCharacterEncoding("utf-8");
        // 设置响应头
        response.setHeader("Content-disposition", "attachment;filename=demo.xlsx");
        EasyExcel.write(response.getOutputStream(), SysUser.class).sheet("成员列表").doWrite(users);
    }

    /**
     * 无模板导出
     * 注解版
     * 基于策略及拦截器导出
     * 通过 registerWriteHandler 自定义样式
     */
    @RequestMapping("/export2")
    public void export2(HttpServletResponse response) throws IOException {
        List<SysUser> members = sysUserRepository.list();

        // 设置文本内省
        response.setContentType("application/vnd.ms-excel");
        // 设置字符编码
        response.setCharacterEncoding("utf-8");
        // 设置响应头
        response.setHeader("Content-disposition", "attachment;filename=demo.xlsx");
        EasyExcel.write(response.getOutputStream(), SysUser.class).sheet("成员列表")
                // 注册通用格式策略
                .registerWriteHandler(CommonCellStyleStrategy.getHorizontalCellStyleStrategy())
                // 设置自定义格式策略
                .registerWriteHandler(new CustomCellWriteHandler())
                .doWrite(members);
    }

    /**
     * 无模板导出
     * （非注解）
     * 生成动态复杂表头+数据填充
     */
    @RequestMapping("/export3")
    public void export3(HttpServletResponse response) throws Exception {
        // 文件输出位置
        OutputStream out = new FileOutputStream("C://hong//output//test2.xlsx");
        ExcelWriter writer = EasyExcelFactory.write(out).build();

        // 动态添加表头，适用一些表头动态变化的场景
        WriteSheet sheet1 = new WriteSheet();
        sheet1.setSheetName("商品明细");
        sheet1.setSheetNo(0);
        // 创建一个表格，用于 Sheet 中使用
        WriteTable table = new WriteTable( );
        table.setTableNo(1);
        table.setHead(head());
        // 写数据
        writer.write(contentData(), sheet1, table);
        writer.finish();
        out.close();


        //=========================//

       /* List<List<String>> header = getHeader();
        //数据
        List<List<String>> data = getData();
        //单元格合并条件
        List<MergeRange> mergePlans = getMergePlans();
        //写入文件
        write(Files.newOutputStream(Paths.get("C://hong//output//test2.xlsx")), header, data, mergePlans);*/
    }

    private static List<List<String>> head() {
        List<List<String>> headTitles = new ArrayList<>();
        String basicInfo = "基础资料", skuInfo = "商品扩展", orderInfo = "经营情况", empty = " ";
        //第一列，1/2/3行
        headTitles.add(List.of(basicInfo, basicInfo, "类别"));
        //第二列，1/2/3行
        headTitles.add(List.of(basicInfo, basicInfo, "名称"));
        List<String> skuTitles = List.of("组合商品", "上一次优惠时间", "销售次数", "库存", "价格");
        skuTitles.forEach(title -> {
            headTitles.add(List.of(skuInfo, skuInfo, title));
        });
        List<Integer> monthList = List.of(5, 6);
        //动态根据月份生成
        List<String> orderSpeaces = List.of("销售额", "客流", "利润");
        monthList.forEach(month -> {
            orderSpeaces.forEach(title -> {
                headTitles.add(List.of(orderInfo, month + "月", title));
            });
        });
        //无一、二行标题
        List<String> lastList = List.of("日均销售金额(元)", "月均销售金额(元)");
        lastList.forEach(title -> {
            headTitles.add(List.of(empty, empty, title));
        });
        return headTitles;
    }

    private static List<List<Object>> contentData() {
        List<List<Object>> contentList = new ArrayList<>();
        //这里一个List<Object>才代表一行数据，需要映射成每行数据填充，横向填充（把实体数据的字段设置成一个List<Object>）
        contentList.add(List.of("测试", "商品A", "苹果🍎"));
        contentList.add(List.of("测试", "商品B", "橙子🍊"));
        return contentList;
    }

    private static List<List<String>> getData() {
        List<List<String>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ArrayList<String> l1 = new ArrayList<>();
            for (int i1 = 0; i1 < 4; i1++) {
                l1.add(i1 + "");
            }
            list.add(l1);
        }
        return list;
    }

    private static List<List<String>> getHeader() {
        List<List<String>> list = new ArrayList<>();
        //创建一个一级标题
        list.add(createHead("标题1"));
        //创建一个二级标题
        list.add(createHead("标题2-1", "标题2-2"));
        list.add(createHead("标题3-1", "标题3-2"));
        //创建一个三级标题
        list.add(createHead("标题4-1", "标题4-2", "标题4-3"));
        return list;
    }

    public static List<String> createHead(String... head) {
        return new ArrayList<>(Arrays.asList(head));
    }

    private static List<MergeRange> getMergePlans() {
        List<MergeRange> list = new ArrayList<>();
        //行合并,0列的3-4行进行合并
        list.add(new RowMergeRange(0, 3, 4));
        //行合并,0列的5-7行进行合并
        list.add(new RowMergeRange(0, 5, 7));
        //列合并,8行的1-3列进行合并
        list.add(new ColMergeRange(8, 1, 3));
        //列合并,10行的1-2列进行合并
        list.add(new ColMergeRange(10, 1, 2));
        return list;
    }

    public static void write(OutputStream outputStream, List<List<String>> header,
                             List<List<String>> data, List<MergeRange> mergeRanges) {

        EasyExcel.write(outputStream)
                .head(header)
                .registerWriteHandler(new BizMergeStrategy(mergeRanges))
                .sheet("sheet")
                .doWrite(data);
    }


    /**
     * 普通模板导出
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "/export33", method = RequestMethod.GET)
    public boolean export33(HttpServletResponse response) {

        // 重要! 设置返回格式是excel形式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        // 设置编码格式
        response.setCharacterEncoding("utf-8");
        // 设置URLEncoder.encode 防止中文乱码
        String fileName = null;
        try {
            fileName = URLEncoder.encode("数据批量导出", "UTF-8").replaceAll("\\+", "%20");
            // 设置响应头
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            List<SysUser> users = sysUserRepository.list();
            // 模板文件保存在springboot项目的resources/static下
            Resource resource = new ClassPathResource("static/数据批量导出模板.xlsx");
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream())
                    .withTemplate(resource.getInputStream())    // 利用模板的输出流
                    .build();
            // 写入模板文件的第一个sheet 索引0
            WriteSheet writeSheet = EasyExcel.writerSheet(0).build();

            // 将数据写入到模板文件的对应sheet中
            excelWriter.write(users, writeSheet);
            excelWriter.finish();
        } catch (UnsupportedEncodingException e) {
            System.out.printf("=========================》导出失败: %s%n", e.getMessage());
            return false;
        } catch (IOException e) {
            System.out.printf("=========================》导出失败: %s%n", e.getMessage());
            return false;
        }
        System.out.println("=========================》导出成功！");
        return false;

    }

    /**
     * 复杂模板模板导出  MAP 多个list
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "/export44", method = RequestMethod.GET)
    public void export44(HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("year", 2022);
        map.put("xxcpde", "23213-2323123");
        map.put("companyName", "水木清华，婉兮清扬");
        map.put("companyAddress", "天龙八部之木婉清");
        map.put("createYear", 1998);
        map.put("month", 12);
        map.put("total", 9999);
        map.put("number1", 999);
        map.put("number2", 9000);
        map.put("allIndex", "99999");
        List<SysUser> users1 = sysUserRepository.list();
        List<SysUser> users2 = sysUserRepository.list();

        // 重要! 设置返回格式是excel形式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        // 设置编码格式
        response.setCharacterEncoding("utf-8");
        // 设置URLEncoder.encode 防止中文乱码
        String fileName = null;
        try {
            fileName = URLEncoder.encode("复杂模板导出", "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            System.out.printf("=========================》导出失败: %s%n", e.getMessage());
        }
        // 设置响应头
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        //导出模板
        ExcelWriter excelWriter = null;
        // 模板文件保存在springboot项目的resources/static下
        Resource resource = new ClassPathResource("static/复杂模板.xlsx");
        try {
            excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(resource.getInputStream()).build();
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            FillConfig fillConfigForList2 = FillConfig.builder()
                    // 设置list填入的时候是纵向填入
                    .direction(WriteDirectionEnum.VERTICAL)
                    // 这里注意 入参用了forceNewRow 代表在写入list的时候不管list下面有没有空行 都会创建一行，
                    // 然后下面的数据往后移动。默认 是false，会直接使用下一行，如果没有则创建。
                    // 如果你的模板有list,且list不是最后一行，下面还有数据需要填充
                    // 就必须设置 forceNewRow=true 但是这个就会把所有数据放到内存 会很耗内存
                    .forceNewRow(Boolean.FALSE)
                    .build();
            excelWriter.fill(users1, fillConfigForList2, writeSheet);
            excelWriter.fill(new FillWrapper(users2), fillConfigForList2, writeSheet);
            excelWriter.fill(map, writeSheet);
            excelWriter.finish();
        } catch (Exception e) {
            System.out.printf("=========================》导出失败: %s%n", e.getMessage());
            throw e;
        }
        System.out.println("=========================》导出成功！");
    }

    /**
     * 从Excel导入
     */
    @PostMapping(value = "/import1")
    @ResponseBody
    public void importMemberList(@RequestPart("file") MultipartFile file) throws IOException {
        List<SysUser> list = EasyExcel.read(file.getInputStream())
                .head(SysUser.class)
                .sheet()
                .doReadSync();
        for (SysUser sysUser : list) {
            System.out.println(sysUser);
        }
    }

    /**
     * 基于Listener方式从Excel导入
     * 在SysUserExcelListener中可以针对每条数据进行对应的业务逻辑处理。
     */
    @RequestMapping(value = "/import2", method = RequestMethod.POST)
    @ResponseBody
    public void importMemberList2(@RequestPart("file") MultipartFile file) throws IOException {
        // 方式一：同步读取，将解析结果返回，比如返回List<Member>，业务再进行相应的数据集中处理
        // 方式二：对照doReadSync()方法的是最后调用doRead()方法，不进行结果返回，而是在SysUserExcelListener中进行一条条数据的处理；
        // 此处示例为方式二
        EasyExcel.read(file.getInputStream(), SysUser.class, new SysUserExcelListener()).sheet().doRead();
    }

}
