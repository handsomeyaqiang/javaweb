package com.gongxiangwang.cn;

import java.io.File;




import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/** 日志对象*/  
    private Log logger = LogFactory.getLog(this.getClass());  
    // 上传文件存储目录
    private static final String UPLOAD_DIRECTORY = "download";
    private String publicname=null;//共享者姓名
    private String path=null;//上传到数据库的path
    private String remark=null;//上传到数据库的remark
    private Date publictime;//上传时间
    private String type=null;//上传资源类型
    private String realname=null;//保存到本地的文件名
    private String filename=null;//上传时的文件名
    private String size=null;//资源大小
    private String modifyname=null;
    private String encode="UTF-8";//统一编码格式
    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 100;  // 300MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 800; // 800MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 810; // 810MB
 
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/htmll;charset=utf-8");
		HttpSession session=request.getSession(false);
		if(session==null||session.getAttribute("user")==null)
		{
			getServletContext().getRequestDispatcher("/login.html").forward(request, response);
		}
		else 
		{
			
			// 检测是否为多媒体上传
			PrintWriter writer = response.getWriter();
			if (!ServletFileUpload.isMultipartContent(request)) {
			    // 如果不是则停止
			    writer.println("Error: 表单必须包含 enctype=multipart/form-data");
			    writer.flush();
			    return;
			}

	        try {
	        // 配置上传参数// 基于磁盘文件项目创建一个工厂对象
	        DiskFileItemFactory factory = new DiskFileItemFactory();
	        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
	        factory.setSizeThreshold(MEMORY_THRESHOLD);
	        // 设置临时存储目录
	        System.out.println("=================================");
	        System.out.println(System.getProperty("java.io.tmpdir"));
	        System.out.println("=================================");
	        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
	        // 创建一个新的文件上传对象  
	        ServletFileUpload upload = new ServletFileUpload(factory);
            
	        // 设置最大文件上传值
	        upload.setFileSizeMax(MAX_FILE_SIZE);
	         
	        // 设置最大请求值 (包含文件和表单数据)//文件最大，设为-1表示不受限制
	        upload.setSizeMax(MAX_REQUEST_SIZE);
	        
	        // 中文处理
	        upload.setHeaderEncoding(encode); 
	        
	        //FileSystemView fsv = FileSystemView.getFileSystemView();
			//File com=fsv.getHomeDirectory();    //这便是读取桌面路径的方法了
	        // 构造临时路径来存储上传的文件
	        // 这个路径相对当前应用的目录
	        //String uploadPath =com.getPath()+ File.separator + UPLOAD_DIRECTORY;
	        String uploadPath=request.getServletContext().getRealPath("./") + UPLOAD_DIRECTORY;
	        System.out.println(uploadPath);
	        // 如果目录不存在则创建
	        File uploadDir = new File(uploadPath);
	        if (!uploadDir.exists()) {
	            uploadDir.mkdir();
	        }
	 

	        	List<FileItem> items=upload.parseRequest(request);
	        	Iterator<FileItem> iter=items.iterator();
	        	while(iter.hasNext())
	        	{
	        		FileItem item = (FileItem)iter.next(); 
	        		//检查是一个普通的表单域还是File组件

	        		if( !item.isFormField() ){
	        			
	        		if(item.getName()!=null && !item.getName().equals("")) { // 判断是否选择了文
	        			filename=item.getName();
	        			System.out.println("FileName:==>"+filename);
	        			
	        			System.out.println("FieldName:==>"+item.getFieldName());

	        			System.out.println("Size:==>"+item.getSize());
	        			double upFileSize=(double)item.getSize();
	        			if(upFileSize>MAX_FILE_SIZE)
	        			{
	        				request.setAttribute("message", "超过文件限制大小："+MAX_FILE_SIZE);
	        		        getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
	        			}
	        			if(upFileSize<1024)
	        			{
	        				size=String.format("%.2f", upFileSize)+"BT";
	        			}else if(upFileSize<1024*1024)
	        			{
	        				size=String.format("%.2f", upFileSize/1024)+"KB";
	        			}else if(upFileSize<1024*1024*1024)
	        			{
	        				size=String.format("%.2f", upFileSize/(1024*1024))+"MB";
	        			}
	        			else {
	        				size=String.format("%.2f", upFileSize/(1024*1024*1024))+"GB";
	        			}
	        			String filenamehouzui=filename.substring(filename.lastIndexOf("."));
	        			//得到不重复的文件名，这一步是为了防止同时上传两个同文件名
	        			publictime= new Date(System.currentTimeMillis());  
	        			SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");  
	        			String fileName = fmt.format(publictime)+filenamehouzui;
	        			
	        			//realname=filename.substring(0, filename.indexOf("."))+fileName;//获取存储本地的文件名等于原资源名加时间
	        			realname=fileName;//获取存储本地的文件名等于原资源名加时间

	        			Pattern pattern1=Pattern.compile("(\\.mp3|\\.wma|\\.wav|\\.cd)$");
	        			Matcher isMatch1 = pattern1.matcher(filenamehouzui);
	        			
	        			Pattern pattern2=Pattern.compile("(\\.xls|\\.txt|\\.doc|\\.exe|\\.wps|\\.zip|\\.rar|\\.xml|\\.html|\\.htm|\\.asp|\\.php|\\.jsp|\\.ppt)$");
	        			Matcher isMatch2 = pattern2.matcher(filenamehouzui);
	        			
	        			Pattern pattern3=Pattern.compile("(\\.mp4|\\.mkv|\\.avi|\\.wmv|\\.swf|\\.flv)$");
	        			Matcher isMatch3 = pattern3.matcher(filenamehouzui);
	        			
	        			Pattern pattern4=Pattern.compile("(\\.png|\\.jpg|\\.jpeg|\\.bmp|\\.gif)$");
	        			Matcher isMatch4 = pattern4.matcher(filenamehouzui);
	        			if(isMatch1.matches())
	        			{
	        				path=UPLOAD_DIRECTORY+File.separator+"yinyue"+File.separator+realname;
	        				uploadPath=uploadPath+File.separator+"yinyue";
	        				type="yinyue";
	        		        // 如果目录不存在则创建
	        		        File yinyuefile = new File(uploadPath);
	        		        if (!yinyuefile.exists()) {
	        		            yinyuefile.mkdir();
	        		        }
	        			}
	        			else if(isMatch2.matches())
	        			{
	        				path=UPLOAD_DIRECTORY+File.separator+"files"+File.separator+realname;
	        				uploadPath=uploadPath+File.separator+"files";
	        				type="files";
	        		        // 如果目录不存在则创建
	        		        File filesfile = new File(uploadPath);
	        		        if (!filesfile.exists()) {
	        		        	filesfile.mkdir();
	        		        }
	        			}
	        			else if(isMatch3.matches())
	        			{
	        				path=UPLOAD_DIRECTORY+File.separator+"shipin"+File.separator+realname;
	        				uploadPath=uploadPath+File.separator+"shipin";
	        				type="shipin";
	        		        // 如果目录不存在则创建
	        		        File shipinfile = new File(uploadPath);
	        		        if (!shipinfile.exists()) {
	        		        	shipinfile.mkdir();
	        		        }
	        			}
	        			else if(isMatch4.matches())
	        			{
	        				path=UPLOAD_DIRECTORY+File.separator+"images"+File.separator+realname;
	        				uploadPath=uploadPath+File.separator+"images";
	        				type="images";
	        		        // 如果目录不存在则创建
	        		        File imagesfile = new File(uploadPath);
	        		        if (!imagesfile.exists()) {
	        		        	imagesfile.mkdir();
	        		        }
	        			}
	        			else {
	                        writer.print("<script>alert('不支持此类型！')</script>");  
	                        return;    				
	        			}
	        			String filePath = uploadPath + File.separator + realname;
	        			File storeFile = new File(filePath);
	        			// 在控制台输出文件的上传路径
	        			System.out.println(filePath);
	        			// 保存文件到硬盘
	        			System.out.println("开始");
	        			item.write(storeFile);
	        			System.out.println("完成");
	        			}
	        		else
	        			{
	                    writer.print("<script>alert('没有选择文件！')</script>");  
	                    return;  
	        			}
	        		}
	        		else{
	        			if("remark".equals(item.getFieldName())){

	        				remark = new String(item.getString().getBytes("iso-8859-1"),"UTF-8");
	        				System.out.println("remark:"+remark);
	        			}if("modifyname".equals(item.getFieldName())){

	        				modifyname = new String(item.getString().getBytes("iso-8859-1"),"UTF-8");
	        				System.out.println("modifyname:"+modifyname);
	        			}

	        		} 
	        	}
	        	if(modifyname!=null&&!modifyname.equals(""))
	        	{
	        		filename=modifyname;
	        	}
	        	System.out.println(modifyname);
				request.setAttribute("message",
						filename+"文件上传成功!");
				SessionUserBean user=new SessionUserBean();
				user=(SessionUserBean)session.getAttribute("user");
				publicname=user.getNickname();
	        	String sqlString="insert into resource_table values(?,?,?,?,?,?,?,?)";
	        	Object[] parms= {filename,publictime,publicname,type,path,realname,size,remark};
	        	int i=utils.SQLHelper.insertBySQL(sqlString, parms);
	        	System.out.println("i:"+i);
	        	//responseMessage(response, State.OK);  
		        // 跳转到 message.jsp
		        getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
	        } catch(Exception e) {  
	            logger.error(e.getMessage(), e);   
	        } 
	        //response.sendRedirect("/message.jsp");

		}

	}
  

}
