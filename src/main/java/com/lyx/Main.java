package com.lyx;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main
{
    /**
     * 使用说明
     * <ul>
     *    <li>功能：将图片进行重命名，新的名字是：(图片的宽)比(图片的高)_(随机码).(扩展名) 用于重命名电脑壁纸.</li>
     *    <li>使用方法：将要重命名的图片全放到一个目录中，把目录路径赋给 DIR_PATH 常量.</li>
     * </ul>
     */
    public static final String DIR_PATH = "/Users/lgf/my-dir/download/1.37至1.78_lsp";

    public static void main(String[] args) throws IOException
    {
        List<File> imgFileList = FileUtil.loopFiles(DIR_PATH, pathname -> !StrUtil.startWith(pathname.getName(), '.'));
        for (File el : imgFileList)
        {
            renameByDimension(el);
        }
    }

    /**
     * 根据尺寸重命名一个文件
     * @param imgFile 文件
     */
    public static void renameByDimension(File imgFile) throws IOException
    {
        String newName = getDimensionValue(imgFile);
        FileUtil.rename(imgFile, newName, true);
        Console.log("修改完毕：{}", IdUtil.fastUUID());
    }

    /**
     * 根据图片的宽和高获取一个新的文件名
     * @param imgFile 图片文件
     * @return 例如图片宽高是1920*1080，那就返回【16比9_xxxxx.type】
     */
    public static String getDimensionValue(File imgFile) throws IOException
    {
        String type = FileUtil.getType(imgFile);
        ImageReader reader = ImgUtil.getReader(type);
        reader.setInput(ImageIO.createImageInputStream(imgFile), true);

        int width = reader.getWidth(0);
        int height = reader.getHeight(0);

        int divisor = NumberUtil.divisor(width, height);

        return StrUtil.format("{}比{}_{}.{}", width/divisor, height/divisor, StrUtil.sub(IdUtil.fastSimpleUUID(), 0, 7), type);
    }
}
