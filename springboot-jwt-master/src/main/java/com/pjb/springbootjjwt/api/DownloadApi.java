package com.pjb.springbootjjwt.api;

import com.alibaba.fastjson.JSONObject;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pjb.springbootjjwt.annotation.UserLoginToken;
import com.pjb.springbootjjwt.entity.*;
import com.pjb.springbootjjwt.service.CourseService;
import com.pjb.springbootjjwt.service.DownloadService;
import com.pjb.springbootjjwt.service.ScService;
import com.pjb.springbootjjwt.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * @author yc
 * @date 2018-07-08 20:45
 */
@RestController
@RequestMapping("download")

public class DownloadApi {
    @Autowired
    DownloadService downloadService;
    @Autowired
    CourseService courseService;
    @Autowired
    ScService scService;
    @Autowired
    UserService userService;

    //@Value("${com.pjb.springbootjjwt}")
    //获取主机端口
    private String post;
    //获取本机ip
    private String host;
    //图片存放根路径
    private String rootPath = "F:";
    //图片存放根目录下的子目录
    private String sonPath = "/test/";
    //获取图片链接
    private String imgPath;

    private static final Logger logger = LoggerFactory.getLogger(DownloadApi.class);
    @UserLoginToken
    @RequestMapping(value = "upload")
    @ResponseBody
    public Map<String, Object> upload(@RequestHeader(name="token") String token,@RequestParam("file") MultipartFile file,
                                      @RequestParam("courseid")Integer courseid) {

        String filemaker=userService.selectStunumberBytoken(token);
        String filemakername=userService.selectNamerBytoken(token);

        Map<String,Object> map=new HashMap<>();
        String path;
        String pdfpath;
        switch(courseid){
            case 1:
                path="F:\\course\\1231\\";
                pdfpath="http://172.18.9.145:8099/1231/";
                break;
            case 2:
                path="F:\\course\\1232\\";
                pdfpath="http://172.18.9.145:8099/1232/";
                break;
            case 3:
                path="F:\\course\\1233\\";
                pdfpath="http://172.18.9.145:8099/1233/";
                break;

            default:
                map.put("code",1);
                map.put("message","这门课程不存在！");
                return map;

        }






        //返回上传的文件是否为空，即没有选择任何文件，或者所选文件没有内容。
        //防止上传空文件导致奔溃



        if (file.isEmpty()) {
            map.put("code",1);
            map.put("message","文件为空");
            return map;
        }

        //获取本机IP
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.error("get server host Exception e:", e);
        }

        // 获取文件名
        String fileName = file.getOriginalFilename();
        logger.info("上传的文件名为：" + fileName);
        // 设置文件上传后的路径
        String filePath = rootPath + sonPath;
        //String path="F:/English/";
        logger.info("上传的文件路径" + path);
        logger.info("整个图片路径：" + host + ":" + post + path+ fileName);
        //创建文件路径

        File dest = new File(path + fileName);

        String imgPath = (host + ":" + post + path+ fileName).toString();

        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;

        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            //假如文件不存在即重新创建新的文件已防止异常发生
            dest.getParentFile().mkdirs();
        }



        try {
            //transferTo（dest）方法将上传文件写到服务器上指定的文件
            file.transferTo(dest);
            //将链接保存到URL中
            //downloadService.addDownload(fileName,imgPath);

            Download download=new Download();
            download.setFilename(fileName);
            String filepathtotle=path+fileName;
            Map<String,Object> PDFmap=new HashMap<>();
            PDFmap=preview(filepathtotle,fileName);

            String pdftotlepath=pdfpath+PDFmap.get("NewFileName").toString();
            download.setPdffiles(pdftotlepath);
            download.setPdffilename(PDFmap.get("NewFileName").toString());
            System.out.println(fileName);
            System.out.println(filepathtotle);
            download.setFiles(filepathtotle);
            download.setFilemaker(filemaker);
            download.setFilemakername(filemakername);
            download.setCourseid(courseid);

            Download chicks=new Download();
            chicks.setFilemaker(filemaker);
            chicks.setCourseid(courseid);
            Map<String,Object> chickresult= findOneDownload(chicks);
            System.out.print(chickresult.get("code"));
            String choose=chickresult.get("code").toString();
            if("0".equals(choose)) {
                downloadService.addDownload(download);
                System.out.print("这里用的添加");
            }else {downloadService.updateDownload(download);
                System.out.print("这里用的更新");}

            Sc sc=new Sc();
            sc.setStudentnumber(filemaker);
            sc.setCourseid(courseid);

            sc.setHomework(PDFmap.get("NewFileName").toString());
            sc.setPdfpath(pdftotlepath);
            // System.out.print(sc.getStudentnumber());
          //  System.out.print(sc.getCourseid());
           // System.out.print(sc.getHomework());
            scService.homework(sc);
            map.put("code",0);
            map.put("message","上传成功！！");
            return map;
        } catch (Exception e) {
            map.put("code",1);
            map.put("message","上传失败！！");
            return map;
        }
    }

    @UserLoginToken
    @PostMapping("/findOneDownload")//查找文件是否已经存在
    public Map<String, Object> findOneDownload(@RequestBody Download download) {


            List<Download> downloadlist=downloadService.findOneDownload(download);
            Map<String,Object> map=new HashMap<>();
            if (downloadlist.isEmpty())
            {map.put("code" ,0);}
            //System.out.print(page.getTotal());
            else
            {map.put("code" ,1);}

            return map;
    }

/**    //创建学科文件夹
   @UserLoginToken
    @RequestMapping(value = "makidr")
    @ResponseBody
    public String makidr( MultipartFile file,String filesname) {
        String success="创建成功";
        String fail="创建失败";


        //获取本机IP
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.error("get server host Exception e:", e);
        }


        String filePath = rootPath + filesname;
        logger.info("上传的文件路径" + filePath);

        //创建文件路径
        File dest = new File(filePath );



        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            //假如文件不存在即重新创建新的文件已防止异常发生
            dest.getParentFile().mkdirs();
        }
        return success;

    }
*/

    //查找文件列表

    @UserLoginToken
    @GetMapping("/findAllDownload")//查找所有下载列表
    public Map<String, Object> findAllUser(int pageNum, int pageSize) throws Exception {


        return downloadService.findAllDownload(pageNum, pageSize);
    }


    /**
     * 文件下载
     *
     * @param response
     * @return
     */
    @UserLoginToken
    @RequestMapping(value = "/fileDownload")
    @ResponseBody
    public String download(HttpServletResponse response,String fileName,String files) {
        //  String fileName = "1.png";
        //  String filepath = "F:/test";
        response.setContentType("multipart/form-data;charset=UTF-8");
        File file = new File(files + "/" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
                os.flush();
                bis = new BufferedInputStream(new FileInputStream(file));
                int i = bis.read(buff);
                while (i != -1) {
                    os.write(buff, 0, buff.length);
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "文件下载失败";
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "end";
    }




    /**
     * 在线预览pdf
     * @param //request
     * @param //response
     * @param filePath
     * @param fileName
     * @throws IOException
     */
    @UserLoginToken
    @RequestMapping(value = "/preview")
    public Map<String,Object> preview( @RequestParam String filePath, @RequestParam String fileName) throws IOException {
        Map<String,Object> map=new HashMap<>();
        //response.setContentType("text/html; charset=UTF-8");

        if(!"".equals(filePath)) {
            /*1)根据项目所在的服务器环境,确定路径中的 /  和 \ */
           String osName = System.getProperty("os.name");
            if (Pattern.matches("Linux.*", osName)) {
                filePath = "/" + filePath.replace("\\","/");
            } else if(Pattern.matches("Windows.*", osName)) {
                filePath.replace("/","\\");
            }

            /*2)获得文件名后缀*/
            String ext = "";
            if(!"".equals(fileName) && fileName.contains(".")){
                ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toUpperCase();
            }

            /*3)根据文件类型不同进行预览*/
            /*预览图片*/
            if ("PNG".equals(ext) || "JPEG".equals(ext) || "JPG".equals(ext)) {
                //response.setContentType("image/jpeg");
            }
            /*预览BMP格式的文件*/
            if ("BMP".equals(ext)) {
                //response.setContentType("image/bmp");
            }
            /*预览pdf*/
            if ("PDF".equals(ext)) {
                //response.setContentType("application/pdf");
                map.put("NewFileName",fileName);
                map.put("NewFilePath",filePath);
                map.put("code",0);

                map.put("message","这是pdf文件可直接预览！！");

                return map;
            }

            /*利用openOffice将office文件转换为pdf格式, 然后预览doc, docx, xls, xlsx, ppt, pptx */
            if ("DOC".equals(ext) || "DOCX".equals(ext) || "XLS".equals(ext) || "XLSX".equals(ext) || "PPT".equals(ext) || "PPTX".equals(ext)) {
                /*filePath在数据库中是不带文件后缀的, 由于jodConverter必须要识别后缀,所以将服务器中的文件重命名为带后缀的文件*/
                File docFile = new File(filePath);
                /*File docFileWithExt = new File(filePath + "." + ext.toLowerCase()); //带后缀的文件
                docFile.renameTo(docFileWithExt);
                */
                /*转换之后的文件名*/
                File pdfFile;
                if(filePath.contains(".")){
                    pdfFile = new File(filePath.substring(0, filePath.lastIndexOf(".")) + ".pdf");
                }else{
                    pdfFile = new File(filePath + ".pdf");
                }

                /*判断即将要转换的文件是否真实存在*/
                if (docFile.exists()) {
                    /*判断改文件是否已经被转换过,若已经转换则直接预览*/
                    if (!pdfFile.exists()) {
                        /*打开OpenOffice连接,*/
                        OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
                        try {
                            connection.connect();
                            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
                            converter.convert(docFile, pdfFile);
                            connection.disconnect();

                            filePath = pdfFile.getPath(); //文件转换之后的路径
                            map.put("NewFileName",pdfFile.getName());
                            map.put("NewFilePath",pdfFile.getPath());
                            //response.setContentType("application/pdf");

                        } catch (java.net.ConnectException e) {
                            e.printStackTrace(); //openoffice 服务未启动
                            throw e;
                        } catch (com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException e) {
                            e.printStackTrace(); //读取转换文件失败
                            throw e;
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw e;
                        }finally { //发生exception时, connection不会自动切断, 程序会一直挂着
                            try{
                                if(connection != null){
                                    connection.disconnect();
                                    connection = null;
                                }
                            }catch(Exception e){}
                        }
                    } else {
                        filePath = pdfFile.getPath(); //文件已经转换过
                       //response.setContentType("application/pdf");
                    }
                } else {
                    map.put("code",1);

                    map.put("message","需要预览的文档在服务器中不存在");

                    return map;
                }
            }


        }
        map.put("code",0);

        map.put("message","更改成功！！");

        return map;
    }


    @UserLoginToken
    @GetMapping("/showHomeworkList")//查看课程的文件列表
    public Map<String, Object> showHomeworkList(int pageNum, int pageSize,Integer courseid) throws Exception {

        Page page= PageHelper.startPage(pageNum, pageSize,true);
        List<DownloadAndSc> downloadlist=downloadService.showHomeworkList(courseid);
        Map<String,Object> map=new HashMap<>();
        map.put("code" ,0);
        //System.out.print(page.getTotal());
        map.put("count", page.getTotal());
        map.put("nowPage", pageNum);
        map.put("data", downloadlist);
        return map;

    }







}