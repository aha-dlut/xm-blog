package com.example.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.example.common.Constants;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.CommonFiles;
import com.example.exception.CustomException;
import com.example.mapper.CommonFilesMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/*
@author TanCheng
@create 2024 -03 -28    
*/
@Service
public class CommonFilesService {

    private static final String filePath = System.getProperty("user.dir") + "/common/";

    private static final Logger log = LoggerFactory.getLogger(CommonFilesService.class);

    @Value("${server.port:9090}")
    private String port;

    @Value("${ip:localhost}")
    private String ip;

    @Resource
    private CommonFilesMapper commonFilesMapper;

    /*文件上传*/
    public void add(MultipartFile file, String name, String folder, String category, Integer folderId) {

        // 参数传  文件夹的名称 是否是文件夹  文件夹的ID
        CommonFiles commonFiles = new CommonFiles();
        String now = DateUtil.now();
        commonFiles.setCreateTime(now);
        commonFiles.setUpdateTime(now);
        commonFiles.setFolder(folder);  // 是否是目录
        commonFiles.setName(name);
        commonFiles.setCategory(category);
        commonFiles.setType(Constants.FILE_TYPE_FOLDER);  // 默认是文件夹  后面如果是文件的话 会覆盖这个值
        commonFiles.setFolderId(folderId); // 当前文件/文件夹 外传的目录ID
        if (Constants.NOT_FOLDER.equals(folder)) {   // 文件操作
            String originalFilename = file.getOriginalFilename();
            commonFiles.setName(originalFilename);
            String extName = FileUtil.extName(originalFilename);   // 获取文件的后缀
            commonFiles.setType(extName);
            long flag = System.currentTimeMillis();
            String fileName = flag + "-" + originalFilename;  // 文件存储在磁盘的文件名
            if (!FileUtil.exist(filePath)) {
                FileUtil.mkdir(filePath);
            }
            try {
                byte[] bytes = file.getBytes();  // byte
                double size = BigDecimal.valueOf(bytes.length).divide(BigDecimal.valueOf(1024), 3, RoundingMode.HALF_UP).doubleValue();
                commonFiles.setSize(size);
                // 文件上传
                file.transferTo(new File(filePath + fileName));
            } catch (Exception e) {
                log.error("网盘文件上传错误", e);
            }
            String url = "http://" + ip + ":" + port + "/commonFiles/download/" + fileName;
            commonFiles.setFile(url);
        }
        commonFilesMapper.insert(commonFiles);


        if (folderId != null) {  // 外层有目录
            CommonFiles parentFolder = this.selectById(folderId);  // 获取到外层的目录
            Integer rootFolderId = parentFolder.getRootFolderId();
            commonFiles.setRootFolderId(rootFolderId);
        } else {
            if (Constants.IS_FOLDER.equals(folder)) {  // 当前是新建目录操作
                Integer commonFilesId = commonFiles.getId();  // 刚才插入到数据库的文件的ID
                commonFiles.setRootFolderId(commonFilesId);
            }
        }
        this.updateById(commonFiles);  // 更新 root_folder_id 字段的值
    }

    /*文件更新*/
    public void updateById(CommonFiles commonFiles) {
        commonFilesMapper.updateById(commonFiles);
    }

    /*根据ID查询文件*/
    public CommonFiles selectById(Integer id) {
        return commonFilesMapper.selectById(id);

    }

    /*文件下载*/
    public void download(String flag, HttpServletResponse response) {
        OutputStream os;
        try {
            if (StrUtil.isNotEmpty(flag)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(flag, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(filePath + flag);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }


    /*文件预览*/
    public void preview(Integer id, HttpServletResponse response) {
        CommonFiles commonFiles = this.selectById(id);
        if (commonFiles == null) {
            throw new CustomException(ResultCodeEnum.FILE_NOT_FOUND);
        }
        ArrayList<String> typeList = CollUtil.newArrayList("jpg", "jpeg", "png", "pdf", "gif");
        if (!typeList.contains(commonFiles.getType())) {
            throw new CustomException(ResultCodeEnum.TYPE_NOT_SUPPORT);
        }
        OutputStream os;
        try {
            String file = commonFiles.getFile();
            String flag = file.substring(file.lastIndexOf("/"));
            response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(commonFiles.getName(), "UTF-8"));
            byte[] bytes = FileUtil.readBytes(filePath + flag);
            os = response.getOutputStream();
            os.write(bytes);
            os.flush();
            os.close();
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }

    /*文件删除*/
    public void deleteById(Integer id) {

        CommonFiles commonFiles = this.selectById(id);
        if (commonFiles == null) {
            return;
        }
        commonFilesMapper.deleteById(id);  // 删除当前的文件记录
        if (Constants.NOT_FOLDER.equals(commonFiles.getFolder())) {  // 是文件
            // 删除文件
            String file = commonFiles.getFile();
            String path = filePath + file.substring(file.lastIndexOf("/"));
            FileUtil.del(path);
            return;
        }
        List<CommonFiles> children = commonFilesMapper.selectByFolderId(id);
        if (CollUtil.isEmpty(children)) {
            return;
        }
        for (CommonFiles child : children) {
            this.deleteById(child.getId());  // 递归寻找子节点
        }
    }

    /*批量删除*/
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }


    /*查询所有*/
    public List<CommonFiles> selectAll(CommonFiles commonFiles) {
        return commonFilesMapper.selectAll(commonFiles);
    }

    /*分页查询*/
    public PageInfo<CommonFiles> selectPage(CommonFiles commonFiles, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CommonFiles> list = commonFilesMapper.selectAllData(commonFiles);
        return PageInfo.of(list);
    }

    /*查询所有父级目录*/
    public List<CommonFiles> selectFolderNames(Integer folderId, List<CommonFiles> list) {
        CommonFiles commonFiles = this.selectById(folderId);
        if (commonFiles == null) {
            return list;
        }
        list.add(commonFiles);  // 把当前节点的名称加到list里面去  最后一起返回
        Integer parentFolderId = commonFiles.getFolderId(); // 父级ID
        if (parentFolderId == null) {  // 当前目录的外层没有目录  就结束
            return list;
        }
        return this.selectFolderNames(parentFolderId, list);
    }

}
